package com.teksystems.nextsteps.wiegman;

import org.testng.annotations.DataProvider;

/**
 * The class containing data used to drive the tests.
 * @author ewiegman
 *
 */
public class DataProviderClass 
{
    @DataProvider(name = "data-provider-get-countCountry")
    public static Object[][] dataProviderGetCountMethod() 
    {
        return new Object[][] { 
        	{"fra", 2},
        	{"france", 1},
        	{"a", 240},
          	{"on", 53},
          	{"aust", 2},
          	{"Australia", 1},
          	{"Coco", 1}
        };
    }
    
    
    @DataProvider(name = "data-provider-get-countryName")
    public static Object[][] dataProviderGetNameMethod() 
    {
        return new Object[][] { 
        	{"fra", "many"},
        	{"france", "France"},
        	{"a", "many"},
          	{"on", "many"},
          	{"aust", "many"},
          	{"Australia", "Australia"},
          	{"Coco", "Cocos (Keeling) Islands"}
        };
    }
    
    
    @DataProvider(name = "data-provider-get-capitalName")
    public static Object[][] dataProviderGetCapitalMethod() 
    {
        return new Object[][] { 
        	{"fra", "unknown"},
        	{"france", "Paris"},
        	{"a", "unknown"},
          	{"on", "unknown"},
          	{"aust", "unknown"},
          	{"Australia", "Canberra"},
          	{"Coco", "West Island"}
        };
    }
    
    
    @DataProvider(name = "data-provider-get-code2")
    public static Object[][] dataProviderGetCode2Method() 
    {
        return new Object[][] { 
        	{"france", "FR"},
          	{"Australia", "AU"},
          	{"cocos", "CC"},
        };
    }
    
        @DataProvider(name = "data-provider-get-code3")
        public static Object[][] dataProviderGetCode3Method() 
        {
            return new Object[][] { 
            	{"France", "FRA"},
            	{"Germany", "DEU"},
              	{"Australia", "AUS"},
              	{"cocos", "CCK"}
            };
        }
        
        
        
        @DataProvider(name = "data-provider-get-neg-countCountry")
        public static Object[][] dataProviderGetCountNegMethod() 
        {
            return new Object[][] { 
            	{"zzzz", 0},
            };
        }
        
        
        @DataProvider(name = "data-provider-get-neg-countryName")
        public static Object[][] dataProviderGetNameNegMethod() 
        {
            return new Object[][] { 
              	{"zzzz", "zero"},
              	{"Paris", "zero"}
            };
        }
        
       
        @DataProvider(name = "data-provider-get-neg-capitalName")
        public static Object[][] dataProviderGetCapitalNegMethod() 
        {
            return new Object[][] { 
            	{"zzzz", "unknown"}
            };
        }
        
        
        @DataProvider(name = "data-provider-get-neg-code2")
        public static Object[][] dataProviderGetCode2NegMethod() 
        {
            return new Object[][] { 
            	{"france", "FRA"},
              	{"Australia", "AUS"},
              	{"cocos", "CCSA"}
            };
        }
        
            @DataProvider(name = "data-provider-get-neg-code3")
            public static Object[][] dataProviderGetCode3NegMethod() 
            {
                return new Object[][] { 
                	{"France", "FR"},
                	{"Germany", "DE"},
                  	{"Australia", "AU"},
                  	{"cocos", "CCKK2"}
                };
            }
            
            @DataProvider(name = "data-provider-get-alternateCountryName")
            static Object[][] dataProviderGetAlternateCountryNameMethod() 
            {
                return new Object[][] { 
                	{"Vatican", "Holy See"},
                	{"Nippon", "Japan"},
                  	{"Pleasant Island", "Nauru"},
                  	{"Oesterreich", "Austria"},
                  	{"RSA", "South Africa"}
                };
            } 
}
