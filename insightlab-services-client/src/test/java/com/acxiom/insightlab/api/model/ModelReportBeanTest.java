package com.acxiom.insightlab.api.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ModelReportBeanTest {

	private static final Date TEST_DATE = new Date();

	@Test
	public final void testEqualsAndHashCode() {
		final ModelReportBean firstModel = constructStableModel();
		final ModelReportBean secondModel = constructStableModel();
		final ModelReportBean thirdModel = constructStableModel();

		// check full model
		assertEqualsAndHashCode(firstModel, secondModel, thirdModel);

		// check parts of the model
		assertEqualsAndHashCode(firstModel.getGain().get(0), secondModel
				.getGain().get(0), thirdModel.getGain().get(0));
		assertEqualsAndHashCode(firstModel.getScorecard().get(0), secondModel
				.getScorecard().get(0), thirdModel.getScorecard().get(0));
		assertEqualsAndHashCode(firstModel.getIbRank(),
				secondModel.getIbRank(), thirdModel.getIbRank());
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

	private ModelReportBean constructStableModel() {
		final ModelReportBean model = new ModelReportBean();

		model.setCreatedDate(TEST_DATE);
		model.setDescription("Model O");
		model.setId("15394797157564604265");
		model.setInsightDeleted(null);
		model.setInsightDescription("Insight 25");
		model.setInsightID("14278104306950060211");
		model.setIsPrivate(0);
		model.setSource(null);
		model.setStatus(null);
		model.setTenantID(null);
		model.setType(null);
		model.setUserID(null);

		final List<GainBean> gainList = new ArrayList<GainBean>();
		final GainBean gain = new GainBean();

		gain.setTotal(2397);
		gain.setCumulativeTargetPercent(0.0983463157);
		gain.setLowerBound(0.98669341090206);
		gain.setTargetNumber(2361);
		gain.setKs(9.7);
		gain.setCumulativeReferencePercent(0.0015043249);
		gain.setReportSource("T");
		gain.setCumulativeReference(36);
		gain.setCumulativeTotal(2397);
		gain.setLiftCurve(1.96);
		gain.setModelID("15394797157564604265");
		gain.setRank(1);
		gain.setTargetPercent(0.9849812265);
		gain.setLift(196);
		gain.setRankGraph(5);
		gain.setCumulativeTotalPercent(0.050002086);
		gain.setBaseline(1);
		gain.setUpperBound(1);
		gain.setCumulativeTarget(2361);

		gainList.add(gain);
		model.setGain(gainList);

		final List<ScorecardBean> scorecardList = new ArrayList<ScorecardBean>();
		final ScorecardBean scorecard = new ScorecardBean();

		scorecard.setAverageRank(1);
		scorecard.setContribution(8.8);
		scorecard.setCount(10);
		scorecard.setDescription("Age in Two-Year Increments - 1st Individual");
		scorecard.setIbElement("IB8616");
		scorecard.setModelID("15394797157564604265");

		scorecardList.add(scorecard);
		model.setScorecard(scorecardList);

		final InfobaseRankModel ibRank = new InfobaseRankModel();

		ibRank.setCumulativeRank(Arrays.asList(123124.0));
		ibRank.setRank(Arrays.asList(43124.0, 12323.0));
		ibRank.setPercent(Arrays.asList(0.123, 0.432));
		ibRank.setRankTotal(43124.0 + 12323.0);
		ibRank.setModelID("15394797157564604265");

		model.setIbRank(ibRank);

		return model;
	}

}
