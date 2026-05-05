package Base;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.ConfigManager;
import utils.LoggerHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
	
	
	protected static RequestSpecification requestSpec;
	protected Logger log = LogManager.getLogger(this.getClass());
	
	
	@BeforeMethod
	public void logTestStart(Method method) {
		LoggerHelper.info(log, 
				"Thread: " + Thread.currentThread().getName() +
				"Thread ID: "+ Thread.currentThread().getId()+
				" | Test : " + method.getName()+
				" | Class :" + this.getClass().getSimpleName()
				);
			
	}
	
	
	 @AfterMethod
	    public void logTestEnd(Method method) {
	        LoggerHelper.info(log,
	            "Thread ID: " + Thread.currentThread().getId() +
	            " | Completed Test: " + method.getName());
	    }
	
	@BeforeClass
	public void setup() {
		
		requestSpec = new RequestSpecBuilder()
				        .setBaseUri(ConfigManager.get("base.url"))
				        .addFilter(new AllureRestAssured())
				        .addHeader("x-api-key", ConfigManager.get("api.key"))
				        .setContentType(ConfigManager.get("content.type"))
				        .build();		
			
	}
	
	
	
	
	

}
