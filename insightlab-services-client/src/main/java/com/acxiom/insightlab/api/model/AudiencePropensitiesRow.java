package com.acxiom.insightlab.api.model;

public class AudiencePropensitiesRow {
	private int index;
	private Double zScore;
	private String questionID;
	private String questionReportText;
	private Double boxedBasePercent;
	private String responseReportText;
	private String responseText;
	private String responseValue;
	private boolean overUnderFlag;
	private String responseID;
	private Double boxedTargetPercent;
	private int boxedTargetTotal;
	private int boxedBaseCount;
	private int boxedTargetCount;
	private String questionText;
	private int boxedBaseTotal;
	private int fieldPosition;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Double getzScore() {
		return zScore;
	}

	public void setzScore(Double zScore) {
		this.zScore = zScore;
	}

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public String getQuestionReportText() {
		return questionReportText;
	}

	public void setQuestionReportText(String questionReportText) {
		this.questionReportText = questionReportText;
	}

	public Double getBoxedBasePercent() {
		return boxedBasePercent;
	}

	public void setBoxedBasePercent(Double boxedBasePercent) {
		this.boxedBasePercent = boxedBasePercent;
	}

	public String getResponseReportText() {
		return responseReportText;
	}

	public void setResponseReportText(String responseReportText) {
		this.responseReportText = responseReportText;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getResponseValue() {
		return responseValue;
	}

	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}

	public boolean isOverUnderFlag() {
		return overUnderFlag;
	}

	public void setOverUnderFlag(boolean overUnderFlag) {
		this.overUnderFlag = overUnderFlag;
	}

	public String getResponseID() {
		return responseID;
	}

	public void setResponseID(String responseID) {
		this.responseID = responseID;
	}

	public Double getBoxedTargetPercent() {
		return boxedTargetPercent;
	}

	public void setBoxedTargetPercent(Double boxedTargetPercent) {
		this.boxedTargetPercent = boxedTargetPercent;
	}

	public int getBoxedTargetTotal() {
		return boxedTargetTotal;
	}

	public void setBoxedTargetTotal(int boxedTargetTotal) {
		this.boxedTargetTotal = boxedTargetTotal;
	}

	public int getBoxedBaseCount() {
		return boxedBaseCount;
	}

	public void setBoxedBaseCount(int boxedBaseCount) {
		this.boxedBaseCount = boxedBaseCount;
	}

	public int getBoxedTargetCount() {
		return boxedTargetCount;
	}

	public void setBoxedTargetCount(int boxedTargetCount) {
		this.boxedTargetCount = boxedTargetCount;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public int getBoxedBaseTotal() {
		return boxedBaseTotal;
	}

	public void setBoxedBaseTotal(int boxedBaseTotal) {
		this.boxedBaseTotal = boxedBaseTotal;
	}

	public int getFieldPosition() {
		return fieldPosition;
	}

	public void setFieldPosition(int fieldPosition) {
		this.fieldPosition = fieldPosition;
	}

}
