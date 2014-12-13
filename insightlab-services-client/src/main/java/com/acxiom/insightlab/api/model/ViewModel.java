package com.acxiom.insightlab.api.model;

public class ViewModel {
	private String id;
	private String description;
	private String userID;

	private String insightID;
	private String insightDescription;
	private String tenantID;
	private String createdDate;
	private String source;
	private int isPrivate;
	private String type;

	private Status status;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(final String userID) {
		this.userID = userID;
	}

	public String getInsightID() {
		return insightID;
	}

	public void setInsightID(final String insightID) {
		this.insightID = insightID;
	}

	public String getInsightDescription() {
		return insightDescription;
	}

	public void setInsightDescription(final String insightDescription) {
		this.insightDescription = insightDescription;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(final String tenantID) {
		this.tenantID = tenantID;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final String createdDate) {
		this.createdDate = createdDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public int getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(final int isPrivate) {
		this.isPrivate = isPrivate;
	}
}