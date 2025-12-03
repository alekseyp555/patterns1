package ru.netology.delivery.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MockExampleTest {

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
    public void exampleMockTest() {
        // Intercept the route to the fruit API
        page.route("https://fruit.ceo/api/breeds/image/random", route -> {
            List<Dictionary<String, Object>> data = new ArrayList<Dictionary<String, Object>>();
            Hashtable<String, Object> dict = new Hashtable<String, Object>();
            dict.put("name", "Strawberry");
            dict.put("id", 21);
            data.add(dict);
            // fulfill the route with the mock data
            route.fulfill((Route.FulfillOptions) RequestOptions.create().setData(data));
        });

        // Go to the page
        page.navigate("https://demo.playwright.dev/api-mocking");

        // Assert that the Strawberry fruit is visible
        assertThat(page.getByText("Strawberry")).isVisible();
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
