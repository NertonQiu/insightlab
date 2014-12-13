package com.acxiom.insightlab.filter;

import java.io.PrintWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CachingResponseWriter extends PrintWriter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(CachingResponseWriter.class);
	private StringBuffer responseBody = new StringBuffer();

	public CachingResponseWriter(final Writer out) {

		super(out);
	}

	@Override
	public void write(final String s) {

		responseBody.append(s);
		LOGGER.info(responseBody.toString());
		super.write(s);
		LOGGER.info(responseBody.toString());
	}

	public String getCollectedResponse() {

		return responseBody.toString();
	}

	public StringBuffer getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(final StringBuffer responseBody) {
		this.responseBody = responseBody;
	}
}