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
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.response.Response;
import listeners.RetryListener;
import pojos.request.UpdateUserRequest;
import utils.AssertionUtils;
import utils.ConfigManager;

@Listeners({AllureTestNg.class,RetryListener.class})
public class DeleteUserTest extends BaseTest{
	
	private AssertionUtils assertionUtils;
	private ReqresClient client;
	
	
	@Step("Initializing test setup")
	@BeforeMethod
	public void methodSetup() {
		assertionUtils = new AssertionUtils();
	    client = new ReqresClient(requestSpec);
	    
	}
	
	
	
	
	@Test
	@Epic("User Management")
	@Feature("Delete the User API")
	@Story("Delete the particular user data")
	@Severity(SeverityLevel.CRITICAL)
	public void deleteUser(ITestContext context) {
		
	//get data from the config file	
	int deleteUserId =ConfigManager.getInt("delete.user.id");
	int deleteExpectedStatusCode = ConfigManager.getInt("delete.expected.status.code");
	
	
	//call the delete user api, no response body
	Response rawResponse = client.deleteUser(deleteUserId)
							.then()
							.log().all()
							.assertThat()
							.statusCode(deleteExpectedStatusCode)
							.extract().response();
	
	
	//store the response for reporting
	context.setAttribute("response", rawResponse.asString());
	
	

	}
	
	
	

}
