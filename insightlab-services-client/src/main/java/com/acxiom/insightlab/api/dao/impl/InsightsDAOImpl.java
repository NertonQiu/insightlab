package com.acxiom.insightlab.api.dao.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.acxiom.insightlab.api.ApiClientJson;
import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.InsightsServiceURIs;
import com.acxiom.insightlab.api.dao.InsightsDAO;
import com.acxiom.insightlab.service.SecurityUtils;

@Component
public class InsightsDAOImpl implements InsightsDAO {

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
	public JSONObject getInsightsList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				InsightsServiceURIs.INSIGHT_LIST, params);

		return response;
	}

	@Override
	public JSONObject getInsightsValuesList(final JSONObject params)
			throws BaseDataApiException, JSONException {

		final JSONObject response = apiClient.get(
				InsightsServiceURIs.INSIGHT_LIST, params);

		return response;
	}

	@Override
	public JSONObject getInsightsCount(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final Object tenantId = params.opt("tenantid");
		String tenantIdString;
		if (tenantId != null) {
			tenantIdString = ((String[]) tenantId)[0];
		} else {
			tenantIdString = SecurityUtils.getTenantInfo().getCompanyId()
					.toString();
		}
		final JSONObject response = apiClient.get(
				InsightsServiceURIs.INSIGHTS_COUNT + "/" + tenantIdString,
				params);

		return response;
	}

	@Override
	public JSONObject deleteInsights(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.put(
				InsightsServiceURIs.DELETE_INSIGHTS, params);

		return response;
	}

	@Override
	public JSONObject getClientInfo(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				InsightsServiceURIs.CLIENT_INFO, params);

		return response;
	}

	@Override
	public JSONObject scoreAnInsight(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.post(InsightsServiceURIs.SCORE,
				params);

		return response;
	}

	@Override
	public JSONObject getRecentInsights(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				InsightsServiceURIs.GET_RECENT_INSIGHTS, params);

		return response;
	}

	@Override
	public JSONObject getInsight(JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				InsightsServiceURIs.GET_INSIGHT, params);
		return response;
	}

}
