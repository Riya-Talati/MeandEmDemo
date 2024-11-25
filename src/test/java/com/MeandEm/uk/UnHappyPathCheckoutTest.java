package com.MeandEm.uk;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.*;

public class UnHappyPathCheckoutTest {

    public static void main(String[] args) {

   /* Case 02 : Unhappy path of your choosing for a user landing on this page and trying to checkout.
      COMPLETED
    */
        Playwright playwright = Playwright.create(); // First we need to start the playwright server.
        BrowserType.LaunchOptions lp = new BrowserType.LaunchOptions();// Then we need to decide in which browser we want to run our test
        lp.setChannel("chrome");
        lp.setHeadless(false);// setting headless mode off
        lp.setSlowMo(700);
        Browser browser = playwright.chromium().launch(lp);

         /* we can also use this method for launching chromiun browser type
         Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)); // setting headless mode off */

        BrowserContext context = browser.newContext();
        Page page1 = context.newPage();

        page1.navigate("https://staging.meandem.vercel.app/palazzo-pant-black");
        page1.locator("//*[@id='onetrust-accept-btn-handler']").click(); //Accepting the cookies

        Locator dropdown = page1.getByTestId("size-select-button-dropdown");
        dropdown.click(); // click on the dropdown

        Locator options = page1.locator("//span[contains(text(),'UK 4')]");
        options.click();// click on the size 4


        page1.locator("//*[@id='product-detail-block-:R3deuubaj5ta:']/div/div[3]/form/button").click(); // click on the add to cart

        page1.getByText(" – Review Bag and Checkout").click();  // click on the Review Bag and Checkout

        //Asserting the checkout page with Order summary Label
        Locator orderSummary = page1.locator("//h2[contains(text(),'Order Summary')]");
        PlaywrightAssertions.assertThat(orderSummary).hasText("Order Sumary");

        //page1.locator("a[aria-label='Checkout'].sm\\:inline-grid").click(); //works
        page1.locator("a[aria-label='Checkout']:visible").click();

    }
}





