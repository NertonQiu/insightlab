package com.acxiom.insightlab.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PersonicxReferenceModel {
	private String type;
	private String id;
	private String question;
	private String response;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.id);
		builder.append(this.question);
		builder.append(this.response);
		builder.append(this.type);

		return builder.toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof PersonicxReferenceModel)) {
			return false;
		}

		final EqualsBuilder builder = new EqualsBuilder();

		final PersonicxReferenceModel other = (PersonicxReferenceModel) obj;
		builder.append(this.id, other.getId());
		builder.append(this.question, other.getQuestion());
		builder.append(this.response, other.getResponse());
		builder.append(this.type, other.getType());
		return builder.isEquals();
	}
}
