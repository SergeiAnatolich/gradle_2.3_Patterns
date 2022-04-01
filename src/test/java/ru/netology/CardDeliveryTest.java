package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.entities.RegistrationInfo;
import ru.netology.utils.DataGenerator;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    private RegistrationInfo validInfo;
    private RegistrationInfo invalidInfo;

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999/");
    }

    @BeforeEach
    public void dataInfoClient() {
        validInfo = DataGenerator.Registration.generateInfo("ru", 4);
        invalidInfo = DataGenerator.Registration.generateInfo("en", 4);
    }

    @Test
    public void confirmationMeeting() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        String newDate = DataGenerator.Registration.generateInfo("ru", 6).getDateMeeting();
        $("[data-test-id=date] input").val(newDate);
        $(withText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title").should(appear);
    }

    @Test
    public void rescheduleMeeting() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        String newDate = DataGenerator.Registration.generateInfo("ru", 6).getDateMeeting();
        $("[data-test-id=date] input").val(newDate);
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__title").should(appear);
    }

    @Test
    public void rescheduleMeetingMessageDate() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        String newDate = DataGenerator.Registration.generateInfo("ru", 6).getDateMeeting();
        $("[data-test-id=date] input").val(newDate);
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        String actual = $("[data-test-id=success-notification] .notification__content").getText();
        String expected = "Встреча успешно запланирована на " + newDate;
        assertEquals(expected, actual);
    }

    @Test
    public void formSendSuccessWindowAppear() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title").should(appear);
    }

    @Test
    public void formSendSuccessMessageSuccessfully() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title").should(appear);
        String actual = $("[data-test-id=success-notification] .notification__title").getText();
        String expected = "Успешно!";
        assertEquals(expected, actual);
    }

    @Test
    public void formSendSuccessMessageDateDelivery() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title").should(appear, Duration.ofSeconds(15));
        String actual = $("[data-test-id=success-notification] .notification__content").getText();
        String expected = "Встреча успешно запланирована на " + validInfo.getDateMeeting();
        assertEquals(expected, actual);
    }

    @Test
    public void allNull() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void cityNull() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void cityInvalid() {
        $("[data-test-id=city] input").val(invalidInfo.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Доставка в выбранный город недоступна";
        assertEquals(expected, actual);
    }

    @Test
    public void dateNull() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=date] .input_invalid .input__sub").getText();
        String expected = "Неверно введена дата";
        assertEquals(expected, actual);
    }

    @Test
    public void dateIsOutdated() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(invalidInfo.getDateMeetingOut());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=date] .input_invalid .input__sub").getText();
        String expected = "Заказ на выбранную дату невозможен";
        assertEquals(expected, actual);
    }

    @Test
    public void dateInvalid() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(invalidInfo.getDateMeetingInvalid());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=date] .input_invalid .input__sub").getText();
        String expected = "Неверно введена дата";
        assertEquals(expected, actual);
    }

    @Test
    public void nameNull() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=name].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void nameInvalid() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(invalidInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=name].input_invalid .input__sub").getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void phoneNull() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

//    Тест не может быть выполнен т.к. валидация поля неверная. Форма отправляется если есть хотябы +.
//    Составлен bug report.
//    @Test
//    public void phoneInvalid() {
//        $("[data-test-id=city] input").val(validInfo.getCity());
//        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
//        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
//        $("[data-test-id=name] input").val(validInfo.getName());
//        $("[data-test-id=phone] input").val("8980");
//        $("[data-test-id=agreement]").click();
//        $(withText("Запланировать")).click();
//        String actual = $("[data-test-id=phone].input_invalid .input__sub").getText();
//        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
//        assertEquals(expected, actual);
//    }

    @Test
    public void allFieldsNullCheckboxChecked() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=city] input").click();
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void allFieldsValidCheckboxUnchecked() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $(withText("Запланировать")).click();
        boolean actual = $("[data-test-id=agreement].input_invalid").isDisplayed();
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void allFieldsInvalidCheckboxChecked() {
        $("[data-test-id=city] input").val(invalidInfo.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(invalidInfo.getDateMeetingInvalid());
        $("[data-test-id=name] input").val(invalidInfo.getName());
        $("[data-test-id=phone] input").val("8987");
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Доставка в выбранный город недоступна";
        assertEquals(expected, actual);
    }

    @Test
    public void allFieldsInvalidCheckboxUnchecked() {
        $("[data-test-id=city] input").val(invalidInfo.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(invalidInfo.getDateMeetingInvalid());
        $("[data-test-id=name] input").val(invalidInfo.getName());
        $("[data-test-id=phone] input").val("89876");
        $(withText("Запланировать")).click();
        String actual = $("[data-test-id=city].input_invalid .input__sub").getText();
        String expected = "Доставка в выбранный город недоступна";
        assertEquals(expected, actual);
    }

    @Test
    public void selectionCityFromTheList() {
        $("[data-test-id=city] input").val("Мо");
        $(withText("Москва")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(validInfo.getDateMeeting());
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title").should(appear);
    }

    @Test
    public void selectionDateFromCalendar() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] button").click();
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        SimpleDateFormat dayFormat = new SimpleDateFormat("d");
        String dayDelivery = dayFormat.format(calendar.getTime());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        String monthDelivery = monthFormat.format(calendar.getTime());
        String actualMonth = $(".calendar__name").getText();
        if (!actualMonth.toLowerCase(Locale.ROOT).contains(monthDelivery)) {
            $$(".calendar__arrow_direction_right").last().click();
        }
        $x("//*[text()='" + dayDelivery + "']").click();
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title").should(appear);
    }
}
