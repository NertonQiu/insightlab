package com.acxiom.insightlab.report.chart.customizer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

public class ColumnChartCustomizer extends JRAbstractChartCustomizer {

	private static final double MAX_COLUMN_WIDTH_PERCENT = 0.11;

	public void customize(JFreeChart freeChart, JRChart jasperChart) {
		CategoryPlot categoryPlot = freeChart.getCategoryPlot();
		BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
		br.setMaximumBarWidth(MAX_COLUMN_WIDTH_PERCENT);
	}

}
