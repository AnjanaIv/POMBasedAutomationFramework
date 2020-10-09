package PageFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features of Stop Object Locators and Methods
 */
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.AssertJUnit;
import org.testng.Reporter;

public class AddStopPage {
	/**
	 * All WebElements are identified by @FindBy annotation
	 */
	WebDriver driver;
	Actions action;
	JavascriptExecutor jse;
	
	@FindBy(id="txtStopName")
	WebElement txt_StopName;
	
	@FindBy(id="txtPrecision")
	WebElement txt_Precision;
	
	@FindBy(id="pac-input")
	WebElement txt_Location;

	
	
	
	public AddStopPage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void setStopName(String strStopName){
		txt_StopName.clear();
		txt_StopName.sendKeys(strStopName);
		Reporter.log("Stop Name "+strStopName+"<br>");
	}
	
		public void setLocation(String searchLoc, String Location,String ExpCoOrdinates){
			try {
				txt_Location.clear();
			txt_Location.sendKeys(searchLoc);
			Reporter.log("Location "+searchLoc+"<br>");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//Thread.sleep(100);
			List<WebElement> lst_locations = driver.findElements(By.xpath("//div[@class='pac-container pac-logo']/div"));
			System.out.println(lst_locations);
			for (int eLoc=0;eLoc<lst_locations.size();eLoc++) {
				List<WebElement> lst_locName = lst_locations.get(eLoc).findElements(By.tagName("span"));
				String sLocationName = "";
				for (int e=2;e<lst_locName.size();e++) {
					System.out.println(lst_locName.get(e).getAttribute("innerHTML"));
					sLocationName =  sLocationName +" "+ lst_locName.get(e).getAttribute("innerHTML").trim();
				}
				//AssertJUnit.assertEquals(sLocationName, Location);
				if(sLocationName.contains(Location)) {
					System.out.println("Selected the Location "+Location);
					System.out.println("Location count "+(eLoc));
					if(eLoc==0) {
						lst_locations.get(eLoc+1).click();
					}else {
						lst_locations.get(eLoc).click();
					}
					System.out.println("Selected the Location "+Location);
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					String sAddress = driver.findElement(By.xpath("//div[@class='gm-style-iw']/div/div")).getAttribute("innerHTML");
					//driver.findElement(By.xpath("//div[@class='gm-style-iw']/div/div")).getAttribute("innerHTML").split("coordinates: ")[1].equalsIgnoreCase(ExpCoOrdinates);
					String sActualCoordinates = sAddress.split("coordinates: ")[1].trim();
					if(sActualCoordinates.equalsIgnoreCase(ExpCoOrdinates)) {
						System.out.println("PASS : Verified the coordinates on the map");
						Reporter.log("PASS : Verified the coordinates on the map. Actual is "+sActualCoordinates+" Expected is "+ExpCoOrdinates);
						break;
					}else {
						Reporter.log("FAIL : Failed to verify the coordinates on the map. Actual is "+sActualCoordinates+" Expected is "+ExpCoOrdinates);
						break;
					}
				}
			}
			}catch(Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
	public void setPrecision(String Precision){
		txt_Precision.clear();
		txt_Precision.sendKeys(Precision);
		Reporter.log("Precision "+Precision+"<br>");
	}
	public boolean fill_AddStop_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[0].toString()!= "") {
			cpo.setParentEntityThroSearch(FormData[0].toString());}
		if(FormData[1].toString()!= "" ) {
		this.setStopName(FormData[1].toString());}
		if(FormData[2].toString()!= "") {
		this.setPrecision(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
			this.setLocation(FormData[3].toString(),FormData[4].toString(),FormData[5].toString());}
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Stop form"+e.getMessage());
		}
		return false;
	}
	public boolean fill_EditStop_form(Object[] FormData,String sForm) throws Exception{
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[2].toString()!= "" ) {
		this.setStopName(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
		this.setPrecision(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
			this.setLocation(FormData[4].toString(),FormData[5].toString(),FormData[6].toString());}
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			return true;
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Edit Stop form"+e.getMessage());
		}
		return false;
	}
}

