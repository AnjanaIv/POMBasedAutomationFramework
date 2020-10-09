cd G:\SATTRAX\SatTrax_Automation
java -cp G:\SATTRAX\SatTrax_Automation\lib*;G:\SATTRAX\SatTrax_Automation\bin org.testng.TestNG testng.xml
set projectLocation=G:\SATTRAX\SatTrax_Automation
cd %projectLocation%
set classpath=%projectLocation%\bin;%projectLocation%\lib\*
java org.testng.TestNG %projectLocation%\testng.xml
pause