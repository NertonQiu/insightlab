package com.acxiom.insightlab.api.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class InfobaseRankWrapper {

	private final List<InfobaseRankRow> infobaseRankRowList;
	private final String modelID;
	private final double rankTotal;

	public InfobaseRankWrapper(final InfobaseRankModel ibRank) {
		modelID = ibRank.getModelID();
		rankTotal = ibRank.getRankTotal();

		final List<Double> cumulativeRank = ibRank.getCumulativeRank();
		final List<Double> rank = ibRank.getRank();
		final List<Double> percent = ibRank.getPercent();

		infobaseRankRowList = new ArrayList<InfobaseRankRow>();
		InfobaseRankRow infobaseRankRow;
		for (int i = 0; i < cumulativeRank.size(); i++) {
			infobaseRankRow = new InfobaseRankRow();
			infobaseRankRow.setCumulativeRank(cumulativeRank.get(i));
			infobaseRankRow.setRank(rank.get(i));
			infobaseRankRow.setPercent(percent.get(i));

			infobaseRankRowList.add(infobaseRankRow);
		}
	}

	/**
	 * @return the infobaseRankRowList
	 */
	public List<InfobaseRankRow> getInfobaseRankRowList() {
		return infobaseRankRowList;
	}

	/**
	 * @return the modelID
	 */
	public String getModelID() {
		return modelID;
	}

	/**
	 * @return the rankTotal
	 */
	public double getRankTotal() {
		return rankTotal;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(this.infobaseRankRowList);
		builder.append(this.modelID);
		builder.append(this.rankTotal);
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

		if (!(obj instanceof InfobaseRankWrapper)) {
			return false;
		}

		final InfobaseRankWrapper other = (InfobaseRankWrapper) obj;
		final EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.infobaseRankRowList, other.getInfobaseRankRowList());
		builder.append(this.modelID, other.getModelID());
		builder.append(this.rankTotal, other.getRankTotal());
		return builder.isEquals();
	}
}
