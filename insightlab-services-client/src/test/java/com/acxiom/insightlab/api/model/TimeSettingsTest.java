package com.acxiom.insightlab.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TimeSettingsTest {

	@Test
	public void testHashCode() {
		TimeSettings timeSettings1 = new TimeSettings();
		TimeSettings timeSettings2 = new TimeSettings();

		// symmetry
		assertEquals(timeSettings1, timeSettings2);
		assertEquals(timeSettings2, timeSettings1);
		assertTrue(timeSettings1.hashCode() == timeSettings2.hashCode());

		timeSettings1.setDateFormat("test format");
		timeSettings1.setTimeFormat("test format");
		timeSettings1.setTimeZoneId("test id");
		final int testUtcOffset = 42;
		timeSettings1.setUtcOffset(testUtcOffset);

		assertFalse(timeSettings1.hashCode() == timeSettings2.hashCode());

		timeSettings2.setDateFormat("test format");
		timeSettings2.setTimeFormat("test format");
		timeSettings2.setTimeZoneId("test id");
		timeSettings2.setUtcOffset(testUtcOffset);

		// symmetry
		assertEquals(timeSettings1, timeSettings2);
		assertEquals(timeSettings2, timeSettings1);
		assertTrue(timeSettings1.hashCode() == timeSettings2.hashCode());

		// the same object
		assertTrue(timeSettings1.equals(timeSettings1));

		// not null vs null
		assertNotEquals(timeSettings1, null);

		// object of different type
		assertFalse(timeSettings1.equals(""));
	}

	@Test
	public void testEqualsObject() {
		TimeSettings timeSettings1 = new TimeSettings();
		TimeSettings timeSettings2 = null;

		assertFalse(timeSettings1.equals(timeSettings2));

		timeSettings2 = new TimeSettings();
		assertTrue(timeSettings1.equals(timeSettings2));
		assertTrue(timeSettings2.equals(timeSettings1));

		timeSettings1.setDateFormat("test format");
		timeSettings1.setTimeFormat("test format");
		timeSettings1.setTimeZoneId("test id");
		final int testUtcOffset = 42;
		timeSettings1.setUtcOffset(testUtcOffset);

		assertFalse(timeSettings1.equals(timeSettings2));

		timeSettings2.setDateFormat("test format");
		timeSettings2.setTimeFormat("test format");
		timeSettings2.setTimeZoneId("test id");
		timeSettings2.setUtcOffset(testUtcOffset);

		// symmetry
		assertTrue(timeSettings1.equals(timeSettings2));
		assertTrue(timeSettings2.equals(timeSettings1));

		// the same object
		assertTrue(timeSettings1.equals(timeSettings1));

		// object of different type
		assertFalse(timeSettings1.equals(""));
	}

}
