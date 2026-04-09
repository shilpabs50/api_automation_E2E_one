package utils;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.response.User;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;

public class AssertionUtils {
	
	
	 private SoftAssertions soft = new SoftAssertions();	 
	 private static final Logger log = LogManager.getLogger(AssertionUtils.class);
	 String msg;

    // 🔷 Generic Equals
    @Step("{message} | Expected: {expected}, Actual: {actual}")
    public <T> void verifyEquals(T actual, T expected, String message) {

    	if (!actual.equals(expected)) {
          
         msg = "[ASSERT FAIL] " + message + " | Expected: " + expected + " | Actual: " + actual;
           LoggerHelper.error(log, msg);
        } else {
        	LoggerHelper.assertion(log, "[ASSERT PASS] " + message);
        }
    
        soft.assertThat(actual)
                .as(message)
                .isEqualTo(expected);
    }

    // 🔷 Not Null
    @Step("{message}")
    public  void verifyNotNull(Object object, String message) {
    	
    	if(object == null) {
    		
    		 msg = "[ASSERT FAIL] {} - value is null";
    		LoggerHelper.error(log, msg);
    	}else {
    		LoggerHelper.assertion(log, "[ASSERT PASS] " + message);
    	}

       soft.assertThat(object)
                .as(message)
                .isNotNull();
    }

    // 🔷 List Size
    @Step("{message} | Expected Size: {expectedSize}, Actual Size: {actualSize}")
    public  <T> void verifyListSize(List<T> list, int expectedSize, String message) {
    	
    	int actualsize = (list == null) ? 0 : list.size();
    	
          if(actualsize != expectedSize) {
        	 
        	  msg = "[ASSERT FAIL] " + message + " Expected size: " + expectedSize + " | Actual size: " + actualsize;
        	  LoggerHelper.error(log, msg);
          }else {
        	          	  
        	  LoggerHelper.assertion(log, "[ASSERT PASS] " + message);
          }

        soft.assertThat(list)
                .as(message)
                .hasSize(expectedSize);
    }

    // 🔷 List Not Empty
    @Step("{message}")
    public  <T> void verifyListNotEmpty(List<T> list, String message) {
    	
    	
    	if(list == null || list.isEmpty()) {
    		msg = "[ASSERT FAIL] {} | List is empty or null";
    		LoggerHelper.error(log, msg);
    		
    	}else {
    		
    		LoggerHelper.assertion(log, "[ASSERT PASS] " + message);
    	}

        soft.assertThat(list)
                .as(message)
                .isNotEmpty();
    }

    // 🔷 Contains
    @Step("{message} | Expected substring: {expected}")
    public  void verifyContains(String actual, String expected, String message) {
    	

    	 if (actual == null || !actual.contains(expected)) {
    	        String msg = "[ASSERT FAIL] " + message +
    	                     " | Expected substring: " + expected +
    	                     " | Actual: " + actual;

    	        LoggerHelper.error(log, msg);
    	    } else {
    	        LoggerHelper.assertion(log, "[ASSERT PASS] " + message);
    	    }
    	

        soft.assertThat(actual)
                .as(message)
                .contains(expected);
    }

    // 🔷 JSON Schema Validation
    @Step("Validate response schema using {schemaPath}")
    public  void verifyJsonSchema(Response response, String schemaPath,String message) {
    	
    	try {

        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
        
                
         //log.info("[ASSERT PASS] Schema validation passed for {}",schemaPath);
         
         LoggerHelper.assertion(log, "[ASSERT PASS] "+message);
         
         
    	}
    	catch(Exception e) {
    		
    		msg = "[ASSERT FAIL] Schema validation failed for: {}"+ schemaPath; 
            LoggerHelper.error(log, msg);    		
    		
    		throw e;
    		
    	}
    }
   
 
    
    @Step("Validate all users in response data array")
    public void validateUserData(List<User> users) {

        for (User user : users) {

            // Email validation
            verifyContains(user.getEmail(), "@reqres.in", "Validate email domain");

            // First name not null
            verifyNotNull(user.getFirst_name(), "Validate first name not null");

            // ID > 0
            soft.assertThat(user.getId())
                    .as("Validate user id is positive")
                    .isGreaterThan(0);

            // Avatar URL validation
            verifyContains(user.getAvatar(), "https://reqres.in", "Validate avatar URL");
        }
    }
    
    
    public void assertAll() {
        soft.assertAll(); // Will throw an exception if any soft assertion failed
    }
}