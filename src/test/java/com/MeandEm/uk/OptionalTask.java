package com.MeandEm.uk;

import com.microsoft.playwright.*;

public class OptionalTask {
    public static void main(String[] args) {

          /* Case 03(Optional) : Write a Playwright test where the user tries to checkout as a guest, but the call submitting the guest email address 500s.
              COMPLETED
          */

        Playwright playwright = Playwright.create(); // First we need to start the playwright server.

        BrowserType.LaunchOptions lp = new BrowserType.LaunchOptions();// Then we need to decide in which browser we want to run our test
        lp.setChannel("chrome");
        lp.setHeadless(false);// setting headless mode off
        lp.setSlowMo(700);
        Browser browser = playwright.chromium().launch(lp);

        BrowserContext context = browser.newContext();
        Page page1 = context.newPage();


        page1.navigate("https://staging.meandem.vercel.app/palazzo-pant-black");

        // Intercept the guest email submission API request and simulate a 500 error
        page1.route("**/checkout", route -> {
            System.out.println("Intercepting request to: " + route.request().url());
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(500) // Set HTTP status code to 500
                    .setContentType("application/json")
                    .setBody("{'message':'Internal Server Error'}")
            );
        });

        page1.locator("//*[@id='onetrust-accept-btn-handler']").click(); //Accepting the cookies


        Locator dropdown = page1.getByTestId("size-select-button-dropdown");
        dropdown.click(); // click on the dropdown

        Locator options = page1.locator("//span[contains(text(),'UK 4')]");
        options.click();// click on the size 4


        page1.locator("//*[@id='product-detail-block-:R3deuubaj5ta:']/div/div[3]/form/button").click(); // click on the add to cart

        page1.getByText(" – Review Bag and Checkout").click();  // click on the Review Bag and Checkout

        //page1.locator("a[aria-label='Checkout'].sm\\:inline-grid").click(); //works
        page1.locator("a[aria-label='Checkout']:visible").click(); // clicks on checkout button

        page1.getByText("Continue as guest").click();
        page1.locator("input[type='email']").fill("bluebook@test.com");

        page1.getByText("Continue to Delivery").click();

        page1.close();
        playwright.close();


    }
}


