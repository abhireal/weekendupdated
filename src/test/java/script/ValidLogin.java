package script;

import org.testng.annotations.Test;

import generic.BaseTest;
import pages.LoginPage;

public class ValidLogin extends BaseTest
{	

	@Test(dataProvider = "getDataFromXL")
	public void testValidLogin(String un,String pw)
	{
		LoginPage loginPage=new LoginPage(driver, eTest,log4j);
		loginPage.setUserName(un);
		loginPage.setPassword(pw);
		loginPage.clickLogin();
	}
}
