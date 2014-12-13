package com.acxiom.insightlab.controller.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportExportingHelper {
	public static final String MEDIA_TYPE_EXCEL = "application/vnd.ms-excel";
	public static final String MEDIA_TYPE_PDF = "application/pdf";
	public static final String EXTENSION_TYPE_EXCEL = "xls";
	public static final String EXTENSION_TYPE_PDF = "pdf";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReportExportingHelper.class);

	private ReportExportingHelper() {
		throw new UnsupportedOperationException();
	}

	public static void sendReport(final ByteArrayOutputStream baos,
			String reportType, String reportName,
			final HttpServletResponse response) throws IOException {

		response.setHeader("Content-Disposition", "inline; filename="
				+ reportName);

		// Set content type
		if (reportType.equalsIgnoreCase(EXTENSION_TYPE_PDF)) {
			response.setContentType(MEDIA_TYPE_PDF);

		} else if (reportType.equalsIgnoreCase(EXTENSION_TYPE_EXCEL)) {
			response.setContentType(MEDIA_TYPE_EXCEL);
		} else {
			throw new RuntimeException("No type set for type " + reportType);
		}

		response.setContentLength(baos.size());

		write(baos, response);
	}

	/**
	 * Writes the report to the output stream
	 */
	private static void write(ByteArrayOutputStream baos,
			HttpServletResponse response) {
		try {
			// Retrieve output stream
			ServletOutputStream outputStream = response.getOutputStream();
			// Write to output stream
			baos.writeTo(outputStream);
			// Flush the stream
			outputStream.flush();

		} catch (Exception e) {
			LOGGER.error("Unable to write report to the output stream");
			throw new RuntimeException(e);
		}
	}
}
