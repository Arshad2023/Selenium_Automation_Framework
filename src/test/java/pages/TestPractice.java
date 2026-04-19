package pages;
import base.BaseTests;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import pages.LoginPages;
import utilities.TestLogger;
//import utils.DriverManager;
import utils.ExcelUtils;

import java.time.Duration;
import java.util.Map;
import java.util.NoSuchElementException;

public class TestPractice {
    WebDriver driver = BaseTests.getDriver();
    public void Setup(){
        WebElement elem = driver.findElement(By.xpath("//input[@title='NoTitle']"));
        Select sel = new Select(elem);
        sel.selectByVisibleText("testc");

        WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(10));

        waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("TestID")));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(driver -> driver.findElement(By.id("loginBtn")));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,500);",element);



        element.click();
    }





}
