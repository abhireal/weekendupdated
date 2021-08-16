package generic;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners(TestListener.class)
public class BaseTest implements IConstant
{
	public static ExtentReports extentReports;
	public WebDriver driver;
	public WebDriverWait wait;
	public ExtentTest eTest;
	public Logger log4j;
	public DBUtils dbUtils;
	public FileUtils fileUtils;
	public JSUtil jsUtil;
	//public RobotUtil robotUtil;
	public ScreenShot screenShot;
	public WebUtil webUtil;
	
	public BaseTest()
	{
		log4j=RootLogger.getLogger(this.getClass());
		dbUtils= new DBUtils(eTest, log4j);
		fileUtils=new FileUtils(eTest, log4j);
		jsUtil=new JSUtil(driver, eTest, log4j);
		//robotUtil=new RobotUtil(eTest, log4j);
		screenShot=new ScreenShot(eTest, log4j);
		webUtil=new WebUtil(eTest, log4j);
	}

	@BeforeSuite
	public void startGrid() throws IOException
	{
//		String useGrid = fileUtils.getPropertyValue(settingsPath,"grid");
//		LogReport.log(eTest, log4j, "use Grid?:"+useGrid, Status.INFO);
//		
//		if(useGrid.equalsIgnoreCase("yes")) 
//		{
//			String[] command = {"cmd.exe", "/C", "Start", ".\\remote\\RunMe.bat"};
//	        Runtime.getRuntime().exec(command);
//		}
	}
	
	@BeforeSuite
	public void initReport()
	{
        extentReports=new ExtentReports();
        ExtentSparkReporter spark=new ExtentSparkReporter(extentPath);
        extentReports.attachReporter(spark);
        LogReport.log(eTest, log4j, "Extent report initialized", Status.INFO);
	}
	
	@AfterSuite
	public void publishReport()
	{
		extentReports.flush();
		LogReport.log(eTest, log4j, "Extent report published", Status.INFO);
	}
	
	
	@Parameters({"hubURL","browser"})
	@BeforeMethod
	public void preCondition(@Optional(defaultHubURL)String hubURL,@Optional(defaultBrowser)String browser,ITestResult testResult) throws Exception
	{
		
		hubURL = System.getProperty("hub_url");
		System.out.println("****"+hubURL+"*****");
		
		String testName = testResult.getMethod().getMethodName();
		eTest = extentReports.createTest(testName);
		
		fileUtils.eTest=eTest;
		dbUtils.eTest=eTest;
		screenShot.eTest=eTest;
		jsUtil.eTest=eTest;
		//robotUtil.eTest=eTest;
		webUtil.eTest=eTest;

		LogReport.log(eTest, log4j, "Extent Test Created", Status.INFO);
		
		String useGrid = fileUtils.getPropertyValue(settingsPath,"grid");
		
		LogReport.log(eTest, log4j, "Using Grid?:"+useGrid, Status.INFO);
		
		if(useGrid.equalsIgnoreCase("yes")) 
		{
			LogReport.log(eTest, log4j, "Using Grid", Status.INFO);
			
			URL url=new URL(hubURL);
			LogReport.log(eTest, log4j, "hubURL:"+hubURL, Status.INFO);
			
			DesiredCapabilities capability=new DesiredCapabilities();
			capability.setBrowserName(browser);
			LogReport.log(eTest, log4j, "Browser:"+browser, Status.INFO);
			driver=new RemoteWebDriver(url, capability);
			
			LogReport.log(eTest, log4j, "Browser launched in grid:"+browser, Status.INFO);
		}
		else
		{

			LogReport.log(eTest, log4j, "Using Local System", Status.INFO);
			String localBrowser = fileUtils.getPropertyValue(settingsPath,"LocalBrowser");
			if(localBrowser.equals("firefox"))
			{
				WebDriverManager.firefoxdriver().setup();
				driver=new FirefoxDriver();
				LogReport.log(eTest, log4j, "Launching Firefox Browser", Status.INFO);
			}
			else
			{
				WebDriverManager.chromedriver().setup();
				System.setProperty("webdriver.chrome.silentOutput", "true");
				java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);
				
				ChromeOptions c = new ChromeOptions();
				c.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
				driver=new ChromeDriver(c);
				LogReport.log(eTest, log4j, "Launching Chrome Browser", Status.INFO);
			}
			
			LogReport.log(eTest, log4j, "Browser launched in local:"+localBrowser, Status.INFO);
			
			EventFiringWebDriver eDriver=new EventFiringWebDriver(driver);
			eDriver.register(new WebListener(log4j));
			driver=eDriver;
			LogReport.log(eTest, log4j, "Webdriver registered to EventFiringWebDriver", Status.INFO);
		}
	
		
		
	
		long ETO = Long.parseLong(fileUtils.getPropertyValue(settingsPath,"ETO"));
		wait=new WebDriverWait(driver,ETO);
		LogReport.log(eTest, log4j, "ETO:"+ETO, Status.INFO);
		
