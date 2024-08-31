package com.Google;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.YandexAfterSearch;
import pages.YandexMainPage;

import static steps.StepsAll.*;
import static steps.StepsForYandex.*;

public class YandexMarketTests extends BaseTest {

    @Test
    public void yandexTest() throws InterruptedException {
        openYandex("https://market.yandex.ru/", chromeDriver);
        catalogueOpen();
        hoverOver("Электроника", "Ноутбуки");
        setPriceRange("10000", "30000");
        manufacturersList("BenQ", "HP", "Lenovo", "Acer", "ASUS");
        countItems();
        assertElementsMatchFilter();
    }
}
