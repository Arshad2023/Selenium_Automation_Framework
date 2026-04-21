package utilities.ActionMethod;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import utilities.TestLogger;

import java.time.Duration;

public class ActionMethod {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor (Driver injected once)
    public ActionMethod(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Click Element
    public void clickElement(WebElement element) {
        try {
            WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(element));
            try {
                ele.click();
            } catch (Exception e) {
                // Fallback to JS click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
                System.out.println("⚠️ Used JS click for: " + element);
            }
            TestLogger.logPass("Clicked on element: " + element);
        } catch (Exception e) {
            String screenshot = ScreenshotUtil.getScreenshot(driver);
            TestLogger.logFailWithScreenshot("Unable to click on element: " + element, screenshot);
            throw new RuntimeException("❌ Failed to click on element: " + element, e);
        }
    }

    // Enter Text
    public void enterText(WebElement element, String value) {
        try {
            WebElement ele = wait.until(ExpectedConditions.visibilityOf(element));
            ele.clear();
            ele.sendKeys(value);
            TestLogger.logPass("Entered text: " + value);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to enter text in element: " + element, e);
        }
    }
    // Select Dropdown by Value
    public void selectByValue(WebElement element, String value) {
        try {
            WebElement ele = wait.until(ExpectedConditions.visibilityOf(element));
            Select select = new Select(ele);
            select.selectByValue(value);
            TestLogger.logPass("Selected value: " + value);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to select value: " + value, e);
        }
    }

    // Wait for Element Visible (custom timeout)
    public void waitForElementVisible(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            customWait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("✅ Element is visible: " + element);
        } catch (TimeoutException e) {
            throw new RuntimeException("⛔ Timeout: Element not visible → " + element, e);
        }
    }

    // Wait for Element Clickable
    public void waitForElementClickable(WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            customWait.until(ExpectedConditions.elementToBeClickable(element));
            System.out.println("✅ Element is clickable: " + element);
        } catch (TimeoutException e) {
            throw new RuntimeException("⛔ Timeout: Element not clickable → " + element, e);
        }
    }
}