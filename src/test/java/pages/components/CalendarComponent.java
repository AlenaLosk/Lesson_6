package pages.components;

import com.codeborne.selenide.SelenideElement;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {
    SelenideElement yearCalendar = $("[class*='year-select']"),
            monthCalendar = $("[class*='month-select']");

    public CalendarComponent setDate (Date date) {
        String year = String.valueOf(1900 + date.getYear());
        String month = Month.of(date.getMonth() + 1).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String day = String.valueOf(date.getDate());
        setBirthdayYear(year);
        setBirthdayMonth(month);
        setBirthdayDay(day);
        return this;
    }

    private void setBirthdayYear(String year) {
        yearCalendar.selectOption(year);
    }

    private void setBirthdayMonth(String month) {
        monthCalendar.selectOption(month);
    }

    private void setBirthdayDay(String day) {
        $(String.format("[class*='day--%s']:not([class*='outside-month'])",
                String.format("%03d", Integer.parseInt(day)))).click();
    }
}
