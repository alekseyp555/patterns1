package ru.netology.delivery.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BookingForm {

    private final SelenideElement cityInputField = $("[data-test-id=city] input");
    public final SelenideElement dateInputField = $("[data-test-id=date] input");
    private final SelenideElement nameInputField = $("[data-test-id=name] input");
    private final SelenideElement phoneInputField = $("[data-test-id=phone] input");
    private final SelenideElement agreementCheckbox = $("[data-test-id=agreement]");
    private final ElementsCollection notifications = $$(".notification__content");

    public void fillAndSubmitForm(DataGenerator.UserInfo user, String meetingDate) {
        cityInputField.setValue(user.getCity());
        clearDateField(); // метод очистки поля даты ниже
        dateInputField.setValue(meetingDate);
        nameInputField.setValue(user.getName());
        phoneInputField.setValue(user.getPhone());
        agreementCheckbox.click();
        submitForm();
    }

    public void submitForm() {
        $(byText("Запланировать")).click();
    }

    public void verifySuccessNotification(String expectedMessage) {
        notifications.shouldHave(sizeGreaterThanOrEqual(1)); // проверяем наличие хотя бы одной уведомления
        notifications.first().shouldHave(exactText(expectedMessage)).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void verifyReplanNotification() {
        notifications.filterBy(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .first()
                .shouldBe(visible)
                .parent()
                .find(byText("Перепланировать"))
                .click();
    }

    public void clearDateField() { // Очистка поля даты
        dateInputField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }
}