package PageFactory;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Staff Object Locators and Methods
 */
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
 * Purpose : Add and Edit features of Staff Object Locators and Methods
 */
public class AddStaffPage {
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	public AddStaffPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	} 

	
	public boolean fill_AddStaff_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntity(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
			cpo.setRolethroSearch(FormData[1].toString());}
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
		Reporter.log("Add Staff Form is filled with above details<br>");
		if(FormData[17].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			//Assert.assertTrue(this.getPageTitle().toLowerCase().contains("entity"));
			return true;
		}else if(FormData[18].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickClear();
			cpo.clickGoBack();
			return false;
		}else if(FormData[19].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
	}catch(Exception e) {
		Reporter.log("Encountered issues while filling up the Add Edit Staff form"+e.getMessage());
	}
		return true;
		
	}
	public boolean fill_EditStaff_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[1].toString()!= "") {
		cpo.setParentEntity(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			cpo.setRolethroSearch(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			cpo.setFirstName(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			cpo.setMiddleName(FormData[4].toString());} 
		if(FormData[5].toString()!= "") {
			cpo.setLastName(FormData[5].toString());} 
		if(FormData[6].toString()!= "") {
			cpo.setEmail(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
		cpo.setPhonePri(FormData[7].toString());}
		if(FormData[8].toString()!= "") {
		cpo.setPhoneSec(FormData[8].toString());}
		if(FormData[9].toString()!= "") {
			cpo.setLandlinePri(FormData[9].toString());}
			if(FormData[10].toString()!= "") {
			cpo.setLandlineSec(FormData[10].toString());}
		if(FormData[11].toString()!= "") {
		cpo.setAddress1(FormData[11].toString());}
		if(FormData[12].toString()!= "") {
		cpo.setAddress2(FormData[12].toString());}
		if(FormData[13].toString()!= "") {
		cpo.setAddress3(FormData[13].toString());}
		if(FormData[14].toString()!= "") {
			cpo.setCountryThroSearch(FormData[14].toString());}
		if(FormData[15].toString()!= "") {
			cpo.setStateThroSearch(FormData[15].toString());}
		if(FormData[16].toString()!= "") {
			cpo.setCityThroSearch(FormData[16].toString());}
		if(FormData[17].toString()!= "") {
			cpo.setPinCode(FormData[17].toString());}
		Reporter.log("Edit Staff Form is filled with above details<br>");
		if(FormData[18].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}
		}catch(Exception e) {
		Reporter.log("Encountered issues while filling up the Add Edit Staff form"+e.getMessage());
	}
		return true;
		
	}

	
}
