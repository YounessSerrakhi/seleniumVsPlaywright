package com.emp.playwrightdemo;

import com.microsoft.playwright.*;

import org.junit.jupiter.api.*;

public abstract class BasePlaywrightTest {
  protected static Playwright playwright;
  protected static Browser browser;
  protected Page page;

  protected abstract BrowserType browserType();

  @BeforeAll
  static void createPlaywright() {
    playwright = Playwright.create();
  }

  @AfterAll
  static void closePlaywright() {
    if (browser != null) browser.close();
    if (playwright != null) playwright.close();
  }

  @BeforeEach
  void setup() {
    browser = browserType().launch(new BrowserType.LaunchOptions().setHeadless(true));
    page = browser.newPage();
    page.addInitScript("() => sessionStorage.clear()");
  }

  @AfterEach
  void cleanup() {
    if (page != null) page.close();
    if (browser != null) browser.close();
  }

  protected void performLoginTest() {
    page.navigate("file:///Users/serrakhi/samples/seleniumVsPlaywright/index.html");

    page.fill("#username", "testuser");
    page.fill("#password", "testpass");
    page.click("input[type=submit]");

    Locator messageLocator = page.locator("#message");
    messageLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000));

    String message = messageLocator.textContent();
    Assertions.assertNotNull(message);
    Assertions.assertTrue(message.contains("You're logged in"), "Message should confirm login");

    String token = page.evaluate("() => sessionStorage.getItem('accessToken')").toString();
    Assertions.assertNotNull(token, "Token should not be null");
    Assertions.assertTrue(token.length() > 10, "Token length should be reasonable");

    System.out.println("[" + browserType().name() + "] Token: " + token);
  }
}
