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

import PageFactory.AddDevicePage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.TestBase;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Device Test Cases
 */
public class TestDeviceFeatures extends TestBase{
	LoginPage objLogin;
	HomePage	objHomePage;
	AddDevicePage addDevicePage;
	CommonPageObjects cpo;
	
	@Test(priority=1,dataProvider="AddDeviceData")
	public void a_AddDevice(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Device Started=====", true);
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
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add New Device"));
		addDevicePage = new AddDevicePage(driver);
		boolean bTCFlag = addDevicePage.fill_AddDevice_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			objHomePage.selectDevices();
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Device Serial No",sTestData[1].toString());
			Reporter.log(sTestData[1].toString()+" Device Data verified successfully",true);
			//Prepare a Hashmap for verifying data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("Device Serial No", sTestData[1].toString());
			dataMap.put("Mobile Number", sTestData[2].toString());
			dataMap.put("Issued By Sattrax", sTestData[3].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Device  and Verification Completed=====", true);
		}
		}
		catch(Exception e){

	}
	}
	@DataProvider (name = "AddDeviceData")
    public	Iterator<Object[]> getAddDeviceData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddDevices");
    	System.out.println(testdata.size());
         return testdata.iterator();
		}
	@Test(priority=2,dataProvider="EditDevice")//,dependsOnMethods = {"a_AddDevice"})
	public void b_EditDevice(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Device Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addDevicePage = new AddDevicePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Device")) {
				Reporter.log("As we are already on Device Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectDevices();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Device"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Device Serial No",sTestData[0].toString(), "Edit");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Device"));
			addDevicePage.fill_EditDevice_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			objHomePage.selectDevices();
			Reporter.log("FAIL : Page does not return to Device Grid after the device been edited<br>",true);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Device"));
			if(gridReader.isDataAvailable("Device Serial No",sTestData[2].toString())) {
			Reporter.log(sTestData[2].toString()+" Device edited verified successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[1].toString());
			dataMap.put("Device Serial No", sTestData[2].toString());
			dataMap.put("Mobile Number", sTestData[3].toString());
			dataMap.put("Issued By Sattrax", sTestData[4].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Device and Verification Completed=====", true);
			}
		}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Device Was not successful=====", true);
			}
		}
	
	@Test(priority=3,dataProvider="DeactivateDevice")//,dependsOnMethods = {"b_EditDevice"}
	public void c_InactivateDevice(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Device Started=====", true);
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
			gridReader.clickAction("Device Serial No",sTestData[0].toString(),"De Activate");
			cpo.handleAlert();
			if(gridReader.isDataAvailable("Device Serial No",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Device deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Device Serial No",sTestData[0].toString(), "InActive");
				Reporter.log("=====TestCase Inactivate Device and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=4,dataProvider="ActivateDevice")//,dependsOnMethods = {"c_InactivateDevice"}
	public void d_ActivateDevice(Object[] sTestData){
		Reporter.log("=====TestCase Activate Device Started=====", true);
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
		gridReader.clickAction("Device Serial No",sTestData[0].toString(),"Activate");
		if(gridReader.isDataAvailable("Device Serial No",sTestData[0].toString())) {
			Reporter.log(sTestData[0].toString()+" Device activated successfuly<br>",true);
			gridReader.verifyStatusinGrid("Device Serial No",sTestData[0].toString(), "Active");
			Reporter.log("=====TestCase Activate Device and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(priority=5,dataProvider="DeleteDevice")//,dependsOnMethods = {"d_ActivateDevice"}
	public void f_DeleteDeviceInUse(Object[] sTestData){
		Reporter.log("=====TestCase Delete Device In Use Started=====", true);
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
			if(gridReader.isDataAvailable("Device Serial No",sTestData[0].toString())){
				Reporter.log(sTestData[0].toString()+" Device cannot be deleted as it has references<br>",true);
				Reporter.log("=====TestCase Delete Device In Use  and Verification Completed=====", true);
			}
		}
	}
	catch(Exception e){
			
	}	
	}
    @DataProvider(name = "EditDevice")
    public	Iterator<Object[]> getEditDeviceData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DeviceData","EditDevice");
         return testdata.iterator();
		}
    @DataProvider(name = "DeactivateDevice")
    public	Iterator<Object[]> getDeactivateDeviceData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DeviceData","DeactivateDevice");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateDevice")
    public	Iterator<Object[]> getActivateDeviceData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DeviceData","ActivateDevice");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteDevice")
    public	Iterator<Object[]> getDeleteDeviceData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DeviceData","DeleteDevice");
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