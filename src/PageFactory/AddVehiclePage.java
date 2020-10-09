package PageFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import utility.DateHandler;

/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Stop Object Locators and Methods
 */
public class AddVehiclePage {
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
	
	@FindBy(xpath="//span[@id='ddlVehicleType_dropdown']/span")
	WebElement img_vehicleType;
	
	@FindBy(id="ddlVehicleMake_inputSearch")
	WebElement txt_vehicleMake;
	
	@FindBy(xpath="//span[@id='ddlVehicleMake_dropdown']/span")
	WebElement img_vehicleMake;

	@FindBy(xpath="//*[text()='Seating Capacity']/following-sibling::span[@role='spinbutton']//input[1]")
	WebElement txt_seatingCapacity;
	
	@FindBy(xpath="//*[text()='Seating Capacity']/following-sibling::span//span[@role='button' and @aria-label='Increase Value']")
	WebElement img_seatingCapacity;
	
	@FindBy(xpath="//*[text()='Fuel Average']/following-sibling::span[@role='spinbutton']//input[1]")
	WebElement txt_FuelAvg;
	
	@FindBy(xpath="//*[text()='Fuel Average']/following-sibling::span//span[@role='button' and @aria-label='Increase Value']")
	WebElement img_FuelAvg;
	
	@FindBy(xpath="//span[@id='ddlDevice_dropdown']/span")
	WebElement img_Device;
	
	String sMfgDateDivId = "e-dtMfgYear";
	@FindBy(id="dtMfgYear-img")
	WebElement img_MfgDate;
	
	
	
	public AddVehiclePage(WebDriver driver){
		this.driver = driver;
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void setRegNo(String regNumber){
		txt_RegNo.clear();
		txt_RegNo.sendKeys(regNumber);
		Reporter.log("Registration Number "+regNumber+"<br>");
	}
	
	public void setVehicleType(String vehType){
		try {
			img_vehicleType.click();
		txt_vehicle.sendKeys(vehType);
		List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlVehicleType_popup']//ul/li"));
		for (WebElement e  : links)
		{
			if(e.getText().equalsIgnoreCase(vehType)){
				e.click();
				Thread.sleep(1000);
			}
		}
		Reporter.log("Vehicle Type "+vehType+"<br>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setVehicleMake(String vehicleMake){
		try {
			img_vehicleMake.click();
		txt_vehicleMake.sendKeys(vehicleMake);
		List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlVehicleMake_popup']//ul/li"));
		for (WebElement e  : links)
		{
			if(e.getText().equalsIgnoreCase(vehicleMake)){
				e.click();
				Thread.sleep(1000);
			}
		}
		Reporter.log("Vehicle Make "+vehicleMake+"<br>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMfgDate(String mfgDate){
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,1000)");
		jse.executeScript("arguments[0].scrollIntoView(true);",img_MfgDate);
		img_MfgDate.click();
		DateHandler date = new DateHandler(driver);
		date.selectDate(sMfgDateDivId,mfgDate);
		Reporter.log("Manufacturing Date "+mfgDate+"<br>");
	}
	
	public void setSeatingCapacity(String seatingCapacity){
		try {
			int i=0;
			int iClick = Integer.valueOf(seatingCapacity);
			while(i<iClick) {
				img_seatingCapacity.click();
				i++;
			}
			Reporter.log("Seating Capacity "+seatingCapacity+"<br>");
	}catch(Exception e) {
		Reporter.log("Error while filling the field Fuel Average "+seatingCapacity+"<br>");
	}
	}
	
	public void setFuelAvg(String fuelAvg){
		try {
			int i=0;
			int iClick = Integer.valueOf(fuelAvg);
			while(i<iClick) {
				img_FuelAvg.click();
				i++;
			}
		Reporter.log("Fuel Average "+fuelAvg+"<br>");
		}catch(Exception e) {
			Reporter.log("Error while filling the field Fuel Average "+fuelAvg+"<br>");
		}
	}
	
	public void setDevice(String device){
		try {
			img_Device.click();
		List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlDevice_popup']//ul/li"));
		for (WebElement e  : links)
		{
			if(e.getText().equalsIgnoreCase(device)){
				e.click();
				Thread.sleep(1000);
			}
		}
		Reporter.log("device "+device+"<br>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean fill_AddVehicle_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntity(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
		this.setRegNo(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			this.setVehicleType(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			this.setVehicleMake(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			this.setDevice(FormData[4].toString());}
		if(FormData[5].toString()!= "") {
			this.setMfgDate(FormData[5].toString());}
		if(FormData[6].toString()!= "") {
			this.setSeatingCapacity(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
			this.setFuelAvg(FormData[7].toString());}
		if(FormData[8].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSubmit();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Edit Vehicle form"+e.getMessage());
		}
		return false;
	}
	public boolean fill_EditVehicle_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[1].toString()!= "" && !sForm.equalsIgnoreCase("Edit")) {
		cpo.setParentEntity(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
		this.setRegNo(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			this.setVehicleType(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			this.setVehicleMake(FormData[4].toString());}
		if(FormData[5].toString()!= "") {
			this.setDevice(FormData[5].toString());}
		if(FormData[6].toString()!= "") {
			this.setMfgDate(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
			this.setSeatingCapacity(FormData[7].toString());}
		if(FormData[8].toString()!= "") {
			this.setFuelAvg(FormData[8].toString());}
		if(FormData[9].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSubmit();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Edit Vehicle form"+e.getMessage());
		}
		return false;
	}

}

