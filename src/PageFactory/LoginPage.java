package PageFactory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.AssertJUnit;
/*
 * author: anjana.iv
 * Purpose : Login Page Object Locators and Methods
 */
import org.testng.Reporter;

import config.TestBase;

public class LoginPage {

	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	@FindBy(id="ClientCode")
	WebElement clientName;
	
	@FindBy(id="txtUserName")
	WebElement useremail;
	
	@FindBy(id="Password")
	WebElement userpwd;
	
	@FindBy(id="frmLogin")
	WebElement titleText;
	
	@FindBy(id="acLogin")
	WebElement LoginButton;
	
	public LoginPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	//Set client code in textbox
	public void setUserName(String strClientName){
		clientName.sendKeys(strClientName);
		
	}
	
	//Set user email in user email textbox
	public void setUserEmail(String strEmail){
		useremail.sendKeys(strEmail);
	}
	
	//Set password in password textbox
		public void setPassword(String strPassword){
			userpwd.sendKeys(strPassword);
		}
	
	//Click on login button
	public void clickLogin(){
		LoginButton.click();
	}
	
	//Get the title of Login Page
	public String getLoginTitle(){
	 return	titleText.getText();
	}
	/**
	 * This POM method will be exposed in test case to login in the application
	 * @param strUserName
	 * @param strPasword
	 * @return
	 */
	public void loginToSaTTrax(String strUserName,String strEmail,String strPwd){
		try {
		//Fill user name
		this.setUserName(strUserName);
		//Fill user email
		this.setUserEmail(strEmail);
		//Fill password
		this.setPassword(strPwd);
		//Click Login button
		this.clickLogin();
		Thread.sleep(100);
		/*if(this.getLoginTitle().contains("Login")) {
			Reporter.log("We are still in Login Page, either user credentials given are wrong or Application is not allowing the user to login",true);
			driver.close();
		}*/
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
