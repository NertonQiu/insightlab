package com.acxiom.insightlab.api.dao.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.acxiom.insightlab.api.ApiClientJson;
import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.NotificationServiceURIs;
import com.acxiom.insightlab.api.dao.NotificationDAO;

@Component
public class NotificationDAOImpl implements NotificationDAO {

	@Autowired
	@Qualifier("apiClientJson")
	private ApiClientJson apiClient;
	
	public ApiClientJson getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClientJson apiClient) {
		this.apiClient = apiClient;
	}
	
	
	@Override
	public JSONObject getInsightStatus(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				NotificationServiceURIs.INSIGHT, params);

		return response;

	}

	@Override
	public JSONObject getModelStatusAlongWithPercentComplete(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				NotificationServiceURIs.MODEL_STATUS, params);

		return response;

	}

	@Override
	public JSONObject getInsightStatusList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				NotificationServiceURIs.INSIGHT_STATUS_LIST, params);

		return response;

	}

	@Override
	public JSONObject getModelStatusList(JSONObject requestParams) throws BaseDataApiException {
		final JSONObject response = apiClient.get(
				NotificationServiceURIs.MODEL_STATUS_LIST, requestParams);
		return response;
	}
	
	@Override
	public JSONObject getModelScoringStatus(JSONObject requestParams) throws BaseDataApiException {
		final JSONObject response = apiClient.get(
				NotificationServiceURIs.GET_MODEL_SCORING_STATUS, requestParams);
		return response;
	}

	@Override
	public JSONObject getModelScoringStatusList(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				NotificationServiceURIs.GET_MODEL_SCORING_STATUS_LIST, requestParams);
		return response;
	}

}
