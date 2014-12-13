package com.acxiom.insightlab.api.service.impl;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.ClientDAO;
import com.acxiom.insightlab.api.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDAO clientDAO;  
	
	@Override
	public JSONObject getStorageUsage(final String companyId) throws BaseDataApiException,
			JSONException {
		return clientDAO.getStorageUsage(companyId);
	}

}
