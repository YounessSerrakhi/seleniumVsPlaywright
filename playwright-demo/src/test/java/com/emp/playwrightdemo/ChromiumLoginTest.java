package com.emp.playwrightdemo;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

public class ChromiumLoginTest extends BasePlaywrightTest {
  @Override
  protected BrowserType browserType() {
    return playwright.chromium();
  }

  @Test
  void testLoginChromium() {
    performLoginTest();
  }
}
