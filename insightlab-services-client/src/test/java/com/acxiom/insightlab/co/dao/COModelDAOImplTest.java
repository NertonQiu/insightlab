package com.acxiom.insightlab.co.dao;

import static com.acxiom.insightlab.test.Fakes.DATE;
import static com.acxiom.insightlab.test.Fakes.DAY;
import static com.acxiom.insightlab.test.Fakes.HOURS;
import static com.acxiom.insightlab.test.Fakes.MINUTES;
import static com.acxiom.insightlab.test.Fakes.MONTH;
import static com.acxiom.insightlab.test.Fakes.SECONDS;
import static com.acxiom.insightlab.test.Fakes.TENANT_ID;
import static com.acxiom.insightlab.test.Fakes.TIME;
import static com.acxiom.insightlab.test.Fakes.TIMEZONE_OFFSET;
import static com.acxiom.insightlab.test.Fakes.YEAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.easymock.EasyMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.co.dao.COModelDAO;
import com.acxiom.insightlab.co.dao.COModelDAOImpl;
import com.acxiom.insightlab.co.model.COModel;
import com.acxiom.insightlab.service.SecurityUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityUtils.class)
@PowerMockIgnore("org.apache.http.conn.ssl.*")
public class COModelDAOImplTest {

	private COModelDAO coModelDAO;
	private RestTemplate restTemplateMock;
	

	@Before
	public void setUp() throws Exception {
		PowerMock.mockStatic(SecurityUtils.class);
		coModelDAO = new COModelDAOImpl();
		restTemplateMock = EasyMock.createMock(RestTemplate.class);

		Whitebox.setInternalState(coModelDAO, restTemplateMock);

		Field endpoint = COModelDAOImpl.class
				.getDeclaredField("coEndpoint");
		endpoint.setAccessible(true);
		endpoint.set(coModelDAO, "http://example.com");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetModelList() throws Exception {
		JSONArray results = new JSONArray();

		JSONObject createdDate = new JSONObject();
		createdDate.put("date", DATE);
		createdDate.put("day", DAY);
		createdDate.put("hours", HOURS);
		createdDate.put("minutes", MINUTES);
		createdDate.put("month", MONTH);
		createdDate.put("seconds", SECONDS);
		createdDate.put("time", TIME);
		createdDate.put("timezoneOffset", TIMEZONE_OFFSET);
		createdDate.put("year", YEAR);

		JSONObject goodModel = new JSONObject();
		goodModel.put("description",
				"COModel Dmytro_v1_9_34:ProOffer_071501-Phone_1008");
		goodModel.put("equation", "8 - 5 + 12 * 56");
		goodModel.put("id", "7");
		goodModel.put("userID", "");
		goodModel.put("clientID", "7fbf1573-6e61-45b6-9073-bab7b6b44443");
		goodModel.put("createdDate", createdDate);

		results.put(goodModel);

		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(String.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class))).andReturn(
				new ResponseEntity<String>(results.toString(), HttpStatus.OK));
		EasyMock.replay(restTemplateMock);

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("");
		EasyMock.expect(SecurityUtils.getAuthCookieDetails()).andReturn(
				new Cookie("DPPAUTH", "test value"));
		EasyMock.expect(SecurityUtils.getAuthCookieDetails()).andReturn(
				new Cookie("DPPAUTH", "test value"));

