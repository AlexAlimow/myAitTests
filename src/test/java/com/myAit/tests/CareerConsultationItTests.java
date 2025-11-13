package com.myAit.tests;

import com.myAit.core.TestBase;
import org.testng.annotations.Test;

public class CareerConsultationItTests extends TestBase {

    @Test
    public void itPageSessionStorageAndButtonsTest() {
        String queryParams = "param1=value1&param2=value2";
        app.getItPage().openPage(queryParams);
        app.getItPage().verifyAllConsultationButtons(queryParams);
    }
}
