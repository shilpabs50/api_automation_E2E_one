package Tests;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
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
import pojo.response.UpdateUserResponse;
import pojos.request.UpdateUserRequest;
import utils.AssertionUtils;
import utils.ConfigManager;
import utils.SchemaConstants;



@Listeners({AllureTestNg.class,RetryListener.class})
public class UpdateUserTest extends BaseTest {
		
	private AssertionUtils assertionUtils;
	private ReqresClient client;
	private UpdateUserRequest userRequestBody;
	
	
	@Step("Initializing test setup")
	@BeforeMethod
	public void methodSetup() {
		assertionUtils = new AssertionUtils();
	    client = new ReqresClient(requestSpec);
	    userRequestBody = new UpdateUserRequest(); // fresh per test
	}
	

	
	@Test
	@Epic("User Management")
	@Feature("Update the User API")
	@Story("Update the particular user data")
	@Severity(SeverityLevel.CRITICAL)
	public void updateUserTest(ITestContext context) {
		
		
		//get test data from config file
		int userId = ConfigManager.getInt("user.id");
		String name = ConfigManager.get("update.name");
		String job = ConfigManager.get("update.job");
		int expectedStatusCode = ConfigManager.getInt("expected.status.code");
		
		
		//prepare the test body
		userRequestBody.setName(name);
		userRequestBody.setJob(job);
		
		
		//call api
		
		Response rawResponse = client.updateUser(userId, userRequestBody)
								.then()
								.log().all()
								.assertThat()
	    						.statusCode(expectedStatusCode)
	    						.extract().response();
		
		
		
		//deserialization		
		UpdateUserResponse response = rawResponse.as(UpdateUserResponse.class);
		
		//store the response for reporting
		context.setAttribute("response", rawResponse.asString());
		
		//schema validation
		assertionUtils.verifyJsonSchema(rawResponse, SchemaConstants.UPDATE_USER, "Update user schema validation");
		
		//validations using pojo
		assertionUtils.verifyEquals(response.getName(), name, "Updated user validation");
		assertionUtils.verifyEquals(response.getJob(), job, "Updated job validation");
		
		assertionUtils.assertAll();
		
	}
	

}
