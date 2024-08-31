package steps;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BankCoursesPage;
import pages.BasePage;
import pages.OpenBankMainPage;
import pages.PageAfterSearch;

public class StepsAll {
    private static WebDriverWait wait;
    private static WebDriver driver;

    @Step("Переходим на сайт: {url}")
    public static void openSite(String url,String title, WebDriver currentDriver){
        driver=currentDriver;
        driver.get(url);
        wait = new WebDriverWait(driver,30);
//        wait.until(ExpectedConditions.titleIs(title));
    }

    @Step("Устанавливаем открытие в новой вкладке")
    public static void setOpenNewTab(){
        BasePage page = new BasePage(driver);
        page.setOpenInNewTab();
    }

    @Step("Ищем в поисковике по ключевой фразе: {searchQuery}")
    public static void searchInGoogle(String searchQuery){
        BasePage page = new BasePage(driver);
        page.find(searchQuery);
    }

    @Step("Переходим на сайт по заголовку: {title}")
    public static void openNewTab(String xPath){
        PageAfterSearch page = new PageAfterSearch(driver);
        page.openingNewTab(xPath);
    }

    @Step("Проверка наличия курсов валют")
    public static void checkCourses(){
        OpenBankMainPage page = new OpenBankMainPage(driver);
        page.areCoursesPresent();
        Assertions.assertTrue(page.areCoursesPresent(), "No element!");
        page.openCourses();
    }

    @Step("Проверка того, что на странице больше 3 курсов")
    public static void checkUSDCourses(){
        BankCoursesPage page = new BankCoursesPage(driver);
        Assertions.assertTrue(page.getResults().size()>3, "На странице менее 3 курсов валют!");
    }

    @Step("Проверка того, что курс продаже бакса больше курса покупки")
    public static void checkUSDCoursesValues(){
        BankCoursesPage page = new BankCoursesPage(driver);
        Assertions.assertTrue(Double.parseDouble(
                        page
                                .convertCurrencyElementsToMap().stream()
                                .filter(x->x.get("Валюта обмена").contains("USD"))
                                .findFirst()
                                .get().get("Банк покупает").replace(",","."))
                        <
                        Double.parseDouble(
                                page
                                        .convertCurrencyElementsToMap().stream()
                                        .filter(x->x.get("Валюта обмена").contains("USD"))
                                        .findFirst()
                                        .get().get("Банк продаёт").replace(",","."))
                , "Курс покупки для долларов больше курса продажи доллара!");
    }

}
