package com.acxiom.insightlab.report.service;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.model.AudiencePropensitiesModel;
import com.acxiom.insightlab.api.model.AudiencePropensitiesRow;
import com.acxiom.insightlab.api.model.GainBean;
import com.acxiom.insightlab.api.model.InfobaseRankModel;
import com.acxiom.insightlab.api.model.InfobaseRankRow;
import com.acxiom.insightlab.api.model.InfobaseRankWrapper;
import com.acxiom.insightlab.api.model.ModelReportBean;
import com.acxiom.insightlab.api.model.PersonicxPortraitModel;
import com.acxiom.insightlab.api.model.PersonicxPortraitRow;
import com.acxiom.insightlab.api.model.PersonicxReferenceModel;
import com.acxiom.insightlab.api.model.PersonicxTargetModel;
import com.acxiom.insightlab.api.model.ScattergramModel;
import com.acxiom.insightlab.api.model.ScattergramRow;
import com.acxiom.insightlab.api.model.ScorecardBean;
import com.acxiom.insightlab.api.model.TimeSettings;
import com.acxiom.insightlab.api.service.impl.ModelingServiceImpl;
import com.acxiom.insightlab.api.service.impl.ReportingServiceImpl;
import com.acxiom.insightlab.test.StableData;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(PowerMockRunner.class)
public class DataSourceServiceTest {

	private static final double DELTA = 0.0000000000001;

	private static DataSourceService dataSourceService;
	private static ReportingServiceImpl reportingServiceMock;
	private static ModelingServiceImpl modelingServiceMock;

	@Before
	public void setUp() throws Exception {
		reportingServiceMock = createMock(ReportingServiceImpl.class);
		modelingServiceMock = createMock(ModelingServiceImpl.class);
		dataSourceService = new DataSourceService();

		Whitebox.setInternalState(dataSourceService, reportingServiceMock);
		Whitebox.setInternalState(dataSourceService, modelingServiceMock);

	}

	@AfterClass
	public static void staticTearDown() throws Exception {
		reportingServiceMock = null;
		modelingServiceMock = null;
		dataSourceService = null;
	}

	@Test
	public void testGetScorecard() throws IOException, URISyntaxException,
			JSONException, BaseDataApiException {
		final String stableData = StableData
				.getDataFromFile("ModelOReport.json");
		final JSONObject stableDataJson = new JSONObject(stableData);

		expect(
				modelingServiceMock
						.fetchModelReport(anyObject(JSONObject.class)))
				.andReturn(stableDataJson);

		replay(modelingServiceMock);

		final JRBeanCollectionDataSource dataSource = (JRBeanCollectionDataSource) dataSourceService
				.getScorecard(null);

		verify(modelingServiceMock);

		final Collection<?> modelCollection = dataSource.getData();
		for (Iterator iterator = modelCollection.iterator(); iterator.hasNext();) {
			final ModelReportBean modelReportBean = (ModelReportBean) iterator
					.next();

			final SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			assertEquals(sdf.format(modelReportBean.getCreatedDate()),
					"2013-09-04 13:15:56");
			assertEquals(modelReportBean.getDescription(), "Model O");
			assertEquals(modelReportBean.getId(), "15394797157564604265");
			assertEquals(modelReportBean.getInsightDeleted(), null);
			assertEquals(modelReportBean.getInsightDescription(), "Insight 25");
			assertEquals(modelReportBean.getInsightID(), "14278104306950060211");
			assertEquals(modelReportBean.getIsPrivate(), 0);
			assertEquals(modelReportBean.getSource(), null);
			assertEquals(modelReportBean.getStatus(), null);
			assertEquals(modelReportBean.getTenantID(), null);
			assertEquals(modelReportBean.getType(), null);
			assertEquals(modelReportBean.getUserID(), null);

			final InfobaseRankModel ibRank = modelReportBean.getIbRank();

			assertEquals(ibRank.getModelID(), "15394797157564604265");
			assertEquals(ibRank.getRankTotal(), 141189700, DELTA);

			final List<Double> rankList = ibRank.getRank();
			final int rankListSize = rankList.size();
			assertEquals(rankList.size(), 9);
			assertEquals(rankList.get(0), 3358600, DELTA);
			assertEquals(rankList.get(rankListSize - 1), 157700, DELTA);

			final List<Double> cumulativeRankList = ibRank.getCumulativeRank();
			final int cumulativeRankListSize = cumulativeRankList.size();
			assertEquals(cumulativeRankList.size(), 9);
			assertEquals(cumulativeRankList.get(0), 3358600, DELTA);
			assertEquals(cumulativeRankList.get(cumulativeRankListSize - 1),
					141189700, DELTA);

			final List<Double> percentList = ibRank.getPercent();
			final int percentListSize = percentList.size();
			assertEquals(percentList.size(), 9);
			assertEquals(percentList.get(0), 0.02378785421316144, DELTA);
			assertEquals(percentList.get(percentListSize - 1),
					0.0011169370003619244, DELTA);

			final List<GainBean> gainList = modelReportBean.getGain();

			final int gainListSize = gainList.size();
			assertEquals(gainListSize, 40);

			final GainBean firstGain = gainList.get(0);

			assertEquals(firstGain.getTotal(), 2397, DELTA);
			assertEquals(firstGain.getCumulativeTargetPercent(), 0.0983463157,
					DELTA);
			assertEquals(firstGain.getLowerBound(), 0.98669341090206, DELTA);
			assertEquals(firstGain.getTargetNumber(), 2361, DELTA);
			assertEquals(firstGain.getKs(), 9.7, DELTA);
			assertEquals(firstGain.getCumulativeReferencePercent(),
					0.0015043249, DELTA);
			assertEquals(firstGain.getReportSource(), "T");
			assertEquals(firstGain.getCumulativeReference(), 36, DELTA);
			assertEquals(firstGain.getCumulativeTotal(), 2397, DELTA);
			assertEquals(firstGain.getLiftCurve(), 1.96, DELTA);
			assertEquals(firstGain.getModelID(), "15394797157564604265");
			assertEquals(firstGain.getRank(), 1, DELTA);
			assertEquals(firstGain.getTargetPercent(), 0.9849812265, DELTA);
			assertEquals(firstGain.getLift(), 196, DELTA);
			assertEquals(firstGain.getRankGraph(), 5, DELTA);
			assertEquals(firstGain.getCumulativeTotalPercent(), 0.050002086,
					DELTA);
			assertEquals(firstGain.getBaseline(), 1, DELTA);
			assertEquals(firstGain.getUpperBound(), 1, DELTA);
			assertEquals(firstGain.getCumulativeTarget(), 2361, DELTA);

			final GainBean lastGain = gainList.get(gainListSize - 1);

			assertEquals(lastGain.getTotal(), 241, DELTA);
			assertEquals(lastGain.getCumulativeTargetPercent(), 0.9799766394,
					DELTA);
			assertEquals(lastGain.getLowerBound(), 0, DELTA);
			assertEquals(lastGain.getTargetNumber(), 3, DELTA);
			assertEquals(lastGain.getKs(), -2, DELTA);
			assertEquals(lastGain.getCumulativeReferencePercent(), 1, DELTA);
			assertEquals(lastGain.getReportSource(), "V");
			assertEquals(lastGain.getCumulativeReference(), 6069, DELTA);
			assertEquals(lastGain.getCumulativeTotal(), 11942, DELTA);
			assertEquals(lastGain.getLiftCurve(), 0.02, DELTA);
			assertEquals(lastGain.getModelID(), "15394797157564604265");
			assertEquals(lastGain.getRank(), 96, DELTA);
			assertEquals(lastGain.getTargetPercent(), 0.0124481328, DELTA);
			assertEquals(lastGain.getLift(), 2, DELTA);
			assertEquals(lastGain.getRankGraph(), 100, DELTA);
			assertEquals(lastGain.getCumulativeTotalPercent(), 0.9900514011,
					DELTA);
			assertEquals(lastGain.getBaseline(), 1, DELTA);
			assertEquals(lastGain.getUpperBound(), 0.01503688522414, DELTA);
			assertEquals(lastGain.getCumulativeTarget(), 5873, DELTA);

			final List<ScorecardBean> scorecardList = modelReportBean
					.getScorecard();

			final int scorecardListSize = scorecardList.size();
			assertEquals(scorecardListSize, 48);

			final ScorecardBean firstScorecard = scorecardList.get(0);

			assertEquals(firstScorecard.getAverageRank(), 1, DELTA);
			assertEquals(firstScorecard.getContribution(), 8.8, DELTA);
			assertEquals(firstScorecard.getCount(), 10, DELTA);
			assertEquals(firstScorecard.getDescription(),
					"Age in Two-Year Increments - 1st Individual");
			assertEquals(firstScorecard.getIbElement(), "IB8616");
			assertEquals(firstScorecard.getModelID(), "15394797157564604265");

			final ScorecardBean lastScorecard = scorecardList
					.get(scorecardListSize - 1);

			assertEquals(lastScorecard.getAverageRank(), 15, DELTA);
			assertEquals(lastScorecard.getContribution(), 0.08, DELTA);
			assertEquals(lastScorecard.getCount(), 1, DELTA);
			assertEquals(lastScorecard.getDescription(), "Tennis");
			assertEquals(lastScorecard.getIbElement(), "IB7810");
			assertEquals(lastScorecard.getModelID(), "15394797157564604265");
		}
	}

