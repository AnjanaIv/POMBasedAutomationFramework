package PageFactory;
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

import config.SessionDetails;
import utility.DateHandler;
import utility.Xls_Reader;
/*
 * author: arpita.hegde
 * reviewer : anjana.iv
 * Purpose : Add and Edit Parent features Object Locators and Methods
 */
public class AddParentPage {
		/**
		 * All WebElements are identified by @FindBy annotation
		 */
		WebDriver driver;
		Actions action;
		JavascriptExecutor jse;
			
		// Entity drop down object
		@FindBy(id="ddlEntity_dropdown")
		WebElement dd_Entity;
		
		// Entity input object
		@FindBy(id="ddlEntity_hidden")
		WebElement txt_Entity;
		
		// Entity search input object
		@FindBy(id="ddlEntity_inputSearch")
		WebElement txt_Entitysearch;
		
		//Role drop down object
		@FindBy(id="ddlRole_dropdown")
		WebElement dd_Role;
		
		//Mother first name object
		@FindBy(id="txtPFname")
		WebElement txt_Mother_Fname;
				
		//Mother middle name object
		@FindBy(id="txtPMname")
		WebElement txt_Mother_Mname;
				
		//Mother last name object
		@FindBy(id="txtPMLname")
		WebElement txt_Mother_Lname;
		
		//Father first name object
		@FindBy(xpath="//label[text()='Father First Name']/following-sibling::input[@id='txtFname']")
		WebElement txt_Father_Fname;
		
				//Father middle name object
				@FindBy(id="txtMname")
				WebElement txt_Father_Mname;
				
				//Father last name object
				@FindBy(xpath="//label[text()='Father Last Name']/following-sibling::input[@id='txtLname']")
				WebElement txt_Father_Lname;
				
				//Mother Email object
				@FindBy(id="txtMotherEmail")
				WebElement txt_MEmail;
				
				//Mother mobile object
				@FindBy(id="txtMMobile")
				WebElement txt_MMobile;
				
				//Father Email object
				@FindBy(id="txtFatherEmail")
				WebElement txt_FEmail;
				
				//Father mobile object
				@FindBy(id="txtFMobile")
				WebElement txt_FMobile;
				
			String sSearchParentDivID = "UserSearch_wrapper";
			@FindBy(xpath="//span[@id='UserSearch_wrapper']//ul/li")
			WebElement span_SearchParent;
			
			
				
