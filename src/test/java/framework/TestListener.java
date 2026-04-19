package framework;

import org.testng.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utilities.ExtentManager;

public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    // Suite Start
    @Override
    public void onStart(ISuite suite) {
        log.info("===== Starting Test Suite: {} =====", suite.getName());
    }

    // Suite Finish
    @Override
    public void onFinish(ISuite suite) {
        log.info("===== Finished Test Suite: {} =====", suite.getName());
        extent.flush(); // Generate the final report here
    }

    // TestNG <test> start
    @Override
    public void onStart(ITestContext context) {
        log.info("---- Starting Test: {} ----", context.getName());
    }

    // TestNG <test> finish
    @Override
    public void onFinish(ITestContext context) {
        log.info("---- Finished Test: {} ----", context.getName());
    }

    // Test Method Start
    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ STARTED: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());

        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testThread.set(test);
    }

    // Test Method Success
    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✅ PASSED: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
        testThread.get().log(Status.PASS, "Test Passed");
    }

    // Test Method Failure
    @Override
    public void onTestFailure(ITestResult result) {
        log.error("❌ FAILED: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            log.error("Exception: ", result.getThrowable());
            testThread.get().log(Status.FAIL, result.getThrowable());
        }
    }

    // Test Method Skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠ SKIPPED: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
        testThread.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("Test partly failed but within success percentage: {}", result.getName());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }
}
