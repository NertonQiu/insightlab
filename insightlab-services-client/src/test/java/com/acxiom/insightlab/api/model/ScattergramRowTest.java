package com.acxiom.insightlab.api.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Test;

public class ScattergramRowTest {

	@Test
	public void testHashCodeAndEquals() {
		ScattergramRow row1 = new ScattergramRow();
		ScattergramRow row2 = new ScattergramRow();

		assertEquals(row1, row2);
		assertEquals(row2, row1);
		assertTrue(row1.hashCode() == row2.hashCode());

		row1.setAge("test age");
		row1.setAgeText("test age text");
		row1.setChartIndex1(42);
		row1.setChartIndex2(43);
		row1.setClusterName("test cluster name");
		row1.setGroupCode("test group code");
		row1.setGroupName("test group name");
		row1.setHomeOwnership("test home ownership");
		row1.setIb1270(18);
		row1.setIncome("test income");
		row1.setIndex1(21);
		row1.setIndex2(5);
		row1.setKids("test kids");
		row1.setMaritalStatus("test marital status");
		row1.setNetWorth("test net worth");
		row1.setQuadrant("test quadrant");
		row1.setReferencePercent(0.125);
		row1.setUrbanicity("test urbanicity");

		assertNotEquals(row1, row2);
		assertNotEquals(row2, row1);
		assertFalse(row1.hashCode() == row2.hashCode());

		row2.setAge("test age");
		row2.setAgeText("test age text");
		row2.setChartIndex1(42);
		row2.setChartIndex2(43);
		row2.setClusterName("test cluster name");
		row2.setGroupCode("test group code");
		row2.setGroupName("test group name");
		row2.setHomeOwnership("test home ownership");
		row2.setIb1270(18);
		row2.setIncome("test income");
		row2.setIndex1(21);
		row2.setIndex2(5);
		row2.setKids("test kids");
		row2.setMaritalStatus("test marital status");
		row2.setNetWorth("test net worth");
		row2.setQuadrant("test quadrant");
		row2.setReferencePercent(0.125);
		row2.setUrbanicity("test urbanicity");

		// symmetry
		assertEquals(row1, row2);
		assertEquals(row2, row1);
		
		assertTrue(row1.hashCode() == row2.hashCode());

		// same object
		assertEquals(row1, row1);
		
		// not null vs null
		assertNotEquals(row1, null);
		
		// objects of different type
		assertNotEquals(row1, "");
	}

}
