package PageFactory;
import java.util.List;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Notification Object Locators and Methods
 */
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Staff Object Locators and Methods
 */
public class AddNotificationPage {
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	@FindBy(xpath="//span[@id='ddlModule_dropdown']/span")
	WebElement img_Module;
	
	@FindBy(id="ddlModule_inputSearch")
	WebElement txt_Modulesearch;
	
	@FindBy(xpath="//span[@id='ddlEvents_dropdown']/span")
	WebElement img_Event;
	
	@FindBy(id="ddlEvents_inputSearch")
	WebElement txt_Eventsearch;
	
	@FindBy(xpath="//span[@id='ddlTempKey_dropdown']/span")
	WebElement img_SMSTemplate;
	
	@FindBy(xpath="//a[@title='TemplateKey']//span[@class='cke_combo_arrow']")
	WebElement img_EmailTemplate;
	
	@FindBy(id="ddlTempKey_inputSearch")
	WebElement txt_Templatesearch;
	
	@FindBy(id="txtDisplayName")
	WebElement txt_DisplayName;
	
	@FindBy(id="txtSubject")
	WebElement txt_mailSubj;
	
	@FindBy(xpath="//input[@id='chkIsStIssued']/following-sibling::span[1]")
	WebElement chk_issuedBySatTrax;
	
	@FindBy(id="smsText")
	WebElement txt_SMSArea;
	
	@FindBy(xpath="//div[@id='cke_editor']//div[@id='cke_1_contents']")
	WebElement txt_EmailBody;
	
	
	
	
	public AddNotificationPage(WebDriver driver){
		this.driver = driver;
		jse = (JavascriptExecutor) driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	} 
	
	public void setModulethroSearch(String Module){		
		try {
			img_Module.click();
			txt_Modulesearch.sendKeys(Module);
			List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlModule_popup']//ul/li"));
			for (WebElement e  : links)
			{
				if(e.getText().equalsIgnoreCase(Module)){
					e.click();
					Thread.sleep(1000);
				}
			}
			Reporter.log("Module "+Module+"<br>");
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void setEventthroSearch(String Event){		
		try {
			img_Event.click();
			txt_Eventsearch.sendKeys(Event);
			List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlEvents_popup']//ul/li"));
			for (WebElement e  : links)
			{
				if(e.getText().equalsIgnoreCase(Event)){
					e.click();
					Thread.sleep(1000);
				}
			}
			Reporter.log("Event "+Event+"<br>");
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void setSMSthroTemplate(String Template){		
		try {
			//Template = "!VehicleNumber! Started on Time";
			if(Template.contains("!")) {
				String sms = Template.split("!")[2].trim();
				Template = Template.split("!")[1].trim();
				img_SMSTemplate.click();
				txt_Templatesearch.sendKeys(Template);
				List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlTempKey_popup']//ul/li"));
				for (WebElement e  : links)
				{
					if(e.getText().equalsIgnoreCase(Template)){
						System.out.println("test");
						e.click();
					}
				}
				txt_SMSArea.sendKeys(sms);
			}else {
				txt_SMSArea.sendKeys(Template);
			}
			Reporter.log("Template "+Template+"<br>");
			
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void selectRecepients(String[][] recipients){		
		try {
			for (String[] recipient : recipients){
				List<WebElement> links = driver.findElements(By.xpath("//div[@id='dvRecipients']/div[@id='combobox']/div"));
				for (WebElement e  : links)
				{
				if(e.getText().equalsIgnoreCase(recipient[0])){
					e.findElement(By.id("select")).click();
					if(recipient[1].equals("FALSE")) {
						e.findElement(By.xpath("//input[@type='checkbox' and @id='isSms']")).click();
					}
					if(recipient[2].equals("FALSE") && e.findElement(By.xpath("//input[@type='checkbox' and @id='isEmail']")).isSelected() ) {
						e.findElement(By.xpath("//input[@type='checkbox' and @id='isEmail']")).click();
					}
				}
			}
			}	
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public void setEmailthroTemplate(String Template){	
		try {
			//Template = "text email body";
			if(Template.contains("!")) {
				String email = Template.split("!")[2].trim();
				Template = Template.split("!")[1].trim();
				//driver.switchTo().frame("Rich Text Editor, editor");
				img_EmailTemplate.click();
				List<WebElement> links = driver.findElements(By.xpath("//div[@id='ddlTempKey_popup']//ul/li"));
				for (WebElement e  : links)
				{
					System.out.println(e.getText());
					driver.findElement(By.xpath("//div[@id='ddlTempKey_popup']//ul/li[3]")).click();
				if(e.findElement(By.tagName("span")).getText().equalsIgnoreCase(Template)){
					e.click();
					Thread.sleep(1000);
				}
				
				}
				txt_EmailBody.sendKeys("text");
				txt_EmailBody.sendKeys(email);				
			}else {
				txt_EmailBody.sendKeys(Template);
			}
			//driver.switchTo().parentFrame();
			Reporter.log("Template "+Template+"<br>");
			
			}catch (Exception e) {
				e.printStackTrace();
				//driver.switchTo().parentFrame();
			}
	}
	public void setDisplayName(String DisplayName){
		txt_DisplayName.clear();
		txt_DisplayName.sendKeys(DisplayName);
		Reporter.log("DisplayName"+DisplayName+"<br>");
	}
	
	public void setMailSubj(String Subject){
		txt_mailSubj.clear();
		txt_mailSubj.sendKeys(Subject);
		Reporter.log("Mail Subject "+Subject+"<br>");
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
	
	public boolean fill_AddNotification_form(Object[] FormData) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
		cpo.setParentEntityThroSearch(FormData[0].toString());}
		if(FormData[1].toString()!= "") {
			this.setModulethroSearch(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
			this.setEventthroSearch(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			this.setDisplayName(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			this.setMailSubj(FormData[4].toString());}
		if(FormData[5].toString() != "") {
			//this.setEmailthroTemplate(FormData[5].toString());
		}
		if(FormData[6].toString() != "") {
			this.setSMSthroTemplate(FormData[6].toString());
		}
		String[][] recepientsDetails = TestDataProvider.getDependentDatainStr("Notification","NotificationRecipients",FormData[2].toString());
		this.selectRecepients(recepientsDetails);
		Reporter.log("Add Notification Form is filled with above details<br>");
		if(FormData[7].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}else if(FormData[8].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickClear();
			cpo.clickGoBack();
			return false;
		}else if(FormData[9].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
	}catch(Exception e) {
		Reporter.log("Encountered issues while filling up the Add Edit Notification form"+e.getMessage());
	}
		return true;
		
	}
	public boolean fill_EditNotification_form(Object[] FormData) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
			if(FormData[1].toString()!= "") {
				cpo.setParentEntityThroSearch(FormData[1].toString());}
				if(FormData[2].toString()!= "") {
					this.setModulethroSearch(FormData[2].toString());}
				if(FormData[3].toString()!= "") {
					this.setEventthroSearch(FormData[3].toString());}
				if(FormData[4].toString()!= "") {
					this.setDisplayName(FormData[4].toString());}
				if(FormData[5].toString()!= "") {
					this.setMailSubj(FormData[5].toString());}
				Reporter.log("Add Notification Form is filled with above details<br>");
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
	}catch(Exception e) {
		Reporter.log("Encountered issues while filling up the Edit Notification form"+e.getMessage());
	}
		return true;
		
	}
	
	
}
