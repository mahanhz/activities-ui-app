package com.amhzing.activities.ui.functionaltest.ui;

import com.amhzing.activities.ui.ActivitiesUIApplication;
import com.amhzing.activities.ui.properties.TestServerProperties;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivitiesUIApplication.class)
@EnableConfigurationProperties(TestServerProperties.class)
@Ignore("Ignoring Selenide test as it is difficult to get it to run in Jenkins")
public class LoginUITest {

    @Autowired
    private TestServerProperties testServerProperties;

    @Before
    public void setUp() {
        open(getUrl() + "/login");
    }

    @Test
    public void should_login_valid_user() {
        givenLoginCredentials("vaadin", "p");

        whenSubmitting();

        thenUserIsRedirectedTo("/vaadin");
    }

    @Test
    public void should_login_valid_admin() {
        givenLoginCredentials("admin", "p");

        whenSubmitting();

        thenUserIsRedirectedTo("/togglz-console");
    }

    @Test
    public void should_not_login_invalid_user() {
        givenLoginCredentials("invalid", "invalid");

        whenSubmitting();

        $("#loginModal").shouldHave(text("Invalid login"));
        thenUserIsRedirectedTo("/login?error");
    }

    private void givenLoginCredentials(final String vaadin, final String p) {
        $(By.name("username")).setValue(vaadin);
        $(By.name("password")).setValue(p);
    }

    private void whenSubmitting() {
        $(By.cssSelector("button[type='submit']")).click();
    }

    private void thenUserIsRedirectedTo(final String uri) {
        assertThat(url()).startsWith(getUrl() + uri);
    }

    private String getUrl() {
        return testServerProperties.getBaseUri()
                + ":"
                + testServerProperties.getPort()
                + testServerProperties.getContextPath();
    }
}
