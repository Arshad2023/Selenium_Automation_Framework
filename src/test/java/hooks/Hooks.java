package hooks;

import base.BaseTests;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.*;
import org.testng.SkipException;
import utilities.ExtentManager;
import utilities.TestLogger;
import utils.ConfigReader;
//import utils.DriverManager;
import utils.ExcelUtils;

import java.util.Map;

public class Hooks {

    private static ExtentReports extent = ExtentManager.getInstance();
    // Thread-safe storage
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ThreadLocal<Map<String, String>> testData = new ThreadLocal<>();
    // Getter for Step Definitions
    public static Map<String, String> getTestData() {
        return testData.get();
    }
    @Before
    public void beforeScenario(Scenario scenario) {

        String scenarioName = scenario.getName();
        String sheetName = "LoginData";

        // ✅ Create Extent Test FIRST (for skip logging also)
        ExtentTest extentTest = extent.createTest(scenarioName);
        test.set(extentTest);
        TestLogger.setTest(extentTest);

        // ✅ Fetch Excel Data ONCE
        Map<String, String> data =
                ExcelUtils.getTestCaseById(sheetName, scenarioName);

        testData.set(data);   // 🔥 store globally

        // ✅ Skip BEFORE browser launch
        if (data == null || !data.getOrDefault("RunMode", "N").equalsIgnoreCase("Y")) {
            extentTest.skip("Skipping due to RunMode = N");
            throw new SkipException("Skipping due to RunMode = N");
        }

        // ✅ Initialize Driver
        BaseTests.initDriver();

        // ✅ Open URL centrally (no need in steps now)
        BaseTests.getDriver().get(ConfigReader.get("baseUrl"));
    }

    @After
    public void afterScenario(Scenario scenario) {

        ExtentTest extentTest = test.get();

        if (extentTest != null) {
            if (scenario.isFailed()) {
                extentTest.fail("Test Failed: " + scenario.getName());
            } else {
                extentTest.pass("Test Passed: " + scenario.getName());
            }
        }

        // ✅ Quit browser
        BaseTests.quitDriver();

        // ✅ Clean ThreadLocal (VERY IMPORTANT)
        test.remove();
        testData.remove();
        TestLogger.testThread.remove();
    }

    @AfterAll
    public static void tearDown() {
        extent.flush();   // ✅ Generate report
    }
}