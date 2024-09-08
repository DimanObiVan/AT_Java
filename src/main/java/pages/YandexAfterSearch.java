package pages;

import helpers.Assertions;
import helpers.Scroller;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;


import static helpers.Assertions.softAssert;
import static helpers.CustomWaits.fluentWait;
import static helpers.CustomWaits.fluentWaitInvisibleIfLocated;
import static java.lang.Thread.sleep;

public class YandexAfterSearch {

    public WebDriverWait wait;

    private String minValueRange = "//div[@data-filter-id='glprice']//span[contains(@data-auto, 'filter-range-min')]//input";
    private String maxValueRange = "//div[@data-filter-id='glprice']//span[contains(@data-auto, 'filter-range-max')]//input";
    private String manufacturerFilter = "//div[contains(@data-zone-data,'Производитель')]";
    private String manufacturerFilterValue = ".//div[contains(@data-zone-data,'";
    private String notebookElement = "//div[@data-auto='SerpList']/child::div";
    private String itemName = "//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name']";
//    private String itemPrice = "//div[@data-apiary-widget-name='@light/Organic']//div[@data-auto='snippet-price-current']";
    private String itemPrice = "//div[@data-baobab-name='price']//span[contains(@class,'headline')]";
    private String pageXpath = "//body";
    private String firstNotebookXpath = "(//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name'])[1]";
    private String searchField = "//input[@id='header-search']";
    private String searchButton = "//button[@data-auto='search-button']";
//    private String filter = "//div[@data-zone-name='QuickFilters']//div[contains(@data-zone-data, '\"filterName\":\"Производитель\"')]";
    private String notebookFoundPath = "//div[@data-auto='SerpList']//*[text()='";
    private String forStaleness = "//div[@data-apiary-widget-name='@marketfront/SerpEntity'][2]";
    private String endOfXpath = "')]";


   // private List<String> manufacturer = List.of("HP", "Lenovo", "Acer", "ASUS");
    public WebDriver chromedriver;


    public YandexAfterSearch(WebDriver chromedriver) {
//        super();
        this.chromedriver = chromedriver;
        wait = new WebDriverWait(chromedriver, 10);
    }

    public void setPriceRange(int minValue, int maxValue) {
        WebElement minValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(minValueRange)));
        minValueField.sendKeys(Integer.toString(minValue));
        WebElement maxValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(maxValueRange)));
        maxValueField.sendKeys(Integer.toString(maxValue));
    }


    public void manufacturersList(List<String> values) throws InterruptedException {
       WebElement filter = chromedriver.findElement(By.xpath(manufacturerFilter));
//       wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(manufacturerFilter)));
        /**
         * Итерируемся по всем указанным производителям
         */
        for (String value : values) {
            /**
             * Ждем появление кнопки Показать еще и нажимаем
             */
            fluentWait(chromedriver)
                    .until(ExpectedConditions
                            .elementToBeClickable(filter
                                    .findElement(By.xpath(".//button"))));
            WebElement button = filter.findElement(By.xpath(".//button"));
            button.click();
            /**
             * Ждем появление поля для ввода и вводим название
             */
            fluentWait(chromedriver)
                    .until(ExpectedConditions
                            .elementToBeClickable(filter
                                    .findElement(By.xpath(  ".//input"))));
            WebElement input = filter.findElement(By.xpath(".//input"));
            input.sendKeys(value);
            /**
             * Ждем появление фильтра по названию и отмечаем чекбокс
             */
            fluentWait(chromedriver)
                    .until(ExpectedConditions
                            .elementToBeClickable(filter
                                    .findElement(By.xpath(manufacturerFilterValue + value + endOfXpath))));
            WebElement element = filter.findElement(By.xpath(manufacturerFilterValue + value + endOfXpath));
            element.click();
//            fluentWait(chromedriver).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-apiary-widget-name='@search/SerpStatic']")));
            WebElement stale = chromedriver.findElement(By.xpath(forStaleness));
            wait.until(ExpectedConditions.stalenessOf(stale));
//            Thread.sleep(2000);
            }
        }




    public void assertElementsMoreThanValue(int number) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notebookElement)));
            List<WebElement> elements = chromedriver.findElements(By.xpath(notebookElement));
            Assertions.assertTrue(elements.size()>number, "Менее " + number + " элементов на странице");
        }

    /**
     * Надо доработать ожидание загрузки страницы, скролл, проверку по регистру для названий
     * @throws InterruptedException
     */
    public void assertElementsMatchFilter(int minValue, int maxValue, List<String> values) throws InterruptedException {
        sleep(3000);
        Scroller.scroll(pageXpath, chromedriver);
        //sleep(30000);
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
//        List<String> MatchingNames = name.stream()
//                .filter(names -> values.stream().anyMatch(names::contains))
//                .collect(Collectors.toList());
//        System.out.println("удовлетворяют условиям: " + MatchingNames);
        List<Integer> nonMatchingPrice = price.stream()
                .filter(prices->prices<= minValue || prices >=maxValue)
                .collect(Collectors.toList());
        System.out.println("НЕ удовлетворяют условиям по цене: " + nonMatchingPrice);
        List<String> nonMatchingNames = name.stream()
                .filter(names -> values.stream().noneMatch(names::contains))
                .collect(Collectors.toList());
        System.out.println("Не удовлетворяют условиям по названию: " + nonMatchingNames);

        for (String names : name) {
            boolean matches = values.stream()
                    .anyMatch(manufacturerName -> names.toLowerCase().contains(manufacturerName.toLowerCase()));

            if (!matches) {
                System.out.println("Не найдено соответствие для: " + names);
            }
        }
        /**
         * Проверка на соответствие названию
         */
        softAssert(
                name.stream().allMatch(names ->
                        values.stream().anyMatch(manufacturerName ->
                                names.toLowerCase().contains(manufacturerName.toLowerCase())
                        )
                ),
                "Не соответствуют фильтру по названию: " + nonMatchingNames
        );
        /**
         * Проверка на соответствие цене
         */
        softAssert(price.stream()
                .allMatch(prices->prices >= minValue && prices <= maxValue), "Не соответствуют фильтру по цене " + nonMatchingPrice);

    }

    /**
     * возможно тут надо разделить на 2 метода (перейти на верх страницы и найти первый ноутбук
     * @return
     */
    public String goToThePageTop() {
        chromedriver.findElement(By.xpath(pageXpath)).sendKeys(Keys.HOME);
        WebElement firstNotebook = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstNotebookXpath)));
        return firstNotebook.getText();
    }

    public void findFirstNotebook() {
       WebElement searchfield = chromedriver.findElement(By.xpath(searchField));
       searchfield.sendKeys(goToThePageTop());
    }

    public void searchButtonClick(){
        chromedriver.findElement(By.xpath(searchButton)).click();
    }
    public void assertNotebookIsFound(){
      WebElement notbokk = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notebookFoundPath+goToThePageTop()+"']")) );
        Assertions.assertEquals(notbokk.getText(), goToThePageTop(), "Отсутствует искомый товар");
    }



    }


