package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Класс для взаимодействия с главной страницей ЯндексМаркет.
 *
 * Содержит методы для работы с каталогом, навигацией по категориям и разделам сайта.
 * В тестах используется для открытия каталога и проверки правильности переходов.
 *
 * <p><b>Автор:</b> Кузнецов Д.К.</p>
 */
public class YandexMainPage {
    /** Объект WebDriver для управления браузером */
    private WebDriver chromeDriver;
    /** Явное ожидание для поиска элементов */
    private WebDriverWait wait;
    /** XPath кнопки открытия каталога */
    private String catalogue = "//div[@data-zone-name='catalog']/button";
    /** XPath для поиска раздела товаров */
    private String categories = "//li[not(@data-baobab-name='department')]//*[contains(text(),'";
    /** XPath для поиска категории */
    private String itemsList = "//ul[@data-autotest-id='subItems']//*[contains(text(),'";
    /** XPath для поиска названия открытой категории */
    private By categoryName = By.xpath("//h1[@data-auto='title']");
    /**
     * Конструктор класса YandexMainPage.
     *
     * @param chromeDriver объект WebDriver, используемый для управления браузером.
     */
    public YandexMainPage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.wait = new WebDriverWait(chromeDriver, 30);
    }

    /**
     * Открывает каталог на главной странице.
     */
    public void catalogueOpen() {
        WebElement catalogueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(catalogue)));
//        WebElement catalogueButton = chromeDriver.findElement(By.xpath(catalogue));
        catalogueButton.click();
    }
    /**
     * Наводит курсор на указанную категорию и выбирает указанный элемент в списке.
     *
     * @param category название категории товаров (например, "Электроника").
     * @param item название конкретного раздела в категории (например, "Ноутбуки").
     *
     * <p>Метод сначала наводит курсор на категорию, а затем кликает на указанный элемент.
     * После клика проверяется, что заголовок страницы содержит название выбранного раздела.</p>
     *
     * <p>Ассерт проверяет, что заголовок страницы совпадает с ожидаемым значением.</p>
     */
    public void hoverOverAndClick(String category, String item){
        Actions actions = new Actions(chromeDriver);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(categories+category+"')]/..")));
        actions.moveToElement(element).perform();
        WebElement notebook = chromeDriver.findElement(By.xpath(itemsList+item+"')]"));
        notebook.click();
        Assertions.assertTrue(chromeDriver.findElement(categoryName).getText().contains(item), "неверный раздел");

    }

}
