package com.acxiom.insightlab.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ScattergramRow implements Comparable<ScattergramRow> {
	private String age;
	private Integer ib1270;
	private String clusterName;
	private String homeOwnership;
	private String income;
	private String kids;
	private String maritalStatus;
	private String netWorth;
	private String urbanicity;
	private int chartIndex1;
	private int chartIndex2;
	private int index1;
	private int index2;
	private String quadrant;
	private String groupName;
	private String groupCode;
	private String ageText;
	private double referencePercent;

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Integer getIb1270() {
		return ib1270;
	}

	public void setIb1270(Integer ib1270) {
		this.ib1270 = ib1270;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getHomeOwnership() {
		return homeOwnership;
	}

	public void setHomeOwnership(String homeOwnership) {
		this.homeOwnership = homeOwnership;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getKids() {
		return kids;
	}

	public void setKids(String kids) {
		this.kids = kids;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	public String getUrbanicity() {
		return urbanicity;
	}

	public void setUrbanicity(String urbanicity) {
		this.urbanicity = urbanicity;
	}

	public int getChartIndex1() {
		return chartIndex1;
	}

	public void setChartIndex1(int chartIndex1) {
		this.chartIndex1 = chartIndex1;
	}

	public int getChartIndex2() {
		return chartIndex2;
	}

	public void setChartIndex2(int chartIndex2) {
		this.chartIndex2 = chartIndex2;
	}

	public int getIndex1() {
		return index1;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public int getIndex2() {
		return index2;
	}

	public void setIndex2(int index2) {
		this.index2 = index2;
	}

	public String getQuadrant() {
		return quadrant;
	}

	public void setQuadrant(String quadrant) {
		this.quadrant = quadrant;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getAgeText() {
		return ageText;
	}

	public void setAgeText(String ageText) {
		this.ageText = ageText;
	}

	public double getReferencePercent() {
		return referencePercent;
	}

	public void setReferencePercent(double referencePercent) {
		this.referencePercent = referencePercent;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.chartIndex1);
		builder.append(this.chartIndex2);
		builder.append(this.ib1270);
		builder.append(this.index1);
		builder.append(this.index2);
		builder.append(this.referencePercent);
		builder.append(this.age);
		builder.append(this.ageText);
		builder.append(this.clusterName);
		builder.append(this.groupCode);
		builder.append(this.groupName);
		builder.append(this.homeOwnership);
		builder.append(this.income);
		builder.append(this.kids);
		builder.append(this.maritalStatus);
		builder.append(this.netWorth);
		builder.append(this.quadrant);
		builder.append(this.urbanicity);

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
		if (getClass() != obj.getClass()) {
			return false;
		}

		final ScattergramRow other = (ScattergramRow) obj;

		final EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.chartIndex1, other.getChartIndex1());
		builder.append(this.chartIndex2, other.getChartIndex2());
		builder.append(this.ib1270, other.getIb1270());
		builder.append(this.index1, other.getIndex1());
		builder.append(this.index2, other.getIndex2());
		builder.append(this.referencePercent, other.getReferencePercent());
		builder.append(this.age, other.getAge());
		builder.append(this.ageText, other.getAgeText());
		builder.append(this.clusterName, other.getClusterName());
		builder.append(this.groupCode, other.getGroupCode());
		builder.append(this.groupName, other.getGroupName());
		builder.append(this.homeOwnership, other.getHomeOwnership());
		builder.append(this.income, other.getIncome());
		builder.append(this.kids, other.getKids());
		builder.append(this.maritalStatus, other.getMaritalStatus());
		builder.append(this.netWorth, other.getNetWorth());
		builder.append(this.quadrant, other.getQuadrant());
		builder.append(this.urbanicity, other.getUrbanicity());
		return builder.isEquals();
	}

	@Override
	public int compareTo(ScattergramRow scattergramRow) {
		int result = 0;
		if (referencePercent - scattergramRow.referencePercent > 0) {
			result = 1;
		} else if (referencePercent - scattergramRow.referencePercent < 0) {
			result = -1;
		}
		return result;
	}
}
