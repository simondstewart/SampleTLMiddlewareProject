package com.deltek.mdm.clientsync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.deltek.domain.LogMessage;
import com.deltek.integration.maconomy.client.MaconomyRestClient;
import com.deltek.integration.trafficlive.domain.EmployeePagedResultsTO;
import com.deltek.integration.trafficlive.service.TrafficLiveRestClient;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmployeeSyncController {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	public TrafficLiveRestClient trafficLiveRestClient;

	@Autowired
	public MaconomyRestClient maconomyRestClient;
	
	@RequestMapping(path="/api/sync/employee", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ingestToTrafficLIVEFromMaconomy(
		@RequestParam(value = "currentPage", required = false, defaultValue="0") Integer currentPage,
		@RequestParam(value = "windowSize", required = false, defaultValue="100") Integer windowSize) {
		//Get Maconomy Page of Records.
		//For each record decide whether we need to insert a new record or update an existing.
		//We should be able to decide, by searching for TrafficLiveEmployees where employeeNumber = externalCode.
		EmployeePagedResultsTO result = trafficLiveRestClient.employee().getPage(currentPage, windowSize);
		return ResponseEntity.ok().body("Success importing all records");
	}
	
	@Bean
	private MaconomyRestClient maconomyRestClient() {
		return new MaconomyRestClient("Administrator", "123456", "http://193.17.206.161:4111/containers/v1/x1demo/");
	}
	
}
