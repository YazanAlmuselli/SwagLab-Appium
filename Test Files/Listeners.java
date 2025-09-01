package org.Appium_Mobile_Testing.SwagLab_App;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {

    ExtentReports extent;
    ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        // Report location
        String reportPath = System.getProperty("user.dir") + "/test-output/Extent-Report.html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setReportName("SwagLag App Appium Test Automation Results");
        spark.config().setDocumentTitle("Test Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Yazan");
        extent.setSystemInfo("App", "SwagLab");
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a test entry in the report for each @Test method
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test passed ✅");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test failed ❌: " + result.getThrowable());
        // 👉 Here you can also attach screenshot from Appium
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); // writes everything to HTML file
    }
}
