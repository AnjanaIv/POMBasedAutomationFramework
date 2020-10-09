package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import PageFactory.AddRoutePage;
import PageFactory.AddSchedulePage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import PageFactory.UserStopMappingPage;
import config.TestBase;
import utility.ChildGridReader;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
/*
 * author: anjana.iv
 * Purpose : Schedule Test Cases
 */
public class TestScheduleFeatures extends TestBase {
	LoginPage objLogin;
	HomePage	objHomePage;
	AddSchedulePage addSchedulePage;
	UserStopMappingPage userStopMappingPage;
	CommonPageObjects cpo;
	
	@Test(priority=1,dataProvider="AddScheduleData")
	public void a_CreateSchedule(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Schedule Started=====", true);
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
			gridReader.clickActionMatchRecords(sTestData[0].toString()+"!"+sTestData[1].toString(),"Add Schedule");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Schedule"));
			addSchedulePage = new AddSchedulePage(driver);
			addSchedulePage.fill_AddSchedule_form(sTestData,"Add");
			ArrayList<Object[]> stopDetails = TestDataProvider.getDependentData("ScheduleData","addStopDetails",sTestData[2].toString());
			addSchedulePage.selectStopsForSchedule(stopDetails);
			boolean bTCFlag = addSchedulePage.click_buttons(sTestData);
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
				gridReader.isDataAvailable("Name",sTestData[2].toString());
				Reporter.log(sTestData[2].toString()+" Schedule verified successfully",true);
				//Prepare a Hashmap for verifing data in table grid columns
				LinkedHashMap dataMap = new LinkedHashMap();
				dataMap.put("Entity", sTestData[0].toString());
				dataMap.put("Name", sTestData[2].toString());
				dataMap.put("Route Name",sTestData[1].toString());
				dataMap.put("Route No", sTestData[1].toString());
				dataMap.put("Start Date", sTestData[3].toString());
				dataMap.put("End Date", sTestData[4].toString());
				dataMap.put("Schedule Time", sTestData[5].toString());
				dataMap.put("Publish", "");
				dataMap.put("Journey Type", sTestData[6].toString());
				dataMap.put("Status", "Draft");
				gridReader.verifyDatainGrid(dataMap);
			}
			Reporter.log(sTestData[2].toString()+" Schedule added and verified successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=2,dataProvider="MapVehicles")
	public void b_MapVehicles(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Mapping Vehicles to Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			ArrayList<Object[]> vehicleDetails = TestDataProvider.getDependentData("ScheduleData","VehicleMapping",sTestData[0].toString());
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())) {
				gridReader.clickAction("Name",sTestData[0].toString(),"Map Vehicle");
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule Vehicle Mapping"));
				addSchedulePage = new AddSchedulePage(driver);
				addSchedulePage.mapVehicles(vehicleDetails);
				waitForLoad(driver);
			}
				//TC Verification
			objHomePage.selectSchedules();
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				String[] lsVehicles;
				ArrayList<String[]> ls_Vehicles = new ArrayList<String[]>();
				for (Object[] objArray : vehicleDetails){
					lsVehicles = (String[]) objArray[0];
					ls_Vehicles.add(lsVehicles);
				}
				for(String[] vehicle:ls_Vehicles) {
					if(childGridReader.isDataAvailable("Vehicle",vehicle[0].toString())){
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Capacity", vehicle[1].toString());
						dataMap.put("Filled", vehicle[2].toString());
						childGridReader.verifyDatainGrid(dataMap);
						Reporter.log(vehicle[0].toString()+" Vehicle mapped successfully",true);
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			}
			Reporter.log(sTestData[1].toString()+" Vehicle mapped to Schedule successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=3,dataProvider="UnMapVehicles")
	public void c_UnMapVehicles(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Un Mapping Vehicles to Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				if(childGridReader.isDataAvailable("Vehicle",sTestData[1].toString())){
					System.out.println("Found the Vehicle mapped to schedule");
					childGridReader.clickAction("Vehicle",sTestData[1].toString(),"Un-Map Vehicle");
					//if(cpo.getToastMessage(driver).equalsIgnoreCase("Vehicle unmapped successfully")){
						gridReader.clickAction("Vehicle",sTestData[0].toString(),"Expand");
						if(!childGridReader.isDataAvailable("Vehicle",sTestData[1].toString())){
							Reporter.log(sTestData[1].toString()+" Vehicle Un mapped successfully",true);
						}
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			Reporter.log(sTestData[1].toString()+" Vehicle Un mapped to Schedule successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=4,dataProvider="MapStudents")
	public void d_MapStudentStaffs(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Mapping Student and Staff to Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			String[][] studentDetails = TestDataProvider.getDependentDatainStr("ScheduleData","UserMapping",sTestData[0].toString());
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())) {
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				for(int i=0;i<studentDetails.length;i++) {
				childGridReader.clickAction("Vehicle",studentDetails[4].toString(),"Map Staff and Students");
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule User Vehicle"));
				userStopMappingPage = new UserStopMappingPage(driver);
				userStopMappingPage.map_StudentStaff(studentDetails);
				waitForLoad(driver);
			}
				//TC Verification
			//objHomePage.selectSchedules();
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				for(int i=0;i<studentDetails.length;i++) {
					if(childGridReader.isDataAvailable("Vehicle",studentDetails[4].toString())){
						childGridReader.clickAction("Vehicle",studentDetails[4].toString(),"CheckUnmapStaff");
						//check if Un-Map Staff and Students icon is available
						Reporter.log(studentDetails[i].toString()+" Students and Staff mapped successfully",true);
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			Reporter.log(sTestData[1].toString()+" Users mapped to Schedule successfully<br>",true);
		}
			}
			}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority=5,dataProvider="UnMapUsers")
	public void e_UnMapUsers(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Un Mapping Users from Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			ArrayList<Object[]> studentDetails = TestDataProvider.getDependentData("ScheduleData","StudentUnMapping",sTestData[0].toString());
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			objHomePage.selectSchedules();
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				String[] lsUsers;
				ArrayList<String[]> ls_Users = new ArrayList<String[]>();
				for (Object[] objArray : studentDetails){
					lsUsers = (String[]) objArray[0];
					ls_Users.add(lsUsers);
				}
				for(String[] user:ls_Users) {
					if(childGridReader.isDataAvailable("Vehicle",user[0].toString())){
						childGridReader.clickAction("Name",sTestData[0].toString(),"Un-Map Staff and Students");
						AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("UnMap User Vehicle"));
						//call unmap function
						userStopMappingPage = new UserStopMappingPage(driver);
						gridReader.clickAction("Stop", sTestData[2].toString(), "Select");
						userStopMappingPage.clickUnMap();
						Reporter.log(user[0].toString()+" Staff and Students Un mapped successfully",true);
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			}
			Reporter.log(sTestData[1].toString()+" Students and Staff Un mapped to Schedule successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(priority=6,dataProvider="MapDriver")
	public void f_MapDriver(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Mapping Driver to Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			String[][] studentDetails = TestDataProvider.getDependentDatainStr("ScheduleData","MapDriver",sTestData[1].toString());
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())) {
				gridReader.clickAction("Name",sTestData[0].toString(),"Map Driver");
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Map Driver to Schedule"));
				userStopMappingPage = new UserStopMappingPage(driver);
				userStopMappingPage.map_StudentStaff(studentDetails);
				waitForLoad(driver);
			}
				//TC Verification
			objHomePage.selectSchedules();
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				for(int i=0;i<studentDetails.length;i++) {
					if(childGridReader.isDataAvailable("Vehicle",studentDetails[i].toString())){
						childGridReader.clickAction("Name",sTestData[0].toString(),"CheckUnmapDriver");
						//check if Un-Map Driver icon is available
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Driver", studentDetails[1].toString());
						childGridReader.verifyDatainGrid(dataMap);
						Reporter.log(studentDetails[i].toString()+" Driver mapped successfully",true);
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			}
			Reporter.log(sTestData[1].toString()+" Driver mapped to Schedule successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority=7,dataProvider="UnMapDriver")
	public void g_UnMapDriver(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Un Mapping Driver from Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			ArrayList<Object[]> studentDetails = TestDataProvider.getDependentData("ScheduleData","DriverUnMapping",sTestData[0].toString());
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			objHomePage.selectSchedules();
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				String[] lsUsers;
				ArrayList<String[]> ls_Users = new ArrayList<String[]>();
				for (Object[] objArray : studentDetails){
					lsUsers = (String[]) objArray[0];
					ls_Users.add(lsUsers);
				}
				for(String[] user:ls_Users) {
					if(childGridReader.isDataAvailable("Vehicle",user[0].toString())){
						childGridReader.clickAction("Name",sTestData[0].toString(),"Un-Map Driver");
						gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Driver", "");
						childGridReader.verifyDatainGrid(dataMap);
						Reporter.log(user[0].toString()+" Driver Un mapped successfully",true);
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			}
			Reporter.log(sTestData[1].toString()+" Driver Un mapped to Schedule successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority=8,dataProvider="MapConductor")
	public void h_MapConductor(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Mapping Conductor to Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			String[][] studentDetails = TestDataProvider.getDependentDatainStr("ScheduleData","selectStudents",sTestData[1].toString());
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())) {
				gridReader.clickAction("Name",sTestData[0].toString(),"Map Staff and Students");
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule User Vehicle"));
				userStopMappingPage = new UserStopMappingPage(driver);
				userStopMappingPage.map_StudentStaff(studentDetails);
				waitForLoad(driver);
			}
				//TC Verification
			objHomePage.selectSchedules();
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				for(int i=0;i<studentDetails.length;i++) {
					if(childGridReader.isDataAvailable("Vehicle",studentDetails[i].toString())){
						childGridReader.clickAction("Name",sTestData[0].toString(),"CheckUnmapConductor");
						//check if Un-Map Staff and Students icon is available
						gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Conductor", studentDetails[1].toString());
						childGridReader.verifyDatainGrid(dataMap);
						Reporter.log(studentDetails[i].toString()+" Conductor mapped successfully",true);
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			}
			Reporter.log(sTestData[1].toString()+" Conductor mapped to Schedule successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority=9,dataProvider="UnMapConductor")
	public void h_UnMapConductor(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Un Mapping Conductor from Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			ArrayList<Object[]> studentDetails = TestDataProvider.getDependentData("ScheduleData","StudentUnMapping",sTestData[0].toString());
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			objHomePage.selectSchedules();
			if(gridReader.isDataAvailable("Name",sTestData[0].toString())){
				gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
				ChildGridReader childGridReader = new ChildGridReader(driver,"FlatGrid",gridReader.dataRowNo);
				String[] lsUsers;
				ArrayList<String[]> ls_Users = new ArrayList<String[]>();
				for (Object[] objArray : studentDetails){
					lsUsers = (String[]) objArray[0];
					ls_Users.add(lsUsers);
				}
				for(String[] user:ls_Users) {
					if(childGridReader.isDataAvailable("Vehicle",user[0].toString())){
						childGridReader.clickAction("Name",sTestData[0].toString(),"Un-Map Conductor");
						gridReader.clickAction("Name",sTestData[0].toString(),"Expand");
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Conductor", sTestData[0].toString());
						childGridReader.verifyDatainGrid(dataMap);
						Reporter.log(user[0].toString()+" Conductor Un mapped successfully",true);
					}
				}
				gridReader.collapseSubGrid("Name", sTestData[0].toString());
			}
			Reporter.log(sTestData[1].toString()+" Conductor Un mapped to Schedule successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(priority=10,dataProvider="EditSchedule")//,dependsOnMethods = {"a_AddRoute"})
	public void i_EditSchedule(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		//addRoutePage = new AddRoutePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Schedule")) {
				Reporter.log("As we are already on Schedule Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectSchedules();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Name",sTestData[0].toString(),"Edit");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Route"));
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			gridReader.isDataAvailable("Name",sTestData[2].toString()+" "+ sTestData[3].toString()+" "+ sTestData[4].toString());
			Reporter.log(sTestData[0].toString()+" Schedule Data verified successfully<br>",true);
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
	
	@Test(priority=11,dataProvider="DeactivateSchedule")//,dependsOnMethods = {"a_AddRoute"}
	public void j_InactivateSchedule(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			objHomePage.selectSchedules();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Name",sTestData[0].toString(),"Deactivate");
			gridReader.clickAction("Name",sTestData[0].toString(),"View");
			Reporter.log(sTestData[1].toString()+" Schedule deactivated successfuly<br>",true);
			gridReader.verifyStatusinGrid("Name",sTestData[0].toString(), "InActive");
			Reporter.log("=====TestCase Inactivate Schedule and Verification Completed=====", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=5,dataProvider="ActivateSchedule")//,dependsOnMethods = {"d_InactivateRoute"}
	public void e_ActivateSchedule(Object[] sTestData){
		Reporter.log("=====TestCase Activate Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Schedule")) {
				Reporter.log("As we are already on Schedule Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectSchedules();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("Name",sTestData[0].toString(),"Activate");
		gridReader.clickAction("Name",sTestData[0].toString(),"View");
				Reporter.log(sTestData[1].toString()+" Schedule re-activated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Name",sTestData[0].toString(), "Active");
			Reporter.log("=====TestCase Activate Schedule and Verification Completed=====", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=6,dataProvider="DeleteSchedule")//,dependsOnMethods = {"e_ActivateRoute"}
	public void f_DeleteSchedule(Object[] sTestData){
		Reporter.log("=====TestCase Delete Schedule Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Schedule")) {
				Reporter.log("As we are already on Schedule Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectSchedules();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Schedule"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("Name",sTestData[0].toString(),"View");
		gridReader.clickAction("Name",sTestData[0].toString(),"Delete");
		gridReader.clickAction("Name",sTestData[0].toString(),"View");
				Reporter.log(sTestData[0].toString()+" Schedule deleted successfully<br>",true);
				Reporter.log("=====TestCase Delete Schedule and Verification Completed=====", true);
	}
	catch(Exception e){
			
	}	
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


  @DataProvider (name="AddScheduleData")
  public Iterator<Object[]> getAddScheduleData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","addschedule");
       return testdata.iterator();
		}

  @DataProvider (name="MapVehicles")
  public Iterator<Object[]> getMapVehicleData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","VehicleMapping");
       return testdata.iterator();
		}
  
  @DataProvider (name="UnMapVehicles")
  public Iterator<Object[]> getMapUnVehicleData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","VehicleUnMapping");
       return testdata.iterator();
		}
  
  @DataProvider (name="MapStudents")
  public Iterator<Object[]> getMapUserData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","UserMapping");
       return testdata.iterator();
		}
  
  @DataProvider (name="UnMapUsers")
  public Iterator<Object[]> getUnMapUserData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","UserUnMapping");
       return testdata.iterator();
		}
  
  
  @DataProvider (name="MapDriver")
  public Iterator<Object[]> getMapDriverData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","DriverMapping");
       return testdata.iterator();
		}
  
  @DataProvider (name="UnMapDriver")
  public Iterator<Object[]> getUnMapDriverData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","DriverUnMapping");
       return testdata.iterator();
		}
  
  
  @DataProvider (name="MapConductor")
  public Iterator<Object[]> getMapConductorData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","ConductorMapping");
       return testdata.iterator();
		}
  
  @DataProvider (name="UnMapConductor")
  public Iterator<Object[]> getUnMapConductorData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","ConductorUnMapping");
       return testdata.iterator();
		}
  @DataProvider(name = "EditSchedule")
  public	Iterator<Object[]> getEditScheduleData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","EditSchedule");
       return testdata.iterator();
		}
  @DataProvider(name = "DeactivateSchedule")
  public	Iterator<Object[]> getDeactivateRouteData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","DeactivateSchedule");
       return testdata.iterator();
		}
  @DataProvider(name = "ActivateSchedule")
  public	Iterator<Object[]> getActivateScheduleData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","ActivateSchedule");
       return testdata.iterator();
		}
  @DataProvider(name = "DeleteSchedule")
  public	Iterator<Object[]> getDeleteScheduleData() throws Exception{
  	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ScheduleData","DeleteSchedule");
       return testdata.iterator();
		}
  @AfterSuite
  public void afterSuite() {
	  driver.close();
  }

}
