package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Класс для страницы с курсами валют (Кузнецов)
 */
public class BankCoursesPage extends OpenBankMainPage {
    protected WebElement usdBuyCourse = chromeDriver.findElement(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'USD')]/../../following-sibling::td[1]"));
    protected WebElement usdSellCourse = chromeDriver.findElement(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'USD')]/../../following-sibling::td[2]"));

    protected WebElement eurBuyCourse = chromeDriver.findElement(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'EUR')]/../../following-sibling::td[1]"));

    protected WebElement eurSellCourse = chromeDriver.findElement(By.xpath("//section[not(contains(@style, 'display: none;'))]//td//span[contains(text(),'EUR')]/../../following-sibling::td[2]"));

    private List<WebElement> resultCourse;

    /**
     * конструктор
     * @param chromeDriver - объект вебдрайвера (Кузнецов)
     */
    public BankCoursesPage(WebDriver chromeDriver) {
        super(chromeDriver);
    }

    /**
     * Метод получения результатов и сохранения в коллекцию (требует рефакторинга)
     * @return - возвращает коллекцию (Кузнецов)
     */
    public List<WebElement> getResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//section[not(contains(@style, 'display: none;'))]//tbody/child::*")));
        resultCourse = chromeDriver.findElements(By.xpath("//section[not(contains(@style, 'display: none;'))]//tbody/child::*"));
        return resultCourse;
    }

    /**
     * Методы конвертации строк в числа для долларов и евро (требует рефакторинга)
     * @return - возвращает курс числом (Кузнецов)
     */

    public Map<String, Double> convertCurrencyElementsToMap() {
        Map<String, Double> currencyValues = new HashMap<>();

        currencyValues.put("USD Buy", Double.parseDouble(usdBuyCourse.getText().replace(",", ".")));
        currencyValues.put("USD Sell", Double.parseDouble(usdSellCourse.getText().replace(",", ".")));
        currencyValues.put("EUR Buy", Double.parseDouble(eurBuyCourse.getText().replace(",", ".")));
        currencyValues.put("EUR Sell", Double.parseDouble(eurSellCourse.getText().replace(",", ".")));

        return currencyValues;
    }


}
