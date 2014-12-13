package com.acxiom.insightlab.report.chart.customizer;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

public class SingleBarCustomizer extends JRAbstractChartCustomizer {
	private static final Color LIGHT_GRAY = new Color(241, 241, 241);
	private static final int VALUABLE_INDEX_POS = 1;
	private static final int LESS_THAN_100_INDEX_POS = 2;
	private static final int INDEX_CHART_SERIES_COUNT = 4;
	private static final int BASE_INDEX = 100;

	public void customize(JFreeChart freeChart, JRChart jasperChart) {
		CategoryPlot categoryPlot = freeChart.getCategoryPlot();

		categoryPlot.setDomainGridlinesVisible(false);
		categoryPlot.setRangeGridlinesVisible(false);
		categoryPlot.setDomainCrosshairVisible(false);
		categoryPlot.setRangeCrosshairVisible(false);

		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		categoryAxis.setAxisLineVisible(false);
		categoryAxis.setMinorTickMarksVisible(false);
		categoryAxis.setTickLabelsVisible(false);
		categoryAxis.setVisible(false);

		BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
		br.setMaximumBarWidth(.35); // set maximum width to 35% of chart
		br.setMinimumBarLength(0.0);

		final String segmentCharacteristic = (String) getParameterValue(
				"SEGMENT_CHARACTERISTIC", true);
		if (segmentCharacteristic !=null) {
			setChartColor(freeChart, segmentCharacteristic);
		}
		
	}

	private void setChartColor(final JFreeChart freeChart, String segmentCharacteristic) {
		final CategoryPlot categoryPlot = freeChart.getCategoryPlot();
		final BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
		final int indexValue = (Integer) getFieldValue("index");
		final int seriesIndexToColorize = indexValue >= BASE_INDEX ? VALUABLE_INDEX_POS
				: LESS_THAN_100_INDEX_POS;

		final Color segmentColor = getSegmentColor(segmentCharacteristic);

		final CategoryDataset dataset = categoryPlot.getDataset();
		final int rowCount = dataset.getRowCount();

		if (canBeColored(rowCount)) {
			for (int i = 0; i < rowCount; i++) {
				br.setSeriesPaint(i, LIGHT_GRAY);
			}
			br.setSeriesPaint(seriesIndexToColorize, segmentColor);
		}
	}

	private boolean canBeColored(final int rowCount) {
		return rowCount == INDEX_CHART_SERIES_COUNT;
	}

	private Color getSegmentColor(String segmentCharacteristic) {
		if(segmentCharacteristic.equals("age")) {
			segmentCharacteristic = "ageText";
		}
		final String segment = (String) getFieldValue(segmentCharacteristic);

		final Map<String, Color> segmentColorMap = (Map<String, Color>) getParameterValue(
				"SEGMENT_COLOR_MAP", true);
		
		return segmentColorMap.get(segment);
	}
}
