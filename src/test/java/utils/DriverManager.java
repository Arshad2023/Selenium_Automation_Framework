//package utils;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import io.github.bonigarcia.wdm.WebDriverManager;
//
//public class DriverManager {
//
//    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
//
//    // ✅ ONLY return driver (no creation here)
//    public static WebDriver getDriver() {
//        return driverThread.get();
//    }
//
//    // ✅ NEW METHOD (🔥 controlled initialization)
//    public static void initDriver() {
//
//        if (driverThread.get() != null) {
//            return; // prevent duplicate browser
//        }
//
//        String browser = ConfigReader.get("browser").toLowerCase();
//        WebDriver driver;
//
//        switch (browser) {
//            case "chrome":
//                WebDriverManager.chromedriver().setup();
//                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addArguments("--remote-allow-origins=*");
//                driver = new ChromeDriver(chromeOptions);
//                break;
//
//            case "firefox":
//                WebDriverManager.firefoxdriver().setup();
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                driver = new FirefoxDriver(firefoxOptions);
//                break;
//
//            default:
//                throw new RuntimeException("❌ Browser not supported: " + browser);
//        }
//
//        driver.manage().window().maximize();
//        driver.manage().deleteAllCookies();
//
//        driverThread.set(driver);   // 🔥 IMPORTANT
//    }
//
//    // ✅ Quit driver
//    public static void quitDriver() {
//        if (driverThread.get() != null) {
//            driverThread.get().quit();
//            driverThread.remove();   // 🔥 VERY IMPORTANT
//        }
//    }
//}
//
////package utils;
////
////import org.openqa.selenium.WebDriver;
////import org.openqa.selenium.chrome.ChromeDriver;
////import org.openqa.selenium.chrome.ChromeOptions;
////import org.openqa.selenium.firefox.FirefoxDriver;
////import org.openqa.selenium.firefox.FirefoxOptions;
////import io.github.bonigarcia.wdm.WebDriverManager;
////
////public class DriverManager {
////
////    // Each thread gets its own driver instance
////    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
////
////    public static WebDriver getDriver() {
////        if (driverThread.get() == null) {
////            String browser = ConfigReader.get("browser").toLowerCase();
////
////            switch (browser) {
////                case "chrome":
////                    WebDriverManager.chromedriver().setup();
////                    ChromeOptions chromeOptions = new ChromeOptions();
////                    chromeOptions.addArguments("--remote-allow-origins=*");
////                    driverThread.set(new ChromeDriver(chromeOptions));
////                    break;
////
////                case "firefox":
////                    WebDriverManager.firefoxdriver().setup();
////                    FirefoxOptions firefoxOptions = new FirefoxOptions();
////                    driverThread.set(new FirefoxDriver(firefoxOptions));
////                    break;
////
////                default:
////                    throw new RuntimeException("❌ Browser not supported: " + browser);
////            }
////
////            driverThread.get().manage().window().maximize();
////            driverThread.get().manage().deleteAllCookies();
////        }
////        return driverThread.get();
////    }
////
////    public static void quitDriver() {
////        if (driverThread.get() != null) {
////            driverThread.get().quit();
////            driverThread.remove(); // very important for parallel tests
////        }
////
////    }
////
////}
