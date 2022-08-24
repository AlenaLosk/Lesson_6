package utils;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;

public class RandomUtils {
    Random random = new Random();
    String[] locale = {"bg", "ca", "ca-CAT", "da-DK", "de", "de-AT", "de-CH", "en", "en-AU", "en-au-ocker",
            "en-BORK", "en-CA", "en-GB", "en-IND", "en-MS", "en-NEP", "en-NG", "en-NZ", "en-PAK", "en-SG",
            "en-UG", "en-US", "en-ZA", "es", "es-MX", "fa", "fi-FI", "fr", "he", "hu", "in-ID", "it", "ja",
            "ko", "nb-NO", "nl", "pl", "pt", "pt-BR", "ru", "sk", "sv", "sv-SE", "tr", "uk", "vi", "zh-CN", "zh-TW"};
    Faker faker = new Faker(new Locale(locale[random.nextInt(locale.length)]));
    Faker fakerEn = new Faker(new Locale("en-US"));

    public String getRandomFirstName() {
        return faker.name().firstName();
    }

    public String getRandomLastName() {
        return faker.name().firstName();
    }

    public String getRandomEmail() {
        return fakerEn.internet().emailAddress();
    }

    public String getRandomGender() {
        return List.of("Male", "Female", "Other").get(random.nextInt(3));
    }

    public String getRandomPhoneNumber(int length) {
        return faker.phoneNumber().subscriberNumber(length);
    }

    public String[] getRandomHobbies(List<String> hobbies) {
        String[] result = new String[random.nextInt(3) + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = hobbies.get(random.nextInt(3 - i));
            String temp = result[i];
            hobbies = hobbies.stream().filter(e -> !e.equals(temp)).collect(Collectors.toList());
        }
        return result;
    }

    public Date getRandomBirthday() {
        return faker.date().birthday();
    }

    public String getRandomAddress() {
        return faker.address().fullAddress();
    }


    public String getRandomState(List<String> states) {
        return states.get(random.nextInt(4));
    }

    public String getRandomCity( Map<String, List<String>> cities, String state) {
        List<String> cites = cities.get(state);
        return cites.get(random.nextInt(cites.size()));
    }
}
