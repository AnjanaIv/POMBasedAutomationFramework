package utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import config.BaseConfig;
import config.SessionDetails;
/*
 * author: anjana.iv
 * Purpose : Handling logging to report files
 */
public class Logger implements BaseConfig{
	static WebDriver driver;
	
	public Logger(WebDriver driver) {
		this.driver = driver;
	}

	public static void takeScreenshot(String fileName) throws IOException{
		// Take screenshot and store as a file format
		/*if(driver.findElement(By.className("help-block")).isDisplayed()) {
			Reporter.log("FAIL : "+fileName+" TC got failed<br>");
			Reporter.log(driver.findElement(By.className("help-block")).getText());
		}*/
		String WORKING_DIR = System. getProperty("user.dir");
		File file = new File(WORKING_DIR+BaseConfig.sScreenshotsFilePath+"/screenshots/");
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// now copy the screenshot to desired location using copyFile //method
		FileUtils.copyFile(src, 
				new File(WORKING_DIR+BaseConfig.sScreenshotsFilePath+"/screenshots/" + fileName+"_"+SessionDetails.TestDataIteration +".png"));
		Reporter.log("Screenshot is captured and placed at "+WORKING_DIR+BaseConfig.sScreenshotsFilePath+"/screenshots/" + fileName +"_"+SessionDetails.TestDataIteration +".png<br>");
		SessionDetails.TestDataIteration++;
	}
}
