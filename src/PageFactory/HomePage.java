package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.Web
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
/*
 * author: anjana.iv
 * Purpose : Home Page Object Locators and Methods
 */
public class HomePage {

	WebDriver driver;
	JavascriptExecutor jse;
	
	@FindBy(xpath="//a[@href='#Profile']//p")
	WebElement homePageUserEmail;
	
	@FindBy(linkText="Entity")
	WebElement lnk_Entity;
	
	@FindBy(linkText="Student")
	WebElement lnk_Student;
	
	@FindBy(linkText="Parent")
	WebElement lnk_Parent;
	
	@FindBy(linkText="Staff")
	WebElement lnk_Staff;
	
	@FindBy(linkText="Driver")
	WebElement lnk_Driver;
	
	@FindBy(linkText="Conductor")
	WebElement lnk_Conductor;
	
	@FindBy(linkText="Roles")
	WebElement lnk_Roles;
	
	@FindBy(linkText="Stops")
	WebElement lnk_Stops;
	
	@FindBy(xpath="//*[text()='Routes']")
	WebElement lnk_Routes;
	
	@FindBy(xpath="//*[text()='Schedules']")
	WebElement lnk_Schedules;
	
	@FindBy(xpath="//*[text()='Vehicle']")
	WebElement lnk_Vehicles;
	
	@FindBy(xpath="//*[text()='Permission']")
	WebElement lnk_Permission;
	
	@FindBy(xpath="//*[text()='User Stop']")
	WebElement lnk_UserStop;
	
	@FindBy(xpath="//*[text()='Devices']")
	WebElement lnk_Devices;
	
	@FindBy(xpath="//*[text()='Notification']")
	WebElement lnk_Notification;
	
	public HomePage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	//Get the User name from Home Page
		public String getHomePageDashboardUserName(){
		 return	homePageUserEmail.getText();
		}
		
		//Click the Entity link from SideBar
		public void selectEntity(){
			lnk_Entity.click();
			Reporter.log("Clicked on Entity sidebar link<br>");
		}
		
		//Click the Student link from SideBar
		public void selectStudent(){
			lnk_Student.click();
			Reporter.log("Clicked on Student sidebar link<br>");
		}
		
		//Click the Parent link from SideBar
				public void selectParent(){
					lnk_Parent.click();
					Reporter.log("Clicked on Parent sidebar link<br>");
				}
		
		//Click the Routes link from SideBar
		public void selectRoutes(){
			try {
				Actions	actions = new Actions(driver); 
				actions.moveToElement(lnk_Routes);
				actions.perform();
				actions.sendKeys(Keys.PAGE_DOWN).perform();
				lnk_Routes.click();
			Reporter.log("Clicked on Routes sidebar link<br>");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//Click the Schedule link from SideBar
		public void selectSchedules(){
			try {
				Actions	actions = new Actions(driver); 
				actions.moveToElement(lnk_Schedules);
				actions.perform();
				actions.sendKeys(Keys.PAGE_DOWN).perform();
			lnk_Schedules.click();
			Reporter.log("Clicked on Schedules sidebar link<br>");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//Click the Staff link from SideBar
		public void selectStaff(){
			lnk_Staff.click();
			Reporter.log("Clicked on Staff sidebar link<br>");
		}
		
		//Click the Driver link from SideBar
		public void selectDriver(){
			lnk_Driver.click();
			Reporter.log("Clicked on Driver sidebar link<br>");
		}
		
		//Click the Conductor link from SideBar
		public void selectConductor(){
			lnk_Conductor.click();
			Reporter.log("Clicked on Conductor sidebar link<br>");
		}
		//Click the Roles link from SideBar
				public void selectRoles(){
					lnk_Roles.click();
					Reporter.log("Clicked on Roles sidebar link<br>");
				}
				
				//Click the Stops link from SideBar
				public void selectStops(){
					lnk_Stops.click();
					Reporter.log("Clicked on Stops sidebar link<br>");
				}
				
				//Click the Vehicle link from SideBar
				public void selectVehicles(){
					try {
						Actions	actions = new Actions(driver); 
						actions.moveToElement(lnk_Vehicles);
						actions.perform();
						actions.sendKeys(Keys.PAGE_DOWN).perform();
						lnk_Vehicles.click();
					Reporter.log("Clicked on Vehicle sidebar link<br>");
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				//Click the Permission link from SideBar
				public void selectPermission(){
					try {
						Actions	actions = new Actions(driver); 
						actions.moveToElement(lnk_Permission);
						actions.perform();
						actions.sendKeys(Keys.PAGE_DOWN).perform();
						lnk_Permission.click();
					Reporter.log("Clicked on Permission sidebar link<br>");
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				//Click the User Stop link from SideBar
				public void selectUserStop(){
					try {
						Actions	actions = new Actions(driver); 
						actions.moveToElement(lnk_UserStop);
						actions.perform();
						actions.sendKeys(Keys.PAGE_DOWN).perform();
						lnk_UserStop.click();
					Reporter.log("Clicked on UserStop sidebar link<br>");
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				//Click the Devices link from SideBar
				public void selectDevices(){
					try {
						Actions	actions = new Actions(driver); 
						actions.moveToElement(lnk_Devices);
						actions.perform();
						actions.sendKeys(Keys.PAGE_DOWN).perform();
						lnk_Devices.click();
					Reporter.log("Clicked on Devices sidebar link<br>");
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				//Click the Notification link from SideBar
				public void selectNotification(){
					try {
						Actions	actions = new Actions(driver); 
						actions.moveToElement(lnk_Notification);
						actions.perform();
						actions.sendKeys(Keys.PAGE_DOWN).perform();
						lnk_Notification.click();
					Reporter.log("Clicked on Notification sidebar link<br>");
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
						
}
