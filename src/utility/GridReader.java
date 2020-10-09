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
 * Purpose : Handling the grids in the AUT
 */
public class GridReader {
	WebDriver driver;
	JavascriptExecutor jse;
	static WebElement gridTable = null;
	static Integer gridRows = 0;
	static Integer gridColumns = 0;
	static Integer gridPages = 1;
	static Integer currentPageNo = 1;
	static Integer totalPageNos = 1;
	public static Integer dataRowNo = 0;
	
	static String before_xpath = "";
	static String after_xpath = "]/td[1]";
	static String table_header_xpath = "";
	static String no_of_pages = "";
	static String nextPage_xpath = "";
	static String prevPage_xpath = "";
	static String TableID = "";

	public GridReader(WebDriver driver,String divTableID) {
		try {
			this.driver = driver;
			jse = (JavascriptExecutor) driver;
			TableID = divTableID;
			gridTable = driver.findElement(By.xpath(".//*[@id='"+divTableID+"']"));
			before_xpath = ".//*[@id='"+divTableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr[";
			List  data = null;
			data = driver.findElements(By.xpath(".//*[@id='"+divTableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr"));
			gridRows = data.size();
	       // System.out.println("No of rows are : " + data.size());
	        data = driver.findElements(By.xpath(".//*[@id='"+divTableID+"']//div[contains(@class,'e-gridheader')]//table[@class='e-table']/thead/tr/th"));
	        //System.out.println("No of columns are : " + data.size());
	        gridColumns = data.size();
	        table_header_xpath = ".//*[@id='"+divTableID+"']//div[contains(@class,'e-gridheader')]//table[@class='e-table']/thead/tr/th";
	        no_of_pages = ".//*[@id='"+divTableID+"']//span[@class='e-pagermsg']";
	        jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(no_of_pages)));	
			 String noOfPages = driver.findElement(By.xpath(no_of_pages)).getText();
			 if(noOfPages.split("of").length > 1) {
				 noOfPages = noOfPages.split("of")[1].split("pages")[0].trim();
				 gridPages = Integer.valueOf(noOfPages);
				 currentPageNo = Integer.valueOf(driver.findElement(By.xpath(no_of_pages)).getText().split("of")[0].trim());
				 totalPageNos = Integer.valueOf(noOfPages);
			 }
	        nextPage_xpath = ".//*[@id='"+divTableID+"']//div[@title='Go to next page']";
	        prevPage_xpath = ".//*[@id='"+divTableID+"']//div[@title='Go to previous page']";
	        
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
			
			jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(table_header_xpath)));
			headers = driver.findElements(By.xpath(table_header_xpath)); 
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
		 while(!bFlag){
		 for(int i=1;i<=gridRows;i++) {
			 //System.out.println(i);
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")));
			if(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")).getText().equalsIgnoreCase(sColData)) {
				Reporter.log("Record found after matching the data in the grid "+sColData+"<br>");;
				System.out.println(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")).getText());
				if(sAction.equalsIgnoreCase("Select")) {
					colIndex = getColIndex("Select");
					jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]//input")));
					driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]//input")).click();
				}else if(sAction.equalsIgnoreCase("Expand")) {
					colIndex = 1;
					jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")));
					driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")).click();
				}else {
					colIndex = getColIndex("Manage");
					jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")));
					try {
					if(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[contains(@title,'"+sAction+"')]")).isDisplayed()) {
						driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[contains(@title,'"+sAction+"')]")).click();
					}else if(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).isDisplayed()) {
						driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).click();
					}}
					catch(Exception e) {
						if(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).isDisplayed()) {
							driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[contains(@onclick,'"+sAction+"')]")).click();
						}
					}
					Reporter.log("Clicked on "+sAction+"<br>");
					currentPageNo = 1;
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
		 for(int i=1;i<=gridRows;i++) {
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+after_xpath)));
			if(driver.findElement(By.xpath(before_xpath+i+after_xpath)).getText().equalsIgnoreCase(sMatchPair.split("!")[0]) && driver.findElement(By.xpath(before_xpath+i+"]/td[2]")).getText().equals(sMatchPair.split("!")[1])) {
				if(sAction == "Schedule")
					Reporter.log("Record found after matching the pair in the grid "+sMatchPair+"<br>");;
				System.out.println(driver.findElement(By.xpath(before_xpath+i+after_xpath)).getText());
				if(sAction !="View") {
				int colIndex = getColIndex("Manage");
				System.out.println(colIndex);
				jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[@title='"+sAction+"']")));
				driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]/a[@title='"+sAction+"']")).click();
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
			 if(currentPageNo!=totalPageNos) {
				 if (currentPageNo<totalPageNos) {
					 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(nextPage_xpath)));
					 driver.findElement(By.xpath(nextPage_xpath)).click();
					 Thread.sleep(500);
				 }else if (currentPageNo>totalPageNos) {
					 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(prevPage_xpath)));
					 driver.findElement(By.xpath(prevPage_xpath)).click();
					 Thread.sleep(500);
				 }
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(".//*[@id='"+TableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")));
				 gridRows = driver.findElements(By.xpath(".//*[@id='"+TableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")).size();
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(no_of_pages)));
				 currentPageNo = Integer.valueOf(driver.findElement(By.xpath(no_of_pages)).getText().split("of")[0].trim());
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(table_header_xpath)));
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
			 System.out.print(colIndex);
		 while(!bFlag){
			 for(int i=1;i<=gridRows;i++) {
				 	jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")));
				 	 actions.moveToElement(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")));
					String sTag=before_xpath+i+"]/td["+colIndex+"]";
					if(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")).getText().equalsIgnoreCase(colData)) {
						Reporter.log(colData+" Data is available in the Grid Table<br>");
						dataRowNo = i;
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
				 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+iCol+"]")));
				 if(driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+iCol+"]")).getText().equals(sData.get(sColName))) {
					 Reporter.log(sData.get(sColName)+" Data is available in the Grid Table<br>");
					 Assert.assertEquals(sData.get(sColName), driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+iCol+"]")).getText());
					 bFlag= true;
					 Reporter.log("Verification of Column "+sColName+" is successful. Actual "+sData.get(sColName)+" Expected "+driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+iCol+"]")).getText()+"<br>");
				 }else {
					 Reporter.log("Verification of Column "+sColName+" is Failed. Actual "+sData.get(sColName)+" Expected "+driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+iCol+"]")).getText()+"<br>");
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
		 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(".//*[@id='"+TableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")));
		 gridRows = driver.findElements(By.xpath(".//*[@id='"+TableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")).size();
		 currentPageNo=1;
		 int colIndex = getColIndex(colName);
		 while(!bFlag){
		for(int i=1;i<=gridRows;i++) {
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")));
			if(driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")).getText().equals(colData)) {
				System.out.println(colData+" Data is available in the Grid Table");
				colIndex = getColIndex("Status");
				jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")));
				Assert.assertEquals(sStatus, driver.findElement(By.xpath(before_xpath+i+"]/td["+colIndex+"]")).getText());
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
	 public void viewSMSNotification(String notificationName, String notificationType, String details)  throws Exception {
		 boolean bFlag= false;
		 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(".//*[@id='"+TableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")));
		 gridRows = driver.findElements(By.xpath(".//*[@id='"+TableID+"']//*[contains(@class,'e-gridcontent')]//table[@class='e-table']/tbody/tr")).size();
		 currentPageNo=1;
		 int noticationColIndex =4;
		 int typeColIndex =0;
		 if(notificationType.equalsIgnoreCase("SMS")) {
			 typeColIndex =5;
		 }else {
			 typeColIndex =6;
		 }
		 while(!bFlag){
		for(int i=1;i<=gridRows;i++) {
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+i+"]/td["+noticationColIndex+"]")));
			if(driver.findElement(By.xpath(before_xpath+i+"]/td["+noticationColIndex+"]")).getText().equals(notificationName)) {
				System.out.println(notificationName+" Data is available in the Grid Table");
				jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(before_xpath+i+"]/td["+typeColIndex+"]")));
				driver.findElement(By.xpath(before_xpath+i+"]/td["+typeColIndex+"]/span")).click();
				//Reporter.log("Clicked on notificationType the status "+sStatus+" for the record "+colData);
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
	 public void fetchSatTraxID(String colName, String colData)  throws Exception {
		 boolean bFlag= false;
		 Actions actions = new Actions(driver);
		 try {
			 int colIndex = getColIndex(colName);
			 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+colIndex+"]")));
			 actions.moveToElement(driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+colIndex+"]")));
			if(driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+colIndex+"]")).getText().equalsIgnoreCase(colData)) {
						bFlag=true;
						colIndex = getColIndex("SatTraX ID");
						 jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+colIndex+"]")));
						SessionDetails.SatTraxID = driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td["+colIndex+"]")).getText();
						
					}
		 }catch(Exception e) {
			 Reporter.log(colData+" Data not found in the Grid Table<br>"+e.getMessage());
		 }
	 }
	 public void collapseSubGrid(String colName, String colData)  throws Exception {
		 jse.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td[1]")));
		 driver.findElement(By.xpath(before_xpath+dataRowNo+"]/td[1]")).click();
	 }
}
