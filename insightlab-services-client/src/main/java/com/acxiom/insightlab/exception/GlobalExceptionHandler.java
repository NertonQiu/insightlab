package com.acxiom.insightlab.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.InsightsServiceURIs;

/**
 * Base class for exception handling.
 * 
 * @author dmytro.plekhotkin
 * 
 */
public class GlobalExceptionHandler {

	/**
	 * Slf4j logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);

	@Value("${app.errorhandling.debug:false}")
	private Boolean isDebug;

	private static final String USER_MESSAGE = "没有记录返回.";

	/**
	 * Creates formated ErrorMessage from caught IOException and writes it to
	 * response.
	 * 
	 * @param ioException
	 *            IOException
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	@ExceptionHandler(IOException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public final void handleIOException(final IOException ioException,
			final HttpServletRequest request, final HttpServletResponse response) {
		final ErrorMessage message = new ErrorMessage(USER_MESSAGE,
				ioException.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		printExceptionToResponse(message, response);
	}

	/**
	 * Creates formated ErrorMessage from caught JSONException and writes it to
	 * response.
	 * 
	 * @param jsonException
	 *            JSONException
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 */
	@ExceptionHandler(JSONException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public final void handleJSONException(final JSONException jsonException,
			final HttpServletRequest request, final HttpServletResponse response) {
		final ErrorMessage message = new ErrorMessage(USER_MESSAGE,
				jsonException.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		printExceptionToResponse(message, response);
	}

	@ExceptionHandler(BaseDataApiException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public final void handleBaseDataApiException(
			final BaseDataApiException exception,
			final HttpServletRequest request, final HttpServletResponse response) {
		final ErrorMessage message = new ErrorMessage(USER_MESSAGE,
				exception.getMessage(), exception.getCode());
		printSpecialExceptionToResponse(message, request, response);
	}
	
	
	@ExceptionHandler(HttpStatusCodeException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public final void handleException(final HttpStatusCodeException exception,
			final HttpServletRequest request, final HttpServletResponse response) {
		final ErrorMessage message = new ErrorMessage(USER_MESSAGE,
				exception.getResponseBodyAsString(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		printExceptionToResponse(message, response);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	public final void handleException(final Exception exception,
			final HttpServletRequest request, final HttpServletResponse response) {
		final ErrorMessage message = new ErrorMessage(USER_MESSAGE,
				exception.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		printExceptionToResponse(message, response);
	}

	/**
	 * Writes error message to response.
	 * 
	 * @param message
	 *            Error message to write to response
	 * @param response
	 *            HttpServletResponse
	 */
	private void printExceptionToResponse(final ErrorMessage message,
			final HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			final PrintWriter out = response.getWriter();

			out.print(message.buildMessage(isDebug));

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private void printSpecialExceptionToResponse(final ErrorMessage message,
			final HttpServletRequest request, final HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try {
			final PrintWriter out = response.getWriter();
			if (message.getCode() == HttpStatus.NOT_FOUND.value()
					&& !skip(request)) {
				out.print(makeEmptyRowsObject());
			} else {
				out.print(message.buildMessage(isDebug));
			}

		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private boolean skip(final HttpServletRequest request) {

		LOGGER.info(request.getPathInfo());
		return skipSpecialResponseForPath.contains(request.getPathInfo());
	}

	private static Set<String> skipSpecialResponseForPath = new HashSet<String>();
	static {
		skipSpecialResponseForPath.add(InsightsServiceURIs.DELETE_INSIGHTS);
	}

	private static JSONObject makeEmptyRowsObject() {
		JSONObject emptyRowsObject = new JSONObject();

		try {
			emptyRowsObject.put("rows", new JSONArray());
		} catch (JSONException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return emptyRowsObject;
	}

}
