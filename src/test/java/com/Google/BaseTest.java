package com.Google;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static helpers.CustomWaits.implicitlyWait;
import static helpers.Properties.testsProperties;

/**
 * Класс с методами (Д. К. Кузнецов)
 */
public class BaseTest {
    protected WebDriver chromeDriver;

    /**
     * Аннотация для выполнения подготовки перед каждым тестом
     */
    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        implicitlyWait(chromeDriver,testsProperties.defaultTimeout());
        chromeDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        chromeDriver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);



    }

    /**
     * Аннотация для закрытия браузера после каждого теста
     */
    @AfterEach
    public void after() {
        chromeDriver.quit();
    }
}
