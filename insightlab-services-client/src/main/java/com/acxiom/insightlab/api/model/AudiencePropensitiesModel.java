package com.acxiom.insightlab.api.model;

import java.util.List;

public class AudiencePropensitiesModel {
  private String category;
  private String reportType;
  private TimeSettings settings;
  private String target;
  private String reference;
  private List<AudiencePropensitiesRow> reportData;
  public String getCategory() {
    return category;
  }
  public void setCategory(String category) {
    this.category = category;
  }
  public String getReportType() {
    return reportType;
  }
  public void setReportType(String reportType) {
    this.reportType = reportType;
  }
  public TimeSettings getSettings() {
    return settings;
  }
  public void setSettings(TimeSettings settings) {
    this.settings = settings;
  }
  public String getTarget() {
    return target;
  }
  public void setTarget(String target) {
    this.target = target;
  }
  public String getReference() {
    return reference;
  }
  public void setReference(String reference) {
    this.reference = reference;
  }
  public List<AudiencePropensitiesRow> getReportData() {
    return reportData;
  }
  public void setReportData(List<AudiencePropensitiesRow> reportData) {
    this.reportData = reportData;
  }
}
