package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

public class SimpleListener implements ITestListener {
	
	
	
	
	//Test Start
	@Override
	public void onTestStart(ITestResult result) {
		Allure.step("Starting test: "+result.getName());		
	}
	
	//Test Success
	@Override
	public void onTestSuccess(ITestResult result) {
	   Allure.step("Test Passed: "+result.getName());	
	}
	

	@Override
	public void onTestFailure(ITestResult result) {
		
		Allure.step("Test Failed: "+result.getName());
		
		attachError(result.getThrowable().toString());
		
		
		//Get response from the test
		Object response = result.getTestContext().getAttribute("response");
		
		if(response != null) {
			attachResponse(response.toString());
			
		}
		
	}
	
	
	
	@Override
	public void onTestSkipped(ITestResult result) {
		
		Allure.step("Test skipped: "+result.getName());
		
		if(result.getThrowable() != null) {
			
			attachError(result.getThrowable().toString());
		}
	}
	
	
	// 🔷 Suite Start
    @Override
    public void onStart(ITestContext context) {
        Allure.step("Test Suite Started: " + context.getName());
    }

    // 🔷 Suite End
    @Override
    public void onFinish(ITestContext context) {
        Allure.step("Test Suite Finished: " + context.getName());
    }
	
	
	
	@Attachment(value = "Failure reason", type = "text/plain")
	public String attachError(String message) {		
		return message;
	}
	
	@Attachment(value = "API Response on failure", type="application/json")
	public String attachResponse(String response) {
		return response;
		
	}
	
	

}
