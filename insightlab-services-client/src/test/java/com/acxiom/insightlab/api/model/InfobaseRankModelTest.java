package com.acxiom.insightlab.api.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class InfobaseRankModelTest {

	@Test
	public final void testEqualsAndHashCode() {
		final InfobaseRankModel firstObject = constructStableObject();
		final InfobaseRankModel secondObject = constructStableObject();
		final InfobaseRankModel thirdObject = constructStableObject();

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

	private InfobaseRankModel constructStableObject() {
		final InfobaseRankModel stableObject = new InfobaseRankModel();

		stableObject.setCumulativeRank(Arrays.asList(0.5, 0.4, 0.1, 0.1342));
		stableObject.setRank(Arrays.asList(0.9, 0.12));
		stableObject.setPercent(Arrays.asList(0.33, 0.22, 0.11));

		return stableObject;
	}

}
