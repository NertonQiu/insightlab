package com.acxiom.insightlab.api.model;

import static com.acxiom.insightlab.test.Fakes.CLIENT_ID;
import static com.acxiom.insightlab.test.Fakes.CREATED_DATE;
import static com.acxiom.insightlab.test.Fakes.DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.ID;
import static com.acxiom.insightlab.test.Fakes.INSIGHT_DESCRIPTION;
import static com.acxiom.insightlab.test.Fakes.INSIGHT_ID;
import static com.acxiom.insightlab.test.Fakes.IS_PRIVATE;
import static com.acxiom.insightlab.test.Fakes.SOURCE;
import static com.acxiom.insightlab.test.Fakes.STATUS;
import static com.acxiom.insightlab.test.Fakes.TYPE;
import static com.acxiom.insightlab.test.Fakes.USER_ID;
import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.Test;

public class ViewModelAdapterTest {

	@Test
	public void testViewModelAdapter() {
		Model model = new Model();
		model.setTenantID(CLIENT_ID);
		model.setCreatedDate(CREATED_DATE);
		model.setDescription(DESCRIPTION);
		model.setId(ID);
		model.setInsightDescription(INSIGHT_DESCRIPTION);
		model.setInsightID(INSIGHT_ID);
		model.setIsPrivate(IS_PRIVATE);
		model.setSource(SOURCE);
		model.setStatus(STATUS);
		model.setType(TYPE);
		model.setUserID(USER_ID);

		ViewModelAdapter viewModelAdapter = new ViewModelAdapter(model);

		assertEquals(CLIENT_ID, viewModelAdapter.getTenantID());

		String fakeDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(CREATED_DATE);
		assertEquals(fakeDateString, viewModelAdapter.getCreatedDate());
		assertEquals(DESCRIPTION, viewModelAdapter.getDescription());
		assertEquals(ID, viewModelAdapter.getId());
		assertEquals(INSIGHT_DESCRIPTION,
				viewModelAdapter.getInsightDescription());
		assertEquals(INSIGHT_ID, viewModelAdapter.getInsightID());
		assertEquals(IS_PRIVATE, viewModelAdapter.getIsPrivate());
		assertEquals(STATUS, viewModelAdapter.getStatus());
		assertEquals(SOURCE, viewModelAdapter.getSource());
		assertEquals(TYPE, viewModelAdapter.getType());
		assertEquals(USER_ID, viewModelAdapter.getUserID());

	}

}
