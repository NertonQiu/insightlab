package com.acxiom.insightlab.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.service.impl.NotificationServiceImpl;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.service.ServletUtils;

/**
 * 
 * Works with Notification service and returns notification service data to
 * client.
 * 
 * @author dmytro.plekhotkin
 * @since 2013-03-19
 * 
 */
@Controller
@RequestMapping("/notificationService")
public class NotificationController extends GlobalExceptionHandler {

	@Autowired
	private NotificationServiceImpl notificationService;

	@RequestMapping("/InsightStatusList")
	public void getInsightStatusList(
			@RequestParam(value = "insightid", required = true) final String[] insightId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);

		JSONObject dataJson = notificationService
				.getInsightStatusList(requestParams);

		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/ModelStatusList")
	public void getModelStatusList(
			@RequestParam(value = "modelid", required = true) final String[] modelId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);

		JSONObject dataJson = notificationService
				.getModelStatusList(requestParams);

		WebHelper.sendJsonResponse(dataJson, response);
	}

	
	@RequestMapping("/getModelScoringStatus")
	public void getModelScoringStatus(
			@RequestParam(value = "modelid", required = true) final String modelId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);

		final JSONObject dataJson = notificationService
				.getModelScoringStatus(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}
	
	@RequestMapping("/getModelScoringStatusList")
	public void getModelScoringStatusList(
			@RequestParam(value = "modelid", required = true) final String[] modelId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);

		final JSONObject dataJson = notificationService
				.getModelScoringStatusList(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}
	
	

}
