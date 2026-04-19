package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;
import runners.TestRunner;

import java.time.Duration;

public class BaseTests {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void initDriver() {
        // 🔥 Prevent duplicate driver creation
        if (driverThread.get() != null) {
            return;
        }

        String browser = ConfigReader.get("browser").toLowerCase();
        WebDriver driver;

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--start-maximized");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().maximize();
                break;
            default:
                throw new RuntimeException("❌ Browser not supported: " + browser);
        }

        // Optional: keep or remove based on your design
        driver.get(ConfigReader.get("baseUrl"));

        driverThread.set(driver);
    }
    public static void quitDriver() {
        WebDriver driver = driverThread.get();

        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // ignore
            } finally {
                driverThread.remove();   // 🔥 VERY IMPORTANT
            }
        }
    }
}