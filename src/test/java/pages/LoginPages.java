package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static utilities.ActionFunc.Click.*;

public class LoginPages {
    WebDriver driver;
    public LoginPages(WebDriver driver) {
        this.driver = driver;
        // Initialize all @FindBy elements
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@id='hrefUserIcon']")
    private WebElement LoginIcon;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement Username;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement Password;

    @FindBy(xpath = "//button[@id='sign_in_btn']")
    private WebElement SigInbtn;

    @FindBy(xpath = "//span[text()='Arshad']/following::a[@id='hrefUserIcon']")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//label[text()='Incorrect user name or password.']")
    private WebElement loginErrorMessage;

    public void LoginEcc(Map<String, String> data){
        clickElement(driver, LoginIcon);
        EnterText(driver, Username, data.get("Email"));
        EnterText(driver, Password, data.get("Password"));
        clickElement(driver, SigInbtn);
    }
    public void verifyLoginSuccess() {
        Assert.assertTrue(welcomeMessage.isDisplayed(), "Login failed: Welcome message not found");
    }
    public void verifyLoginErrorMessage() throws InterruptedException {
        waitForElementVisible(driver,loginErrorMessage,3);
        Assert.assertTrue(loginErrorMessage.isDisplayed(), "Login failed: Welcome message not found");
    }
}
