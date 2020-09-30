package com.teksystems.nextsteps.wiegman;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * GET API tests for https://restcountries.eu/#similar-projects Webservice
 */
public class ApiGetTests
{
	
	
    /**
     * DataDriven HttpGet Tests for number of countries returned
     * @throws IOException 
     */
    @Test (dataProvider = "data-provider-get-countCountry", 
    		dataProviderClass = DataProviderClass.class )
    public void countCountryTests(String input, int expected) throws IOException
    {
    	 int count = SDET_QA_Test_JAVA.countCountriesReturned (input, "name");
    	 
    	 Assert.assertEquals(count, expected, 
    			 "Country count does not match");
    }
    
    
    @Test (dataProvider = "data-provider-get-countryName",
    		dataProviderClass = DataProviderClass.class)
    public void countryNameTests(String input, String expected) throws IOException
    {
    	int count = SDET_QA_Test_JAVA.countCountriesReturned (input, "name");
    	String name  = SDET_QA_Test_JAVA.getCountry(count, input, "name");

    	 Assert.assertEquals(name, expected, 
    			 "Country name does not match");
    }
    
    
    @Test (dataProvider = "data-provider-get-capitalName",
    		dataProviderClass = DataProviderClass.class)
    public void capitalNameTests(String input, String expected) throws IOException
    {
    	String capital = SDET_QA_Test_JAVA.getCapital(input);

    	 Assert.assertEquals(capital, expected, 
    			 "Capital name does not match");
    }
    
    
    @Test (dataProvider = "data-provider-get-code2",
    		dataProviderClass = DataProviderClass.class)
    public void code2Tests(String input, String expected) throws IOException
    {
    	String code2 = SDET_QA_Test_JAVA.getCode2(input);

    	 Assert.assertEquals(code2, expected, 
    			 "Two-Character Code does not match");
    }

    
    @Test (dataProvider = "data-provider-get-code3",
    		dataProviderClass = DataProviderClass.class)
    public void code3Tests(String input, String expected) throws IOException
    {
    	String code3 = SDET_QA_Test_JAVA.getCode3(input);

    	 Assert.assertEquals(code3, expected, 
    			 "Three-Character Code does not match");
    }
    
}
