package com.emp.playwrightdemo;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

  static Playwright playwright;
  static Browser browser;
  Page page;

  @BeforeAll
  static void setupAll() {
    playwright = Playwright.create();
    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
  }

  @AfterAll
  static void tearDownAll() {
    browser.close();
    playwright.close();
  }

  @BeforeEach
  void setup() {
    page = browser.newPage();
    page.addInitScript("() => sessionStorage.clear()");
  }

  @AfterEach
  void cleanup() {
    page.close();
  }

  @Test
  void testLoginAndToken() {
    page.navigate("file:///Users/serrakhi/samples/seleniumVsPlaywright/index.html");

    page.fill("#username", "testuser");
    page.fill("#password", "testpass");
    page.click("input[type=submit]");

    Locator messageLocator = page.locator("#message");
    messageLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    String message = messageLocator.textContent();

    assertNotNull(message);
    assertTrue(message.contains("You're logged in"), "Message should confirm login");

    String token = page.evaluate("() => sessionStorage.getItem('accessToken')").toString();
    assertNotNull(token, "Token should not be null");
    assertTrue(token.length() > 10, "Token length should be reasonable");

    System.out.println("Token: " + token);
  }
}
