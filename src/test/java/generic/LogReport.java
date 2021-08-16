package generic;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
//TODO -Add enable/disable option for each reporting type based on the value
//present in settings.properties
public class LogReport {

	public static void log(ExtentTest eTest,Logger log4j,String msg,Status status)
	{
		if(eTest!=null)
		{
			eTest.log(status,msg);
			
		}
		
		if(log4j!=null)
		{

			switch (status) 		//fall through switch
			{
				case PASS		:
				case INFO		:log4j.info(msg);break;

				case SKIP		:
				case WARNING	:log4j.warn(msg);break;
				
				case FAIL		:log4j.error(msg);break;
			}

		}
		
		Reporter.log(msg); //testng report
	}
}
