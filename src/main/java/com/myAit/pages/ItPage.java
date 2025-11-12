package com.myAit.pages;

import com.myAit.core.BaseHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ItPage extends BaseHelper {

    private final By consultationButtons = By
            .xpath("//a[contains(@href,'career-consultation')]");

    public ItPage(WebDriver driver) {
        super(driver);
    }

    public void openItPage(String queryParams) {
        driver.get("https://my-ait.com/it?" + queryParams);
    }

    public void verifyAllConsultationButtons(String queryParams) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> buttons = driver.findElements(consultationButtons);

        for (int i = 0; i < buttons.size(); i++) {
            WebElement button = driver.findElements(consultationButtons).get(i);

            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();

            wait.until(ExpectedConditions.urlContains("career-consultation"));

            // Возвращаемся на страницу IT
            openItPage(queryParams);

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(consultationButtons));
        }
    }
}

