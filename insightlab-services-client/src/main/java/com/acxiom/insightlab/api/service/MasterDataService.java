package com.acxiom.insightlab.api.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

public interface MasterDataService {
	
	/**
	 * Fetches Personicx Reference Files data. Method: GET.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 */
	JSONObject getPersonicxReferenceFiles(final JSONObject params)
			throws BaseDataApiException, JSONException;


	
	/**
	 * Fetches all MRI Categories. Method: GET.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getAllSynDataCategories(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Fetches questions for the MRI Categories
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getSynDataQuestionsBySubcategory(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Fetches MRI Categories. Method: GET.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getResponseForSpecificMRIQuestions(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Fetches the MRI SubCategories. Method: GET.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject getSynDataSubcategoriesByCategory(final JSONObject params)
			throws BaseDataApiException, JSONException;

	
}
