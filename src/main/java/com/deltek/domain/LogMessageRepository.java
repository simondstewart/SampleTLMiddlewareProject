package com.deltek.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "logMessages", path = "logMessages")
public interface LogMessageRepository extends PagingAndSortingRepository<LogMessage, Long> {

	List<LogMessage> findByMessage(String message);
	
}
