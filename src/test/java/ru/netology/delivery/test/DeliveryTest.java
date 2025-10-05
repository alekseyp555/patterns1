package ru.netology.delivery.test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.delivery.data.DataGenerator;

public class DeliveryTest {

    private static Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeMethod
    public void setup() {
        playwright = Playwright.create();
//        Playwright runs browsers in headless mode by default. To change this behavior, use headless: false as a launch option.
//        You can also use the setSlowMo option to slow down execution (by N milliseconds per operation) and follow along while debugging.
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)); // Or .firefox(), .webkit()
        page = browser.newPage();
        page.navigate("http://localhost:9999");
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

    @Test(description = "Should successful plan meeting using Playwright")
    public void shouldSuccessfulPlanMeeting() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        page.fill("[data-test-id=city] input", validUser.getCity());
        page.fill("[data-test-id=date] input", "");
        page.fill("[data-test-id=date] input", firstMeetingDate);
        page.fill("[data-test-id=name] input", validUser.getName());
        page.fill("[data-test-id=phone] input", validUser.getPhone());
        page.check("[data-test-id=agreement]");
        page.click("text=Запланировать");
        page.locator("[data-test-id='success-notification']").waitFor();
        page.waitForSelector("[data-test-id='success-notification']", new Page.WaitForSelectorOptions().setTimeout(15_000));
        Assert.assertTrue(page.innerText("[data-test-id='success-notification'] .notification__content").contains("Встреча успешно запланирована на " + firstMeetingDate));
        page.fill("[data-test-id=date] input", "");
        page.fill("[data-test-id=date] input", secondMeetingDate);
        page.click("text=Запланировать");
        page.waitForSelector("[data-test-id='replan-notification']", new Page.WaitForSelectorOptions().setTimeout(15_000));
        Assert.assertTrue(page.innerText("[data-test-id='replan-notification'] .notification__content")
                .contains("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        page.click("[data-test-id='replan-notification'] button");
        page.waitForSelector("[data-test-id='success-notification']", new Page.WaitForSelectorOptions().setTimeout(15_000));
        Assert.assertTrue(page.innerText("[data-test-id='success-notification'] .notification__content")
                .contains("Встреча успешно запланирована на " + secondMeetingDate));
    }
}