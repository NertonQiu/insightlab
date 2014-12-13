package com.acxiom.insightlab.api.dao.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.acxiom.insightlab.api.ApiClientJson;
import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.ReportingServiceURIs;
import com.acxiom.insightlab.api.dao.ReportingDAO;

@Component
public class ReportingDAOImpl implements ReportingDAO {

	@Autowired
	@Qualifier("apiClientJson")
	private ApiClientJson apiClient;

	public ApiClientJson getApiClient() {
		return apiClient;
	}

	public void setApiClient(ApiClientJson apiClient) {
		this.apiClient = apiClient;
	}

	@Override
	public JSONObject savePersonicxTargetGroup(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.post(
				ReportingServiceURIs.SAVE_PERSONICX_TARGET_GROUP, params);

		return response;

	}

	@Override
	public JSONObject getPersonicxPortraitReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_PERSONICX_PORTRAIT_REPORT, params);

		return response;
	}

	@Override
	public JSONObject getScattergramReport(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_SCATTERGRAM_REPORT, requestParams);

		return response;
	}

	@Override
	public JSONObject getDPACategories(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_DPA_CATEGORIES, requestParams);

		return response;
	}

	@Override
	public JSONObject getDPAQuestions(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_DPA_QUESTIONS, requestParams);

		return response;
	}

	@Override
	public JSONObject getDPAResponses(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_DPA_RESPONSES, requestParams);

		return response;
	}

	@Override
	public JSONObject getDPAReport(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_DPA_REPORT, requestParams);

		return response;
	}

	@Override
	public JSONObject getPersonicxTargetGroups(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_PERSONICX_TARGET_GROUPS, params);

		return response;
	}

	@Override
	public JSONObject getPropensityDetailReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_PROPENSITY_DETAILS_REPORT, params);

		return response;
	}

	@Override
	public JSONObject getAudiencePropensitiesDPAReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_AUDIENCE_PROPENSITIES_DPA_REPORT,
				params);

		return response;
	}

	@Override
	public JSONObject savePersonicxPortrait(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.post(
				ReportingServiceURIs.SAVE_PERSONICX_PORTRAIT, requestParams);

		return response;
	}

	@Override
	public JSONObject getPersonicxPortraits(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_PERSONICX_PORTRAITS, requestParams);

		return response;
	}

	@Override
	public JSONObject updatePersonicxPortrait(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.put(
				ReportingServiceURIs.UPDATE_PERSONICX_PORTRAIT, requestParams);

		return response;
	}

	@Override
	public JSONObject deletePersonicxPortrait(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.delete(
				ReportingServiceURIs.DELETE_PERSONICX_PORTRAIT, requestParams);

		return response;
	}

	@Override
	public JSONObject getPersonicxPortraitStub(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient
				.get(ReportingServiceURIs.GET_PERSONICX_PORTRAIT_STUB,
						requestParams);

		return response;
	}

	@Override
	public JSONObject getPersonicxTargetGroupReport(JSONObject requestParams)
			throws BaseDataApiException, JSONException {

		final JSONObject response = apiClient.get(
				ReportingServiceURIs.GET_PERSONICX_TARGET_GROUP_REPORT,
				requestParams);

		return response;

	}
	
	@Override
	public JSONObject getTemporarySegmentPersonicxPortraitReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.post(
				ReportingServiceURIs.SEGMENT_PERSONICX_PORTRAIT, params.toString());

		return response;

	}
}