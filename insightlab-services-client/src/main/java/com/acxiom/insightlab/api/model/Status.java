package com.acxiom.insightlab.api.model;

public class Status {
	private String status;
	private String percentage;
	private String modelID;

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(final String percentage) {
		this.percentage = percentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(final String modelID) {
		this.modelID = modelID;
	}
}
