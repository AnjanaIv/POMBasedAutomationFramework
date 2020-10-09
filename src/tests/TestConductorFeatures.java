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

import PageFactory.AddConductorPage;
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
public class TestConductorFeatures extends TestBase{
	LoginPage objLogin;
	HomePage	objHomePage;
	AddConductorPage addConductorPage;
	CommonPageObjects cpo;
	
	@Test(priority=1,dataProvider="AddConductorData")
	public void a_AddConductor(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Conductor Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Conductor Details")) {
				Reporter.log("As we are already on Conductor Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectConductor();
				waitForLoad(driver);
				AssertJUnit.assertEquals("Page Title Verification",cpo.getPageTitle(),"Conductor Details");
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue("Page Title Verification ",cpo.getPageTitle().equalsIgnoreCase("Add Conductor"));
		addConductorPage = new AddConductorPage(driver);
		boolean bTCFlag = addConductorPage.fill_AddConductor_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable( "Name",sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString());
			gridReader.fetchSatTraxID( "Name", sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString());
			Reporter.log(sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString()+" Conductor Data verified successfully",true);
			Xls_Reader reader = new Xls_Reader();
			reader.writeToExcel("AddConductor", "SatTraxID",SessionDetails.currentRowRef, SessionDetails.SatTraxID);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("SatTraX ID", SessionDetails.SatTraxID);
			dataMap.put("Name",sTestData[1].toString()+" "+sTestData[2].toString()+ " "+sTestData[3].toString());
			dataMap.put("Email", sTestData[4].toString());
			dataMap.put("Mobile-Primary", sTestData[5].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Add Conductor  and Verification Completed=====", true);
		}
		}
		catch(Exception e) {
			Reporter.log("=====TestCase Add Conductor Was not successful=====", true);
		}
	
	}
	@Test(priority=2,dataProvider="EditConductor")//,dependsOnMethods = {"a_AddConductor"})//(enabled = false)
	public void b_EditConductor(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Conductor Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addConductorPage = new AddConductorPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Conductor Details")) {
				Reporter.log("As we are already on Conductor Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectConductor();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Conductor Details"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(), "Edit");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Conductor"));
			addConductorPage.fill_EditConductor_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			gridReader.isDataAvailable("Name",sTestData[2].toString()+" "+ sTestData[3].toString()+" "+ sTestData[4].toString());
			Reporter.log(sTestData[0].toString()+" Conductor edited verified successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[1].toString());
			dataMap.put("SatTraX ID", sTestData[0].toString());
			dataMap.put("Name",sTestData[2].toString()+" "+ sTestData[3].toString()+" "+ sTestData[4].toString());
			dataMap.put("Email", sTestData[5].toString());
			dataMap.put("Mobile-Primary", sTestData[6].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Conductor  and Verification Completed=====", true);
			}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Conductor Was not successful=====", true);
			}
		}
	
	@Test(priority=3,dataProvider="DeactivateConductor")//,dependsOnMethods = {"b_EditConductor"}
	public void c_InactivateConductor(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Conductor Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Conductor Details")) {
				Reporter.log("As we are already on Conductor Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectConductor();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Conductor Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Deactivate");
			cpo.handleAlert();
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Conductor deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Inactive");
				Reporter.log("=====TestCase Inactivate Conductor  and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=4,dataProvider="ActivateConductor")//,dependsOnMethods = {"c_InactivateConductor"}
	public void d_ActivateConductor(Object[] sTestData){
		Reporter.log("=====TestCase Activate Conductor Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Conductor Details")) {
				Reporter.log("As we are already on Conductor Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectConductor();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Conductor Details"));
			}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {				
				gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Activate");
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Active");
				Reporter.log(sTestData[0].toString()+" Data re-activated successfuly<br>",true);
				Reporter.log("=====TestCase Activate Conductor  and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=5,dataProvider="DeleteConductor")//,dependsOnMethods = {"d_ActivateConductor"}
	public void e_DeleteConductor(Object[] sTestData){
		Reporter.log("=====TestCase Delete Conductor Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addConductorPage = new AddConductorPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Conductor Details")) {
				Reporter.log("As we are already on Conductor Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectConductor();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Conductor Details"));
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
				Reporter.log(sTestData[0].toString()+" Conductor deleted and verified successfully<br>",true);
				Reporter.log("=====TestCase Delete Conductor  and Verification Completed=====", true);
			}
			}
	}
	catch(Exception e){
			
	}	
	}

	@DataProvider(name = "AddConductorData")
    public	Iterator<Object[]> getAddConductorData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddConductor");
    	System.out.println(testdata.size());
         return testdata.iterator();
		}
    @DataProvider(name = "EditConductor")
    public	Iterator<Object[]> getEditConductorData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ConductorData","EditConductor");
         return testdata.iterator();
		}
    
    @DataProvider(name = "DeactivateConductor")
    public	Iterator<Object[]> getDeactivateConductorData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ConductorData","DeactivateConductor");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateConductor")
    public	Iterator<Object[]> getActivateConductorData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ConductorData","ActivateConductor");
         return testdata.iterator();
		}
    @DataProvider(name = "DeleteConductor")
    public	Iterator<Object[]> getDeleteConductorData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ConductorData","DeleteConductor");
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