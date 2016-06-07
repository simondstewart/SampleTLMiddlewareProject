package com.deltek;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deltek.domain.LogMessage;
import com.deltek.domain.LogMessageRepository;

@Component
public class DevDatabaseLoader {

	private final LogMessageRepository logMessageRepository;

	@Autowired
	public DevDatabaseLoader(LogMessageRepository logMessageRepository) {
		this.logMessageRepository = logMessageRepository;
	}
	
	@PostConstruct
	private void init() {
		LogMessage logMessage = new LogMessage();
		logMessage.setMessage("Message 1");
		logMessageRepository.save(logMessage);

		LogMessage logMessage2 = new LogMessage();
		logMessage2.setMessage("Message 2");
		logMessageRepository.save(logMessage2);
		
		LogMessage logMessage3 = new LogMessage();
		logMessage3.setMessage("Message 3");
		logMessageRepository.save(logMessage3);
	}
	
}
