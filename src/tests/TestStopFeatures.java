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

import PageFactory.AddStopPage;
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
public class TestStopFeatures extends TestBase{

	LoginPage objLogin;
	HomePage	objHomePage;
	AddStopPage addStopPage;
	CommonPageObjects cpo;
	
	
	@Test(priority=1,dataProvider="AddStopData")
	public void a_AddStop(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Stop Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Stop")) {
				Reporter.log("As we are already on Stop Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStops();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Stop"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Stop"));
		addStopPage = new AddStopPage(driver);
		boolean bTCFlag = addStopPage.fill_AddStop_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Stop Name",sTestData[1].toString());
			Reporter.log(sTestData[1].toString()+" Stop Data added successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("Stop Name", sTestData[1].toString());
			dataMap.put("Address",sTestData[3].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Stop and Verification Completed=====", true);
		}
		}
		catch(Exception e) {
			Reporter.log("=====TestCase Add Stop Was not successful=====", true);
		}
	}
	
	@DataProvider (name="AddStopData")
    public	Iterator<Object[]> getAddStopData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddStop");
    	//System.out.println(testdata.size());
         return testdata.iterator();
         
		}
	@Test(priority=2,dataProvider="EditStop")//,dependsOnMethods = {"a_AddStop"})
	public void b_EditStop(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Stop Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addStopPage = new AddStopPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Stop")) {
				Reporter.log("As we are already on Stop Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStops();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Stop"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Stop Name",sTestData[0].toString(), "EditStop");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Stop"));
			addStopPage.fill_EditStop_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			if(gridReader.isDataAvailable("Name",sTestData[2].toString())) {
			Reporter.log(sTestData[2].toString()+" Stop Data verified successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[1].toString());
			dataMap.put("Stop Name", sTestData[2].toString());
			dataMap.put("Address",sTestData[8].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Stop and Verification Completed=====", true);
			}
		}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Stop Was not successful=====", true);
			}
		}
	
	@Test(priority=3,dataProvider="DeactivateStop")//,dependsOnMethods = {"a_AddStop"}
	public void c_InactivateStop(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Stop Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Stop")) {
				Reporter.log("As we are already on Stop Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStops();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Stop"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Stop Name",sTestData[0].toString(),"ChangeStatus");
			if(gridReader.isDataAvailable("Stop Name",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Stop deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Stop Name",sTestData[0].toString(), "InActive");
				Reporter.log("=====TestCase Inactivate Stop and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=4,dataProvider="ActivateStop")//,dependsOnMethods = {"c_InactivateStop"}
	public void d_ActivateStop(Object[] sTestData){
		Reporter.log("=====TestCase Activate Stop Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Stop")) {
				Reporter.log("As we are already on Stop Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStops();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Stop"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("Stop Name",sTestData[0].toString())) {				
				gridReader.clickAction("Stop Name",sTestData[0].toString(),"ChangeStatus");
				gridReader.verifyStatusinGrid("Stop Name",sTestData[0].toString(), "Active");
				Reporter.log(sTestData[0].toString()+" Data re-activated successfuly<br>",true);
				Reporter.log("=====TestCase Activate Stop and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=5,dataProvider="DeleteStop")//,dependsOnMethods = {"d_ActivateStop"}
	public void e_DeleteStop(Object[] sTestData){
		Reporter.log("=====TestCase Delete Stop Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addStopPage = new AddStopPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Stop")) {
				Reporter.log("As we are already on Stop Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStops();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Stop"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("Stop Name",sTestData[0].toString())) {
				gridReader.clickAction("Stop Name",sTestData[0].toString(),"Delete");
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
			if(!gridReader.isDataAvailable("Stop Name",sTestData[0].toString())){
				Reporter.log(sTestData[0].toString()+" Stop deleted successfully<br>",true);
				Reporter.log("=====TestCase Delete Stop and Verification Completed=====", true);
			}
			}
	}
	catch(Exception e){
			
	}	
	}
    @DataProvider(name = "EditStop")
    public	Iterator<Object[]> getEditStopData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StopData","EditStop");
    	//ArrayList<Object[]> testdata = TestDataProvider.getExcelData("EditStop");
         return testdata.iterator();
		}
    @DataProvider(name = "DeactivateStop")
    public	Iterator<Object[]> getDeactivateStopData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StopData","DeactivateStop");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateStop")
    public	Iterator<Object[]> getActivateStopData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StopData","ActivateStop");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteStop")
    public	Iterator<Object[]> getDeleteStopData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StopData","DeleteStop");
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