package org.example.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class MobilePhonePage {
    private WebDriver driver;

    public MobilePhonePage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getSearchResult(String searchResultId) {
        return driver.findElements(By.className(searchResultId));
    }

    public List<WebElement> getResultDetails(String resultDetailId) {
        return driver.findElements(By.className(resultDetailId));
    }

    public  List<WebElement> getFirstResult(String firstResultId) {
        return driver.findElements(By.className(firstResultId));
    }
}
