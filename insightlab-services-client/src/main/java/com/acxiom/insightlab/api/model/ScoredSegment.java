package com.acxiom.insightlab.api.model;

import java.util.Date;
import java.util.List;

public class ScoredSegment {

	private String id;
	private String modelID;
	private String name;
	private String description;
	private int bestRankBucket;
	private int worstRankBucket;
	private String geographicLevel;
	private List<String> geographicEntityIDs;
	private String tenantID;
	private String username;
	private Date createdDate;
	private Date modifiedDate;
	private int individualCount;
	private int householdCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModelID() {
		return modelID;
	}

	public void setModelID(String modelID) {
		this.modelID = modelID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBestRankBucket() {
		return bestRankBucket;
	}

	public void setBestRankBucket(int bestRankBucket) {
		this.bestRankBucket = bestRankBucket;
	}

	public int getWorstRankBucket() {
		return worstRankBucket;
	}

	public void setWorstRankBucket(int worstRankBucket) {
		this.worstRankBucket = worstRankBucket;
	}

	public String getGeographicLevel() {
		return geographicLevel;
	}

	public void setGeographicLevel(String geographicLevel) {
		this.geographicLevel = geographicLevel;
	}

	public List<String> getGeographicEntityIDs() {
		return geographicEntityIDs;
	}

	public void setGeographicEntityIDs(List<String> geographicEntityIDs) {
		this.geographicEntityIDs = geographicEntityIDs;
	}

	public String getTenantID() {
		return tenantID;
	}

	public void setTenantID(String tenantID) {
		this.tenantID = tenantID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getIndividualCount() {
		return individualCount;
	}

	public void setIndividualCount(int individualCount) {
		this.individualCount = individualCount;
	}

	public int getHouseholdCount() {
		return householdCount;
	}

	public void setTotalHouseholdCount(int householdCount) {
		this.householdCount = householdCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bestRankBucket;
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((geographicEntityIDs == null) ? 0 : geographicEntityIDs
						.hashCode());
		result = prime * result
				+ ((geographicLevel == null) ? 0 : geographicLevel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modelID == null) ? 0 : modelID.hashCode());
		result = prime * result
				+ ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((tenantID == null) ? 0 : tenantID.hashCode());
		result = prime * result + householdCount;
		result = prime * result + individualCount;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + worstRankBucket;
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
		ScoredSegment other = (ScoredSegment) obj;
		if (bestRankBucket != other.bestRankBucket) {
			return false;
		}
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
		if (geographicEntityIDs == null) {
			if (other.geographicEntityIDs != null) {
				return false;
			}
		} else if (!geographicEntityIDs.equals(other.geographicEntityIDs)) {
			return false;
		}
		if (geographicLevel == null) {
			if (other.geographicLevel != null) {
				return false;
			}
		} else if (!geographicLevel.equals(other.geographicLevel)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (modelID == null) {
			if (other.modelID != null) {
				return false;
			}
		} else if (!modelID.equals(other.modelID)) {
			return false;
		}
		if (modifiedDate == null) {
			if (other.modifiedDate != null) {
				return false;
			}
		} else if (!modifiedDate.equals(other.modifiedDate)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (tenantID == null) {
			if (other.tenantID != null) {
				return false;
			}
		} else if (!tenantID.equals(other.tenantID)) {
			return false;
		}
		if (householdCount != other.householdCount) {
			return false;
		}
		if (individualCount != other.individualCount) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		if (worstRankBucket != other.worstRankBucket) {
			return false;
		}
		return true;
	}
}
