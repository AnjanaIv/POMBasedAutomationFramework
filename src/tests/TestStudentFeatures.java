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
import PageFactory.AddStudentPage;
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
 * author: arpita.hegde
 * reviewer : anjana.iv
 * Purpose : Student Test Cases
 */
public class TestStudentFeatures extends TestBase {

	LoginPage objLogin;
	HomePage	objHomePage;
	CommonPageObjects cpo;
	AddStudentPage addStudentPage;
	AddParentPage addParentPage;
	
	@Test(priority=1,dataProvider="AddParent")
	public void a_AddParent(Object[] sTestData) throws Exception{
		Reporter.log("=====TestCase Add Parent Started=====", true);
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
		if(!sTestData[20].toString().equalsIgnoreCase("")) {
			Reporter.log("Student details might have been already added in the application. Test Case might fail as the data already exists. SatTraxID :"+sTestData[21].toString(),true);
		}
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Student"));
		addStudentPage = new AddStudentPage(driver);
		addStudentPage.fill_AddStudent_form(sTestData,"Add");
		waitForLoad(driver);
		Thread.sleep(5000);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Parent"));
		addParentPage =new AddParentPage(driver);
		ArrayList<Object[]> parent = TestDataProvider.getDependentData("StudentData","addparent",sTestData[7].toString());
		Object[] parentData = null;
		for (Object[] objArray : parent){
			parentData = (String[]) objArray[0];
		}
		boolean bTCFlag =addParentPage.fill_AddParent_form(parentData);
		if(bTCFlag){
			GridReader gridReader = new GridReader( driver,"FlatGrid");
			String email = "";String phone="";
			if(parentData[7].toString().equalsIgnoreCase("Father")){
				email = parentData[10].toString();
				phone = parentData[11].toString();
			}else{
				email = parentData[8].toString();
				phone = parentData[9].toString();
			}
			gridReader.isDataAvailable("Email", email);
			gridReader.fetchSatTraxID( "Email", email);
			//write to xls with the SatTrax IDs for all students for future references
			Xls_Reader reader = new Xls_Reader();
			reader.writeToExcel("StudentData", "SatTraxID",SessionDetails.currentChildRowRef, SessionDetails.SatTraxID);
			Reporter.log(sTestData[1].toString()+" Student and Parent Data added successfully",true);
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", parentData[1].toString());
			dataMap.put("SatTrax ID", SessionDetails.SatTraxID);
			dataMap.put("Mother",parentData[1].toString()+" "+parentData[3].toString());
			dataMap.put("Father",parentData[4].toString()+" "+parentData[6].toString());
			dataMap.put("Email", email);
			dataMap.put("Mobile-Primary",phone);
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Parent and Verification Completed=====", true);
			/*objHomePage.selectStudent();
			waitForLoad(driver);
			gridReader = new GridReader( driver,"FlatGrid");
			gridReader.isDataAvailable("Email", sTestData);*/
		}
		}
		catch(Exception e){

	}
	}		
	
	@Test(priority=2,dataProvider="AddStudent")//,dependsOnMethods= {"a_AddParent"})
	public void b_AddStudent(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Student Started=====", true);
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
		if(!sTestData[20].toString().equalsIgnoreCase("")) {
			Reporter.log("Student details might have been already added in the application. Test Case might fail as the data already exists. SatTraxID :"+sTestData[20].toString(),true);
		}else {
		cpo.clickAdd();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Student"));
		addStudentPage = new AddStudentPage(driver);
		boolean bTCFlag = addStudentPage.fill_AddStudent_form(sTestData,"Add");
		/*if(driver.findElement(By.className("help-block")).isDisplayed()) {
			bTCFlag = false;
			Reporter.log("FAIL : "+sTestData[1].toString()+" Student Data not added successfully",true);
		}*/
		//TC Verification
		if(bTCFlag) {
			waitForLoad(driver);
			Thread.sleep(1000);
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Registration Number", SessionDetails.studentRegNumber);
			gridReader.fetchSatTraxID( "Registration Number", SessionDetails.studentRegNumber);
			//write to xls with the SatTrax IDs for all students for future references
			Xls_Reader reader = new Xls_Reader();
			//int rowNum = reader.getCellRowNum("AddStudent","FirstName",sTestData[1].toString());
			reader.writeToExcel("AddStudent", "SatTraxID", 0, SessionDetails.SatTraxID);
			Reporter.log(sTestData[1].toString()+" Student Data added successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("SatTrax ID", SessionDetails.SatTraxID);
			if(!sTestData[2].toString().equalsIgnoreCase("")) {
			dataMap.put("Name",sTestData[1].toString()+" "+ sTestData[2].toString()+" "+ sTestData[3].toString());
			}else {
				dataMap.put("Name",sTestData[1].toString()+ " "+ sTestData[3].toString());
			}
			dataMap.put("Registration Number", SessionDetails.studentRegNumber);
			dataMap.put("Class", sTestData[5].toString());
			dataMap.put("Section", sTestData[6].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Add Student and Verification Completed=====", true);
		}
		}
		}catch(Exception e){
			
		}
	}
	@Test(priority=3,dataProvider="EditStudent")//,dependsOnMethods = {"b_AddStudent"})
	public void c_EditStudent(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Edit Student Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		addStudentPage = new AddStudentPage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Student Details")) {
				Reporter.log("As we are already on Student Page, ignoring the navigational click",true);
			}else {
				objHomePage.selectStudent();
				waitForLoad(driver);
				AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Student Details"));
			}
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(), "Edit Student");
			waitForLoad(driver);
			AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Modify Student"));
			addStudentPage.fill_EditStudent_form(sTestData,"Edit");
			Reporter.log(sTestData[0].toString()+" Data edited successfully<br>",true);
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
			Reporter.log(sTestData[0].toString()+" Student Data edited successfully<br>",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			//dataMap.put("Entity", sTestData[0].toString());
			dataMap.put("SatTraX ID", sTestData[0].toString());
			dataMap.put("Name",sTestData[2].toString()+" "+ sTestData[3].toString()+" "+ sTestData[4].toString());
			dataMap.put("Registration Number", SessionDetails.studentRegNumber);
			dataMap.put("Class", sTestData[6].toString());
			//dataMap.put("Section", sTestData[7].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid( dataMap);
			Reporter.log("=====TestCase Edit Student and Verification Completed=====", true);
			}
		}
			catch(Exception e) {
				Reporter.log("=====TestCase Edit Student Was not successful=====", true);
			}
		}
	
	@Test(priority=4,dataProvider="DeactivateStudent")//dependsOnMethods = {"b_AddStudent"},
	public void d_InactivateStudent(Object[] sTestData)throws Exception{
		Reporter.log("=====TestCase Inactivate Student Started=====", true);
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
			gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Deactivate");
			cpo.handleAlert();
			if(gridReader.isDataAvailable("SatTraX ID",sTestData[0].toString())) {
				Reporter.log(sTestData[0].toString()+" Student deactivated successfuly<br>",true);
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "InActive");
				Reporter.log("=====TestCase Inactivate Student and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test(priority=5,dataProvider="ActivateStudent")//,dependsOnMethods = {"d_InactivateStudent"}
	public void e_ActivateStudent(Object[] sTestData){
		Reporter.log("=====TestCase Activate Student Started=====", true);
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
				gridReader.clickAction("SatTraX ID",sTestData[0].toString(),"Activate");
				gridReader.verifyStatusinGrid("SatTraX ID",sTestData[0].toString(), "Active");
				Reporter.log(sTestData[0].toString()+" Data re-activated successfuly<br>",true);
				Reporter.log("=====TestCase Activate Student and Verification Completed=====", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@DataProvider(name = "AddStudent")
    public	Iterator<Object[]> getAddStudentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddStudent");
         return testdata.iterator();
		}
    
    @DataProvider(name = "EditStudent")
    public	Iterator<Object[]> getEditStudentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StudentData","EditStudent");
         return testdata.iterator();
		}
    @DataProvider(name = "AddParent")
    public	Iterator<Object[]> getAddStudentParentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StudentData","addstudent");
         return testdata.iterator();
		}
    @DataProvider(name = "DeactivateStudent")
    public	Iterator<Object[]> getDeactivateStudentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StudentData","DeactivateStudent");
         return testdata.iterator();
		}
    @DataProvider(name = "ActivateStudent")
    public	Iterator<Object[]> getActivateStudentData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getMultipleData("StudentData","ActivateStudent");
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

	
	
