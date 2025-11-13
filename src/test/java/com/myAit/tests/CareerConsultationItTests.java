package com.myAit.tests;

import com.myAit.core.TestBase;
import org.testng.annotations.Test;

public class CareerConsultationItTests extends TestBase {

    @Test
    public void itPageSessionStorageTest() {
        String queryParams = "utm_medium=social&utm_campaign=winter_promo&gclid=abcdef";

        // Открываем страницу IT
        app.getItPage().openPage(queryParams);

        // Проверяем кнопки и сохранение sessionStorage до и после перехода
        app.getItPage().verifyAllConsultationButtons(queryParams);
    }
}
