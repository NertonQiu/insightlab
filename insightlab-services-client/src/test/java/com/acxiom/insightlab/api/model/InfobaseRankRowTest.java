package com.acxiom.insightlab.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class InfobaseRankRowTest {

	@Test
	public final void testEqualsAndHashCode() {
		final InfobaseRankRow firstObject = constructStableObject();
		final InfobaseRankRow secondObject = constructStableObject();
		final InfobaseRankRow thirdObject = constructStableObject();

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

	private InfobaseRankRow constructStableObject() {
		final InfobaseRankRow stableObject = new InfobaseRankRow();

		stableObject.setCumulativeRank(0.5);
		stableObject.setRank(0.3);
		stableObject.setPercent(0.25);

		return stableObject;
	}
}
