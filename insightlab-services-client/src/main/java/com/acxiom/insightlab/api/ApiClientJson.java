package com.acxiom.insightlab.api;

import org.json.JSONObject;

public interface ApiClientJson {

	JSONObject get(final String path, final JSONObject params)
			throws BaseDataApiException;

	JSONObject post(final String path, final JSONObject params)
			throws BaseDataApiException;

	JSONObject put(final String path, final JSONObject params)
			throws BaseDataApiException;

	JSONObject delete(final String path, final JSONObject params)
			throws BaseDataApiException;

	JSONObject post(String path, String body) throws BaseDataApiException;

	JSONObject put(String path, String body) throws BaseDataApiException;
}
