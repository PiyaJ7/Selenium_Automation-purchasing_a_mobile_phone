package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;

    public  HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void openURL(String url) {
        driver.get(url);
    }

    public void getCategory(String categoryID, String category) {
        Select selectCategory = new Select(driver.findElement(By.id(categoryID)));
        selectCategory.selectByVisibleText(category);
    }

    public String getSelectedCategory(String categoryID) {
        WebElement selectedOption = new Select(driver.findElement(By.id(categoryID))).getFirstSelectedOption();
        return selectedOption.getText();
    }

    public void getSearchInput(String searchId, String searchInput) {
        driver.findElement(By.id(searchId)).sendKeys(searchInput);
    }

    public WebElement getSearchButton(String searchButtonID) {
        return driver.findElement(By.id(searchButtonID));
    }






}
