package com.acxiom.insightlab.api.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InfobaseRankModel {

	private List<Double> cumulativeRank;
	private List<Double> rank;
	private List<Double> percent;
	private String modelID;
	private double rankTotal;

	public List<Double> getCumulativeRank() {
		return cumulativeRank;
	}

	public void setCumulativeRank(final List<Double> cumulativeRank) {
		this.cumulativeRank = Collections.unmodifiableList(cumulativeRank);
	}

	public List<Double> getRank() {
		return rank;
	}

	public void setRank(final List<Double> rank) {
		this.rank = Collections.unmodifiableList(rank);
	}

	public List<Double> getPercent() {
		return percent;
	}

	public void setPercent(final List<Double> percent) {
		this.percent = Collections.unmodifiableList(percent);
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(final String modelID) {
		this.modelID = modelID;
	}

	public double getRankTotal() {
		return rankTotal;
	}

	public void setRankTotal(double rankTotal) {
		this.rankTotal = rankTotal;
	}

	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.cumulativeRank);
		builder.append(this.rank);
		builder.append(this.percent);
		builder.append(this.modelID);
		builder.append(this.rankTotal);

		return builder.toHashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof InfobaseRankModel)) {
			return false;
		}

		final InfobaseRankModel other = (InfobaseRankModel) obj;
		final EqualsBuilder builder = new EqualsBuilder();

		builder.append(this.rankTotal, other.getRankTotal());
		builder.append(this.rank, other.getRank());
		builder.append(this.cumulativeRank, other.getCumulativeRank());
		builder.append(this.modelID, other.getModelID());
		builder.append(this.percent, other.getPercent());

		return builder.isEquals();
	}
}
