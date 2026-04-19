package utilities;

import com.aventstack.extentreports.ExtentTest;

public class TestLogger {
    public static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    public static void setTest(ExtentTest test) {
        testThread.set(test);
    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

    public static void logInfo(String message) {
        if (getTest() != null)
            getTest().info(message);
    }

    public static void logPass(String message) {
        if (getTest() != null)
            getTest().pass(message);
    }

    public static void logFail(String message) {
        if (getTest() != null)
            getTest().fail(message);
    }
}
