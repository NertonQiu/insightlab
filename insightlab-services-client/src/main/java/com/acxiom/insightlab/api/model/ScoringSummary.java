package com.acxiom.insightlab.api.model;

import java.util.Date;

public class ScoringSummary {

	private String modeledDatasetName;
	private int modeledDatasetRecordCount;
	private Date latestScoreDate;
	private ScoringStatus scoringStatus;

	public String getModeledDatasetName() {
		return modeledDatasetName;
	}

	public void setModeledDatasetName(String modeledDatasetName) {
		this.modeledDatasetName = modeledDatasetName;
	}

	public int getModeledDatasetRecordCount() {
		return modeledDatasetRecordCount;
	}

	public void setModeledDatasetRecordCount(int modeledDatasetRecordCount) {
		this.modeledDatasetRecordCount = modeledDatasetRecordCount;
	}

	public Date getLatestScoreDate() {
		return latestScoreDate;
	}

	public void setLatestScoreDate(Date latestScoreDate) {
		this.latestScoreDate = latestScoreDate;
	}

	public ScoringStatus getScoringStatus() {
		return scoringStatus;
	}

	public void setScoringStatus(ScoringStatus scoringStatus) {
		this.scoringStatus = scoringStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((latestScoreDate == null) ? 0 : latestScoreDate.hashCode());
		result = prime
				* result
				+ ((modeledDatasetName == null) ? 0 : modeledDatasetName
						.hashCode());
		result = prime * result + modeledDatasetRecordCount;
		result = prime * result
				+ ((scoringStatus == null) ? 0 : scoringStatus.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ScoringSummary other = (ScoringSummary) obj;
		if (latestScoreDate == null) {
			if (other.latestScoreDate != null) {
				return false;
			}
		} else if (!latestScoreDate.equals(other.latestScoreDate)) {
			return false;
		}
		if (modeledDatasetName == null) {
			if (other.modeledDatasetName != null) {
				return false;
			}
		} else if (!modeledDatasetName.equals(other.modeledDatasetName)) {
			return false;
		}
		if (modeledDatasetRecordCount != other.modeledDatasetRecordCount) {
			return false;
		}
		if (scoringStatus == null) {
			if (other.scoringStatus != null) {
				return false;
			}
		} else if (!scoringStatus.equals(other.scoringStatus)) {
			return false;
		}
		return true;
	}

}
