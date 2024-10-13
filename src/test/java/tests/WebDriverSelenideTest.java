package tests;

import config.WebDriverProviderSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WebDriverSelenideTest {
    private final WebDriverProviderSelenide webDriverProviderSelenide = new WebDriverProviderSelenide();

    @BeforeEach
    public void setUp() {
        webDriverProviderSelenide.createWebDriver();
    }

    @Tag("selenide")
    @Test
    public void testGithub() {
        open(webDriverProviderSelenide.config.getBaseUrl());
        String title = title();
        assertEquals("GitHub: Let’s build from here · GitHub", title);
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}