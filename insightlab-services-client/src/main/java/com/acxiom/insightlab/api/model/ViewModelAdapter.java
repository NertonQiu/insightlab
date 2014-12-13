package com.acxiom.insightlab.api.model;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ViewModelAdapter extends ViewModel {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	public ViewModelAdapter(final Model model) {
		final String formattedCreatedDate = dateFormat.format(model
				.getCreatedDate());
		this.setCreatedDate(formattedCreatedDate);
        this.setTenantID(model.getTenantID());
		this.setDescription(model.getDescription());
		this.setId(model.getId());
		this.setInsightDescription(model.getInsightDescription());
		this.setInsightID(model.getInsightID());
		this.setIsPrivate(model.getIsPrivate());
		this.setSource(model.getSource());
		this.setStatus(model.getStatus());
		this.setType(model.getType());
		this.setUserID(model.getUserID());
	}

}
