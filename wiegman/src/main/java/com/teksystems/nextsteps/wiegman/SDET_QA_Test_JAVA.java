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
 * a TestNG.xml suite file) that test the GET method for the API.
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
		System.out.println(NEWLINE + "Please try again, later.");
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
	 * 
	 */
	public static void askForChoice () {
		System.out.println(
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
			System.out.println("Sorry, you needed to enter either "
					+ "Country Name ('N') or Country Code ('C'.)");
			System.out.println(NEWLINE + "---" + NEWLINE);
			exercise.setEndpoint("quit");
		}
	}

	
	/**
	 * 
	 */
	public static void getResults (String endpoint) {
		if (!endpoint.equals("quit")) {

			System.out.println("Please enter your country" + SPACE + exercise.getPrompt() + COLON
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
	 * 
	 * @param responseCode
	 * @param input
	 */
	public static void handleBadRequests (String responseCode, String input, String endpoint) {
		
			switch (responseCode) {
			case "400":
				System.out.println("That was a bad request. The codes "
						+ "must be two or three characters long.");
				System.out.println(NEWLINE + "---" + NEWLINE);
				break;
			case "404":
				System.out.println("There was no match. Try again.");
				handleWeirdBackwardCase(input, endpoint);
				break;
			case "500":
				System.out.println("Server Error occurred. Please contact support.");
				System.out.println(NEWLINE + "---" + NEWLINE);
				break;

			}
	}
	
	
	/**
	 * 
	 * @param json
	 */
	public static String printGoodData(JSONObject json) {
		if (json != null) {
			System.out.println(NEWLINE + DIVIDER + NEWLINE + NEWLINE
					+ "Your country name is " +  json.get("name"));
			System.out.println("with capital city of " +  json.get("capital"));
			System.out.println("and ISO 3166-1 codes of " +  json.get("alpha2Code")
			+ " and " + json.get("alpha3Code"));
			System.out.println(NEWLINE + DIVIDER + NEWLINE);
		}
		return json.getString("name");
	}
	
	
	/**
	 * 
	 * @param input
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
				System.out.println("Wait. You got it backward!");
				System.out.println(NEWLINE + DIVIDER + NEWLINE );
				String wrongCountry = (String) json.get("capital");
				String actualCountry = (String) json.get("name");
				System.out.println(wrongCountry + " is not the "
						+ "country -- it is the capital of the country of "
						+ actualCountry);
				System.out.println(NEWLINE + DIVIDER + NEWLINE);
			} else {
				System.out.println(NEWLINE + "---" + NEWLINE);
			}
		}
	}
	
	
	/**
	 * 
	 * @param input
	 * @return
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
	
	
	public static String getCountry (int count, String input, String endpoint) {
		String country = "unknown";
		
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
				System.out.println("Sorry, please enter a more unique substring "
						+ "as your value returned " + count + " results");
				System.out.println(NEWLINE + "---" + NEWLINE);
				country = "many";
		}
		
		return country;
		
	}
	
	
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

