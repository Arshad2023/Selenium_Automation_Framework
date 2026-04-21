package pages;

import base.BasePage;
import hooks.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utilities.ActionMethod.ActionMethod;

import java.util.Map;

public class LoginPages extends BasePage {

    public LoginPages(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[@id='hrefUserIcon']")
    private WebElement loginIcon;

    @FindBy(xpath = "//input[@name='username']")
    private WebElement username;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement password;

    @FindBy(xpath = "//button[@id='sign_in_btn']")
    private WebElement signInBtn;

    @FindBy(xpath = "//span[text()='Arshad']/following::a[@id='hrefUserIcon']")
    private WebElement welcomeMessage;

    @FindBy(xpath = "//label[text()='Incorrect user name or password.']")
    private WebElement loginErrorMessage;

    // Login Action
    public void loginEcc() {
        action.clickElement(loginIcon);
        action.enterText(username, data.get("Email"));
        action.enterText(password, data.get("Password"));
        action.clickElement(signInBtn);
    }

    // Verify Success
    public void verifyLoginSuccess() {
        Assert.assertTrue(welcomeMessage.isDisplayed(),
                "Login failed: Welcome message not found");
    }

    // Verify Error
    public void verifyLoginErrorMessage() {
        action.waitForElementVisible(loginErrorMessage, 3);
        Assert.assertTrue(loginErrorMessage.isDisplayed(),
                "Login failed: Error message not displayed");
    }
}