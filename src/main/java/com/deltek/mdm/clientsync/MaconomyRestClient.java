package com.deltek.mdm.clientsync;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.deltek.domain.maconomy.MRestData;
import com.deltek.domain.maconomy.MRestError;
import com.deltek.domain.maconomy.to.MRestJobBudget;
import com.deltek.domain.maconomy.to.MRestJobBudgetLine;

public class MaconomyRestClient {

	private final String apiBasePath;
	private final Client client;
	
	public MaconomyRestClient(String maconomyUser, String maconomyPassword, String apiBasePath) {
		this.apiBasePath = apiBasePath;
		this.client = buildClientForCurrentUser(maconomyUser, maconomyPassword);
	}
	
    private Client buildClientForCurrentUser(String maconomyUser, String maconomyPassword) {

        HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic(maconomyUser, maconomyPassword);

        Client client = ClientBuilder.newBuilder()
                .register(JacksonFeature.class)
                .register(authFeature)
                .build();
        return client;
    }

    public MRestData<MRestJobBudget, MRestJobBudgetLine> getMaconomyBudget(WebTarget target, String jobNumber) {
        String encodedJobNumber = urlSafeEncodedString(jobNumber);
        WebTarget getTarget = target.path("jobbudgets").path(String.format("data;jobnumber=%s", encodedJobNumber));
        return executeRequest(getTarget);
    }
    
    private String urlSafeEncodedString(String jobNumber){
        try{
            return URLEncoder.encode(jobNumber, "UTF-8").replaceAll("\\+", "%20");
        }catch (UnsupportedEncodingException ex){
            throw new RuntimeException("Error Encoding url for jobNumber: "+jobNumber, ex);
        }
    }
    
    private MRestData<MRestJobBudget, MRestJobBudgetLine> executeRequest(WebTarget getTarget){
        Invocation.Builder getInvocationBuilder = getTarget.request(MediaType.APPLICATION_JSON);
        Response getResponse = getInvocationBuilder.get();

        if (getResponse.getStatus() != 200) {
            throwExceptionFromResponse(getResponse);
        }

        return getResponse.readEntity(new GenericType<MRestData<MRestJobBudget, MRestJobBudgetLine>>() {
        });
    }
    
    private void throwExceptionFromResponse(Response response) {
        String errorMessage = buildErrorMessage(response);
        throw new RuntimeException(errorMessage);
    }
    
    //This is broken.
    private String buildErrorMessage(Response response){
        MRestError restError = response.readEntity(MRestError.class);
        String message = restError.getErrorMessage();

        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append(String.format("Response status: %s %s \n", response.getStatus(), response.getStatusInfo()));
        errorBuilder.append(String.format("Message: %s ", message));
        return errorBuilder.toString();
    }
}
