package com.acxiom.insightlab.api.formatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class TableFormatter {
	private TableFormatter() {
		throw new UnsupportedOperationException();
	}

	public static void format(final JSONObject objectToFormat,
			final String rowsKey) throws JSONException {
		if (objectToFormat != null) {
			final JSONArray rowsObject = objectToFormat.getJSONArray(rowsKey);
			objectToFormat.put("rows", rowsObject);
			SettingsFormatter.addDateTimeSettings(objectToFormat);
			objectToFormat.remove(rowsKey);
		}
	}
	
	
}
