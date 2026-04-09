package Base;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.BeforeClass;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.ConfigManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
	
	
	protected static RequestSpecification requestSpec;
	protected Logger log = LogManager.getLogger(this.getClass());
	
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
