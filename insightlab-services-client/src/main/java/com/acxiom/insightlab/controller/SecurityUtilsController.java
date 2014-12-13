package com.acxiom.insightlab.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.service.SecurityUtilsJsonAdapter;

/**
 * Sample web service that writes to response different security information in
 * json format. Extends GlobalExceptionHandler that handles all thrown errors.
 * Warning: some function designed for testing purposes only.
 * 
 * @author dmytro.plekhotkin
 * 
 */
@Controller
@RequestMapping("/authtoken")
public class SecurityUtilsController extends GlobalExceptionHandler {

	/**
	 * Writes user name to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/user/details/username", method = RequestMethod.GET)
	public void getUserName(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONObject userName = SecurityUtilsJsonAdapter.getUserNameJson();
		WebHelper.sendJsonResponseNoCache(userName, response);
	}

	/**
	 * Writes user name to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/company/details/companyid", method = RequestMethod.GET)
	public void getCompanyId(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONObject userName = SecurityUtilsJsonAdapter.getCompanyIdJson();
		WebHelper.sendJsonResponseNoCache(userName, response);
	}

	/**
	 * Writes user details to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/user/details", method = RequestMethod.GET)
	public void getUserDetails(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONObject userDetails = SecurityUtilsJsonAdapter
				.getUserDetailsJson();
		WebHelper.sendJsonResponseNoCache(userDetails, response);
	}

	/**
	 * Writes global profile data to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/user/details/globalprofile", method = RequestMethod.GET)
	public void getGlobalProfile(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONObject globalProfile = SecurityUtilsJsonAdapter
				.getGlobalProfileJson();
		WebHelper.sendJsonResponseNoCache(globalProfile, response);
	}

	/**
	 * Writes company information to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/company/info", method = RequestMethod.GET)
	public void getCompanyInfo(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONObject companyInfo = SecurityUtilsJsonAdapter
				.getCompanyInfoJson();
		WebHelper.sendJsonResponseNoCache(companyInfo, response);
	}

	/**
	 * Writes user roles to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/user/details/roles", method = RequestMethod.GET)
	public void getUserRoles(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONArray userRolesArray = SecurityUtilsJsonAdapter
				.getUserRolesJson();
		final JSONObject userRolesObject = new JSONObject();

		userRolesObject.put("user_roles", userRolesArray);

		WebHelper.sendJsonResponseNoCache(userRolesObject, response);
	}

	/**
	 * Writes application roles to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/application/roles", method = RequestMethod.GET)
	public void getApplicationRoles(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONArray appRolesArray = SecurityUtilsJsonAdapter
				.getApplicationRolesJson();
		final JSONObject appRolesObject = new JSONObject();

		appRolesObject.put("application_roles", appRolesArray);

		WebHelper.sendJsonResponseNoCache(appRolesObject, response);
	}

	/**
	 * Writes authentication cookie details to response.
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 * @throws JSONException
	 */
	@RequestMapping(value = "/cookie", method = RequestMethod.GET)
	public void getAuthCookieDetails(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONObject authCookieDetails = SecurityUtilsJsonAdapter
				.getAuthCookieDetailsJson();
		WebHelper.sendJsonResponseNoCache(authCookieDetails, response);
	}

	@RequestMapping(value = "/authenticated", method = RequestMethod.GET)
	public void isAuthenticated(final HttpServletResponse response)
			throws IOException, JSONException {

		final JSONObject isAuthenticated = SecurityUtilsJsonAdapter
				.isAuthenticated();
		WebHelper.sendJsonResponseNoCache(isAuthenticated, response);
	}
}
