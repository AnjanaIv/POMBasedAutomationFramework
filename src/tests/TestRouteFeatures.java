package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageFactory.AddRoutePage;
import PageFactory.AddSchedulePage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.TestBase;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Route Test Cases
 */
public class TestRouteFeatures extends TestBase{

	LoginPage objLogin;
	HomePage	objHomePage;
	AddRoutePage addRoutePage;
	AddSchedulePage addSchedulePage;
	CommonPageObjects cpo;
	
	@Test(priority=1,dataProvider="AddRouteData")
	public void a_AddRoute(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Route Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Route")) {
				Reporter.log("As we are already on Route Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectRoutes();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Route"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Route"));
		addRoutePage = new AddRoutePage(driver);
		boolean bTCFlag = addRoutePage.fill_AddRoute_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"View");
			Reporter.log(sTestData[1].toString()+" Route Data added successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("Route Number", sTestData[1].toString());
			dataMap.put("Route Name",sTestData[2].toString());
			dataMap.put("Transport Supervisor", sTestData[3].toString());
			dataMap.put("IsFixedRoute", sTestData[4].toString());
			dataMap.put("Status", "Active");
			dataMap.put("Number of Schedules", "");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Route and Verification Completed=====", true);
		}
		}
		catch(Exception e) {
			Reporter.log("=====TestCase Add Route Was not successful=====", true);
		}
	}
	
	@DataProvider (name="AddRouteData")
    public	Iterator<Object[]> getAddRouteData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("RouteData","AddRoute");
    	System.out.println(testdata.size());
         return testdata.iterator();
		}
    
    @DataProvider (name="AddRouteScheduleData")
    public	Iterator<Object[]> getAddRouteScheData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("RouteData","addrouteSchedule");
    	System.out.println(testdata.size());
         return testdata.iterator();
		}
    @Test(priority=2,dataProvider="AddRouteScheduleData")
	public void b_AddRoute(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Route and Add Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Route")) {
				Reporter.log("As we are already on Route Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectRoutes();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Route"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Route"));
		addRoutePage = new AddRoutePage(driver);
		boolean bTCFlag = addRoutePage.fill_AddRoute_form(sTestData,"Add");
		waitForLoad(driver);
		Thread.sleep(500);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Schedule"));
		ArrayList<Object[]> scheduleDetails = TestDataProvider.getMultipleData("RouteData","addschedule");
		Object[] scheduleData = null;
		for (Object[] objArray : scheduleDetails){
			scheduleData = (String[]) objArray[0];
		}
		addSchedulePage = new AddSchedulePage(driver);
		addSchedulePage.fill_AddSchedule_form(scheduleData,"Add");
		waitForLoad(driver);
		ArrayList<Object[]> stopDetails = TestDataProvider.getDependentData("RouteData","addStopDetails",scheduleData[2].toString());
		addSchedulePage.selectStopsForSchedule(stopDetails);
		bTCFlag = addSchedulePage.click_buttons(scheduleData);
		try {
		if(driver.findElement(By.className("help-block")).isDisplayed()) {
			bTCFlag = false;
			Reporter.log("FAIL : "+sTestData[1].toString()+" Schedule not added",true);
		}}catch(Exception e) {
			Reporter.log("PASS : "+sTestData[1].toString()+" Schedule added successfully",true);
		}
		//TC Verification
		objHomePage.selectSchedules();
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Route Name",scheduleData[2].toString());
			Reporter.log(scheduleData[2].toString()+" Schedule verified successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", scheduleData[0].toString());
			dataMap.put("Name", scheduleData[2].toString());
			dataMap.put("Route Name",scheduleData[1].toString());
			dataMap.put("Route No", sTestData[1].toString());
			dataMap.put("Start Date", scheduleData[3].toString());
			dataMap.put("End Date", scheduleData[4].toString());
			dataMap.put("Schedule Time", scheduleData[5].toString());
			dataMap.put("Publish", "");
			dataMap.put("Journey Type", scheduleData[6].toString());
			dataMap.put("Status", "Draft");
			gridReader.verifyDatainGrid(dataMap);
		}
		Reporter.log(sTestData[2].toString()+" Schedule added  and verified successfully<br>",true);
		
		}catch(Exception e) {
			Reporter.log("=====TestCase Add Route and Schedule Was not successful=====", true);
		}
	}
    @Test(priority=3,dataProvider="EditRoute")//,dependsOnMethods = {"a_AddRoute"})
	public void c_EditRoute(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Route Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addRoutePage = new AddRoutePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Route")) {
				Reporter.log("As we are already on Route Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectRoutes();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Route"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"Edit");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Route"));
			addRoutePage.fill_EditRoute_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			gridReader.isDataAvailable("Name",sTestData[2].toString()+" "+ sTestData[3].toString()+" "+ sTestData[4].toString());
			Reporter.log(sTestData[0].toString()+" Route Data verified successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("Route Number", sTestData[1].toString());
			dataMap.put("Route Name",sTestData[2].toString());
			dataMap.put("Transport Supervisor", sTestData[3].toString());
			dataMap.put("IsFixedRoute", sTestData[4].toString());
			dataMap.put("Status", "Active");
			dataMap.put("Number of Schedules", "");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit and Verification of Edited Route Completed=====", true);
			}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Route Was not successful=====", true);
			}
		}
	
	@Test(priority=4,dataProvider="DeactivateRoute")//,dependsOnMethods = {"a_AddRoute"}
	public void d_InactivateRoute(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Route Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Route")) {
				Reporter.log("As we are already on Route Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectRoutes();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Route"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"Deactivate");
		gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"View");
				Reporter.log(sTestData[1].toString()+" Route deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Route Name",sTestData[1].toString(), "InActive");
				Reporter.log("=====TestCase Inactivate Route and Verification Completed=====", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=5,dataProvider="ActivateRoute")//,dependsOnMethods = {"d_InactivateRoute"}
	public void e_ActivateRoute(Object[] sTestData){
		Reporter.log("=====TestCase Activate Route Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Route")) {
				Reporter.log("As we are already on Route Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectRoutes();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Route"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"Activate");
		gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"View");
				Reporter.log(sTestData[1].toString()+" Route re-activated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Route Name",sTestData[1].toString(), "Active");
			Reporter.log("=====TestCase Activate Route and Verification Completed=====", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=6,dataProvider="DeleteRoute")//,dependsOnMethods = {"e_ActivateRoute"}
	public void f_DeleteRoute(Object[] sTestData){
		Reporter.log("=====TestCase Delete Route Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Route")) {
				Reporter.log("As we are already on Route Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectRoutes();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Route"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"View");
		gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"Delete");
		gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"View");
				Reporter.log(sTestData[1].toString()+" Route deleted successfully<br>",true);
				Reporter.log("=====TestCase Delete Route and Verification Completed=====", true);
	}
	catch(Exception e){
			
	}	
	}
    @DataProvider(name = "EditRoute")
    public	Iterator<Object[]> getEditRouteData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("RouteData","EditRoute");
         return testdata.iterator();
		}
    @DataProvider(name = "DeactivateRoute")
    public	Iterator<Object[]> getDeactivateRouteData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("RouteData","DeactivateRoute");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateRoute")
    public	Iterator<Object[]> getActivateRouteData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("RouteData","ActivateRoute");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteRoute")
    public	Iterator<Object[]> getDeleteRouteData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("RouteData","DeleteRoute");
         return testdata.iterator();
		}
 
   @AfterMethod
 
    public void afterMethod(ITestResult result) {
	   	Reporter.log("Test is executed with the data set provided above<br>");
		Reporter.log("TestCase: " + result.getMethod().getMethodName()+ " executed with the data set provided above<br>");
		try {
			Logger logger = new Logger(driver);
			Logger.takeScreenshot(result.getMethod().getMethodName());
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    @AfterSuite
    public void afterSuite() {
  	    driver.close();
    }

}