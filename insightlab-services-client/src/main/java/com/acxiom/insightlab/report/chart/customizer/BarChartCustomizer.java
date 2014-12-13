package com.acxiom.insightlab.report.chart.customizer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.renderer.category.BarRenderer;

public class BarChartCustomizer extends JRAbstractChartCustomizer {

	private static final double MAXIMUM_BAR_WIDTH = 0.016;
	private static final double ITEM_MARGIN = 0.01;

	public void customize(JFreeChart freeChart, JRChart jasperChart) {
		final CategoryPlot categoryPlot = freeChart.getCategoryPlot();

		final BarRenderer barRenderer = (BarRenderer) categoryPlot
				.getRenderer();

		barRenderer.setMaximumBarWidth(MAXIMUM_BAR_WIDTH);
		barRenderer.setItemMargin(ITEM_MARGIN);
		categoryPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		categoryAxis.setUpperMargin(ITEM_MARGIN);
		categoryAxis.setLowerMargin(ITEM_MARGIN);
	}

}
