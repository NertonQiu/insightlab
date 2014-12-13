package com.acxiom.insightlab.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.service.ClientService;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.service.SecurityUtils;

@Controller
@Deprecated
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@RequestMapping("/storageUsage")
	public void getStorageUsage(
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		
		final String companyId = SecurityUtils.getTenantInfo().getCompanyId();
		
		final JSONObject storageUsage = clientService
				.getStorageUsage(companyId);

		WebHelper.sendJsonResponse(storageUsage, response);
	}
}
