package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class YandexAfterSearch {

    public WebDriverWait wait;

    private String minValueRange = "//div[@data-filter-id='glprice']//div[contains(@data-zone-data, 'valueFrom')]//input";
    private String maxValueRange = "//div[@data-filter-id='glprice']//div[contains(@data-zone-data, 'valueTo')]//input";
    private String manufacturerFilter = "//div[contains(@data-zone-data,'Производитель')]";
    private String manufacturerFilterValue = "//div[contains(@data-zone-data,'";
    private String notebookElement = "//div[@data-auto='SerpList']/child::div";
    private String itemName = "//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name']";
    private String itemPrice = "//div[@data-apiary-widget-name='@light/Organic']//div[@data-auto='snippet-price-current']";
    int minValue = 10000;
    int maxValue = 30000;
//    private String filter = "//div[@data-zone-name='QuickFilters']//div[contains(@data-zone-data, '\"filterName\":\"Производитель\"')]";


    private List<String> manufacturer = List.of("BenQ", "HP", "Lenovo", "Acer", "ASUS");
    public WebDriver chromedriver;


    public YandexAfterSearch(WebDriver chromedriver) {
//        super(chromedriver);
        this.chromedriver = chromedriver;
        wait = new WebDriverWait(chromedriver, 10);
    }

    public void setPriceRange(String minValue, String maxValue) {
        WebElement minValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(minValueRange)));
        minValueField.sendKeys(minValue);
        WebElement maxValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(maxValueRange)));
        maxValueField.sendKeys(maxValue);
    }

    public /*List<String>*/ void manufacturersList(String... values) {
        List<WebElement> manufacturer = new ArrayList<>();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(manufacturerFilter)));
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath( "//div[contains(@data-zone-data,'Производитель')]//button")));
            button.click();
//            Actions a = new Actions(chromedriver);
            for (String value : values) {
                try {

                    WebElement element = chromedriver.findElement(By.xpath(manufacturerFilter + manufacturerFilterValue + value + "')]"));
                    if (!element.isDisplayed()) {
                        button.click();
                        element.sendKeys(Keys.END);
                        System.out.println("Нажал кнопку тк нет элемента");
                       wait.until(ExpectedConditions.visibilityOf(element));
                        break;
                    }
                        manufacturer.add(element);
                        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                        System.out.println("Элемент найден и добавлен: " + value);

                } catch (Exception e) {
                    System.out.println("Не найден элемент: " + value);
                }

            }
 //           return manufacturer;
        }

        public void assertElementsMoreThanTwelve() {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notebookElement)));
            List<WebElement> elements = chromedriver.findElements(By.xpath(notebookElement));
            Assertions.assertTrue(elements.size()>12, "Менее 12 эелементов на странице");
        }
    public void assertElementsMatchFilter() throws InterruptedException {
        sleep(3000);
     //   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(filter)));
        List<String> name = chromedriver.findElements(By.xpath(itemName))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        System.out.println(name);
        List<Integer> price = chromedriver.findElements(By.xpath(itemPrice))
                .stream()
                .map(WebElement::getText)
                .map(prices->prices.replaceAll("[^\\d.]", ""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        System.out.println(price);

        Assertions.assertTrue(name.stream()
                .allMatch(names->manufacturer.stream().anyMatch(names::contains)), "Не все элементы соответствуют фильтру по названию!");

        Assertions.assertTrue(price.stream()
                .allMatch(prices->prices >= minValue && prices <= maxValue), "Не все элементы соответствуют фильтру по цене!");

    }


    }

