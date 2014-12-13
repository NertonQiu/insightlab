package com.acxiom.insightlab.co.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

public interface COModelService {
	JSONObject getModelList() throws JSONException, BaseDataApiException;

	JSONObject getModel(String modelId) throws JSONException,
			BaseDataApiException;

}
