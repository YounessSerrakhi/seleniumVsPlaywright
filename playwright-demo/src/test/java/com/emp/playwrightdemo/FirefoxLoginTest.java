package com.emp.playwrightdemo;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

public class FirefoxLoginTest extends BasePlaywrightTest {
  @Override
  protected BrowserType browserType() {
    return playwright.firefox();
  }

  @Test
  void testLoginFirefox() {
    performLoginTest();
  }
}
