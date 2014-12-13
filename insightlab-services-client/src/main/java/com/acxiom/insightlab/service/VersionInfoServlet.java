package com.acxiom.insightlab.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class VersionInfoServlet.
 */
public final class VersionInfoServlet extends HttpServlet {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 3426929162128415169L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		final InputStream stream = getServletContext().getResourceAsStream(
				"/META-INF/MANIFEST.MF");
		try {
			final JSONObject versionData = getVersionJson(stream);
			sendResponse(
					versionData.toString().getBytes(Charsets.UTF_8.toString()),
					response);
		} catch (JSONException e) {
			LOGGER.debug(e.getMessage());
		}

	}

	/**
	 * Returns data about build version and svn revision, etc, gotten from
	 * MANIFEST file.
	 * 
	 * @param stream
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private JSONObject getVersionJson(final InputStream stream)
			throws IOException, JSONException {

		final JSONObject versionJson = new JSONObject();

		if (stream == null) {
			versionJson
					.put("error",
							"Required parameters are in \"META-INF/MANIFEST.MF\" file."
									+ " Application could not find \"META-INF/MANIFEST.MF\"."
									+ " This functionality does not supported for Jetty app server");
		} else {
			final java.util.jar.Manifest manifest = new java.util.jar.Manifest();
			manifest.read(stream);
			final java.util.jar.Attributes attributes = manifest
					.getMainAttributes();

			for (Object ob : attributes.keySet()) {
				versionJson.put(ob.toString(), attributes.get(ob));

			}
		}

		return versionJson;
	}

	/**
	 * Writes data to response and than closes response stream.
	 * 
	 * @param request
	 *            HttpServletRequest request
	 * @param response
	 *            HttpServletResponse response
	 * @throws IOException
	 *             Exception that throws if sending response fails.
	 */
	private void sendResponse(final byte[] data,
			final HttpServletResponse response) throws IOException {

		setResponseHeaders(data, response);

		final PrintWriter responseWriter = response.getWriter();
		try {

			IOUtils.write(data, responseWriter, Charsets.UTF_8.toString());

			responseWriter.flush();
		} finally {

			if (responseWriter != null) {
				responseWriter.close();
			}
		}
	}

	/**
	 * Sets response headers.
	 * 
	 * @param data
	 * 
	 * @param request
	 *            HttpServletRequest request
	 * @param response
	 *            HttpServletResponse response
	 */
	private void setResponseHeaders(final byte[] data,
			final HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		response.setContentLength(data.length);
		response.setCharacterEncoding(Charsets.UTF_8.toString());
	}

	/**
	 * Slf4j logger.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(VersionInfoServlet.class);
}
