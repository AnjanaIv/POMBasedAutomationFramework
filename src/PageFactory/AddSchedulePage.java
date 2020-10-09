package PageFactory;
/*
 * author: anjana.iv
 * Purpose : Add and Edit features Object Locators and Methods
 */
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import utility.DateHandler;
import utility.TestDataProvider;

public class AddSchedulePage {
	WebDriver driver;
	
	Actions action;
	JavascriptExecutor jse;
	@FindBy(id="txtScheduleName")
	WebElement txt_scheduleName;
	
	@FindBy(id="dtStartDate")
	WebElement txt_scheStartDate;
	
	String sStartDateDivId = "e-dtStartDate";
	@FindBy(id="dtStartDate-img")
	WebElement img_StartDate;
	
	@FindBy(id="dtEndDate-img")
	WebElement img_EndDate;
	String sEndDateDivId = "e-dtEndDate";
	
	@FindBy(id="dtEndDate")
	WebElement txt_scheEndDate;
	
	@FindBy(id="txtStartTime")
	WebElement txt_scheduleTime;
	
	String chk_JourneyTime = "//table[@id='onwardWorkingDaysTimings']";
	
	String lst_StopIntial = "//ul[@id='lstOnwardStopInitial']";
	String lst_StopFinal = "//ul[@id='lstOnwardStopFinal']";
	String lst_VehicleIntial = "//ul[@id='lstVehicleInitial']";
	
	
	@FindBy(id="onwardAdd")
	WebElement btn_AddStops;
	
	@FindBy(id="onwardUp")
	WebElement btn_MoveUp;
	
	@FindBy(id="onwardDown")
	WebElement btn_MoveDown;
	
	@FindBy(id="onwardRemove")
	WebElement btn_RemoveStops;
	
	@FindBy(id="chkReturn")
	WebElement chk_Trip;
	
	@FindBy(id="btnSave")
	WebElement btn_Map;
	
