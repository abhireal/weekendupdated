package generic;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
//todo reporting
public class WebUtil {
	public ExtentTest eTest;
	public Logger log4j;
	
	public WebUtil(ExtentTest eTest,Logger log4j)
	{
		this.eTest=eTest;
		this.log4j=log4j;
	}
	
	public void sendKeys(WebElement element,String input)
	{
		element.sendKeys(input);
		eTest.log(Status.INFO, "Entering the input:"+input);
	}
	
	public void click(WebElement element)
	{
		element.click();
		eTest.log(Status.INFO, "clicking on the element");
	}

	public void submit(WebElement element)
	{
		element.submit();
		eTest.log(Status.INFO, "submitting the element");
	}

	public void clear(WebElement element)
	{
		element.clear();
		eTest.log(Status.INFO, "clearing the element value");
	}

	public String getAttribute(WebElement element,String name)
	{
		String value=element.getAttribute(name);
		eTest.log(Status.INFO, "Attribute:"+name+"; value:"+value);
		return value;
	}

	public String getCssValue(WebElement element,String name)
	{
		String value=element.getCssValue(name);
		eTest.log(Status.INFO, "CSS Attribute:"+name+"; value:"+value);
		return value;
	}
	
	public String getText(WebElement element)
	{
		String value=element.getText();
		eTest.log(Status.INFO, "Text value:"+value);
		return value;
	}
	
	public String getTagName(WebElement element)
	{
		String value=element.getTagName();
		eTest.log(Status.INFO, "Tag Name:"+value);
		return value;
	}

	public int getX(WebElement element)
	{
		int value=element.getLocation().getX();
		eTest.log(Status.INFO, "x value:"+value);
		return value;
	}
	
	public int getY(WebElement element)
	{
		int value=element.getLocation().getY();
		eTest.log(Status.INFO, "Y value:"+value);
		return value;
	}
	
	public int getHeight(WebElement element)
	{
		int value=element.getSize().getHeight();
		eTest.log(Status.INFO, "Height value:"+value);
		return value;
	}

	public int getWidth(WebElement element)
	{
		int value=element.getSize().getWidth();
		eTest.log(Status.INFO, "Width value:"+value);
		return value;
	}

	public boolean isDisplayed(WebElement element)
	{
		boolean value=element.isDisplayed();
		eTest.log(Status.INFO, "isDisplayed value:"+value);
		return value;
	}

	public boolean isEnabled(WebElement element)
	{
		boolean value=element.isEnabled();
		eTest.log(Status.INFO, "isEnabled value:"+value);
		return value;
	}
	
	public boolean isSelected(WebElement element)
	{
		boolean value=element.isSelected();
		eTest.log(Status.INFO, "isSelected value:"+value);
		return value;
	}
}
