package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import generic.JSUtil;
import generic.RobotUtil;
import generic.ScreenShot;
import generic.WebUtil;

public class BasePage
{
	public WebDriver driver;
	public WebUtil webUtil;
	public JSUtil jsUtil;
	public RobotUtil robotUtil;
	public ExtentTest eTest;
	public ScreenShot screenShot;
	
	public BasePage(WebDriver driver,ExtentTest eTest,Logger log4j)
	{
		this.driver=driver;
		this.eTest=eTest;
		PageFactory.initElements(driver, this);
		webUtil=new WebUtil(eTest,log4j);
		jsUtil=new JSUtil(driver, eTest,log4j);
		robotUtil=new RobotUtil(eTest,log4j);
		screenShot=new ScreenShot(eTest, log4j);
	}
}
