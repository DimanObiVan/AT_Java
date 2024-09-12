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
    private String itemPrice = "//div[@data-baobab-name='price']//span[contains(@class,'headline')]";
    private String pageXpath = "//body";
    private String firstNotebookXpath = "(//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name'])[1]";
    private String searchField = "//input[@id='header-search']";
    private String searchButton = "//button[@data-auto='search-button']";
    private String notebookFoundPath = "//div[@data-auto='SerpList']//*[text()=\"";
    private String forStaleness = "//div[@data-apiary-widget-name='@marketfront/SerpEntity'][2]";
    private String endOfXpath = "')]";

    public WebDriver chromedriver;


    public YandexAfterSearch(WebDriver chromedriver) {
        this.chromedriver = chromedriver;
        wait = new WebDriverWait(chromedriver, 10);
    }

    public void setPriceRange(int minValue, int maxValue) {
        WebElement minValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(minValueRange)));
        minValueField.sendKeys(Integer.toString(minValue));
        WebElement maxValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(maxValueRange)));
        maxValueField.sendKeys(Integer.toString(maxValue));
    }

    /**
     *
     * @param values - список производителей (Кузнецов)
     */
    public void manufacturersList(List<String> values) {
 //      WebElement filter = chromedriver.findElement(By.xpath(manufacturerFilter));
        WebElement filter = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(manufacturerFilter)));
        for (String value : values) {
             //Ждем появление кнопки Показать еще и нажимаем
            fluentWait(chromedriver)
                    .until(ExpectedConditions
                            .elementToBeClickable(filter
                                    .findElement(By.xpath(".//button"))));
            WebElement button = filter.findElement(By.xpath(".//button"));
            button.click();
             //Ждем появление поля для ввода и вводим название
            fluentWait(chromedriver)
                    .until(ExpectedConditions
                            .elementToBeClickable(filter
                                    .findElement(By.xpath(  ".//input"))));
            WebElement input = filter.findElement(By.xpath(".//input"));
            input.sendKeys(value);
             //Ждем появление фильтра по названию и отмечаем чекбокс
            fluentWait(chromedriver)
                    .until(ExpectedConditions
                            .elementToBeClickable(filter
                                    .findElement(By.xpath(manufacturerFilterValue + value + endOfXpath))));
            WebElement element = filter.findElement(By.xpath(manufacturerFilterValue + value + endOfXpath));
            element.click();
            //ждем, когда прогрузится список
            WebElement stale = chromedriver.findElement(By.xpath(forStaleness));
            wait.until(ExpectedConditions.stalenessOf(stale));
            }
        }


    /**
     * Проверка того, что на странице более number элементов товаров
     * @param number - число элементов (Кузнецов)
     */
    public void assertElementsMoreThanValue(int number) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notebookElement)));
            List<WebElement> elements = chromedriver.findElements(By.xpath(notebookElement));
            Assertions.assertTrue(elements.size()>number, "Менее " + number + " элементов на странице");
        }

    /**
     * Метод проверки на соответствие фильтру
     * @param  minValue - цена от
     * @param maxValue - цена до
     * @param values - список производителей (Кузнецов)
     * @throws InterruptedException
     */
    public void assertElementsMatchFilter(int minValue, int maxValue, List<String> values) throws InterruptedException {
        Scroller.scrollWithJS(chromedriver);

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
         * Проверка на соответствие названию (Кузнецов)
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
         * Проверка на соответствие цене (Кузнецов)
         */
        softAssert(price.stream()
                .allMatch(prices->prices >= minValue && prices <= maxValue), "Не соответствуют фильтру по цене " + nonMatchingPrice);

    }

    /**
     * Метод перемещения на верх страницы (Кузнецов)
     */
    public void goToThePageTop() {
        chromedriver.findElement(By.xpath(pageXpath)).sendKeys(Keys.HOME);
    }

    /**
     * Функция которая находит первый ноутбук, берет его название, вводит в строку поиска и жмет Найти. Также запоминает название
     * @return firstNotebookName - название ноутбука (Кузнецов)
     */
    public String findFirstNotebook() {
       WebElement searchfield = chromedriver.findElement(By.xpath(searchField));
       WebElement firstNotebook = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstNotebookXpath)));
        String firstNotebookName = firstNotebook.getText();
        searchfield.sendKeys(firstNotebook.getText());
        chromedriver.findElement(By.xpath(searchButton)).click();
        return firstNotebookName;
    }

    /**
     * Метод проверки наличия ноутбука на странице
     * @param firstNotebookName - название ноутбука (Кузнецов)
     */
//    public void assertNotebookIsFound(String firstNotebookName){
//      WebElement notbokk = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notebookFoundPath+firstNotebookName+"']")) );
////        List<WebElement> notbokk = chromedriver.findElements(By.xpath(notebookFoundPath+firstNotebookName+"']"));
//        softAssert(notbokk.getText(), firstNotebookName, "Отсутствует искомый товар");
//    }

    public void assertNotebookIsFound(String firstNotebookName) {
        // Используем ExpectedConditions для поиска элемента без выброса исключения
        List<WebElement> notebooks = chromedriver.findElements(By.xpath(notebookFoundPath + firstNotebookName + "\"]"));

        // Проверяем, что список не пустой (элемент найден)
        if (!notebooks.isEmpty()) {
            // Выполняем мягкую проверку, если элемент найден
            Assertions.assertEquals(notebooks.get(0).getText(), firstNotebookName, "Отсутствует искомый товар");
        } else {
            // Выполняем мягкую проверку, если элемент не найден
            Assertions.assertEquals(null, firstNotebookName, "Элемент не найден: отсутствует искомый товар");
        }
    }



    }


