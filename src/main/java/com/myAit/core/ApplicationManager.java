package com.myAit.core;

import com.myAit.pages.AccountingPage;
import com.myAit.pages.CareerConsultationPage;
import com.myAit.pages.ItPage;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class ApplicationManager {

    protected WebDriver driver;
    public static SoftAssert softAssert;

    AccountingPage accountingPage;
    CareerConsultationPage careerPage;
    ItPage itPage;

    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public ApplicationManager() {
        this(System.getProperty("browser", "chrome"));
    }

    public void init() {
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        softAssert = new SoftAssert();

        accountingPage = new AccountingPage(driver);
        careerPage = new CareerConsultationPage(driver);
        itPage = new ItPage(driver);
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    public AccountingPage getAccountingPage() { return accountingPage; }
    public CareerConsultationPage getCareerPage() { return careerPage; }
    public ItPage getItPage() { return itPage; }
}
