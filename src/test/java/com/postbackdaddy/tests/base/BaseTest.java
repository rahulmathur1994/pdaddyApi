package com.postbackdaddy.tests.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.postbackdaddy.api.utils.TokenManager;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base Test Class
 * Contains setup and teardown for all tests
 * Handles ExtentReports configuration
 */
public class BaseTest {

    protected static ExtentReports extentReports;
    protected ExtentTest extentTest;

    @BeforeClass(alwaysRun = true)
    public void setUpExtentReports(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String reportPath = "target/reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("PostBackDaddy API Automation Report");
        sparkReporter.config().setDocumentTitle("API Test Report");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Environment", "Staging");
        extentReports.setSystemInfo("Rahul Mathur", "API Test Engineer");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUpTest(ITestContext context) {
        String testName = context.getName();
        extentTest = extentReports.createTest(testName);
        extentTest.info("Test: " + testName + " Started");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownTest(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test: " + result.getName() + " Passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.fail("Test: " + result.getName() + " Failed");
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip("Test: " + result.getName() + " Skipped");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDownExtentReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}

