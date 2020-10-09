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

import PageFactory.AddParentPage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.TestBase;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Parent test cases
 */


public class TestParentFeatures extends TestBase{
	LoginPage objLogin;
	HomePage	objHomePage;
	CommonPageObjects cpo;
	AddParentPage addParentPage;
	
@Test(priority=1,dataProvider="EditParentData")
public void a_EditParent(Object[] sTestData){
	Reporter.log("=====TestCase Edit Parent Started=====", true);
	cpo = new CommonPageObjects(driver);
	objHomePage = new HomePage(driver);
	try{
		if(cpo.getPageTitle().equalsIgnoreCase("Parent Details")) {
			Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
		}else {
			objHomePage.selectParent();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Parent Details"));
		}
		GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("SatTrax ID", sTestData[1].toString(),"Edit");
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Parent"));
		addParentPage = new AddParentPage(driver);
		boolean bTCFlag =addParentPage.fill_EditParent_form(sTestData);
		/*if(driver.findElement(By.className("help-block")).isDisplayed()) {
			bTCFlag = false;
			Reporter.log("FAIL : "+sTestData[1].toString()+" Parent Data edit failed",true);
		}*/
		//Reporter.log(sTestData[1].toString()+" Data edited successfully<br>",true);
		
		if(gridReader.isDataAvailable("SatTraX ID",sTestData[1].toString())) {
		Reporter.log(sTestData[1].toString()+" Parent Data edited successfully<br>",true);
		String email = "";
		if(sTestData[7].toString().equalsIgnoreCase("Father")){
			email = sTestData[10].toString();
		}else{
			email = sTestData[8].toString();
		}
		//Prepare a Hashmap for verifing data in table grid columns
		LinkedHashMap dataMap = new LinkedHashMap();
		dataMap.put("Entity", sTestData[0].toString());
		dataMap.put("SatTraX ID", sTestData[1].toString());
		dataMap.put("Mother", "Kamla N Takia");
		dataMap.put("Father","Vivek N Takia");
		dataMap.put("email", email);
		dataMap.put("Status", "Active");
		gridReader.verifyDatainGrid(dataMap);
		Reporter.log("=====TestCase Edit Parent and Verification Completed=====", true);
		}
		}
		catch(Exception e) {
			e.printStackTrace();

		}
}
@Test(priority=2,dataProvider="DeactivateParentInUse")//,dependsOnMethods= {"a_EditParent"})
public void b_DeactivateParentInUse(Object[] sTestData){
	Reporter.log("=====TestCase Deactivate Parent in Use Started=====", true);
	cpo = new CommonPageObjects(driver);
	objHomePage = new HomePage(driver);
	try{
		if(cpo.getPageTitle().equalsIgnoreCase("Parent Details")) {
			Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
		}else {
			objHomePage.selectParent();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Parent Details"));
		}
	GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Deactivate");
		cpo.handleAlert();
		if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
			gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Active");
			Reporter.log(sTestData[0].toString()+" Data could not be deactivated as it has references<br>",true);
			Reporter.log("=====TestCase Deactivate Parent in Use and Verification Completed=====", true);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
@Test(priority=3,dataProvider="DeleteStudent")//,dependsOnMethods = {"e_ActivateStudent"}
public void c_DeleteStudent(Object[] sTestData){
	Reporter.log("=====TestCase Delete Student Started=====", true);
	cpo = new CommonPageObjects(driver);
	objHomePage = new HomePage(driver);
	try{
		if(cpo.getPageTitle().equalsIgnoreCase("Student Details")) {
			Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
		}else {
			objHomePage.selectStudent();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Student Details"));
		}
	GridReader gridReader = new GridReader(driver,"FlatGrid");
		if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Delete");
			cpo.handleAlert();
		if(!gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())){
			Reporter.log(sTestData[0].toString()+" Student deleted successfully<br>",true);
			Reporter.log("=====TestCase Delete Student and Verification Completed=====", true);
		}
		}
}
catch(Exception e){
		
}	
}

@Test(priority=4,dataProvider="DeactivateParent")//,dependsOnMethods= {"a_EditParent"})
public void d_DeactivateParent(Object[] sTestData){
	Reporter.log("=====TestCase Deactivate Parent Started=====", true);
	cpo = new CommonPageObjects(driver);
	objHomePage = new HomePage(driver);
	try{
		if(cpo.getPageTitle().equalsIgnoreCase("Parent Details")) {
			Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
		}else {
			objHomePage.selectParent();
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Parent Details"));
		}
	GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Deactivate");
		cpo.handleAlert();
		if(!gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
			
			Reporter.log(sTestData[0].toString()+" Data deactivated successfuly after deleting references<br>",true);
			gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "InActive");
			Reporter.log("=====TestCase Deactivate Parent and Verification Completed=====", true);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

@Test(priority=5,dataProvider="ActivateParent")//,dependsOnMethods= {"b_DeactivateParent"})
public void e_ActivateParent(Object[] sTestData){
	Reporter.log("=====TestCase Activate Parent Started=====", true);
	cpo = new CommonPageObjects(driver);
	objHomePage = new HomePage(driver);
	try{
		if(cpo.getPageTitle().equalsIgnoreCase("Parent Details")) {
			Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
		}else {
	objHomePage.selectParent();
	waitForLoad(driver);
	addParentPage = new AddParentPage(driver);
	AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Parent Details"));
		}
	GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Activate");
		if(!gridReader.isDataAvailable("SatTraX ID", sTestData[0].toString())) {
			Reporter.log(sTestData[0].toString()+" Data activated successfully<br>",true);
			gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Active");
			Reporter.log("=====TestCase Activate Parent and Verification Completed=====", true);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}


@Test(priority=6,dataProvider="DeleteParent")//,dependsOnMethods= {"c_ActivateParent"})
public void f_DeleteParent(Object[] sTestData){
	Reporter.log("=====TestCase Delete Parent Started=====", true);
	cpo = new CommonPageObjects(driver);
	objHomePage = new HomePage(driver);
	try{
		if(cpo.getPageTitle().equalsIgnoreCase("Parent Details")) {
			Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
		}else {
	objHomePage.selectParent();
	waitForLoad(driver);
	AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Parent Details"));
		}
	GridReader gridReader = new GridReader(driver,"FlatGrid");
		gridReader.clickAction("SatTraX ID", sTestData[0].toString(),"Delete");
		cpo.handleAlert();
		if(!gridReader.isDataAvailable("SatTraX ID", sTestData[0].toString())) {
			
			Reporter.log(sTestData[0].toString()+" Data deleted and verified successfully<br>",true);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}

@DataProvider(name = "EditParentData")
public	Iterator<Object[]> getEditParentData() throws Exception{
	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ParentData","EditParent");
     return testdata.iterator();
	}
@DataProvider(name = "DeactivateParentInUse")
public	Iterator<Object[]> getDeactivateParent() throws Exception{
	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ParentData","DeactivateParentinUse");
     return testdata.iterator();
	}
@DataProvider(name = "DeactivateParent")
public	Iterator<Object[]> getDeactivateParentData() throws Exception{
	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ParentData","DeactivateParent");
     return testdata.iterator();
	}
@DataProvider(name = "ActivateParent")
public	Iterator<Object[]> getActivateParentData() throws Exception{
	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ParentData","ActivateParent");
     return testdata.iterator();
	}
@DataProvider(name = "DeleteParent")
public	Iterator<Object[]> getDeleteParentData() throws Exception{
	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ParentData","DeleteParent");
     return testdata.iterator();
	}
@DataProvider(name = "DeleteStudent")
public	Iterator<Object[]> getDeleteStudentData() throws Exception{
	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("ParentData","DeleteStudent");
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