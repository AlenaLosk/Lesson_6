import com.codeborne.selenide.*;
import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import pages.RegistrationFormPage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static com.codeborne.selenide.Selenide.*;

public class TestForm {
    private SoftAssertions softAssertions;
    Random random = new Random();
    String firstName, lastName, email, gender, number, subject, hobby1, hobby2, address, state, city;
    Date birthday;
    RegistrationFormPage formPage = new RegistrationFormPage();

    @BeforeEach
    public void setUpEach() {
        softAssertions = new SoftAssertions();

        // generate data for inputs
        String[] locale = {"bg", "ca", "ca-CAT", "da-DK", "de", "de-AT", "de-CH", "en", "en-AU", "en-au-ocker",
                "en-BORK", "en-CA", "en-GB", "en-IND", "en-MS", "en-NEP", "en-NG", "en-NZ", "en-PAK", "en-SG",
                "en-UG", "en-US", "en-ZA", "es", "es-MX", "fa", "fi-FI", "fr", "he", "hu", "in-ID", "it", "ja",
                "ko", "nb-NO", "nl", "pl", "pt", "pt-BR", "ru", "sk", "sv", "sv-SE", "tr", "uk", "vi", "zh-CN", "zh-TW"};
        Faker faker = new Faker(new Locale(locale[random.nextInt(locale.length)]));
        Faker fakerEn = new Faker(new Locale("en-US"));
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = fakerEn.internet().emailAddress();
        gender = List.of("Male", "Female", "Other").get(random.nextInt(3));
        number = faker.phoneNumber().subscriberNumber(10);
        List<String> hobbies = List.of("Sports", "Reading", "Music");
        hobby1 = hobbies.get(random.nextInt(3));
        hobbies = hobbies.stream().filter(e -> !e.equals(hobby1)).collect(Collectors.toList());
        hobby2 = hobbies.get(random.nextInt(2));
        birthday = faker.date().birthday();
        address = faker.address().fullAddress();
        state = List.of("NCR", "Uttar Pradesh", "Haryana", "Rajasthan").get(random.nextInt(4));
        List<String> cites = Map.of("NCR", List.of("Delhi", "Gurgaon", "Noida"),
                "Uttar Pradesh", List.of("Agra", "Lucknow", "Merrut"),
                "Haryana", List.of("Karnal", "Panipat"),
                "Rajasthan", List.of("Jaipur", "Jaiselmer")).get(state);
        city = cites.get(random.nextInt(cites.size()));
    }

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = "https://demoqa.com";
    }

    @AfterEach
    public void tearDownEach() {
        softAssertions.assertAll();
    }

    @Test
    public void fillFormTest() {
        formPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserEmail(email)
                .setGender(gender)
                .setUserNumber(number)
                .setBirthday(birthday)
                .setHobbies(hobby1, hobby2)
                .setAddress(address)
                .uploadFile("src/test/resources/stitch.jpg");

        subject = formPage.setSubject();

        // scroll window
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");

        formPage.setState(state)
                .setCity(city)
                .submit();

        // check data: inserted information and from notification window ones
        formPage.checkResultsTableVisible()
                .checkResultsTable("Student Name", firstName + " " + lastName)
                .checkResultsTable("Student Email", email)
                .checkResultsTable("Gender", gender)
                .checkResultsTable("Mobile", number)
                .checkResultsTable("Date of Birth", new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH).format(birthday))
                .checkResultsTable("Subjects", subject)
                .checkResultsTable("Hobbies", hobby1 + ", " + hobby2)
                .checkResultsTable("Picture", "stitch.jpg")
                .checkResultsTable("Address", address)
                .checkResultsTable("State and City", state + " " + city);
    }
}
