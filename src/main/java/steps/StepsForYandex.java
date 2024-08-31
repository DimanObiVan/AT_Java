package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import pages.YandexAfterSearch;
import pages.YandexMainPage;

public class StepsForYandex {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @Step("Переходим на сайт: {url}")
    public static void openYandex(String url, WebDriver currentDriver){
        driver=currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver, 30);
//        wait.until(ExpectedConditions.titleIs(title));
    }

    @Step("Переходим в Каталог")
    public static void catalogueOpen(){
        YandexMainPage page = new YandexMainPage(driver);
        page.catalogueOpen();
    }
    @Step("Навести курсор на раздел Электроника и Перейти в раздел \"Ноутбуки\"")
    public static void hoverOver(String category, String item){
        YandexMainPage page = new YandexMainPage(driver);
        page.hoverOverAndClick(category, item);
    }
    @Step("Задать параметр «Цена, Р» от  10000 до 30000 рублей.")
    public static void setPriceRange(String minValue, String maxValue){
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.setPriceRange(minValue, maxValue);
    }
    @Step("Выбрать производителя HP и Lenovo")
    public static void manufacturersList(String...values){
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.manufacturersList(values);
    }
    @Step("Проверить, что на первой странице более 12 элементов товаров.")
    public static void countItems(){
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.assertElementsMoreThanTwelve();
    }
    @Step("Проверить что все предложения соответствуют фильтру.")
    public static void assertElementsMatchFilter() throws InterruptedException {
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.assertElementsMatchFilter();
    }





}
