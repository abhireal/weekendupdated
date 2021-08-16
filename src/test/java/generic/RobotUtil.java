package generic;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
//TODO --Reporting and Aa case typing
public class RobotUtil {

	Robot robot;
	public ExtentTest eTest;
	public Logger log4j;
	
	public RobotUtil(ExtentTest eTest,Logger log4j)
	{
		try 
		{
			robot=new Robot();
		} catch (AWTException e) 
		{
			//e.printStackTrace();
		}
		
		this.eTest=eTest;
		this.log4j=log4j;
	}
	
	public void click(int x,int width,int y)
	{
		robot.mouseMove(x+width, y+ IConstant.BCONST);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void click(WebElement element)
	{
		int x=element.getRect().getX();
		int y=element.getRect().getY();
		int width=element.getRect().getWidth();
		
		robot.mouseMove(x+width, y+ IConstant.BCONST);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public void moveToElement(WebElement element)
	{
		int x=element.getRect().getX();
		int y=element.getRect().getY();
		int width=element.getRect().getWidth();
		robot.mouseMove(x+width, y+ IConstant.BCONST);
	}
	//develop a method type the given input in respective case Bhanu
	public void sendKeys(String input)
	{
		char[] a=input.toUpperCase().toCharArray();
		for(char c:a)
		{
			int i=c;
			robot.keyPress(i); 
			robot.keyRelease(i);
		}
	}
	
	public void scrollDown(int numberOfScroll)
	{
		for(int i=1;i<=numberOfScroll;i++)
		{ 
				robot.mouseWheel(1); 
				try 
				{
					Thread.sleep(IConstant.SLEEP);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				} 
		}
	}
	
	public void scrollUp(int numberOfScroll)
	{
		for(int i=1;i<=numberOfScroll;i++)
		{ 
				robot.mouseWheel(-1); 
				try 
				{
					Thread.sleep(IConstant.SLEEP);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				} 
		}
	}
	
	public void rightClick()
	{
		robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
}
