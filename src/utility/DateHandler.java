package utility;

import java.time.Month;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
/*
 * author: anjana.iv
 * Purpose : Handling the dates from Calender
 */
public class DateHandler {
	static WebDriver driver;
	static String monthYearDivID = "//span[@class='e-datepicker-headertext']";
	public DateHandler(WebDriver driver) {
		this.driver = driver;
	}
	
	public static int getMonthNumber(String monthName) {
	    return Month.valueOf(monthName.toUpperCase()).getValue();
	}
	
	public static void selectDate(String sObjId,String sDateData){
		//Date Pattern expected November 02 2018 - Single Month selection in Calender
		String splitter[] = sDateData.split(" ");
		Actions	actions = new Actions(driver); 
		String month = splitter[1];
		int expMonth = getMonthNumber(month);
		boolean bPrev = false;
		try {	
			int year = Integer.valueOf(splitter[2]);
			String day = splitter[0];
			//traverse d month year
			String sMonthYearDisplayed ="";
			while(!driver.findElement(By.xpath("//div[@id='"+sObjId+"']/div[1]/span[2]")).getText().equalsIgnoreCase(month+" "+year)) {
				sMonthYearDisplayed = driver.findElement(By.xpath("//div[@id='"+sObjId+"']"+monthYearDivID)).getText();
				String monthDisplayed = sMonthYearDisplayed.split(" ")[0].trim();
				Integer actualMonth = getMonthNumber(monthDisplayed);
				Integer yearDisplayed = Integer.valueOf(sMonthYearDisplayed.split(" ")[1]);
				/*if(yearDisplayed != year) {
					driver.findElement(By.xpath("//div[@id='"+sObjId+"']"+monthYearDivID)).click();
					
				}*/
				if(yearDisplayed < year) {
					driver.findElement(By.xpath("//div[@id='"+sObjId+"']/div[1]/span[3]/a")).click();
				}else if(yearDisplayed > year){
					driver.findElement(By.xpath("//div[@id='"+sObjId+"']/div[1]/span[1]/a")).click();
				}else {
					if(actualMonth>expMonth) {
						driver.findElement(By.xpath("//div[@id='"+sObjId+"']/div[1]/span[1]/a")).click();
					}else if(actualMonth<expMonth) {
						driver.findElement(By.xpath("//div[@id='"+sObjId+"']/div[1]/span[3]/a")).click();
					}
				}
				Thread.sleep(1000);
			}
			if(driver.findElement(By.xpath("//div[@id='"+sObjId+"']/div[1]/span[2]")).getText().equalsIgnoreCase(month+" "+year)) {
				WebElement target = driver.findElement(By.xpath("//div[@id='"+sObjId+"']//tbody[@class='e-datepicker-days']//td[contains(@title,'"+month.substring(0, 3)+" "+day+" "+year+"')]"));
				actions.moveToElement(target);
				target.click();
				Reporter.log("Successfully selected date from the Calender");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
}


}
