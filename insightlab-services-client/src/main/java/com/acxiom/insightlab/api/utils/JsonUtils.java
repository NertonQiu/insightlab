package com.acxiom.insightlab.api.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

/**
 * 
 * @author dmytro.plekhotkin
 * 
 */
public final class JsonUtils {

	/**
	 * Private constructor. For using as helper class only.
	 */
	private JsonUtils() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Converts string to JSONObject.
	 * 
	 * @param str
	 *            String that has json structure.
	 * @return JSONObject or null if input string is null or empty.
	 * @throws BaseDataApiException
	 */
	public static JSONObject stringToJSONObject(final String str)
			throws BaseDataApiException {
		JSONObject json = null;
		if (str != null && !str.trim().equals("")) {

			try {
				if (validJsonArray(str)) {

					final JSONArray jsonArray = new JSONArray(str);
					json = new JSONObject();
					json.put("results", jsonArray);

				} else if (validJsonObject(str)) {
					json = new JSONObject(str);
				} else {
					json = new JSONObject();
					json.put("results", str);
				}
			} catch (JSONException e) {
				throw new BaseDataApiException(e.getMessage(), e);
			}
		}
		return json;
	}

	private static boolean validJsonArray(final String str) {
		return isWrappedBy(str, '[', ']');
	}

	private static boolean validJsonObject(final String str) {
		return isWrappedBy(str, '{', '}');
	}

	private static boolean isWrappedBy(final String str, final char firstChar,
			final char lastChar) {
		return str.charAt(0) == firstChar
				&& str.charAt(str.length() - 1) == lastChar;
	}

}
