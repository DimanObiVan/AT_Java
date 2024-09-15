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

/**
 * Класс для взаимодействия с результатами поиска на ЯндексМаркете.
 *
 * <p>Содержит методы для установки ценовых диапазонов, выбора производителей и поиска товаров
 * после применения фильтров. Класс используется для тестов на странице с результатами поиска.</p>
 *
 * <p><b>Автор:</b> Кузнецов Д.К.</p>
 */
public class YandexAfterSearch {
    /** Объект WebDriverWait для явных ожиданий элементов на странице */
    public WebDriverWait wait;
    /** XPath для минимального значения в диапазоне цен */
    private String minValueRange = "//div[@data-filter-id='glprice']//span[contains(@data-auto, 'filter-range-min')]//input";
    /** XPath для максимального значения в диапазоне цен */
    private String maxValueRange = "//div[@data-filter-id='glprice']//span[contains(@data-auto, 'filter-range-max')]//input";
    /** XPath для фильтра производителей */
    private String manufacturerFilter = "//div[contains(@data-zone-data,'Производитель')]";
    /** XPath для выбора значения производителя */
    private String manufacturerFilterValue = ".//div[contains(@data-zone-data,'";
    /** XPath для списка элементов ноутбуков на странице поиска */
    private String notebookElement = "//div[@data-auto='SerpList']/child::div";
    /** XPath для имени элемента */
    private String itemName = "//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name']";
    /** XPath для цены элемента */
    private String itemPrice = "//div[@data-baobab-name='price']//span[contains(@class,'headline')]";
    /** XPath для тела страницы */
    private String pageXpath = "//body";
    /** XPath для первого элемента в списке ноутбуков */
    private String firstNotebookXpath = "(//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name'])[1]";
    /** XPath для поля поиска */
    private String searchField = "//input[@id='header-search']";
    /** XPath для кнопки поиска */
    private String searchButton = "//button[@data-auto='search-button']";
    /** XPath для найденного ноутбука */
    private String notebookFoundPath = "//div[@data-auto='SerpList']//*[text()='";
    /** XPath списка элементов на странице (используется для проверки стейл-элементов) */
    private String forStaleness = "//div[@data-apiary-widget-name='@marketfront/SerpEntity'][2]";
    /** XPath для конца поиска элементов */
    private String endOfXpath = "')]";
    /** Объект WebDriver для управления браузером */
    public WebDriver chromedriver;

    /**
     * Конструктор класса YandexAfterSearch.
     *
     * @param chromedriver объект WebDriver, используемый для управления браузером.
     */
    public YandexAfterSearch(WebDriver chromedriver) {
        this.chromedriver = chromedriver;
        wait = new WebDriverWait(chromedriver, 10);
    }
    /**
     * Устанавливает диапазон цен на странице поиска товаров.
     *
     * @param minValue минимальная цена.
     * @param maxValue максимальная цена.
     */
    public void setPriceRange(int minValue, int maxValue) {
        WebElement minValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(minValueRange)));
        minValueField.sendKeys(Integer.toString(minValue));
        WebElement maxValueField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(maxValueRange)));
        maxValueField.sendKeys(Integer.toString(maxValue));
    }

    /**
     * Метод для работы с фильтром производителей на странице.
     * Осуществляет следующие шаги для каждого производителя из списка:
     * 1. Нажимает кнопку "Показать еще", если она доступна.
     * 2. Вводит название производителя в поле ввода.
     * 3. Ожидает появления чекбокса для производителя и отмечает его.
     * 4. Ожидает завершения загрузки товаров после применения фильтра.
     *
     * @param values список значений производителей, которые необходимо выбрать
     * @throws InterruptedException в случае, если в процессе выполнения возникли проблемы с ожиданием
     */
    public void manufacturersList(List<String> values) {
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
//        System.out.println("НЕ удовлетворяют условиям по цене: " + nonMatchingPrice);
        List<String> nonMatchingNames = name.stream()
                .filter(names -> values.stream().noneMatch(names::contains))
                .collect(Collectors.toList());
//        System.out.println("Не удовлетворяют условиям по названию: " + nonMatchingNames);

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
     * Проверка того, что в результатах поиска, на первой странице, есть искомый товар
     * @param firstNotebookName - название ноута, который ищем (Кузнецов)
     */
    public void assertNotebookIsFound(String firstNotebookName) {
        String firstNotebookName1 = firstNotebookName
                .replace("'", "\"");
        System.out.println(firstNotebookName1);
        List<WebElement> notebooks = chromedriver.findElements(By.xpath(notebookFoundPath + firstNotebookName
                .replace("'", "\"")
                + "']"));
        System.out.println(notebooks);
        // Проверяем, что список не пустой (элемент найден)
        if (!notebooks.isEmpty()) {
            System.out.println(notebooks.get(0).getText());
            Assertions.assertTrue(notebooks
                    .get(0)
                    .getText()
                    .replace("'", "\"")
                    .contains(firstNotebookName1), "Отсутствует искомый товар");
        } else {
            Assertions.fail("Элемент не найден: отсутствует искомый товар");
        }
    }



    }


