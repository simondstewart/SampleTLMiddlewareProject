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
import com.deltek.integration.maconomy.client.MaconomyRestClient;
import com.deltek.integration.maconomy.domain.to.HoursJournal;
import com.deltek.integration.maconomy.domain.to.JobJournal;
import com.deltek.integration.maconomy.domain.to.Journal;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleTlMiddlewareProjectApplication.class)
public class MaconomyRestClientTest {

	private MaconomyRestClient mrc = new MaconomyRestClient("Administrator", "123456", "http://193.17.206.162:4111/containers/v1/x1demo");
	
	@Test
	public void jobJournalGet() {
		Endpoint endPoint = mrc.jobJournal().endPoint();
		Assert.assertEquals("jobjournal", endPoint.getContainerName());
		Assert.assertNotNull(endPoint.getLinks().getLinks().get("data:filter"));
		Assert.assertNotNull(endPoint.getLinks().getLinks().get("specification"));
		Assert.assertNotNull(endPoint.getLinks().getLinks().get("data:any-key"));
	}

	@Test
	public void journal(){
		Record<Journal> templateJournal = mrc.jobJournal().init();
		Assert.assertNotNull(templateJournal);
		Assert.assertNotNull(templateJournal.getData());
		CardTableContainer<Journal, HoursJournal> createdJournal = mrc.jobJournal().createCard(templateJournal);
		Assert.assertNotNull(createdJournal);
	}
	
}