	public AddParentPage(WebDriver driver){
		this.driver=driver;
		jse = (JavascriptExecutor) driver;
		////This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
		
	}
		public void setMother_Fname(String strMother_Fname){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_Mother_Fname);
			txt_Mother_Fname.clear();
		txt_Mother_Fname.sendKeys(strMother_Fname);
		Reporter.log("Mother_Fname "+strMother_Fname+"<br>");
		}
		public void setMother_Mname(String strMother_Mname){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_Mother_Mname);
			txt_Mother_Mname.clear();
			txt_Mother_Mname.sendKeys(strMother_Mname);
			Reporter.log("Mother_Mname "+strMother_Mname+"<br>");
		}
		public void setMother_Lname(String strMother_Lname){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_Mother_Lname);
			txt_Mother_Lname.clear();
			txt_Mother_Lname.sendKeys(strMother_Lname);
			Reporter.log("Mother_Lname "+strMother_Lname+"<br>");
		}
		
		public void setFather_Fname(String strFather_Fname){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_Father_Fname);
			txt_Father_Fname.clear();
			txt_Father_Fname.sendKeys(strFather_Fname);
			Reporter.log("Father_Fname "+strFather_Fname+"<br>");
		}
		public void setFather_Mname(String strFather_Mname){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_Father_Mname);
			txt_Father_Mname.clear();
			txt_Father_Mname.sendKeys(strFather_Mname);
			Reporter.log("Father_Mname "+strFather_Mname+"<br>");
			
		}
		public void setFather_Lname(String strFather_Lname){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_Father_Lname);
			txt_Father_Lname.clear();
			txt_Father_Lname.sendKeys(strFather_Lname);
			Reporter.log("Father_Lname "+strFather_Lname+"<br>");
		}
		
		public void setMEmail(String MEmail){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_MEmail);
			txt_MEmail.clear();
			txt_MEmail.sendKeys(MEmail);
			Reporter.log("Mother Email "+MEmail+"<br>");
			}
		
		public void setMMobile(String MMobile){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_MMobile);
			txt_MMobile.clear();
			txt_MMobile.sendKeys(MMobile);
			Reporter.log("Mother Mobile "+MMobile+"<br>");
		}			
		public void setFEmail(String FEmail){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_FEmail);
			txt_FEmail.clear();
			txt_FEmail.sendKeys(FEmail);
			Reporter.log("Father Email "+FEmail+"<br>");
			}
		
		public void setFMobile(String FMobile){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_FMobile);
			txt_FMobile.clear();
			txt_FMobile.sendKeys(FMobile);
			Reporter.log("Father Mobile"+FMobile+"<br>");
		}
		
		public void setPrimaryUser(String PrimaryUser){
			jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//label[text()='Primary User']/following-sibling::div/label[text()='"+PrimaryUser+"']")));
			if(!driver.findElement(By.xpath("//label[text()='Primary User']/following-sibling::div/label[text()='"+PrimaryUser+"']")).isSelected())
				driver.findElement(By.xpath("//label[text()='Primary User']/following-sibling::div/label[text()='"+PrimaryUser+"']")).click();
			Reporter.log("Primary User : "+PrimaryUser+"<br>");
			
		}
		public void setEntity(String Entity){
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_Entity);
			txt_Entity.clear();
			txt_Entity.sendKeys(Entity);
			Reporter.log(" Entity "+Entity+"<br>");
		}
		public void setEntityThroSearch(String Entity){
			txt_Entity.clear();
			txt_Entity.sendKeys(Entity);
			Reporter.log(" Entity "+Entity+"<br>");
		}

		public boolean fill_AddParent_form(Object[] FormData)throws Exception{
			CommonPageObjects cpo = new CommonPageObjects(driver);
			try {
if(FormData[0].toString()!= "") {
		this.setEntity(FormData[0].toString());}
if(FormData[1].toString()!= "") {
		this.setMother_Fname(FormData[1].toString());}
if(FormData[2].toString()!= "") {
		this.setMother_Mname(FormData[2].toString());} 
if(FormData[3].toString()!= "") {
		this.setMother_Lname(FormData[3].toString());} 
if(FormData[4].toString()!= "") {
		this.setFather_Fname(FormData[4].toString());}
if(FormData[5].toString()!= "") {
		this.setFather_Mname(FormData[5].toString());} 
if(FormData[6].toString()!= "") {
		this.setFather_Lname(FormData[6].toString());} 
if(FormData[7].toString()!= "") {
	this.setPrimaryUser(FormData[7].toString());}
if(FormData[8].toString()!= "") {
		this.setMEmail(FormData[8].toString());}
if(FormData[9].toString()!= "") {
		this.setMMobile(FormData[9].toString());}
if(FormData[10].toString()!= "") {
		this.setFEmail(FormData[10].toString());}
if(FormData[11].toString()!= "") {
		this.setFMobile(FormData[11].toString());}
if(FormData[12].toString()!= "") {
	cpo.setLandlinePri(FormData[12].toString());}
if(FormData[13].toString()!= "") {
	cpo.setLandlineSec(FormData[13].toString());}	
if(FormData[14].toString()!= "") {
	cpo.setAddress1(FormData[14].toString());}
if(FormData[15].toString()!= "") {
	cpo.setAddress2(FormData[15].toString());}
if(FormData[16].toString()!= "") {
	cpo.setAddress3(FormData[16].toString());}
if(FormData[17].toString()!= "") {
	cpo.setCountryThroSearch(FormData[17].toString());}
if(FormData[18].toString()!= "") {
	cpo.setStateThroSearch(FormData[18].toString());}
if(FormData[19].toString()!= "") {
	cpo.setCityThroSearch(FormData[19].toString());}
if(FormData[20].toString()!= "") {
	cpo.setPinCode(FormData[20].toString());}
Reporter.log("Add parent Form is filled with above details<br>");
//fetch SatTraxID from Wards field in the Add Parent Page and save it as Student SatTrax ID in excel sheet
jse.executeScript("arguments[0].scrollIntoView(true);",span_SearchParent);
if(span_SearchParent.isDisplayed()) {
	SessionDetails.SatTraxID = span_SearchParent.getText().split(":")[0].trim();
	//write to xls with the SatTrax IDs for all students for future references
	Xls_Reader reader = new Xls_Reader();
	reader.writeToExcel("StudentData", "SatTraxID",SessionDetails.currentRowRef, SessionDetails.SatTraxID);
}


if(FormData[22].toString().equalsIgnoreCase("TRUE")) {
	cpo.clickSave();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	return true;
}else if(FormData[23].toString().equalsIgnoreCase("TRUE")) {
	cpo.clickClear();
	cpo.clickGoBack();
	return false;
}else if(FormData[24].toString().equalsIgnoreCase("TRUE")) {
	cpo.clickGoBack();
	return false;
}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Parent form"+e.getMessage());
			return false;
		}
return true;

}
		public boolean fill_EditParent_form(Object[] FormData)throws Exception{
			CommonPageObjects cpo = new CommonPageObjects(driver);
			try {
/*if(FormData[0].toString()!= "") {
		this.setEntity(FormData[0].toString());}*/
if(FormData[2].toString()!= "") {
		this.setMother_Fname(FormData[2].toString());}
if(FormData[3].toString()!= "") {
		this.setMother_Mname(FormData[3].toString());} 
if(FormData[4].toString()!= "") {
		this.setMother_Lname(FormData[4].toString());} 
if(FormData[5].toString()!= "") {
		this.setFather_Fname(FormData[5].toString());}
if(FormData[6].toString()!= "") {
		this.setFather_Mname(FormData[6].toString());} 
if(FormData[7].toString()!= "") {
		this.setFather_Lname(FormData[7].toString());} 
if(FormData[8].toString()!= "") {
	this.setPrimaryUser(FormData[8].toString());}
if(FormData[9].toString()!= "") {
		this.setMEmail(FormData[9].toString());}
if(FormData[10].toString()!= "") {
		this.setMMobile(FormData[10].toString());}
if(FormData[11].toString()!= "") {
		this.setFEmail(FormData[11].toString());}
if(FormData[12].toString()!= "") {
		this.setFMobile(FormData[12].toString());}
if(FormData[13].toString()!= "") {
	cpo.setLandlinePri(FormData[13].toString());}
if(FormData[14].toString()!= "") {
	cpo.setLandlineSec(FormData[14].toString());}	
if(FormData[15].toString()!= "") {
	cpo.setAddress1(FormData[15].toString());}
if(FormData[16].toString()!= "") {
	cpo.setAddress2(FormData[16].toString());}
if(FormData[17].toString()!= "") {
	cpo.setAddress3(FormData[17].toString());}
if(FormData[18].toString()!= "") {
	cpo.setCountryThroSearch(FormData[18].toString());}
if(FormData[19].toString()!= "") {
	cpo.setStateThroSearch(FormData[19].toString());}
if(FormData[20].toString()!= "") {
	cpo.setCityThroSearch(FormData[20].toString());}
if(FormData[21].toString()!= "") {
	cpo.setPinCode(FormData[21].toString());}
Reporter.log("Edit parent Form is filled with above details<br>");
if(FormData[22].toString().equalsIgnoreCase("TRUE")) {
	cpo.clickSave();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	return true;
}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Edit Parent form"+e.getMessage());
			return false;
		}
return true;

}		
}
			
	
		
			
				
				

