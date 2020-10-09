package PageFactory;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Entity Object Locators and Methods
 */
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

public class AddEntityPage {
	
	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	@FindBy(xpath="//input[@id='chkParent']")
	WebElement chk_parentDetails;
	
	@FindBy(id="txtPhonePrimary")
	WebElement txt_PhonePri;
	
	@FindBy(id="txtPhoneSecondary")
	WebElement txt_PhoneSec;
	
	@FindBy(id="txtAddress")
	WebElement txt_Address1;
	
	@FindBy(id="txtAddress2")
	WebElement txt_Address2;
	
	@FindBy(id="txtAddress3")
	WebElement txt_Address3;
	
	
	public AddEntityPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void setAddress1(String Address){
		txt_Address1.clear();
		txt_Address1.sendKeys(Address);
		Reporter.log("Address "+Address+"<br>");
	}
	public void setAddress2(String Address2){
		txt_Address2.clear();
		txt_Address2.sendKeys(Address2);
		Reporter.log("Address2 "+Address2+"<br>");
	}
	public void setAddress3(String Address3){
		txt_Address3.clear();
		txt_Address3.sendKeys(Address3);
		Reporter.log("Address3 "+Address3+"<br>");
	}

	public void setPhonePri(String PhonePrimary){
		txt_PhonePri.clear();
		txt_PhonePri.sendKeys(PhonePrimary);
		Reporter.log("PhonePrimary "+PhonePrimary+"<br>");
	}
	public void setPhoneSec(String PhoneSec){
		txt_PhoneSec.clear();
		txt_PhoneSec.sendKeys(PhoneSec);
		Reporter.log("Phone Sec "+PhoneSec+"<br>");
	}

	public void setBindDetailsofParent(Boolean bflag){
		if(bflag) {
			try {
				chk_parentDetails.click();
				Reporter.log("Bind Parents details "+bflag+"<br>");
			}catch(Exception e) {
				Reporter.log("Javascript execution for Bind Parents details checkbox+\"<br>\"");
			jse = (JavascriptExecutor) driver;
			jse.executeScript("document.getElementById('chkParent').click()");
			Reporter.log("Bind Parents details "+bflag+"<br>");
			}
		}
	}
	
	public boolean fill_AddEntity_form(Object[] FormData,String sForm) throws Exception{
		try {
		CommonPageObjects cpo = new CommonPageObjects(driver);
		if(FormData[0].toString()!= "" && !sForm.equalsIgnoreCase("Edit")) {
			cpo.setEntityName(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
		cpo.setParentEntity(FormData[1].toString());}
		if(FormData[2].toString().equalsIgnoreCase("true")) {
			this.setBindDetailsofParent(true);
		}else {
		if(FormData[3].toString()!= "") {
		cpo.setLandlinePri(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			cpo.setLandlineSec(FormData[4].toString());}
		if(FormData[5].toString()!= "") {
		cpo.setEmail(FormData[5].toString());}
		if(FormData[6].toString()!= "") {
		this.setPhonePri(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
		this.setPhoneSec(FormData[7].toString());}
		if(FormData[8].toString()!= "") {
		this.setAddress1(FormData[8].toString());}
		if(FormData[9].toString()!= "") {
		this.setAddress2(FormData[9].toString());}
		if(FormData[10].toString()!= "") {
		this.setAddress3(FormData[10].toString());}
		if(FormData[11].toString()!= "") {
			cpo.setCountryThroSearch(FormData[11].toString());}
		if(FormData[12].toString()!= "") {
			cpo.setStateThroSearch(FormData[12].toString());}
		if(FormData[13].toString()!= "") {
			cpo.setCityThroSearch(FormData[13].toString());}
		if(FormData[14].toString()!= "") {
			cpo.setPinCode(FormData[14].toString());}
		}
		Reporter.log("Add Entity Form is filled with above details<br>");
		if(FormData[15].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			Thread.sleep(2000);
			Reporter.log("Verifying the page title to ensure Create Entity was successful");
			Assert.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Entity"));
			Reporter.log("Page Title Verification Successful. Actual "+cpo.getPageTitle()+"<br>");
			/*if(driver.findElement(By.className("toast-title")).isDisplayed()) {
				Actions	actions = new Actions(driver);
				actions.moveToElement(driver.findElement(By.className("toast-title")));
				actions.contextClick(driver.findElement(By.className("toast-title")));
			}*/
			return true;
		}else if(FormData[16].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickClear();
			cpo.clickGoBack();
			return false;
		}else if(FormData[17].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Edit Entity form"+e.getMessage());
			return false;
		}
		return true;
		
	}
	
}
