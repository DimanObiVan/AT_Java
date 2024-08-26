package helpers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static helpers.Properties.testsProperties;

public class CustomWaits {

    private static int implicitlyWait=testsProperties.defaultTimeout();
    private static int pageLoadTimeout=testsProperties.defaultTimeout();
    private static int setScriptTimeout=testsProperties.defaultTimeout();


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
        implicitlyWait(driver,implicitlyWait);
    }
}
