package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker;

    @BeforeEach
    void setUpAll () {
        faker = new Faker(new Locale("ru"));
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        return faker.address().city();
    }

    public static String generateName(String locale) {
        return faker.name().fullName();
    }

    public static String generatePhone(String locale) {
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
