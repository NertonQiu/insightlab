package com.acxiom.insightlab.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class InfobaseRankRow {
	private Double cumulativeRank;
	private Double rank;
	private Double percent;

	public Double getCumulativeRank() {
		return cumulativeRank;
	}

	public void setCumulativeRank(Double cumulativeRank) {
		this.cumulativeRank = cumulativeRank;
	}

	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(this.cumulativeRank);
		builder.append(this.rank);
		builder.append(this.percent);
		return builder.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof InfobaseRankRow)) {
			return false;
		}
		final EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.cumulativeRank, this.getCumulativeRank());
		builder.append(this.percent, this.getPercent());
		builder.append(this.rank, this.getRank());

		return builder.isEquals();
	}
}
