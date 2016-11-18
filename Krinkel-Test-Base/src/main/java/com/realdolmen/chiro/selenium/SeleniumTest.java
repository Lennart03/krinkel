package com.realdolmen.chiro.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assume.assumeTrue;

/**
 * Abstract base class to setup the WebDriver
 * for Selenium Testing. Selenium tests are
 * disabled by default. You can enable them
 * with the -Dselenium flag.
 *
 * Use the driver() method to access the WebDriver
 * in subclasses.
 *
 * Before running the Selenium Tests make sure you have
 * the ChromeDriver correctly installed.
 * Download it from https://chromedriver.storage.googleapis.com/index.html?path=2.25/
 * (Compatible with the current version of Chrome, v54)
 * Extract the chromedriver.exe file into the root of your C:\ drive.
 * The base class expects the Chrome Driver to be at C:\chromedriver.exe
 *
 * Should the test open the browser window but then fail with a connection time out,
 * then check if your version of the Chrome is compatible with the version of chromedriver.
 *
 */
public abstract class SeleniumTest {

    private static String DRIVER_PATH = "C:\\chromedriver.exe";
    private static WebDriver driver;

    private String baseUrl = "http://localhost:8080";

    private static final String SELENIUM_ENABLED_SYSTEM_PROPERTY = "selenium";

    @BeforeClass
    public static void setUp(){
        verifySeleniumEnablingPreConditions();
        initializeWebDriver();
    }

    private static void verifySeleniumEnablingPreConditions(){
        boolean isSeleniumEnabled = (System.getProperty(SELENIUM_ENABLED_SYSTEM_PROPERTY) != null);
        assumeTrue("Selenium testing is disabled", isSeleniumEnabled);
    }

    private static void initializeWebDriver() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage()
                .window()
                .maximize();
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected WebDriver driver() {
    return driver;
  }

    protected String getBaseUrl() {
        return baseUrl;
    }
}
