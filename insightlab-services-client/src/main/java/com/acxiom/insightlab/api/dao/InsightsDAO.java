package com.acxiom.insightlab.api.dao;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

/**
 * Insights service proxy
 * 
 * @author dmytro.plekhotkin
 * 
 */
public interface InsightsDAO {

	/**
	 * Get insights list for a given Client ID.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getInsightsList(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getInsightsValuesList(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getInsightsCount(JSONObject params) throws BaseDataApiException,
			JSONException;

	/**
	 * Delete insights. Method: PUT.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject deleteInsights(JSONObject params) throws BaseDataApiException,
			JSONException;

	JSONObject getClientInfo(JSONObject params) throws BaseDataApiException,
			JSONException;

	JSONObject scoreAnInsight(JSONObject params) throws BaseDataApiException,
			JSONException;

	JSONObject getRecentInsights(JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getInsight(JSONObject params) throws BaseDataApiException,
			JSONException;

}
