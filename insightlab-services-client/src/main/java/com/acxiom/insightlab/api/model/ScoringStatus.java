package com.acxiom.insightlab.api.model;

public class ScoringStatus {

	private String modelID;
	private String percentage;
	private String status;

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modelID == null) ? 0 : modelID.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ScoringStatus other = (ScoringStatus) obj;
		if (modelID == null) {
			if (other.modelID != null) {
				return false;
			}
		} else if (!modelID.equals(other.modelID)) {
			return false;
		}
		if (percentage == null) {
			if (other.percentage != null) {
				return false;
			}
		} else if (!percentage.equals(other.percentage)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}
}
