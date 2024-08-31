package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Scroller {
    private static WebDriver chrome;
    private static void scroll(String xPath){
       WebElement scroller = chrome.findElement(By.xpath(xPath));
       scroller.click();
       scroller.sendKeys(Keys.ARROW_DOWN);


    }
}
