package com.myAit.tests;

import com.myAit.core.TestBase;
import org.testng.annotations.Test;

public class CareerConsultationTests extends TestBase {

    @Test
    public void accountingPageSessionStorageTest() {
        String queryParams = "utm_medium=email&utm_campaign=autumn_promo&gclid=xyz123";

        // Открываем страницу Accounting
        app.getAccountingPage().openPage(queryParams);

        // Проверяем кнопки и сохранение sessionStorage до и после перехода
        app.getAccountingPage().verifyAllConsultationButtons(queryParams);
    }


}



