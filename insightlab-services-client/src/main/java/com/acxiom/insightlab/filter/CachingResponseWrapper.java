package com.acxiom.insightlab.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class CachingResponseWrapper extends HttpServletResponseWrapper {
	private HttpServletResponse originalResponse;
	private PrintWriter responseWriter;

	public CachingResponseWrapper(HttpServletResponse response) {
		super(response);
		originalResponse = response;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (responseWriter == null)
			responseWriter = new CachingResponseWriter(
					originalResponse.getWriter());
		return responseWriter;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return super.getOutputStream();
	}

	public HttpServletResponse getOriginalResponse() {
		return originalResponse;
	}

	public void setOriginalResponse(final HttpServletResponse originalResponse) {
		this.originalResponse = originalResponse;
	}

	public PrintWriter getResponseWriter() {
		return responseWriter;
	}

	public void setResponseWriter(final PrintWriter responseWriter) {
		this.responseWriter = responseWriter;
	}

}
