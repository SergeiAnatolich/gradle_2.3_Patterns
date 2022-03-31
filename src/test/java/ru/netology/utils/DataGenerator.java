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
        public static RegistrationInfo generateInfo(String locale, int upDay) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationInfo(
                    faker.address().cityName(),
                    LocalDate.now().plusDays(upDay).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    LocalDate.now().plusDays(-4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    faker.date().toString(),
                    faker.name().fullName(),
                    faker.phoneNumber().phoneNumber()
            );
        }
    }
}
