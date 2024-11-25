package com.MeandEm.uk;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class HappyPathCheckoutTest {

    public static void main(String[] args) {
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


        page1.locator("//*[@id='product-detail-block-:R3deuubaj5ta:']/div/div[3]/form/button").click(); // click on the add to bag

        page1.getByText(" – Review Bag and Checkout").click();  // click on the Review Bag and Checkout

        //Asserting the checkout page with Order summary Label
        Locator orderSummary = page1.locator("//h2[contains(text(),'Order Summary')]");
        PlaywrightAssertions.assertThat(orderSummary).hasText("Order Summary");


        //page1.locator("a[aria-label='Checkout'].sm\\:inline-grid").click(); //works
        page1.locator("a[aria-label='Checkout']:visible").click();


        //to contiunue as a guest
        page1.getByText("Continue as guest").click();
        page1.locator("input[type='email']").fill("test123@test.com");
        page1.getByText("Continue to Delivery").click();
        //Delivery Address
        page1.getByLabel("First Name*").click();
        page1.getByLabel("First Name*").fill("Steve");
        page1.getByLabel("Last Name*").fill("joe");
        page1.getByLabel("Phone Number*").fill("074785858574");
        page1.getByLabel("Country*").selectOption("GB");
        page1.getByLabel("Address Line1*").fill("abc street");
        page1.getByLabel("Post code*").fill("cr1 1pp");
        page1.getByLabel("City*").fill("London");
        page1.locator("//*[@id='address-form']/form/div[7]/button").click();
        //billing address
        page1.locator("//*[@data-testid='billingAddress']/div/button").click();
        //Delivery Method
        page1.locator("//*[@class='grid gap-8']/div[5]").click(); // radiobutton
        page1.locator("//*[@data-testid='deliveryOptions']/div/button").click();

        //card number
        Frame iframeCardNumber = page1.frame("braintree-hosted-field-number");
        iframeCardNumber.locator("input[placeholder='0000 0000 0000 0000']").fill("4360 0000 0100 0005");

        // card expiry date
        Frame ifarmeExpiryDate = page1.frame("braintree-hosted-field-expirationDate");
        ifarmeExpiryDate.locator("input[placeholder='MM/YY']").fill("03/2030");

        //cvv number
        Frame iframeCVVNumber = page1.frame("braintree-hosted-field-cvv");
        iframeCVVNumber.locator("input[placeholder='123']").fill("737");

        //name on card
        Frame iframeNameonCard = page1.frame("braintree-hosted-field-cardholderName");
        iframeNameonCard.locator("input[placeholder='Full name']").fill("Mrs Smith");

        //postcode
        Frame iframePostcode = page1.frame("braintree-hosted-field-postalCode");
        iframePostcode.locator("input[id='postal-code']").fill("CR77PP");

        System.out.println("Case 01 : Happy path from landing on the page to completing the card details in the checkout   COMPLETED");

        page1.close();
        playwright.close();
    }

}

