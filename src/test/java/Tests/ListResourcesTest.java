package Tests;

import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import Base.BaseTest;
import clients.ReqresClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import listeners.RetryListener;
import pojo.response.ListResourcesResponse;
import utils.AssertionUtils;
import utils.ConfigManager;
import utils.LoggerHelper;
import utils.SchemaConstants;




@Listeners({AllureTestNg.class,RetryListener.class})
public class ListResourcesTest extends BaseTest{
	
	 private AssertionUtils assertionUtils;
	 private ReqresClient client;
	
	 
	 @BeforeMethod
	    public void methodSetup() {
	        assertionUtils = new AssertionUtils();
	        client = new ReqresClient(requestSpec);
	    }
	
	
	@Test
	@Epic("User Management")
	@Feature("Get List Resources API")
	@Story("Fetch List Resources")
	@Severity(SeverityLevel.CRITICAL)
	 public void getResourcesList(ITestContext context) {
		 
		 LoggerHelper.info(log,"Started List resources Test");
		 
		 int expectedStatusCode = ConfigManager.getInt("expected.status.code");
		 int listresourcesArrayId = ConfigManager.getInt("listresources.data.arrayid");
		 String listexpectedData = ConfigManager.get("listresources.expected.data");
		 
		 
		 Response rawResponse = client.getlistResources()		 
		  				.then()
		  				.log().all()
		  				.assertThat().statusCode(expectedStatusCode)
		  				.extract()
		  				.response();
		 
		 
		 //deserialization
		 ListResourcesResponse response =  rawResponse.as(ListResourcesResponse.class);
		 
		 
		// Store response in ITestContext for listener
	     context.setAttribute("response", rawResponse.asString());
		 
		 //json schema validation
	     assertionUtils.verifyJsonSchema(rawResponse, SchemaConstants.lIST_RESOURCES, "List resources schema validation");

	
	    //validations
	     assertionUtils.verifyListNotEmpty(response.getData(), "validation of data list not empty in resources list");
	     
	     //validations
	     assertionUtils.verifyEquals(response.getData().get(listresourcesArrayId).getName(), listexpectedData, "response data validation");
	
	     
	     // soft assertions for reporting
	        assertionUtils.assertAll();
	        
	        
	     LoggerHelper.info(log, "Get Users Test completed");
	
	
	}
	
	

}












