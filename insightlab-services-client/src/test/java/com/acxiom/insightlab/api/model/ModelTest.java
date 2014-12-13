package com.acxiom.insightlab.api.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.acxiom.insightlab.test.Fakes.DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.ID;
import static com.acxiom.insightlab.test.Fakes.SOURCE;
import static com.acxiom.insightlab.test.Fakes.USER_ID;

import static com.acxiom.insightlab.test.Fakes.NULL;

public class ModelTest {

	static {
		JSONArray results = new JSONArray();
		JSONObject testObject = new JSONObject();
		JSONObject testStatus = new JSONObject();

		try {

			testStatus.put("status", "MODEL_COMPLETED");
			testStatus.put("percentage", "100");
			testStatus.put("modelID", NULL);

			testObject.put("id", NULL);
			testObject.put("description", NULL);
			testObject.put("insightID", NULL);
			testObject.put("insightDescription", NULL);
			testObject.put("userID", NULL);
			testObject.put("clientID", NULL);
			testObject.put("source", NULL);
			testObject.put("createdDate", NULL);
			testObject.put("isPrivate", NULL);
			testObject.put("type", NULL);
			testObject.put("status", NULL);
			results.put(testObject);

			final String modelArrayString = results.toString();

			List<Model> modelList = new ArrayList<Model>();

			Map<String, Class<?>> map = new HashMap<String, Class<?>>();
			map.put("model", Model.class);
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray
					.fromObject(modelArrayString);
			for (Iterator<?> iter = jsonArray.iterator(); iter.hasNext();) {
				net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject) iter
						.next();
				modelList.add((Model) net.sf.json.JSONObject.toBean(jsonObject,
						Model.class));

			}

			Assert.assertEquals(1, modelList.size());

		} catch (JSONException e) {
			// Do nothing.It's a test.
		}
	}

	private Model model;

	@Before
	public void setUp() {
		model = new Model();
	}

	@Test
	public void testModel() {
		model = new Model();
	}

	@Test
	public void testGetSource() {

		model.setSource(SOURCE);
		assertEquals(SOURCE, model.getSource());
	}

	@Test
	public void testSetSource() {
		model.setSource(SOURCE);
		assertEquals(SOURCE, model.getSource());
	}

	@Test
	public void testGetUserID() {

		model.setUserID(USER_ID);
		assertEquals(USER_ID, model.getUserID());
	}

	@Test
	public void testSetUserID() {
		model.setUserID(USER_ID);
		assertEquals(USER_ID, model.getUserID());
	}

	@Test
	public void testGetDescription() {
		model.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, model.getDescription());
	}

	@Test
	public void testSetDescription() {

		model.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, model.getDescription());
	}

	@Test
	public void testGetId() {

		model.setId(ID);
		assertEquals(ID, model.getId());
	}

	@Test
	public void testSetId() {
		model.setId(ID);
		assertEquals(ID, model.getId());
	}

}
