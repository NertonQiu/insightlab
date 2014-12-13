package com.acxiom.insightlab.report.chart.customizer;

import java.awt.Color;
import java.awt.Font;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.ui.TextAnchor;

public class StackedBarChartCustomizer extends JRAbstractChartCustomizer {

	private static final int CATEGORY_AXIS_OFFSET = -1;
	private static final int FONT_SIZE = 8;

	private static final double MAXIMUM_BAR_WIDTH = 0.027;
	private static final double ITEM_MARGIN = 0.01;

	public void customize(JFreeChart freeChart, JRChart jasperChart) {
		final CategoryPlot categoryPlot = freeChart.getCategoryPlot();

		final ValueAxis valueAxis = categoryPlot.getRangeAxis();

		// fixes bug with white bars that overlap category axis.
		valueAxis.setLowerBound(CATEGORY_AXIS_OFFSET);

		setLabelFont(categoryPlot);
		setBarWidthAndMargin(categoryPlot);
	}

	private void setLabelFont(final CategoryPlot categoryPlot) {
		final BarRenderer barRenderer = (BarRenderer) categoryPlot
				.getRenderer();

		final Font font = new Font("Default", 0, FONT_SIZE);

		final ItemLabelPosition position = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT,
				TextAnchor.CENTER, 0.0D);

		barRenderer.setBasePositiveItemLabelPosition(position);
		barRenderer.setBaseItemLabelFont(font);

		barRenderer.setBaseItemLabelPaint(new Color(102, 102, 102));
	}

	private void setBarWidthAndMargin(final CategoryPlot categoryPlot) {
		final BarRenderer barRenderer = (BarRenderer) categoryPlot
				.getRenderer();
		barRenderer.setMinimumBarLength(0.0);
		barRenderer.setMaximumBarWidth(MAXIMUM_BAR_WIDTH);
		barRenderer.setItemMargin(ITEM_MARGIN);
		categoryPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		final CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
		categoryAxis.setUpperMargin(ITEM_MARGIN);
		categoryAxis.setLowerMargin(ITEM_MARGIN);
	}
}