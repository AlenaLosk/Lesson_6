import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import helpers.CredentialsConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TestBase {
    static String selenoidRemote = System.getProperty("selenoid_remote");

    @BeforeAll
    public static void config() {
        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;

        Configuration.baseUrl = System.getProperty("base_url", "https://demoqa.com");
        Configuration.browser = System.getProperty("browser_name", "chrome");
        Configuration.browserVersion = System.getProperty("browser_version", "101");
        Configuration.browserSize = System.getProperty("browser_size","1920x1080");
        if (selenoidRemote != null) {
            Configuration.remote = "https://" + config.loginSelenoid() + ":" + config.passwordSelenoid() + "@" + selenoidRemote;
        }
        SelenideLogger.addListener("allure", new AllureSelenide());


    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        if (selenoidRemote != null) {
            Attach.addVideo();
        }
    }
}