	@Test
	public void testGetGains() throws IOException, URISyntaxException,
			JSONException, BaseDataApiException {
		final String stableData = StableData
				.getDataFromFile("ModelOReport.json");
		final JSONObject stableDataJson = new JSONObject(stableData);

		expect(
				modelingServiceMock
						.fetchModelReport(anyObject(JSONObject.class)))
				.andReturn(stableDataJson);

		replay(modelingServiceMock);

		final JRBeanCollectionDataSource dataSource = (JRBeanCollectionDataSource) dataSourceService
				.getGains(null);

		verify(modelingServiceMock);

		final Collection<?> modelCollection = dataSource.getData();
		for (Iterator iterator = modelCollection.iterator(); iterator.hasNext();) {
			final ModelReportBean modelReportBean = (ModelReportBean) iterator
					.next();

			final SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			assertEquals(sdf.format(modelReportBean.getCreatedDate()),
					"2013-09-04 13:15:56");
			assertEquals(modelReportBean.getDescription(), "Model O");
			assertEquals(modelReportBean.getId(), "15394797157564604265");
			assertEquals(modelReportBean.getInsightDeleted(), null);
			assertEquals(modelReportBean.getInsightDescription(), "Insight 25");
			assertEquals(modelReportBean.getInsightID(), "14278104306950060211");
			assertEquals(modelReportBean.getIsPrivate(), 0);
			assertEquals(modelReportBean.getSource(), null);
			assertEquals(modelReportBean.getStatus(), null);
			assertEquals(modelReportBean.getTenantID(), null);
			assertEquals(modelReportBean.getType(), null);
			assertEquals(modelReportBean.getUserID(), null);

			final InfobaseRankModel ibRank = modelReportBean.getIbRank();

			assertEquals(ibRank.getModelID(), "15394797157564604265");
			assertEquals(ibRank.getRankTotal(), 141189700, DELTA);

			final List<Double> rankList = ibRank.getRank();
			final int rankListSize = rankList.size();
			assertEquals(rankList.size(), 9);
			assertEquals(rankList.get(0), 3358600, DELTA);
			assertEquals(rankList.get(rankListSize - 1), 157700, DELTA);

			final List<Double> cumulativeRankList = ibRank.getCumulativeRank();
			final int cumulativeRankListSize = cumulativeRankList.size();
			assertEquals(cumulativeRankList.size(), 9);
			assertEquals(cumulativeRankList.get(0), 3358600, DELTA);
			assertEquals(cumulativeRankList.get(cumulativeRankListSize - 1),
					141189700, DELTA);

			final List<Double> percentList = ibRank.getPercent();
			final int percentListSize = percentList.size();
			assertEquals(percentList.size(), 9);
			assertEquals(percentList.get(0), 0.02378785421316144, DELTA);
			assertEquals(percentList.get(percentListSize - 1),
					0.0011169370003619244, DELTA);

			final List<GainBean> gainList = modelReportBean.getGain();

			final int gainListSize = gainList.size();
			assertEquals(gainListSize, 43);

			final GainBean firstNonZeroGain = gainList.get(1);

			assertEquals(firstNonZeroGain.getTotal(), 2397, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTargetPercent(),
					0.0983463157, DELTA);
			assertEquals(firstNonZeroGain.getLowerBound(), 0.98669341090206,
					DELTA);
			assertEquals(firstNonZeroGain.getTargetNumber(), 2361, DELTA);
			assertEquals(firstNonZeroGain.getKs(), 9.7, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeReferencePercent(),
					0.0015043249, DELTA);
			assertEquals(firstNonZeroGain.getReportSource(), "T");
			assertEquals(firstNonZeroGain.getCumulativeReference(), 36, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTotal(), 2397, DELTA);
			assertEquals(firstNonZeroGain.getLiftCurve(), 1.96, DELTA);
			assertEquals(firstNonZeroGain.getModelID(), "15394797157564604265");
			assertEquals(firstNonZeroGain.getRank(), 1, DELTA);
			assertEquals(firstNonZeroGain.getTargetPercent(), 0.9849812265,
					DELTA);
			assertEquals(firstNonZeroGain.getLift(), 196, DELTA);
			assertEquals(firstNonZeroGain.getRankGraph(), 5, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTotalPercent(),
					0.050002086, DELTA);
			assertEquals(firstNonZeroGain.getBaseline(), 1, DELTA);
			assertEquals(firstNonZeroGain.getUpperBound(), 1, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTarget(), 2361, DELTA);

			final GainBean lastNonBaseLineGain = gainList.get(gainListSize - 3);

			assertEquals(lastNonBaseLineGain.getTotal(), 241, DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTargetPercent(),
					0.9799766394, DELTA);
			assertEquals(lastNonBaseLineGain.getLowerBound(), 0, DELTA);
			assertEquals(lastNonBaseLineGain.getTargetNumber(), 3, DELTA);
			assertEquals(lastNonBaseLineGain.getKs(), -2, DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeReferencePercent(),
					1, DELTA);
			assertEquals(lastNonBaseLineGain.getReportSource(), "V");
			assertEquals(lastNonBaseLineGain.getCumulativeReference(), 6069,
					DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTotal(), 11942, DELTA);
			assertEquals(lastNonBaseLineGain.getLiftCurve(), 0.02, DELTA);
			assertEquals(lastNonBaseLineGain.getModelID(),
					"15394797157564604265");
			assertEquals(lastNonBaseLineGain.getRank(), 96, DELTA);
			assertEquals(lastNonBaseLineGain.getTargetPercent(), 0.0124481328,
					DELTA);
			assertEquals(lastNonBaseLineGain.getLift(), 2, DELTA);
			assertEquals(lastNonBaseLineGain.getRankGraph(), 100, DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTotalPercent(),
					0.9900514011, DELTA);
			assertEquals(lastNonBaseLineGain.getBaseline(), 1, DELTA);
			assertEquals(lastNonBaseLineGain.getUpperBound(), 0.01503688522414,
					DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTarget(), 5873, DELTA);

			final GainBean zeroGainBean = gainList.get(0);

			assertEquals(zeroGainBean.getTotal(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTargetPercent(), 0, DELTA);
			assertEquals(zeroGainBean.getLowerBound(), 0, DELTA);
			assertEquals(zeroGainBean.getTargetNumber(), 0, DELTA);
			assertEquals(zeroGainBean.getKs(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeReferencePercent(), 0, DELTA);
			assertEquals(zeroGainBean.getReportSource(), "ZERO");
			assertEquals(zeroGainBean.getCumulativeReference(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTotal(), 0, DELTA);
			assertEquals(zeroGainBean.getLiftCurve(), 0, DELTA);
			assertEquals(zeroGainBean.getModelID(), null);
			assertEquals(zeroGainBean.getRank(), 0, DELTA);
			assertEquals(zeroGainBean.getTargetPercent(), 0, DELTA);
			assertEquals(zeroGainBean.getLift(), 0, DELTA);
			assertEquals(zeroGainBean.getRankGraph(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTotalPercent(), 0, DELTA);
			assertEquals(zeroGainBean.getBaseline(), 0, DELTA);
			assertEquals(zeroGainBean.getUpperBound(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTarget(), 0, DELTA);

			final GainBean baselineFirstPoint = gainList
					.get(gainList.size() - 2);

			assertEquals(baselineFirstPoint.getTotal(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTargetPercent(), 0,
					DELTA);
			assertEquals(baselineFirstPoint.getLowerBound(), 0, DELTA);
			assertEquals(baselineFirstPoint.getTargetNumber(), 0, DELTA);
			assertEquals(baselineFirstPoint.getKs(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeReferencePercent(), 0,
					DELTA);
			assertEquals(baselineFirstPoint.getReportSource(), "BASELINE");
			assertEquals(baselineFirstPoint.getCumulativeReference(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTotal(), 0, DELTA);
			assertEquals(baselineFirstPoint.getLiftCurve(), 1, DELTA);
			assertEquals(baselineFirstPoint.getModelID(), null);
			assertEquals(baselineFirstPoint.getRank(), 0, DELTA);
			assertEquals(baselineFirstPoint.getTargetPercent(), 0, DELTA);
			assertEquals(baselineFirstPoint.getLift(), 0, DELTA);
			assertEquals(baselineFirstPoint.getRankGraph(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTotalPercent(),
					0.0499917095, DELTA);
			assertEquals(baselineFirstPoint.getBaseline(), 0, DELTA);
			assertEquals(baselineFirstPoint.getUpperBound(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTarget(), 0, DELTA);

			final GainBean baselineSecondPoint = gainList
					.get(gainList.size() - 1);

			assertEquals(baselineSecondPoint.getTotal(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTargetPercent(), 1,
					DELTA);
			assertEquals(baselineSecondPoint.getLowerBound(), 0, DELTA);
			assertEquals(baselineSecondPoint.getTargetNumber(), 0, DELTA);
			assertEquals(baselineSecondPoint.getKs(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeReferencePercent(),
					0, DELTA);
			assertEquals(baselineSecondPoint.getReportSource(), "BASELINE");
			assertEquals(baselineSecondPoint.getCumulativeReference(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTotal(), 0, DELTA);
			assertEquals(baselineSecondPoint.getLiftCurve(), 1, DELTA);
			assertEquals(baselineSecondPoint.getModelID(), null);
			assertEquals(baselineSecondPoint.getRank(), 0, DELTA);
			assertEquals(baselineSecondPoint.getTargetPercent(), 0, DELTA);
			assertEquals(baselineSecondPoint.getLift(), 0, DELTA);
			assertEquals(baselineSecondPoint.getRankGraph(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTotalPercent(), 1,
					DELTA);
			assertEquals(baselineSecondPoint.getBaseline(), 0, DELTA);
			assertEquals(baselineSecondPoint.getUpperBound(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTarget(), 0, DELTA);

			final List<ScorecardBean> scorecardList = modelReportBean
					.getScorecard();

			final int scorecardListSize = scorecardList.size();
			assertEquals(scorecardListSize, 48);

			final ScorecardBean firstScorecard = scorecardList.get(0);

			assertEquals(firstScorecard.getAverageRank(), 1, DELTA);
			assertEquals(firstScorecard.getContribution(), 8.8, DELTA);
			assertEquals(firstScorecard.getCount(), 10, DELTA);
			assertEquals(firstScorecard.getDescription(),
					"Age in Two-Year Increments - 1st Individual");
			assertEquals(firstScorecard.getIbElement(), "IB8616");
			assertEquals(firstScorecard.getModelID(), "15394797157564604265");

			final ScorecardBean lastScorecard = scorecardList
					.get(scorecardListSize - 1);

			assertEquals(lastScorecard.getAverageRank(), 15, DELTA);
			assertEquals(lastScorecard.getContribution(), 0.08, DELTA);
			assertEquals(lastScorecard.getCount(), 1, DELTA);
			assertEquals(lastScorecard.getDescription(), "Tennis");
			assertEquals(lastScorecard.getIbElement(), "IB7810");
			assertEquals(lastScorecard.getModelID(), "15394797157564604265");
		}
	}

	@Test
	public void testGetValidationGains() throws IOException,
			URISyntaxException, BaseDataApiException, JSONException {
		final String stableData = StableData
				.getDataFromFile("ModelOReport.json");
		final JSONObject stableDataJson = new JSONObject(stableData);

		expect(
				modelingServiceMock
						.fetchModelReport(anyObject(JSONObject.class)))
				.andReturn(stableDataJson);

		replay(modelingServiceMock);

		final JRBeanCollectionDataSource dataSource = (JRBeanCollectionDataSource) dataSourceService
				.getValidationGains(null);

		verify(modelingServiceMock);

		final Collection<?> modelCollection = dataSource.getData();
		for (Iterator iterator = modelCollection.iterator(); iterator.hasNext();) {
			final ModelReportBean modelReportBean = (ModelReportBean) iterator
					.next();

			final SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			assertEquals(sdf.format(modelReportBean.getCreatedDate()),
					"2013-09-04 13:15:56");
			assertEquals(modelReportBean.getDescription(), "Model O");
			assertEquals(modelReportBean.getId(), "15394797157564604265");
			assertEquals(modelReportBean.getInsightDeleted(), null);
			assertEquals(modelReportBean.getInsightDescription(), "Insight 25");
			assertEquals(modelReportBean.getInsightID(), "14278104306950060211");
			assertEquals(modelReportBean.getIsPrivate(), 0);
			assertEquals(modelReportBean.getSource(), null);
			assertEquals(modelReportBean.getStatus(), null);
			assertEquals(modelReportBean.getTenantID(), null);
			assertEquals(modelReportBean.getType(), null);
			assertEquals(modelReportBean.getUserID(), null);

			final InfobaseRankModel ibRank = modelReportBean.getIbRank();

			assertEquals(ibRank.getModelID(), "15394797157564604265");
			assertEquals(ibRank.getRankTotal(), 141189700, DELTA);

			final List<Double> rankList = ibRank.getRank();
			final int rankListSize = rankList.size();
			assertEquals(rankList.size(), 9);
			assertEquals(rankList.get(0), 3358600, DELTA);
			assertEquals(rankList.get(rankListSize - 1), 157700, DELTA);

			final List<Double> cumulativeRankList = ibRank.getCumulativeRank();
			final int cumulativeRankListSize = cumulativeRankList.size();
			assertEquals(cumulativeRankList.size(), 9);
			assertEquals(cumulativeRankList.get(0), 3358600, DELTA);
			assertEquals(cumulativeRankList.get(cumulativeRankListSize - 1),
					141189700, DELTA);

			final List<Double> percentList = ibRank.getPercent();
			final int percentListSize = percentList.size();
			assertEquals(percentList.size(), 9);
			assertEquals(percentList.get(0), 0.02378785421316144, DELTA);
			assertEquals(percentList.get(percentListSize - 1),
					0.0011169370003619244, DELTA);

			final List<GainBean> gainList = modelReportBean.getGain();

			final int gainListSize = gainList.size();
			assertEquals(gainListSize, 44);

			final GainBean firstNonZeroGain = gainList.get(2);

			assertEquals(firstNonZeroGain.getTotal(), 2397, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTargetPercent(),
					0.0983463157, DELTA);
			assertEquals(firstNonZeroGain.getLowerBound(), 0.98669341090206,
					DELTA);
			assertEquals(firstNonZeroGain.getTargetNumber(), 2361, DELTA);
			assertEquals(firstNonZeroGain.getKs(), 9.7, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeReferencePercent(),
					0.0015043249, DELTA);
			assertEquals(firstNonZeroGain.getReportSource(), "T");
			assertEquals(firstNonZeroGain.getCumulativeReference(), 36, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTotal(), 2397, DELTA);
			assertEquals(firstNonZeroGain.getLiftCurve(), 1.96, DELTA);
			assertEquals(firstNonZeroGain.getModelID(), "15394797157564604265");
			assertEquals(firstNonZeroGain.getRank(), 1, DELTA);
			assertEquals(firstNonZeroGain.getTargetPercent(), 0.9849812265,
					DELTA);
			assertEquals(firstNonZeroGain.getLift(), 196, DELTA);
			assertEquals(firstNonZeroGain.getRankGraph(), 5, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTotalPercent(),
					0.050002086, DELTA);
			assertEquals(firstNonZeroGain.getBaseline(), 1, DELTA);
			assertEquals(firstNonZeroGain.getUpperBound(), 1, DELTA);
			assertEquals(firstNonZeroGain.getCumulativeTarget(), 2361, DELTA);

			final GainBean lastNonBaseLineGain = gainList.get(gainListSize - 3);

			assertEquals(lastNonBaseLineGain.getTotal(), 241, DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTargetPercent(),
					0.9799766394, DELTA);
			assertEquals(lastNonBaseLineGain.getLowerBound(), 0, DELTA);
			assertEquals(lastNonBaseLineGain.getTargetNumber(), 3, DELTA);
			assertEquals(lastNonBaseLineGain.getKs(), -2, DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeReferencePercent(),
					1, DELTA);
			assertEquals(lastNonBaseLineGain.getReportSource(), "V");
			assertEquals(lastNonBaseLineGain.getCumulativeReference(), 6069,
					DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTotal(), 11942, DELTA);
			assertEquals(lastNonBaseLineGain.getLiftCurve(), 0.02, DELTA);
			assertEquals(lastNonBaseLineGain.getModelID(),
					"15394797157564604265");
			assertEquals(lastNonBaseLineGain.getRank(), 96, DELTA);
			assertEquals(lastNonBaseLineGain.getTargetPercent(), 0.0124481328,
					DELTA);
			assertEquals(lastNonBaseLineGain.getLift(), 2, DELTA);
			assertEquals(lastNonBaseLineGain.getRankGraph(), 100, DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTotalPercent(),
					0.9900514011, DELTA);
			assertEquals(lastNonBaseLineGain.getBaseline(), 1, DELTA);
			assertEquals(lastNonBaseLineGain.getUpperBound(), 0.01503688522414,
					DELTA);
			assertEquals(lastNonBaseLineGain.getCumulativeTarget(), 5873, DELTA);

			final GainBean zeroGainBean = gainList.get(0);

			assertEquals(zeroGainBean.getTotal(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTargetPercent(), 0, DELTA);
			assertEquals(zeroGainBean.getLowerBound(), 0, DELTA);
			assertEquals(zeroGainBean.getTargetNumber(), 0, DELTA);
			assertEquals(zeroGainBean.getKs(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeReferencePercent(), 0, DELTA);
			assertEquals(zeroGainBean.getReportSource(), "ZERO");
			assertEquals(zeroGainBean.getCumulativeReference(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTotal(), 0, DELTA);
			assertEquals(zeroGainBean.getLiftCurve(), 0, DELTA);
			assertEquals(zeroGainBean.getModelID(), null);
			assertEquals(zeroGainBean.getRank(), 0, DELTA);
			assertEquals(zeroGainBean.getTargetPercent(), 0, DELTA);
			assertEquals(zeroGainBean.getLift(), 0, DELTA);
			assertEquals(zeroGainBean.getRankGraph(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTotalPercent(), 0, DELTA);
			assertEquals(zeroGainBean.getBaseline(), 0, DELTA);
			assertEquals(zeroGainBean.getUpperBound(), 0, DELTA);
			assertEquals(zeroGainBean.getCumulativeTarget(), 0, DELTA);

			final GainBean validationZeroGainBean = gainList.get(1);

			assertEquals(validationZeroGainBean.getTotal(), 0, DELTA);
			assertEquals(validationZeroGainBean.getCumulativeTargetPercent(),
					0, DELTA);
			assertEquals(validationZeroGainBean.getLowerBound(), 0, DELTA);
			assertEquals(validationZeroGainBean.getTargetNumber(), 0, DELTA);
			assertEquals(validationZeroGainBean.getKs(), 0, DELTA);
			assertEquals(
					validationZeroGainBean.getCumulativeReferencePercent(), 0,
					DELTA);
			assertEquals(validationZeroGainBean.getReportSource(),
					"VALIDATION_ZERO");
			assertEquals(validationZeroGainBean.getCumulativeReference(), 0,
					DELTA);
			assertEquals(validationZeroGainBean.getCumulativeTotal(), 0, DELTA);
			assertEquals(validationZeroGainBean.getLiftCurve(), 0, DELTA);
			assertEquals(validationZeroGainBean.getModelID(), null);
			assertEquals(validationZeroGainBean.getRank(), 0, DELTA);
			assertEquals(validationZeroGainBean.getTargetPercent(), 0, DELTA);
			assertEquals(validationZeroGainBean.getLift(), 0, DELTA);
			assertEquals(validationZeroGainBean.getRankGraph(), 0, DELTA);
			assertEquals(validationZeroGainBean.getCumulativeTotalPercent(), 0,
					DELTA);
			assertEquals(validationZeroGainBean.getBaseline(), 0, DELTA);
			assertEquals(validationZeroGainBean.getUpperBound(), 0, DELTA);
			assertEquals(validationZeroGainBean.getCumulativeTarget(), 0, DELTA);

			final GainBean baselineFirstPoint = gainList
					.get(gainList.size() - 2);

			assertEquals(baselineFirstPoint.getTotal(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTargetPercent(), 0,
					DELTA);
			assertEquals(baselineFirstPoint.getLowerBound(), 0, DELTA);
			assertEquals(baselineFirstPoint.getTargetNumber(), 0, DELTA);
			assertEquals(baselineFirstPoint.getKs(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeReferencePercent(), 0,
					DELTA);
			assertEquals(baselineFirstPoint.getReportSource(), "BASELINE");
			assertEquals(baselineFirstPoint.getCumulativeReference(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTotal(), 0, DELTA);
			assertEquals(baselineFirstPoint.getLiftCurve(), 1, DELTA);
			assertEquals(baselineFirstPoint.getModelID(), null);
			assertEquals(baselineFirstPoint.getRank(), 0, DELTA);
			assertEquals(baselineFirstPoint.getTargetPercent(), 0, DELTA);
			assertEquals(baselineFirstPoint.getLift(), 0, DELTA);
			assertEquals(baselineFirstPoint.getRankGraph(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTotalPercent(),
					0.0499917095, DELTA);
			assertEquals(baselineFirstPoint.getBaseline(), 0, DELTA);
			assertEquals(baselineFirstPoint.getUpperBound(), 0, DELTA);
			assertEquals(baselineFirstPoint.getCumulativeTarget(), 0, DELTA);

			final GainBean baselineSecondPoint = gainList
					.get(gainList.size() - 1);

			assertEquals(baselineSecondPoint.getTotal(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTargetPercent(), 1,
					DELTA);
			assertEquals(baselineSecondPoint.getLowerBound(), 0, DELTA);
			assertEquals(baselineSecondPoint.getTargetNumber(), 0, DELTA);
			assertEquals(baselineSecondPoint.getKs(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeReferencePercent(),
					0, DELTA);
			assertEquals(baselineSecondPoint.getReportSource(), "BASELINE");
			assertEquals(baselineSecondPoint.getCumulativeReference(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTotal(), 0, DELTA);
			assertEquals(baselineSecondPoint.getLiftCurve(), 1, DELTA);
			assertEquals(baselineSecondPoint.getModelID(), null);
			assertEquals(baselineSecondPoint.getRank(), 0, DELTA);
			assertEquals(baselineSecondPoint.getTargetPercent(), 0, DELTA);
			assertEquals(baselineSecondPoint.getLift(), 0, DELTA);
			assertEquals(baselineSecondPoint.getRankGraph(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTotalPercent(), 1,
					DELTA);
			assertEquals(baselineSecondPoint.getBaseline(), 0, DELTA);
			assertEquals(baselineSecondPoint.getUpperBound(), 0, DELTA);
			assertEquals(baselineSecondPoint.getCumulativeTarget(), 0, DELTA);

			final List<ScorecardBean> scorecardList = modelReportBean
					.getScorecard();

			final int scorecardListSize = scorecardList.size();
			assertEquals(scorecardListSize, 48);

			final ScorecardBean firstScorecard = scorecardList.get(0);

			assertEquals(firstScorecard.getAverageRank(), 1, DELTA);
			assertEquals(firstScorecard.getContribution(), 8.8, DELTA);
			assertEquals(firstScorecard.getCount(), 10, DELTA);
			assertEquals(firstScorecard.getDescription(),
					"Age in Two-Year Increments - 1st Individual");
			assertEquals(firstScorecard.getIbElement(), "IB8616");
			assertEquals(firstScorecard.getModelID(), "15394797157564604265");

			final ScorecardBean lastScorecard = scorecardList
					.get(scorecardListSize - 1);

			assertEquals(lastScorecard.getAverageRank(), 15, DELTA);
			assertEquals(lastScorecard.getContribution(), 0.08, DELTA);
			assertEquals(lastScorecard.getCount(), 1, DELTA);
			assertEquals(lastScorecard.getDescription(), "Tennis");
			assertEquals(lastScorecard.getIbElement(), "IB7810");
			assertEquals(lastScorecard.getModelID(), "15394797157564604265");
		}
	}

	@Test
	public void testGetInfobase() throws IOException, URISyntaxException,
			JSONException, BaseDataApiException {
		final String stableData = StableData
				.getDataFromFile("ModelOReport.json");
		final JSONObject stableDataJson = new JSONObject(stableData);

		expect(
				modelingServiceMock
						.fetchModelReport(anyObject(JSONObject.class)))
				.andReturn(stableDataJson);

		replay(modelingServiceMock);

		final JRBeanCollectionDataSource dataSource = (JRBeanCollectionDataSource) dataSourceService
				.getInfobase(null);

		verify(modelingServiceMock);

		final Collection<?> modelCollection = dataSource.getData();
		for (Iterator iterator = modelCollection.iterator(); iterator.hasNext();) {
			final InfobaseRankWrapper infobaseRankWrapper = (InfobaseRankWrapper) iterator
					.next();

			assertEquals(infobaseRankWrapper.getModelID(),
					"15394797157564604265");
			assertEquals(infobaseRankWrapper.getRankTotal(), 141189700, DELTA);

			final List<InfobaseRankRow> infobaseRankRowList = infobaseRankWrapper
					.getInfobaseRankRowList();
			assertEquals(infobaseRankRowList.size(), 9);

			final InfobaseRankRow firstInfobaseRowList = infobaseRankRowList
					.get(0);
			assertEquals(firstInfobaseRowList.getRank(), 3358600, DELTA);
			assertEquals(firstInfobaseRowList.getCumulativeRank(), 3358600,
					DELTA);
			assertEquals(firstInfobaseRowList.getPercent(),
					0.02378785421316144, DELTA);

			final InfobaseRankRow lastInfobaseRowList = infobaseRankRowList
					.get(infobaseRankRowList.size() - 1);
			assertEquals(lastInfobaseRowList.getRank(), 157700, DELTA);
			assertEquals(lastInfobaseRowList.getCumulativeRank(), 141189700,
					DELTA);
			assertEquals(lastInfobaseRowList.getPercent(),
					0.0011169370003619244, DELTA);

		}
	}

	@Test
	public void testGetScattergram() throws JsonParseException,
			JsonMappingException, BaseDataApiException, JSONException,
			IOException, URISyntaxException {
		final String stableData = StableData
				.getDataFromFile("Scattergram.json");
		final JSONObject stableDataJson = new JSONObject(stableData);

		expect(
				reportingServiceMock
						.getScattergramReport(anyObject(JSONObject.class)))
				.andReturn(stableDataJson);

		replay(reportingServiceMock);

		final ScattergramModel scattergramModel = dataSourceService
				.getScattergram(null);
		verify(reportingServiceMock);

		assertEquals(scattergramModel.getInsightDescription(), "Insight 25");
		assertEquals(scattergramModel.getInsightID(), "14278104306950060211");
		assertEquals(scattergramModel.getRefQuestion(),
				"Credit Cards - Accepted");
		assertEquals(scattergramModel.getRefResponse(), "Accepted");

		final TimeSettings timeSettings = scattergramModel.getSettings();

		assertEquals(timeSettings.getDateFormat(), "dd/MM/yyyy");
		assertEquals(timeSettings.getTimeFormat(), "h:mm tt");
		assertEquals(timeSettings.getTimeZoneId(), "FLE Standard Time");
		assertEquals(timeSettings.getUtcOffset(), 2);

		final List<ScattergramRow> scattergramData = scattergramModel
				.getScattergramData();

		assertEquals(scattergramData.size(), 70);

		final ScattergramRow firstRow = scattergramData.get(0);

		assertEquals(firstRow.getAge(), "36-45");
		assertEquals(firstRow.getAgeText(), null);
		assertEquals(firstRow.getChartIndex1(), 475);
		assertEquals(firstRow.getChartIndex2(), 100);
		assertEquals(firstRow.getClusterName(), "Working & Active");
		assertEquals(firstRow.getGroupCode(), null);
		assertEquals(firstRow.getGroupName(), null);
		assertEquals(firstRow.getHomeOwnership(), "Owner");
		assertEquals(firstRow.getIb1270().intValue(), 35);
		assertEquals(firstRow.getIncome(), "Middle");
		assertEquals(firstRow.getIndex1(), 598);
		assertEquals(firstRow.getIndex2(), 100);
		assertEquals(firstRow.getKids(), "No Kids");
		assertEquals(firstRow.getMaritalStatus(), "Single");
		assertEquals(firstRow.getNetWorth(), "<$500K");
		assertEquals(firstRow.getQuadrant(), "Strengths");
		assertEquals(firstRow.getReferencePercent(), 0.00441084, DELTA);
		assertEquals(firstRow.getUrbanicity(), "City & Surrounds");

		final ScattergramRow lastRow = scattergramData.get(scattergramData
				.size() - 1);

		assertEquals(lastRow.getAge(), "30-45");
		assertEquals(lastRow.getAgeText(), null);
		assertEquals(lastRow.getChartIndex1(), 1);
		assertEquals(lastRow.getChartIndex2(), 100);
		assertEquals(lastRow.getClusterName(), "Productive Havens");
		assertEquals(lastRow.getGroupCode(), null);
		assertEquals(lastRow.getGroupName(), null);
		assertEquals(lastRow.getHomeOwnership(), "Owner");
		assertEquals(lastRow.getIb1270().intValue(), 69);
		assertEquals(lastRow.getIncome(), "Lowest");
		assertEquals(lastRow.getIndex1(), 1);
		assertEquals(lastRow.getIndex2(), 100);
		assertEquals(lastRow.getKids(), "No Kids");
		assertEquals(lastRow.getMaritalStatus(), "Single");
		assertEquals(lastRow.getNetWorth(), "<$250K");
		assertEquals(lastRow.getQuadrant(), "Opportunities");
		assertEquals(lastRow.getReferencePercent(), 0.00392305, DELTA);
		assertEquals(lastRow.getUrbanicity(), "City & Surrounds");

	}

	@Test
	public void testGetPersonicxPortrait() throws IOException,
			URISyntaxException, JSONException, BaseDataApiException {
		final String stableData = StableData
				.getDataFromFile("PersonicxPortrait.json");
		final JSONObject stableDataJson = new JSONObject(stableData);

		expect(
				reportingServiceMock
						.getPersonicxPortraitReport(anyObject(JSONObject.class)))
				.andReturn(stableDataJson);

		replay(reportingServiceMock);

		final JRBeanCollectionDataSource dataSource = (JRBeanCollectionDataSource) dataSourceService
				.getPersonicxPortrait(null);

		verify(reportingServiceMock);

		final Collection<?> modelCollection = dataSource.getData();
		for (Iterator iterator = modelCollection.iterator(); iterator.hasNext();) {
			final PersonicxPortraitModel personicxPortrait = (PersonicxPortraitModel) iterator
					.next();

			final PersonicxTargetModel target = personicxPortrait.getTarget();

			assertEquals(target.getType(), "Dataset");
			assertEquals(target.getId(), "11538078560090972356");
			assertEquals(target.getQuestion(), "Insight 3");
			assertEquals(target.getResponse(), null);

			final PersonicxReferenceModel reference = personicxPortrait
					.getReference();

			assertEquals(reference.getType(), "National");
			assertEquals(reference.getId(), "0");
			assertEquals(reference.getQuestion(), "National");
			assertEquals(reference.getResponse(), null);

			final List<PersonicxPortraitRow> data = personicxPortrait
					.getPortraitData();

			assertEquals(data.size(), 70);

			final PersonicxPortraitRow firstRow = data.get(0);

			assertEquals(firstRow.getAge(), "30-45");
			assertEquals(firstRow.getAgeText(), "Career");
			assertEquals(firstRow.getBaseCount(), 867897);
			assertEquals(firstRow.getBasePercent(), 0.00370086, DELTA);
			assertEquals(firstRow.getBaseTotal(), 234511935);
			assertEquals(firstRow.getBubbleSize(), "SMALL");
			assertEquals(firstRow.getClusterName(), "Metro Active");
			assertEquals(firstRow.getGroupCode(), "09B");
			assertEquals(firstRow.getGroupName(), "Comfortable Independence");
			assertEquals(firstRow.getHomeOwnership(), "Owner");
			assertEquals(firstRow.getIb1270().intValue(), 56);
			assertEquals(firstRow.getIncome(), "Low");
			assertEquals(firstRow.getIndex(), 437);
			assertEquals(firstRow.getKids(), "No Kids");
			assertEquals(firstRow.getMaritalStatus(), "Single");
			assertEquals(firstRow.getNetWorth(), "<$500K");
			assertEquals(firstRow.getPenetration(), 0.00370086, DELTA);
			assertEquals(firstRow.getProbabilityIndex(), "Highly Likely");
			assertEquals(firstRow.getTargetCount(), 3239);
			assertEquals(firstRow.getTargetPercent(), 0.016195, DELTA);
			assertEquals(firstRow.getTargetTotal(), 200000);
			assertEquals(firstRow.getUrbanicity(), "City & Surrounds");
			assertEquals(firstRow.getUsedInMultipleGroups(), null);
			assertEquals(firstRow.getzScore(), 91.84770392278503, DELTA);

			final PersonicxPortraitRow lastRow = data.get(data.size() - 1);

			assertEquals(lastRow.getAge(), "46-65");
			assertEquals(lastRow.getAgeText(), "Earning");
			assertEquals(lastRow.getBaseCount(), 9820240);
			assertEquals(lastRow.getBasePercent(), 0.04187522, DELTA);
			assertEquals(lastRow.getBaseTotal(), 234511935);
			assertEquals(lastRow.getBubbleSize(), "BIG");
			assertEquals(lastRow.getClusterName(), "Firmly Established");
			assertEquals(lastRow.getGroupCode(), "12B");
			assertEquals(lastRow.getGroupName(), "Comfortable Households");
			assertEquals(lastRow.getHomeOwnership(), "Owner");
			assertEquals(lastRow.getIb1270().intValue(), 17);
			assertEquals(lastRow.getIncome(), "Upper Middle");
			assertEquals(lastRow.getIndex(), 39);
			assertEquals(lastRow.getKids(), "Kids: School-age");
			assertEquals(lastRow.getMaritalStatus(), "Married");
			assertEquals(lastRow.getNetWorth(), "$25K-$999K");
			assertEquals(lastRow.getPenetration(), 0.04187522, DELTA);
			assertEquals(lastRow.getProbabilityIndex(), "Unlikely");
			assertEquals(lastRow.getTargetCount(), 3314);
			assertEquals(lastRow.getTargetPercent(), 0.01657, DELTA);
			assertEquals(lastRow.getTargetTotal(), 200000);
			assertEquals(lastRow.getUrbanicity(), "City & Surrounds");
			assertEquals(lastRow.getUsedInMultipleGroups(), null);
			assertEquals(lastRow.getzScore(), 56.48814975188741, DELTA);

		}
	}

	@Test
	public void testGetAudiencePortraitJSONObject() throws IOException,
			URISyntaxException, BaseDataApiException, JSONException {
		final String stableData = StableData
				.getDataFromFile("AudiencePortrait.json");
		final JSONObject stableDataJson = new JSONObject(stableData);

		expect(reportingServiceMock.getDPAReport(anyObject(JSONObject.class)))
				.andReturn(stableDataJson).times(2);

		replay(reportingServiceMock);

		final JRBeanCollectionDataSource dataSource = (JRBeanCollectionDataSource) dataSourceService
				.getAudiencePortrait(null, null, 0, 0, 0);

		verify(reportingServiceMock);

		final Collection<?> modelCollection = dataSource.getData();

		final AudiencePropensitiesModel audiencePortrait = (AudiencePropensitiesModel) modelCollection
				.iterator().next();

		final String category = audiencePortrait.getCategory();
		assertEquals(category, "Social Media");

		final String reference = audiencePortrait.getReference();
		assertEquals(reference, "US");

		final String target = audiencePortrait.getTarget();
		assertEquals(target, "100K-2");

		final String reportType = audiencePortrait.getReportType();
		assertEquals(reportType, "B");

		final TimeSettings timeSettings = audiencePortrait.getSettings();
		assertEquals(timeSettings.getDateFormat(), "yyyy/MM/dd");
		assertEquals(timeSettings.getTimeFormat(), "h:mm tt");
		assertEquals(timeSettings.getTimeZoneId(), "FLE Standard Time");
		assertEquals(timeSettings.getUtcOffset(), 2);

		final List<AudiencePropensitiesRow> data = audiencePortrait
				.getReportData();

		assertEquals(data.size(), 40);

		final AudiencePropensitiesRow firstRow = data.get(0);

		assertEquals(firstRow.getBoxedBaseCount(), 11374164);
		assertEquals(firstRow.getBoxedBasePercent(), 0.0478, DELTA);
		assertEquals(firstRow.getBoxedBaseTotal(), 237955797);
		assertEquals(firstRow.getBoxedTargetCount(), 10774);
		assertEquals(firstRow.getBoxedTargetPercent(), 0.107850007, DELTA);
		assertEquals(firstRow.getBoxedTargetTotal(), 99898);
		assertEquals(firstRow.getFieldPosition(), 0);
		assertEquals(firstRow.getIndex(), 225);
		assertEquals(firstRow.getQuestionID(), "18");
		assertEquals(firstRow.getQuestionReportText(), "Business Fan");
		assertEquals(firstRow.getQuestionText(),
				"Business Fan - Input Individual");
		assertEquals(firstRow.getResponseReportText(), "20 - Least Likely");
		assertEquals(firstRow.getResponseID(), "341");
		assertEquals(firstRow.getResponseText(),
				"20 Least Likely to be Socially Influenced");
		assertEquals(firstRow.getResponseValue(), "20");
		assertEquals(firstRow.getzScore(), 88.92334871092854, DELTA);
		assertEquals(firstRow.isOverUnderFlag(), false);

		final AudiencePropensitiesRow lastRow = data.get(data.size() - 1);

		assertEquals(lastRow.getBoxedBaseCount(), 13699107);
		assertEquals(lastRow.getBoxedBasePercent(), 0.0576, DELTA);
		assertEquals(lastRow.getBoxedBaseTotal(), 237955797);
		assertEquals(lastRow.getBoxedTargetCount(), 647);
		assertEquals(lastRow.getBoxedTargetPercent(), 0.006476606, DELTA);
		assertEquals(lastRow.getBoxedTargetTotal(), 99898);
		assertEquals(lastRow.getFieldPosition(), 0);
		assertEquals(lastRow.getIndex(), 11);
		assertEquals(lastRow.getQuestionID(), "18");
		assertEquals(lastRow.getQuestionReportText(), "Business Fan");
		assertEquals(lastRow.getQuestionText(),
				"Business Fan - Input Individual");
		assertEquals(lastRow.getResponseReportText(), "01 - Most Likely ");
		assertEquals(lastRow.getResponseID(), "322");
		assertEquals(lastRow.getResponseText(),
				"01 Most Likely to be Socially Influenced");
		assertEquals(lastRow.getResponseValue(), "01");
		assertEquals(lastRow.getzScore(), 69.36818571856544, DELTA);
		assertEquals(lastRow.isOverUnderFlag(), false);
	}

}
