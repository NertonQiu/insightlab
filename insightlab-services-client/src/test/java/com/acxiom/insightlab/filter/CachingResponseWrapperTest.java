package com.acxiom.insightlab.filter;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

public class CachingResponseWrapperTest {

	private CachingResponseWrapper responseWrapper;

	@Before
	public void setUp() throws Exception {
		responseWrapper = new CachingResponseWrapper(
				new MockHttpServletResponse());
	}

	@Test
	public void testGetWriter() throws IOException {
		PrintWriter writter = responseWrapper.getWriter();
		assertNotNull(writter);
	}

	@Test
	public void testGetOutputStream() throws IOException {
		OutputStream stream = responseWrapper.getOutputStream();
		assertNotNull(stream);
	}

	@Test
	public void testGetSetResponseWriter() {
		PrintWriter mockWritter = EasyMock.createMock(PrintWriter.class);
		EasyMock.replay(mockWritter);
		responseWrapper.setResponseWriter(mockWritter);
		assertNotNull(responseWrapper.getResponseWriter());
	}

	@Test
	public void testGetSetOriginalResponse() {
		responseWrapper.setOriginalResponse(new MockHttpServletResponse());
		assertNotNull(responseWrapper.getOriginalResponse());
	}

}