		PowerMock.replayAll();
		List<COModel> modelList = coModelDAO.getModelList();
		assertNotNull(modelList);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetModel() throws JSONException, BaseDataApiException {
		JSONArray results = new JSONArray();

		JSONObject createdDate = new JSONObject();
		createdDate.put("date", DATE);
		createdDate.put("day", DAY);
		createdDate.put("hours", HOURS);
		createdDate.put("minutes", MINUTES);
		createdDate.put("month", MONTH);
		createdDate.put("seconds", SECONDS);
		createdDate.put("time", TIME);
		createdDate.put("timezoneOffset", TIMEZONE_OFFSET);
		createdDate.put("year", YEAR);

		JSONObject goodModel = new JSONObject();
		goodModel.put("description",
				"COModel Dmytro_v1_9_34:ProOffer_071501-Phone_1008");
		goodModel.put("equation", "8 - 5 + 12 * 56");
		goodModel.put("id", "7");
		goodModel.put("userID", "");
		goodModel.put("clientID", "7fbf1573-6e61-45b6-9073-bab7b6b44443");
		goodModel.put("createdDate", createdDate);

		results.put(goodModel);

		EasyMock.expect(
				restTemplateMock.exchange(EasyMock.isA(String.class),
						EasyMock.isA(HttpMethod.class),
						EasyMock.isA(HttpEntity.class),
						EasyMock.isA(Class.class))).andReturn(
				new ResponseEntity<String>(results.toString(), HttpStatus.OK));
		EasyMock.replay(restTemplateMock);

		EasyMock.expect(SecurityUtils.getCompanyId()).andReturn(TENANT_ID);
		EasyMock.expect(SecurityUtils.getUserName()).andReturn("");
		EasyMock.expect(SecurityUtils.getAuthCookieDetails()).andReturn(
				new Cookie("DPPAUTH", "test value"));
		EasyMock.expect(SecurityUtils.getAuthCookieDetails()).andReturn(
				new Cookie("DPPAUTH", "test value"));

		PowerMock.replayAll();
		JSONObject model = coModelDAO.getModel(
				"fake model id");
		assertNotNull(model);
	}

	@Test
	public void testPrivateUpdateModelToModelLibraryFormat() throws Exception {
		JSONObject json = new JSONObject();
		JSONArray results = new JSONArray();

		JSONObject createdDate = new JSONObject();
		createdDate.put("date", DATE);
		createdDate.put("day", DAY);
		createdDate.put("hours", HOURS);
		createdDate.put("minutes", MINUTES);
		createdDate.put("month", MONTH);
		createdDate.put("seconds", SECONDS);
		createdDate.put("time", TIME);
		createdDate.put("timezoneOffset", TIMEZONE_OFFSET);
		createdDate.put("year", YEAR);

		JSONObject goodModel = new JSONObject();
		goodModel.put("description",
				"COModel Dmytro_v1_9_34:ProOffer_071501-Phone_1008");
		goodModel.put("equation", "8 - 5 + 12 * 56");
		goodModel.put("id", "7");
		goodModel.put("userID", "");
		goodModel.put("clientID", "7fbf1573-6e61-45b6-9073-bab7b6b44443");
		goodModel.put("createdDate", createdDate);

		JSONObject badModel = new JSONObject();
		
		badModel.put("createdDate", "zxcx");

		JSONObject emptyModel = new JSONObject();

		results.put(goodModel);

		List<COModel> list = new ArrayList<COModel>();

		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		map.put("model", COModel.class);
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
				.fromObject(results.toString());
		for (Iterator<?> iter = jsonArray.iterator(); iter.hasNext();) {
			net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject) iter
					.next();
			list.add((COModel) net.sf.json.JSONObject.toBean(jsonObject,
					COModel.class));
		}

		results.put(badModel);
		results.put(emptyModel);
		json.put("results", results);

		Whitebox.invokeMethod(coModelDAO, "updateModelToModelLibraryFormat",
				goodModel);
		// assertEquals?

		Whitebox.invokeMethod(coModelDAO, "updateModelToModelLibraryFormat",
				emptyModel);
		assertEquals("{}", emptyModel.toString());

		assertEquals("{\"createdDate\":\"zxcx\"}", badModel.toString());
		Whitebox.invokeMethod(coModelDAO, "updateModelToModelLibraryFormat",
				badModel);
		assertEquals("{\"createdDate\":\"zxcx\"}", badModel.toString());
	}

}
