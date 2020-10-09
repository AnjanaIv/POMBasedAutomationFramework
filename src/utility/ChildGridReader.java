package utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import config.SessionDetails;
/*
 * author: anjana.iv
 * Purpose : Handling the child grids in the AUT
 */
public class ChildGridReader {
	WebDriver driver;
	JavascriptExecutor jse;
	static WebElement childGridTable = null;
	static Integer childGridRows = 0;
	static Integer childGridColumns = 0;
	static Integer childGridPages = 0;
	static Integer childCurrentPageNo = 0;
	static Integer childGtotalPageNos = 0;
	static Integer childGdataRowNo = 0;
	
	static String child_before_xpath = "";
	static String child_after_xpath = "]/td[1]";
	static String child_header_xpath = "";
	static String child_no_of_pages = "";
	static String child_nextPage_xpath = "";
	static String child_prevPage_xpath = "";
	static String childTableID = "";

	public ChildGridReader(WebDriver driver,String divTableID,Integer iParentRow) {
		try {
			this.driver = driver;
			jse = (JavascriptExecutor) driver;
			childTableID = ".//*[@id='"+divTableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr["+(iParentRow+1)+"]";
			childGridTable = driver.findElement(By.xpath(childTableID+"//div[@class='e-gridcontent']//table[@class='e-table']"));
			child_before_xpath = ".//*[@id='"+divTableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr["+(iParentRow+1)+"]//div[@class='e-gridcontent']//table[@class='e-table']/tbody/tr";
			List  data = null;
			data = driver.findElements(By.xpath(child_before_xpath));
			childGridRows = data.size();
	       // System.out.println("No of rows are : " + data.size());
	        data = driver.findElements(By.xpath(childTableID+"//div[contains(@class,'e-gridheader')]//table[@class='e-table']/thead/tr/th"));
	        //System.out.println("No of columns are : " + data.size());
	        childGridColumns = data.size();
	        child_header_xpath = childTableID+"//div[contains(@class,'e-gridheader')]//table[@class='e-table']/thead/tr/th";
	        child_no_of_pages = childTableID+"//div[@class='e-pager e-js e-pager']//span[@class='e-pagermsg']";
	        jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_no_of_pages)));	
			 String noOfPages = driver.findElement(By.xpath(child_no_of_pages)).getText();
			 noOfPages = noOfPages.split("of")[1].split("pages")[0].trim();
			 childGridPages = Integer.valueOf(noOfPages);
			 childCurrentPageNo = Integer.valueOf(driver.findElement(By.xpath(child_no_of_pages)).getText().split("of")[0].trim());
			 childGtotalPageNos = Integer.valueOf(noOfPages);
	        jse.executeScript("arguments[0].scrollIntoView(true);",childGridTable);
	        child_nextPage_xpath = child_no_of_pages+"//div[@title='Go to next page']";
	        child_prevPage_xpath = child_no_of_pages+"//div[@title='Go to previous page']";
	        
		} catch (Exception e) {
			System.out.println("Unable to find the table by xpath .//*[@id='"+divTableID+"']");
			e.printStackTrace();
		}
		}	

	public int getColIndex(String colName) throws Exception {
		List<WebElement>  headers = null;
		Actions actions = new Actions(driver);
		int index=0;
		try {
			
			jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_header_xpath)));
			headers = driver.findElements(By.xpath(child_header_xpath)); 
			/*if(headers.isEmpty()) {
				headers = driver.findElements(By.xpath("//div[contains(@class,'e-gridheader')]//thead/tr/th")); 
			}*/
			for (WebElement e  : headers){
				 actions.moveToElement(e);
				if(e.getText().contains(colName)|| e.getText().equalsIgnoreCase(colName)){
					index++;
					return index;
				}
				index++;				
			}
		}catch(Exception e) {
			System.out.println("Unable to access the table headers "+e.getStackTrace());
		}
		return index;
	}
	 public void clickAction(String ColName, String sColData,String sAction)  throws Exception {
		 boolean bFlag= false;
		 int colIndex = getColIndex(ColName);
		 System.out.println(colIndex);
		 while(!bFlag){
		 for(int i=1;i<=childGridRows;i++) {
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")));
			if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")).getText().equalsIgnoreCase(sColData)) {
				Reporter.log("Record found after matching the data in the grid "+sColData+"<br>");;
				System.out.println(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")).getText());
				if(sAction.equalsIgnoreCase("Select")) {
					colIndex = getColIndex("Select");
					jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]//input")));
					driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]//input")).click();
				}else if(sAction.equalsIgnoreCase("Expand")) {
					colIndex = 1;
					jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]//input")));
					driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")).click();
				}else if(sAction.equalsIgnoreCase("CheckUnmapStaff")){
					if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@title,'Un-Map Staff and Students')]")).isDisplayed()) {
						System.out.println("Found Un-Map Staff and Students icon");
					}else {
						System.out.println("Unable to find Un-Map Staff and Students icon");
					}
				}else if(sAction.equalsIgnoreCase("CheckUnmapDriver")){
					if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@title,'Un-Map Driver')]")).isDisplayed()) {
						System.out.println("Found Un-Map Driver icon");
					}else {
						System.out.println("Unable to find Un-Map Driver icon");
					}
				}else if(sAction.equalsIgnoreCase("CheckUnmapConductor")){
					if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@title,'Un-Map Conductor')]")).isDisplayed()) {
						System.out.println("Found Un-Map Conductor icon");
					}else {
						System.out.println("Unable to find Un-Map Conductor icon");
					}
				}else {
					colIndex = getColIndex("Manage");
					if(colIndex==-1) {
						colIndex=6;
						System.out.println("Column index=6");
					}
					jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")));
					try {
					if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@title,'"+sAction+"')]")).isDisplayed()) {
						driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@title,'"+sAction+"')]")).click();
					}else if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).isDisplayed()) {
						driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).click();
					}}
					catch(Exception e) {
						if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).isDisplayed()) {
							driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).click();
						}
					}
					Reporter.log("Clicked on "+sAction+"<br>");
					childCurrentPageNo = 1;
				}
				bFlag = true;
				break;
			}
		 }
			//Go to next page will be enabled when it has multiple pages
			 if(!bFlag) {
				 if(!navigateToPageNo()){
					 break;
				 }
				 }
		 }
		 
	 }
	 public void clickActionMatchRecords(String sMatchPair,String sAction)  throws Exception {
		 boolean bFlag= false;
		 //Matching the column data of columns 1 & 2 
		 for(int i=1;i<=childGridRows;i++) {
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+i+child_after_xpath)));
			if(driver.findElement(By.xpath(child_before_xpath+"["+i+child_after_xpath)).getText().equalsIgnoreCase(sMatchPair.split("!")[0]) && driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td[2]")).getText().equals(sMatchPair.split("!")[1])) {
				if(sAction == "Schedule")
					Reporter.log("Record found after matching the pair in the grid "+sMatchPair+"<br>");;
				System.out.println(driver.findElement(By.xpath(child_before_xpath+"["+i+child_after_xpath)).getText());
				if(sAction !="View") {
				int colIndex = getColIndex("Manage");
				System.out.println(colIndex);
				jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[@title='"+sAction+"']")));
				driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]/a[@title='"+sAction+"']")).click();
				bFlag = true;
				Reporter.log("Clicked on "+sAction+"<br>");
				}
				break;
			}
		 }
		 if(!bFlag) {
			 if(!navigateToPageNo()){
				 return;
			 }
		 }	
	 }
	 private boolean navigateToPageNo() {
		 boolean bFlag = false;
		 try {
			 if(childCurrentPageNo!=childGtotalPageNos) {
				 if (childCurrentPageNo<childGtotalPageNos) {
					 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_nextPage_xpath)));
					 driver.findElement(By.xpath(child_nextPage_xpath)).click();
					 Thread.sleep(500);
				 }else if (childCurrentPageNo>childGtotalPageNos) {
					 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_prevPage_xpath)));
					 driver.findElement(By.xpath(child_prevPage_xpath)).click();
					 Thread.sleep(500);
				 }
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(childTableID+"//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")));
				 childGridRows = driver.findElements(By.xpath(childTableID+"//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")).size();
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_no_of_pages)));
				 childCurrentPageNo = Integer.valueOf(driver.findElement(By.xpath(child_no_of_pages)).getText().split("of")[0].trim());
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_header_xpath)));
				 bFlag = true;
			 }else {
				 Reporter.log("No more pages to navigate <br>",true);
				 bFlag = false;
			 }
		 } catch(Exception e) {
			 Reporter.log("Issues in navigating the pages "+e.getMessage()+"<br>");
			 bFlag = false;
		 }
		 return bFlag;
	 }
	 public boolean isDataAvailable(String colName, String colData)  throws Exception {
		 boolean bFlag=false;
		 Actions actions = new Actions(driver);
		 JavascriptExecutor jse = (JavascriptExecutor) driver;
		 try {
			 int colIndex = getColIndex(colName);
		 while(!bFlag){
			 for(int i=1;i<=childGridRows;i++) {
				 //driver.findElement(By.xpath(child_before_xpath+i+"]/td["+colIndex+"]"))
				 	jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")));
				 	 actions.moveToElement(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")));
					String sTag=child_before_xpath+"["+i+"]/td["+colIndex+"]";
					if(driver.findElement(By.xpath(child_before_xpath+"["+i+"]/td["+colIndex+"]")).getText().equalsIgnoreCase(colData)) {
						Reporter.log(colData+" Data is available in the Grid Table<br>");
						childGdataRowNo = i;
						bFlag=true;
						break;
					}
					actions.sendKeys(Keys.DOWN).perform();
				 }
			//Go to next page will be enabled when it has multiple pages
			 if(!bFlag) {
				 if(!navigateToPageNo()){
					 bFlag=false;
					 break;
				 }
				 }
			 }

		 }catch(Exception e) {
			 Reporter.log(colData+" Data not found in the Grid Table<br>"+e.getMessage());
			 bFlag=false;
		 }
		 return bFlag;
	 }
	 public void verifyDatainGrid(HashMap sData){
		 boolean bFlag= false;
		 try {
		// for(int i=1;i<=gridRows;i++) {
			 Set<String> set = sData.keySet();
			/* for(int iMap=1;iMap<=sData.size();iMap++) {*/
				 Iterator itr = set.iterator();
				 while(itr.hasNext()) {
				 String sColName = itr.next().toString();
				 int iCol = this.getColIndex(sColName);
				 iCol = this.getColIndex(sColName);
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+"["+childGdataRowNo+"]/td["+iCol+"]")));
				 if(driver.findElement(By.xpath(child_before_xpath+"["+childGdataRowNo+"]/td["+iCol+"]")).getText().equals(sData.get(sColName))) {
					 Reporter.log(sData.get(sColName)+" Data is available in the Grid Table<br>");
					 Assert.assertEquals(sData.get(sColName), driver.findElement(By.xpath(child_before_xpath+"["+childGdataRowNo+"]/td["+iCol+"]")).getText());
					 bFlag= true;
					 Reporter.log("Verification of Column "+sColName+" is successful. Actual "+sData.get(sColName)+" Expected "+driver.findElement(By.xpath(child_before_xpath+"["+childGdataRowNo+"]/td["+iCol+"]")).getText()+"<br>");
				 }else {
					 Reporter.log("Verification of Column "+sColName+" is Failed. Actual "+sData.get(sColName)+" Expected "+driver.findElement(By.xpath(child_before_xpath+"["+childGdataRowNo+"]/td["+iCol+"]")).getText()+"<br>");
				 }
				}
				/*}*/
			/*//Go to next page will be enabled when it has multiple pages
			 if(!bFlag) {
				 if(!navigateToPageNo()){
					 break;
				 }
				 }*/
		 	}
		 catch(Exception e) {
			 Reporter.log("Verification of data inside the grid table failed "+e.getMessage()+"<br>");
		 }
	 }
	 public void verifyStatusinGrid(String colName, String colData, String sStatus)  throws Exception {
		 boolean bFlag= false;
		 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(".//*[@id='"+childTableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")));
		 childGridRows = driver.findElements(By.xpath(".//*[@id='"+childTableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")).size();
		 childCurrentPageNo=1;
		 int colIndex = getColIndex(colName);
		 while(!bFlag){
		for(int i=1;i<=childGridRows;i++) {
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(child_before_xpath+i+"]/td["+colIndex+"]")));
			if(driver.findElement(By.xpath(child_before_xpath+i+"]/td["+colIndex+"]")).getText().equals(colData)) {
				System.out.println(colData+" Data is available in the Grid Table");
				colIndex = getColIndex("Status");
				jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(child_before_xpath+i+"]/td["+colIndex+"]")));
				Assert.assertEquals(sStatus, driver.findElement(By.xpath(child_before_xpath+i+"]/td["+colIndex+"]")).getText());
				Reporter.log("Verified the status "+sStatus+" for the record "+colData);
				bFlag= true;
				break;
			}
		}
			//Go to next page will be enabled when it has multiple pages
			if(!bFlag) {
				if(!navigateToPageNo()){
					 break;
				 }
				 }
		}
	 }
	 
	 
}
