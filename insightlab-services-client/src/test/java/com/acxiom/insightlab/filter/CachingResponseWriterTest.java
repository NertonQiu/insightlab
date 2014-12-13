package com.acxiom.insightlab.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

public class CachingResponseWriterTest {

	private CachingResponseWriter responseWriter; 
	@Before
	public void setUp() throws Exception {
		responseWriter = new CachingResponseWriter(new MockHttpServletResponse().getWriter());
	}

	@Test
	public void testCachingResponseWriter() throws UnsupportedEncodingException {
		CachingResponseWriter writer = new CachingResponseWriter(new MockHttpServletResponse().getWriter());
		assertNotNull(writer);
	}

	@Test
	public void testWriteString() {
		responseWriter.write("fake string");
		assertEquals("fake string", responseWriter.getCollectedResponse());
	}
	
	@Test
	public void testGetSetResponseBody() {
		responseWriter.setResponseBody(new StringBuffer("fake string"));
		assertEquals("fake string", responseWriter.getResponseBody().toString());
	}

}
