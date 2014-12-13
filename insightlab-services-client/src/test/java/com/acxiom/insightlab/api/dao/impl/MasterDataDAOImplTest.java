package com.acxiom.insightlab.api.dao.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.acxiom.insightlab.api.ApiClientJsonImpl;
import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.constant.MasterDataServiceURIs;


public class MasterDataDAOImplTest {

	private MasterDataDAOImpl service;
	private ApiClientJsonImpl mockApiClient;
	
	@Before
	public void init() {
		service = new MasterDataDAOImpl();
		mockApiClient = EasyMock.createStrictMock(ApiClientJsonImpl.class);
		service.setApiClient(mockApiClient);
	}
	
	@Test
	public void testSetApiClient() {
		MasterDataDAOImpl masterDataService = new MasterDataDAOImpl();
		ApiClientJsonImpl apiClient = new ApiClientJsonImpl();
		masterDataService.setApiClient(apiClient);
    	assertEquals(apiClient, masterDataService.getApiClient());
	}

	@Test
	public void testGetPersonicxReferenceFiles() throws JSONException, BaseDataApiException {
		JSONObject results = new JSONObject();
		JSONArray resultArray = new JSONArray();
		
		resultArray.put(new JSONObject("{\"fileName\":\"US Personicx\",\"fileID\":\"-1\"}"));
		resultArray.put(new JSONObject("{\"fileName\":\"State Personicx\",\"fileID\":\"-2\"}"));
		resultArray.put(new JSONObject("{\"fileName\":\"DMA Personicx\",\"fileID\":\"-4\"}"));
		resultArray.put(new JSONObject("{\"fileName\":\"County Personicx\",\"fileID\":\"-3\"}"));
		resultArray.put(new JSONObject("{\"fileName\":\"ZIP Personicx\",\"fileID\":\"-5\"}"));
		results.put("results", resultArray);
		
		JSONObject params = new JSONObject();
		params.put("clientid", new String[] {"1"});
		expect(mockApiClient.get(MasterDataServiceURIs.GET_PERSONICX_REFERENCE_FILES, params)).andReturn(results);

		replay(mockApiClient);
		assertEquals(results, service.getPersonicxReferenceFiles(params));
		verify(mockApiClient);
	}


	@Test
	public void testGetAllSynDataCategories() throws JSONException, BaseDataApiException {
				JSONObject results = new JSONObject();
				JSONArray resultArray = new JSONArray();
				
				resultArray.put(new JSONObject("{\"id\":\"1\",\"categoryName\":\"Advertising\"}"));
				resultArray.put(new JSONObject("{\"id\":\"2\",\"categoryName\":\"Banking-Finance-Insurance\"}"));
				resultArray.put(new JSONObject("{\"id\":\"3\",\"categoryName\":\"Computer Related\"}"));
				resultArray.put(new JSONObject("{\"id\":\"4\",\"categoryName\":\"Consumer Products & Purchases\"}"));
				resultArray.put(new JSONObject("{\"id\":\"5\",\"categoryName\":\"Food & Beverages\"}"));
				resultArray.put(new JSONObject("{\"id\":\"6\",\"categoryName\":\"Healthcare\"}"));
				resultArray.put(new JSONObject("{\"id\":\"7\",\"categoryName\":\"Heavy Users\"}"));
				resultArray.put(new JSONObject("{\"id\":\"8\",\"categoryName\":\"Home-Appliances-Electronics\"}"));
				resultArray.put(new JSONObject("{\"id\":\"9\",\"categoryName\":\"Light Users\"}"));
				resultArray.put(new JSONObject("{\"id\":\"10\",\"categoryName\":\"Magazines\"}"));
				resultArray.put(new JSONObject("{\"id\":\"11\",\"categoryName\":\"Media\"}"));
				resultArray.put(new JSONObject("{\"id\":\"12\",\"categoryName\":\"Medium Users\"}"));
				resultArray.put(new JSONObject("{\"id\":\"13\",\"categoryName\":\"Miscellaneous\"}"));
				resultArray.put(new JSONObject("{\"id\":\"14\",\"categoryName\":\"Primary Users\"}"));
				resultArray.put(new JSONObject("{\"id\":\"15\",\"categoryName\":\"Recreation & Hobbies\"}"));
				resultArray.put(new JSONObject("{\"id\":\"16\",\"categoryName\":\"Shopping\"}"));
				resultArray.put(new JSONObject("{\"id\":\"17\",\"categoryName\":\"Travel & Auto\"}"));
				results.put("result", resultArray);
				
		JSONObject params = new JSONObject();
		params.put("clientid", new String[] {"1"});
		expect(mockApiClient.get(MasterDataServiceURIs.GET_ALL_SYN_DATA_CATEGORIES, params)).andReturn(results);
	
		replay(mockApiClient);
		assertEquals(results, service.getAllSynDataCategories(params));
		verify(mockApiClient);
	}

	@Test
	public void testGetSynDataQuestionsBySubcategory() throws JSONException, BaseDataApiException {
		
		JSONObject results = new JSONObject();
		JSONArray resultArray = new JSONArray();
		
		resultArray.put(new JSONObject("{\"id\":\"1\",\"subcategoryID\":\"1\",\"questionText\":\"Responsiveness To Ads Across Media Segments\"}"));
		results.put("result", resultArray);
		
		JSONObject params = new JSONObject();
		params.put("clientid", new String[] {"1"});
		params.put("subcategoryid", new String[] {"1"});
		expect(mockApiClient.get(MasterDataServiceURIs.GET_SYN_DATA_QUESTIONS_BY_SUBCATEGORY, params)).andReturn(results);

		replay(mockApiClient);
		assertEquals(results, service.getSynDataQuestionsBySubcategory(params));
		verify(mockApiClient);
	}

}
