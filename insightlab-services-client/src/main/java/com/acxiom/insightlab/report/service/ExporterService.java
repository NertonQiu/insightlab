package com.acxiom.insightlab.report.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.acxiom.insightlab.report.template.DemographicsTable;

@Service
public class ExporterService {
	public static final String MEDIA_TYPE_EXCEL = "application/vnd.ms-excel";
	public static final String MEDIA_TYPE_PDF = "application/pdf";
	public static final String EXTENSION_TYPE_EXCEL = "xls";
	public static final String EXTENSION_TYPE_PDF = "pdf";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExporterService.class);

	public JasperDesign getJasperDesign(String templateFileName) {
		// 2. Retrieve template
		InputStream reportStream = this.getClass().getResourceAsStream(
				templateFileName);
		try {
			return JRXmlLoader.load(reportStream);
		} catch (JRException e) {
			LOGGER.error("Unable to process download");
			throw new RuntimeException(e);
		}
	}

	public ByteArrayOutputStream generateReport(JRDataSource dataSource,
			String reportType, String templateFileName) {
		return generateReport(dataSource, reportType, templateFileName, null);
	}

	public ByteArrayOutputStream generateReport(JRDataSource dataSource,
			String reportType, JasperDesign jasperDesign) {
		return generateReport(dataSource, reportType, jasperDesign, null);

	}

	public ByteArrayOutputStream generateReport(JRDataSource dataSource,
			String reportType, String templateFileName,
			Map<String, Object> params) {

		final JasperDesign jd = getJasperDesign(templateFileName);
		return generateReport(dataSource, reportType, jd, params);

	}

	public ByteArrayOutputStream generateReport(JRDataSource dataSource,
			String reportType, JasperDesign jasperDesign,
			Map<String, Object> params) {

		try {
			// 4. Compile design to JasperReport
			JasperReport jr = JasperCompileManager.compileReport(jasperDesign);

			// 5. Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and data
			// source
			JasperPrint jp = JasperFillManager.fillReport(jr, params,
					dataSource);

			// 6. Create an output byte stream where data will be written
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// 7. Export report
			export(reportType, jp, baos);

			return baos;

		} catch (JRException jre) {
			LOGGER.error("Unable to process download");
			throw new RuntimeException(jre);
		}
	}

	private void export(String type, JasperPrint jp, ByteArrayOutputStream baos) {

		if (type.equalsIgnoreCase(EXTENSION_TYPE_EXCEL)) {
			// Export to output stream
			exportXls(jp, baos);
		} else if (type.equalsIgnoreCase(EXTENSION_TYPE_PDF)) {
			// Export to output stream
			exportPdf(jp, baos);
		} else {
			throw new RuntimeException("No type set for type " + type);
		}
	}

	private void exportXls(JasperPrint jp, ByteArrayOutputStream baos) {
		// Create a JRXlsExporter instance
		JRXlsExporter exporter = new JRXlsExporter();

		// Here we assign the parameters jp and baos to the exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		// Excel specific parameters
		exporter.setParameter(
				JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET,
				Boolean.FALSE);
		exporter.setParameter(
				JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
				Boolean.TRUE);
		exporter.setParameter(
				JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND,
				Boolean.FALSE);

		try {
			exporter.exportReport();

		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}

	private void exportPdf(JasperPrint jp, ByteArrayOutputStream baos) {
		// Create a JRXlsExporter instance
		JRPdfExporter exporter = new JRPdfExporter();

		// Here we assign the parameters jp and baos to the exporter
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		try {
			exporter.exportReport();

		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}
}
