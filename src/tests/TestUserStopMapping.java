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

import PageFactory.UserStopMappingPage;
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
public class TestUserStopMapping extends TestBase{
	LoginPage objLogin;
	HomePage	objHomePage;
	GridReader gridReader;
	UserStopMappingPage userStopMappingPage;
	CommonPageObjects cpo;
	
	@Test(priority=1,dataProvider="UserStopData")
	public void a_UserStopMapping(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase User Stop Mapping Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Stop Mapping")) {
				Reporter.log("As we are already on Stop Mapping Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectUserStop();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Stop Mapping"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Map user stop"));
		userStopMappingPage = new UserStopMappingPage(driver);
		boolean bTCFlag = userStopMappingPage.fill_UserStopMapping_form(sTestData);
		String[][] mappingArray = TestDataProvider.getDependentDatainStr("UserStopMapping","selectStudents",sTestData[1].toString());
		userStopMappingPage.map_StudentStaff(mappingArray);
		//TC Verification
		if(bTCFlag) {
			gridReader = new GridReader(driver,"dvStudentPartial");
			for (int i=0;i<mappingArray.length;i++){
				if(mappingArray[i][0].toString().equalsIgnoreCase("Student")) {
					if(gridReader.isDataAvailable("Sattrax Id",mappingArray[i][1])) {
						Reporter.log(mappingArray[i][1]+" Student Stop Mapping done successfully",true);
						//Prepare a Hashmap for verifing data in table grid columns
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Stop", sTestData[1].toString());
						gridReader.verifyDatainGrid(dataMap);
					}
				}else {
					userStopMappingPage.clickMapStaff();
					gridReader = new GridReader(driver,"dvStaffPartial");
					if(gridReader.isDataAvailable("Sattrax Id",mappingArray[i][2])) {
						Reporter.log(mappingArray[i][1]+" Staff Stop Mapping done successfully",true);
						//Prepare a Hashmap for verifing data in table grid columns
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Stop", sTestData[1].toString());
						gridReader.verifyDatainGrid(dataMap);
					}
				}	
			}
			Reporter.log("=====TestCase Student/Staff Stop Mapping and Verification Completed=====", true);
			}
			}
			catch(Exception e) {
				Reporter.log("=====TestCase User Stop Mapping Was not successful=====", true);
			}
	}
	
	@Test(priority=2,dataProvider="UnMapData")
	public void b_UserStopUnMapping(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase User Stop UnMapping Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Stop Mapping")) {
				Reporter.log("As we are already on Stop Mapping Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectUserStop();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Stop Mapping"));
			}
			if(sTestData[1].toString().equalsIgnoreCase("Student")) {
				gridReader = new GridReader(driver,"dvStudentPartial");
				if(gridReader.isDataAvailable("Sattrax Id",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Student Stop Mapping is found",true);
				gridReader.clickAction("Stop", sTestData[2].toString(), "Select");
				userStopMappingPage = new UserStopMappingPage(driver);
				userStopMappingPage.clickUnMap();
				cpo.handleAlert();
				if(gridReader.isDataAvailable("Sattrax Id",sTestData[0].toString())) {
					Reporter.log(sTestData[0].toString()+" Student Stop UnMapping done successfully",true);
					LinkedHashMap dataMap = new LinkedHashMap();
					dataMap.put("Stop", "");
					gridReader.verifyDatainGrid(dataMap);
					
				}
				}
			}else {
				userStopMappingPage = new UserStopMappingPage(driver);
				userStopMappingPage.clickMapStaff();
				gridReader = new GridReader(driver,"dvStaffPartial");
				if(gridReader.isDataAvailable("Sattrax Id",sTestData[0].toString())) {
					Reporter.log(sTestData[0].toString()+" Staff Stop Mapping is found",true);
					gridReader.clickAction("Stop", sTestData[2].toString(), "Select");
					userStopMappingPage = new UserStopMappingPage(driver);
					userStopMappingPage.clickUnMap();
					if(gridReader.isDataAvailable("Sattrax Id",sTestData[0].toString())) {
						Reporter.log(sTestData[0].toString()+" Staff Stop UnMapping done successfully",true);
						LinkedHashMap dataMap = new LinkedHashMap();
						dataMap.put("Stop", "");
						gridReader.verifyDatainGrid(dataMap);
						
					}
				}
			}
			Reporter.log("=====TestCase Student Staff Stop UnMapping and Verification Completed=====", true);
		}
			catch(Exception e) {
				Reporter.log("=====TestCase User Stop UnMapping Was not successful=====", true);
			}
	}

    @DataProvider (name="UserStopData")
    public	Iterator<Object[]> getUserStopData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("UserStopMapping","selectStopForMapping");
         return testdata.iterator();
		}
    
    @DataProvider (name="UnMapData")
    public	Iterator<Object[]> getUnMapData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("UserStopMapping","UnMapping");
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