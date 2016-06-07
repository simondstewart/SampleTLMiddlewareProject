package com.deltek;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;

import com.deltek.domain.LogMessage;
import com.deltek.integration.trafficlive.domain.JobTaskTimeEntryPagedResultsTO;
import com.deltek.integration.trafficlive.domain.JobTaskTimeEntryTO;
import com.deltek.integration.trafficlive.service.TrafficLiveRestClient;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {

	@Resource
	private TrafficLiveRestClient tlRestClient;
	
    @Bean
    public MessageSource<?> integerMessageSource() {
        MethodInvokingMessageSource source = new MethodInvokingMessageSource();
        source.setObject(new AtomicInteger());
        source.setMethodName("getAndIncrement");
        return source;
    }
    
    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }
    
    public IntegrationFlow inputFlow() {
        return IntegrationFlows.from(this.integerMessageSource(), c -> 
                                                   c.poller(Pollers.fixedRate(5000)))
                    .channel(this.inputChannel())
                    .filter((Integer p) -> p > 0)
                    .transform(Transformers.converter((Integer counter) -> 
                    		{return ""+tlRestClient.getJobTaskTimeEntries(counter, 1).getResultList().get(0); }))
                    .channel("stringPayloadOutputChannel")
                    .get();
    }
    
    @Bean
    public IntegrationFlow loadPageFlow() {
    	return IntegrationFlows.from(this.integerMessageSource(), c -> c.poller(Pollers.fixedRate(5000)))
    			.channel(this.inputChannel())
    			.transform(Transformers.converter((Integer counter) -> 
    			{return tlRestClient.getJobTaskTimeEntries(counter, 10);}))
    			.split(JobTaskTimeEntryPagedResultsTO.class, JobTaskTimeEntryPagedResultsTO::getResultList)
    			.transform(Transformers.converter((JobTaskTimeEntryTO timeEntry) ->
    					{return "Id: "+timeEntry.id + " " + timeEntry.comment + ""; }))
    			.channel("stringPayloadOutputChannel")
    			.get();
    }
    
    @Bean
    public IntegrationFlow saveMessageFlow() {
		return IntegrationFlows.from("stringPayloadOutputChannel")
                    .transform("Message: "::concat)
                    .transform(Transformers.converter((String message) -> { return new LogMessage(message); }))
                    .handle("logMessageRepository", "save")
                    .channel("responseChannel")
                    .get();
    }
	
    @Bean
    public IntegrationFlow outputFlow() {
    	return IntegrationFlows.from("responseChannel")
    			.handle(System.out::println)
    			.get();
    }
    
}