		long ITO = Long.parseLong(fileUtils.getPropertyValue(settingsPath,"ITO"));
		driver.manage().timeouts().implicitlyWait(ITO,TimeUnit.SECONDS);
		LogReport.log(eTest, log4j, "ITO:"+ITO, Status.INFO);
		
		long PLT = Long.parseLong(fileUtils.getPropertyValue(settingsPath,"PLT"));
		driver.manage().timeouts().pageLoadTimeout(PLT, TimeUnit.SECONDS);
		LogReport.log(eTest, log4j, "PLT:"+PLT, Status.INFO);
		
		long STO = Long.parseLong(fileUtils.getPropertyValue(settingsPath,"STO"));
		driver.manage().timeouts().setScriptTimeout(STO,TimeUnit.SECONDS);
		LogReport.log(eTest, log4j, "STO:"+STO, Status.INFO);
		
		String appUrl=fileUtils.getPropertyValue(settingsPath, "AppUrl");
		driver.get(appUrl);
		LogReport.log(eTest, log4j,"appUrl:"+appUrl, Status.INFO);
		
		driver.manage().deleteAllCookies();
		LogReport.log(eTest, log4j, "Cookies Deleted", Status.INFO);
		
		driver.manage().window().maximize();
		LogReport.log(eTest, log4j, "Browser Maximized", Status.INFO);
		
		LogReport.log(eTest, log4j, "Precondition completed", Status.INFO);
	}
	
	@AfterMethod
	public void postCondition(ITestResult result)
	{
		driver.quit();
		LogReport.log(eTest, log4j, "Browser is closed", Status.INFO);
		
		int status = result.getStatus();
		if(status==1)
		{
			LogReport.log(eTest, log4j,"Test executed successfully", Status.PASS);
		}
		else if(status==2)
		{
			LogReport.log(eTest, log4j,"Test execution failed", Status.FAIL);
		}
		else if(status==3)
		{
			LogReport.log(eTest, log4j, "Test execution is Skipped", Status.SKIP);
		}
		else
		{
			LogReport.log(eTest, log4j, "Test execution is unknown", Status.WARNING);
		}
		LogReport.log(eTest, log4j, "Postcondition completed", Status.INFO);
	}
	
	@DataProvider
	public Iterator<String[]> getDataFromXL(Method method)
	{
		LogReport.log(eTest, log4j, "XL DP", Status.INFO);
		String sheet=method.getName();
		Iterator<String[]> data=dbUtils.getDataFromXLtoDP(INPUT_XLPATH, sheet);
		LogReport.log(eTest, log4j, "XL path:"+INPUT_XLPATH+" XL Sheet:"+sheet, Status.INFO);
		return data;
	}
	
	@DataProvider
	public Iterator<String[]>  getDataFromCSV(Method method) throws Exception
	{
		
		LogReport.log(eTest, log4j, "CSV DP", Status.INFO);
		String path=DATA_PATH+method.getName()+".csv";
		Iterator<String[]> data =fileUtils.getDataFromCSVForDP(path);
		LogReport.log(eTest, log4j, "CSV path:"+path, Status.INFO);
		return data;
		
	}

	@DataProvider
	public Iterator<String[]>  getDataFromDB(Method method) throws Exception
	{
		LogReport.log(eTest, log4j, "MYSQL DP", Status.INFO);
		String dbURL=fileUtils.getPropertyValue(settingsPath,"DB_URL");
		String un=fileUtils.getPropertyValue(settingsPath,"DB_UN");
		String pw=fileUtils.getPropertyValue(settingsPath,"DB_PW");
		String query="select * from "+method.getName();
		Iterator<String[]> data =dbUtils.getDataFromMySQLToDP(dbURL, un,pw,query );
		return data;
		
	}
}
