package com.acxiom.insightlab.api.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

/**
 * @author dmytro.plekhotkin
 * 
 */
public interface ReportingService {
	
	JSONObject savePersonicxTargetGroup(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getPersonicxPortraitReport(final JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getScattergramReport(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getDPACategories(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getDPAQuestions(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getDPAResponses(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getDPAReport(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getPersonicxTargetGroups(JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getPropensityDetailReport(JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject getAudiencePropensitiesDPAReport(JSONObject params)
			throws BaseDataApiException, JSONException;

	JSONObject savePersonicxPortrait(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getPersonicxPortraits(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject updatePersonicxPortrait(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject deletePersonicxPortrait(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getPersonicxPortraitStub(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getPersonicxTargetGroupReport(JSONObject requestParams)
			throws BaseDataApiException, JSONException;

	JSONObject getTemporarySegmentPersonicxPortraitReport(JSONObject requestParams)
            throws BaseDataApiException, JSONException;
}