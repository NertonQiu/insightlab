package com.acxiom.insightlab.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class StableData {

	public static String getDataFromFile(String fileName)
			throws IOException, URISyntaxException {
		final URL fileURL = StableData.class.getClassLoader().getResource(
				fileName);

		final StringBuilder stringBuilder = new StringBuilder();

		BufferedReader bufferedReader = null;
		final URI fileURI = fileURL.toURI();

		try {
			bufferedReader = new BufferedReader(new FileReader(
					new File(fileURI)));
			String str;
			while ((str = bufferedReader.readLine()) != null) {
				stringBuilder.append(str);
			}
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}

		return stringBuilder.toString();
	}
}
