package stepDefinitions;

import base.BaseTests;
import hooks.Hooks;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.LoginPages;
import utilities.TestLogger;
import java.util.Map;


public class LoginSteps extends BaseTests{
    WebDriver  driver = BaseTests.getDriver();
    LoginPages loginPage;

    @Given("User is on login page for test case {string} from sheet {string}")
    public void user_is_on_login_page(String testCaseID, String sheetName) {
        TestLogger.logInfo("Navigating to login page");
        loginPage = new LoginPages(driver);
        if (data == null) {
            throw new RuntimeException("No data found in Excel for TestCaseID: " + testCaseID);
        }
    }

    @When("User enters login credentials and submits")
    public void user_enters_login_credentials_and_submits() {

        // ✅ safe usage now (driver is fresh per scenario)
        loginPage.loginEcc();
    }

    @Then("Login result should be validated for test case {string}")
    public void login_result_should_be_validated(String testCaseID) {

        String expected = data.getOrDefault("ExpectedResult", "Error");

        if (expected.equalsIgnoreCase("Success") || expected.equalsIgnoreCase("Homepage")) {
            loginPage.verifyLoginSuccess();
        } else {
            // loginPage.verifyLoginError();
        }
    }

    @Then("User should get error message {string}")
    public void login_with_wrong_password(String testCaseID) throws InterruptedException {
        loginPage.verifyLoginErrorMessage();
    }
}