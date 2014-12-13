package com.acxiom.insightlab.exception;

import java.io.IOException;
import java.lang.reflect.Field;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.acxiom.insightlab.api.BaseDataApiException;

public class GlobalExceptionHandlerTest {
	private GlobalExceptionHandler exceptionHadler;
	
	@Before
	public void setUp() throws Exception {
		exceptionHadler = new GlobalExceptionHandler();
		
		Field isDebug = GlobalExceptionHandler.class.getDeclaredField("isDebug");
		isDebug.setAccessible(true);
		isDebug.set(exceptionHadler, new Boolean(true));
	}

	@Test
	public void testHandleIOException() {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockResponse.setWriterAccessAllowed(true);
		exceptionHadler.handleIOException(new IOException("test error message"), mockRequest, mockResponse);
		exceptionHadler.handleIOException(new IOException(), mockRequest, mockResponse);
	}

	@Test
	public void testHandleJSONException() {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockResponse.setWriterAccessAllowed(true);
		exceptionHadler.handleJSONException(new JSONException("test error message"), mockRequest, mockResponse);
	}

	@Test
	public void testHandleBaseDataApiException() {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockResponse.setWriterAccessAllowed(true);
		exceptionHadler.handleBaseDataApiException(new BaseDataApiException("test error message", 404), mockRequest, mockResponse);
		exceptionHadler.handleBaseDataApiException(new BaseDataApiException(), mockRequest, mockResponse);
	}

	@Test
	public void testHandleException() {
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		mockResponse.setWriterAccessAllowed(true);
		exceptionHadler.handleException(new Exception(), mockRequest, mockResponse);
		exceptionHadler.handleException(new Exception("test error message"), mockRequest, mockResponse);
	}

}
