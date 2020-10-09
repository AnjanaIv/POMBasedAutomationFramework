package PageFactory;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
/*
 * author: arpita.hegde
 * reviewer : anjana.iv
 * Purpose : Add and Edit Student features Object Locators and Methods
 */
public class AddStudentPage {

	
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
	
	// Entity dropdown image object
	@FindBy(xpath="//span[@id='ddlEntity_dropdown']/span")
	WebElement img_Entity;
	
	String sDOBDivId = "e-DatePickDOB";
	@FindBy(id="DatePickDOB-img")
	WebElement img_DOB;
	
	// Class drop down object
		@FindBy(id="dd1Class_dropdown")
		WebElement dd_Class;
		
		//Class input object
		@FindBy(id="ddlClass_hidden")
		WebElement txt_Class;
		
		// Class search input object
		@FindBy(id="dd1Class_inputSearch")
		WebElement txt_Classsearch;
		
		// Class dropdown image object
		@FindBy(xpath="//span[@id='dd1Class_dropdown']/span")
		WebElement img_Class;
	
		// Section drop down object
		@FindBy(id="dd1Section_dropdown")
		WebElement dd_Section;
					
		//Class input object
		@FindBy(id="ddlSection_hidden")
		WebElement txt_Section;
					
		// Class search input object
		@FindBy(id="dd1Section_inputSearch")
		WebElement txt_Sectionsearch;
					
		// Class dropdown image object
		@FindBy(xpath="//span[@id='dd1Section_dropdown']/span")
		WebElement img_Section;
	
		@FindBy(id="RegistrationNumber")
		WebElement txt_RegNumber;
					
		@FindBy(id="UserSearch")
		WebElement txt_SearchParent;
	
	String sSearchParentDivID = "UserSearch_suggestion";
	@FindBy(xpath="//div[@id='UserSearch_suggestion']//table[@class='e-atc-tableContent']")
	WebElement tbl_SearchParent;
	
	String span_SearchParent ="//div[@id='UserSearch_suggestion']//table[@class='e-atc-tableContent']/tbody/tr";	
	
	
	@FindBy(xpath="//table[@role='listbox']")
	WebElement tbl_ResultParents;
	
	@FindBy(id="btnSaveContinue")
	WebElement btn_AddParent;

	public AddStudentPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void setRegNumber(String RegistrationNumber){
		txt_RegNumber.clear();
		Random randomNum = new Random();
		int regNum = 0 + randomNum.nextInt(1000);
		txt_RegNumber.sendKeys("AUTO"+regNum);
		SessionDetails.studentRegNumber = "AUTO"+regNum;
		Reporter.log("RegistrationNumber :"+"AUTO"+regNum+"<br>");
	}
	
	
	public void setEntity(String Entity){
		txt_Entity.sendKeys(Entity);
		Reporter.log(" Entity "+Entity+"<br>");
	}
	public void setEntityThroSearch(String Entity){
		txt_Entity.sendKeys(Entity);
		Reporter.log(" Entity "+Entity+"<br>");
	}
	
	public void setDOB(String DOB){
		try {
		jse.executeScript("arguments[0].scrollIntoView(true);",img_DOB);
		img_DOB.click();
		DateHandler date = new DateHandler(driver);
		date.selectDate(sDOBDivId,DOB);
		Reporter.log(" DOB "+DOB+"<br>");
		}catch(Exception e) {
			Reporter.log("Unable to select the date "+ e.getMessage());
		}
	}
	
	
	public void setClass(String Class){
		txt_Class.sendKeys(Class);
		Reporter.log(" Class "+Class+"<br>");
	}
	public void setClassThroSearch(String Class){
		txt_Class.sendKeys(Class);
		Reporter.log(" Class "+Class+"<br>");
	}
	
	public void setSection(String Section){
		txt_Section.sendKeys(Section);
		Reporter.log(" Section "+Section+"<br>");
	}
	public void setSectionThroSearch(String Section){
		txt_Section.sendKeys(Section);
		Reporter.log(" Section "+Section+"<br>");
	}
	
