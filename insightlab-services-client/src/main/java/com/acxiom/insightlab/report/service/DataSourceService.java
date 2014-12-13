package com.acxiom.insightlab.report.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.model.AudiencePropensitiesModel;
import com.acxiom.insightlab.api.model.GainBean;
import com.acxiom.insightlab.api.model.InfobaseRankModel;
import com.acxiom.insightlab.api.model.InfobaseRankWrapper;
import com.acxiom.insightlab.api.model.ModelReportBean;
import com.acxiom.insightlab.api.model.PersonicxPortraitModel;
import com.acxiom.insightlab.api.model.ScattergramModel;
import com.acxiom.insightlab.api.service.ModelingService;
import com.acxiom.insightlab.api.service.ReportingService;
import com.acxiom.insightlab.api.service.util.JSONToBean;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class DataSourceService {

	@Autowired
	private ModelingService modelingService;
	@Autowired
	private ReportingService reportingService;

	private static final double BASELINE_MAX_VALUE = 1.0;

	// service
	public JRDataSource getScorecard(JSONObject params) throws JSONException,
			BaseDataApiException, JsonParseException, JsonMappingException,
			IOException {

		JSONObject json = modelingService.fetchModelReport(params);

		ModelReportBean modelReportBean = JSONToBean.getBean(json,
				ModelReportBean.class);

		return new JRBeanCollectionDataSource(Arrays.asList(modelReportBean));
	}

	public JRDataSource getGains(JSONObject params) throws JSONException,
			BaseDataApiException, JsonParseException, JsonMappingException,
			IOException {

		JSONObject json = modelingService.fetchModelReport(params);

		ModelReportBean modelReportBean = JSONToBean.getBean(json,
				ModelReportBean.class);

		// baseline points

		final GainBean baselineFirstPoint = new GainBean();
		baselineFirstPoint.setReportSource("BASELINE");
		final GainBean minGainBean = Collections.min(modelReportBean.getGain(),
				new GainBean.ComparatorByCumulativeTotalPercent());
		final double baselineMinValue = minGainBean.getCumulativeTotalPercent();
		baselineFirstPoint.setCumulativeTotalPercent(baselineMinValue);
		baselineFirstPoint.setLiftCurve(BASELINE_MAX_VALUE);
		modelReportBean.getGain().add(baselineFirstPoint);

		final GainBean baselineSecondPoint = new GainBean();
		baselineSecondPoint.setReportSource("BASELINE");
		baselineSecondPoint.setCumulativeTotalPercent(BASELINE_MAX_VALUE);
		baselineSecondPoint.setCumulativeTargetPercent(BASELINE_MAX_VALUE);
		baselineSecondPoint.setLiftCurve(BASELINE_MAX_VALUE);
		modelReportBean.getGain().add(baselineSecondPoint);

		// zero point
		final GainBean zeroBean = new GainBean();
		zeroBean.setReportSource("ZERO");
		modelReportBean.getGain().add(0, zeroBean);

		return new JRBeanCollectionDataSource(Arrays.asList(modelReportBean));
	}

	public JRDataSource getValidationGains(JSONObject params)
			throws JSONException, BaseDataApiException, JsonParseException,
			JsonMappingException, IOException {

		JSONObject json = modelingService.fetchModelReport(params);

		ModelReportBean modelReportBean = JSONToBean.getBean(json,
				ModelReportBean.class);

		// fake gain.

		// ZERO, VALIDATION_ZERO, BASELINE, CUMULATIVE_BASELINE - fake report
		// types
		// but they need because they should not be present at the table (in
		// charts only).

		GainBean baseLinePoint = new GainBean();
		baseLinePoint.setReportSource("BASELINE");
		baseLinePoint.setLiftCurve(BASELINE_MAX_VALUE);
		final GainBean minGainBean = Collections.min(modelReportBean.getGain(),
				new GainBean.ComparatorByCumulativeTotalPercent());
		final double baselineMinValue = minGainBean.getCumulativeTotalPercent();
		baseLinePoint.setCumulativeTotalPercent(baselineMinValue);

		modelReportBean.getGain().add(baseLinePoint);

		baseLinePoint = new GainBean();
		baseLinePoint.setReportSource("BASELINE");
		baseLinePoint.setLiftCurve(BASELINE_MAX_VALUE);
		baseLinePoint.setCumulativeTargetPercent(BASELINE_MAX_VALUE);
		baseLinePoint.setCumulativeTotalPercent(BASELINE_MAX_VALUE);
		modelReportBean.getGain().add(baseLinePoint);

		GainBean zeroGain = new GainBean();
		zeroGain.setReportSource("VALIDATION_ZERO");
		modelReportBean.getGain().add(0, zeroGain);

		zeroGain = new GainBean();
		zeroGain.setReportSource("ZERO");
		modelReportBean.getGain().add(0, zeroGain);

		return new JRBeanCollectionDataSource(Arrays.asList(modelReportBean));
	}

	public JRDataSource getInfobase(JSONObject requestParams)
			throws BaseDataApiException, JSONException, JsonParseException,
			JsonMappingException, IOException {
		// load
		JSONObject json = modelingService.fetchModelReport(requestParams);
		// tranform to object
		ModelReportBean modelReportBean = JSONToBean.getBean(json,
				ModelReportBean.class);

		InfobaseRankModel infobaseRankModel = modelReportBean.getIbRank();
		InfobaseRankWrapper infobaseRankWrapper = new InfobaseRankWrapper(
				infobaseRankModel);

		// create return result
		return new JRBeanCollectionDataSource(
				Arrays.asList(infobaseRankWrapper));
	}

	public ScattergramModel getScattergram(JSONObject requestParams)
			throws BaseDataApiException, JSONException, JsonParseException,
			JsonMappingException, IOException {
		// load
		JSONObject json = reportingService.getScattergramReport(requestParams);

		// tranform to object
		ScattergramModel scattergramModel = JSONToBean.getBean(json,
				ScattergramModel.class);

		return scattergramModel;
	}

	public JRDataSource getPersonicxPortrait(JSONObject requestParams)
			throws BaseDataApiException, JSONException, JsonParseException,
			JsonMappingException, IOException {

		final PersonicxPortraitModel personicxPortraitModel = getPersonicxPortraitModel(requestParams);
		// create return result
		return new JRBeanCollectionDataSource(
				Arrays.asList(personicxPortraitModel));
	}

	public PersonicxPortraitModel getPersonicxPortraitModel(
			final JSONObject requestParams) throws BaseDataApiException,
			JSONException, JsonParseException, JsonMappingException,
			IOException {

		final JSONObject json = reportingService
				.getPersonicxPortraitReport(requestParams);

		final PersonicxPortraitModel personicxPortraitModel = JSONToBean
				.getBean(json, PersonicxPortraitModel.class);

		return personicxPortraitModel;
	}

	public JRDataSource getAudiencePortrait(String insightId, String category,
			int question, int undercount, int overcount)
			throws BaseDataApiException, JSONException, JsonParseException,
			JsonMappingException, IOException {
		JSONObject buterflyChartParams = new JSONObject();
		buterflyChartParams.put("insightid", new String[] { insightId });
		buterflyChartParams.put("category", new String[] { category });
		buterflyChartParams.put("undercount", new String[] { undercount + "" });
		buterflyChartParams.put("overcount", new String[] { overcount + "" });

		JSONObject butterflyChartJson = reportingService
				.getDPAReport(buterflyChartParams);
		AudiencePropensitiesModel modelForButterflyChart = JSONToBean.getBean(
				butterflyChartJson, AudiencePropensitiesModel.class);

		JSONObject tableParams = new JSONObject();

		tableParams.put("insightid", new String[] { insightId });
		tableParams.put("category", new String[] { category });
		tableParams.put("question", new String[] { question + "" });

		JSONObject tableJson = reportingService.getDPAReport(tableParams);
		AudiencePropensitiesModel modelForTable = JSONToBean.getBean(tableJson,
				AudiencePropensitiesModel.class);

		// create return result
		return new JRBeanCollectionDataSource(Arrays.asList(
				modelForButterflyChart, modelForTable));
	}
}
