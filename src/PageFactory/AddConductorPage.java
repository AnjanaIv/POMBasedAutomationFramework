package PageFactory;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Conductor Object Locators and Methods
 */
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class AddConductorPage {

	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	
	public AddConductorPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	public boolean fill_AddConductor_form(Object[] FormData,String sForm) throws Exception{
		try {
		CommonPageObjects cpo = new CommonPageObjects(driver);
		if(FormData[0].toString()!= "") {
		cpo.setParentEntity(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
			cpo.setFirstName(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			cpo.setMiddleName(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			cpo.setLastName(FormData[3].toString());} 
		if(FormData[4].toString()!= "") {
			cpo.setEmail(FormData[4].toString());} 
		if(FormData[5].toString()!= "") {
			cpo.setPhonePri(FormData[5].toString());}
		if(FormData[6].toString()!= "") {
		cpo.setPhoneSec(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
		cpo.setLandlinePri(FormData[7].toString());}
		if(FormData[8].toString()!= "") {
			cpo.setLandlineSec(FormData[8].toString());}
		if(FormData[9].toString()!= "") {
			cpo.setAddress1(FormData[9].toString());}
		if(FormData[10].toString()!= "") {
		cpo.setAddress2(FormData[10].toString());}
		if(FormData[11].toString()!= "") {
		cpo.setAddress3(FormData[11].toString());}
		if(FormData[12].toString()!= "") {
		cpo.setCountryThroSearch(FormData[12].toString());}
		if(FormData[13].toString()!= "") {
			cpo.setStateThroSearch(FormData[13].toString());}
		if(FormData[14].toString()!= "") {
			cpo.setCityThroSearch(FormData[14].toString());}
		if(FormData[15].toString()!= "") {
			cpo.setPinCode(FormData[15].toString());}
		Reporter.log("Add Conductor Form is filled with above details<br>");
		if(FormData[16].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}else if(FormData[17].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickClear();
			cpo.clickGoBack();
			return false;
		}else if(FormData[18].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Conductor form"+e.getMessage());
			return false;
		}
		return true;
		
	}
	public boolean fill_EditConductor_form(Object[] FormData,String sForm) throws Exception{
		try {
		CommonPageObjects cpo = new CommonPageObjects(driver);
		if(FormData[1].toString()!= "") {
		cpo.setParentEntity(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			cpo.setFirstName(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			cpo.setMiddleName(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			cpo.setLastName(FormData[4].toString());} 
		if(FormData[5].toString()!= "") {
			cpo.setEmail(FormData[5].toString());} 
		if(FormData[6].toString()!= "") {
			cpo.setPhonePri(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
		cpo.setPhoneSec(FormData[7].toString());}
		if(FormData[8].toString()!= "") {
		cpo.setLandlinePri(FormData[8].toString());}
		if(FormData[9].toString()!= "") {
			cpo.setLandlineSec(FormData[9].toString());}
		if(FormData[10].toString()!= "") {
			cpo.setAddress1(FormData[10].toString());}
		if(FormData[11].toString()!= "") {
		cpo.setAddress2(FormData[11].toString());}
		if(FormData[12].toString()!= "") {
		cpo.setAddress3(FormData[12].toString());}
		if(FormData[13].toString()!= "") {
		cpo.setCountryThroSearch(FormData[13].toString());}
		if(FormData[14].toString()!= "") {
			cpo.setStateThroSearch(FormData[14].toString());}
		if(FormData[15].toString()!= "") {
			cpo.setCityThroSearch(FormData[15].toString());}
		if(FormData[16].toString()!= "") {
			cpo.setPinCode(FormData[16].toString());}
		Reporter.log("Add Conductor Form is filled with above details<br>");
		if(FormData[17].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Edit Conductor form"+e.getMessage());
			return false;
		}
		return true;
		
	}
}
