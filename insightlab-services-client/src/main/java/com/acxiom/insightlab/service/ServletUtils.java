package com.acxiom.insightlab.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ServletUtils {

	public static final String STUBBY_DATA_CONTEXT = "/data";

	public static final String REFERER_PARAM_NAME = "referer";
	
	private static final String NOCACHE = "nocache"; 

	public static JSONObject getParams(final HttpServletRequest request)
			throws JSONException {

		JSONObject result = new JSONObject();

		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();

		for (Iterator<Entry<String, String[]>> iterator = params.entrySet()
				.iterator(); iterator.hasNext();) {
			Entry<String, String[]> entry = iterator.next();
			String parameterKey = entry.getKey();
			
			result.put(parameterKey, entry.getValue());
		}
		result.remove(NOCACHE);

		return result;
	}

}
