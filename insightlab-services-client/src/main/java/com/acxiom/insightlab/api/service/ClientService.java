package com.acxiom.insightlab.api.service;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

public interface ClientService {
	JSONObject getStorageUsage(String companyId) throws BaseDataApiException, JSONException;
}
