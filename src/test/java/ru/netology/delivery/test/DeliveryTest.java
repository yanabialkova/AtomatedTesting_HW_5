package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Configuration.holdBrowserOpen;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DeliveryTest {

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var locale = "ru";
        var validUser = DataGenerator.Registration.generateUser(locale);
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        var name = DataGenerator.generateName(locale);
        var city = DataGenerator.generateCity(locale);
        var phone = DataGenerator.generatePhone(locale);
        var firstMeeting = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var secondMeeting = DataGenerator.generateDate(daysToAddForSecondMeeting);
        //
        $("[data-test-id ='city'] input").setValue(city);
        $("[data-test-id ='date'] input").setValue(firstMeeting);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeeting);

        $("[data-test-id ='name'] input").setValue(name);
        $("[data-test-id ='phone'] input").setValue(phone);
        $("[data-test-id ='agreement']").click();
        $("[type=button] .button__text").click();
        $(".notification__content").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));

        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(secondMeetingDate);
        $(".button__text").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[type=button] .button__text").click();
        $("[data-test-id=replan-notification] .notification__title").should(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id=replan-notification] span.button__text").click();
        $("[data-test-id=success-notification] .notification__content").should(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

}
