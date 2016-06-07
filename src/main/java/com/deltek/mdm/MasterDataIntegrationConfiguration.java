package com.deltek.mdm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.endpoint.MethodInvokingMessageSource;
import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.messaging.support.GenericMessage;

//@Configuration
public class MasterDataIntegrationConfiguration {

	private Log log = LogFactory.getLog(getClass());

	@Resource
	private MasterDataCollector masterDataCollector;
	
	/**
	 * Produce Master Data Messages, by retrieving using the MasterDataSource.
	 * @return
	 */
	IntegrationFlow masterDataProducer() {
		return IntegrationFlows.from(this.masterDataRequestSource(), 
				consumer -> consumer.poller(Pollers.fixedDelay(5000)))
				.channel(this.dataMessageChannel())
				.get();
	}
	
    private DirectChannel dataMessageChannel() {
		return new DirectChannel();
	}

	@Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }
    
	IntegrationFlow masterDataConsumer() {
		return IntegrationFlows.from(
				 this.dataMessageChannel())
				.route(new HeaderValueRouter(MasterDataMessage.HEADER_ACTION))
				.get();
	}
	
    @Bean
    public MessageSource<?> masterDataRequestSource() {
        MethodInvokingMessageSource source = new MethodInvokingMessageSource();
        source.setObject(masterDataCollector);
        source.setMethodName("getOrderedMasterDataMessages");
        return source;
    }

    /**
     * Retrieve a Collection of MasterDataMessages.
     * 
     * @author SimonStewart
     *
     */
    interface MasterDataCollector {
    	List<MasterDataMessage<?>> getOrderedMasterDataMessages();
    }
    
    /**
     * Call the TrafficLIVE Service to 
     * @author SimonStewart
     *
     */
    class TrafficLiveMasterDataCollector implements MasterDataCollector {

		@Override
		public List<MasterDataMessage<?>> getOrderedMasterDataMessages() {
			List<MasterDataMessage<?>> result = new ArrayList<>();
			result.addAll(getCreates());
			result.addAll(getUpdates());
			result.addAll(getDeletes());
			return result;
		}
		
		private List<MasterDataMessage<?>> getCreates() {
			//select data where id > last id retrieved.
			return Collections.emptyList();
		}
    	
		private Collection<? extends MasterDataMessage<?>> getUpdates() {
			//select data where id < last id retrieved and dataModified > last date retrieved.
			return Collections.emptyList();
		}

		private Collection<? extends MasterDataMessage<?>> getDeletes() {
			//Select deletes from audit table. OR Ignore. OR get all records you have now and consolidate with current stored values.
			return Collections.emptyList();
		}

    }
    
    /*
     * Base Domain Object for the Master Data Context.
     */
    class MasterDataType {
    	
    	public Long id;
    	public Calendar dateModified;

    }
    
    enum MasterDataAction {
    	CREATE,
    	UPDATE,
    	DELETE;
    }
    
    class MasterDataMessage<MASTER_TYPE extends MasterDataType> extends GenericMessage<MASTER_TYPE> {

    	public static final String HEADER_ACTION = "HEADER_ACTION";
    	
    	private final MasterDataAction masterDataAction;
    	
		public MasterDataMessage(MASTER_TYPE payload, MasterDataAction masterDataAction) {
			super(payload);
			this.masterDataAction = masterDataAction;
		}

		public MasterDataAction getMasterDataAction() {
			return masterDataAction;
		}
		
	};
    
    class MasterDataServiceRequestMessage extends GenericMessage<Object>  {

		public MasterDataServiceRequestMessage(Object payload) {
			super(payload);
		}

    }
    
}
