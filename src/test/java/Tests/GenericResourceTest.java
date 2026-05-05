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
import utils.AssertionUtils;
import utils.ConfigManager;
import utils.LoggerHelper;
import utils.SchemaConstants;



@Listeners({AllureTestNg.class,RetryListener.class})
public class GenericResourceTest extends BaseTest {
	
	 private AssertionUtils assertionUtils;
	 private ReqresClient client;
	
	
	 
	 @BeforeMethod
	    public void methodsetUp() {
	        assertionUtils = new AssertionUtils();
	        client = new ReqresClient(requestSpec);
	    }

	 
	@Test
	@Epic("User Management")
	@Feature("Get Generic resource API")
	@Story("Fetch generic resource")
	@Severity(SeverityLevel.CRITICAL)
	 public void getGenericResource(ITestContext context) {
		 
		 LoggerHelper.info(log, "Generic resource test started");
		 
		int genericResourcePageId =  ConfigManager.getInt("genericresource.pageid");
		int expectedStatusCode = ConfigManager.getInt("expected.status.code");
		
		
		Response rawResponse = client.getGenericResource(genericResourcePageId)
							.then()
							.log().all()
							.assertThat()
							.statusCode(expectedStatusCode)
							.extract()
							.response();
		
		
		// Store response in ITestContext for listener
		context.setAttribute("response", rawResponse.asString());
		
		 
		assertionUtils.verifyJsonSchema(rawResponse, SchemaConstants.GENERIC_RESOURCE, "Generic resource schema validation");
		
		 
		 
		 
		 
		 
		 
	 }
	 
	 
}
