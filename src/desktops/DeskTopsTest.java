package desktops;

import com.google.common.base.Verify;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Write the following Test:
1.Test name verifyProductArrangeInAlphaBaticalOrder()
1.1 Mouse hover on Desktops Tab.and click
1.2 Click on “Show All Desktops”
1.3 Select Sort By position "Name: Z to A"
1.4 Verify the Product will arrange in Descending order.
2. Test name verifyProductAddedToShoppingCartSuccessFully()
2.1 Mouse hover on Currency Dropdown and click
2.2 Mouse hover on £Pound Sterling and click
2.3 Mouse hover on Desktops Tab.
2.4 Click on “Show All Desktops”
2.5 Select Sort By position "Name: A to Z"
2.6 Select product “HP LP3065”
2.7 Verify the Text "HP LP3065"
2.8 Select Delivery Date "2023-11-27"
2.9.Enter Qty "1” using Select class.
2.10 Click on “Add to Cart” button
2.11 Verify the Message “Success: You have added HP LP3065 to your shopping cart!”
2.12 Click on link “shopping cart” display into success message
2.13 Verify the text "Shopping Cart"
2.14 Verify the Product name "HP LP3065"
2.15 Verify the Delivery Date "2023-11-27"
2.16 Verify the Model "Product21"
2.17 Verify the Todat "£74.73"
 */
public class DeskTopsTest extends Utility {
    String baseUrl = "http://tutorialsninja.com/demo/index.php?";

    @Before
    public void setUp() {
        // Open browser and launch url
        openBrowser(baseUrl);
    }

    public void selectMenu(String menu) {
        clickOnElement(By.xpath("//nav[@id='menu']//a[normalize-space()='" + menu + "']"));
    }

    @Test
    public void verifyProductArrangeInAlphaBeticalOrder() throws InterruptedException {
        String menuName = "Show AllDesktops";
        // Mouse hover on Desktops Tab.and click
        mouseHoverAndClickOnElement(By.xpath("//a[@class='dropdown-toggle'][normalize-space()='Desktops']"));
        // Click on “Show All Desktops”
        selectMenu(menuName);
        // before sorting value
        List<WebElement> beforeSortValue = driver.findElements(By.xpath("//div[@class='caption']//h4"));
        List<String> beforeSortDesktopValue = new ArrayList<>();
        for (WebElement value : beforeSortValue) {
            beforeSortDesktopValue.add(value.getText());
        }
        // Select Sort By position "Name: Z to A"
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='input-sort']"), "Name (Z - A)");
        Thread.sleep(1000);
        // After shorting value
        List<WebElement> afterSortValue = driver.findElements(By.xpath("//div[@class='caption']//h4"));
        List<String> afterSortDesktopValue = new ArrayList<>();
        Thread.sleep(1000);
        for (WebElement value1 : afterSortValue) {
            afterSortDesktopValue.add(value1.getText());
        }
        Collections.sort(beforeSortDesktopValue, String.CASE_INSENSITIVE_ORDER);// Ascending order
        Collections.reverse(beforeSortDesktopValue); // descending order
        // Verify the Product will arrange in Descending order.
        Assert.assertEquals(beforeSortDesktopValue, afterSortDesktopValue);
    }

    @Test
    public void verifyProductAddedToShoppingCartSuccessFully() throws InterruptedException {
        String menuName = "Show AllDesktops";
        // Mouse hover on Desktops Tab.and click
        mouseHoverAndClickOnElement(By.xpath("//a[@class='dropdown-toggle'][normalize-space()='Desktops']"));
        // Click on “Show All Desktops”
        selectMenu(menuName);
        // Select Sort By position "Name: A to Z"
        selectByVisibleTextFromDropDown(By.xpath("//select[@id='input-sort']"), "Name (A - Z)");
        // 2.4 Select product “HP LP3065”

        clickOnElement(By.xpath("//a[normalize-space()='HP LP3065']"));
        String expectedMessage = "HP LP3065";
        String actualMessage = getTextFromElement(By.xpath("//h1[normalize-space()='HP LP3065']"));
        //Verify the Text "HP LP3065"
        //verifyTextDisplayed(expectedMessage, actualMessage);
        // Select Delivery Date "2022-11-30"
        selectDate("27", "November", "2023");
        // Click on “Add to Cart” button
        clickOnElement(By.xpath("//button[@id='button-cart']"));
        // Verify the Message “Success: You have added HP LP3065 to your shopping cart!”
        expectedMessage = "Success: You have added HP LP3065 to your shopping cart!\n";
        actualMessage = getTextFromElement(By.xpath("//div[@class='alert alert-success alert-dismissible']"));
        String[] actualmsg = actualMessage.split("×");
        //verifyTextDisplayed(expectedMessage, actualmsg[0]);
        // Click on link “shopping cart” display into success message
        Thread.sleep(1000);
        clickOnElement(By.linkText("shopping cart"));
        // Verify the text "Shopping Cart"
        expectedMessage = "Shopping Cart  (1.00kg)";
        actualMessage = getTextFromElement(By.xpath("//body/div/div/div/h1[1]"));
        // Verify the Model "Product21"
        expectedMessage = "Product 21";
        actualMessage = getTextFromElement(By.xpath("//td[normalize-space()='Product 21']"));
        //verifyTextDisplayed(expectedMessage, actualMessage);
        // Verify the Todat "£74.73"
        expectedMessage = "£74.73";
        actualMessage = getTextFromElement(By.xpath("//tbody//tr//td[6]"));
        //verifyTextDisplayed(expectedMessage, actualMessage);


    }

    public void selectDate(String date, String month, String year) throws InterruptedException {
        clickOnElement(By.xpath("//i[@class='fa fa-calendar']"));
        while (true) {
            String monthYear = driver.findElement(By.xpath("(//th[@class='picker-switch'])[1]")).getText();
            String[] a = monthYear.split(" ");
            String mon = a[0];
            String yer = a[1];
            if (mon.equalsIgnoreCase(month) && yer.equalsIgnoreCase(year)) {
                break;
            } else {
                clickOnElement(By.xpath("//div[@class='datepicker-days']//th[@class='next'][contains(text(),'›')]"));
            }
        }
        Thread.sleep(1000);
        // Select the date
        List<WebElement> allDates = driver.findElements(By.xpath("//*[@class='datepicker-days']//tbody//tr//td"));
        for (WebElement dt : allDates) {
            if (dt.getText().equalsIgnoreCase(date)) {
                dt.click();
                break;
            }
        }
    }

    @After
    public void tearDown() {
        // close all windows
        closeBrowser();
    }


}
