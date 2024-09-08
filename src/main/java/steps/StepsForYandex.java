package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import pages.YandexAfterSearch;
import pages.YandexMainPage;

import java.util.ArrayList;
import java.util.List;

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
    public static void setPriceRange(int minValue, int maxValue){
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.setPriceRange(minValue, maxValue);
    }
    @Step("Выбрать производителя HP и Lenovo")
    public static void manufacturersList(List<String> values) throws InterruptedException {
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.manufacturersList(values);
    }
    @Step("Проверить, что на первой странице более 12 элементов товаров.")
    public static void countItems(int number){
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.assertElementsMoreThanValue(number);
    }
    @Step("Проверить что все предложения соответствуют фильтру.")
    public static void assertElementsMatchFilter(int minValue, int maxValue, List<String> values) throws InterruptedException {
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.assertElementsMatchFilter(minValue, maxValue, values);
    }
    @Step("Вернуться в начало списка и запомнить первое наименование ноутбука.")
    public static void goToThePageTop() {
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.goToThePageTop();
    }

    @Step("В поисковую строку ввести запомненное значение")
    public static String findFirstNotebook() {
        YandexAfterSearch page = new YandexAfterSearch(driver);
        String firstNotebookName = page.findFirstNotebook();
        return firstNotebookName;
    }
 //   @Step("Нажать кнопку «Найти»")
//    public static void searchButtonClick() {
//        YandexAfterSearch page = new YandexAfterSearch(driver);
//        page.searchButtonClick();
//    }
    @Step("Проверить, что в результатах поиска, на первой странице, есть искомый товар")
    public static void assertNotebookIsFound(String firstNotebookName)  {
        YandexAfterSearch page = new YandexAfterSearch(driver);
        page.assertNotebookIsFound(firstNotebookName);
    }







}
