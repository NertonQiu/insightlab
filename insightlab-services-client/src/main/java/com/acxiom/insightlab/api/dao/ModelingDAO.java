package com.acxiom.insightlab.api.dao;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

/**
 * @author dmytro.plekhotkin
 * 
 */
public interface ModelingDAO {

	/**
	 * Create models from an Insight. Method: POST.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject createModel(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Update models status for a given modelId with Status. Method: PUT.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject updateModelStatus(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Update models status for multiple modelIDs. Status will be the same for
	 * all updated models. Method: PUT.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject updateModelStatusForMultipleModels(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Fetch model list for a given user. Method: GET.
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject fetchModelList(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Deletes models. Returns list of models with updated status or failure
	 * message. Api call: /deleteModel. Method: PUT.
	 * 
	 * @param params
	 * @return model columns with updated status or fail status as json object
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject deleteModels(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Searches My Models, My Library, or Propensity Models. Returns found
	 * models etc. Api call: /searchModels. Method: GET.
	 * 
	 * @param params
	 * @return list of found models as json object
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject searchModels(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Fetches model library data. Api call: /getModelLibrary. Method: GET.
	 * 
	 * @param params
	 * @return model library data based as json object
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject fetchModelLibraryData(final JSONObject params)
			throws BaseDataApiException, JSONException;

	/**
	 * Saves model library data for one or more model ids. Api call:
	 * /saveModelToLibrary. Method: POST.
	 * 
	 * @param params
	 * @return api response as json object
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject saveModelToLibrary(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject deleteModelFromLibrary(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	/**
	 * Fetch Model Report
	 * 
	 * @param params
	 * @return
	 * @throws BaseDataApiException
	 * @throws JSONException
	 */
	JSONObject fetchModelReport(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject createModelCounts(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getModelCounts(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject createSegment(String segment) throws BaseDataApiException;

	JSONObject updateSegment(String segment) throws BaseDataApiException;

}