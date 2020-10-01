package com.teksystems.nextsteps.wiegman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This code reads the JSON response from the REST API Url and returns it
 * as a JSON Object.
 * 
 * NOTE: This is not the most efficient code, and I am in the process
 * of removing all code from this class. 
 */
public class JsonReader {
	
	/**
	 * @param rd An instance of an abstract class for reading character streams.
	 * @return the JSON String that is buffered in the return from the REST call
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an I/O exception of some sort has occurred.
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * @param url A REST-style URL, which typically returns JSON in the response
	 * body
	 * @return The Object form of the returned JSON
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an I/O exception of some sort has occurred.
	 * @throws JSONException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an JSON-related exception of some sort has occurred.
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			String jsonText = readAll(rd);
			//if many JSON Strings, just read the first one.
			if (jsonText.substring(0,1).equals("[")) {
				jsonText = jsonText.substring(1);
			}
			return new JSONObject(jsonText);
		} finally {
			is.close();
		}
	}
}
