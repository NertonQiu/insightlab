package com.acxiom.insightlab.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.formatter.SettingsFormatter;
import com.acxiom.insightlab.api.formatter.TableFormatter;
import com.acxiom.insightlab.api.service.impl.ModelingServiceImpl;
import com.acxiom.insightlab.controller.helper.ParamNameConstants;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.service.ServletUtils;

/**
 * 
 * Works with Modeling service and returns modeling service data to client.
 * 
 * @author dmytro.plekhotkin
 * @since 2013-03-19
 * 
 */
@Controller
@RequestMapping("/modelingService")
public class ModelingController extends GlobalExceptionHandler {

	@Autowired
	private ModelingServiceImpl modelingService;

	@RequestMapping(value = "/Model", method = RequestMethod.POST)
	public void createModel(
			@RequestParam(value = "insightid", required = true) String insightId,
			@RequestParam(value = "modelname", required = true, defaultValue = "default model name") String modelName,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		JSONObject dataJson = modelingService.createModel(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping(value = "/Model", method = RequestMethod.GET)
	public void getModelReport(
			@RequestParam(value = "modelid", required = true) String modelId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserName(requestParams);
		JSONObject dataJson = modelingService.fetchModelReport(requestParams);
		SettingsFormatter.addDateTimeSettings(dataJson);

		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/deleteModel")
	public void deleteModel(
			@RequestParam(value = "modelids", required = true) String[] modelIds,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);

		final JSONObject dataJson = modelingService.deleteModels(requestParams);

		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/getModelLibrary")
	public void getModelLibrary(
			@RequestParam(value = ParamNameConstants.RESPONSE_FORMAT_PARAM, required = false) String responseFormat,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);

		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		final JSONObject dataJson = modelingService
				.fetchModelLibraryData(requestParams);

		TableFormatter.format(dataJson, "results");
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/getModelList")
	public void getModelList(
			@RequestParam(value = "isprepping", required = false) final String isPrepping,
			@RequestParam(value = "columnname", required = false) final String columnName,
			@RequestParam(value = "orderby", required = false) final String orderBy,
			@RequestParam(value = "filter", required = false) final String filter,

			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserName(requestParams);
	    
		final JSONObject dataJson = modelingService
				.fetchModelList(requestParams);

		TableFormatter.format(dataJson, "models");
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/saveModelToLibrary")
	public void saveModelToLibrary(
			@RequestParam(value = "modelid", required = true) final String[] modelId,
			@RequestParam(value = "source", required = true) final String source,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);

		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		final JSONObject dataJson = modelingService
				.saveModelToLibrary(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/searchModels")
	public void searchModels(
			@RequestParam(value = "filter", required = true) final String filter,
			@RequestParam(value = "source", required = true) final String source,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);

		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		final JSONObject dataJson = modelingService.searchModels(requestParams);
		TableFormatter.format(dataJson, "results");
		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/setModelStatus")
	public void setModelStatus(
			@RequestParam(value = "modelids", required = true) final String modelIds,
			@RequestParam(value = "statusmsg", required = true) final String statusMsg,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);

		final JSONObject dataJson = modelingService
				.updateModelStatusForMultipleModels(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/deleteModelFromLibrary")
	public void deleteModelFromLibrary(
			@RequestParam(value = "modelid", required = true) final String[] modelId,
			@RequestParam(value = "source", required = true) final String source,

			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserId(requestParams);

		final JSONObject dataJson = modelingService
				.deleteModelFromLibrary(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/createModelCounts")
	public void createModelCounts(
			@RequestParam(value = "modelid", required = true) final String modelId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);
		WebHelper.addUserName(requestParams);

		final JSONObject dataJson = modelingService
				.createModelCounts(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/getModelCounts")
	public void getModelCounts(
			@RequestParam(value = "modelid", required = true) final String modelId,
			@RequestParam(value = "level", required = true) final String level,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addTenantId(requestParams);

		final JSONObject dataJson = modelingService
				.getModelCounts(requestParams);
		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping(value = "/updateSegment", method = RequestMethod.POST)
	public void updateSegment(@RequestBody String segment,
			final HttpServletResponse response) throws JSONException,
			BaseDataApiException, IOException {

		segment = WebHelper.addTenantId(segment);
		segment = WebHelper.addUserName(segment);

		final JSONObject dataJson = modelingService.updateSegment(segment);

		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping(value = "/createSegment", method = RequestMethod.POST)
	public void createSegment(@RequestBody String segment,
			final HttpServletResponse response) throws JSONException,
			BaseDataApiException, IOException {

		segment = WebHelper.addTenantId(segment);
		segment = WebHelper.addUserName(segment);

		final JSONObject dataJson = modelingService.createSegment(segment);

		WebHelper.sendJsonResponse(dataJson, response);

	}
}
