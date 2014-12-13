package com.acxiom.insightlab.co.model;

import java.util.Date;

public class COModel {

	private String userID;
	private String description;
	private String id;
	private String clientID;
	private String equation;
	private Date createdDate;

	public String getUserID() {
		return userID;
	}

	public void setUserID(final String userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(final String clientID) {
		this.clientID = clientID;
	}

	public String getEquation() {
		return equation;
	}

	public void setEquation(final String equation) {
		this.equation = equation;
	}

	public Date getCreatedDate() {
		return createdDate == null ? null : new Date(createdDate.getTime());
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate == null ? null : new Date(
				createdDate.getTime());
	}

}
