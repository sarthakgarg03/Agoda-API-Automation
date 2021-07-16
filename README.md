
---------------


This Test Automation Framework is created using Java + apache httpclient + TestNG + Maven. Which can be used across different web based applications.
In this approach, the endeavor is to build a lot of applications independent reusable keyword components so that they can directly used for another web application without spending any extra effort. 


Prerequisites:
---------------
*	Java jdk-1.8 or higher
*	Apache Maven 3 or higher
*	Please refer for any help in Maven. 
* 	http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
* 	http://www.tutorialspoint.com/maven/maven_environment_setup.htm

Environment/Testing Type:
---------------
* 	There are 3 different environment configuration set up [dev, test and prod]
*	There are 2 different Testing Type [Smoke, Regression]

Execution:
---------------
*	download the git Repo
*	Open command prompt and go to agoda directory.
*	To run TestNG.xml File[SuiteXmls/Regression/test/SearchQuoteRegression.xml] on environment[test] with Testing Type[Regression]  use command
*	....\.\agoda> mvn clean test -DTestingType=Regression -DEnvironment=test  -DxmlFileName=SuiteXmls/Regression/test/SearchQuoteRegression.xml

Logging:
---------------

*	slf4j configured to capture the test execution logs
*	Configuration file is located at src\test\resources\log4j.properties
*	Execution log is captured in the Logs/logs.log


Source code Folder:
---------------
*	..\agoda\src\test\java\com\apiautomation\api :- contains java file corresponding to different api
*   ..\agoda\src\test\java\com\apiautomation\environmentconfiguration :-  contains java file which intialize environment variables
*   ..\agoda\src\test\java\com\apiautomation\testcases :- contains testcases java file
*   ..\agoda\src\test\java\com\apiautomation\utils :- contains different utility like api utils , json parsor utlis , xls reader utils etc.


Test Data:
---------------
*	Test Data is passed to the testcase by xlsx file.
*   Data File Path :-  TestData/SearchQuotes.xlsx
*   Data File have following columns (TestCase_type| Tags(optional)| Author(optional)| Page(optional)| ExpectedStatusCode)

Reporting:
---------------
* Extent Report is implemented by class src\test\java\com\automation\Utils\ExtentReporterNG.java and store extent report at ./target/surefire-reports/TestExecutionReport.html Path 
*  The framework also  produce index.html report. It resides in the same 'target\surefire-reports' folder. This reports gives the link to all the different component of the TestNG reports like Groups & Reporter Output. On clicking these will display detailed descriptions of execution.
*  You can also find emailable-report.html from target\surefire-reports to email the test reports. As this is a html report you can open it with browser.


JenkinsScreenshot:
- - - -- - - - -- - -
* this folder contains docx file which contains screenshot of jenkins dashboard, job, execution, Publish HTML Report

Agoda assignment1
- - - - - - - - - - -  -
* this folder contain xlsx file which contains test plan which is created for assignment1.
