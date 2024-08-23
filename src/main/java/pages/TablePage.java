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

/**
 * Класс для страницы про таблицы, требует доработки (Кузнецов)
 */
public class TablePage {
    protected WebDriver chromeDriver;
    private List<WebElement> results;

    private WebElement table;
//    private String tableSelector = "//*[contains(text(),'Преподаватели кафедры программирования')]/..";
    private String tableSelector = "//table[@class='wikitable'][1]";

    private String tableHeaderSelector = ".//tr[1]";

    private String tableRowSelector = ".//tr[1]/following-sibling::tr";

    protected WebDriverWait wait;


    /**
     * Конструктор страницы
     * @param chromeDriver - объект вебдрайвера (Кузнецов)
     */
    public TablePage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        wait = new WebDriverWait(chromeDriver, 35);
    }

    /**
     * Результат поиска строк таблицы помещается в коллекцию
     * @return - возвращает строки таблицы (Кузнецов)
     */
    public List<WebElement> rows() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='wikitable'][1]//tr[td]")));
        results = chromeDriver.findElements(By.xpath("//table[@class='wikitable'][1]//tr[td]"));
        return results;
    }
    public List<Map<String, String>> NamesTable = new ArrayList<>();
    public List<Map<String, String>> getNamesTable () {
        table = chromeDriver.findElement(By.xpath(tableSelector));
        wait.until(ExpectedConditions.visibilityOf(table));
        List<WebElement> tableHeaders = table.findElements(By.xpath(tableHeaderSelector));
        List<WebElement> tableRows = table.findElements(By.xpath(tableRowSelector));
        System.out.println(tableHeaders);
        System.out.println(tableRows);

    for(int i = 0; i<tableRows.size(); ++i) {
        Map<String, String> tableHeader = new HashMap<>();
        for (int j = 0; j<tableHeaders.size(); ++j) {
//            List<WebElement> rowCells = tableRows.get(i).findElements(By.xpath("./td"));
            tableHeader.put(
                    tableHeaders.get(j).getText(),
                    tableRows.get(j).getText());

        }
        NamesTable.add(tableHeader);
    }
    return NamesTable;
}
}
