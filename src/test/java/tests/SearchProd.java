//package tests;
//
//import base.BaseTests;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.testng.annotations.Test;
//import pages.SearchProdPage;
//import utils.ExcelDataProvider;
//
//import java.util.Map;
//
//import static utilities.ActionFunc.Click.EnterText;
//import static utilities.ActionFunc.Click.clickElement;
//
//public class SearchProd extends BaseTests {
//
//    //@Listeners(TestListener.class)
//    @Test(dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class)
//    public void searchProd(Map<String, String> data) throws InterruptedException {
//        SearchProdPage Srch = new SearchProdPage(getDriver());  // Pass driver to Page Object
//        Srch.Search(data);
//    }
//
//}