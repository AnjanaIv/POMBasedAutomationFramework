package PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class CommonPageObjects {
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	@FindBy(xpath="//h4[@class='title']")
	WebElement hdr_SuccessPage;
	
	@FindBy(id="btnCreate")
	WebElement btn_Create;
	
	@FindBy(id="txtFname")
	WebElement txt_FirstName;
	
	@FindBy(id="txtMname")
	WebElement txt_MiddleName;
	
	@FindBy(id="txtLname")
	WebElement txt_LastName;
	
	@FindBy(id="AddressLine1")
	WebElement txt_Address1;
	
	@FindBy(id="AddressLine2")
	WebElement txt_Address2;
	
	@FindBy(id="AddressLine3")
	WebElement txt_Address3;
	
	//Country drop down object
		@FindBy(id="ddlCountry")
		WebElement dd_Country;
			
		//Country input object
		@FindBy(id="ddlCountry_hidden")
		WebElement txt_Country;
			
		//Country search input object
		@FindBy(id="ddlCountry_inputSearch")
		WebElement txt_Countrysearch;
		
		//Country dropdown image object
		@FindBy(xpath="//span[@id='ddlCountry_dropdown']/span")
		WebElement img_Country;
			
		
		@FindBy(xpath="//span[@id='ddlRole_dropdown']/span")
		WebElement img_Role;
		
		@FindBy(id="ddlRole_inputSearch")
		WebElement txt_Rolesearch;
		
		//State drop down object
		@FindBy(id="ddlState")
		WebElement dd_State;
				
     	//State input object
		@FindBy(id="ddlState_hidden")
		WebElement txt_State;
				
		//State search input object
		@FindBy(id="ddlState_inputSearch")
		WebElement txt_Statesearch;
			
		//State dropdown image object
		@FindBy(xpath="//span[@id='ddlState_dropdown']/span")
		WebElement img_State;
				
		//City drop down object
		@FindBy(id="ddlCity")
		WebElement dd_City;
					
		//City input object
		@FindBy(id="ddlCity_hidden")
		WebElement txt_City;
					
		//City search input object
		@FindBy(id="ddlCity_inputSearch")
		WebElement txt_Citysearch;
				
		//City dropdown image object
		@FindBy(xpath="//span[@id='ddlCity_dropdown']/span")
		WebElement img_City;
		
		@FindBy(xpath="//input[contains(@id,'Pincode')]")
		WebElement txt_PinCode;	
		
		@FindBy(xpath="//input[contains(@name,'Email')]")
		WebElement txt_Email;	
		
		@FindBy(id="txtLandlinePrimary")
		WebElement txt_LandlinePri;
		
		@FindBy(id="txtLandlineSecondary")
		WebElement txt_LandlineSec;
		
		//Entity drop down object
		@FindBy(id="ddlEntity_dropdown")
		WebElement dd_Entity;
		
		//Entity input object
		@FindBy(id="ddlEntity_hidden")
		WebElement txt_Entity;
		
		//Entity search input object
		@FindBy(id="ddlEntity_inputSearch")
		WebElement txt_Entitysearch;
		
		//Entity dropdown image object
		@FindBy(xpath="//span[@id='ddlEntity_dropdown']/span")
		WebElement img_Entity;
		
		//Role drop down object
				@FindBy(id="dd1Role_dropdown")
				WebElement dd_Role;
	@FindBy(id="mobilePrimary")
	WebElement txt_MobilePri;
				
	@FindBy(id="mobileSecondary")
	WebElement txt_MobileSec;			
	
	@FindBy(xpath="//button[contains(@id,'btnSave')]")
	WebElement btn_Save;
	
	@FindBy(id="btnClear")
	WebElement btn_Clear;
	
	@FindBy(xpath="//button[text()='Submit']")
	WebElement btn_Submit;
	
	@FindBy(id="btnReset")
	WebElement btn_Reset;
	
	@FindBy(id="btngoBack")
	WebElement btn_GoBack;
	
	@FindBy(id="txtEntityName")
	WebElement txt_entityName;
	
	public CommonPageObjects(WebDriver driver){
		this.driver = driver;
		jse = (JavascriptExecutor) driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public String getPageTitle(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",hdr_SuccessPage);
		return hdr_SuccessPage.getText();
	}
	public void setEntityName(String strEntityName){
		txt_entityName.sendKeys(strEntityName);
		Reporter.log("Entity Name "+strEntityName+"<br>");
	}
	public void clickSave(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_Save);	
		btn_Save.click();
		Reporter.log("Clicked on Save button<br>");
	}
	
	public void clickSubmit(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_Submit);	
		btn_Submit.click();
		Reporter.log("Clicked on Submit button<br>");
	}
	public void clickClear(){
		jse = (JavascriptExecutor) driver;
		try {
			jse.executeScript("window.scrollBy(0,1000)");
			btn_Clear.click();
			Reporter.log("Clicked on Clear button<br>");
		}catch(Exception e) {
			System.out.println("Unable to click Clear button");
			jse.executeScript("document.getElementById('btnClear').click()");
			Reporter.log("Clicked on Clear button<br>");
		}
	}
	
	public void clickReset(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_Reset);	
		btn_Reset.click();
		Reporter.log("Clicked on btn_Reset button<br>");}
	
	public void clickGoBack(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_GoBack);
			btn_GoBack.click();
			Reporter.log("Clicked on Goback button<br>");

	}
	public void selectCountry(String Country){
		txt_Country.sendKeys(Country);
		Reporter.log("Country "+Country+"<br>");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void setPhonePri(String PhonePrimary){
		jse.executeScript("arguments[0].scrollIntoView(true);",txt_MobilePri);
		txt_MobilePri.clear();
		txt_MobilePri.sendKeys(PhonePrimary);
		Reporter.log("PhonePrimary "+PhonePrimary+"<br>");
	}
	public void setPhoneSec(String PhoneSec){
		txt_MobileSec.clear();
		txt_MobileSec.sendKeys(PhoneSec);
		Reporter.log("Phone Sec "+PhoneSec+"<br>");
	}
	public void setRole(String Role){
		dd_Role.sendKeys(Role);
		Reporter.log("Role "+Role+"<br>");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void setRolethroSearch(String Role){		
		try {
			//jse.executeScript("arguments[0].scrollIntoView(true);",img_Role);
			/*Deselecting already selected role for Edit scenario
			 * if(driver.findElement(By.xpath("//span[@id='ddlRole_container']//span[@class='e-icon e-close']")).isDisplayed()){
				driver.findElement(By.xpath("//span[@id='ddlRole_container']//span[@class='e-icon e-close']")).click();
			}*/
			img_Role.click();
			txt_Rolesearch.sendKeys(Role);
			List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlRole_popup']//ul/li"));
			for (WebElement e  : links)
			{
				if(e.getText().equalsIgnoreCase(Role)){
					e.click();
					Thread.sleep(1000);
				}
			}
			Reporter.log("Role "+Role+"<br>");
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void setCountryThroSearch(String Country){
		try {
		//jse.executeScript("arguments[0].scrollIntoView(true);",img_Country);
		img_Country.click();
		txt_Countrysearch.sendKeys(Country);
		List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlCountry_popup']//ul/li"));
		for (WebElement e  : links)
		{
			if(e.getText().equalsIgnoreCase(Country)){
				e.click();
				Thread.sleep(1000);
			}
		}
		Reporter.log("Country "+Country+"<br>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setState(String State){
		txt_State.sendKeys(State);
		Reporter.log("State "+State+"<br>");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void setStateThroSearch(String State){
		try {
			//jse.executeScript("arguments[0].scrollIntoView(true);",img_State);
			img_State.click();
			txt_Statesearch.sendKeys(State);
			List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlState_popup']//ul/li"));
			for (WebElement e  : links)
			{
				if(e.getText().equalsIgnoreCase(State)){
					e.click();
					Thread.sleep(1000);
				}
			}
			Reporter.log("State "+State+"<br>");
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void setCity(String City){
		Reporter.log("City "+City+"<br>");
		txt_City.sendKeys(City);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void setCityThroSearch(String City){
		try {
			//jse.executeScript("arguments[0].scrollIntoView(true);",img_City);
			img_City.click();
			txt_Citysearch.sendKeys(City);
			List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlCity_popup']//ul/li"));
			for (WebElement e  : links)
			{
				if(e.getText().equalsIgnoreCase(City)){
					e.click();
					Thread.sleep(1000);
				}
			}
			Reporter.log("City "+City+"<br>");
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void setPinCode(String Pincode){
		jse = (JavascriptExecutor) driver;
		try {
		jse.executeScript("window.scrollBy(0,1000)");
		jse.executeScript("arguments[0].scrollIntoView(true);",txt_PinCode);
		txt_PinCode.clear();
		txt_PinCode.sendKeys(Pincode);
		Reporter.log("Pincode "+Pincode+"<br>");
		}
		catch(Exception e) {
			Reporter.log("focus to element did not work for scrollbar");
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			txt_PinCode.clear();
			txt_PinCode.sendKeys(Pincode);
			Reporter.log("Pincode "+Pincode+"<br>");
		}
	}
	public void setFirstName(String strFirstName){
		txt_FirstName.clear();
		txt_FirstName.sendKeys(strFirstName);
		Reporter.log("First Name "+strFirstName+"<br>");
	}
	
	public void setMiddleName(String strMiddleName){
		txt_MiddleName.clear();
		txt_MiddleName.sendKeys(strMiddleName);
		Reporter.log("Middle Name "+strMiddleName+"<br>");
	}
	
	public void setLastName(String strLastName){
		txt_LastName.clear();
		txt_LastName.sendKeys(strLastName);
		Reporter.log("Last Name "+strLastName+"<br>");
	}
	public void setAddress1(String Address){
		jse.executeScript("arguments[0].scrollIntoView(true);",txt_Address1);
		txt_Address1.clear();
		txt_Address1.sendKeys(Address);
		Reporter.log("Address "+Address+"<br>");
	}
	public void setAddress2(String Address2){
		jse.executeScript("arguments[0].scrollIntoView(true);",txt_Address2);
		txt_Address2.clear();
		txt_Address2.sendKeys(Address2);
		Reporter.log("Address2 "+Address2+"<br>");
	}
	public void setAddress3(String Address3){
		jse.executeScript("arguments[0].scrollIntoView(true);",txt_Address3);
		txt_Address3.clear();
		txt_Address3.sendKeys(Address3);
		Reporter.log("Address3 "+Address3+"<br>");
	}
	
	public void setLandlinePri(String LandlinePrimary){
		txt_LandlinePri.clear();
		txt_LandlinePri.sendKeys(LandlinePrimary);
		Reporter.log("LandlinePrimary "+LandlinePrimary+"<br>");
	}
	public void setLandlineSec(String LandlineSec){
		txt_LandlineSec.clear();
		txt_LandlineSec.sendKeys(LandlineSec);
		Reporter.log("Landline Sec "+LandlineSec+"<br>");
	}
	//Click on Add button
		public void clickAdd(){
			btn_Create.click();
		}
		
		public void setParentEntity(String ParentEntity){
			txt_Entity.sendKeys(ParentEntity);
			Reporter.log("Parents Entity "+ParentEntity+"<br>");
		}
		public void setParentEntityThroSearch(String ParentEntity){
			try {
			img_Entity.click();
			txt_Entitysearch.sendKeys(ParentEntity);
			List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlEntity_popup']//ul/li"));
			for (WebElement e  : links)
			{
				if(e.getText().equalsIgnoreCase(ParentEntity)){
					e.click();
					Thread.sleep(1000);
				}
			}
			Reporter.log("Parent Entity "+ParentEntity+"<br>");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void setEmail(String Email){
			txt_Email.clear();
			txt_Email.sendKeys(Email);
			Reporter.log("Email "+Email+"<br>");
		}
		/**
		* @return True if JavaScript Alert is present on the page otherwise false
		*/
		public boolean isAlertPresent(){
		try{
		driver.switchTo().alert();
		return true;
		}catch(NoAlertPresentException ex){
		return false;
		}
		}
		
		/**
		* If Javascript Alert is present on the page cancels it.
		*/
		public void handleAlert(){
		if(isAlertPresent()){
		Alert alert = driver.switchTo().alert();
		Reporter.log(alert.getText());
		System.out.println(alert.getText());
		alert.accept();
		}
		}
	
		public String getToastMessage(WebDriver driver) {
			WebElement dynamicElement = (new WebDriverWait(driver, 10))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id(".//*[@id='toast-container']/div/div[1][@class='toast-message']")));
			return dynamicElement.getText();
			
		}
		
		
}

