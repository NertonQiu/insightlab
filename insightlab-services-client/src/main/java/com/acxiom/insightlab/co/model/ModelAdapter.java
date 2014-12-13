package com.acxiom.insightlab.co.model;

import java.util.Date;

import com.acxiom.insightlab.api.model.Model;
import com.acxiom.insightlab.api.model.Status;

public class ModelAdapter extends Model {
	private final COModel instance;
	private static final String DEFAULT_SOURCE = "CO";

	public ModelAdapter(final COModel instance) {
		this.instance = instance;
		super.setSource(DEFAULT_SOURCE);
		super.setStatus(new Status());
	}

	@Override
	public String getId() {
		return instance.getId();
	}

	@Override
	public void setId(final String id) {
		instance.setId(id);
	}

	@Override
	public String getDescription() {
		return instance.getDescription();
	}

	@Override
	public void setDescription(final String description) {
		instance.setDescription(description);
	}

	@Override
	public String getUserID() {
		return instance.getUserID();
	}

	@Override
	public void setUserID(final String userID) {
		instance.setUserID(userID);
	}

	@Override
	public String getTenantID() {
		return instance.getClientID();
	}

	@Override
	public void setTenantID(final String tenantID) {
		instance.setClientID(tenantID);
	}

	@Override
	public Date getCreatedDate() {
		return instance.getCreatedDate();
	}

	@Override
	public void setCreatedDate(final Date createdDate) {
		instance.setCreatedDate(createdDate);
	}
}
