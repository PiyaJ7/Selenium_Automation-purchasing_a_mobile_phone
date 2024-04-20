package selenium;

import org.example.Pages.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EbayMobilePhonePurchase {
    private WebDriver driver;
    private HomePage homePage;
    private MobilePhonePage mobilePhonePage;
    private FirstResultPage firstResultPage;
    private ShoppingCartPage shoppingCartPage;
    private CheckoutPage checkoutPage;
    private  String selectTitle;
    private  String selectPrice;
    private  String detailTitle;
    private String detailPrice;

    @BeforeTest
    public  void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        mobilePhonePage = new MobilePhonePage(driver);
        firstResultPage = new FirstResultPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test(priority = 0)
    public void goToURL() {
        homePage.openURL("https://www.ebay.com/");

        //Verify the correct page has loaded
        String homePageURL = driver.getCurrentUrl();
        Assert.assertEquals(homePageURL, "https://www.ebay.com/", "Incorrect page has been loaded.");
    }

    @Test(priority = 1)
    public void selectCategory () {
        homePage.getCategory("gh-cat", "Cell Phones & Accessories");

        // Verify the selected category
        String expectedCategory = "Cell Phones & Accessories";
        String actualCategory = homePage.getSelectedCategory("gh-cat");
        Assert.assertEquals(actualCategory, expectedCategory, "Incorrect category selected.");
    }

    @Test(priority = 2)
    public void searchItem() {
        homePage.getSearchInput("gh-ac", "Mobile Phone");
        homePage.getSearchButton("gh-btn").click();
    }

    @Test(priority = 3)
    public void searchResultDetails() {

        //Assert that the search results item details containing the word “Mobile Phone”
        List<WebElement>  result = mobilePhonePage.getSearchResult("s-item__title");

        for (int i = 0; i < Math.min(5, result.size()); i++) {
            if (result.get(i+1).getText().contains("Mobile Phone")){
                System.out.println("Result " + (i + 1) + " contains 'Mobile Phone'.");
            } else {
                System.out.println("Result " + (i + 1) + " doesn't contains 'Mobile Phone'.");
            }

        }
        System.out.println();

        //Extract the name/title and price of the mobile phone
        List<WebElement> resultDetails = mobilePhonePage.getResultDetails("s-item__info");

        System.out.println("Titles and Prices of first five items...");
        System.out.println("-------------------------------------------------");

        for (int i = 0; i < Math.min(5, resultDetails.size()); i++) {
            WebElement details = resultDetails.get(i+1);

            String title = details.findElement(By.className("s-item__title")).getText();
            System.out.println("Title " + (i+1) + ": " + title);

            String price = details.findElement(By.className("s-item__price")).getText();
            System.out.println("Price " + (i+1) + ": " + price);

            System.out.println();
        }

        List <WebElement> firstResult = mobilePhonePage.getFirstResult("s-item__title");
        if(!firstResult.isEmpty()) {
            selectTitle = resultDetails.get(1).findElement(By.className("s-item__title")).getText();
            selectPrice = resultDetails.get(1).findElement(By.className("s-item__price")).getText();
            firstResult.get(1).click();

        } else {
            System.out.println("There is no items to view.");
        }

    }

    @Test(priority = 4)
    public void retrieveDetails() {

        // Switch to the new tab
        String currentWindowHandle = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(currentWindowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        //Extract the name/title and price
        detailTitle = firstResultPage.getDetailsTitle("x-item-title").getText();
        System.out.println("Title of the Mobile Phone: " + detailTitle);

        detailPrice = firstResultPage.getDetailPrice("x-price-approx__price").getText();
        System.out.println("Price of the Mobile Phone: " + detailPrice);
        System.out.println();

        //Assert selected item details
        if (detailPrice.startsWith("US ")) {
            detailPrice = detailPrice.substring(3);
        }

        Assert.assertEquals(detailTitle, selectTitle, "Title of the selected item doesn't match");
        Assert.assertEquals(detailPrice, selectPrice, "Price of the selected item doesn't match");

        //Set the color
        try {
            List<WebElement> selectBoxes = driver.findElements(By.className("x-msku__select-box"));

            if(!selectBoxes.isEmpty()) {
                new Select(driver.findElement(By.className("x-msku__select-box"))).selectByVisibleText("Blue");
            } else {
                System.out.println("No colors are available");
            }
        } catch (Exception var3) {
            System.out.println("Color dropdown not found");
        }

//        //Select plug
        try {
            Select plugSelect = new Select(firstResultPage.getPlugSelect("x-msku__select-box-1001"));
            plugSelect.selectByIndex(1);
        } catch (Exception var3) {
            System.out.println("Plug dropdown not found");
        }
        System.out.println();
//

        //Select quantity
        WebElement quantity = firstResultPage.getQuantityInput("qtyTextBox");
        quantity.clear();
        quantity.sendKeys("1");
    }

    @Test(priority = 5)
    public void addToCart() {
        WebElement addToCart = firstResultPage.getCartButton("x-atc-action");
        addToCart.click();
    }

    @Test(priority = 6)
    public void cartDetails() {
        WebElement cartButton = shoppingCartPage.getCartButton("gh-minicart-hover");
        cartButton.click();

        //Extract the name/title and price of the selected mobile phone
        String cartTitle = shoppingCartPage.getCartDetailTitle("item-title").getText();
        String cartPrice = shoppingCartPage.getCartDetailPrice("additional-prices").getText();
        cartPrice = cartPrice.replaceAll("[^\\d.]", "");
        detailPrice = detailPrice.replaceAll("[^\\d.]", "");


        //print details
        System.out.println("Title of the selected product: " + cartTitle);
        System.out.println("Price of the selected product: $" + cartPrice);

        //Assert that the Cart item details
        Assert.assertEquals(cartTitle, detailTitle, "Title is mismatch.");
        Assert.assertEquals(cartPrice, detailPrice, "Price is incorrect");
    }

    @Test(priority = 7)
    public void  checkout() {
        shoppingCartPage.getCheckoutButton(".btn--primary").click();
    }

    @Test(priority = 8)
    public void continueAsGuest() {
        String parentWindowHandler = driver.getWindowHandle();
        String subWindowHandler = null;

        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()){
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("gxo-btn")));

        WebElement continueAsGuest = shoppingCartPage.getContinueAsGuest("gxo-btn");
        continueAsGuest.click();

    }

    @Test(priority = 9)
    public void checkoutPageTitle() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String checkoutPageTitle = checkoutPage.getCheckoutPageTitle("page-title").getText();
        String expectedTitle = "Checkout";

        System.out.println();
        System.out.println("Page Title: " + checkoutPageTitle);
        Assert.assertEquals(expectedTitle, checkoutPageTitle, "Title of the page is incorrect");
    }



    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
