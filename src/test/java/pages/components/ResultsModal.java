package pages.components;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class ResultsModal {

    private final static String TITLE_TEXT = "Thanks for submitting the form";

    public ResultsModal checkVisible() {
        $(".modal-dialog").should(Condition.appear);
        $(".modal-title").shouldHave(Condition.text(TITLE_TEXT));
        return this;
    }

    public ResultsModal checkResult(String key, String value) {
        $(".table-responsive table").$(byText(key)).parent().shouldHave(Condition.text(value));
        return this;
    }
}
