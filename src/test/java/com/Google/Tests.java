package com.Google;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Класс с тестом (Д. К. Кузнецов)
 */

public class Tests extends BaseTest {

    /**
     * Тест кейс №1.1 с помощью Page Object (Д. К. Кузнецов)
     */
    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PO")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @CsvSource(value = {"Гладиолус| wikipedia.org|//div[contains(@class,'g')]//a[contains(@href,'https://ru.wikipedia.org')]"}, delimiter = '|')
    public void testCase_1_1_PO(String word, String result, String path) {
        chromeDriver.get("https://www.google.com/");
        BasePage page = new BasePage(chromeDriver);
        page.find(word);
        Assertions.assertTrue(page.getResults(path).stream().anyMatch(x->x.getText().contains(result)), "No wiki link found!");


    }
    /**
     * Тест кейс №1.1 с помощью Page Factory (Д. К. Кузнецов)
     */
    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PF")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @CsvSource({"Гладиолус, wikipedia.org"})
    public void testCase_1_1_PF(String word, String result){
        chromeDriver.get("https://www.google.com/");
        GooglePageFactory page = PageFactory.initElements(chromeDriver,GooglePageFactory.class);
        page.find(word);
        Assertions.assertTrue(page.getResults().stream().anyMatch(x->x.getText().contains(result)),
                "No wiki link found!");
    }
//    @Feature("Проверка результатов поиска")
//    @DisplayName("Проверка результатов поиска c помощью PO")
//    @ParameterizedTest(name="{displayName}: {arguments}")
//    @CsvSource({"Открытие, wikipedia.org"})
//    public void testCase_1_1_PO(String word, String result) {
//        chromeDriver.get("https://www.google.com/");
//        BasePage page = new BasePage(chromeDriver);
//        page.find(word);
//        Assertions.assertTrue(page.getResults().stream().anyMatch(x->x.getText().contains(result)), "No wiki link found!");
//
//
//    }

//    @Test
//    public void testCase_1_2() {
//        chromeDriver.get("https://www.google.com/");
//        BasePage page = new BasePage(chromeDriver);
//        page.setOpenInNewTab();
//        /**
//         * Тест
//         */
//        WebDriverWait wait = new WebDriverWait(chromeDriver, 45);
//        WebElement searchField = chromeDriver.findElement(By.xpath("//textarea[@aria-label='Найти']"));
//        searchField.sendKeys("Открытие");
//        WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@jsname, 'VlcLAe')]//input[1]")));
//        searchButton.click();
//        List<WebElement> resultSearch = chromeDriver.findElements(By.xpath("//*[@href='https://www.open.ru/']/h3"));
//        resultSearch.forEach(x-> System.out.println(x.getText()));
//        Assertions.assertTrue(resultSearch.stream().anyMatch(x->x.getText().contains("Банк Открытие: кредит наличными, ипотека, кредитные и ...")), "No text found");
//        WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@href='https://www.open.ru/']/..")));
//        link.click();
//        ArrayList<String> tabs = new ArrayList<> (chromeDriver.getWindowHandles());
//        chromeDriver.switchTo().window(tabs.get(1));
//        WebElement resultSearch = chromeDriver.findElement(By.xpath("//*[@data-block-id='exchange']"));
//        Assertions.assertTrue(resultSearch.isDisplayed(), "No element!");
//        WebElement closeCookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'CookieWarning_cookie-warning-button__XaO44')]")));
//        closeCookie.click();
//        WebElement allCourses = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Все курсы']")));
//        allCourses.click();
//        List<WebElement> resultCourse = chromeDriver.findElements(By.xpath("//section[@class='section section--white currency-exchange' and not(contains(@style, 'display: none;'))]//tr[@class='card-rates-table__row']"));
//        for (WebElement element : resultCourse) {
//            System.out.println(element);
//        }
//        System.out.println(resultCourse.size());
//        Assertions.assertTrue(resultCourse.size()>=3, "Less elements");

//        WebElement usdBuyCourse = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'USD')]/../../following-sibling::td[1]")));
//        WebElement usdSellCourse = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'USD')]/../../following-sibling::td[2]")));
//        double usdBuyCourseValue = Double.parseDouble(usdBuyCourse.getText().replace(",", "."));
//        double usdSellCourseValue = Double.parseDouble(usdSellCourse.getText().replace(",", "."));
//        Assertions.assertTrue(usdBuyCourseValue<usdSellCourseValue, "Курс покупки для долларов больше курса продажи доллара!");
//
//        WebElement eurBuyCourse = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'EUR')]/../../following-sibling::td[1]")));
//        WebElement eurSellCourse = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'EUR')]/../../following-sibling::td[2]")));
//        double eurBuyCourseValue = Double.parseDouble(eurBuyCourse.getText().replace(",", "."));
//        double eurSellCourseValue = Double.parseDouble(eurSellCourse.getText().replace(",", "."));
//        Assertions.assertTrue(eurBuyCourseValue<eurSellCourseValue, "Курс покупки для евро больше курса продажи евро!");


//    }
    /**
     * Тест кейс №1.2 с помощью Page Object (Д. К. Кузнецов)
     */
    @Feature("ТК 1.2")
    @DisplayName("Проверка ТК 1.2 c помощью PO")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @CsvSource(value = {"Открытие|Банк Открытие: кредит наличными, ипотека, кредитные и ...|" +
            "//*[contains(text(), 'Банк Открытие: кредит наличными, ипотека, кредитные и ...')]|" +
            "//*[@href='https://www.open.ru/']/.."},
            delimiter = '|')
    public void testCase_1_2_PO(String word, String phrase, String xPath1, String xPath2) {
        chromeDriver.get("https://www.google.com/");
        BasePage page = new BasePage(chromeDriver);
        page.setOpenInNewTab();
        BasePage anotherPage = new BasePage(chromeDriver);
        anotherPage.find(word);
        Assertions.assertTrue(page.getResults(xPath1).stream().anyMatch(x->x.getText().contains(phrase)),
                "Отсутствует заголовок с текстом Банк Открытие: кредит наличными, ипотека, кредитные и ...");
        anotherPage.openingNewTab(xPath2);
        OpenBankMainPage mainPage = new OpenBankMainPage(chromeDriver);
        Assertions.assertTrue(mainPage.areCoursesPresent(), "No element!");
        mainPage.openCourses();
        BankCoursesPage coursesPage = new BankCoursesPage(chromeDriver);
        Assertions.assertTrue(coursesPage.getResults().size()>3, "На странице менее 3 курсов валют!");
        Assertions.assertTrue(coursesPage
                .convertCurrencyElementsToMap()
                .get("USD Buy")
                <
                    coursesPage
                        .convertCurrencyElementsToMap()
                            .get("USD Sell"), "Курс покупки для долларов больше курса продажи доллара!");
        Assertions.assertTrue(coursesPage
                .convertCurrencyElementsToMap()
                .get("EUR Buy")
                <
                coursesPage
                        .convertCurrencyElementsToMap()
                        .get("EUR Sell"), "Курс покупки для евро больше курса продажи евро!");


    }
    /**
     * Тест кейс №1.3 с помощью Page Object, требует рефакторинга (Д. К. Кузнецов)
     */
    @Feature("ТК 1.3")
    @DisplayName("Проверка ТК 1.3 c помощью PO")
    @ParameterizedTest(name="{displayName}: {arguments}")
    @CsvSource(value = {"таблица википедия|Таблица|//h3[text()='Таблица']|//h3[text()='Таблица']"},
            delimiter = '|')
    public void testCase_1_3_PO(String word, String phrase, String xPath1, String xPath2) {
        chromeDriver.get("https://www.google.com/");
        BasePage page = new BasePage(chromeDriver);
        page.setOpenInNewTab();
        BasePage anotherPage = new BasePage(chromeDriver);
        anotherPage.find(word);
        Assertions.assertTrue(page.getResults(xPath1).stream().anyMatch(x -> x.getText().contains(phrase)),
                "Отсутствует заголовок с текстом Таблица");
        anotherPage.openingNewTab(xPath2);
        TablePage tablePage = new TablePage(chromeDriver);
        List<Map<String,String>> teachersNames = tablePage.getNamesTable();
        System.out.println(teachersNames);
        String firstPerson = teachersNames.stream()
                .findFirst()
                .map(x->x.get("Имя")+ " " + x.get("Отчество"))
                        .orElse("Не найдено");
        String lastPerson = teachersNames.stream()
                        .skip(teachersNames.size()-1)
                                .findFirst()
                                        .map(x->x.get("Имя")+ " " + x.get("Отчество"))
                                                .orElse("Не найдено");
        Assertions.assertTrue(firstPerson.equals("Сергей Владимирович") && lastPerson.equals("Сергей Адамович"), "Неверный порядок учителей");
    }
}
