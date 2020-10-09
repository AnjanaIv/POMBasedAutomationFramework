package config;
/*
 * author: anjana.iv
 * Purpose : Configuration file for details of AUT
 */
public interface BaseConfig {
	String sAppURL = "https://www.sattrax.in:444/";
	String sStandardUserName = "demo";//"auto";//demo
	String sStandardUserEmail = "superadmin";
	String sStandardPassword = "Passw0rd";
	String sTestDataFilePath = "\\testdata\\SatTrax_AutomationTestData.xls";
	String sScreenshotsFilePath = "\\test-output\\";
	String sBrowser ="Chrome";
}
