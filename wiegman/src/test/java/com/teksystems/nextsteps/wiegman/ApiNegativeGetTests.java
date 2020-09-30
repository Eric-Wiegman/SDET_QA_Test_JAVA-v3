package com.teksystems.nextsteps.wiegman;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Negative GET API tests for 
 * https://restcountries.eu/#similar-projects Webservice
 */
public class ApiNegativeGetTests
{
	
	
    /**
     * DataDriven HttpGet Tests for number of countries returned
     * @throws IOException 
     */
    @Test (dataProvider = "data-provider-get-neg-countCountry", 
    		dataProviderClass = DataProviderClass.class )
    public void countCountryNegTests(String input, int expected) throws IOException
    {
    	 int count = SDET_QA_Test_JAVA.countCountriesReturned (input, "name");
    	 
    	 Assert.assertEquals(count, expected, 
    			 "Country count does not match");
    }
    
    
    @Test (dataProvider = "data-provider-get-neg-countryName",
    		dataProviderClass = DataProviderClass.class)
    public void countryNameNegTests(String input, String expected) throws IOException
    {
    	int count = SDET_QA_Test_JAVA.countCountriesReturned (input, "name");
    	String name  = SDET_QA_Test_JAVA.getCountry(count, input, "name");

    	 Assert.assertEquals(name, expected, 
    			 "Country name does not match");
    }
    
    
    @Test (dataProvider = "data-provider-get-neg-capitalName",
    		dataProviderClass = DataProviderClass.class)
    public void capitalNameNegTests(String input, String expected) throws IOException
    {
    	String capital = SDET_QA_Test_JAVA.getCapital(input);

    	 Assert.assertEquals(capital, expected, 
    			 "Capital name does not match");
    }
    
    
    @Test (dataProvider = "data-provider-get-neg-code2",
    		dataProviderClass = DataProviderClass.class)
    public void code2NegTests(String input, String expected) throws IOException
    {
    	String code2 = SDET_QA_Test_JAVA.getCode2(input);

    	 Assert.assertNotEquals(code2, expected, 
    			 "Two-Character Code does not match");
    }

    
    @Test (dataProvider = "data-provider-get-neg-code3",
    		dataProviderClass = DataProviderClass.class)
    public void code3NegTests(String input, String expected) throws IOException
    {
    	String code3 = SDET_QA_Test_JAVA.getCode3(input);

    	 Assert.assertNotEquals(code3, expected, 
    			 "Three-Character Code does not match");
    }
    
}
