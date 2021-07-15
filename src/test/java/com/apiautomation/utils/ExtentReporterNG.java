package com.apiautomation.utils;

import com.apiautomation.environmentconfiguration.EnvVariablesConfig;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.*;

public class ExtentReporterNG implements IReporter{
    private ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        // TODO Auto-generated method stub
        Calendar cal = Calendar.getInstance();
        System.out.println("starting report:" + cal.getTimeInMillis());
        this.extent = new ExtentReports("./target/surefire-reports/TestExecutionReport.html", Boolean.valueOf(true));
        addDetailstoTheExtentReport(suites);
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();
                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }

        }

        this.extent.flush();
        this.extent.close();

        Calendar cal1 = Calendar.getInstance();
        System.out.println("end of the report:" + cal1.getTimeInMillis());
    }





    private void buildTestNodes(IResultMap tests, LogStatus status)  {
        Calendar cal = Calendar.getInstance();
        if (tests.size() <= 0)
            return;
        for (ITestResult result : tests.getAllResults()) {
            String finalDesc = result.getMethod().getDescription();
            String testClassName = result.getMethod().getTestClass().getName();
            String testMethodName = result.getMethod().getMethodName();
            int arrlen = testClassName.split("\\.").length;
            ExtentTest test = this.extent.startTest(testMethodName);
            if (arrlen > 0)
                test.assignCategory(new String[]{testClassName.split("\\.")[(arrlen - 1)]});
            else {
                test.assignCategory(new String[]{testClassName});
            }
            test.setStartedTime(getTime(result.getStartMillis()));
            test.setEndedTime(getTime(result.getEndMillis()));

            if (result.getThrowable() != null) {
                if (result.getThrowable().toString().contains("java.lang.AssertionError")) {
                    test.log(LogStatus.INFO, getparams(result)+"");
                    test.log(LogStatus.INFO, finalDesc);
                    test.log(status, result.getThrowable());



                } else {
                    test.log(LogStatus.INFO, getparams(result) + "");
                    test.log(LogStatus.INFO, finalDesc);
                    test.log(LogStatus.SKIP, result.getThrowable());
                }
            }else {
                test.log(LogStatus.INFO, getparams(result)+"");
                test.log(status, "Test " + status.toString().toLowerCase() + "ed");
            }

            this.extent.endTest(test);
        }
    }

    private StringBuilder getparams(ITestResult result) {
        StringBuilder testdata = new StringBuilder();
        testdata.append("TestData:<br>");
        Object[] parameters = result.getParameters();
        for (int i = 0; (parameters != null) && (i < parameters.length); ++i) {
            Object parameter = parameters[i];
            testdata.append("param[" + i + "]: ").append(parameter).append("<br>");
        }
        return testdata;
    }




    public void addDetailstoTheExtentReport(List<ISuite> suites) {
        Map sysInfo = new HashMap();
        sysInfo.put("Environment", EnvVariablesConfig.environment);
        sysInfo.put("Testing Type",EnvVariablesConfig.testingType );
        this.extent.addSystemInfo(sysInfo);
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
