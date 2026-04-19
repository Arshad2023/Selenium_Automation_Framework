package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class http {

    public static void main(String[] args) {

        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.webpagetest.org/"); // replace with your URL

        List<WebElement> links = driver.findElements(By.tagName("a"));

        System.out.println("Total links found: " + links.size());

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty()) {
                System.out.println("URL is empty or null");
                continue;
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(5000);
                connection.connect();

                int responseCode = connection.getResponseCode();

                if (responseCode >= 400) {
                    System.out.println(url + " --> BROKEN LINK ❌ (" + responseCode + ")");
                } else {
                    System.out.println(url + " --> VALID LINK ✅ (" + responseCode + ")");
                }

            } catch (Exception e) {
                System.out.println(url + " --> ERROR ❌ (Invalid URL or connection issue)");
            }
        }

        driver.quit();
    }
}