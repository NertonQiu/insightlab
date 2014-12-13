package com.acxiom.insightlab.api.model;

import java.util.Comparator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class GainBean {
	private double total;
	private double cumulativeTargetPercent;
	private double lowerBound;
	private double targetNumber;
	private double ks;
	private double cumulativeReferencePercent;
	private String reportSource;
	private double cumulativeReference;
	private double cumulativeTotal;
	private double liftCurve;
	private String modelID;
	private double rank;
	private double targetPercent;
	private double lift;
	private double rankGraph;
	private double cumulativeTotalPercent;
	private double baseline;
	private double upperBound;
	private double cumulativeTarget;

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getCumulativeTargetPercent() {
		return cumulativeTargetPercent;
	}

	public void setCumulativeTargetPercent(double cumulativeTargetPercent) {
		this.cumulativeTargetPercent = cumulativeTargetPercent;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public double getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(double targetNumber) {
		this.targetNumber = targetNumber;
	}

	public double getKs() {
		return ks;
	}

	public void setKs(double ks) {
		this.ks = ks;
	}

	public double getCumulativeReferencePercent() {
		return cumulativeReferencePercent;
	}

	public void setCumulativeReferencePercent(double cumulativeReferencePercent) {
		this.cumulativeReferencePercent = cumulativeReferencePercent;
	}

	public String getReportSource() {
		return reportSource;
	}

	public void setReportSource(String reportSource) {
		this.reportSource = reportSource;
	}

	public double getCumulativeReference() {
		return cumulativeReference;
	}

	public void setCumulativeReference(double cumulativeReference) {
		this.cumulativeReference = cumulativeReference;
	}

	public double getCumulativeTotal() {
		return cumulativeTotal;
	}

	public void setCumulativeTotal(double cumulativeTotal) {
		this.cumulativeTotal = cumulativeTotal;
	}

	public double getLiftCurve() {
		return liftCurve;
	}

	public void setLiftCurve(double liftCurve) {
		this.liftCurve = liftCurve;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public double getRank() {
		return rank;
	}

	public void setRank(double rank) {
		this.rank = rank;
	}

	public double getTargetPercent() {
		return targetPercent;
	}

	public void setTargetPercent(double targetPercent) {
		this.targetPercent = targetPercent;
	}

	public double getLift() {
		return lift;
	}

	public void setLift(double lift) {
		this.lift = lift;
	}

	public double getRankGraph() {
		return rankGraph;
	}

	public void setRankGraph(double rankGraph) {
		this.rankGraph = rankGraph;
	}

	public double getCumulativeTotalPercent() {
		return cumulativeTotalPercent;
	}

	public void setCumulativeTotalPercent(double cumulativeTotalPercent) {
		this.cumulativeTotalPercent = cumulativeTotalPercent;
	}

	public double getBaseline() {
		return baseline;
	}

	public void setBaseline(double baseline) {
		this.baseline = baseline;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}

	public double getCumulativeTarget() {
		return cumulativeTarget;
	}

	public void setCumulativeTarget(double cumulativeTarget) {
		this.cumulativeTarget = cumulativeTarget;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.total);
		builder.append(this.cumulativeTargetPercent);
		builder.append(this.lowerBound);
		builder.append(this.targetNumber);
		builder.append(this.ks);
		builder.append(this.cumulativeReferencePercent);
		builder.append(this.reportSource);
		builder.append(this.cumulativeReference);
		builder.append(this.cumulativeTotal);
		builder.append(this.liftCurve);
		builder.append(this.modelID);
		builder.append(this.rank);
		builder.append(this.targetPercent);
		builder.append(this.lift);
		builder.append(this.rankGraph);
		builder.append(this.cumulativeTotalPercent);
		builder.append(this.baseline);
		builder.append(this.upperBound);
		builder.append(this.cumulativeTarget);

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

		if (!(obj instanceof GainBean)) {
			return false;
		}

		final GainBean other = (GainBean) obj;
		final EqualsBuilder builder = new EqualsBuilder();

		builder.append(this.total, other.getTotal());
		builder.append(this.cumulativeTargetPercent,
				other.getCumulativeTargetPercent());
		builder.append(this.lowerBound, other.getLowerBound());
		builder.append(this.targetNumber, other.getTargetNumber());
		builder.append(this.ks, other.getKs());
		builder.append(this.cumulativeReferencePercent,
				other.getCumulativeReferencePercent());
		builder.append(this.reportSource, other.getReportSource());
		builder.append(this.cumulativeReference, other.getCumulativeReference());
		builder.append(this.cumulativeTotal, other.getCumulativeTotal());
		builder.append(this.liftCurve, other.getLiftCurve());
		builder.append(this.modelID, other.getModelID());
		builder.append(this.rank, other.getRank());
		builder.append(this.targetPercent, other.getTargetPercent());
		builder.append(this.lift, other.getLift());
		builder.append(this.rankGraph, other.getRankGraph());
		builder.append(this.cumulativeTotalPercent,
				other.getCumulativeTotalPercent());
		builder.append(this.baseline, other.getBaseline());
		builder.append(this.upperBound, other.getUpperBound());
		builder.append(this.cumulativeTarget, other.getCumulativeTarget());

		return builder.isEquals();
	}

	public static class ComparatorByCumulativeTotalPercent implements
			Comparator<GainBean> {

		@Override
		public int compare(GainBean o1, GainBean o2) {
			int result = 0;
			if (o1.getCumulativeTotalPercent() > o2.getCumulativeTotalPercent()) {
				result = 1;
			} else if (o1.getCumulativeTotalPercent() < o2
					.getCumulativeTotalPercent()) {
				result = -1;
			}
			return result;
		}

	}

}
