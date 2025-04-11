package com.emp.playwrightdemo;

import com.microsoft.playwright.*;

public class LoginTest {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      Page page = browser.newPage();

      // Pre-initialize script to ensure sessionStorage is cleared before any script on the page runs
      page.addInitScript("() => sessionStorage.clear()");

      // Navigate to your local HTML file
      page.navigate("file:///Users/serrakhi/samples/seleniumVsPlaywright/index.html");

      // Fill in the login credentials
      page.fill("#username", "testuser");
      page.fill("#password", "testpass");
      page.click("input[type=submit]");

      // Wait for the message that contains the token
      Locator messageLocator = page.locator("#message");
      messageLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));

      // Verify that the message contains the token
      String message = messageLocator.textContent();
      if (message.contains("You're logged in")) {
        System.out.println("Message received: " + message);

        // Fetch the token from sessionStorage
        String token = page.evaluate("() => sessionStorage.getItem('accessToken')").toString();
        System.out.println("Token: " + token);

        // Calculate the length of the token
        int tokenLength = token.length();
        System.out.println("Token Length: " + tokenLength);

        // Validate token length
        if (tokenLength > 10) {
          System.out.println("The token is valid and has a reasonable length.");
        } else {
          System.out.println("The token length seems invalid.");
        }
      } else {
        System.out.println("Failed to log in or find token message.");
      }

      // Close the browser after the test
      browser.close();
    }
  }
}
