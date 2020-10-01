package com.teksystems.nextsteps.wiegman;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is an exercise that allows the user to input the name of a
 * country (partial match allowed) <BR> or the ISO 3166-1 2-letter or 
 * 3-letter country code (exact match.)
 * <BR><BR>
 * The output is either (minimally) the name of
 * the capital city or an explanation of what went wrong. <BR>At no point does
 * it throw an exception and give a stack trace, as that is caught and
 * explained in 'simple English.'
 * <BR><BR>
 * NOTE: To quit the run, type in the country code or name as the String:
 * <code><i>quit</i></code>
 * <BR><BR>
 * The API used is the service provided at:
 * https://restcountries.eu/#api-endpoints-code
 * <BR><BR>
 * Also included are a series of positive and negative tests (included in
 * the `TestNG.xml' suite file) that test the GET method for the API (positive
 * and negative tests).
 * <OL>
 * Assumptions Made
 * <LI>The obscure specification of code was taken to mean the endpoint called
 * <code>code</code>, which is <code>alpha2Code</code> or <code>alpha3Code</code>.
 * These codes (of all 'codes' covered in the API) are most likely to return a
 * unique country, and that is important as the specification says to 
 * "Using the provide REST service, create a program that returns, at minimum, 
 * capital city based on user input for name or code." This means since there can
 * only be one capital city, the idea is to return a unique country name.</LI>
 * <LI>It is assumed that if the user provides an insufficient substring to 
 * uniquely identify a single country, that the output is to be the String `unknown'</LI>
 * <LI>It is further assumed that if the user provides a substring that identifies
 * no known countries, then the output is to be the String `zero'</LI>
 * </OL>
 */
public class SDET_QA_Test_JAVA  extends Fetch {

	private String endpoint;
	private String prompt;

	public static final String BASE_URL =
			"https://restcountries.eu/rest/v2/";

	static Scanner scanner = new Scanner(System.in);

	static SDET_QA_Test_JAVA exercise = new SDET_QA_Test_JAVA();

	/**
	 * The main entry point into the Java code.
	 * 
	 * @param args used for CLI use.
	 * @throws IOException Anomalous or exceptional conditions requiring 
	 * special processing during the execution of this program. 
	 * Signals that an I/O exception of some sort has occurred.
	 */
	public static void main(String[] args) throws IOException {
		exercise.setEndpoint("name");
		while (!"quit".equals(exercise.getEndpoint())) {
			askForChoice ();
			if ("quit" != exercise.getEndpoint()) {
				getResults (exercise.getEndpoint());
			}
		}
		logger.info(NEWLINE + "Please try again, later.");
		scanner.close();
	}

	
	/**
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}


	/**
	 * @param endpoint the endpoint to set
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}


	/**
	 * @return the prompt
	 */
	public String getPrompt() {
		return prompt;
	}


	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	
	/**
	 * Uses the Java Scanner class to allow user to choose to identify the
	 * country by either Name or Code.
	 */
	public static void askForChoice () {
		logger.info(
				"Do you want to input Country Name ('N')"
						+ " or Country Code ('C')?");
		String choose = scanner.nextLine();
		switch (choose.toUpperCase()) {
		case "N": 
			exercise.setEndpoint("name");
			exercise.setPrompt("name");
			break;
		case "C":
			exercise.setEndpoint("alpha");
			exercise.setPrompt("code");
			break;
		default:
			logger.info("Sorry, you needed to enter either "
					+ "Country Name ('N') or Country Code ('C'.)");
			logger.info(NEWLINE + "---" + NEWLINE);
			exercise.setEndpoint("quit");
		}
	}

	
	/**
	 * Gets the REST API response and gives the required information about the 
	 * requested country/ies returned (giving the count) and (if only one found)
	 * then the name of that country. Indirectly calls other code that ultimately
	 * prints information on how well the lookup went and any information (especially
	 * the capital city) found.
	 * 
	 * @param endpoint The endpoint used in the REST URL
	 */
	public static void getResults (String endpoint) {
		if (!endpoint.equals("quit")) {

			logger.info("Please enter your country" + SPACE + exercise.getPrompt() + COLON
					+ SPACE);
			String input = scanner.nextLine();

			if (!input.equals("quit")) {

				int count = countCountriesReturned(input, endpoint);
				getCountry(count, input, endpoint);
				
				} else {
				exercise.setEndpoint("quit");
			}
		} else {
			exercise.setEndpoint("quit");	
		}
	}
	
	
	/**
	 * This code handles requests that are not giving a 200 response status code,
	 * primarily those that return 400, 404, or 500.
	 * @param responseCode The HTTP response status code returned.
	 * @param input The input provided by the user (either country name or code)
	 * @param endpoint The endpoint used in the REST URL 
	 */
	public static void handleBadRequests (String responseCode, String input, String endpoint) {
		
			switch (responseCode) {
			case "400":
				logger.info("That was a bad request. The codes "
						+ "must be two or three characters long.");
				logger.info(NEWLINE + "---" + NEWLINE);
				break;
			case "404":
				logger.info("There was no match. Try again.");
				handleWeirdBackwardCase(input, endpoint);
				break;
			case "500":
				logger.info("Server Error occurred. Please contact support.");
				logger.info(NEWLINE + "---" + NEWLINE);
				break;

			}
	}
	
	
	/**
	 * Prints the "good data" ... country name, capital city, and 2- and 3-char
	 * codes associated with the response body returned from the REST API call.
	 * 
	 * @param json A JSON Object that contains the response body returned
	 */
	public static String printGoodData(JSONObject json) {
		if (json != null) {
			logger.info(NEWLINE + DIVIDER + NEWLINE + NEWLINE);
			logger.info("Your country name is " +  json.get("name"));
			logger.info("with capital city of " +  json.get("capital"));
			logger.info("and ISO 3166-1 codes of " +  json.get("alpha2Code")
			+ " and " + json.get("alpha3Code"));
			logger.info(NEWLINE + DIVIDER + NEWLINE);
		}
		return json.getString("name");
	}
	
	
	/**
	 * This is a special case where the user accidentally enters the Capital City
	 * when prompted to enter the Country Name. This code explains what they did
	 * incorrectly.
	 * @param input The "country" name that is incorrectly entered as the capital
	 * city
	 */
	public static void handleWeirdBackwardCase (String input, String endpoint) {
		if (endpoint.equals("name")) {
			endpoint = "capital";
			JSONObject json = new JSONObject();
			try {
				json = JsonReader.readJsonFromUrl(
						BASE_URL + endpoint + SLASH + input);
			} catch (JSONException | IOException e) {
				// Exception is handled, essentially, below
			}

			String capital = "unknown";
			try {
				capital = (String) json.get(endpoint);
			} catch (JSONException e) {
				//There is no JSONObject Key["capital"} found
			}

			if (!capital.equals("unknown")) {
				logger.info("Wait. You got it backward!");
				logger.info(NEWLINE + DIVIDER + NEWLINE );
				String wrongCountry = (String) json.get("capital");
				String actualCountry = (String) json.get("name");
				logger.info(wrongCountry + " is not the "
						+ "country -- it is the capital of the country of "
						+ actualCountry);
				logger.info(NEWLINE + DIVIDER + NEWLINE);
			} else {
				logger.info(NEWLINE + "---" + NEWLINE);
			}
		}
	}
	
	
	/**
	 * This counts the number of countries returned in the REST response body.
	 * 
	 * @param input The "country" name that is entered by the user
	 * @param endpoint The endpoint used in the REST URL
	 * @return the number of countries returned in the REST response body
	 */
	public static int countCountriesReturned (String input, String endpoint) {
		int count = 0;
		String response = "unknown";

		try {
			response = GETRequest("GET", 
					BASE_URL + endpoint + SLASH + input,
					"null");
		} catch (IOException e) {
			//Ignore this exception. We handle this next.
		}

		String responseCode = HTTPRequest.getResponseCode();

		if (!responseCode.equals("200")){
			handleBadRequests(responseCode, input, endpoint);
		} else {
			count = StringUtils.countMatches(response, "{\"name\":");
		}
		
		return count;
	}
	
	
	/**
	 * This gives the unique one country name or else gives the special keywords 
	 * of `unknown' or `zero' for cases where many or zero countries (respectively)
	 * are returned.
	 * @param count The count of countries returned, as by the 
	 * <code>countCountriesReturned (String input, String endpoint)</code> method
	 * @param input either a unique ISO code or a relatively unique subset of the 
	 * country name
	 * @param endpoint The endpoint used in the REST URL
	 * @return either the unique country name returned or the String `unknown' if
	 * that count is greater than one. In the case where no JSON response occurs
	 * (such as when there is a 404) then the return is the String `zero'.
	 */
	public static String getCountry (int count, String input, String endpoint) {
		String country;
		
		switch (count) {
		case 0:
			// no action, except go through loop again
			country = "zero";
			break;
			
		case 1:
			JSONObject json = new JSONObject();

			try {
				json = JsonReader.readJsonFromUrl(
						BASE_URL + endpoint + SLASH + input);
			} catch (JSONException | IOException e) {
				// Exception is handled, essentially, in the printGoodData method
			}

			country = printGoodData (json);
			break;
			
			default:
				//it is more than one country returned
				logger.info("Sorry, please enter a more unique substring "
						+ "as your value returned " + count + " results");
				logger.info(NEWLINE + "---" + NEWLINE);
				country = "many";
		}
		
		return country;
		
	}
	
	
	/**
	 * This returns the name of the capital city for the given country.
	 * @param input The data entered by the user at the prompt
	 * @return The name of the capital city associated with the country associated
	 * with the REST response body
	 */
	public static String getCapital (String input) {
		String capital = "unknown";

		if (countCountriesReturned(input, "name") == 1) {

			JSONObject json = new JSONObject();

			try {
				json = JsonReader.readJsonFromUrl(
						BASE_URL + "name" + SLASH + input);
				capital = (String) json.get("capital");
			} catch (JSONException | IOException e) {
				// Exception is handled, essentially, in the printGoodData method
			}
		}

		return capital;
	}
	
	/**
	 * This returns the name of the ISO Alpha-2 code for the given country.
	 * @param input The data entered by the user at the prompt
	 * @return The name of the code associated with the country associated
	 * with the REST response body
	 */
	public static String getCode2 (String input) {
		String code2 = "unknown";

		if (countCountriesReturned(input, "name") == 1) {

			JSONObject json = new JSONObject();

			try {
				json = JsonReader.readJsonFromUrl(
						BASE_URL + "name" + SLASH + input);
				code2 = (String) json.get("alpha2Code");
			} catch (JSONException | IOException e) {
				// Exception is handled, essentially, in the printGoodData method
			}
		}

		return code2;
		
	}
	
	/**
	 * This returns the name of the ISO Alpha-3 code for the given country.
	 * @param input The data entered by the user at the prompt
	 * @return The name of the code associated with the country associated
	 * with the REST response body
	 */
	public static String getCode3 (String input) {
		String code3 = "unknown";
		
		if (countCountriesReturned(input, "name") == 1) {

			JSONObject json = new JSONObject();

			try {
				json = JsonReader.readJsonFromUrl(
						BASE_URL + "name" + SLASH + input);
				code3 = (String) json.get("alpha3Code");
			} catch (JSONException | IOException e) {
				// Exception is handled, essentially, in the printGoodData method
			}
		}
		
		return code3;
		
	}
}

