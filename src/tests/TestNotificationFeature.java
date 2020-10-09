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

import PageFactory.AddNotificationPage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.TestBase;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Notification Test Cases
 */
public class TestNotificationFeature extends TestBase{
	LoginPage objLogin;
	HomePage	objHomePage;
	AddNotificationPage addNotificationPage;
	CommonPageObjects cpo;
	
	@Test(priority=1,dataProvider="AddNotification")
	public void a_AddNotification(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Notification Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Notification Details")) {
				Reporter.log("As we are already on Notification Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectNotification();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Notification Details"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add New Notification"));
		addNotificationPage = new AddNotificationPage(driver);
		boolean bTCFlag = addNotificationPage.fill_AddNotification_form(sTestData);
	
		//TC Verification
		if(bTCFlag) {
			objHomePage.selectNotification();
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Notification",sTestData[3].toString());
			Reporter.log(sTestData[3].toString()+" Notification Data added successfully",true);
			//Prepare a Hashmap for verifying data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("Module", sTestData[1].toString());
			dataMap.put("Event", sTestData[2].toString());
			dataMap.put("Notification", sTestData[3].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Notification Completed=====", true);
		}
		}
		catch(Exception e){

	}
	}
	@DataProvider (name = "AddNotification")
    public	Iterator<Object[]> getAddNotificationData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("Notification","AddNotification");
    	System.out.println(testdata.size());
         return testdata.iterator();
		}
	@Test(priority=2,dataProvider="EditNotification")//,dependsOnMethods = {"a_AddNotification"})
	public void b_EditNotification(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Notification Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addNotificationPage = new AddNotificationPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Notification Details")) {
				Reporter.log("As we are already on Notification Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectNotification();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Notification Details"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Notification",sTestData[0].toString(), "Edit");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Notification"));
			addNotificationPage.fill_EditNotification_form(sTestData);
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			if(gridReader.isDataAvailable("Notification",sTestData[4].toString())) {
			Reporter.log(sTestData[4].toString()+" Notification Data edited successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Notification", sTestData[4].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Notification and Verification Completed=====", true);
			}
		}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Notification Was not successful=====", true);
			}
		}
	
	@Test(priority=3,dataProvider="DeactivateNotification")//,dependsOnMethods = {"b_EditNotification"}
	public void c_InactivateNotification(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Notification Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Notification Details")) {
				Reporter.log("As we are already on Notification Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectNotification();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Notification Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Notification",sTestData[0].toString(),"De Activate");
			cpo.handleAlert();
			if(gridReader.isDataAvailable("Notification",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Notification deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Notification",sTestData[0].toString(), "InActive");
				Reporter.log("=====TestCase Inactivate Notification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=4,dataProvider="ActivateNotification")//,dependsOnMethods = {"c_InactivateNotification"}
	public void d_ActivateNotification(Object[] sTestData){
		Reporter.log("=====TestCase Activate Notification Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Notification Details")) {
				Reporter.log("As we are already on Notification Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectNotification();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Notification Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("Notification",sTestData[0].toString(),"Activate");
		if(gridReader.isDataAvailable("Notification",sTestData[0].toString())) {
			Reporter.log(sTestData[0].toString()+" Notification activated successfuly<br>",true);
			gridReader.verifyStatusinGrid("Notification",sTestData[0].toString(), "Active");
			Reporter.log("=====TestCase Activate Notification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(priority=5,dataProvider="DeleteNotification")//,dependsOnMethods = {"d_ActivateNotification"}
	public void f_DeleteNotification(Object[] sTestData){
		Reporter.log("=====TestCase Delete Notification In Use Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Notification Details")) {
				Reporter.log("As we are already on Notification Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectNotification();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Notification Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		if(gridReader.isDataAvailable("Notification",sTestData[0].toString())){
			gridReader.clickAction("Notification",sTestData[0].toString(),"delete");
			if(!gridReader.isDataAvailable("Notification",sTestData[0].toString())){
				Reporter.log(sTestData[0].toString()+" Notification deleted successfuly",true);
				Reporter.log("=====TestCase Delete Notification Completed=====", true);
			}
		}
	}
	catch(Exception e){
			
	}	
	}
    @DataProvider(name = "EditNotification")
    public	Iterator<Object[]> getEditNotificationData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("Notification","EditNotification");
         return testdata.iterator();
		}
    @DataProvider(name = "DeactivateNotification")
    public	Iterator<Object[]> getDeactivateNotificationData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("Notification","DeactivateNotification");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateNotification")
    public	Iterator<Object[]> getActivateNotificationData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("Notification","ActivateNotification");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteNotification")
    public	Iterator<Object[]> getDeleteNotificationData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("Notification","DeleteNotification");
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