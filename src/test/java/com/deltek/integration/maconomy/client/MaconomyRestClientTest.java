package com.deltek.integration.maconomy.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deltek.SampleTlMiddlewareProjectApplication;
import com.deltek.integration.maconomy.domain.CardTableContainer;
import com.deltek.integration.maconomy.domain.Endpoint;
import com.deltek.integration.maconomy.domain.Record;
import com.deltek.integration.maconomy.psorestclient.MaconomyPSORestContext;
import com.deltek.integration.maconomy.psorestclient.domain.HoursJournal;
import com.deltek.integration.maconomy.psorestclient.domain.JobJournal;
import com.deltek.integration.maconomy.psorestclient.domain.Journal;
import com.deltek.integration.maconomy.client.MaconomyRestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleTlMiddlewareProjectApplication.class)
public class MaconomyRestClientTest {

	private final MaconomyRestClient mrc = new MaconomyRestClient("Administrator", "123456", "http://193.17.206.162:4111/containers/v1/x1demo");
	private final MaconomyPSORestContext psoContext = new MaconomyPSORestContext(mrc);
	
	@Test
	public void jobJournalGet() {
		Endpoint endPoint = psoContext.jobJournal().endPoint();
		Assert.assertEquals("jobjournal", endPoint.getContainerName());
		Assert.assertNotNull(endPoint.getLinks().getLinks().get("data:filter"));
		Assert.assertNotNull(endPoint.getLinks().getLinks().get("specification"));
		Assert.assertNotNull(endPoint.getLinks().getLinks().get("data:any-key"));
	}

	@Test
	public void journal(){
		Record<Journal> templateJournal = psoContext.jobJournal().init();
		Assert.assertNotNull(templateJournal);
		Assert.assertNotNull(templateJournal.getData());
		CardTableContainer<Journal, HoursJournal> createdJournal = psoContext.jobJournal().createCard(templateJournal);
		Assert.assertNotNull(createdJournal);
	}
	
}
