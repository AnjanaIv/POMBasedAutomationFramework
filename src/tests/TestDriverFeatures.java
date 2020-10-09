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

import PageFactory.AddDriverPage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.SessionDetails;
import config.TestBase;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
import utility.Xls_Reader;
/*
 * author: anjana.iv
 * Purpose : Route Test Cases
 */
public class TestDriverFeatures extends TestBase{

	LoginPage objLogin;
	HomePage	objHomePage;
	AddDriverPage addDriverPage;
	CommonPageObjects cpo;
	
	@Test(priority = 1, dataProvider = "AddDriverData")
	public void a_AddDriver(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Driver Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Driver Details")) {
				Reporter.log("As we are already on Driver Page, ignoring the navigational click",true);
			}else {
		objHomePage.selectDriver();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Driver Details"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Driver"));
		addDriverPage = new AddDriverPage(driver);
		boolean bTCFlag = addDriverPage.fill_AddDriver_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable( "Name",sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString());
			gridReader.fetchSatTraxID( "Name", sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString());
			Xls_Reader reader = new Xls_Reader();
			reader.writeToExcel("AddDriver", "SatTraxID",SessionDetails.currentRowRef, SessionDetails.SatTraxID);
			Reporter.log(sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString()+" Driver Data verified successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("SatTraxID", SessionDetails.SatTraxID);
			dataMap.put("Name",sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString());
			dataMap.put("Email", sTestData[4].toString());
			dataMap.put("Mobile-Primary", sTestData[5].toString());
			dataMap.put("Driving Licence Number", sTestData[9].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Add Driver  and Verification Completed=====", true);
		}
	}
	catch(Exception e) {
		Reporter.log("=====TestCase Edit Conductor Was not successful=====", true);
	}
	}
	@Test(priority=2,dataProvider="EditDriver")//,dependsOnMethods = {"a_AddDriver"})
	public void b_EditDriver(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Driver Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addDriverPage = new AddDriverPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Driver Details")) {
				Reporter.log("As we are already on Driver Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectDriver();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Driver Details"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(), "Edit Driver");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Driver"));
			addDriverPage.fill_EditDriver_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString());
			Reporter.log(sTestData[0].toString()+" Driver edited and verified successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[1].toString());
			dataMap.put("SatTraX ID", sTestData[0].toString());
			dataMap.put("Name",sTestData[2].toString()+" "+sTestData[3].toString()+ " "+sTestData[4].toString());
			dataMap.put("Email", sTestData[5].toString());
			dataMap.put("Mobile-Primary", sTestData[6].toString());
			dataMap.put("Driving Licence Number", sTestData[10].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Driver and Verification Completed=====", true);
			}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Driver Was not successful=====", true);
			}
		}
	
	@Test(priority=3,dataProvider="DeactivateDriver")//,dependsOnMethods = {"a_AddDriver"}
	public void c_InactivateDriver(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Driver Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Driver Details")) {
				Reporter.log("As we are already on Driver Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectDriver();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Driver Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Deactivate");
			cpo.handleAlert();
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Driver deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Inactive");
				Reporter.log("=====TestCase Inactivate Driver and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=4,dataProvider="ActivateDriver")//,dependsOnMethods = {"c_InactivateDriver"}
	public void d_ActivateDriver(Object[] sTestData){
		Reporter.log("=====TestCase Activate Driver Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Driver Details")) {
				Reporter.log("As we are already on Driver Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectDriver();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Driver Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {				
				gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Activate");
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Active");
				Reporter.log(sTestData[0].toString()+" Data re-activated successfuly<br>",true);
				Reporter.log("=====TestCase Activate Driver and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=5,dataProvider="DeleteDriver")//,dependsOnMethods = {"d_ActivateDriver"}
	public void e_DeleteDriver(Object[] sTestData){
		Reporter.log("=====TestCase Delete Driver Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addDriverPage = new AddDriverPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Driver Details")) {
				Reporter.log("As we are already on Driver Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectDriver();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Driver Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
				gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Delete");
				cpo.handleAlert();
			//if (driver.findElements(By.cssSelector("#toaster")).size() > 0) {// && toaster.isDisplayed()) {
				
					//Actions builder = new Actions(driver);
				    //builder.moveToElement(driver.findElement(By.className("toast-title"))).moveByOffset(2,2).click().build().perform();
					//String toastTitle = driver.findElement(By.className("toast-title")).getText();
					//String toastMsg = driver.findElement(By.className("toast-message")).getText();
					//System.out.println("Title of Toast Message "+ toastTitle);
					//System.out.println("Message of Toast Message "+ toastMsg);
					//	ssert.assertEquals(toastTitle, "Success");
					//Assert.assertEquals(toastMsg, "Record deleted successfully");
					
			//}
			if(!gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())){
				Reporter.log(sTestData[0].toString()+" Driver deleted verified successfully<br>",true);
				Reporter.log("=====TestCase Delete Driver and Verification Completed=====", true);
			}
			}
	}
	catch(Exception e){
			
	}	
	}
	@DataProvider(name = "AddDriverData")
    public	Iterator<Object[]> getAddDriverData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddDriver");
         return testdata.iterator();
		}
    @DataProvider(name = "EditDriver")
    public	Iterator<Object[]> getEditParentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DriverData","EditDriver");
         return testdata.iterator();
    	}
    @DataProvider(name = "DeactivateDriver")
    public	Iterator<Object[]> getDeactivateParentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DriverData","DeactivateDriver");
         return testdata.iterator();
    	}
    @DataProvider(name = "ActivateDriver")
    public	Iterator<Object[]> getActivateParentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DriverData","ActivateDriver");
         return testdata.iterator();
    	}
    @DataProvider(name = "DeleteDriver")
    public	Iterator<Object[]> getDeleteParentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("DriverData","DeleteDriver");
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