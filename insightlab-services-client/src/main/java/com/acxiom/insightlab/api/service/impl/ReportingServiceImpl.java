package com.acxiom.insightlab.api.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.ReportingDAO;
import com.acxiom.insightlab.api.service.ReportingService;

@Service("reportingService")
public class ReportingServiceImpl implements ReportingService {

	@Autowired
	private ReportingDAO reportingDAO;

	@Override
	public JSONObject savePersonicxTargetGroup(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.savePersonicxTargetGroup(params);
	}

	@Override
	public JSONObject getPersonicxPortraitReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getPersonicxPortraitReport(params);
	}

	@Override
	public JSONObject getScattergramReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getScattergramReport(params);
	}

	@Override
	public JSONObject getDPACategories(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getDPACategories(params);
	}

	@Override
	public JSONObject getDPAQuestions(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getDPAQuestions(params);
	}

	@Override
	public JSONObject getDPAResponses(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getDPAResponses(params);
	}

	@Override
	public JSONObject getDPAReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getDPAReport(params);
	}

	@Override
	public JSONObject getPersonicxTargetGroups(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getPersonicxTargetGroups(params);
	}

	@Override
	public JSONObject getPropensityDetailReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getPropensityDetailReport(params);
	}

	@Override
	public JSONObject getAudiencePropensitiesDPAReport(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return reportingDAO.getAudiencePropensitiesDPAReport(params);
	}

	@Override
	public JSONObject savePersonicxPortrait(final JSONObject requestParams)
			throws BaseDataApiException, JSONException {
		return reportingDAO.savePersonicxPortrait(requestParams);
	}

	@Override
	public JSONObject getPersonicxPortraits(final JSONObject requestParams) throws BaseDataApiException, JSONException {
		
		return reportingDAO.getPersonicxPortraits(requestParams);
	}
	@Override
	public JSONObject updatePersonicxPortrait(final JSONObject requestParams)  throws BaseDataApiException, JSONException {
		return reportingDAO.updatePersonicxPortrait(requestParams);
	}

	@Override
	public JSONObject deletePersonicxPortrait(final JSONObject requestParams) throws BaseDataApiException, JSONException {
		return reportingDAO.deletePersonicxPortrait(requestParams);
	}

	@Override
	public JSONObject getPersonicxPortraitStub(final JSONObject requestParams)throws BaseDataApiException, JSONException {
		return reportingDAO.getPersonicxPortraitStub(requestParams);
	}

	@Override
	public JSONObject getPersonicxTargetGroupReport(final JSONObject requestParams) throws BaseDataApiException, JSONException {
		return reportingDAO.getPersonicxTargetGroupReport(requestParams);
	}

	@Override
    public JSONObject getTemporarySegmentPersonicxPortraitReport(final JSONObject requestParams) throws BaseDataApiException, JSONException {
    	return reportingDAO.getTemporarySegmentPersonicxPortraitReport(requestParams);
    }

}