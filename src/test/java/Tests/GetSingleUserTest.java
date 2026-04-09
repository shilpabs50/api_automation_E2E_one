package Tests;

import org.testng.annotations.Test;

import Base.BaseTest;
import clients.ReqresClient;
import io.qameta.allure.Attachment;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import pojo.response.GetUserResponse;
import utils.AssertionUtils;
import utils.ConfigManager;
import utils.SchemaConstants;


public class GetSingleUserTest extends BaseTest{
	
	@Test
	@Epic("User Management")
	@Feature("Get Users API")
	@Story("Fetch single user list")
	@Severity(SeverityLevel.CRITICAL)
	public void getSingleUserTest() {
		
		
		ReqresClient client = new ReqresClient(requestSpec);	
		
		int page = ConfigManager.getInt("page.number");
		int expectedStatusCode = ConfigManager.getInt("expected.status.code");
		
		
		
		
		Response rawResponse = client.getUser(page) 
				  .then() 
				  .log().all()
				  .assertThat()
				  .statusCode(expectedStatusCode)
				  .extract().response();
		
		
		//Deserialization
		GetUserResponse response = rawResponse.as(GetUserResponse.class);

		
		/*
		 * GetUserResponse response = client.getUser(2) .then() .log().all()
		 * .assertThat() .statusCode(200) .extract().as(GetUserResponse.class);
		 */
		
		AssertionUtils assertion = new AssertionUtils();
		
		
		  //schema validation
		assertion.verifyJsonSchema(rawResponse, SchemaConstants.SINGLE_USER,"Schema validation");
		 
		  
	
	}
	



}
