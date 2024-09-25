package ui.tests;

import base.BaseUiTest;
import helpers.ConfigLoader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginTestSelenide extends BaseUiTest {

    @BeforeMethod
    @Parameters({"environment"})
    public void clearLoginForm(@Optional("local") String environment) {
        ConfigLoader configLoader = new ConfigLoader();
        String baseUrl = configLoader.getBaseUrl(environment);

        open(baseUrl + "/login");
        $("#form-username").clear();
        $("#form-password").clear();
    }

    @Test
    @Parameters({"environment"})
    public void positiveLoginTestSelenide(@Optional("local") String environment) {
        $("#form-username").setValue("admin1");
        $("#form-password").setValue("admin1");
        $x("//button[text()='Sign in']").click();
        $(".title").shouldHave(text("Dashboard for admin"));
    }

    @Test
    @Parameters({"environment"})
    public void negativeLoginTest_InvalidUsername_Selenide(@Optional("local") String environment) {
        $("#form-username").setValue("wrongUser");
        $("#form-password").setValue("admin1");
        $x("//button[text()='Sign in']").click();
        $("p.alert.alert-error").shouldHave(text("Bad username or password"));
    }

    @Test
    @Parameters({"environment"})
    public void negativeLoginTest_InvalidPassword_Selenide(@Optional("local") String environment) {
        $("#form-username").setValue("admin1");
        $("#form-password").setValue("wrongPass");
        $x("//button[text()='Sign in']").click();
        $("p.alert.alert-error").shouldHave(text("Bad username or password"));
    }

}
