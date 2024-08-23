package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Главная страница банка (Кузнецов)
 */
public class OpenBankMainPage {
    protected WebDriver chromeDriver;
    private List<WebElement> results;

    protected WebDriverWait wait;

    private WebElement resultSearch;

    private WebElement closeCookie;

    private WebElement allCoursesButton;

    /**
     * Конструктор страницы
     * @param chromeDriver - объект вебдрайвера (Кузнецов)
     */
    public OpenBankMainPage(WebDriver chromeDriver) {
        this.chromeDriver = chromeDriver;
        wait = new WebDriverWait(chromeDriver, 35);
    }

    /**
     * метод закрытия кукиса и открытия ссылки на курс валют (Кузнецов)
     */
    public void openCourses() {
        WebElement closeCookie = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class,'CookieWarning_cookie-warning-button__XaO44')]")));
        closeCookie.click();
        WebElement allCourses = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Все курсы']")));
        allCourses.click();
    }

    /**
     * Проверка наличия блока с курсом
     * @return - возвращает boolean (Кузнецов)
     */
    public boolean areCoursesPresent() {
        WebElement resultSearch = chromeDriver.findElement(By.xpath("//*[@data-block-id='exchange']"));
        return resultSearch.isDisplayed();
    }

}
