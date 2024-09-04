package pages;

import helpers.Assertions;
import helpers.Scroller;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;


import static java.lang.Thread.sleep;

public class YandexAfterSearch {

    public WebDriverWait wait;

    private String minValueRange = "//div[@data-filter-id='glprice']//span[contains(@data-auto, 'filter-range-min')]//input";
    private String maxValueRange = "//div[@data-filter-id='glprice']//span[contains(@data-auto, 'filter-range-max')]//input";
    private String manufacturerFilter = "//div[contains(@data-zone-data,'Производитель')]";
    private String manufacturerFilterValue = "//div[contains(@data-zone-data,'";
    private String notebookElement = "//div[@data-auto='SerpList']/child::div";
    private String itemName = "//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name']";
    private String itemPrice = "//div[@data-apiary-widget-name='@light/Organic']//div[@data-auto='snippet-price-current']";
    private String pageXpath = "//body";
    private String firstNotebookXpath = "(//div[@data-apiary-widget-name='@light/Organic']//span[@itemprop='name'])[1]";
    private String searchField = "//input[@id='header-search']";
    private String searchButton = "//button[@data-auto='search-button']";
//    private String filter = "//div[@data-zone-name='QuickFilters']//div[contains(@data-zone-data, '\"filterName\":\"Производитель\"')]";
    private String notebookFoundPath = "//div[@data-auto='SerpList']//*[text()='";


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

    /**
     * Добавил возвращаемое значение
     * @param values
     * @return
     */
    public void manufacturersList(List<String> values) throws InterruptedException {
        // Ждем появления фильтра производителей на странице
        WebElement manufacturerSection = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(manufacturerFilter)));

        for (String value : values) {
            // Попробуем найти кнопку "Показать еще" и кликнуть по ней
            try {
                WebElement showMoreButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manufacturerFilter + "//button")));
                showMoreButton.click();
                System.out.println("Кнопка 'Показать еще' нажата.");
            } catch (Exception e) {
                System.out.println("Кнопка 'Показать еще' не найдена или неактивна.");
            }

            // Ждем, пока появится input для ввода значения производителя
            try {
                WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(manufacturerFilter + "//input")));
                wait.until(ExpectedConditions.visibilityOf(input));
                input.clear();
                input.sendKeys(value);
                System.out.println("Вводим значение: " + value);

                // Ждем появления элемента производителя и кликаем по нему
                String manufacturerXpath = manufacturerFilter + manufacturerFilterValue + value + "')]";
                WebElement manufacturerElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manufacturerXpath)));
                manufacturerElement.click();
                System.out.println("Элемент найден и добавлен: " + value);
            } catch (TimeoutException e) {
                System.out.println("Не удалось найти input или элемент производителя для значения: " + value);
            } catch (StaleElementReferenceException e) {
                System.out.println("Элемент устарел, повторная попытка для значения: " + value);
                WebElement manufacturerElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(manufacturerFilter + manufacturerFilterValue + value + "')]")));
                manufacturerElement.click();
            } catch (Exception e) {
                System.out.println("Ошибка при работе с элементом: " + value + ". " + e.getMessage());
            }
        }
    }
//    public void manufacturersList(List<String> values) throws InterruptedException {
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(manufacturerFilter)));
//        /**
//         * Ищем кнопку "Показать еще и нажимаем"
//         */
//        /**
//         * Итерируемся по всем указанным производителям и нажимаем
//         */
//        for (String value : values) {
//            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(manufacturerFilter + "//button")));
//            button.click();
//            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(manufacturerFilter+"//input")));
//            input.sendKeys(value);
//            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(manufacturerFilter+manufacturerFilterValue + value + "')]")));
//            element.click();
//            Thread.sleep(2000);
//            }
//        }




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
        List<String> MatchingNames = name.stream()
                .filter(names -> values.stream().anyMatch(names::contains))
                .collect(Collectors.toList());
        System.out.println("удовлетворяют условиям: " + MatchingNames);
//        List<String> nonMatchingNames = name.stream()
//                .filter(names -> manufacturer.stream().noneMatch(manufacturerName ->
//                        names.toLowerCase().contains(manufacturerName.toLowerCase())
//
//                )
//        ).collect(Collectors.toList());
//        System.out.println("НЕ удовлетворяют условиям: " + nonMatchingNames);
//        List<String> nonMatchingNames = name.stream()
//                .filter(names -> manufacturer.stream().noneMatch(names::contains))
//                .collect(Collectors.toList());
//        System.out.println("Не удовлетворяют условиям: " + nonMatchingNames);

        for (String names : name) {
            boolean matches = values.stream()
                    .anyMatch(manufacturerName -> names.toLowerCase().contains(manufacturerName.toLowerCase()));

            if (!matches) {
                System.out.println("Не найдено соответствие для: " + names);
            }
        }

//        Assertions.assertTrue(
//                name.stream().allMatch(names ->
//                        manufacturer.stream().anyMatch(manufacturerName ->
//                                names.toLowerCase().contains(manufacturerName.toLowerCase())
//                        )
//                ),
//                "Не все элементы соответствуют фильтру по названию!"
//        );

        Assertions.assertTrue(price.stream()
                .allMatch(prices->prices >= minValue && prices <= maxValue), "Не все элементы соответствуют фильтру по цене!");

    }
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


