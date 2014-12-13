package com.acxiom.insightlab.api;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;

import org.easymock.EasyMock;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("org.apache.http.conn.ssl.*")
public class ApiClientJsonImplTest {

	private ApiClientJson apiClient;
	private RestTemplate restTemplateMock;

	@Before
	public void setUp() throws Exception {
		apiClient = new ApiClientJsonImpl();
		restTemplateMock = EasyMock.createMock(RestTemplate.class);

		Whitebox.setInternalState(apiClient, restTemplateMock);

		Field endpoint = ApiClientJsonImpl.class.getDeclaredField("endPoint");
		endpoint.setAccessible(true);
		endpoint.set(apiClient, "http://example.com");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGet() throws BaseDataApiException {
		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(URI.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class))).andReturn(
				new ResponseEntity<String>("{}", HttpStatus.OK));
		EasyMock.replay(restTemplateMock);
		JSONObject response = apiClient.get("/test/path", new JSONObject());
		assertNotNull(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPost() throws BaseDataApiException {
		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(URI.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class))).andReturn(
				new ResponseEntity<String>("{}", HttpStatus.OK));
		EasyMock.replay(restTemplateMock);
		JSONObject response = apiClient.post("/test/path", new JSONObject());
		assertNotNull(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPut() throws BaseDataApiException {
		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(URI.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class))).andReturn(
				new ResponseEntity<String>("{}", HttpStatus.OK));
		EasyMock.replay(restTemplateMock);
		JSONObject response = apiClient.put("/test/path", new JSONObject());
		assertNotNull(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDelete() throws BaseDataApiException {
		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(String.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class))).andReturn(
				new ResponseEntity<String>("{}", HttpStatus.OK));
		EasyMock.replay(restTemplateMock);
		JSONObject response = apiClient.delete("/test/path", new JSONObject());
		assertNotNull(response);
	}


}
