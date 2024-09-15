package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.YandexAfterSearch;

import static helpers.Properties.testsProperties;
import static java.lang.Thread.sleep;

public class Scroller {

    /**
     * Метод для скролла страницы с помощью JS
     * @param chromedriver - хромдрайвер
     * @throws InterruptedException
     * (Кузнецов)
     */
    public static void scrollWithJS(WebDriver chromedriver) throws InterruptedException {
        sleep(testsProperties.sleepTime());
        JavascriptExecutor js = (JavascriptExecutor) chromedriver;
        WebElement element = chromedriver.findElement(By.xpath("//*[text()='Статистика']"));
        while (true) {try {
          boolean a = (boolean) js.executeScript(
                    "var rect = arguments[0].getBoundingClientRect();" +
                            "return (rect.top >= 0 && rect.left >= 0 && rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                    element
            );
            // Проверяем, видим ли элемент
            if (a) {
                break;
            }

            // Скроллим страницу вниз
            js.executeScript("window.scrollBy(0, 1000);");

            // Ждем некоторое время, чтобы контент успел подгрузиться
            Thread.sleep(testsProperties.sleepTime());

            // Проверяем снова
            if (a) {
                break;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
        }
    }


}
