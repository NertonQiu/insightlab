package com.acxiom.insightlab.sso.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceClientJsonAdapter {

	@Autowired
	private UserServiceClient userService;

	public JSONObject isAppUser(final String appName) throws JSONException {
		//boolean isAppUser = userService.isAppUser(appName);
		//by jack
		boolean isAppUser = true;
		JSONObject isAppUserJson = new JSONObject();
		isAppUserJson.put("isUser", isAppUser);
		return isAppUserJson;
	}

}
