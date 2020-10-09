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

import PageFactory.AddRolePage;
import PageFactory.CommonPageObjects;
import PageFactory.HomePage;
import PageFactory.LoginPage;
import config.TestBase;
import utility.GridReader;
import utility.Logger;
import utility.TestDataProvider;
/*
 * author: anjana.iv
 * Purpose : Role Test Cases
 */
public class TestRoleFeatures extends TestBase{
	LoginPage objLogin;
	HomePage	objHomePage;
	AddRolePage addRolePage;
	CommonPageObjects cpo;
	
	
	@Test(priority=1,dataProvider="AddRoleData")
	public void test_AddRole(Object[] sTestData) throws Exception {
		Reporter.log("=====TestCase Add Role Started=====", true);
		cpo = new CommonPageObjects(driver);
		objHomePage = new HomePage(driver);
		try{
			if(cpo.getPageTitle().equalsIgnoreCase("Manage Role")) {
				Reporter.log("As we are already on Driver Page, ignoring the navigational click",true);
			}else {
		objHomePage.selectRoles();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Manage Role"));
			}
		addRolePage = new AddRolePage(driver);
		addRolePage.clickAddRole();
		waitForLoad(driver);
		AssertJUnit.assertTrue(cpo.getPageTitle().equalsIgnoreCase("Add Role"));
		
		boolean bTCFlag = addRolePage.fill_AddRole_form(sTestData,"Add");
		//TC Verification
		if(bTCFlag) {
			GridReader gridReader = new GridReader(driver,"FlatGrid");
			gridReader.isDataAvailable("Role Name",sTestData[1].toString());
			Reporter.log(sTestData[1].toString()+" Roles Data added successfully",true);
			//Prepare a Hashmap for verifing data in table grid columns
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap.put("Entity Name", sTestData[0].toString());
			dataMap.put("Role Name", sTestData[1].toString());
			dataMap.put("Status", "Active");
			gridReader.verifyDatainGrid(dataMap);
			Reporter.log("=====TestCase Add Role and Verification Completed=====", true);
		}
		}
		catch(Exception e) {
			e.printStackTrace();

		}
	}
	
	@DataProvider(name = "AddRoleData")
    public	Iterator<Object[]> getAddRoleData() throws Exception{
    	ArrayList<Object[]> testdata = TestDataProvider.getExcelData("AddRoles");
    	System.out.println(testdata.size());
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