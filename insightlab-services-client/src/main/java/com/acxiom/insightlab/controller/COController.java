package com.acxiom.insightlab.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.formatter.TableFormatter;
import com.acxiom.insightlab.co.service.COModelService;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;

@Controller
@RequestMapping("/co")
public class COController extends GlobalExceptionHandler {

	@Autowired
	private COModelService coModelService;

	@RequestMapping("/model/get/{modelId}")
	public void getModel(@PathVariable final String modelId,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {
		final JSONObject storageUsage = coModelService.getModel(modelId);
		WebHelper.sendJsonResponse(storageUsage, response);
	}

	@RequestMapping(value = "/models/get", method = RequestMethod.GET)
	public void getModelList(final HttpServletRequest request,
			final HttpServletResponse response) throws JSONException,
			BaseDataApiException, IOException {
		JSONObject dataJson = coModelService.getModelList();

		TableFormatter.format(dataJson, "results");
		WebHelper.sendJsonResponse(dataJson, response);
	}

}
