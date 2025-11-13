package com.myAit.core;

import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {
    protected static ApplicationManager app =
            new ApplicationManager(System.getProperty("browser", Browser.CHROME.browserName()));

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (app != null) {
            app.stop();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void startTest(Method method, Object[] p) {
        MDC.put("testName", method.getName()); //добавляем имя теста в контекст
        logger.info("▶ START test: {} with data: {}", method.getName(), Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void stopTest(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        if (result.isSuccess()) {
            logger.info("PASSED: {}", testName);
        } else {
            logger.error("FAILED: {}", testName);
        }
        logger.info("──────────────────────────────────────────────");
        MDC.clear(); //очищаем контекст
    }
}
