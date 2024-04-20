package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage {
    public WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getCheckoutPageTitle(String checkoutTitleId) {
        return driver.findElement(By.className(checkoutTitleId));
    }
}
