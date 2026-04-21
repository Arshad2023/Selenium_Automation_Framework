package base;

import hooks.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

import java.time.Duration;
import java.util.Map;

public class BaseTests {

    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    protected Map<String, String> data = Hooks.getTestData();
    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void initDriver() {

        if (driverThread.get() != null) {
            return;
        }

        String browser = ConfigReader.get("browser").toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("headless"));

        WebDriver driver;
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("-headless");
                }

                driver = new FirefoxDriver(firefoxOptions);

                if (!isHeadless) {
                    driver.manage().window().maximize();
                }
                break;

            default:
                throw new RuntimeException("❌ Browser not supported: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfigReader.get("baseUrl"));

        driverThread.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();

        if (driver != null) {
            try {
                driver.quit();
            } finally {
                driverThread.remove();
            }
        }
    }
}