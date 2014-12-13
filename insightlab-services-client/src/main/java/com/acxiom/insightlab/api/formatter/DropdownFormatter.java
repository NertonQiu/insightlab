package com.acxiom.insightlab.api.formatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DropdownFormatter {

	/**
	 * Slf4j logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DropdownFormatter.class);

	/**
	 * Private constructor.
	 */
	private DropdownFormatter() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Formats object, that has array of values to be formatted in root field.
	 * 
	 * @param objectToFormat
	 * @param rootName
	 * @param keyName
	 * @param valueName
	 * @throws JSONException
	 */
	public static void format(final JSONObject objectToFormat,
			final String rootName) throws JSONException {

		format(objectToFormat, rootName, null);
	}

	/**
	 * Formats object, that has array of values for formatting in the node. Node
	 * is a field of root field.
	 * 
	 * @param objectToFormat
	 * @param rootName
	 * @param nodeName
	 * @param keyName
	 * @param valueName
	 * @throws JSONException
	 */
	public static void format(final JSONObject objectToFormat,
			final String rootName, final String nodeName) throws JSONException {

		Object rootObject = objectToFormat.opt(rootName);

		if (rootObject == null) {
			LOGGER.error("Can not format JSON object, Root object does not exist.");
		} else {

			if (nodeName != null) {
				final Object nodeObject = ((JSONObject) rootObject)
						.opt(nodeName);
				if (nodeObject != null) {
					rootObject = nodeObject;
				}
			}

			final JSONArray rootArray = makeJSONArray(rootObject);

			objectToFormat.put("results", rootArray);

			if (!rootName.equals("results")) {
				objectToFormat.remove(rootName);
			}
		}
	}

	/**
	 * Makes JSONArray from.
	 * 
	 * @param json
	 *            Object that can be as JSONArray as JSONObject
	 * @return json array
	 */
	private static JSONArray makeJSONArray(final Object json) {
		JSONArray jsonArray = new JSONArray();
		if (json instanceof JSONArray) {
			jsonArray = (JSONArray) json;
		} else if (json instanceof JSONObject) {
			jsonArray.put(json);
		} else {
			LOGGER.error("Object has wrong type. Expected type is JSONArray or JSONObject.");
		}
		return jsonArray;
	}
}
