package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Класс для страницы поиск Гугла (Кузнецов)
 */
public class BasePage {
    protected WebDriver chromeDriver;
    protected WebElement searchField;

    protected WebElement searchButton;

    protected WebDriverWait wait;

    protected List<WebElement> results;

    /**
     *
     * @param chromeDriver - объект вебдрайвера
     * Конструктор страницы (Кузнецов)
     */

    public BasePage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.searchField = chromeDriver.findElement(By.xpath("//textarea[@aria-label='Найти']"));
        this.searchButton = chromeDriver.findElement(By.xpath("//div[contains(@jsname, 'VlcLAe')]//input[1]"));
        wait = new WebDriverWait(chromeDriver, 35);

    }

    /**
     * Метод поиска слова (комментарии оставлял для отладки)
     * @param word - слово для поиска (Кузнецов)
     */
    public void find(String word){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@aria-label='Найти']")));
        searchField.click();
//        System.out.println("нажал на поле поиска");
        searchField.sendKeys(word);
//        System.out.println("напечатал слово");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@jsname, 'VlcLAe')]//input[1]")));
//        System.out.println("подождал");
        searchButton.click();
//        System.out.println("нажал поиск");
    }

    /**
     * Метод получения результатов и сохранения в коллекцию
     * @param xPath - xPath элемента
     * @return - возвращает содержимое коллекции (Кузнецов)
     */
    public List<WebElement> getResults(String xPath) {
//        System.out.println("подождал загрузки");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
//        System.out.println("сохранил элементы");
        results = chromeDriver.findElements(By.xpath(xPath));
//        System.out.println("вернул элементы");
        return results;
    }

    /**
     * Метод открытия ссылки и переключения на новое окно
     * @param xPath - xPath элемента (Кузнецов)
     */
    public void openingNewTab(String xPath) {
        WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        link.click();
        ArrayList<String> tabs = new ArrayList<> (chromeDriver.getWindowHandles());
        chromeDriver.switchTo().window(tabs.get(1));
    }



    /**
     * Метод для настройки открытия ссылок в новой вкладке (коряво, но времени исправлять не было) (Кузнецов)
     */

    public void setOpenInNewTab() {
        WebElement settings = chromeDriver.findElement(By.xpath("//div[@aria-haspopup='true']/*[contains(text(),'Настройки')]"));
        settings.click();
        chromeDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement searchSettings = chromeDriver.findElement(By.xpath("//*[contains(text(),'Настройки поиска')]"));
        searchSettings.click();
        WebElement otherSettings = chromeDriver.findElement(By.xpath("//div[contains(text(),'Другие настройки')]"));
        otherSettings.click();
        WebElement checkBox = chromeDriver.findElement(By.xpath("//div[@data-pid='newwindow']//input[@type='checkbox']"));
        if (checkBox.getAttribute("checked") == null) {
            WebElement newTab = chromeDriver.findElement(By.xpath("//div[@data-pid='newwindow']/div[@class='HrqWPb']"));
            newTab.click();
        }
        WebElement returnButton = chromeDriver.findElement(By.xpath("//a[@href='/webhp']"));
        returnButton.click();

    }

}
