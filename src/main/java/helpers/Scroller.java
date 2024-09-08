package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.YandexAfterSearch;
import static java.lang.Thread.sleep;

public class Scroller {
//    private static WebDriver chrome = new ChromeDriver();
private static WebDriverWait wait;


    public static void scroll(String xPath, WebDriver chromeDriver) throws InterruptedException {
       WebElement scroller = chromeDriver.findElement(By.xpath(xPath));
       //scroller.click();
        long startTime = System.currentTimeMillis();
        long timeout = 45000;
       while (System.currentTimeMillis() - startTime < timeout) {

       //        chromeDriver.findElement(By.xpath(xPath)).click();
               scroller.sendKeys(Keys.PAGE_DOWN);

           sleep(700);
        //   WebElement footer = chromeDriver.findElement(By.xpath("//footer"));

       }
    }
    public static void scrollWindow(String xPath, String xPath2, WebDriver chromeDriver) throws InterruptedException {
        WebElement scroller = chromeDriver.findElement(By.xpath(xPath));
        scroller.click();
        long startTime = System.currentTimeMillis();
        long timeout = 15000;
        WebElement element = chromeDriver.findElement(By.xpath(xPath2));
        while (System.currentTimeMillis() - startTime < timeout) {
            if(!element.isDisplayed()) {
                System.out.println("Скроллер: не нашел элемент, начинаю скролл");
                scroller.sendKeys(Keys.ARROW_DOWN);
//                sleep(500);
            } else break;
            //   WebElement footer = chromeDriver.findElement(By.xpath("//footer"));

        }
    }

//    public static WebElement scrollUntilElementIsFound(String xpath, WebDriver chromeDriver) throws InterruptedException {
//        WebElement element = null;
//        int scrollCount = 0;
//
//        while (scrollCount < 10) {
//            try {
//                // Попробуем найти элемент
//                element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
//                if (element.isDisplayed()) {
//                    System.out.println("Элемент найден на странице.");
//                    return element;
//                }
//            } catch (NoSuchElementException | TimeoutException e) {
//                // Игнорируем, если элемент не найден, и продолжаем прокрутку
//            }
//
//            // Скроллим вниз на определенное количество пикселей
//            ((JavascriptExecutor) chromeDriver).executeScript("window.scrollBy(0, window.innerHeight);");
//            scrollCount++;
//            Thread.sleep(1000); // Ожидание после прокрутки
//
//            System.out.println("Прокрутка " + scrollCount + " из " + 10);
//        }
//
//        System.out.println("Элемент не найден после максимального числа прокруток.");
//        return element;
//    }

}
