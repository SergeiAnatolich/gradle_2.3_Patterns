package ru.netology.utils;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.entities.RegistrationInfo;

import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Registration {
        public static RegistrationInfo generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationInfo(
                    faker.address().cityName(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }
    }

    public String dateMeeting(int Day) {
        String dateMeeting = LocalDate.now().plusDays(Day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return dateMeeting;
    }
}