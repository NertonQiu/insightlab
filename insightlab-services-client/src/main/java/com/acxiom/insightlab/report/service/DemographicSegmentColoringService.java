package com.acxiom.insightlab.report.service;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.acxiom.insightlab.api.model.PersonicxPortraitModel;
import com.acxiom.insightlab.api.model.PersonicxPortraitRow;

public class DemographicSegmentColoringService {

	private static final Color[] COLOR_ARRAY = { new Color(74, 137, 220),
			new Color(160, 212, 104), new Color(215, 112, 173),
			new Color(150, 122, 220), new Color(140, 193, 82),

			new Color(93, 156, 236), new Color(246, 187, 66),
			new Color(218, 68, 83), new Color(101, 109, 120),
			new Color(212, 178, 117),

			new Color(172, 146, 236), new Color(79, 193, 233),
			new Color(72, 207, 173), new Color(74, 110, 81),
			new Color(161, 116, 119),

			new Color(236, 135, 192), new Color(105, 178, 187),
			new Color(36, 152, 195), new Color(255, 206, 84),
			new Color(237, 85, 101) };

	public static Map<String, Color> getSegmentColorMap(
			final PersonicxPortraitModel model,
			final String segmentCharacteristicName) throws IOException {
		Map<String, Color> segmentColorMap = null;

		Color currentColor = null;

		if (segmentCharacteristicName != null) {
			segmentColorMap = new HashMap<String, Color>();

			for (PersonicxPortraitRow row : model.getPortraitData()) {
				String segment = getSegment(segmentCharacteristicName, row);
				if (!segmentColorMap.containsKey(segment)) {
					currentColor = getColor(currentColor);
					segmentColorMap.put(segment, currentColor);
				}
			}
		}
		return segmentColorMap;
	}

	private static Color getColor(Color currentColor) {
		if (currentColor != null) {
			for (int i = 0; i < COLOR_ARRAY.length; i++) {
				if (currentColor.equals(COLOR_ARRAY[i])) {
					currentColor = COLOR_ARRAY[i + 1];
					break;
				}
			}
		} else {
			currentColor = COLOR_ARRAY[0];
		}

		return currentColor;
	}

	private static String getSegment(String segmentCharacteristicName,
			PersonicxPortraitRow row) throws IOException {
		String segment = null;
		if (segmentCharacteristicName.equals("age")) {
			segment = row.getAgeText();
		} else if (segmentCharacteristicName.equals("income")) {
			segment = row.getIncome();
		} else if (segmentCharacteristicName.equals("maritalStatus")) {
			segment = row.getMaritalStatus();
		} else if (segmentCharacteristicName.equals("homeOwnership")) {
			segment = row.getHomeOwnership();
		} else if (segmentCharacteristicName.equals("kids")) {
			segment = row.getKids();
		} else if (segmentCharacteristicName.equals("urbanicity")) {
			segment = row.getUrbanicity();
		} else if (segmentCharacteristicName.equals("netWorth")) {
			segment = row.getNetWorth();
		} else {
			throw new IOException("Wrong segment characteristic name: '"
					+ segmentCharacteristicName + "'!");
		}

		return segment;
	}
}
