package com.emp.playwrightdemo;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

public class WebkitLoginTest extends BasePlaywrightTest {
  @Override
  protected BrowserType browserType() {
    return playwright.webkit();
  }

  @Test
  void testLoginWebkit() {
    performLoginTest();
  }
}
