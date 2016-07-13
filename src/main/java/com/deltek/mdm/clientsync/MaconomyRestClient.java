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
import com.deltek.domain.maconomy.to.MRestEmployeeCard;
import com.deltek.domain.maconomy.to.MRestEmployeeTable;
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

    public MRestData<MRestEmployeeCard, MRestEmployeeTable> getMaconomyEmployees(WebTarget target) {
        return executeRequest(target, new GenericType<MRestData<MRestEmployeeCard, MRestEmployeeTable>>(){});
    }
    
    public MRestData<MRestJobBudget, MRestJobBudgetLine> getMaconomyBudget(WebTarget target, String jobNumber) {
        String encodedJobNumber = urlSafeEncodedString(jobNumber);
        WebTarget getTarget = target.path("jobbudgets").path(String.format("data;jobnumber=%s", encodedJobNumber));
        return executeRequest(getTarget, new GenericType<MRestData<MRestJobBudget, MRestJobBudgetLine>>(){});
    }
    
    private String urlSafeEncodedString(String jobNumber){
        try{
            return URLEncoder.encode(jobNumber, "UTF-8").replaceAll("\\+", "%20");
        }catch (UnsupportedEncodingException ex){
            throw new RuntimeException("Error Encoding url for jobNumber: "+jobNumber, ex);
        }
    }
    
    private <CARD extends Object, TABLE extends Object> MRestData<CARD, TABLE>
    					executeRequest(WebTarget getTarget, GenericType<MRestData<CARD, TABLE>> type){
        Invocation.Builder getInvocationBuilder = getTarget.request(MediaType.APPLICATION_JSON);
        Response getResponse = getInvocationBuilder.get();

        if (getResponse.getStatus() != 200) {
            throwExceptionFromResponse(getResponse);
        }

        return getResponse.readEntity(type);
    }
    
    private void throwExceptionFromResponse(Response response) {
        String errorMessage = buildErrorMessage(response);
        throw new RuntimeException(errorMessage);
    }
    
    //TODO: This is broken, HTTP error responses are not handled correctly.
    private String buildErrorMessage(Response response){
        MRestError restError = response.readEntity(MRestError.class);
        String message = restError.getErrorMessage();

        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append(String.format("Response status: %s %s \n", response.getStatus(), response.getStatusInfo()));
        errorBuilder.append(String.format("Message: %s ", message));
        return errorBuilder.toString();
    }
}
