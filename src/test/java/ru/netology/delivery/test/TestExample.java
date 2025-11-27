package ru.netology.delivery.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestExample {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)); // Or .firefox(), .webkit()
        page = browser.newPage();
    }

    String payload = "{\n" + "    \"Items\": [\n" + "        {\n" + "            \"cat\": \"phone\",\n" + "            \"desc\": \"The Samsung Galaxy S6 is powered by 1.5GHz octa-core Samsung Exynos 7420\\n processor and it comes with 3GB of RAM. The phone packs 32GB of \\ninternal storage cannot be expanded. \",\n" + "            \"id\": 1,\n" + "            \"img\": \"imgs/galaxy_s6.jpg\",\n" + "            \"price\": 360.0,\n" + "            \"title\": \"Samsung galaxy s6\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"phone\",\n" + "            \"desc\": \"The Nokia Lumia 1520 is powered by 2.2GHz quad-core Qualcomm Snapdragon 800 processor and it comes with 2GB of RAM. \",\n" + "            \"id\": 2,\n" + "            \"img\": \"imgs/Lumia_1520.jpg\",\n" + "            \"price\": 820.0,\n" + "            \"title\": \"Nokia lumia 1520\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"phone\",\n" + "            \"desc\": \"The Motorola Google Nexus 6 is powered by 2.7GHz quad-core Qualcomm Snapdragon 805 processor and it comes with 3GB of RAM.\",\n" + "            \"id\": 3,\n" + "            \"img\": \"imgs/Nexus_6.jpg\",\n" + "            \"price\": 650.0,\n" + "            \"title\": \"Nexus 6\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"phone\",\n" + "            \"desc\": \"The Samsung Galaxy S7 is powered by 1.6GHz octa-core it comes with 4GB \\nof RAM. The phone packs 32GB of internal storage that can be expanded up\\n to 200GB via a microSD card.\",\n" + "            \"id\": 4,\n" + "            \"img\": \"imgs/galaxy_s6.jpg\",\n" + "            \"price\": 800.0,\n" + "            \"title\": \"Samsung galaxy s7\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"phone\",\n" + "            \"desc\": \"It comes with 1GB of RAM. The phone packs 16GB of internal storage \\ncannot be expanded. As far as the cameras are concerned, the Apple \\niPhone 6 packs a 8-megapixel primary camera on the rear and a \\n1.2-megapixel front shooter for selfies.\",\n" + "            \"id\": 5,\n" + "            \"img\": \"imgs/iphone_6.jpg\",\n" + "            \"price\": 790.0,\n" + "            \"title\": \"Iphone 6 32gb\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"phone\",\n" + "            \"desc\": \"Sony Xperia Z5 Dual smartphone was launched in September 2015. The phone\\n comes with a 5.20-inch touchscreen display with a resolution of 1080 \\npixels by 1920 pixels at a PPI of 424 pixels per inch.\",\n" + "            \"id\": 6,\n" + "            \"img\": \"imgs/xperia_z5.jpg\",\n" + "            \"price\": 320.0,\n" + "            \"title\": \"Sony xperia z5\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"phone\",\n" + "            \"desc\": \"The HTC One M9 is powered by 1.5GHz octa-core Qualcomm Snapdragon 810 \\nprocessor and it comes with 3GB of RAM. The phone packs 32GB of internal\\n storage that can be expanded up to 128GB via a microSD card. \",\n" + "            \"id\": 7,\n" + "            \"img\": \"imgs/HTC_M9.jpg\",\n" + "            \"price\": 700.0,\n" + "            \"title\": \"HTC One M9\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"notebook\",\n" + "            \"desc\": \"Sony is so confident that the VAIO S is a superior ultraportable laptop \\nthat the company proudly compares the notebook to Apple's 13-inch \\nMacBook Pro. And in a lot of ways this notebook is better, thanks to a \\nlighter weight.\",\n" + "            \"id\": 8,\n" + "            \"img\": \"imgs/sony_vaio_5.jpg\",\n" + "            \"price\": 790.0,\n" + "            \"title\": \"Sony vaio i5\"\n" + "        },\n" + "        {\n" + "            \"cat\": \"notebook\",\n" + "            \"desc\": \"REVIEW\\n \\nSony is so confident that the VAIO S is a superior \\nultraportable laptop that the company proudly compares the notebook to \\nApple's 13-inch MacBook Pro. And in a lot of ways this notebook is \\nbetter, thanks to a lighter weight, higher-resolution display, more \\nstorage space, and a Blu-ray drive. \",\n" + "            \"id\": 9,\n" + "            \"img\": \"imgs/sony_vaio_5.jpg\",\n" + "            \"price\": 790.0,\n" + "            \"title\": \"Sony vaio i7\\n\"\n" + "        }\n" + "    ],\n" + "    \"LastEvaluatedKey\": {\n" + "        \"id\": \"9\"\n" + "    }\n" + "}";

//    @Test
//    public void exampleTest() {
//        // Intercept all requests to a specific API endpoint
//        page.route("**/login", route -> {
//            // Fulfill the request with a custom JSON response
//            route.fulfill(new Route.FulfillOptions().setStatus(200).setContentType("application/json"));
//        });
//        page.route("**/config.json", route -> {
//            // Fulfill the request with a custom JSON response
//            route.fulfill(new Route.FulfillOptions().setStatus(200).setContentType("application/json").setBody("{\n" + "    \"API_URL\": \"https://api.demoblaze.com\",\n" + "    \"HLS_URL\": \"https://hls.demoblaze.com\"\n" + "}"));
//        });
//        page.route("**/check", route -> {
//            // Fulfill the request with a custom JSON response
//            route.fulfill(new Route.FulfillOptions().setStatus(200).setContentType("application/json").setBody("{\"Item\":{\"expiration\":1764693,\"token\":\"dGVzdDE3NjQ2OTM=\",\"username\":\"test\"}}\n"));
//        });
//        page.route("**/entries", route -> {
//            // Fulfill the request with a custom JSON response
//            route.fulfill(new Route.FulfillOptions().setStatus(200).setContentType("application/json").setBody(payload));
//        });
//        page.navigate("https://www.demoblaze.com/index.html");
//        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
//        page.locator("#login2").click();
//        page.locator("#loginusername").fill("test");
//        page.locator("#loginpassword").fill("test");
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in")).click();
//        assertThat(page.locator("#logout2")).isVisible();
//    }

    @Test
    public void mockApiTest() {
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
