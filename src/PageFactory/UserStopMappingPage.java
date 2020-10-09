package PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import config.SessionDetails;
import utility.GridReader;
import utility.TestDataProvider;

/*
 * author: anjana.iv
 * Purpose : User Stop Mapping Object Locators and Methods
 */
public class UserStopMappingPage {
	WebDriver driver;
	JavascriptExecutor jse;
	GridReader gridReader;
	@FindBy(xpath="//span[@id='ddlStop_dropdown']/span")
	WebElement img_Stops;
	
	@FindBy(id="ddlStop_inputSearch")
	WebElement txt_Stopssearch;
	
	@FindBy(id="btnStudent")
	WebElement tab_MapStudent;

	@FindBy(id="btnStaff")
	WebElement tab_MapStaff;

	@FindBy(id="btnUnMap")
	WebElement btn_Map;
	
	@FindBy(id="btnUnMap")
	WebElement btn_UnMap;
	
	
	
	public UserStopMappingPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	public void setStopsthroSearch(String Stop){		
		try {
			//jse.executeScript("arguments[0].scrollIntoView(true);",img_Country);
			img_Stops.click();
			txt_Stopssearch.sendKeys(Stop);
			List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlStop_popup']//ul/li"));
			for (WebElement e  : links)
			{
				if(e.getText().equalsIgnoreCase(Stop)){
					e.click();
					Thread.sleep(1000);
				}
			}
			Reporter.log("Stop "+Stop+"<br>");
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void clickMapStudent(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",tab_MapStudent);
		tab_MapStudent.click();
		Reporter.log("Clicked on Map Student tab<br>");
	}
	public void clickMapStaff(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",tab_MapStaff);
		tab_MapStaff.click();
		Reporter.log("Clicked on Map Staff tab<br>");
	}
	public void clickMap(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_Map);
		btn_Map.click();
		Reporter.log("Clicked on Map button<br>");
	}
	public void clickUnMap(){
		jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);",btn_UnMap);
		btn_UnMap.click();
		Reporter.log("Clicked on UnMap button<br>");
	}
	public void selectStudents(String[][] StudentStaffData){
		boolean bStudent = false;boolean bStaff =false;
		//Object[] mappingArray = null;
		try {
			for (String[] objArray : StudentStaffData){
				//mappingArray = (String[]) objArray[0];
				if(objArray[0].toString().equalsIgnoreCase("Student")) {
					if(!bStudent) {
						clickMapStudent();
						gridReader = new GridReader(driver,"dvStudentPartial");
						bStudent= true;
					}
					gridReader.clickAction("Sattrax Id", objArray[1].toString(), "Select");
					
				}else if(objArray[0].toString().equalsIgnoreCase("Staff")) {
					if(!bStaff) {
						clickMapStaff();
						gridReader = new GridReader(driver,"dvStaffPartial");
						bStaff=true;
					}
					gridReader.clickAction("Sattrax Id", objArray[2].toString(), "Select");
				}
			}
		}catch(Exception e) {
			Reporter.log("Encountered issues while selecting students / staff for mapping "+e.getMessage());
		}
	}
	public boolean fill_UserStopMapping_form(Object[] FormData) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntityThroSearch(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
		this.setStopsthroSearch(FormData[1].toString());}
		return true;
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the User Stop Mapping form"+e.getMessage());
		}
		return false;
	}
	public boolean map_StudentStaff(String[][] mappingData) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
	try {
	selectStudents(mappingData);
	this.clickMap();
	// accepting javascript alert
	cpo.handleAlert();
	Reporter.log("Clicked on Map button<br>");
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}catch(Exception e) {
		Reporter.log("Encountered issues while selecting Students or Staff"+e.getMessage());
	}
	return false;
}
}
