package generic;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.ExtentTest;

public class TestListener implements ITestListener {

	public static String getTimeStamp()
	{
		SimpleDateFormat d=new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String timeStamp= d.format(new Date());
		return timeStamp;
	}
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult test) {
		
		Object testObject = test.getInstance();//it will give current TestClass object
		
		BaseTest baseTest=(BaseTest)testObject;//down casting
		WebDriver driver=baseTest.driver;//get the current browser
		ExtentTest eTest=baseTest.eTest;//get the current extent test
		
		String timeStamp=getTimeStamp();
		String filePath=IConstant.IMG_PATH+timeStamp;
		baseTest.screenShot.getScreenShot(driver, filePath);//get the page screenshot
		Media media = MediaEntityBuilder.createScreenCaptureFromPath("./../images/"+timeStamp+".png").build();
		eTest.fail(media);//add it to extent report
		
		eTest.addScreenCaptureFromPath("./../images/"+timeStamp+".png");//adding screenshot at the top
		baseTest.screenShot.getScreenShot(filePath);//taking Desktop screenshot
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
