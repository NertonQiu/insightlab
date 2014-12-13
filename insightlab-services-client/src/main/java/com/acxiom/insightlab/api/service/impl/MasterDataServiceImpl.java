package com.acxiom.insightlab.api.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.MasterDataDAO;
import com.acxiom.insightlab.api.service.MasterDataService;

@Service("masterDataService")
public class MasterDataServiceImpl implements MasterDataService {

	@Autowired
	private MasterDataDAO masterDataDAO;

	@Override
	public JSONObject getPersonicxReferenceFiles(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return masterDataDAO.getPersonicxReferenceFiles(params);
	}

	@Override
	public JSONObject getAllSynDataCategories(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return masterDataDAO.getAllSynDataCategories(params);
	}

	@Override
	public JSONObject getSynDataQuestionsBySubcategory(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return masterDataDAO.getSynDataQuestionsBySubcategory(params);

	}

	@Override
	public JSONObject getResponseForSpecificMRIQuestions(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return masterDataDAO.getResponseForSpecificMRIQuestions(params);

	}

	@Override
	public JSONObject getSynDataSubcategoriesByCategory(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return masterDataDAO.getSynDataSubcategoriesByCategory(params);
	}
}
