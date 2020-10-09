package PageFactory;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Role Object Locators and Methods
 */
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class AddRolePage {

	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	@FindBy(id="txtRole")
	WebElement txt_Role;
	
	@FindBy(id="btnCreateRole")
	WebElement btn_AddRole;
	
	public AddRolePage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	public void setRoleName(String roleName){
		txt_Role.clear();
		txt_Role.sendKeys(roleName);
		Reporter.log("Role Name "+roleName+"<br>");
	}
	public void clickAddRole(){
		btn_AddRole.click();
		Reporter.log("Clicked on Add Role button<br>");
	}
	public boolean fill_AddRole_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntity(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
			this.setRoleName(FormData[1].toString());}
		Reporter.log("Add Roles Form is filled with above details<br>");
		if(FormData[2].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}else if(FormData[3].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickClear();
			cpo.clickGoBack();
			return false;
		}else if(FormData[4].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Edit Role form"+e.getMessage());
			return false;
		}
		return true;
		
	}
	
}
