package com.acxiom.insightlab.api.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

// com.acxiom.insightlab.model.ModelReportBean
public class ModelReportBean {
	private String tenantID;
	private String status;
	private List<ScorecardBean> scorecard;
	private List<GainBean> gain;
	private InfobaseRankModel ibRank;
	private String id;
	private String userID;
	private String source;
	private int isPrivate;
	private String description;
	private String insightDescription;
	private Date createdDate;
	private String insightID;
	private String type;
	private Boolean insightDeleted;
	private int segmentCount;
	private ScoringSummary scoringSummary;
	private ScoringStatus scoringStatus;
	private List<ScoredSegment> scoredSegments;

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ScorecardBean> getScorecard() {
		return scorecard;
	}

	public void setScorecard(List<ScorecardBean> scorecard) {
		this.scorecard = scorecard;
	}

	public List<GainBean> getGain() {
		return gain;
	}

	public void setGain(List<GainBean> gain) {
		this.gain = gain;
	}

	public InfobaseRankModel getIbRank() {
		return ibRank;
	}

	public void setIbRank(InfobaseRankModel ibRank) {
		this.ibRank = ibRank;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(int isPrivate) {
		this.isPrivate = isPrivate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInsightDescription() {
		return insightDescription;
	}

	public void setInsightDescription(String insightDescription) {
		this.insightDescription = insightDescription;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getInsightID() {
		return insightID;
	}

	public void setInsightID(String insightID) {
		this.insightID = insightID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getInsightDeleted() {
		return insightDeleted;
	}

	public void setInsightDeleted(Boolean insightDeleted) {
		this.insightDeleted = insightDeleted;
	}

	public int getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount) {
		this.segmentCount = segmentCount;
	}

	public ScoringSummary getScoringSummary() {
		return scoringSummary;
	}

	public void setScoringSummary(ScoringSummary scoringSummary) {
		this.scoringSummary = scoringSummary;
	}

	public ScoringStatus getScoringStatus() {
		return scoringStatus;
	}

	public void setScoringStatus(ScoringStatus scoringStatus) {
		this.scoringStatus = scoringStatus;
	}

	public List<ScoredSegment> getScoredSegments() {
		return scoredSegments;
	}

	public void setScoredSegments(List<ScoredSegment> scoredSegments) {
		this.scoredSegments = scoredSegments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((gain == null) ? 0 : gain.hashCode());
		result = prime * result + ((ibRank == null) ? 0 : ibRank.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((insightDeleted == null) ? 0 : insightDeleted.hashCode());
		result = prime
				* result
				+ ((insightDescription == null) ? 0 : insightDescription
						.hashCode());
		result = prime * result
				+ ((insightID == null) ? 0 : insightID.hashCode());
		result = prime * result + isPrivate;
		result = prime * result
				+ ((scorecard == null) ? 0 : scorecard.hashCode());
		result = prime * result
				+ ((scoredSegments == null) ? 0 : scoredSegments.hashCode());
		result = prime * result
				+ ((scoringStatus == null) ? 0 : scoringStatus.hashCode());
		result = prime * result
				+ ((scoringSummary == null) ? 0 : scoringSummary.hashCode());
		result = prime * result + segmentCount;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((tenantID == null) ? 0 : tenantID.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		ModelReportBean other = (ModelReportBean) obj;
		if (createdDate == null) {
			if (other.createdDate != null) {
				return false;
			}
		} else if (!createdDate.equals(other.createdDate)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (gain == null) {
			if (other.gain != null) {
				return false;
			}
		} else if (!gain.equals(other.gain)) {
			return false;
		}
		if (ibRank == null) {
			if (other.ibRank != null) {
				return false;
			}
		} else if (!ibRank.equals(other.ibRank)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (insightDeleted == null) {
			if (other.insightDeleted != null) {
				return false;
			}
		} else if (!insightDeleted.equals(other.insightDeleted)) {
			return false;
		}
		if (insightDescription == null) {
			if (other.insightDescription != null) {
				return false;
			}
		} else if (!insightDescription.equals(other.insightDescription)) {
			return false;
		}
		if (insightID == null) {
			if (other.insightID != null) {
				return false;
			}
		} else if (!insightID.equals(other.insightID)) {
			return false;
		}
		if (isPrivate != other.isPrivate) {
			return false;
		}
		if (scorecard == null) {
			if (other.scorecard != null) {
				return false;
			}
		} else if (!scorecard.equals(other.scorecard)) {
			return false;
		}
		if (scoredSegments == null) {
			if (other.scoredSegments != null) {
				return false;
			}
		} else if (!scoredSegments.equals(other.scoredSegments)) {
			return false;
		}
		if (scoringStatus == null) {
			if (other.scoringStatus != null) {
				return false;
			}
		} else if (!scoringStatus.equals(other.scoringStatus)) {
			return false;
		}
		if (scoringSummary == null) {
			if (other.scoringSummary != null) {
				return false;
			}
		} else if (!scoringSummary.equals(other.scoringSummary)) {
			return false;
		}
		if (segmentCount != other.segmentCount) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		} else if (!source.equals(other.source)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		if (tenantID == null) {
			if (other.tenantID != null) {
				return false;
			}
		} else if (!tenantID.equals(other.tenantID)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (userID == null) {
			if (other.userID != null) {
				return false;
			}
		} else if (!userID.equals(other.userID)) {
			return false;
		}
		return true;
	}

}
