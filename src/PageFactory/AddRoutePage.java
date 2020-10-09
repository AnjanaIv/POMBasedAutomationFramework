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
public class AddRoutePage {
	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	@FindBy(xpath="//span[@id='ddlTransportSupervisor_dropdown']/span")
	WebElement img_TransportSup;
	
	@FindBy(id="txtRouteNumber")
	WebElement txt_RouteNo;
	
	@FindBy(id="txtRouteName")
	WebElement txt_RouteName;
	
	@FindBy(id="ddlTransportSupervisor_inputSearch")
	WebElement txt_TransportSup;
	
	@FindBy(xpath="//input[@id='IsFixedRoute_hidden']")
	WebElement chk_FixedRoute;	
	
	@FindBy(xpath="//button[text()='Create']")
	WebElement btn_Create;	
	
	@FindBy(xpath="//button[text()='Create and Schedule']")
	WebElement btn_CreateSchedule;	
	
	public AddRoutePage(WebDriver driver){
		this.driver = driver;
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void setRouteNo(String RouteNo){
		txt_RouteNo.clear();
		txt_RouteNo.sendKeys(RouteNo);
		Reporter.log("Route Number "+RouteNo+"<br>");
	}
	public void setRouteName(String RouteName){
		txt_RouteName.clear();
		txt_RouteName.sendKeys(RouteName);
		Reporter.log("Route Name "+RouteName+"<br>");
	}
	public void setTransportSup(String Supervisor){
		try {
			img_TransportSup.click();
		txt_TransportSup.sendKeys(Supervisor);
		List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlTransportSupervisor_popup']//ul/li"));
		for (WebElement e  : links)
		{
			if(e.getText().equalsIgnoreCase(Supervisor)){
				e.click();
				Thread.sleep(1000);
			}
		}
		Reporter.log("Supervisor "+Supervisor+"<br>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setFixedRoute(Boolean bflag){
		if(bflag) {
			try {
				chk_FixedRoute.click();
				Reporter.log("Is Fixed Route "+bflag+"<br>");
			}catch(Exception e) {
				Reporter.log("Javascript execution for Is Fixed Route checkbox+\"<br>\"");
			jse = (JavascriptExecutor) driver;
			jse.executeScript("document.getElementById('chk_FixedRoute').click()");
			Reporter.log("Is Fixed Route "+bflag+"<br>");
			}
		}
	}
	
	public void clickCreate(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_Create);	
		btn_Create.click();
		Reporter.log("Clicked on Create button<br>");
	}
	
	public void clickCreateSchedule(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_CreateSchedule);	
		btn_CreateSchedule.click();
		Reporter.log("Clicked on Create and Add Schedule button<br>");
	}
	public boolean fill_AddRoute_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntity(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
		this.setRouteNo(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			this.setRouteName(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			this.setTransportSup(FormData[3].toString());}
		if(FormData[4].toString()== "Yes") {
			this.setFixedRoute(true);}
		if(FormData[5].toString().equalsIgnoreCase("TRUE")) {
			this.clickCreate();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}else if(FormData[6].toString().equalsIgnoreCase("TRUE")) {
			this.clickCreateSchedule();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}else if(FormData[7].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickClear();
			cpo.clickGoBack();
			return false;
		}else if(FormData[8].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Route form"+e.getMessage());
			return false;
		}
		return true;
	}
	public boolean fill_EditRoute_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[2].toString()!= "") {
		this.setRouteNo(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			this.setRouteName(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			this.setTransportSup(FormData[4].toString());}
		cpo.clickSubmit();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		return true;
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Edit Route form"+e.getMessage());
			return false;
		}
		}

}

