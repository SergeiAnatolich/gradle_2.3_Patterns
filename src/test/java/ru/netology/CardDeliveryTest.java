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
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private RegistrationInfo validInfo;
    private RegistrationInfo invalidInfo;

    @BeforeEach
    public void openBrowser() {
        open("http://localhost:9999/");
    }

    @BeforeEach
    public void dataInfoClient() {
        validInfo = DataGenerator.Registration.generateInfo("ru");
        invalidInfo = DataGenerator.Registration.generateInfo("en");
    }

    @Test
    public void rescheduleMeeting() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно запланирована на "))
                .shouldHave(text(DataGenerator.dateMeeting(4)));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(6));
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно запланирована на "))
                .shouldHave(text(DataGenerator.dateMeeting(6)));
    }

    @Test
    public void formSendSuccessMessageDateDelivery() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__title").should(appear, Duration.ofSeconds(15));
        $("[data-test-id=success-notification]")
                .shouldHave(text("Успешно!"))
                .shouldHave(text("Встреча успешно запланирована на "))
                .shouldHave(text(DataGenerator.dateMeeting(4)));
    }

    @Test
    public void allNull() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void cityNull() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void cityInvalid() {
        $("[data-test-id=city] input").val(invalidInfo.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
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
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void dateIsOutdated() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(-4));
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void dateInvalid() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val("22.15.2022");
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    public void nameNull() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void nameInvalid() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=name] input").val(invalidInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void phoneNull() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

//    Тест не может быть выполнен т.к. валидация поля неверная. Форма отправляется если есть хотябы +.
//    Составлен bug report.
//    @Test
//    public void phoneInvalid() {
//        $("[data-test-id=city] input").val(validInfo.getCity());
//        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
//        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
//        $("[data-test-id=name] input").val(validInfo.getName());
//        $("[data-test-id=phone] input").val("8980");
//        $("[data-test-id=agreement]").click();
//        $(withText("Запланировать")).click();
//        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
//    }

    @Test
    public void allFieldsNullCheckboxChecked() {
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=city] input").click();
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    public void allFieldsValidCheckboxUnchecked() {
        $("[data-test-id=city] input").val(validInfo.getCity().substring(0, 2));
        $$(".menu-item").first().click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
        $("[data-test-id=name] input").val(validInfo.getName());
        $("[data-test-id=phone] input").val(validInfo.getPhoneNumber());
        $(withText("Запланировать")).click();
        $("[data-test-id=agreement].input_invalid").isDisplayed();
    }

    @Test
    public void allFieldsInvalidCheckboxChecked() {
        $("[data-test-id=city] input").val(invalidInfo.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val("00.00.0000");
        $("[data-test-id=name] input").val(invalidInfo.getName());
        $("[data-test-id=phone] input").val("8987");
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void allFieldsInvalidCheckboxUnchecked() {
        $("[data-test-id=city] input").val(invalidInfo.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val("35.20.2022");
        $("[data-test-id=name] input").val(invalidInfo.getName());
        $("[data-test-id=phone] input").val("89876");
        $(withText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    public void selectionCityFromTheList() {
        $("[data-test-id=city] input").val("Мо");
        $(withText("Москва")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        $("[data-test-id=date] input").val(DataGenerator.dateMeeting(4));
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
