package com.deltek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.TestRestTemplate.HttpClientOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.deltek.integration.trafficlive.service.TrafficLiveRestClient;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
public class SampleTlMiddlewareProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleTlMiddlewareProjectApplication.class, args);
	}
	
	//Temporary configuration location.  Place the core context beans below.
	@Bean
	public RestTemplate restTemplate() {
		TestRestTemplate basicAuthRestTemplate = new TestRestTemplate("simonstewart@deltek.com", "turK0QoPU5gkO6usNGfZcYcBpMEIaI0nUNLZQQSA");
		return basicAuthRestTemplate;
	}
	
	@Bean
	public TrafficLiveRestClient trafficLiveRestClient() {
		TrafficLiveRestClient tlClient = new TrafficLiveRestClient(restTemplate(), "https://api.sohnar.com/TrafficLiteServer/openapi");
		return tlClient;
	}
	
}
