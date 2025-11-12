package com.myAit.pages;

import com.myAit.core.BaseHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AccountingPage extends BaseHelper {

    private final By consultationButtons = By
            .xpath("//a[contains(@href,'career-consultation')]");

    public AccountingPage(WebDriver driver) {
        super(driver);

    }

    public void openAccountingPage(String queryParams) {
        driver.get("https://my-ait.com/accounting?" + queryParams);
    }

    public void verifyAllConsultationButtons(String queryParams) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Получаем список кнопок
        List<WebElement> buttons = driver.findElements(consultationButtons);

        for (int i = 0; i < buttons.size(); i++) {
            // Переполучаем кнопку на каждой итерации, чтобы избежать StaleElementReferenceException
            WebElement button = driver.findElements(consultationButtons).get(i);

            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();

            // Ждём загрузки страницы консультации
            wait.until(ExpectedConditions.urlContains("career-consultation"));

            // Возвращаемся на страницу Accounting
            openAccountingPage(queryParams);

            // Ждём, пока кнопки снова появятся
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(consultationButtons));
        }
    }
}




