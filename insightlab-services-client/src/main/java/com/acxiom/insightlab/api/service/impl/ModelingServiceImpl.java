package com.acxiom.insightlab.api.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.ModelingDAO;
import com.acxiom.insightlab.api.service.ModelingService;
import com.acxiom.insightlab.co.dao.COModelDAO;
import com.acxiom.insightlab.sso.service.UserServiceClient;

@Service("modelingService")
public class ModelingServiceImpl implements ModelingService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ModelingServiceImpl.class);

	@Value("${co.applicationName}")
	private String coApplicationName;
	@Autowired
	private ModelingDAO modelingDAO;

	@Autowired
	private COModelDAO coModelDAO;

	@Autowired
	private UserServiceClient userService;

	@Override
	public JSONObject createModel(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.createModel(params);
	}

	@Override
	public JSONObject updateModelStatus(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.updateModelStatus(params);
	}

	@Override
	public JSONObject updateModelStatusForMultipleModels(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.updateModelStatusForMultipleModels(params);

	}

	@Override
	public JSONObject fetchModelList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.fetchModelList(params);
	}

	@Override
	public JSONObject deleteModels(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.deleteModels(params);
	}

	@Override
	public JSONObject searchModels(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.searchModels(params);
	}

	@Override
	public JSONObject fetchModelLibraryData(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.fetchModelLibraryData(params);
	}

	@Override
	public JSONObject saveModelToLibrary(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.saveModelToLibrary(params);
	}

	@Override
	public JSONObject deleteModelFromLibrary(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.deleteModelFromLibrary(params);
	}

	@Override
	public JSONObject fetchModelReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.fetchModelReport(params);
	}

	@Override
	public JSONObject createModelCounts(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.createModelCounts(params);
	}

	@Override
	public JSONObject getModelCounts(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return modelingDAO.getModelCounts(params);
	}

	@Override
	public JSONObject createSegment(String segment) throws JSONException,
			BaseDataApiException {
		return modelingDAO.createSegment(segment);
	}

	@Override
	public JSONObject updateSegment(String segment) throws JSONException,
			BaseDataApiException {
		return modelingDAO.updateSegment(segment);
	}

}
