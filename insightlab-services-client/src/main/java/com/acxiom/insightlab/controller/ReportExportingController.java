package com.acxiom.insightlab.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acxiom.insightlab.api.BaseDataApiException;
import com.acxiom.insightlab.api.model.PersonicxPortraitModel;
import com.acxiom.insightlab.api.model.ScattergramModel;
import com.acxiom.insightlab.controller.helper.ReportExportingHelper;
import com.acxiom.insightlab.controller.helper.WebHelper;
import com.acxiom.insightlab.exception.GlobalExceptionHandler;
import com.acxiom.insightlab.report.service.DataSourceService;
import com.acxiom.insightlab.report.service.DemographicSegmentColoringService;
import com.acxiom.insightlab.report.service.ExporterService;
import com.acxiom.insightlab.report.template.DemographicsTable;
import com.acxiom.insightlab.service.ServletUtils;

@Controller
@RequestMapping("/exportReport")
public class ReportExportingController extends GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReportExportingController.class);
	@Autowired
	private ExporterService exporterService;

	@Autowired
	private DataSourceService dataSourceService;

	// produces = "application/pdf"
	@RequestMapping(value = "/scorecard")
	public void scorecard(@RequestParam(required = true) String type,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addUserName(requestParams);
		WebHelper.addTenantId(requestParams);

		// 2. add additional and remove internal params.
		// TODO

		JRDataSource dataSource = dataSourceService.getScorecard(requestParams);

		ByteArrayOutputStream outputStream = exporterService.generateReport(
				dataSource, type, "/Scorecard.jrxml");

		ReportExportingHelper.sendReport(outputStream, type, "Scorecard."
				+ type, response);
	}

	// produces = "application/pdf"
	@RequestMapping(value = "/personicxPortrait")
	public void personicxPortrait(
			@RequestParam(value = "targettype", required = true) final String targetType,
			@RequestParam(value = "targetid", required = true) final String targetId,
			@RequestParam(value = "referencetype", required = true) final String referenceType,
			@RequestParam(value = "referenceid", required = true) final String referenceId,
			@RequestParam(required = true) String type,
			@RequestParam(required = false) String portrait,
			@RequestParam(required = false) String includedColumns,
			@RequestParam(required = false) String segmentCharacteristic,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		final JSONObject requestParams = ServletUtils.getParams(request);

		final PersonicxPortraitModel personicxPortraitModel = dataSourceService
				.getPersonicxPortraitModel(requestParams);

		final JRDataSource dataSource = new JRBeanCollectionDataSource(
				Arrays.asList(personicxPortraitModel));

		final Map<String, Color> segmentColorMap = DemographicSegmentColoringService
				.getSegmentColorMap(personicxPortraitModel,
						segmentCharacteristic);

		Map<String, Color> sorterdSegmentColorMap = null;
		if (segmentColorMap != null) {
			sorterdSegmentColorMap = new TreeMap<String, Color>(segmentColorMap);
		}

		final Map<String, Object> reportParams = new HashMap<String, Object>();

		reportParams.put("PORTRAIT", portrait);
		reportParams.put("SEGMENT_CHARACTERISTIC", segmentCharacteristic);
		reportParams.put("SEGMENT_COLOR_MAP", sorterdSegmentColorMap);

		JasperDesign jasperDesign = exporterService
				.getJasperDesign("/PersonicxPortrait.jrxml");

		String[] includedColumnsArray = null;
		if (includedColumns != null) {
			includedColumnsArray = includedColumns.split(",");
		}
		DemographicsTable.excludeNotDisplayedColumns(jasperDesign,
				includedColumnsArray);

		// 4. export data to baos
		ByteArrayOutputStream outputStream = exporterService.generateReport(
				dataSource, type, jasperDesign, reportParams);

		ReportExportingHelper.sendReport(outputStream, type,
				"PersonicxPortrait." + type, response);

	}

	// produces = "application/pdf"
	@RequestMapping(value = "/scattergram")
	public void scattergram(@RequestParam(required = true) String type,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		JSONObject requestParams = ServletUtils.getParams(request);

		ScattergramModel scattergramModel = dataSourceService
				.getScattergram(requestParams);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				Arrays.asList(scattergramModel));

		double maxReferencePercent = Collections.max(
				scattergramModel.getScattergramData()).getReferencePercent();
		Map<String, Object> reportParams = new HashMap<String, Object>();

		reportParams.put("MAX_REFERENCE_PERCENT", maxReferencePercent);

		ByteArrayOutputStream outputStream = exporterService.generateReport(
				dataSource, type, "/Scattergram.jrxml", reportParams);

		ReportExportingHelper.sendReport(outputStream, type, "Scattergram."
				+ type, response);

	}

	// produces = "application/pdf"
	@RequestMapping(value = "/audiencePortrait")
	public void audiencePortrait(
			@RequestParam(required = true) String type,
			@RequestParam(required = true) int overcount,
			@RequestParam(required = true) int undercount,
			@RequestParam(required = true) String category,
			@RequestParam(required = true, value = "insightid") String insightId,
			@RequestParam(required = true) int question,

			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		JRDataSource dataSource = dataSourceService.getAudiencePortrait(
				insightId, category, question, undercount, overcount);

		ByteArrayOutputStream outputStream = exporterService.generateReport(
				dataSource, type, "/AudiencePortrait.jrxml");

		ReportExportingHelper.sendReport(outputStream, type,
				"AudiencePortrait." + type, response);

	}

	// produces = "application/pdf"
	@RequestMapping(value = "/gains")
	public void gains(@RequestParam(required = true) String type,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addUserName(requestParams);
		WebHelper.addTenantId(requestParams);

		JRDataSource dataSource = dataSourceService.getGains(requestParams);

		ByteArrayOutputStream outputStream = exporterService.generateReport(
				dataSource, type, "/Gains.jrxml");

		ReportExportingHelper.sendReport(outputStream, type, "Gains." + type,
				response);

	}

	// produces = "application/pdf"
	@RequestMapping(value = "/validationGains")
	public void validationGains(@RequestParam(required = true) String type,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addUserName(requestParams);
		WebHelper.addTenantId(requestParams);

		// 2. add additional and remove internal params.
		// TODO

		JRDataSource dataSource = dataSourceService
				.getValidationGains(requestParams);

		ByteArrayOutputStream outputStream = exporterService.generateReport(
				dataSource, type, "/ValidationGains.jrxml");

		ReportExportingHelper.sendReport(outputStream, type, "ValidationGains."
				+ type, response);

	}

	// produces = "application/pdf"
	@RequestMapping(value = "/infobase")
	public void infobase(@RequestParam(required = true) String type,
			final HttpServletRequest request, final HttpServletResponse response)
			throws JSONException, BaseDataApiException, IOException {

		JSONObject requestParams = ServletUtils.getParams(request);
		WebHelper.addUserName(requestParams);
		WebHelper.addTenantId(requestParams);

		// 2. add additional and remove internal params.
		// TODO

		JRDataSource dataSource = dataSourceService.getInfobase(requestParams);

		ByteArrayOutputStream outputStream = exporterService.generateReport(
				dataSource, type, "/Infobase.jrxml");

		ReportExportingHelper.sendReport(outputStream, type,
				"Infobase." + type, response);

	}
}
