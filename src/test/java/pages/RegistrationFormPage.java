package pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import pages.components.CalendarComponent;
import pages.components.ResultsModal;

import java.io.File;
import java.util.Date;
import java.util.Random;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class RegistrationFormPage {
    private final static String TITLE_TEXT = "Student Registration Form";
    private final static String PATH = "/automation-practice-form";

    CalendarComponent calendarComponent = new CalendarComponent();
    ResultsModal resultsModal = new ResultsModal();

    SelenideElement firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            userEmailInput = $("#userEmail"),
            genderRadioButton = $("#genterWrapper"),
            userNumberInput = $("#userNumber"),
            dateOfBirthInput = $("#dateOfBirthInput"),
            subjectsInput = $("#subjectsInput"),
            currentAddressInput = $("#currentAddress"),
            pictureChooseWindow = $("#uploadPicture"),
            stateInput = $("#state"),
            cityInput = $("#city"),
            submitButton = $("#submit");

    ElementsCollection hobbiesCheckBoxes = $$("#hobbiesWrapper .custom-checkbox");

    //@Step("Открытие страницы")
    public RegistrationFormPage openPage() {
        // open page and check header form
        open(PATH);
        $(".practice-form-wrapper").shouldHave(Condition.text(TITLE_TEXT));
        // close advertising footer
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
        return this;
    }

    //@Step("Ввод имени")
    public RegistrationFormPage setFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    //@Step("Ввод фамилии")
    public RegistrationFormPage setLastName(String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    //@Step("Ввод email")
    public RegistrationFormPage setUserEmail(String userEmail) {
        userEmailInput.setValue(userEmail);
        return this;
    }

    //@Step("Выбор пола")
    public RegistrationFormPage setGender(String gender) {
        genderRadioButton.$(byText(gender)).click();
        return this;
    }

    //@Step("Ввод номера телефона")
    public RegistrationFormPage setUserNumber(String number) {
        //userNumberInput.setValue(Keys.PAGE_DOWN);
        userNumberInput.setValue(number);
        return this;
    }

    //@Step("Выбор дня рождения")
    public RegistrationFormPage setBirthday(Date birthday) {
        dateOfBirthInput.click(ClickOptions.usingJavaScript());
        calendarComponent.setDate(birthday);
        return this;
    }

    //@Step("Выбор предмета")
    public String setSubject() {
        subjectsInput.click(ClickOptions.usingJavaScript());
        subjectsInput.setValue("a");
        ElementsCollection temp = $$("[class*=subjects-auto-complete__option]");
        String subject = temp.get(new Random().nextInt(temp.size())).getText();
        temp.findBy(Condition.text(subject)).click();
        return subject;
    }

    //@Step("Выбор хобби")
    public RegistrationFormPage setHobbies(String ... hobbies) {
        ElementsCollection temp = $$("#hobbiesWrapper .custom-checkbox");
        for (String hobby: hobbies) {
            temp.findBy(Condition.text(hobby)).click();
        }
        return this;
    }

    //@Step("Ввод адреса")
    public RegistrationFormPage setAddress(String address) {
        currentAddressInput.setValue(address);
        return this;
    }

    //@Step("Выбор иконки")
    public RegistrationFormPage uploadFile(String filePath) {
        //pictureChooseWindow.uploadFromClasspath("stitch.jpg"); - доп. вариант загрузки из папки resources
        pictureChooseWindow.uploadFile(new File(filePath));
        return this;
    }

    //@Step("Выбор штата")
    public RegistrationFormPage setState(String state) {
        stateInput.click();
        $(byText(state)).click();
        return this;
    }

    //@Step("Выбор города")
    public RegistrationFormPage setCity(String city) {
        cityInput.click();
        $(byText(city)).click();
        return this;
    }

    //@Step("Отправка заполненной формы")
    public RegistrationFormPage submit() {
        submitButton.click();
        return this;
    }

    //@Step("Проверка отображения таблицы с результатами")
    public RegistrationFormPage checkResultsTableVisible() {
        resultsModal.checkVisible();
        return this;
    }

    //@Step("Проверка полей внутри таблицы с результатами")
    public RegistrationFormPage checkResultsTable(String key, String value) {
        resultsModal.checkResult(key, value);
        return this;
    }
}

