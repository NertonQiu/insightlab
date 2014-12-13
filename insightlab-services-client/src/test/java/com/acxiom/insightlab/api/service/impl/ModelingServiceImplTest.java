package com.acxiom.insightlab.api.service.impl;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.dao.impl.ModelingDAOImpl;
import com.acxiom.insightlab.co.dao.COModelDAO;
import com.acxiom.insightlab.sso.service.UserServiceClient;

public class ModelingServiceImplTest {
	
	private ModelingDAOImpl mockDao;
	private COModelDAO mockCOModelDAO;
	private UserServiceClient mockUserService;

	private ModelingServiceImpl service;

	@Before
	public void setUp() throws Exception {
		service = new ModelingServiceImpl();
		mockDao = EasyMock.createStrictMock(ModelingDAOImpl.class);
		mockUserService = EasyMock.createStrictMock(UserServiceClient.class);
		mockCOModelDAO = EasyMock.createStrictMock(COModelDAO.class);
		Whitebox.setInternalState(service, mockDao);
		Whitebox.setInternalState(service, mockUserService);
		Whitebox.setInternalState(service, mockCOModelDAO);

	}

	@Test
	public void testFetchModelList() throws JSONException, BaseDataApiException {
		JSONObject expectedDAOResponse = new JSONObject(
				"{\"results\": [{\"isPrivate\": 0," + "\"type\": null,"
						+ "\"id\": \"9770038622290248609\","
						+ "\"description\": \"The new model X\","
						+ "\"userID\": \"kenich\","
						+ "\"insightID\": \"11013573005114598210\","
						+ "\"insightDescription\": \"The next test\","
						+ "\"clientID\": \"117\","
						+ "\"createdDate\": \"2013-04-24 09:54:58\","
						+ "\"status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}},{" + "\"isPrivate\": 0,"
						+ "\"type\": null,"
						+ "\"id\": \"10511723515316131150\","
						+ "\"description\": \"default model name\","
						+ "\"userID\": \"kenich\","
						+ "\"insightID\": \"18275485967599939947\","
						+ "\"insightDescription\": \"insight three\","
						+ "\"clientID\": \"117\","
						+ "\"createdDate\": \"2013-04-22 14:28:55\","
						+ "\"status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}}]}");
		

		JSONObject params = new JSONObject();

		expect(mockDao.fetchModelList(params)).andReturn(expectedDAOResponse);

		replay(mockDao);
		JSONObject actual = service.fetchModelList(params);
		JSONArray actualResults = actual.getJSONArray("results");
		Assert.assertNotEquals(actualResults.getJSONObject(0).getString("createdDate"), actualResults.getJSONObject(1).getString("createdDate"));
		verify(mockDao);
	}
	
