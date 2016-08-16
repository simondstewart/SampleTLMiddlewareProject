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

import com.deltek.domain.maconomy.Data;
import com.deltek.domain.maconomy.Endpoint;
import com.deltek.domain.maconomy.Data;
import com.deltek.domain.maconomy.Error;
import com.deltek.domain.maconomy.to.EmployeeCard;
import com.deltek.domain.maconomy.to.EmployeeTable;
import com.deltek.domain.maconomy.to.HoursJournal;
import com.deltek.domain.maconomy.to.JobBudget;
import com.deltek.domain.maconomy.to.JobBudgetLine;
import com.deltek.domain.maconomy.to.Journal;

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

    //GET base endpoint
    //GET http://193.17.206.162:4111/containers/v1/x1demo/jobjournal
    //follow insert action and POST with empty body.
    //Take the response template, fill out the required fields.
    //follow the 
    
    public Endpoint getJobJournalEndpoint() {
    	 WebTarget getTarget = client.target(apiBasePath).path("jobjournal");
         Invocation.Builder getInvocationBuilder = getTarget.request(MediaType.APPLICATION_JSON);
         Response getResponse = getInvocationBuilder.get();

         if (getResponse.getStatus() != 200) {
             throwExceptionFromResponse(getResponse);
         }

         return getResponse.readEntity(new GenericType<Endpoint>(){});
    }
    
    public class JobJournal extends Data<Journal, HoursJournal> {};
    
    public JobJournal getJobJournal() {
    	return new JobJournal();
    }
    
    public Data<EmployeeCard, EmployeeTable> getMaconomyEmployees(WebTarget target) {
        return executeRequest(target, new GenericType<Data<EmployeeCard, EmployeeTable>>(){});
    }
    
    public Data<JobBudget, JobBudgetLine> getMaconomyBudget(WebTarget target, String jobNumber) {
        String encodedJobNumber = urlSafeEncodedString(jobNumber);
        WebTarget getTarget = target.path("jobbudgets").path(String.format("data;jobnumber=%s", encodedJobNumber));
        return executeRequest(getTarget, new GenericType<Data<JobBudget, JobBudgetLine>>(){});
    }
    
    private String urlSafeEncodedString(String jobNumber){
        try{
            return URLEncoder.encode(jobNumber, "UTF-8").replaceAll("\\+", "%20");
        }catch (UnsupportedEncodingException ex){
            throw new RuntimeException("Error Encoding url for jobNumber: "+jobNumber, ex);
        }
    }
    
    private <CARD extends Object, TABLE extends Object> Data<CARD, TABLE>
    					executeRequest(WebTarget getTarget, GenericType<Data<CARD, TABLE>> type){
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
        Error restError = response.readEntity(Error.class);
        String message = restError.getErrorMessage();

        StringBuilder errorBuilder = new StringBuilder();
        errorBuilder.append(String.format("Response status: %s %s \n", response.getStatus(), response.getStatusInfo()));
        errorBuilder.append(String.format("Message: %s ", message));
        return errorBuilder.toString();
    }
}
