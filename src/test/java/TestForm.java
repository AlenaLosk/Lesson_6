import org.junit.jupiter.api.*;
import pages.RegistrationFormPage;
import utils.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@DisplayName("Check input forms")
public class TestForm extends TestBase {
    String firstName, lastName, email, gender, number, subject, address, state, city;
    String[] hobbies;
    Date birthday;
    RegistrationFormPage formPage = new RegistrationFormPage();

    @BeforeEach
    public void setUp() {
        // generate data for inputs
        RandomUtils randomUtils = new RandomUtils();
        firstName = randomUtils.getRandomFirstName();
        lastName = randomUtils.getRandomLastName();
        email = randomUtils.getRandomEmail();
        gender = randomUtils.getRandomGender();
        number = randomUtils.getRandomPhoneNumber(10);
        hobbies = randomUtils.getRandomHobbies(List.of("Sports", "Reading", "Music"));
        birthday = randomUtils.getRandomBirthday();
        address = randomUtils.getRandomAddress();
        state = randomUtils.getRandomState(List.of("NCR", "Uttar Pradesh", "Haryana", "Rajasthan"));
        city = randomUtils.getRandomCity(Map.of("NCR", List.of("Delhi", "Gurgaon", "Noida"),
                "Uttar Pradesh", List.of("Agra", "Lucknow", "Merrut"),
                "Haryana", List.of("Karnal", "Panipat"),
                "Rajasthan", List.of("Jaipur", "Jaiselmer")), state);
    }

    @DisplayName("Check input form")
    @Test
    public void fillFormTest() {
        formPage.openPage()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserEmail(email)
                .setGender(gender)
                .setUserNumber(number)
                .setBirthday(birthday)
                .setHobbies(hobbies)
                .setAddress(address)
                .uploadFile("src/test/resources/stitch.jpg");

        subject = formPage.setSubject();

        /* scroll window
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");*/

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
                .checkResultsTable("Hobbies", String.join(", ", hobbies))
                .checkResultsTable("Picture", "stitch.jpg")
                .checkResultsTable("Address", address)
                .checkResultsTable("State and City", state + " " + city);
    }
}
