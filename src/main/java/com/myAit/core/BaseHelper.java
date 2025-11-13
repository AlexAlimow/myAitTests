package com.myAit.core;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseHelper {
    protected WebDriver driver;


    public BaseHelper(WebDriver driver) {
        this.driver = driver;

    }

    public boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public void type(By locator, String text) {
        if (text != null) {
            click(locator);
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
        }
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public boolean isAlertPresent() {
        Alert alert = getWait(20)
                .until(ExpectedConditions.alertIsPresent());
        if (alert == null) {
            return false;
        } else {
            driver.switchTo().alert().accept();
            return true;
        }
    }

    public WebDriverWait getWait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Сохраняем параметры URL в sessionStorage
    public void saveUrlParamsToSessionStorage(String url) {
        String[] allowedKeys = {"utm_medium","utm_campaign","utm_content","utm_term","utm_id",
                "fbclid","gclid","ttclid","ymclid","yclid"};

        String query = url.contains("?") ? url.split("\\?")[1] : "";
        String[] pairs = query.isEmpty() ? new String[0] : query.split("&");

        StringBuilder jsObject = new StringBuilder("{");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            String key = kv[0];
            String value = kv.length > 1 ? kv[1] : "";
            for (String allowed : allowedKeys) {
                if (allowed.equals(key)) {
                    jsObject.append("\"").append(key).append("\":\"").append(value).append("\",");
                }
            }
        }
        if (jsObject.length() > 1) jsObject.setLength(jsObject.length() - 1); // убрать последнюю запятую
        jsObject.append("}");

        String storageKey = "platform_app_675bbcef-18d8-41f5-800e-131ec9e08762_c57021d5-8161-4bc9-9a6c-920a006a7fe7";
        String finalJs = String.format(
                "sessionStorage.setItem('%s', JSON.stringify({trackingData:%s}));",
                storageKey, jsObject.toString()
        );

        ((JavascriptExecutor) driver).executeScript(finalJs);
    }



}
