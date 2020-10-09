package tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageFactory.AddEntityPage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.TestBase;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Entity Test Cases
 */
public class TestEntityFeatures extends TestBase{

	TestBase testBase;
	LoginPage objLogin;
	HomePage	objHomePage;
	CommonPageObjects cpo;
	AddEntityPage addEntityPage;
	GridReader gridReader;
	String sEntityForCrud = "AutoEntity3";
	JavascriptExecutor jse;

	@Test(priority=1,dataProvider="AddEntityData")
	public void a_AddEntity(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase AddEntity Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Entity")) {
				Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectEntity();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Entity"));
			}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Create Entity"));
		addEntityPage = new AddEntityPage(driver);
		boolean bTCFlag = addEntityPage.fill_AddEntity_form(sTestData,"Add");
		/*if(driver.findElement(By.className("help-block")).isDisplayed()) {
			bTCFlag = false;
			Reporter.log("FAIL : "+sTestData[0].toString()+" Entity Data not added successfully",true);
		}*/
		//TC Verification
		if(bTCFlag) {
			/*Commenting toast verification for TC result
			 try {
				if(driver.findElement(By.className("toast-title")).isDisplayed()) {
			String toastTitle = driver.findElement(By.className("toast-title")).getText();
			String toastMsg = driver.findElement(By.className("toast-message")).getText();
			System.out.println("Title of Toast Message "+ toastTitle);
			System.out.println("Message of Toast Message "+ toastMsg);
			Assert.assertEquals(toastTitle, "Success");
			Assert.assertEquals(toastMsg, "Record added successfully");
			Reporter.log("Toast Message "+toastMsg+" verified successfully");
			Thread.sleep(5000);
				}
			}catch(Exception e) {
				System.out.println("Could not verify the toast message");
				//e.printStackTrace();
			}*/
			gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Name",sTestData[0].toString());
			Reporter.log(sTestData[0].toString()+" Entity Data added and verified successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			//Pass the column Name in the grid and column value
			dataMap.put("Name", sTestData[0].toString());
			dataMap.put("Parent Entity", sTestData[1].toString());
			dataMap.put("Landline-Primary",sTestData[3].toString());
			dataMap.put("Mobile-Primary", sTestData[6].toString());
			dataMap.put("E-mail", sTestData[5].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase AddEntity  and Verification Completed=====", true);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	@Test(priority=2,dataProvider="EditEntityData")//dependsOnMethods= {"a_AddEntity"},
	public void b_EditEntity(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Edit Entity Started=====", true);
		CommonPageObjects cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Entity")) {
				Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectEntity();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Entity"));
			}
			gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Name",sTestData[0].toString(),"Edit");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Edit Entity"));
			addEntityPage.fill_AddEntity_form(sTestData,"Edit");
			//System.out.println(sTestData[0].toString()+" Data edited successfully");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*Commenting toast verification for TC result
		try {
			String toastTitle = driver.findElement(By.className("toast-title")).getText();
			String toastMsg = driver.findElement(By.className("toast-message")).getText();
			System.out.println("Title of Toast Message "+ toastTitle);
			System.out.println("Message of Toast Message "+ toastMsg);
			Assert.assertEquals(toastTitle, "Success");
			Assert.assertEquals(toastMsg, "Record added successfully");
			Reporter.log("Toast Message "+toastMsg+" verified successfully");
			Thread.sleep(5000);
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		//Validation of edited entity
		try {
		gridReader.isDataAvailable("Name", sTestData[0].toString());
		Reporter.log(sTestData[0].toString()+" Entity Data verified successfully<br>",true);
		//Prepare a Hashmap for verifing data in table grid columns
		LinkedHashMap dataMap = new LinkedHashMap();
		dataMap.put("Name", sTestData[0].toString());
		dataMap.put("ParentEntity", sTestData[1].toString());
		dataMap.put("landlinePri",sTestData[3].toString());
		dataMap.put("mobilePri", sTestData[6].toString());
		dataMap.put("email", sTestData[5].toString());
		dataMap.put("Status", "Active");
		gridReader.verifyDatainGrid(dataMap);
		Reporter.log("=====TestCase Edit Entity and Verification Completed=====", true);
		}
		catch(Exception e) {
			
		}
	}
	@Test(priority=3)//dependsOnMethods= {"b_EditEntity"},dataProvider="getDelEntityData")
	public void c_Inactivate_Entity(){
		Reporter.log("=====TestCase Inactivate Entity Started=====", true);
		CommonPageObjects cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Entity")) {
				Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectEntity();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Entity"));
			}
			gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Name",sEntityForCrud,"De Activate");
			if(!gridReader.isDataAvailable("Name", sEntityForCrud)) {
				Reporter.log(sEntityForCrud+" Data deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("Name",sEntityForCrud, "InActive");
				Reporter.log("=====TestCase Inactivate Entity and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test(priority=4)//,dependsOnMethods= {"c_Inactivate_Entity"})
	public void d_Activate_Entity(){
		Reporter.log("=====TestCase Activate Entity Started=====", true);
		CommonPageObjects cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Entity")) {
				Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectEntity();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Entity"));
			}
			gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Name", sEntityForCrud,"Activate");
			if(!gridReader.isDataAvailable("Name", sEntityForCrud)) {
				Reporter.log(sEntityForCrud+" Data activated successfully<br>",true);
				gridReader.verifyStatusinGrid("Name",sEntityForCrud, "Active");
				Reporter.log("=====TestCase Activate Entity and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test(priority=5)//,dependsOnMethods= {"d_Activate_Entity"})
	public void e_Delete_Entity(){
		Reporter.log("=====TestCase Delete Entity Started=====", true);
		CommonPageObjects cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Entity")) {
				Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectEntity();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Entity"));
			}
			gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("Name", sEntityForCrud,"delete");
			if (driver.findElements(By.cssSelector("#toaster")).size() > 0) {// && toaster.isDisplayed()) {
				try {
					Actions builder = new Actions(driver);
				    builder.moveToElement(driver.findElement(By.className("toast-title"))).moveByOffset(2,2).click().build().perform();
					String toastTitle = driver.findElement(By.className("toast-title")).getText();
					String toastMsg = driver.findElement(By.className("toast-message")).getText();
					System.out.println("Title of Toast Message "+ toastTitle);
					System.out.println("Message of Toast Message "+ toastMsg);
					Assert.assertEquals(toastTitle, "Success");
					Assert.assertEquals(toastMsg, "Record deleted successfully");
					Reporter.log("Toast Message "+toastMsg+" verified successfully<br>");
					Thread.sleep(5000);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!gridReader.isDataAvailable("Name", sEntityForCrud)) {
				//System.out.println("AutoTestEntity1"+" Data deleted successfully");
				Reporter.log(sEntityForCrud+" Data deleted and verified successfully<br>",true);
				Reporter.log("=====TestCase Delete Entity and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@DataProvider(name = "AddEntityData")
    public	Iterator<Object[]> getAddEntityData() throws Exception{
    	Reporter.log("Fetching the data from the excel sheet<br>");
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddEntity");
    	Reporter.log("Fetching the data from the excel sheet is complete. Total Rows of data are "+testdata.size()+"<br>");
         return testdata.iterator();
		}
    
    @DataProvider(name = "EditEntityData")
    public	Iterator<Object[]> getEditEntityData() throws Exception{
    	Reporter.log("Fetching the data from the excel sheet<br>");
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("EditEntity");
    	Reporter.log("Fetching the data from the excel sheet is complete. Total Rows of data are "+testdata.size()+"<br>");
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