package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Класс для страницы поиска через ПФ (Кузнецов)
 */
public class GooglePageFactory {
    private WebDriver chromeDriver;

    @FindBy(how= How.XPATH, using="//textarea[@aria-label='Найти']")
    WebElement searchField;

    @FindBy(how= How.XPATH, using="//div[contains(@jsname, 'VlcLAe')]//input[1]")
    WebElement searchButton;

    @FindBy(how= How.XPATH, using = "//*[contains(text(),'wikipedia.org')]")
    List<WebElement> results;

    /**
     * Конструктор страницы
     * @param chromeDriver - объект вебдрайвера (Кузнецов)
     */
    public GooglePageFactory(WebDriver chromeDriver) {

        this.chromeDriver = chromeDriver;
    }

    /**
     * метод поиска слова
     * @param word - слово для поиска (Кузнецов)
     */

    public void find(String word){
        searchField.click();
        searchField.sendKeys(word);
        searchButton.click();
    }

    /**
     * возвращает результаты
     * @return - возвращает коллекцию (Кузнецов)
     */

    public List<WebElement> getResults() {

        return results;
    }


}
