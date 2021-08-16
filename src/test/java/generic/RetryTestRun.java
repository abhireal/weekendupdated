package generic;

import java.util.ArrayList;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
//todo report
public class RetryTestRun implements IRetryAnalyzer
{
	ArrayList<String> testList=new ArrayList<String>();
	
	public boolean retry(ITestResult result) {
		String testName=result.getMethod().getMethodName();
		if(testList.contains(testName))
		{
			System.out.println("already failed");
			return false;//false means dont run it
		}
		else
		{
			System.out.println("1st time it is failing, so rerun it");
			testList.add(testName);
			BaseTest.extentReports.removeTest(testName);
			return true;//true means run it
		}
		
	}


}
