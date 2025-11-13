package com.myAit.pages;

import com.myAit.core.BaseHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ConsultationPage extends BaseHelper {

    protected final By consultationButtons = By.xpath("//a[contains(@href,'career-consultation')]");
    protected WebDriverWait wait;
    protected String baseUrl;

    public ConsultationPage(WebDriver driver, String baseUrl) {
        super(driver);
        this.baseUrl = baseUrl;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String queryParams) {
        String url = queryParams != null && !queryParams.isEmpty() ? baseUrl + "?" + queryParams : baseUrl;
        driver.get(url);

        // Сохраняем параметры в sessionStorage
        if (queryParams != null && !queryParams.isEmpty()) {
            saveUrlParamsToSessionStorage(driver.getCurrentUrl());
        }
    }

    private String getSessionStorageItem(String key) {
        return (String) ((JavascriptExecutor) driver)
                .executeScript("return window.sessionStorage.getItem(arguments[0]);", key);
    }

    public void verifyAllConsultationButtons(String queryParams) {
        List<WebElement> buttons = driver.findElements(consultationButtons);

        for (int i = 0; i < buttons.size(); i++) {
            WebElement button = driver.findElements(consultationButtons).get(i);

            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();

            // Ждем, что URL содержит career-consultation
            wait.until(ExpectedConditions.urlContains("career-consultation"));

            String currentUrl = driver.getCurrentUrl();
            System.out.println("Текущий URL: " + currentUrl);

            if (!currentUrl.contains("career-consultation")) {
                throw new AssertionError("URL не содержит career-consultation!");
            }

            // Проверка sessionStorage
            if (queryParams != null && !queryParams.isEmpty()) {
                String[] params = queryParams.split("&");
                for (String param : params) {
                    String key = param.split("=")[0];
                    String value = param.split("=")[1];
                    String sessionValue = getSessionStorageItem(key);
                    System.out.println("sessionStorage " + key + ": " + sessionValue);

                    if (!value.equals(sessionValue)) {
                        System.out.println("Параметр " + key + " в sessionStorage не совпадает");
                    }
                }
            }

            // Возврат на страницу с исходными параметрами
            openPage(queryParams);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(consultationButtons));
        }
    }

}
