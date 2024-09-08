package com.Google;
import helpers.DataProvider;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.YandexAfterSearch;
import pages.YandexMainPage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static steps.StepsAll.*;
import static steps.StepsForYandex.*;

public class YandexMarketTests extends BaseTest {

    @Feature("Яндекс Маркет")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @DisplayName("Тест яндекс маркета c помощью PO")
    @MethodSource("helpers.DataProvider#dataForYandexMarket")
    public void yandexTest(String category,
                           String section,
                           int minPrice,
                           int maxPrice,
                           int number,
                           List<String> values) throws InterruptedException {
        openYandex("https://market.yandex.ru/", chromeDriver);
        catalogueOpen();
        hoverOver(category, section);
        setPriceRange(minPrice, maxPrice);
        manufacturersList(values);
        countItems(number);
        assertElementsMatchFilter(minPrice, maxPrice, values);
        goToThePageTop();
       String first = findFirstNotebook();
//        searchButtonClick();
        assertNotebookIsFound(first);
    }
}
