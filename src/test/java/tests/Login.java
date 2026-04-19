//package tests;
//
//import org.testng.annotations.Test;
//import pages.LoginPages;
//import utils.ExcelDataProvider;
//
//import java.util.Map;
//
//import static base.BaseTests.getDriver;
//
//public class Login {
//
//    @Test(dataProvider = "getDataFromExcel", dataProviderClass = ExcelDataProvider.class)
//    public void testValidLogin(Map<String, String> data) throws InterruptedException {
//        LoginPages loginPages = new LoginPages(getDriver());
//        //Map<String, String> testData = ExcelUtils.getRowData("LoginData", "TC_Login_01");
//        System.out.println("Test Data: " + data);
//        loginPages.LoginEcc(data);
//
//    }
//}
