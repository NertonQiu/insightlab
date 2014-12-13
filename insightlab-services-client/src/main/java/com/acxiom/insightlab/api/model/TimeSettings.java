package com.acxiom.insightlab.api.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TimeSettings {

	private String timeFormat;
	private int utcOffset;
	private String dateFormat;
	private String timeZoneId;

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public int getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(int utcOffset) {
		this.utcOffset = utcOffset;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(dateFormat);
		builder.append(timeFormat);
		builder.append(timeZoneId);
		builder.append(utcOffset);
		return builder.toHashCode();
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

		final TimeSettings other = (TimeSettings) obj;

		final EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.dateFormat, other.getDateFormat());
		builder.append(this.timeFormat, other.getTimeFormat());
		builder.append(this.timeZoneId, other.getTimeZoneId());
		builder.append(utcOffset, other.getUtcOffset());

		return builder.isEquals();
	}

}
