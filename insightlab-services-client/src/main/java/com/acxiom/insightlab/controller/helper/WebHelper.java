package com.acxiom.insightlab.controller.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.acxiom.insightlab.service.SecurityUtils;

/**
 * Helper class. Helps to write data to response, etc...
 * 
 * @author dmytro.plekhotkin
 * 
 */
public final class WebHelper {

	/**
	 * Private constructor. It's not able to instantiate helper class.
	 */
	private WebHelper() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Writes data to response. Sets response content type as application/json.
	 * 
	 * @param data
	 *            Some data that represents json object.
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 */
	public static void sendJsonResponse(final JSONObject data,
			final HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().print(data);
	}

	/**
	 * Writes data to response. Sets response content type as application/json
	 * and standart no-cache headers.
	 * 
	 * @param data
	 *            Some data that represents json object.
	 * @param response
	 *            HttpServletResponse
	 * @throws IOException
	 *             Input/Output exception
	 */
	public static void sendJsonResponseNoCache(final JSONObject data,
			final HttpServletResponse response) throws IOException {
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		sendJsonResponse(data, response);
	}

	public static void sendStringResponse(final String responseData,
			final HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().print(responseData);

	}

	public static void addTenantId(final JSONObject params)
			throws JSONException {
		params.put("tenantid", new String[] { SecurityUtils.getCompanyId()
				.toString() });
	}

	public static void addUserName(final JSONObject params)
			throws JSONException {
		params.put("username", new String[] { SecurityUtils.getUserName() });
	}

	public static void addUserId(final JSONObject params) throws JSONException {
		params.put("userid", new String[] { SecurityUtils.getUserName() });
	}

	public static String addTenantId(String requestBody) throws JSONException {
		JSONObject json = new JSONObject(requestBody);
		json.put("tenantID", SecurityUtils.getCompanyId());
		return json.toString();
	}

	public static String addUserName(String requestBody) throws JSONException {
		JSONObject json = new JSONObject(requestBody);
		json.put("username", SecurityUtils.getUserName());
		return json.toString();
	}

}