	public AddSchedulePage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create  all WebElements
		PageFactory.initElements(driver, this);
	}
	
	public void setScheduleName(String strScheName){
		txt_scheduleName.sendKeys(strScheName);
		Reporter.log("Schedule Name "+strScheName+"<br>");
	}
	
	public void setScheStartDate(String ScheduleStartDate){
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,1000)");
		jse.executeScript("arguments[0].scrollIntoView(true);",img_StartDate);
		img_StartDate.click();
		DateHandler date = new DateHandler(driver);
		date.selectDate(sStartDateDivId,ScheduleStartDate);
		Reporter.log("Schedule Start Date "+ScheduleStartDate+"<br>");
	}
	public void setScheEndDate(String ScheduleEndDate){
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,1000)");
		jse.executeScript("arguments[0].scrollIntoView(true);",img_StartDate);
		img_EndDate.click();
		DateHandler date = new DateHandler(driver);
		date.selectDate(sEndDateDivId,ScheduleEndDate);
		Reporter.log("Schedule End Date "+ScheduleEndDate+"<br>");
	}
	public void setScheduleTime(String ScheduleTime){
		txt_scheduleTime.clear();
		txt_scheduleTime.sendKeys(ScheduleTime);
		Reporter.log("Schedule Time "+ScheduleTime+"<br>");
	}
	
	public void setTypeOfTrip(String ReturnTrip){
		if(ReturnTrip.equalsIgnoreCase("Return")){
			chk_Trip.click();
			Reporter.log("Type of trip :Return<br>");
		}else {
		Reporter.log("Type of trip :Onward<br>");
		}
	}
	public void setJourneyTime(String JourneyTime){
		try {
		String splitter[] = JourneyTime.split(",");
		boolean isSelected = false;
		//check/verify the ones which is been sent by the data
		for(int i=0;i<splitter.length;i++) {
			isSelected = driver.findElement(By.xpath(chk_JourneyTime+"//input[@id='chk"+splitter[i]+"']")).isSelected();
			if(!isSelected) {
				//find the matching tr and click d checkbox chk_JourneyTime
				driver.findElement(By.xpath(chk_JourneyTime+"//input[@id='chk"+splitter[i]+"']")).click();
			}
		}
		//uncheck whichever is not sent from data
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] shortWeekdays = dfs.getShortWeekdays();
		List<String> list = new ArrayList<String>(Arrays.asList(shortWeekdays));
		int iDays = 0;
        for (String shortWeekday : shortWeekdays) {
        	if(shortWeekday != "") {
        		if (JourneyTime.contains(shortWeekday)) {//In the array!
        			list.remove(shortWeekday);  
        			iDays++;
        		}
        	}
        }
		//get the list of days to be unselected
		for(int uncheckDay=0;uncheckDay<list.size();uncheckDay++){
			if(list.get(uncheckDay) != "") {
			isSelected = driver.findElement(By.xpath(chk_JourneyTime+"//input[@id='chk"+list.get(uncheckDay)+"']")).isSelected();
			if(isSelected) {
				//find the matching tr and click d checkbox chk_JourneyTime
				driver.findElement(By.xpath(chk_JourneyTime+"//input[@id='chk"+list.get(uncheckDay)+"']")).click();
			}
			}
		}
		Reporter.log("Journey Time "+JourneyTime+"<br>");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void selectStopsForSchedule(ArrayList<Object[]> stopDetails) {
		int i=0;
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			Actions	actions = new Actions(driver);
			String[] lsStops;
			ArrayList<String[]> ls_Stops = new ArrayList<String[]>();
			for (Object[] objArray : stopDetails){
				lsStops = (String[]) objArray[0];
				ls_Stops.add(lsStops);
			}
			WebElement ul_stops = driver.findElement(By.xpath(lst_StopIntial));
			List<WebElement> links = ul_stops.findElements(By.tagName("li"));
				for (int eLink=0;eLink<links.size();eLink++)
				{
					WebElement e = links.get(eLink);
					jse.executeScript("arguments[0].scrollIntoView(true);",e);
					//System.out.println(e.getText());
					i=0;
					while(i<ls_Stops.size()) {
						lsStops = ls_Stops.get(i);
						jse.executeScript("arguments[0].scrollIntoView(true);",e);
						if(e.getText().equalsIgnoreCase(lsStops[0].trim())){
							driver.findElement(By.xpath(lst_StopIntial+"/li["+(eLink+1)+"]//div[@class='e-chkbox-small']//span")).click();
							ls_Stops.remove(i);
							break;
						}
						actions.moveToElement(e);
						actions.sendKeys(Keys.DOWN).perform();
					i++;
					}
			}
			btn_AddStops.click();
			stopsEdit(stopDetails);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopsEdit(ArrayList<Object[]> stopDetails) {
		int iActStop=1;int i=0;
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			Actions	actions = new Actions(driver);
			String[] lsStops;
			ArrayList<String[]> ls_Stops = new ArrayList<String[]>();
			for (Object[] objArray : stopDetails){
				lsStops = (String[]) objArray[0];
				ls_Stops.add(lsStops);
			}
			WebElement ul_stops = driver.findElement(By.xpath(lst_StopFinal));
			List<WebElement> links = ul_stops.findElements(By.tagName("li"));
				for (int eLink=0;eLink<links.size();eLink++)
				{
					WebElement e = links.get(eLink);
					jse.executeScript("arguments[0].scrollIntoView(true);",e);
					//System.out.println(e.getText());
					while(i<ls_Stops.size()) {
						lsStops = ls_Stops.get(i);
						Integer iExpStop =  Integer.valueOf(lsStops[2]);
						//If data sent order matches with stop displayed, then enter stop duration
						//lsStops[3] is sequence of stops
						
						if(e.getText().equalsIgnoreCase(lsStops[0])) {
							if(iActStop ==iExpStop) {
								if(!driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop)+"]/div[2]/input")).getText().equalsIgnoreCase(lsStops[1])) {
									driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop)+"]/div[2]/input")).clear();
									driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop)+"]/div[2]/input")).sendKeys(lsStops[1]);
								}
							Reporter.log("Entered Stop Duration for the record "+lsStops[1]+"<br>");
							i++;
							iActStop++;
							break;
							//return;
							}else if(iActStop<iExpStop) {
									jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(lst_StopFinal+"/li["+iActStop+"]//div[@class='e-chkbox-small']//span")));
									driver.findElement(By.xpath(lst_StopFinal+"/li["+iActStop+"]//div[@class='e-chkbox-small']//span")).click();
							int iMoveDown = iExpStop-iActStop;
							while(iMoveDown!= 0) {
								btn_MoveDown.click();
								iMoveDown--;
							}
							try {
							jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(lst_StopFinal+"/li["+(iExpStop)+"]//div[@class='e-chkbox-small']//span")));
							if(driver.findElement(By.xpath(lst_StopFinal+"/li["+(iExpStop)+"]/span")).getAttribute("aria-checked") != null) {
								driver.findElement(By.xpath(lst_StopFinal+"/li["+(iExpStop)+"]//div[@class='e-chkbox-small']//span")).click();
							}}catch(Exception e1) {
								jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(lst_StopFinal+"/li["+(iExpStop-1)+"]//div[@class='e-chkbox-small']//span")));
								if(driver.findElement(By.xpath(lst_StopFinal+"/li["+(iExpStop-1)+"]/span")).getAttribute("aria-checked") != null) {
									driver.findElement(By.xpath(lst_StopFinal+"/li["+(iExpStop-1)+"]//div[@class='e-chkbox-small']//span")).click();
								}
							}
						    Reporter.log("Record "+lsStops[0]+" in the final list of stops is moved down<br>");;
						    stopsEdit(stopDetails);
							i++;
							iActStop++;
							return;
							}else if(iActStop>iExpStop) {
									jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop)+"]//div[@class='e-chkbox-small']//span")));
									driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop)+"]//div[@class='e-chkbox-small']//span")).click();
							int iMoveUp = iActStop - iExpStop;
							while(iMoveUp!= 0) {
								btn_MoveUp.click();
								iMoveUp--;
							}
							actions.moveToElement(btn_MoveUp);
							actions.build().perform();
							btn_MoveUp.click();
							jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop+1)+"]//div[@class='e-chkbox-small']//span")));
							if(driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop+1)+"]/span")).getAttribute("aria-checked") != null) {
								driver.findElement(By.xpath(lst_StopFinal+"/li["+(iActStop+1)+"]//div[@class='e-chkbox-small']//span")).click();
							}
							Reporter.log("Record "+lsStops[0]+" in the final list of stops is moved up<br>");;
							i++;
							iActStop++;
							return;
							}
						}
						i++;
	        }
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mapVehicles(ArrayList<Object[]> vehicleDetails) {
		int i=0;
		try {
			String[] lsVehicles;
			ArrayList<String[]> ls_Vehicles = new ArrayList<String[]>();
			for (Object[] objArray : vehicleDetails){
				lsVehicles = (String[]) objArray[0];
				ls_Vehicles.add(lsVehicles);
			}
			Actions	actions = new Actions(driver);
			WebElement ul_stops = driver.findElement(By.xpath(lst_VehicleIntial));
			List<WebElement> links = ul_stops.findElements(By.tagName("li"));
				for (int eLink=0;eLink<links.size();eLink++)
				{
					WebElement e = links.get(eLink);
					i=0;
					System.out.println(eLink+" : "+e.getText());
					while(i<ls_Vehicles.size()) {
						if(e.getText().contains(ls_Vehicles.get(i)[0].toString())){
							System.out.println(eLink+" : "+e.getText());
							driver.findElement(By.xpath(lst_VehicleIntial+"/li["+(eLink+1)+"]//div[@class='e-chkbox-small']//span")).click();
							break;
						}
						actions.moveToElement(e);
						actions.sendKeys(Keys.DOWN).perform();
					i++;
					}
			}
				btn_AddStops.click();
				btn_Map.click();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fill_AddSchedule_form(Object[] FormData,String sForm) throws Exception{
		try {
		if(FormData[2].toString()!= "" && !sForm.equalsIgnoreCase("Edit")) {
		this.setScheduleName(FormData[2].toString());}
		if(FormData[3].toString()!= "") {
		this.setScheStartDate(FormData[3].toString());}
		if(FormData[4].toString()!= "") {
		this.setScheEndDate(FormData[4].toString());}
		if(FormData[5].toString()!= "") {
		this.setScheduleTime(FormData[5].toString());}
		if(FormData[6].toString()!= "") {
		this.setJourneyTime(FormData[6].toString());}
		if(FormData[7].toString()!= "") {
			this.setTypeOfTrip(FormData[7].toString());}
		Reporter.log("Add Schedule Form is filled with above details<br>");
		}catch(Exception e) {
			Reporter.log("Encountered issues while filling up the Add Edit Schedule form"+e.getMessage());
		}
	}
	
	public boolean click_buttons(Object[] FormData) {
		CommonPageObjects cpo = new CommonPageObjects(driver);
		try {
		if(FormData[8].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickSave();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			//Assert.assertTrue(this.getPageTitle().toLowerCase().contains("entity"));
			return true;
		}else if(FormData[9].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickReset();
			cpo.clickGoBack();
			return false;
		}else if(FormData[10].toString().equalsIgnoreCase("TRUE")) {
			cpo.clickGoBack();
			return false;
		}
	}catch(Exception e) {
		Reporter.log("Encountered issues while clicking on buttons in Schedule page "+e.getMessage());
	}
		return true;
	}
	
	
	
}

