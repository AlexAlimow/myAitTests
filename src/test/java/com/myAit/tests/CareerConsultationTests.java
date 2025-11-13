package com.myAit.tests;

import com.myAit.core.TestBase;
import org.testng.annotations.Test;

public class CareerConsultationTests extends TestBase {

    @Test
    public void AccountingPageSessionStorageAndButtonsTest() {
        String queryParams = "param_1=value_1&param_2=value_2";
        app.getAccountingPage().openPage(queryParams);
        app.getAccountingPage().verifyAllConsultationButtons(queryParams);
    }

}

