package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import PageFactory.LoginPage;

public class TestBase{
	public static WebDriver driver;
	public static Properties prop;
	static LoginPage objLogin;
	static String sAppURL = "";
	static String sStandardUserName = "";
	static String sStandardUserEmail = "";
	static String sStandardPassword = "";
	static String sTestDataFilePath = "";
	static String sScreenshotsFilePath = "";
	static String sBrowser ="";

	
	public TestBase(){
 		try {
 			prop = new Properties();
			FileInputStream ip = new FileInputStream("test.properties");
			prop.load(ip);
 		} catch (FileNotFoundException e) {
 			e.printStackTrace();
 		}catch(IOException e) {
 			e.printStackTrace();
 		}
	}
	@BeforeSuite
	public static void intilization(){
		try {
		sBrowser = prop.getProperty("sBrowser");
		String WORKING_DIR = System. getProperty("user.dir");
		
		if(sBrowser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", WORKING_DIR+"\\lib\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver(); 
			Reporter.log("=====Chrome Browser Session Started=====", true);
	 	}/*
	 	
		else if(browserName.equals("FF")){
	 		System.setProperty("webdriver.gecko.driver", "MyRunner\\geckodriver.exe");
	 		driver = new FirefoxDriver();
        }*/
		 driver.manage().window().maximize();
	     sAppURL = prop.getProperty("sAppURL");
         driver.get(sAppURL);
         waitForLoad(driver);
         Reporter.log("=====Loading SatTrax Application=====", true);
       //Create Login Page object
 		objLogin = new LoginPage(driver);
 		//Verify login page title
 		String loginPageTitle = objLogin.getLoginTitle();
 		Assert.assertTrue(loginPageTitle.toLowerCase().contains("login"));
 		Reporter.log("=====SatTrax Application Started============",true);
 		//login to application
 		sStandardUserName =prop.getProperty("sStandardUserName");
 		sStandardUserEmail = prop.getProperty("sStandardUserEmail");
 		sStandardPassword = prop.getProperty("sStandardPassword");
 		objLogin.loginToSaTTrax(sStandardUserName,sStandardUserEmail,sStandardPassword);
 		waitForLoad(driver);
		}catch(Exception e) {
			Reporter.log("=====Failed to load SatTrax Application=====", false);
		}
		
	}
	@AfterSuite
	public static void closeApplication()
	{
		driver.quit();
		Reporter.log("=====Browser Session End=====", true);
		
	}
	public static void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {                 
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(pageLoadCondition);       
    }
	
}    