	@Test
	public void testCreateModel() throws JSONException, BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"id\": \"13659499926987345629\","
						+ "\"type\": \"lookalike model\"}");

		JSONObject params = new JSONObject();
		params.put("insightid", new String[] { "18248540551966399988" });
		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "gpundi" });
		params.put("modelname", new String[] { "default model name" });

		expect(mockDao.createModel(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.createModel(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testUpdateModelStatus() throws JSONException,
			BaseDataApiException {

	}

	@Test
	public void testUpdateModelStatusForMultipleModels() throws JSONException,
			BaseDataApiException {

	}

	@Test
	public void testDeleteModels() throws JSONException, BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"results\": [{\"isPrivate\": 0," + "\"type\": null,"
						+ "\"ModelID\": \"9770038622290248609\","
						+ "\"description\": \"The new model X\","
						+ "\"UserID\": \"kenich\","
						+ "\"id\": \"11013573005114598210\","
						+ "\"InsightDescription\": \"The next test\","
						+ "\"ClientID\": \"117\","
						+ "\"CreatedDate\": \"2013-04-24 09:54:58\","
						+ "\"Status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}},{" + "\"isPrivate\": 0,"
						+ "\"type\": null,"
						+ "\"ModelID\": \"10511723515316131150\","
						+ "\"description\": \"default model name\","
						+ "\"UserID\": \"kenich\","
						+ "\"id\": \"18275485967599939947\","
						+ "\"InsightDescription\": \"insight three\","
						+ "\"ClientID\": \"117\","
						+ "\"CreatedDate\": \"2013-04-22 14:28:55\","
						+ "\"Status\": {" + "\"status\": \"MODEL_COMPLETED\","
						+ "\"percentage\": \"100\"}}]}");

		JSONObject params = new JSONObject();

		params.put("modelids", new String[] { "2564955026744203322",
				"6795582480709715965" });

		expect(mockDao.deleteModels(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.deleteModels(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testSearchModels() throws JSONException, BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"results\": [{\"description\": \"default model name\","
						+ "\"id\": \"11517404493877613814\"},{"
						+ "\"description\": \"default model name\","
						+ "\"id\": \"12523075299001768542\"}]}");

		JSONObject params = new JSONObject();

		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "gpundi" });
		params.put("source", new String[] { "MyModels" });
		params.put("filter", new String[] { "test" });

		expect(mockDao.searchModels(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.searchModels(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testSaveModelToLibrary() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"results\": \"Data Saved Successfully\"}");

		JSONObject params = new JSONObject();
		params.put("source", new String[] { "C" });
		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "gpundi" });
		params.put("modelid", new String[] { "10470085823556497107",
				"10470085823556497156" });

		expect(mockDao.saveModelToLibrary(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.saveModelToLibrary(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testDeleteModelFromLibrary() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject(
				"{\"ID\": \"13659499926987345629\","
						+ "\"Type\": \"lookalike model\"}");

		JSONObject params = new JSONObject();
		params.put("source", new String[] { "C" });
		params.put("clientid", new String[] { "1" });
		params.put("userid", new String[] { "gpundi" });
		params.put("modelid", new String[] { "10470085823556497107",
				"10470085823556497156" });

		expect(mockDao.deleteModelFromLibrary(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.deleteModelFromLibrary(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

	@Test
	public void testFetchModelReport() throws JSONException,
			BaseDataApiException {
		final JSONObject expected = new JSONObject("{\"isPrivate\": 0,"
				+ " \"type\": null," + " \"gain\": [{\"total\": 1205,"
				+ "\"rank\": 1," + "\"baseline\": 1,"
				+ "\"upperBound\": 0.82155464204702,"
				+ "\"lowerBound\": 0.35217162333712,"
				+ "\"targetPercent\": 0.4406639004," + "\"modelID\": \"1\","
				+ "\"ks\": 16.7," + "\"lift\": 396,"
				+ "\"cumulativeTotal\": 1205,"
				+ "\"cumulativeTotalPercent\": 0.0502229817,"
				+ "\"targetNumber\": 531," + "\"cumulativeTarget\": 531,"
				+ "\"cumulativeReference\": 674,"
				+ "\"cumulativeTargetPercent\": 0.1990254873,"
				+ "\"cumulativeReferencePercent\": 0.0316060961,"
				+ "\"liftCurve\": 3.96," + "\"reportSource\": \"T\","
				+ "\"rankGraph\": 0}],\"scorecard\": [{"
				+ "\"modelID\": \"1\","
				+ "\"description\": \"Other Merchandise/Services\","
				+ "\"averageRank\": 1," + "\"count\": 10,"
				+ "\"contribution\": 24.21," + "\"ibelement\": \"IB6726\"}],"
				+ "\"ibRank\": {" + "\"modelID\": \"1\","
				+ "\"rankTotal\": 100000," + "\"cumulativeRank\": [101,303,"
				+ " 606," + "1010," + "1515," + "2121," + " 2828," + "3636,"
				+ "4545," + "14555]," + "\"rank\": [" + "101," + "202,"
				+ "303," + "404," + " 505," + " 606," + "707," + " 808,"
				+ " 909," + " 10010]," + "\"percent\": [" + "  0.00101,"
				+ "0.00202," + "0.00303," + "0.00404," + " 0.00505,"
				+ " 0.00606," + " 0.00707," + " 0.00808," + "0.00909,"
				+ "0.1001]}," + "\"id\": \"1\","
				+ "\"description\": \"default model name\","
				+ "\"userID\": null," + "\"insightID\": null,"
				+ "\"insightDescription\": null," + "\"clientID\": null,"
				+ "\"createdDate\": null," + "\"status\": null}");

		JSONObject params = new JSONObject();

		params.put("modelid", new String[] { "9795364798852424624" });

		expect(mockDao.fetchModelReport(params)).andReturn(expected);

		replay(mockDao);
		JSONObject actual = service.fetchModelReport(params);
		assertEquals(expected, actual);
		verify(mockDao);
	}

}
