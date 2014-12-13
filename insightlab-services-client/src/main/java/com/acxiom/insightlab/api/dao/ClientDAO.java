package com.acxiom.insightlab.api.dao;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.api.BaseDataApiException;

public interface ClientDAO {

	JSONObject getStorageUsage(String companyId) throws BaseDataApiException, JSONException;
}
