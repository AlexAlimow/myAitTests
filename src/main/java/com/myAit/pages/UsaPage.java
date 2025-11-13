package com.myAit.pages;

import com.myAit.core.BaseHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UsaPage extends BaseHelper {

    private final By consultationButton = By.xpath(
            "//span[contains(@class,'StylableButton') and .//span[text()='Получить консультацию']]"
    );
    private final By consultationForm = By.xpath(
            "//span[text()='Запишитесь на бесплатную консультацию с экспертом']"
    );
    private final By signUpButton = By.xpath("//span[text()='Записаться']");
    private final By cookiesButton = By.id("cst-cookies-submit");

    private WebDriverWait wait;

    public UsaPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openUsaPage() {
        driver.get("https://www.my-ait.com/usa");
    }

    public void closeCookiesPopup() {
        try {
            WebElement cookies = wait.until(ExpectedConditions.elementToBeClickable(cookiesButton));
            cookies.click();
            System.out.println("Popup с куками закрыт.");
        } catch (Exception e) {
            System.out.println("Popup с куками не найден или уже закрыт.");
        }
    }

    public void clickConsultationButton() {
        closeCookiesPopup();
        try {
            WebElement consultBtn = wait.until(ExpectedConditions.elementToBeClickable(consultationButton));
            consultBtn.click();
            System.out.println("Кнопка 'Получить консультацию' нажата.");
        } catch (Exception e) {
            System.out.println("Не удалось нажать кнопку консультации: " + e.getMessage());
        }
    }

    public boolean isSignUpButtonVisible() {
        try {
            WebElement signUp = wait.until(ExpectedConditions.visibilityOfElementLocated(signUpButton));
            return signUp.isDisplayed();
        } catch (Exception e) {
            System.out.println("Кнопка 'Записаться' не видна.");
            return false;
        }
    }

    public boolean isConsultationFormVisible() {
        try {
            WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(consultationForm));
            boolean visible = form.isDisplayed();
            System.out.println("Форма консультации видна: " + visible);
            return visible;
        } catch (Exception e) {
            System.out.println("Форма консультации не отображается: " + e.getMessage());
            return false;
        }
    }
}
