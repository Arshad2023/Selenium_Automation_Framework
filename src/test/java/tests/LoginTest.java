//package tests;
//
//import base.BaseTests;
//import org.openqa.selenium.By;
//import org.testng.annotations.Test;
//import pages.LoginPage;
//import utilities.ActionFunc.Click;
//import utils.ExcelDataProvider;
//
//import java.util.Map;
//
//import static utilities.ActionFunc.Click.EnterText;
//import static utilities.ActionFunc.Click.clickElement;
//
//public class LoginTest extends BaseTests {
//
//    //@Listeners(TestListener.class)
//    @Test(dataProvider = "getDataFromExcel", dataProviderClass = ExcelDataProvider.class)
//    public void testValidLogin(Map<String, String> data) throws InterruptedException {
//        LoginPage loginPage = new LoginPage(getDriver());
//        //Map<String, String> testData = ExcelUtils.getRowData("LoginData", "TC_Login_01");
//        System.out.println("Test Data: " + data);
//        loginPage.Login(data);
//
//    }
//
//}
