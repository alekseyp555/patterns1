package ru.netology.delivery.pages;// package ru.netology.delivery.pages;

import com.codeborne.selenide.Selenide;

public class MainPage {

    private final BookingForm bookingForm = new BookingForm();

    public MainPage() {
        Selenide.open("http://localhost:9999"); // Открываем главную страницу приложения
    }

    public BookingForm goToBookingForm() {
        return bookingForm;
    }
}