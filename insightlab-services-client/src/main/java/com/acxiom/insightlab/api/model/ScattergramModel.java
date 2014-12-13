package com.acxiom.insightlab.api.model;

import java.util.List;

public class ScattergramModel {
  private String insightDescription;
  private String insightID;
  private String refQuestion;
  private String refResponse;
  private TimeSettings settings;
  private List<ScattergramRow> scattergramData;

  public String getInsightDescription() {
    return insightDescription;
  }

  public void setInsightDescription(String insightDescription) {
    this.insightDescription = insightDescription;
  }

  public String getInsightID() {
    return insightID;
  }

  public void setInsightID(String insightID) {
    this.insightID = insightID;
  }

  public String getRefQuestion() {
    return refQuestion;
  }

  public void setRefQuestion(String refQuestion) {
    this.refQuestion = refQuestion;
  }

  public String getRefResponse() {
    return refResponse;
  }

  public void setRefResponse(String refResponse) {
    this.refResponse = refResponse;
  }

  public TimeSettings getSettings() {
    return settings;
  }

  public void setSettings(TimeSettings settings) {
    this.settings = settings;
  }

  public List<ScattergramRow> getScattergramData() {
    return scattergramData;
  }

  public void setScattergramData(List<ScattergramRow> scattergramData) {
    this.scattergramData = scattergramData;
  }


}
