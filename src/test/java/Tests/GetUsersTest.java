package Tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import listeners.RetryListener;
import Base.BaseTest;
import clients.ReqresClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.ITestContext;
import pojo.response.GetUsersResponse;
import pojo.response.User;
import utils.AssertionUtils;
import utils.ConfigManager;
import utils.LoggerHelper;
import utils.SchemaConstants;


@Listeners({AllureTestNg.class,RetryListener.class})
public class GetUsersTest extends BaseTest{
	
	 private AssertionUtils assertionUtils;
	 private ReqresClient client;
	
	 
	 @BeforeMethod
	    public void setUp() {
	        assertionUtils = new AssertionUtils();
	        client = new ReqresClient(requestSpec);
	    }
		
	
	@Test
	@Epic("User Management")
	@Feature("Get Users API")
	@Story("Fetch multiple users list")
	@Severity(SeverityLevel.CRITICAL)
	public void getUsersTest(ITestContext context) {
		
		LoggerHelper.info(log,"Started Get Users Test");
		
		int page = ConfigManager.getInt("page.number");
		int expectedStatusCode = ConfigManager.getInt("expected.status.code");
		int expectedPage = ConfigManager.getInt("expected.page");
			
		
		//raw response extraction
		Response rawResponse = client.getUsers(page).
				 then()
				 .log().all()
				 	.assertThat() 
				 	.statusCode(expectedStatusCode) 
				 	.extract().response();
				
        
        // deserialization
        GetUsersResponse response = rawResponse.as(GetUsersResponse.class);

        // Store response in ITestContext for listener
        context.setAttribute("response", rawResponse.asString());

        // response validation
        assertionUtils.verifyEquals(response.getPage(), expectedPage, "Page number validation");

        // schema validation
        assertionUtils.verifyJsonSchema(rawResponse, SchemaConstants.USER_LIST, "Schema validation");

        // soft assertions for reporting
        assertionUtils.assertAll();

        LoggerHelper.info(log, "Get Users Test completed");
        
    
	}


}
