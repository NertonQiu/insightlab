package com.acxiom.insightlab.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PersonicxPortraitModelTest {

	@Test
	public final void testEqualsAndHashCode() {
		final PersonicxPortraitModel firstModel = constructStableModel();
		final PersonicxPortraitModel secondModel = constructStableModel();
		final PersonicxPortraitModel thirdModel = constructStableModel();

		// check full model
		assertEqualsAndHashCode(firstModel, secondModel, thirdModel);

		// check parts of the model
		assertEqualsAndHashCode(firstModel.getPortraitData().get(0),
				secondModel.getPortraitData().get(0), thirdModel
						.getPortraitData().get(0));
		assertEqualsAndHashCode(firstModel.getReference(),
				secondModel.getReference(), thirdModel.getReference());
		assertEqualsAndHashCode(firstModel.getTarget(),
				secondModel.getTarget(), thirdModel.getTarget());
	}

	private void assertEqualsAndHashCode(Object first, Object second,
			Object third) {
		// reflexive
		assertTrue(first.equals(first));
		assertTrue(first.hashCode() == first.hashCode());

		// symmetric
		assertEquals(first.equals(second), second.equals(first));
		assertTrue(first.hashCode() == second.hashCode());

		// transitive
		assertEquals(first, second);
		assertEquals(second, third);
		assertEquals(first, third);

		// consistent
		assertEquals(first.equals(second), first.equals(second));

		// not null
		assertFalse(first.equals(null));

		// different class
		assertFalse(first.equals(""));
	}

	private PersonicxPortraitModel constructStableModel() {
		final PersonicxPortraitModel model = new PersonicxPortraitModel();

		final List<PersonicxPortraitRow> portraitData = new ArrayList<PersonicxPortraitRow>();

		final PersonicxPortraitRow portraitRow = new PersonicxPortraitRow();
		portraitData.add(portraitRow);

		portraitRow.setAge("30-45");
		portraitRow.setAgeText("Career");
		portraitRow.setBaseCount(867897);
		portraitRow.setBasePercent(0.00370086);
		portraitRow.setBaseTotal(234511935);
		portraitRow.setBubbleSize("SMALL");
		portraitRow.setClusterName("Metro Active");
		portraitRow.setGroupCode("09B");
		portraitRow.setGroupName("Comfortable Independence");
		portraitRow.setHomeOwnership("Owner");
		portraitRow.setIb1270(56);
		portraitRow.setIncome("Low");
		portraitRow.setIndex(437);
		portraitRow.setKids("No Kids");
		portraitRow.setMaritalStatus("Single");
		portraitRow.setNetWorth("<$500K");
		portraitRow.setPenetration(0.00370086);
		portraitRow.setProbabilityIndex("Highly Likely");
		portraitRow.setTargetCount(3239);
		portraitRow.setTargetPercent(0.016195);
		portraitRow.setTargetTotal(200000);
		portraitRow.setUrbanicity("City & Surrounds");
		portraitRow.setUsedInMultipleGroups(null);
		portraitRow.setzScore(91.84770392278503);

		model.setPortraitData(portraitData);

		final PersonicxReferenceModel reference = new PersonicxReferenceModel();
		reference.setType("National");
		reference.setId("0");
		reference.setQuestion("National");
		reference.setResponse(null);

		model.setReference(reference);

		final PersonicxTargetModel target = new PersonicxTargetModel();

		target.setType("Dataset");
		target.setId("11538078560090972356");
		target.setQuestion("Insight 3");
		target.setResponse(null);

		model.setTarget(target);
		return model;
	}
}
