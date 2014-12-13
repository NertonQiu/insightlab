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
import com.acxiom.insightlab.api.formatter.DropdownFormatter;
import com.acxiom.insightlab.api.service.MasterDataService;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.service.ServletUtils;

/**
 * 
 * Works with MasterData service and returns masterData service data to client.
 * 
 * @author dmytro.plekhotkin
 * @since 2013-03-19
 * 
 */
@Controller
@RequestMapping("/masterDataService")
public class MasterDataController extends GlobalExceptionHandler {

	private static final String RESULTS = "results";
	
	@Autowired
	private MasterDataService masterDataService;

	@RequestMapping("/getSynDataResponsesByQuestion")
	public void getSynDataResponsesByQuestion(
			@RequestParam(value = "questionid", required = true) final String questionId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);

		JSONObject dataJson = masterDataService
				.getResponseForSpecificMRIQuestions(requestParams);

		DropdownFormatter.format(dataJson, RESULTS);

		WebHelper.sendJsonResponse(dataJson, response);

	}

	@RequestMapping("/getAllSynDataCategories")
	public void getAllSynDataCategories(

	final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);

		JSONObject dataJson = masterDataService
				.getAllSynDataCategories(requestParams);

		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/getSynDataSubcategoriesByCategory")
	public void getSynDataSubcategoriesByCategory(
			@RequestParam(value = "categoryid", required = true) final String mriCategoryId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);

		JSONObject dataJson = masterDataService
				.getSynDataSubcategoriesByCategory(requestParams);
		DropdownFormatter.format(dataJson, RESULTS);
		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/getPersonicxReferenceFiles")
	public void getPersonicxReferenceFiles(final HttpServletRequest request,
			final HttpServletResponse response) throws JSONException,
			BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);

		JSONObject dataJson = masterDataService
				.getPersonicxReferenceFiles(requestParams);
		DropdownFormatter.format(dataJson, RESULTS);
		WebHelper.sendJsonResponse(dataJson, response);
	}

	@RequestMapping("/getSynDataQuestionsBySubcategory")
	public void getSynDataQuestionsBySubcategory(
			@RequestParam(value = "subcategoryid", required = true) final String subcategoryId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		JSONObject requestParams = ServletUtils.getParams(request);

		JSONObject dataJson = masterDataService
				.getSynDataQuestionsBySubcategory(requestParams);
		DropdownFormatter.format(dataJson, RESULTS);
		WebHelper.sendJsonResponse(dataJson, response);
	}
}