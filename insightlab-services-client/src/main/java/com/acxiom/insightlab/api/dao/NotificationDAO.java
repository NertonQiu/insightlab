package com.acxiom.insightlab.api.dao;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

/**
 * 
 * @author dmytro.plekhotkin
 * 
 */
public interface NotificationDAO {
	/**
	 * Get current status of an Insight. Method: GET
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getInsightStatus(JSONObject params) throws BaseDataApiException,
			JSONException;

	/**
	 * Fetch model's status with percentage complete for a given modelID.
	 * Method: GET .
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getModelStatusAlongWithPercentComplete(JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Not described in docs.
	 * 
	 * @since 2013-02-07
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getInsightStatusList(JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getModelStatusList(JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getModelScoringStatus(JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getModelScoringStatusList(JSONObject params)
			throws BaseDataApiException, JSONException;
}
