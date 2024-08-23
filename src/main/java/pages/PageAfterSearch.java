package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Класс для страницы с результатами, не используется (Кузнецов)
 */
public class PageAfterSearch {
    protected WebDriver chromeDriver;
    private List<WebElement> results;

    private WebDriverWait wait;


    public PageAfterSearch(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        wait = new WebDriverWait(chromeDriver, 35);
    }



    public List<WebElement> getResults() {
        System.out.println("подождал загрузки");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'wikipedia.org')]")));
        System.out.println("сохранил элементы");
        results = chromeDriver.findElements(By.xpath("//*[contains(text(),'wikipedia.org')]"));
        System.out.println("вернул элементы");
        return results;
    }
}
