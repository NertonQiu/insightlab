package com.acxiom.insightlab.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.service.SecurityUtils;
import com.acxiom.insightlab.sso.service.UserServiceClientJsonAdapter;

@Controller
@RequestMapping("/ui")
public class UiController extends GlobalExceptionHandler {

	@Value("${app.ui.serviceURL.files}")
	private String fileCenterURL;

	@Value("${co.endpoint}")
	private String coEndpoint;

	@Value("${co.applicationName}")
	private String coAppName;

	private static final String IS_APP_USER = "isAppUser";
	private static final String APP_NAME_PARAM = "appName";

	@Autowired
	private UserServiceClientJsonAdapter userServiceJsonAdapter;

	@RequestMapping(value = "/properties")
	public void getProperties(final HttpServletRequest request,
			final HttpServletResponse response) throws JSONException,
			IOException {

		JSONObject properties = new JSONObject();
		properties.put("fileCenterURL",
				request.getRequestURI().replace("properties", "fileCenter"));
		properties.put("coEndpoint", coEndpoint);

		WebHelper.sendJsonResponse(properties, response);
	}

	@RequestMapping("/fileCenter")
	public void redirectToFileCenter(final HttpServletRequest request,
			final HttpServletResponse response) throws IOException {
		response.sendRedirect(fileCenterURL);
	}

	@RequestMapping(IS_APP_USER)
	public void isAppUser(
			@RequestParam(value = APP_NAME_PARAM, required = true) String appName,
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException, IOException {
		JSONObject isAppUser = userServiceJsonAdapter.isAppUser(appName);
		final String userName = SecurityUtils.getUserName();

		isAppUser.put("userName", userName);
		WebHelper.sendJsonResponse(isAppUser, response);
	}

	@RequestMapping("/isCOUser")
	public void isCOUser(HttpServletRequest request,
			HttpServletResponse response) throws JSONException,
			IOException, ServletException {

		RequestDispatcher dispatcher = request.getRequestDispatcher(IS_APP_USER
				+ "?" + APP_NAME_PARAM + "=" + coAppName);
		dispatcher.forward(request, response);
	}

	public String getFileCenterURL() {
		return fileCenterURL;
	}

	public void setFileCenterURL(final String fileCenterURL) {
		this.fileCenterURL = fileCenterURL;
	}

	public String getCoEndpoint() {
		return coEndpoint;
	}

	public void setCoEndpoint(final String coEndpoint) {
		this.coEndpoint = coEndpoint;
	}

}
