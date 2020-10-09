package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageFactory.AddVehiclePage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.TestBase;
import utility.DateHandler;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Vehicle Test Cases
 */
public class TestVehicleFeatures extends TestBase{

	LoginPage objLogin;
	HomePage	objHomePage;
	AddVehiclePage addVehiclePage;
	CommonPageObjects cpo;
	
	@Test(priority=1,dataProvider="AddVehicleData")
	public void a_AddVehicle(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Vehicle Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		DateHandler date = new DateHandler(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Vehicle")) {
				Reporter.log("As we are already on Vehicle Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectVehicles();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Vehicle"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add New Vehicle"));
		addVehiclePage = new AddVehiclePage(driver);
		boolean bTCFlag = addVehiclePage.fill_AddVehicle_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Registration Number",sTestData[1].toString());
			Reporter.log(sTestData[1].toString()+" Vehicle Data added successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("Registration Number", sTestData[1].toString());
			dataMap.put("Seating Capacity",sTestData[6].toString());
			dataMap.put("Fuel Average", sTestData[7].toString());
			String sDate= sTestData[5].toString().split(" ")[0]+"/"+date.getMonthNumber(sTestData[5].toString().split(" ")[1])+"/"+sTestData[5].toString().split(" ")[2];
			dataMap.put("Mfg Year", sDate);
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Vehicle and Verification Completed=====", true);
		}
		}
		catch(Exception e) {
			Reporter.log("=====TestCase Add Vehicle Was not successful=====", true);
		}
	}
	
	@DataProvider (name="AddVehicleData")
    public	Iterator<Object[]> getAddVehicleData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddVehicle");
    	System.out.println(testdata.size());
         return testdata.iterator();
		}
	@Test(priority=2,dataProvider="EditVehicle")//,dependsOnMethods = {"a_AddVehicle"})
	public void b_EditVehicle(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Vehicle Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addVehiclePage = new AddVehiclePage(driver);
		DateHandler date = new DateHandler(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Vehicle")) {
				Reporter.log("As we are already on Vehicle Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectVehicles();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Vehicle"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Registration Number",sTestData[0].toString(), "Edit");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Vehicle"));
			addVehiclePage.fill_EditVehicle_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			if(gridReader.isDataAvailable("Registration Number",sTestData[2].toString())){
			Reporter.log(sTestData[2].toString()+" Vehicle Data verified successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[1].toString());
			dataMap.put("Registration Number", sTestData[2].toString());
			dataMap.put("Seating Capacity",sTestData[7].toString());
			dataMap.put("Fuel Average", sTestData[8].toString());
			String sDate= sTestData[6].toString().split(" ")[0]+"/"+date.getMonthNumber(sTestData[6].toString().split(" ")[1])+"/"+sTestData[6].toString().split(" ")[2];
			dataMap.put("Mfg Year", sDate);
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Vehicle and Verification Completed=====", true);
			}
		}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Vehicle Was not successful=====", true);
			}
		}
	
	@Test(priority=3,dataProvider="DeactivateVehicle")//,dependsOnMethods = {"a_AddVehicle"}
	public void c_InactivateVehicle(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Vehicle Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Vehicle")) {
				Reporter.log("As we are already on Vehicle Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectVehicles();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Vehicle"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Registration Number",sTestData[0].toString(),"Deactivate");
			if(gridReader.isDataAvailable("Registration Number",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Vehicle deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Registration Number",sTestData[0].toString(), "InActive");
				Reporter.log("=====TestCase Inactivate Vehicle and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=4,dataProvider="ActivateVehicle")//,dependsOnMethods = {"c_InactivateVehicle"}
	public void d_ActivateVehicle(Object[] sTestData){
		Reporter.log("=====TestCase Activate Vehicle Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Vehicle")) {
				Reporter.log("As we are already on Vehicle Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectVehicles();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Vehicle"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("Registration Number",sTestData[0].toString())) {				
				gridReader.clickAction("Registration Number",sTestData[0].toString(),"Activate");
				gridReader.verifyStatusinGrid("Registration Number",sTestData[0].toString(), "Active");
				Reporter.log(sTestData[0].toString()+" Data re-activated successfuly<br>",true);
				Reporter.log("=====TestCase Activate Vehicle and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=6,dataProvider="DeleteDevice")//,dependsOnMethods = {"d_ActivateDevice"}
	public void f_DeleteDevice(Object[] sTestData){
		Reporter.log("=====TestCase Delete Device Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Device")) {
				Reporter.log("As we are already on Device Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectDevices();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Device"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		if(gridReader.isDataAvailable("Device Serial No",sTestData[0].toString())){
			gridReader.clickAction("Device Serial No",sTestData[0].toString(),"delete");
			if(!gridReader.isDataAvailable("Device Serial No",sTestData[0].toString())){
				Reporter.log(sTestData[0].toString()+" Device deleted successfully<br>",true);
				Reporter.log("=====TestCase Delete Device and Verification Completed=====", true);
			}
		}
	}
	catch(Exception e){
			
	}	
	}
	@Test(priority=5,dataProvider="DeleteVehicle")//,dependsOnMethods = {"c_InactivateVehicle"}
	public void d_DeleteVehicle(Object[] sTestData){
		Reporter.log("=====TestCase Delete Vehicle Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Vehicle")) {
				Reporter.log("As we are already on Vehicle Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectVehicles();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Vehicle"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("Registration Number",sTestData[0].toString())) {				
				gridReader.clickAction("Registration Number",sTestData[0].toString(),"Delete");
				if(!gridReader.isDataAvailable("Registration Number",sTestData[0].toString())){
				Reporter.log(sTestData[0].toString()+" Data deleted successfuly<br>",true);
				Reporter.log("=====TestCase Delete Vehicle and Verification Completed=====", true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @DataProvider(name = "EditVehicle")
    public	Iterator<Object[]> getEditVehicleData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("VehicleData","EditVehicle");
    	//ArrayList<Object[]> testdata = TestDataProvider.getExcelData("EditVehicle");
         return testdata.iterator();
		}
    @DataProvider(name = "DeactivateVehicle")
    public	Iterator<Object[]> getDeactivateVehicleData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("VehicleData","DeactivateVehicle");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateVehicle")
    public	Iterator<Object[]> getActivateVehicleData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("VehicleData","ActivateVehicle");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteVehicle")
    public	Iterator<Object[]> getDeleteVehicleData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("VehicleData","DeleteVehicle");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteDevice")
    public	Iterator<Object[]> getDeleteDeviceData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("VehicleData","DeleteDevice");
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