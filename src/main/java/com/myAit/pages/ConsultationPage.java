package com.myAit.pages;

import com.myAit.core.BaseHelper;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConsultationPage extends BaseHelper {

    protected final By consultationButtons = By.xpath("//a[contains(@href,'career-consultation')]");
    protected WebDriverWait wait;
    protected String baseUrl;

    // добавляем нужные константы
    private final String storageKey = "platform_app_675bbcef-18d8-41f5-800e-131ec9e08762_c57021d5-8161-4bc9-9a6c-920a006a7fe7";

    private final Set<String> allowedKeys = new HashSet<>(Arrays.asList(
            "utm_medium", "utm_campaign", "utm_content", "utm_term", "utm_id",
            "fbclid", "gclid", "ttclid", "ymclid", "yclid"
    ));

    public ConsultationPage(WebDriver driver, String baseUrl) {
        super(driver);
        this.baseUrl = baseUrl;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String queryParams) {
        String url = queryParams != null && !queryParams.isEmpty() ? baseUrl + "?" + queryParams : baseUrl;
        driver.get(url);
    }

    private String getSessionStorageItem(String key) {
        return (String) ((JavascriptExecutor) driver)
                .executeScript("return window.sessionStorage.getItem(arguments[0]);", key);
    }

    // переработанный метод
    public void verifySessionStorageTrackingData(String queryParams) {
        checkSessionStorage(queryParams, true);
    }

    private void checkSessionStorage(String queryParams, boolean waitForUpdate) {
        if (waitForUpdate) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        String storageJson = getSessionStorageItem(storageKey);

        if (storageJson == null) {
            throw new AssertionError("Ключ " + storageKey + " отсутствует в sessionStorage!");
        }

        JSONObject obj = new JSONObject(storageJson);

        System.out.println("Текущее содержимое sessionStorage: " + storageJson);

        // trackingData — строка, парсим её вручную
        JSONObject trackingData = null;
        if (obj.has("trackingData")) {
            Object rawData = obj.get("trackingData");
            if (rawData instanceof String) {
                trackingData = new JSONObject((String) rawData);
            } else if (rawData instanceof JSONObject) {
                trackingData = (JSONObject) rawData;
            }
        }

        if (trackingData == null) {
            throw new AssertionError("Объект trackingData отсутствует или нераспознан в sessionStorage!");
        }

        // Проверяем допустимые ключи
        for (String key : trackingData.keySet()) {
            if (!allowedKeys.contains(key)) {
                throw new AssertionError("trackingData содержит запрещенный ключ: " + key);
            }
        }

        // Проверяем совпадение значений
        if (queryParams != null && !queryParams.isEmpty()) {
            String[] params = queryParams.split("&");
            for (String param : params) {
                String[] parts = param.split("=");
                String key = parts[0];
                String value = parts.length > 1 ? parts[1] : "";

                String storedValue = trackingData.optString(key, null);

                if (storedValue == null) {
                    System.out.println("Значение " + key + " отсутствует в trackingData (ожидалось: " + value + ")");
                } else if (!value.equals(storedValue)) {
                    throw new AssertionError("Значение параметра " + key + " не совпадает! " +
                            "Ожидалось: " + value + ", получено: " + storedValue);
                }
            }
        }

        System.out.println("Проверка sessionStorage успешна. trackingData: " + trackingData);
    }

    public void verifyAllConsultationButtons(String queryParams) {
        List<WebElement> buttons = driver.findElements(consultationButtons);

        for (int i = 0; i < buttons.size(); i++) {
            WebElement button = driver.findElements(consultationButtons).get(i);

            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();

            wait.until(ExpectedConditions.urlContains("career-consultation"));
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Текущий URL: " + currentUrl);

            ///Проверка sessionStorage на новой странице
            checkSessionStorage(queryParams, true);

            // Возврат на исходную страницу
            openPage(queryParams);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(consultationButtons));
        }
    }
}
