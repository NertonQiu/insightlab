package com.acxiom.insightlab.api.formatter;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.web.sso.dataAccess.model.GlobalProfileDto;

public final class SettingsFormatter {
	private SettingsFormatter() {
		throw new UnsupportedOperationException();
	}
	
	public static void addDateTimeSettings(final JSONObject objectToFormat) throws JSONException {
		if (objectToFormat != null) {
			objectToFormat.put("settings", getDateTimeSettings());
		}
	}

	private static JSONObject getDateTimeSettings() throws JSONException {
		final GlobalProfileDto globalProfile = SecurityUtils.getGlobalProfile();

		final JSONObject dateTimeSettings = new JSONObject();
		dateTimeSettings.put("utcOffset", globalProfile.getUtcOffset());
		dateTimeSettings.put("dateFormat", globalProfile.getUserDateFormat());
		dateTimeSettings.put("timeFormat", globalProfile.getUserTimeFormat());
		dateTimeSettings.put("timeZoneId", globalProfile.getTimeZoneInfoID());

		return dateTimeSettings;
	}
	
}
