package com.acxiom.insightlab.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;

@Controller
@RequestMapping("/admin")
public class AdminController extends GlobalExceptionHandler {
	// TODO: create MemcacheService
	// TODO: create Performance service

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdminController.class);
	private static final String CATALINA_BASE = System
			.getProperty("catalina.base");

	@RequestMapping(value = "/log/{logType}")
	public void getLogs(
			@PathVariable("logType") final String logType,
			@RequestParam(value = "download", required = false) final String download,
			final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {

		// TODO: replace by LogService
		writeLogFileToResponse(logType, download, response);
	}

	@RequestMapping(value = "/")
	public String index() {
		return "admin/index";
	}

	@RequestMapping(value = "/help")
	public String help() {
		return "admin/help";
	}
	
	@RequestMapping("/xss/test")
	public void xss(final String script, HttpServletRequest request,
			HttpServletResponse response) throws JSONException,
			IOException, ServletException {
		JSONObject scriptJson = new JSONObject();
		scriptJson.put("script", script);
		WebHelper.sendJsonResponse(scriptJson, response);
	}


	private static Map<String, String> fileMap = new HashMap<String, String>();

	static {
		fileMap.put("connection", "insightlab-urls-debug.html");
		fileMap.put("navigation", "insightlab-global-navigation-log.html");
	}

	/**
	 * Full path to folder with html-file to which logging system writes logs.
	 * NOTE: Value of this constant has to be synchronized with logging
	 * configuration.
	 */
	private static final String LOGS_FILE_PATH = CATALINA_BASE + "/logs/";

	private void writeLogFileToResponse(final String logType,
			final String download, final HttpServletResponse response)
			throws IOException {
		final String fileName = fileMap.get(logType);
		if (fileName != null) {
			if (download != null) {
				response.setHeader("Content-Disposition",
						"attachment; filename=" + fileName);
			}
			final InputStream fileInputStream = new BufferedInputStream(
					new FileInputStream(LOGS_FILE_PATH + fileName));
			final PrintWriter responseWriter = response.getWriter();
			try {

				IOUtils.copy(fileInputStream, responseWriter,
						Charsets.UTF_8.toString());

				responseWriter.flush();
			} finally {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (responseWriter != null) {
					responseWriter.close();
				}
			}
		} else {
			LOGGER.error("log file not found.");
		}
	}
}
