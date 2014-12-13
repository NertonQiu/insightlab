package com.acxiom.insightlab.api.dao.impl;

import static com.acxiom.insightlab.test.Fakes.RANDOM_UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.api.BaseDataApiException;

public class ClientDAOImplTest {

	private RestTemplate restTemplateMock;
	private ClientDAOImpl clientDAO;
	
	@Before
	public void setUp() throws Exception {
		clientDAO = new ClientDAOImpl();
		restTemplateMock = EasyMock.createStrictMock(RestTemplate.class);
		Whitebox.setInternalState(clientDAO, restTemplateMock);

	}

	@Test
	public void testGetStorageUsage() throws BaseDataApiException, JSONException {
		JSONObject results = new JSONObject();
		results.put("key", "value");
		
		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(String.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class))).andReturn(
				new ResponseEntity<String>(results.toString(), HttpStatus.OK));
		EasyMock.replay(restTemplateMock);
		
		
		JSONObject result = clientDAO.getStorageUsage(RANDOM_UUID.toString());
		assertNotNull(result);
		assertEquals("", "");
	}

}
