package com.myAit.tests;

import com.myAit.core.TestBase;
import org.testng.annotations.Test;

public class CareerConsultationTests extends TestBase {



    @Test
    public void allButtonsOnAccountingPageLeedsToCareerConsultationPageTest(){
        app.getAccountingPage().openAccountingPage("param1=value1&param2=value2");
        app.getAccountingPage().verifyAllConsultationButtons("param1=value1&param2=value2");
    }


}

