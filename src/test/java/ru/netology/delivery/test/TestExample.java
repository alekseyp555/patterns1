package ru.netology.delivery.test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestExample {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(); // Or .firefox(), .webkit()
        page = browser.newPage();
    }

    @Test
    public void exampleTest() {
        page.navigate("https://www.example.com");
        System.out.println("Page title: " + page.title());
        // Add your Playwright interactions here
    }

    @AfterMethod
    public void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