	public void setRegistrationNumber(String RegistrationNumber){
		txt_RegNumber.clear();
		txt_RegNumber.sendKeys(RegistrationNumber);
		Reporter.log(" RegistrationNumber "+RegistrationNumber+"<br>");
	}
	
	
	public void setSearchParentThroSearch(String SearchParent,String selectParent){
		try {
			jse.executeScript("arguments[0].scrollIntoView(true);",txt_SearchParent);
		txt_SearchParent.sendKeys(SearchParent);
		Thread.sleep(100);
		if(tbl_SearchParent.isDisplayed()) {
			List<WebElement> links = driver.findElements(By.xpath(span_SearchParent));
			for (WebElement e  : links)
			{
				//match with the partial search
				if(!selectParent.equalsIgnoreCase("")) {
					List<WebElement> colVals = e.findElements(By.tagName("td"));
					for(int j=0; j<colVals.size(); j++){
							jse.executeScript("arguments[0].scrollIntoView(true);",colVals.get(j));
							if(colVals.get(j).getText().equalsIgnoreCase(selectParent)) {
								e.click();
								Reporter.log("PASS : Data retrieved for Search Parent for keyword "+SearchParent+" : "+e.getText()+" and parent selected has matching keyword :"+selectParent);
							}
						}
					}
				else {
				if(e.getText().contains(SearchParent)) {
					Reporter.log("PASS : Data retrieved for Search Parent for search keyword "+SearchParent+" : "+e.getText());
					e.click();
					//each column & row to iterated to match with keyword
				}
				}
			}			
		}
		if(btn_AddParent.isDisplayed()) {
			clickAddParent();
		}	
		Reporter.log("SearchParent "+SearchParent+"<br>");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clickAddParent(){
			btn_AddParent.click();
			Reporter.log("Clicked on AddParent button<br>");
	}
	
	
	public boolean fill_AddStudent_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		jse = (JavascriptExecutor) driver;
		if(FormData[0].toString()!= "" && !sForm.equalsIgnoreCase("Edit")) {
		this.setEntity(FormData[0].toString());}		
		if(FormData[1].toString()!= "") {
			cpo.setFirstName(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			cpo.setMiddleName(FormData[2].toString());} 
		if(FormData[3].toString()!= "") {
			cpo.setLastName(FormData[3].toString());} 
		if(FormData[4].toString()!= "") {
		this.setDOB(FormData[4].toString());}
		if(FormData[5].toString()!= "") {
		this.setClass(FormData[5].toString());}
		if(FormData[6].toString()!= "") {
		this.setSection(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
		this.setRegNumber(FormData[7].toString());}		
		if(FormData[8].toString()!= "") {
			cpo.setAddress1(FormData[8].toString());}
		if(FormData[9].toString()!= "") {
			cpo.setAddress2(FormData[9].toString());}
		if(FormData[10].toString()!= "") {
			cpo.setAddress3(FormData[10].toString());}
		if(FormData[11].toString()!= "") {
			cpo.setCountryThroSearch(FormData[11].toString());}
		if(FormData[12].toString()!= "") {
			cpo.setStateThroSearch(FormData[12].toString());}
		if(FormData[13].toString()!= "") {
			cpo.setCityThroSearch(FormData[13].toString());}
		if(FormData[14].toString()!= "") {
			cpo.setPinCode(FormData[14].toString());}
		if(FormData[15].toString()!= "") {
			this.setSearchParentThroSearch(FormData[15].toString(),FormData[16].toString());}
		Reporter.log("Add student Form is filled with above details<br>");
		if(FormData[17].toString().equalsIgnoreCase("TRUE")) {
			if(btn_AddParent.isDisplayed()) {
				clickAddParent();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				return true;}
			else
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
			Reporter.log("Encountered issues while filling up the Add Student form"+e.getMessage());
		}
		return true;
		
	}
	
	public boolean fill_EditStudent_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		jse = (JavascriptExecutor) driver;
		if(FormData[1].toString()!= "" && !sForm.equalsIgnoreCase("Edit")) {
		this.setEntity(FormData[1].toString());}		
		if(FormData[2].toString()!= "") {
			cpo.setFirstName(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			cpo.setMiddleName(FormData[3].toString());} 
		if(FormData[4].toString()!= "") {
			cpo.setLastName(FormData[4].toString());} 
		if(FormData[5].toString()!= "") {
		this.setDOB(FormData[5].toString());}
		if(FormData[6].toString()!= "") {
		this.setClass(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
		this.setSection(FormData[7].toString());}
		if(FormData[8].toString()!= "") {
		this.setRegNumber(FormData[8].toString());}		
		if(FormData[9].toString()!= "") {
			cpo.setAddress1(FormData[9].toString());}
		if(FormData[10].toString()!= "") {
			cpo.setAddress2(FormData[10].toString());}
		if(FormData[11].toString()!= "") {
			cpo.setAddress3(FormData[11].toString());}
		if(FormData[12].toString()!= "") {
			cpo.selectCountry(FormData[12].toString());}
		if(FormData[13].toString()!= "") {
			cpo.setState(FormData[13].toString());}
		if(FormData[14].toString()!= "") {
			cpo.setCity(FormData[14].toString());}
		if(FormData[15].toString()!= "") {
			cpo.setPinCode(FormData[15].toString());}
		if(FormData[16].toString()!= "") {
			if(driver.findElement(By.xpath("//span[@class='e-icon e-close']")).isDisplayed()){
				driver.findElement(By.xpath("//span[@class='e-icon e-close']")).click();
			}
			this.setSearchParentThroSearch(FormData[16].toString(),FormData[17].toString());}
		Reporter.log("Edit student Form is filled with above details<br>");
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			//Assert.assertTrue(this.getPageTitle().toLowerCase().contains("entity"));
			return true;
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Edit Student form"+e.getMessage());
			return false;
		}
	}
	
}