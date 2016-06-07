package com.deltek.domain;

import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LogMessage {

	@Id
	@GeneratedValue
	private Long id;
	
	private String uuid = UUID.randomUUID().toString();
	
	private Calendar dateCreated = Calendar.getInstance();
	
	private String message;

	public LogMessage() {
		super();
	}

	public LogMessage(String message) {
		super();
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LogMessage [id=" + id + ", message=" + message + "]";
	}
	
}
