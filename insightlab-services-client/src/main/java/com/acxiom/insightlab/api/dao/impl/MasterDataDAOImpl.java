package com.acxiom.insightlab.api.dao.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.acxiom.insightlab.api.ApiClientJson;
import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.MasterDataServiceURIs;
import com.acxiom.insightlab.api.dao.MasterDataDAO;

@Component
public class MasterDataDAOImpl implements MasterDataDAO {

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
	public JSONObject getPersonicxReferenceFiles(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				MasterDataServiceURIs.GET_PERSONICX_REFERENCE_FILES, params);
		return response;

	}

	@Override
	public JSONObject getAllSynDataCategories(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				MasterDataServiceURIs.GET_ALL_SYN_DATA_CATEGORIES, params);

		

		return response;

	}

	@Override
	public JSONObject getSynDataQuestionsBySubcategory(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				MasterDataServiceURIs.GET_SYN_DATA_QUESTIONS_BY_SUBCATEGORY, params);

		

		return response;

	}

	@Override
	public JSONObject getResponseForSpecificMRIQuestions(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				MasterDataServiceURIs.GET_RESPONSE_FOR_MRI_QUESTION, params);

		

		return response;

	}

	@Override
	public JSONObject getSynDataSubcategoriesByCategory(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject response = apiClient.get(
				MasterDataServiceURIs.GET_SYN_DATA_SUBCATEGORIES_BY_CATEGORY, params);

		

		return response;

	}

}
