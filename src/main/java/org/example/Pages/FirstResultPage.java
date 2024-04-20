package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstResultPage {
    public WebDriver driver;

    public FirstResultPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getDetailsTitle(String detailsTitleId) {
        return driver.findElement(By.className(detailsTitleId));
    }

    public WebElement getDetailPrice(String detailPriceId) {
        return driver.findElement(By.className(detailPriceId));
    }

    public WebElement getPlugSelect(String plugSelectId) {
        return driver.findElement(By.id(plugSelectId));
    }

    public WebElement getQuantityInput(String quantityInputID) {
        return driver.findElement(By.id(quantityInputID));
    }

    public WebElement getCartButton(String cartButtonID) {
        return driver.findElement(By.className(cartButtonID));
    }
}
