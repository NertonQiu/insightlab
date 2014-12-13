package com.acxiom.insightlab.co.dao;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.co.model.COModel;

public interface COModelDAO {

	List<COModel> getModelList() throws BaseDataApiException;

	JSONObject getModel(final String modelId) throws JSONException,
			BaseDataApiException;

}
