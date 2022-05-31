package com.qa.tests;

import com.qa.base.BaseClass;
import com.qa.pages.*;
import com.qa.utils.TestUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

public class EndToEndTest extends BaseClass {
    LoginPage loginPage;
    ProductsPage productsPage;
    MenuPage menuPage;
    SettingsPage settingsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CheckoutInformationPage checkoutInformationPage;
    CheckoutOverviewPage checkoutOverviewPage;
    CheckoutCompletePage checkoutCompletePage;

    @BeforeMethod
    public void objInit(Method m) {
        loginPage = new LoginPage();
        System.out.println(" *** Started " + m.getName() + " ***");
    }

    @Test
    public void Tc_01() throws InterruptedException {

        JSONObject jsonObjData = null;
        JSONObject jsonObjMsg = null;
        try {
            jsonObjData = new TestUtil().getJsonObject(getTestDataFilePath(), "TC_04");
            jsonObjMsg = new TestUtil().getJsonObject(getStringsFileFilePath(), "LoginPage");

        } catch (IOException e) {
            e.printStackTrace();
        }

        productsPage = loginPage.successfulLogin(jsonObjData.getString("ValidUserName"),
                jsonObjData.getString("ValidPassword"));
        new TestUtil().log().info("Login Successful !");
        menuPage = productsPage.tapAddCartBtn();
        checkoutPage = menuPage.tapCartIConWithCount();
        checkoutPage.tapContinueShoppingBtn();
        menuPage.tapCartIConWithCount();
        checkoutInformationPage = checkoutPage.tapCheckoutShoppingBtn();
        checkoutInformationPage.inputFirstName(jsonObjData.getString("FirstName"));
        checkoutInformationPage.inputLastName(jsonObjData.getString("LastName"));
        checkoutInformationPage.inputZipCode(jsonObjData.getString("zip"));
        checkoutOverviewPage = checkoutInformationPage.tapContinueBtn();
        checkoutCompletePage = checkoutOverviewPage.tapFinishBtn();
        checkoutCompletePage.tapBackHomeBtn();
    }
}