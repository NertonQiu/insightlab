package com.acxiom.insightlab.report.template;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.components.table.BaseColumn;
import net.sf.jasperreports.components.table.Cell;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardColumnGroup;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignElement;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JasperDesign;

public class DemographicsTable {

	/**
	 * The table should be in the first detail band and each column header must
	 * contain only one static text element.
	 */
	private static final int FIRST_ELEMENT = 0;
	private static final String DYNAMIC_TABLE_KEY = "table 1";
	private static final Map<String, String> COLUMN_NAMES_BY_KEY = new HashMap<String, String>();
	private static final int START_INDEX_FOR_DYNAMIC_COLS = 4;

	static {
		COLUMN_NAMES_BY_KEY.put("age", "CLUSTER AGE");
		COLUMN_NAMES_BY_KEY.put("income", "INCOME");
		COLUMN_NAMES_BY_KEY.put("maritalStatus", "MARITAL STATUS");
		COLUMN_NAMES_BY_KEY.put("homeOwnership", "HOME OWNERSHIP");
		COLUMN_NAMES_BY_KEY.put("kids", "KIDS");
		COLUMN_NAMES_BY_KEY.put("urbanicity", "URBANICITY");
		COLUMN_NAMES_BY_KEY.put("netWorth", "NET WORTH");
	}

	public static void excludeNotDisplayedColumns(JasperDesign jasperDesign,
			String[] includedColumnKeys) {
		final StandardTable table = getTable(jasperDesign);
		List<String> includedColumnNamesList = getIncludedColumnNames(includedColumnKeys);

		removeNotIncludedColumnsFromTable(table, includedColumnNamesList);
	}

	private static StandardTable getTable(JasperDesign jasperDesign) {
		final JRSection detailSection = jasperDesign.getDetailSection();
		final JRBand[] detailBandArray = detailSection.getBands();

		final JRBand mainDetailBand = detailBandArray[FIRST_ELEMENT];

		final JRDesignComponentElement tableComponent = (JRDesignComponentElement) mainDetailBand
				.getElementByKey(DYNAMIC_TABLE_KEY);

		return (StandardTable) tableComponent.getComponent();
	}

	private static void removeNotIncludedColumnsFromTable(StandardTable table,
			List<String> includedColumnNamesList) {
		final List<BaseColumn> columnListGroup = table.getColumns();

		int freeSpace = 0;

		StandardColumnGroup columnGroup = (StandardColumnGroup) columnListGroup
				.get(0);
		final List<BaseColumn> columnList = columnGroup.getColumns();

		for (int i = columnList.size() - 1; i >= START_INDEX_FOR_DYNAMIC_COLS; i--) {
			final BaseColumn column = columnList.get(i);
			
			final JRDesignStaticText staticText = (JRDesignStaticText) column
					.getColumnHeader().getElements()[FIRST_ELEMENT];
			final String text = staticText.getText();
			
			if (!includedColumnNamesList.contains(text)) {
				freeSpace += column.getWidth();
			
				columnGroup.removeColumn(column);
			}
		}

		StandardColumn lastColumn = (StandardColumn) columnList.get(columnList
				.size() - 1);

		addRightBorder(lastColumn);

		int groupWidth = stretchColumns(columnList, freeSpace);
		columnGroup.setWidth(groupWidth);
		JRElement[] columnFooterElements = columnGroup.getColumnFooter()
				.getElements();
		for (JRElement jrElement : columnFooterElements) {
			jrElement.setWidth(groupWidth);
		}
	}

	private static int stretchColumns(List<BaseColumn> columnList, int freeSpace) {

		int subcolumnsWidthSum = columnList.get(0).getWidth();
		int widthToAdd = freeSpace / (columnList.size() - 1);

		// do not stretch first column
		for (int i = 1; i < columnList.size(); i++) {

			StandardColumn column = (StandardColumn) columnList.get(i);
			column.setWidth(column.getWidth() + widthToAdd);

			JRElement[] detailElements = column.getDetailCell().getElements();

			if (detailElements.length > 1) {
				for (int j = 0; j < detailElements.length; j++) {
					JRDesignElement designDetailElement = (JRDesignElement) detailElements[j];
					if (designDetailElement instanceof JRDesignChart) {
						designDetailElement.setWidth(designDetailElement
								.getWidth() + widthToAdd);
					} else if (designDetailElement.getX() != 0) {
						designDetailElement.setX(designDetailElement.getX()
								+ widthToAdd);
					}

				}
			} else {
				JRDesignElement designDetailElement = (JRDesignElement) detailElements[0];
				designDetailElement.setWidth(designDetailElement.getWidth()
						+ widthToAdd);
			}

			JRElement[] headerElements = column.getColumnHeader().getElements();
			JRDesignElement headerDesignElement = (JRDesignElement) headerElements[0];
			headerDesignElement.setWidth(headerDesignElement.getWidth()
					+ widthToAdd);

			subcolumnsWidthSum += column.getWidth();
		}
		return subcolumnsWidthSum;
	}

	private static void addRightBorder(StandardColumn lastColumn) {
		Cell detatilCell = lastColumn.getDetailCell();
		final Color lineColor = new Color(128, 128, 128);
		detatilCell.getLineBox().getRightPen().setLineWidth(0.5f);
		detatilCell.getLineBox().getRightPen().setLineColor(lineColor);
		Cell columnHeader = lastColumn.getColumnHeader();
		columnHeader.getLineBox().getRightPen().setLineWidth(0.5f);
		columnHeader.getLineBox().getRightPen().setLineColor(lineColor);
	}

	private static List<String> getIncludedColumnNames(
			String[] includedColumnKeys) {
		List<String> includedColumnNamesList = new ArrayList<String>();
		if (includedColumnKeys != null) {
			for (int i = 0; i < includedColumnKeys.length; i++) {
				final String columnName = COLUMN_NAMES_BY_KEY
						.get(includedColumnKeys[i]);
				includedColumnNamesList.add(columnName);
			}
		}
		return includedColumnNamesList;
	}
}
