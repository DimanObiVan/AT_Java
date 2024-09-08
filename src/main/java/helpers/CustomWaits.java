package helpers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static helpers.Properties.testsProperties;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class CustomWaits {

//    private static int implicitlyWait=testsProperties.defaultTimeout();
//    private static int pageLoadTimeout=testsProperties.defaultTimeout();
//    private static int setScriptTimeout=testsProperties.defaultTimeout();
private static int implicitlyWait=30;
    private static int pageLoadTimeout=30;
    private static int setScriptTimeout=30;



    public static void implicitlyWait (WebDriver driver, int defaultTimeout){
        driver.manage().timeouts().implicitlyWait(defaultTimeout, TimeUnit.SECONDS);
        implicitlyWait=defaultTimeout;
    }
    public static void pageLoadTimeout (WebDriver driver, int defaultTimeout){
        driver.manage().timeouts().pageLoadTimeout(defaultTimeout, TimeUnit.SECONDS);
        pageLoadTimeout=defaultTimeout;
    }
    public static void setScriptTimeout (WebDriver driver, int defaultTimeout){
        driver.manage().timeouts().setScriptTimeout(defaultTimeout, TimeUnit.SECONDS);
        setScriptTimeout=defaultTimeout;
    }
    private static void sleep(int sec){
        try {
            Thread.sleep(sec* 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void waitInvisibleIfLocated(WebDriver driver, String elementXpath, int timeWaitLocated, int timeWaitInvisible) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        for(int i=0;i<timeWaitLocated;++i){
            if(driver.findElements(By.xpath(elementXpath)).size()!=0){
                for(int j=0;;++j){
                    if(driver.findElements(By.xpath(elementXpath)).size()==0)
                        break;
                    if(j+1==timeWaitInvisible)
                        Assertions.fail("Элемент "+elementXpath+" не исчез за "+ timeWaitInvisible + "секунд");
                    sleep(1);
                }
            }
            sleep(1);
        }
    //    implicitlyWait(driver,implicitlyWait);
    }

    private static FluentWait<WebDriver> getFluentWait(WebDriver chromedriver, int timeWait, int frequency){
        return new FluentWait<>(chromedriver)
                .withTimeout(Duration.ofSeconds(timeWait))
                .pollingEvery(Duration.ofMillis(frequency))
                .ignoreAll(List.of(
                        NoSuchElementException.class,
                        WebDriverException.class,
                        StaleElementReferenceException.class,
                        ElementClickInterceptedException.class,
                        TimeoutException.class)
                );
    }


    public static void fluentWaitInvisibleIfLocated(WebDriver chromedriver, String elementXpath, int timeWaitLocated/*, int timeWaitInvisible*/){
        chromedriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        try {
            getFluentWait(chromedriver, timeWaitLocated, 100).until(visibilityOfElementLocated(By.xpath(elementXpath)));
            //       getFluentWait(driver,timeWaitInvisible, 100).until(invisibilityOfElementLocated(By.xpath(elementXpath)));
        } catch (StaleElementReferenceException e) {
            System.out.println("Поймали эксепшн");
        }
        implicitlyWait(chromedriver,implicitlyWait);
    }

    public static FluentWait<WebDriver> fluentWait(WebDriver driver) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30)) // общее время ожидания
                .pollingEvery(Duration.ofMillis(200)) // частота повторных проверок
                .ignoring(StaleElementReferenceException.class) // игнорируем устаревшие элементы
                .ignoring(NoSuchElementException.class);
    }



}
