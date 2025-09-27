package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

// Класс предназначен для генерации фейковых данных пользователей и некоторых вспомогательных функций.
public class DataGenerator {

    // Конструктор сделан приватным, чтобы предотвратить инстанцирование класса,
    // так как этот класс является утилитарным (статическим).
    private DataGenerator() {}

    // Генерация будущей даты на указанное количество дней вперед.
    // Возвращается строка формата dd.MM.yyyy.
    public static String generateDate(int shift) {
        return LocalDate.now()           // получаем сегодняшнюю дату
                .plusDays(shift)       // добавляем заданное число дней
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")); // преобразуем в строку нужного формата
    }

    // Генерация города из заранее подготовленного списка городов России.
    public static String generateCity() {
        var cities = new String[]{      // массив русских городов
                "Москва", "Санкт-Петербург", "Казань", "Рязань", "Нижний Новгород",
                "Владивосток", "Ростов-на-Дону", "Тверь", "Ярославль", "Тула", "Мурманск", "Владимир"
        };
        return cities[new Random().nextInt(cities.length)]; // возвращаем случайный элемент массива
    }

    // Генерация имени пользователя на определенном локали (языке).
    public static String generateName(String locale) {
        var faker = new Faker(new Locale(locale));          // инициализация генератора имен
        return faker.name().lastName() + " "               // фамилия
                + faker.name().firstName();                  // имя
    }

    // Генерация номера телефона на определенной локали.
    public static String generatePhone(String locale) {
        var faker = new Faker(new Locale(locale));          // генератор номеров телефонов
        return faker.phoneNumber().phoneNumber();          // получение случайного номера телефона
    }

    // Внутренний статический класс для регистрации пользователя.
    public static class Registration {
        private Registration() {}                          // закрытый конструктор

        // Создание экземпляра UserInfo с указанием необходимых данных пользователя.
        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateCity(),                         // генерируем случайный город
                    generateName(locale),                   // генерируем полное имя пользователя
                    generatePhone(locale)                   // генерируем номер телефона
            );
        }
    }

    // Иммутабельный класс (благодаря аннотации @Value) для хранения данных пользователя.
    @Value
    public static class UserInfo {
        String city;                                        // Город проживания
        String name;                                        // Полное имя пользователя
        String phone;                                       // Номер телефона
    }
}