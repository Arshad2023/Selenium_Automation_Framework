//package tests;
//
//import base.BaseTests;
//import org.testng.annotations.Test;
//import pages.LoginPage;
//import pages.SearchProdPage;
//import utils.ExcelDataProvider;
//
//import java.util.Map;
//
//public class E2E extends BaseTests {
//
//    @Test(dataProvider = "getDataFromExcel", dataProviderClass = ExcelDataProvider.class)
//    public void E2ETest(Map<String, String> data) {
//        // Initialize page objects using thread-safe driver
//        LoginPage loginPage = new LoginPage(getDriver());
//        SearchProdPage srchPage = new SearchProdPage(getDriver());
//        // Perform actions
//        loginPage.Login(data);
//        srchPage.Search(data);
//    }
//}
