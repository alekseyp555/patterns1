package ru.netology.delivery.test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

public class LoginRequestMockTest {

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
        String payload = "{\"username\":\"test\",\"password\":\"dGVzdA==\"}";

        // Отправляем POST-запрос с новыми аргументами
        APIResponse response = context.request().post(
                "https://api.demoblaze.com/login",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json") // устанавливаем заголовок
                        .setData(payload));

        // Проверяем статус ответа
        assert (response.status() == 200 || response.status() == 201); // ожидаем успешный статус

        // Получаем тело ответа и выводим его
        byte[] bodyBytes = response.body();
        String bodyString = new String(bodyBytes, StandardCharsets.UTF_8);
        System.out.println("Response body: " + bodyString);
    }
}