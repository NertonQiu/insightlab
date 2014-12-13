package com.acxiom.insightlab.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class InfobaseRankWrapperTest {

	@Test
	public final void testEqualsAndHashCode() {
		final InfobaseRankWrapper firstObject = constructStableModel();
		final InfobaseRankWrapper secondObject = constructStableModel();
		final InfobaseRankWrapper thirdObject = constructStableModel();

		// check full model
		assertEqualsAndHashCode(firstObject, secondObject, thirdObject);
	}

	private void assertEqualsAndHashCode(Object first, Object second,
			Object third) {
		// reflexive
		assertTrue(first.equals(first));
		assertTrue(first.hashCode() == first.hashCode());

		// symmetric
		assertEquals(first.equals(second), second.equals(first));
		assertEquals(first.hashCode(), second.hashCode());

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

	private InfobaseRankWrapper constructStableModel() {
		final InfobaseRankModel infobaseRankModel = new InfobaseRankModel();

		infobaseRankModel.setCumulativeRank(Arrays
				.asList(0.535, 0.4, 0.1, 0.1342));
		infobaseRankModel.setRank(Arrays.asList(0.235, 0.42, 0.1, 0.1342));
		infobaseRankModel.setPercent(Arrays.asList(0.5, 0.14, 0.1231, 0.1342));

		return new InfobaseRankWrapper(infobaseRankModel);
	}

}
