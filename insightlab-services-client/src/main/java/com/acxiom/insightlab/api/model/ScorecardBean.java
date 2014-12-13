package com.acxiom.insightlab.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ScorecardBean {

	private String modelID;
	private double count;

	/**
	 * InfoBase element.
	 */
	private String ibElement;
	private String description;
	private double contribution;
	private double averageRank;

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public String getIbElement() {
		return ibElement;
	}

	public void setIbElement(String ibElement) {
		this.ibElement = ibElement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getContribution() {
		return contribution;
	}

	public void setContribution(double contribution) {
		this.contribution = contribution;
	}

	public double getAverageRank() {
		return averageRank;
	}

	public void setAverageRank(double averageRank) {
		this.averageRank = averageRank;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(modelID);
		builder.append(count);
		builder.append(ibElement);
		builder.append(description);
		builder.append(contribution);
		builder.append(averageRank);

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
		if (!(obj instanceof ScorecardBean)) {
			return false;
		}

		final ScorecardBean other = (ScorecardBean) obj;
		final EqualsBuilder builder = new EqualsBuilder();

		builder.append(modelID, other.getModelID());
		builder.append(count, other.getCount());
		builder.append(ibElement, other.getIbElement());
		builder.append(description, other.getDescription());
		builder.append(contribution, other.getContribution());
		builder.append(averageRank, other.getAverageRank());

		return builder.isEquals();
	}

}
