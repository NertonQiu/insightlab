package com.acxiom.insightlab.api.service.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.ClientDAO;
import com.acxiom.insightlab.api.dao.InsightsDAO;
import com.acxiom.insightlab.api.service.InsightsService;
import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.web.sso.dataAccess.model.TenantDto;

@Service("insightsService")
public class InsightsServiceImpl implements InsightsService {

	private static final String INSIGHT_COMPLETED = "INSIGHT_COMPLETED";

	@Autowired
	private InsightsDAO insightsDAO;

	@Autowired
	private ClientDAO clientDAO;

	@Override
	public JSONObject getInsightsList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return insightsDAO.getInsightsList(params);
	}

	@Override
	public JSONObject getInsightsValuesList(final JSONObject params)
			throws BaseDataApiException, JSONException {
		final JSONObject result = insightsDAO.getInsightsValuesList(params);
		final JSONArray insightsList = result.getJSONArray("insights");
		final JSONArray completedInsightsList = new JSONArray();
		for (int i = 0; i < insightsList.length(); i++) {
			JSONObject insight = insightsList.optJSONObject(i);
			if (insight != null
					&& insight.optString("status").equals(INSIGHT_COMPLETED)) {
				completedInsightsList.put(insight);
			}
		}

		result.put("insights", completedInsightsList);

		return result;
	}

	@Override
	public JSONObject getInsightsCount(final JSONObject params)
			throws BaseDataApiException, JSONException {

		// aggregation
		TenantDto companyInfo = SecurityUtils.getTenantInfo();
		final String companyId = companyInfo.getCompanyId();
		JSONObject storagePurchased = clientDAO.getStorageUsage(companyId);
		JSONObject insightsCount = insightsDAO.getInsightsCount(params);
		if (storagePurchased != null) {
			insightsCount.put("storagePurchased",
					storagePurchased.getString("storageUsed"));
		}

		return insightsCount;

	}

	@Override
	public JSONObject deleteInsights(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return insightsDAO.deleteInsights(params);
	}

	@Override
	public JSONObject getClientInfo(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return insightsDAO.getClientInfo(params);
	}

	@Override
	public JSONObject scoreAnInsight(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return insightsDAO.scoreAnInsight(params);
	}

	@Override
	public JSONObject getRecentInsights(final JSONObject params)
			throws BaseDataApiException, JSONException {
		return insightsDAO.getRecentInsights(params);
	}

	@Override
	public JSONObject getInsight(JSONObject params) throws BaseDataApiException, JSONException {
		return insightsDAO.getInsight(params);
	}

}
