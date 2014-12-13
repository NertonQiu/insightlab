package com.acxiom.insightlab.api.dao.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.api.ApiClientJson;
import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.ModelingServiceURIs;
import com.acxiom.insightlab.api.dao.ModelingDAO;

@Component
public class ModelingDAOImpl implements ModelingDAO {

	@Autowired
	@Qualifier("apiClientJson")
	private ApiClientJson apiClient;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${app.api.endpoint}")
	private String endpoint;

	public ApiClientJson getApiClient() {
		return apiClient;
	}

	public void setApiClient(final ApiClientJson apiClient) {
		this.apiClient = apiClient;
	}

	@Override
	public JSONObject createModel(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.post(ModelingServiceURIs.MODEL,
				params);

		return response;

	}

	@Override
	public JSONObject updateModelStatus(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.put(
				ModelingServiceURIs.MODEL_STATUS, params);

		return response;

	}

	@Override
	public JSONObject updateModelStatusForMultipleModels(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.put(
				ModelingServiceURIs.SET_MODEL_STATUS, params);

		return response;

	}

	@Override
	public JSONObject fetchModelList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ModelingServiceURIs.GET_MODEL_LIST, params);

		return response;

	}

	@Override
	public JSONObject deleteModels(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.put(
				ModelingServiceURIs.DELETE_MODELS, params);

		return response;

	}

	@Override
	public JSONObject searchModels(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ModelingServiceURIs.SEARCH_MODELS, params);

		return response;

	}

	@Override
	public JSONObject fetchModelLibraryData(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ModelingServiceURIs.GET_MODEL_LIBRARY, params);

		return response;
	}

	@Override
	public JSONObject saveModelToLibrary(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.post(
				ModelingServiceURIs.SAVE_MODEL_TO_LIBRARY, params);

		return response;

	}

	@Override
	public JSONObject deleteModelFromLibrary(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.put(
				ModelingServiceURIs.DELETE_MODEL_FROM_LIBRARY, requestParams);

		return response;
	}

	@Override
	public JSONObject fetchModelReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(ModelingServiceURIs.MODEL
				+ "/" + ((String[]) params.opt("modelid"))[0], params);

		return response;

	}

	@Override
	public JSONObject createModelCounts(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.post(
				ModelingServiceURIs.CREATE_MODEL_COUNTS, requestParams);

		return response;
	}

	@Override
	public JSONObject getModelCounts(JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				ModelingServiceURIs.GET_MODEL_COUNTS, requestParams);

		return response;
	}

	@Override
	public JSONObject createSegment(String segment) throws BaseDataApiException {
		final JSONObject response = apiClient.post(
				ModelingServiceURIs.CREATE_SEGMENT, segment);

		return response;
	}

	@Override
	public JSONObject updateSegment(String segment) throws BaseDataApiException {
		final JSONObject response = apiClient.put(
				ModelingServiceURIs.UPDATE_SEGMENT, segment);
		return response;
	}

}
