package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static helpers.CustomWaits.waitInvisibleIfLocated;


/**
 * Класс для страницы с курсами валют (Кузнецов)
 */
public class BankCoursesPage {
    private String selectorPreload = "//div[@class='open-ui-loader rate-loader centered']";
    private String selectorExchangeRates = "//section[not(@style='display: none;')]//table[@class='card-rates-table cards']";

    private String selectorTableHeaders = ".//thead//th";

    private String selectorTableRows = ".//tbody/tr";

    private WebDriver chromedriver;

    private WebElement exchangeRates;

    private List<Map<String,String>> collectExchangeRates = new ArrayList<>();
    /**
     * конструктор
     * @param chromeDriver - объект вебдрайвера (Кузнецов)
     */
    public BankCoursesPage(WebDriver chromeDriver) {

        this.chromedriver = chromeDriver;
    }

    /**
     * Метод получения результатов и сохранения в коллекцию (требует рефакторинга)
     * @return - возвращает коллекцию (Кузнецов)
     */
    public List<WebElement> getResults() {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[not(contains(@style, 'display: none;'))]//tbody/child::*")));
        List<WebElement> resultCourse = chromedriver.findElements(By.xpath("//section[not(contains(@style, 'display: none;'))]//tbody/child::*"));
        return resultCourse;
    }

    /**
     * Методы конвертации строк в числа для долларов и евро (требует рефакторинга)
     * @return - возвращает курс числом (Кузнецов)
     */

    public List<Map<String, String>> convertCurrencyElementsToMap() {
//        waitInvisibleIfLocated(chromedriver, selectorPreload,2,5);
        //fluentWaitInvisibleIfLocated(driver, selectorPreload,2,5);
        exchangeRates=chromedriver.findElement(By.xpath(selectorExchangeRates));
        List<WebElement> tableHeaders = exchangeRates.findElements(By.xpath(selectorTableHeaders));
        List<WebElement> tableRows = exchangeRates.findElements(By.xpath(selectorTableRows));

        for (int i=0; i<tableRows.size();++i){
            Map<String,String> collectRow = new HashMap<>();
            for (int j=0; j<tableHeaders.size();++j)
                collectRow.put(
                        tableHeaders.get(j).getText(),
                        tableRows.get(i).findElement(By.xpath("./td["+(j+1)+"]")).getText()
                );
            collectExchangeRates.add(collectRow);
        }

        return collectExchangeRates;
    }


}
