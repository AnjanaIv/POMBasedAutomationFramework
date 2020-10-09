package PageFactory;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Permission Object Locators and Methods
 */
public class AddPermissionPage {
	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	@FindBy(id="RegistrationNumber")
	WebElement txt_RegNo;
	
	@FindBy(id="ddlVehicleType_inputSearch")
	WebElement txt_vehicle;
	
	@FindBy(xpath="//span[@id='ddlVehicleType_dropdown'/span")
	WebElement img_vehicleType;
	public AddPermissionPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void setRegNo(String regNumber){
		txt_RegNo.sendKeys(regNumber);
		Reporter.log("Registration Number "+regNumber+"<br>");
	}
	public boolean fill_AddPermssion_form(Object[] FormData) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntityThroSearch(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
		cpo.setRolethroSearch(FormData[1].toString());}
	
		
		if(FormData[7].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Permission Mapping form"+e.getMessage());
		}
		return false;
	}
}
