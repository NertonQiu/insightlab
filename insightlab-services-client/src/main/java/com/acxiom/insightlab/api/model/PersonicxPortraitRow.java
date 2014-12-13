package com.acxiom.insightlab.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PersonicxPortraitRow {

	private Integer ib1270;
	private String clusterName;
	private String groupCode;
	private String groupName;
	private String maritalStatus;
	private String age;
	private String income;
	private String netWorth;
	private String homeOwnership;
	private String kids;
	private String urbanicity;
	private String ageText;
	private int baseCount;
	private int targetCount;
	private int baseTotal;
	private int targetTotal;
	private double basePercent;
	private double targetPercent;
	private double penetration;
	private int index;
	private String probabilityIndex;
	private double zScore;
	private String bubbleSize;
	private Boolean usedInMultipleGroups;

	public Integer getIb1270() {
		return ib1270;
	}

	public void setIb1270(Integer ib1270) {
		this.ib1270 = ib1270;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	public String getHomeOwnership() {
		return homeOwnership;
	}

	public void setHomeOwnership(String homeOwnership) {
		this.homeOwnership = homeOwnership;
	}

	public String getKids() {
		return kids;
	}

	public void setKids(String kids) {
		this.kids = kids;
	}

	public String getUrbanicity() {
		return urbanicity;
	}

	public void setUrbanicity(String urbanicity) {
		this.urbanicity = urbanicity;
	}

	public String getAgeText() {
		return ageText;
	}

	public void setAgeText(String ageText) {
		this.ageText = ageText;
	}

	public int getBaseCount() {
		return baseCount;
	}

	public void setBaseCount(int baseCount) {
		this.baseCount = baseCount;
	}

	public int getTargetCount() {
		return targetCount;
	}

	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}

	public int getBaseTotal() {
		return baseTotal;
	}

	public void setBaseTotal(int baseTotal) {
		this.baseTotal = baseTotal;
	}

	public int getTargetTotal() {
		return targetTotal;
	}

	public void setTargetTotal(int targetTotal) {
		this.targetTotal = targetTotal;
	}

	public double getBasePercent() {
		return basePercent;
	}

	public void setBasePercent(double basePercent) {
		this.basePercent = basePercent;
	}

	public double getTargetPercent() {
		return targetPercent;
	}

	public void setTargetPercent(double targetPercent) {
		this.targetPercent = targetPercent;
	}

	public double getPenetration() {
		return penetration;
	}

	public void setPenetration(double penetration) {
		this.penetration = penetration;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getProbabilityIndex() {
		return probabilityIndex;
	}

	public void setProbabilityIndex(String probabilityIndex) {
		this.probabilityIndex = probabilityIndex;
	}

	public double getzScore() {
		return zScore;
	}

	public void setzScore(double zScore) {
		this.zScore = zScore;
	}

	public String getBubbleSize() {
		return bubbleSize;
	}

	public void setBubbleSize(String bubbleSize) {
		this.bubbleSize = bubbleSize;
	}

	public Boolean getUsedInMultipleGroups() {
		return usedInMultipleGroups;
	}

	public void setUsedInMultipleGroups(Boolean usedInMultipleGroups) {
		this.usedInMultipleGroups = usedInMultipleGroups;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();

		builder.append(this.baseCount);
		builder.append(this.basePercent);
		builder.append(this.baseTotal);
		builder.append(this.index);
		builder.append(this.penetration);
		builder.append(this.targetCount);
		builder.append(this.targetPercent);
		builder.append(this.targetTotal);
		builder.append(this.zScore);
		builder.append(this.age);
		builder.append(this.ageText);
		builder.append(this.bubbleSize);
		builder.append(this.clusterName);
		builder.append(this.groupCode);
		builder.append(this.groupName);
		builder.append(this.homeOwnership);
		builder.append(this.ib1270);
		builder.append(this.income);
		builder.append(this.kids);
		builder.append(this.maritalStatus);
		builder.append(this.netWorth);
		builder.append(this.probabilityIndex);
		builder.append(this.urbanicity);
		builder.append(this.usedInMultipleGroups);

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

		if (!(obj instanceof PersonicxPortraitRow)) {
			return false;
		}

		final PersonicxPortraitRow other = (PersonicxPortraitRow) obj;

		final EqualsBuilder builder = new EqualsBuilder();

		builder.append(this.baseCount, other.getBaseCount());
		builder.append(this.basePercent, other.getBasePercent());
		builder.append(this.baseTotal, other.getBaseTotal());
		builder.append(this.index, other.getIndex());
		builder.append(this.penetration, other.getPenetration());
		builder.append(this.targetCount, other.getTargetCount());
		builder.append(this.targetPercent, other.getTargetPercent());
		builder.append(this.targetTotal, other.getTargetTotal());
		builder.append(this.zScore, other.getzScore());
		builder.append(this.age, other.getAge());
		builder.append(this.ageText, other.getAgeText());
		builder.append(this.bubbleSize, other.getBubbleSize());
		builder.append(this.clusterName, other.getClusterName());
		builder.append(this.groupCode, other.getGroupCode());
		builder.append(this.groupName, other.getGroupName());
		builder.append(this.homeOwnership, other.getHomeOwnership());
		builder.append(this.ib1270, other.getIb1270());
		builder.append(this.income, other.getIncome());
		builder.append(this.kids, other.getKids());
		builder.append(this.maritalStatus, other.getMaritalStatus());
		builder.append(this.netWorth, other.getNetWorth());
		builder.append(this.probabilityIndex, other.getProbabilityIndex());
		builder.append(this.urbanicity, other.getUrbanicity());
		builder.append(this.usedInMultipleGroups,
				other.getUsedInMultipleGroups());

		return builder.isEquals();

	}
}
