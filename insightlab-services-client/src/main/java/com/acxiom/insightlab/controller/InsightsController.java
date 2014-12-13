package com.acxiom.insightlab.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.formatter.DropdownFormatter;
import com.acxiom.insightlab.api.formatter.TableFormatter;
import com.acxiom.insightlab.api.service.impl.InsightsServiceImpl;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.service.ServletUtils;

/**
 * 
 * Works with Insights service and returns insights service data to client.
 * 
 * @author dmytro.plekhotkin
 * @since 2013-03-05
 * 
 */
@Controller
@RequestMapping("/Insights")
public class InsightsController extends GlobalExceptionHandler {

	@Autowired
	private InsightsServiceImpl insightsService;

	@RequestMapping("/InsightList")
	public void insightList(
			@RequestParam(value = "userid", required = false) final String userId,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "isprepping", required = false) final Boolean isPrepping,
			@RequestParam(value = "orderby", required = false) final String orderBy,
			@RequestParam(value = "orderasc", required = false) final Boolean orderAsc,
			@RequestParam(value = "skip", required = false) final String skip,
			@RequestParam(value = "next", required = false) final String next,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);
		
		final JSONObject dataJson = insightsService
				.getInsightsList(requestParams);

		TableFormatter.format(dataJson, "insights");
		WebHelper.sendJsonResponse(dataJson, response);
	}
	
	@RequestMapping("/getInsight")
	public void getInsight(
			@RequestParam(value = "insightid", required = true) final String insightId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		
		final JSONObject dataJson = insightsService
				.getInsight(requestParams);

		WebHelper.sendJsonResponse(dataJson, response);
	}

	/**
	 * @deprecated Use getInsight.
	 * 
	 * @param userId
	 * @param filter
	 * @param isPrepping
	 * @param orderBy
	 * @param orderAsc
	 * @param skip
	 * @param next
	 * @param request
	 * @param response
	 * @throws JSONException
	 * @throws BaseDataApiException
	 * @throws IOException
	 */
	@RequestMapping("/getInsightsValuesList")
	public void getInsightsValuesList(
			@RequestParam(value = "userid", required = false) final String userId,
			@RequestParam(value = "filter", required = false) final String filter,
			@RequestParam(value = "isprepping", required = false) final Boolean isPrepping,
			@RequestParam(value = "orderby", required = false) final String orderBy,
			@RequestParam(value = "orderasc", required = false) final Boolean orderAsc,
			@RequestParam(value = "skip", required = false) final String skip,
			@RequestParam(value = "next", required = false) final String next,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);

		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		final JSONObject dataJson = insightsService
				.getInsightsValuesList(requestParams);

		DropdownFormatter.format(dataJson, "insights");
		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/count")
	public void count(final HttpServletRequest request,
			final HttpServletResponse response) throws JSONException,
			BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		final JSONObject dataJson = insightsService
				.getInsightsCount(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/DeleteInsight")
	public void deleteInsight(

			@RequestParam(value = "insightids", required = true) final String[] insightIds,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		final JSONObject dataJson = insightsService
				.deleteInsights(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/ClientInfo")
	public void getClientInfo(final HttpServletRequest request,
			final HttpServletResponse response) throws JSONException,
			BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		final JSONObject dataJson = insightsService
				.getClientInfo(requestParams);

		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/Score")
	public void scoreAnInsight(
			@RequestParam(value = "insightid", required = true) final String insightId,
			@RequestParam(value = "modelsource", required = true) final String modelSource,
			@RequestParam(value = "modelid", required = true) final String modelId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);

		final JSONObject dataJson = insightsService
				.scoreAnInsight(requestParams);

		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/getRecentInsights")
	public void getRecentInsights(
			@RequestParam(value = "count", required = true) final int count,
			@RequestParam(value = "includefailedinsights", required = false) final boolean includeFailedInsights,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);
		final JSONObject dataJson = insightsService
				.getRecentInsights(requestParams);
		TableFormatter.format(dataJson, "insights");
		WebHelper.sendJsonResponse(dataJson, response);
	}

}
