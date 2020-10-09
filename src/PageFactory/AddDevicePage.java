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
public class AddDevicePage {
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	@FindBy(id="txtDeviceSrlNo")
	WebElement txt_deviceSrNo;
	
	@FindBy(id="txtMobileNumber")
	WebElement txt_mobileNo;
	
	@FindBy(xpath="//input[@id='chkIsStIssued']/following-sibling::span[1]")
	WebElement chk_issuedBySatTrax;
	
	
	public AddDevicePage(WebDriver driver){
		this.driver = driver;
		jse = (JavascriptExecutor) driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	} 
	public void setDeviceSrNo(String DeviceSerialNo){
		txt_deviceSrNo.clear();
		txt_deviceSrNo.sendKeys(DeviceSerialNo);
		Reporter.log("Device Serial No "+DeviceSerialNo+"<br>");
	}
	
	public void setMobileNo(String MobileNo){
		txt_mobileNo.clear();
		txt_mobileNo.sendKeys(MobileNo);
		Reporter.log("Mobile No "+MobileNo+"<br>");
	}
	
	public void setIssuedBySatTrax(Boolean bflag){
		if(bflag) {
			try {
				chk_issuedBySatTrax.click();
				Reporter.log("Issued By SatTrax "+bflag+"<br>");
			}catch(Exception e) {
				Reporter.log("Javascript execution for Issued By SatTrax checkbox+\"<br>\"");
			}
		}
	}
	
	public boolean fill_AddDevice_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntity(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
			this.setDeviceSrNo(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			this.setMobileNo(FormData[2].toString());}
		if(FormData[3].toString().equalsIgnoreCase("Yes")) {
			this.setIssuedBySatTrax(true);
		}
		Reporter.log("Add Device Form is filled with above details<br>");
		if(FormData[4].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}else if(FormData[5].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickClear();
			cpo.clickGoBack();
			return false;
		}else if(FormData[6].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
	}catch(Exception e) {
		Reporter.log("Encountered issues while filling up the Add Edit Device form"+e.getMessage());
	}
		return true;
		
	}
	public boolean fill_EditDevice_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[1].toString()!= "") {
		cpo.setParentEntity(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			this.setDeviceSrNo(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			this.setMobileNo(FormData[3].toString());}
		if(FormData[4].toString().equalsIgnoreCase("Yes")) {
			this.setIssuedBySatTrax(true);
		}
		Reporter.log("Add Device Form is filled with above details<br>");
		if(FormData[5].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}
	}catch(Exception e) {
		Reporter.log("Encountered issues while filling up the Edit Device form"+e.getMessage());
	}
		return true;
		
	}
	
	
}
