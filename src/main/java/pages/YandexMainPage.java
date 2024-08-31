package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.beans.Visibility;

public class YandexMainPage {

    private WebDriver chromeDriver;
    private WebDriverWait wait;
    private String catalogue = "//div[@data-zone-name='catalog']/button";
    private String categories = "//li[not(@data-baobab-name='department')]//*[contains(text(),'";
    private String itemsList = "//ul[@data-autotest-id='subItems']//*[contains(text(),'";
    private String category = "Электроника";
    private String item = "Ноутбуки";

    public YandexMainPage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        this.wait = new WebDriverWait(chromeDriver, 30);
    }


    public void catalogueOpen() {
        WebElement catalogueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(catalogue)));
//        WebElement catalogueButton = chromeDriver.findElement(By.xpath(catalogue));
        catalogueButton.click();
    }

    public void hoverOverAndClick(String category, String item){
        Actions actions = new Actions(chromeDriver);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(categories+category+"')]/..")));
 //       WebElement element = chromeDriver.findElement(By.xpath(categories+category+"')]/.."));
        actions.moveToElement(element).perform();
        WebElement notebook = chromeDriver.findElement(By.xpath(itemsList+item+"')]"));
        notebook.click();
        Assertions.assertTrue(chromeDriver.findElement(By.xpath("//h1[@data-auto='title']")).getText().contains(item), "неверный раздел");
    }

//    public void assertNotebooksAreOpened () {
//        Assertions.assertTrue(chromeDriver.findElement(By.xpath("//h1[@data-auto='title']")).getText().contains(item), "неверный раздел");
//
//    }




}
