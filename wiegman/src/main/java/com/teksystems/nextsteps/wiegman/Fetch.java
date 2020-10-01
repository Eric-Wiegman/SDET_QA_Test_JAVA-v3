package com.teksystems.nextsteps.wiegman;

import java.io.IOException;


/**
 * This class defines the Fetch API calls, subclasses of the
 * HTTPRequest class. It defines the POST, GET, PUT, and DELETE
 * methods.
 *
 */
public class Fetch extends HTTPRequest {
	/**
	 * This is just here for some quick unit test, if we write them
	 * 
	 * @param args used for CLI use.
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an I/O exception of some sort has occurred.
	 */
	public static void main(String[] args) throws IOException {
		//This is just here for some quick unit test, if we write them
	}

	/**
	 * @param httpMethod Sets the method for the URL request.
	 * @param fetchUrl URL used to generate a Fetch API call.
	 * @param params The JSON String that defines the data that is being 
	 * Posted, Patched, or Put.
	 * @return the body of the response.
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an I/O exception of some sort has occurred.
	 */
	static String POSTRequest (
			String httpMethod, 
			String fetchUrl, 
			String params) throws IOException {
		HTTPRequest.runRequest(httpMethod, fetchUrl, params);
		return getResponseBody();
	}

	/**
	 * @param httpMethod Sets the method for the URL request.
	 * @param fetchUrl URL used to generate a Fetch API call.
	 * @param params The JSON String that defines the data that is being 
	 * Posted, Patched, or Put. As is does not apply for DELETE, a "null" is 
	 * passed.
	 * @return the body of the response.
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program.  
	 * Signals that an I/O exception of some sort has occurred.
	 */
	static String DELETERequest(
			String httpMethod,
			String fetchUrl,
			String params) throws IOException {
		HTTPRequest.runRequest(httpMethod, fetchUrl, params);
		return getResponseBody();
	}

	/**
	 * @param httpMethod Sets the method for the URL request.
	 * @param fetchUrl URL used to generate a Fetch API call.
	 * @param params The JSON String that defines the data that is being 
	 * Posted, Patched, or Put.
	 * @return the body of the response. 
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an I/O exception of some sort has occurred.
	 */
	static String PUTRequest(
			String httpMethod,
			String fetchUrl,
			String params) throws IOException {
		HTTPRequest.runRequest(httpMethod, fetchUrl, params);
		return getResponseBody();
	}
	
	
	/**
	 * @param httpMethod Sets the method for the URL request.
	 * @param fetchUrl URL used to generate a Fetch API call.
	 * @param params The JSON String that defines the data that is being 
	 * Posted, Patched, or Put. As is does not apply for GET, a "null" is passed.
	 * @return the body of the response.
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an I/O exception of some sort has occurred.
	 */
	static String GETRequest (
			String httpMethod, 
			String fetchUrl, 
			String params) throws IOException {
		HTTPRequest.runRequest(httpMethod, fetchUrl, params);	
		return getResponseBody();
	}
}