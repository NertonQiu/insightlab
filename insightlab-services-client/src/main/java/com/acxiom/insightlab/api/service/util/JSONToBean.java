package com.acxiom.insightlab.api.service.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONToBean {

	private static final DateFormat DB_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static <T> T getBean(JSONObject json, Class<T> resultClass)
			throws JSONException, JsonParseException, JsonMappingException,
			IOException {

		ObjectMapper mapper = new ObjectMapper();

		mapper.setDateFormat(DB_DATE_FORMAT);
		T result = mapper.readValue(json.toString(), resultClass);

		return result;

	}
}
