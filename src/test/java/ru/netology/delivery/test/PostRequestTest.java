package ru.netology.delivery.test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

public class PostRequestTest {

    private static Playwright playwright;
    private BrowserContext context;

    @BeforeClass
    public void beforeClass() {
        playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        Browser browser = browserType.launch();
        context = browser.newContext();
    }

    @AfterClass
    public void afterClass() {
        context.close();
        playwright.close();
    }

    @Test
    public void postUserDataTest() {
        // Тело запроса в формате JSON
        String payload = "{\n" +
                "    \"id\": 0,\n" +
                "    \"username\": \"string\",\n" +
                "    \"firstName\": \"string\",\n" +
                "    \"lastName\": \"string\",\n" +
                "    \"email\": \"string\",\n" +
                "    \"password\": \"string\",\n" +
                "    \"phone\": \"string\",\n" +
                "    \"userStatus\": 0\n" +
                "}";

        // Отправляем POST-запрос с новыми аргументами
        APIResponse response = context.request().post(
                "https://petstore.swagger.io/v2/user",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json") // устанавливаем заголовок
                        .setData(payload));

        // Проверяем статус ответа
        assert response.status() == 200; // ожидаем успешный статус

        // Получаем тело ответа и выводим его
        byte[] bodyBytes = response.body();
        String bodyString = new String(bodyBytes, StandardCharsets.UTF_8);
        System.out.println("Response body: " + bodyString);
    }
}