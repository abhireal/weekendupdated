package generic;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
//TODO report
public class ScreenShot {
	public ExtentTest eTest;
	public Logger log4j;
	
	public ScreenShot(ExtentTest eTest,Logger log4j)
	{
		this.eTest=eTest;
		this.log4j=log4j;
	}
	public void getScreenShot(WebElement element,String path)
	{
		try
		{
			File srcFile = element.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(path+".png"));
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
	}
	public void getScreenShot(WebDriver driver,String path)
	{
		try
		{
			TakesScreenshot t=(TakesScreenshot)driver;
			File srcFile = t.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(srcFile, new File(path+".png"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void getScreenShot(String path)
	{
		try
		{
			Robot robot=new Robot(); 
			Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRect=new Rectangle(ss); 
			BufferedImage img = robot.createScreenCapture(screenRect); 
			ImageIO.write(img, "jpg",new File(path+".jpg"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void getScreenShot(WebDriver driver,String path,int scrollDealy)
	{
		try
		{
			AShot ashot=new AShot(); 
			ashot.takeScreenshot(driver); 
			Screenshot img = ashot.shootingStrategy(ShootingStrategies.viewportPasting(scrollDealy)).takeScreenshot(driver); 
			ImageIO.write(img.getImage(),"jpg",new File(path+".jpg"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
