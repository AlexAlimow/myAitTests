package com.myAit.tests;

import com.myAit.core.TestBase;
import org.testng.annotations.Test;

public class CareerConsultationUsaTests extends TestBase {

    @Test
    public void buttonLeadsToSignFormAndButton() {
        app.getUsaPage().openUsaPage();
        app.getUsaPage().clickConsultationButton();
        app.getUsaPage().isConsultationFormVisible();
        app.getUsaPage().isSignUpButtonVisible();

    }
}
