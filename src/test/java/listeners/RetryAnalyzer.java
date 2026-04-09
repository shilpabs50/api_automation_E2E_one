package listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import utils.LoggerHelper;

public class RetryAnalyzer implements IRetryAnalyzer{
	
	private int retryCount = 0;
	private static final int MAX_RETRY = 2; // retry 2 times
	
	private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);
	 
		

	@Override
	public boolean retry(ITestResult result) {
		
		if(retryCount < MAX_RETRY) {
			retryCount++;
			
			LoggerHelper.info(log, "Retrying test: "+result.getName() +
					"Attempt: "+retryCount
					);
			
			return true;
		}
		
		return false;
	}

}
