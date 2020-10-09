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

import PageFactory.AddStaffPage;
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
 * Purpose : Staff Test Cases
 */
public class TestStaffFeatures extends TestBase{

	LoginPage objLogin;
	HomePage	objHomePage;
	AddStaffPage addStaffPage;
	CommonPageObjects cpo;
	

	@Test(enabled=false)//(priority=1,dataProvider="AddStaffData")
	public void a_AddStaff(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Staff Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Staff Details")) {
				Reporter.log("As we are already on Staff Page, ignoring the navigational click",true);
			}else {
		objHomePage.selectStaff();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Staff Details"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Staff"));
		addStaffPage = new AddStaffPage(driver);
		boolean bTCFlag = addStaffPage.fill_AddStaff_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Name",sTestData[2].toString()+" "+sTestData[3].toString()+ " "+sTestData[4].toString());
			gridReader.fetchSatTraxID("Name",sTestData[2].toString()+" "+sTestData[3].toString()+ " "+sTestData[4].toString());
			Xls_Reader reader = new Xls_Reader();
			reader.writeToExcel("AddStaff", "SatTraxID",SessionDetails.currentRowRef, SessionDetails.SatTraxID);
			Reporter.log(sTestData[2].toString()+" "+sTestData[3].toString()+ " "+sTestData[4].toString()+" Staff Data added successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("SatTraX ID", SessionDetails.SatTraxID);
			dataMap.put("Name",sTestData[2].toString()+" "+sTestData[3].toString()+ " "+sTestData[4].toString());
			dataMap.put("Email", sTestData[5].toString());
			dataMap.put("Mobile-Primary", sTestData[6].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Staff and Verification Completed=====", true);
		}
		}
		catch(Exception e) {
			Reporter.log("=====TestCase Add Staff Was not successful=====", true);
		}
	}
	
	@DataProvider (name="AddStaffData")
    public	Iterator<Object[]> getAddStaffData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddStaff");
    	System.out.println(testdata.size());
         return testdata.iterator();
		}
	@Test(enabled=false)//(priority=2,dataProvider="EditStaff")//,dependsOnMethods = {"a_AddStaff"})
	public void b_EditStaff(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Staff Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addStaffPage = new AddStaffPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Staff Details")) {
				Reporter.log("As we are already on Staff Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStaff();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Staff Details"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(), "Edit Staff");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Staff"));
			addStaffPage.fill_EditStaff_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
			Reporter.log(sTestData[0].toString()+" Staff Data verified successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			//dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("SatTraX ID", sTestData[0].toString());
			dataMap.put("Name","Auto "+ sTestData[3].toString()+ " "+sTestData[5].toString());
			dataMap.put("Email", sTestData[6].toString());
			dataMap.put("Mobile-Primary", sTestData[7].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Staff and Verification Completed=====", true);
			}
		}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Staff Was not successful=====", true);
			}
		}
	
	@Test(priority=3,dataProvider="DeactivateStaff")//,dependsOnMethods = {"a_AddStaff"}
	public void c_InactivateStaff(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Staff Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Staff Details")) {
				Reporter.log("As we are already on Staff Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStaff();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Staff Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Deactivate");
			cpo.handleAlert();
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Staff deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "InActive");
				Reporter.log("=====TestCase Inactivate Staff and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=4,dataProvider="ActivateStaff")//,dependsOnMethods = {"c_InactivateStaff"}
	public void d_ActivateStaff(Object[] sTestData){
		Reporter.log("=====TestCase Activate Staff Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Staff Details")) {
				Reporter.log("As we are already on Staff Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStaff();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Staff Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {				
				gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Activate");
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Active");
				Reporter.log(sTestData[0].toString()+" Data re-activated successfuly<br>",true);
				Reporter.log("=====TestCase Activate Staff and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=5,dataProvider="DeleteStaff")//,dependsOnMethods = {"d_ActivateStaff"}
	public void e_DeleteStaff(Object[] sTestData){
		Reporter.log("=====TestCase Delete Staff Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addStaffPage = new AddStaffPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Staff Details")) {
				Reporter.log("As we are already on Staff Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStaff();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Staff Details"));
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
				Reporter.log(sTestData[0].toString()+" Staff deleted successfully<br>",true);
				Reporter.log("=====TestCase Delete Staff and Verification Completed=====", true);
			}
			}
	}
	catch(Exception e){
			
	}	
	}
    @DataProvider(name = "EditStaff")
    public	Iterator<Object[]> getEditStaffData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StaffData","EditStaff");
         return testdata.iterator();
		}
    @DataProvider(name = "DeactivateStaff")
    public	Iterator<Object[]> getDeactivateStaffData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StaffData","DeactivateStaff");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateStaff")
    public	Iterator<Object[]> getActivateStaffData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StaffData","ActivateStaff");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteStaff")
    public	Iterator<Object[]> getDeleteStaffData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StaffData","DeleteStaff");
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