package com.myAit.tests;

import com.myAit.core.TestBase;
import org.testng.annotations.Test;

public class CareerConsultationItTests extends TestBase {

    @Test
    public void allButtonsOnItPageLeadsToCareerConsultationPageTest() {
        app.getItPage().openItPage("param1=value1&param2=value2");
        app.getItPage().verifyAllConsultationButtons("param1=value1&param2=value2");
    }
}
