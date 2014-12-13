package com.acxiom.insightlab.report.chart.customizer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.charts.fill.JRFillXyzDataset;
import net.sf.jasperreports.charts.util.DefaultXYZDataset;
import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.data.xy.XYDataset;

import com.acxiom.insightlab.api.model.ScattergramRow;

public class ScattergramCustomizer extends JRAbstractChartCustomizer {

	private static final float ZERO_WIDTH = 0;
	private static final int FIRST_ITEM_INDEX = 0;
	private static final double BASELINE_VALUE = 100.0;
	private static final int ALPHA = 153;
	private static final Color BLACK_TRANSPARENT = new Color(0, 0, 0, ALPHA);
	private static final Map<String, Color> ENG_GROUP_COLOR = new HashMap<String, Color>();
	static {
		ENG_GROUP_COLOR.put("Opportunities", new Color(168, 178, 120, ALPHA));
		ENG_GROUP_COLOR.put("Strengths", new Color(206, 153, 113, ALPHA));
		ENG_GROUP_COLOR.put("Threats", new Color(178, 136, 33, ALPHA));
		ENG_GROUP_COLOR.put("Weaknesses", new Color(124, 157, 192, ALPHA));
	}

	private static boolean belongsToWeaknesses(double x, double y) {
		return x <= BASELINE_VALUE && y > BASELINE_VALUE;
	}

	private static boolean belongsToOpportunities(double x, double y) {
		return x <= BASELINE_VALUE && y <= BASELINE_VALUE;
	}

	private static boolean belongsToThreats(double x, double y) {
		return x > BASELINE_VALUE && y > BASELINE_VALUE;
	}

	private static boolean belongsToStrengths(double x, double y) {
		return x > BASELINE_VALUE && y <= BASELINE_VALUE;
	}

	public void customize(JFreeChart freeChart, JRChart jasperChart) {
		XYPlot plot = freeChart.getXYPlot();
		XYBubbleRenderer renderer = (XYBubbleRenderer) plot.getRenderer();
		XYDataset dataset = plot.getDataset();

		final int seriesCount = dataset.getSeriesCount();
		for (int seriesIdex = 0; seriesIdex < seriesCount; seriesIdex++) {

			final double x = dataset.getXValue(seriesIdex, FIRST_ITEM_INDEX);
			final double y = dataset.getYValue(seriesIdex, FIRST_ITEM_INDEX);

			Color color = null;
			if (belongsToOpportunities(x, y)) {
				// O
				color = ENG_GROUP_COLOR.get("Opportunities");
			} else if (belongsToStrengths(x, y)) {
				// S
				color = ENG_GROUP_COLOR.get("Strengths");
			} else if (belongsToWeaknesses(x, y)) {
				// W
				color = ENG_GROUP_COLOR.get("Weaknesses");
			} else if (belongsToThreats(x, y)) {
				// T
				color = ENG_GROUP_COLOR.get("Threats");
			}

			renderer.setBaseOutlineStroke(new BasicStroke(ZERO_WIDTH));

			renderer.setSeriesPaint(seriesIdex, color);

			setLabels(freeChart, jasperChart);
		}
	}

	public void setLabels(JFreeChart freeChart, JRChart jasperChart) {
		XYPlot plot = (XYPlot) freeChart.getPlot();

		JRFillXyzDataset dataset = (JRFillXyzDataset) jasperChart.getDataset();

		DefaultXYZDataset xyzDataset = (DefaultXYZDataset) dataset
				.getCustomDataset();

		List<ScattergramRow> data = (List<ScattergramRow>) getFieldValue("scattergramData");

		for (int i = 0; i < xyzDataset.getSeriesCount(); i++) {

			for (int j = 0; j < xyzDataset.getItemCount(i); j++) {

				double x = xyzDataset.getXValue(i, j);
				double y = xyzDataset.getYValue(i, j);
				int maxXYForBubbles = (Integer) getVariableValue(
						"MAX_X_Y_FOR_BUBBLES", true);
				for (ScattergramRow dataElement : data) {
					if (Math.min(dataElement.getChartIndex1(), maxXYForBubbles) == x
							&& Math.min(dataElement.getChartIndex2(),
									maxXYForBubbles) == y) {
						String s = dataElement.getIb1270() + "";
						XYTextAnnotation a = new XYTextAnnotation(s, x, y);

						a.setFont(new Font("SansSerif", Font.PLAIN, 6));
						a.setPaint(BLACK_TRANSPARENT);

						plot.addAnnotation(a);
					}
				}
			}
		}
	}
}