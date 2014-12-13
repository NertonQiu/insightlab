package com.acxiom.insightlab.report.chart.customizer;

import java.awt.Color;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;

public class LegendCustomizer extends JRAbstractChartCustomizer {
	

	public void customize(JFreeChart freeChart, JRChart jasperChart) {
		final String segmentCharacteristic = (String) getParameterValue(
				"SEGMENT_CHARACTERISTIC", false);
		
		if (segmentCharacteristic != null) {
			setChartColor(freeChart, segmentCharacteristic);
		}
	}

	private void setChartColor(final JFreeChart freeChart,
			String segmentCharacteristic) {
		final CategoryPlot categoryPlot = freeChart.getCategoryPlot();
		final BarRenderer br = (BarRenderer) categoryPlot.getRenderer();

		final CategoryDataset dataset = categoryPlot.getDataset();
		final int rowCount = dataset.getRowCount();

		
		for (int i = 0; i < rowCount; i++) {
			final Color color = getSegmentColor((String)dataset.getRowKey(i));
			br.setSeriesPaint(i, color);
		}
		freeChart.getLegend().setBorder(0.5, 0.5, 0.5, 0.5);
		freeChart.getLegend().setMargin(20, 0, 0, 0);
	}

	private Color getSegmentColor(String segmentCharacteristic) {

//		final String segment = (String) getFieldValue("_THIS", true);
//		
		final Map<String, Color> segmentColorMap = (Map<String, Color>) getParameterValue(
				"SEGMENT_COLOR_MAP", false);
		if(segmentCharacteristic.equals("age")) {
			segmentCharacteristic = "ageText";
		}
		
		return segmentColorMap.get(segmentCharacteristic);
	}
}
