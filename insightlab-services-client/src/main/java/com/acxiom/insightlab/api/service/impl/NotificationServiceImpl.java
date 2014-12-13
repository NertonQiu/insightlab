package com.acxiom.insightlab.api.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.NotificationDAO;
import com.acxiom.insightlab.api.service.NotificationService;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDAO notificationDAO;

	@Override
	public JSONObject getInsightStatus(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return notificationDAO.getInsightStatus(params);
	}

	@Override
	public JSONObject getModelStatusAlongWithPercentComplete(
			final JSONObject params) throws BaseDataApiException, JSONException {
		return notificationDAO.getModelStatusAlongWithPercentComplete(params);
	}

	@Override
	public JSONObject getInsightStatusList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return notificationDAO.getInsightStatusList(params);
	}

	@Override
	public JSONObject getModelStatusList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return notificationDAO.getModelStatusList(params);
	}

	@Override
	public JSONObject getModelScoringStatus(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return notificationDAO.getModelScoringStatus(params);
	}

	@Override
	public JSONObject getModelScoringStatusList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return notificationDAO.getModelScoringStatusList(params);
	}

}
