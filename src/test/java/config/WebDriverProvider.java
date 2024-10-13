package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Supplier;

public class WebDriverProvider implements Supplier<WebDriver> {

    private final WebDriverConfig config;

    public WebDriverProvider() {
        this.config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());
    }

    @Override
    public WebDriver get() {
        WebDriver driver;
        try {
            driver = createDriver();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        driver.get(config.getBaseUrl());
        return driver;
    }

    public WebDriver createDriver() throws MalformedURLException {

        if (config.isRemote()) {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);

            capabilities.setBrowserName(config.getBrowser().toString());
            capabilities.setVersion(config.getBrowserVersion());

            return new RemoteWebDriver(new URL(config.getRemoteUrl()), capabilities);
        }

        System.out.println("Using browser: " + config.getBrowser());
        System.out.println("Browser version: " + config.getBrowserVersion());
        System.out.println("Base URL: " + config.getBaseUrl());

        switch (config.getBrowser()) {
            case CHROME: {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            }
            case FIREFOX: {
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            }
            default: {
                throw new RuntimeException("No such browser");
            }
        }
    }
}