package utilities.ActionFunc;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.TestLogger;

import java.time.Duration;

public class Click {
    public static void clickElement(WebDriver driver, WebElement locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            try {
                element.click();
            } catch (Exception e) {
                // fallback to JS click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                System.out.println("⚠️ Used JS click for: " + locator);
            }
            TestLogger.logPass("Clicked on Login Button");
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to click on element: " + locator, e);
        }
    }
    public static void EnterText(WebDriver driver, WebElement locator, String value){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.sendKeys(value);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to click on element: " + locator, e);
        }
    }
    public static void selectByValue(WebDriver driver, WebElement locator, String value) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) locator));
            Select select = new Select(element);
            select.selectByValue(value);
            System.out.println("✅ Selected value '" + value + "' from dropdown: " + locator);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to select value '" + value + "' from dropdown: " + locator, e);
        }
    }
    public static void waitForElement(WebDriver driver, WebElement locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated((By) locator));
            System.out.println("Element is visible on the screen" + locator);
        } catch (Exception e) {
            throw new RuntimeException("Element is not visibole on the screen" + locator, e);
        }
    }
    public static void waitForElementVisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("✅ Element is visible: " + element.toString());
        } catch (TimeoutException e) {
            System.err.println("⛔ Timeout: Element not visible after " + timeoutInSeconds + " seconds → " + element.toString());
        } catch (Exception e) {
            System.err.println("⚠️ Unexpected error while waiting for element → " + element.toString());
            e.printStackTrace();
        }
    }

}
