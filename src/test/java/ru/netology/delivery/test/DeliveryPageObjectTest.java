package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import ru.netology.delivery.pages.MainPage;

class DeliveryPageObjectTest {

    private MainPage mainPage;
    /**
     * Метод подготовки перед каждым тестовым методом.
     * Здесь создается объект страницы, что автоматически открывает браузер
     * и загружает основную страницу сайта (страницу доставки).
     */
    @BeforeEach
    void setup() {
        mainPage = new MainPage(); // открытие основной страницы
    }
    /**
     * Тестируем успешное планирование встречи.
     * Проверяется процесс выбора даты, заполнения формы,
     * подтверждение успешного сохранения первой встречи и возможности изменения даты второй встречи.
     */
    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSuccessfulPlanMeeting() {
        // Генерируем валидные данные пользователя (город, имя, телефон),
        // использующие фейк-данные на русском языке.
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        // Рассчитываем будущую дату для первой встречи (через 4 дня):
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        // Рассчитываем дату для второй встречи (через 7 дней):
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // Получаем доступ к форме бронирования через главную страницу.
        var bookingForm = mainPage.goToBookingForm();
        // Заполняем форму с использованием первого выбранного датасета
        // (пользовательские данные и первая выбранная дата встречи).
        bookingForm.fillAndSubmitForm(validUser, firstMeetingDate);
        // Проверяем появление уведомления об успешной записи на указанную дату.
        bookingForm.verifySuccessNotification("Встреча успешно запланирована на " + firstMeetingDate);
        // очищаем поле даты
        bookingForm.clearDateField();
        // Меняем дату встречи повторно (зачищаем поле даты).
        bookingForm.dateInputField.setValue(secondMeetingDate);
        // Отправляем заново форму.
        bookingForm.submitForm();
        // Подтверждение появления окна с вопросом о повторном планировании встречи.
        bookingForm.verifyReplanNotification();
        // После подтверждения повторной отправки формы ожидаем нового уведомления
        // об успешном изменении даты встречи.
        bookingForm.verifySuccessNotification("Встреча успешно запланирована на " + secondMeetingDate);
    }
}