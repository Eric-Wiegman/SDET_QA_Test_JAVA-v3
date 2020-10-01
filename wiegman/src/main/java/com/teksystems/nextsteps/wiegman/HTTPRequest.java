package com.teksystems.nextsteps.wiegman;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 

/**
 * This class is the superclass to be used to define the HTTP
 * Methods of POST, GET, PUT, and DELETE
 *
 */
public class HTTPRequest implements CharConsts {
	public static final Logger logger = LogManager.getLogger(HTTPRequest.class);

	/**
	 * The HTTP Response Code, such as 201 or 404.
	 */
	private static String responseCode;
	
	/**
	 * The HTTP Response Message, such as "Created" or "Not Found".
	 */
	private static String responseMsg;
	
	/**
	 * The Body of the HTTP Response, in JSON.
	 */
	private static String responseBody;
	
	/**
	 * @return the responseCode
	 */
	public static String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public static void setResponseCode(String responseCode) {
		HTTPRequest.responseCode = responseCode;
	}

	/**
	 * @return the responseMsg
	 */
	public static String getResponseMsg() {
		return responseMsg;
	}

	/**
	 * @param responseMsg the responseMsg to set
	 */
	public static void setResponseMsg(String responseMsg) {
		HTTPRequest.responseMsg = responseMsg;
	}

	/**
	 * @return the responseBody
	 */
	public static String getResponseBody() {
		return responseBody;
	}

	/**
	 * @param responseBody the responseBody to set
	 */
	public static void setResponseBody(String responseBody) {
		HTTPRequest.responseBody = responseBody;
	}
	

	/**
	 * @param requestType Sets the method for the URL request.
	 * @param fetchUrl URL used to generate a Fetch API call.
	 * @param params The JSON String that defines the data that is being Posted or Put. If the requestType
	 * does not support params, a "null" is passed.
	 * @throws IOException Anomalous or exceptional conditions requiring special processing - 
	 * 	during the execution of this program. Signals that an I/O exception of some sort has occurred.
	 */
	public static void runRequest(
			String requestType, 
			String fetchUrl, 
			String params) throws IOException {

		if(!params.equals("null")) logger.info("");
		URL obj = new URL(fetchUrl);
		HttpURLConnection httpCon = (HttpURLConnection) obj.openConnection();
		httpCon.setRequestMethod(requestType);

		httpCon.setRequestProperty("Content-Type", "application/json");
		httpCon.setDoOutput(true);

		switch (requestType) {
		case "PUT":
			//fall through
		case "POST":
			OutputStream os = httpCon.getOutputStream();
			os.write(params.getBytes());
			os.flush();
			os.close();
			default:
				//no op
		}

		int responseCode = httpCon.getResponseCode();
		String responseMsg = httpCon.getResponseMessage();
		
		setResponseCode(Integer.toString(responseCode));
		setResponseMsg(responseMsg);

		BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		String responseString = response.toString();
		setResponseBody(responseString);
	}

/**
 * Constructor for this superclass.
 */
public HTTPRequest() {
	super();
}

}