package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DataProvider {

    public static Stream<Arguments> providerCheckingMoney(){
        return Stream.of(
                Arguments.of("Oткрытие", "Банк Открытие: кредит наличными, ипотека, кредитные и ...",
                        "//*[contains(text(), 'Банк Открытие: кредит наличными, ипотека, кредитные и ...')]",
                                "//*[@href='https://www.open.ru/']/..")//,
                //Arguments.of("Oткрытие", "Банк Открытие: кредит наличными, ипотека, кредитные и ...", "EUR")
        );
    }

    public static Stream<Arguments> provideCheckingMoneyList(){
        List<String> money = new ArrayList<>();
        money.add("USD");
        money.add("EUR");
        return Stream.of(
                Arguments.of("Oткрытие", "Банк Открытие: кредит наличными, ипотека, кредитные и ...", money)
        );
    }

    public static Stream<Arguments> dataForWikiTable() {
        return Stream.of(
                Arguments.of("таблица википедия", "Таблица", "//h3[text()='Таблица']","//h3[text()='Таблица']")
        );
    }

    /**
     * Параметры для теста яндекс маркета
     * @values коллекция производителей ноутбуков, для выбора фильтров
     * @return Стрим из названия категории, раздела, мин и макс ценами, кол-во ноутбуков на странице
     */
    public static Stream<Arguments> dataForYandexMarket() {
        List<String> values = new ArrayList<>();
        values.add("HP");
//        values.add("Lenovo");
//        values.add("Acer");
//        values.add("ASUS");
//        values.add("DELL");
        return Stream.of(
                Arguments.of("Электроника", "Ноутбуки", "10000", "30000", 12, values)
        );
    }
}
