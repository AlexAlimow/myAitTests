package com.myAit.core;

import com.myAit.pages.AccountingPage;
import com.myAit.pages.CareerConsultationPage;
import com.myAit.pages.ItPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class ApplicationManager {
    public String browser;
    protected WebDriver driver;
    public static SoftAssert softAssert;

    AccountingPage accountingPage;
    CareerConsultationPage careerPage;
    ItPage itPage;



    public ApplicationManager(String browser) {
        this.browser = browser;
    }



    public void init() {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

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
