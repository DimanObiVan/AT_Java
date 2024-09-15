package helpers;

import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

public class Assertions {
    @Step("Проверяем: {message}")
    public static void assertTrue(boolean condition, String message) {
        try {


            org.junit.jupiter.api.Assertions.assertTrue(condition, message);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Step("Проверяем что {expected} равно {actual}: {message}")
    public static void assertEquals(Object expected, Object actual, String message) {
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
    }

    @Step("Проверяем что {expected} равно {actual}: {message}")
    public static void softAssert(Object expected, Object actual, String message) {
        final List<String> errors = new ArrayList<>();
        try {
            assertTrue(expected.equals(actual), message);
        } catch (AssertionError e) {
            errors.add(message);
        }
        System.out.println(errors);
    }
    @Step("Проверяем что {condition} верно: {message}")
    public static void softAssert(boolean condition, String message) {
        final List<String> errors = new ArrayList<>();
        try {
            assertTrue(condition, message);
        } catch (AssertionError e) {
            errors.add(message);
        }
        System.out.println(errors);

    }

    public static void fail(String message) {
        org.junit.jupiter.api.Assertions.fail(message);
    }

}
