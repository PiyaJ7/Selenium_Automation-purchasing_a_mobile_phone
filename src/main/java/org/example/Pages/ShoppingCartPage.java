package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ShoppingCartPage {
    public WebDriver driver;

    public ShoppingCartPage(WebDriver driver) {

        this.driver = driver;
    }

    public WebElement getCartButton(String cartButtonId) {

        return driver.findElement(By.id(cartButtonId));
    }

    public WebElement getCartDetailTitle(String cartTitleId) {

        return driver.findElement(By.className(cartTitleId));
    }

    public WebElement getCartDetailPrice(String cartPriceId) {

        return driver.findElement(By.className(cartPriceId));
    }

    public WebElement getCheckoutButton(String checkoutButtonId) {
        return driver.findElement(By.cssSelector(checkoutButtonId));
    }

    public WebElement getContinueAsGuest(String guestId) {

        return driver.findElement(By.id(guestId));
    }
}
