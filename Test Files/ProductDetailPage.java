package org.Appium_Mobile_Testing.SwagLab_App;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class ProductDetailPage extends BasicConfig{
	@BeforeMethod
    public void Login() {
		driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
		driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
    }
	
	@Test
	public void BacktoProductButtonTest() {
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiSelector().text(\"Sauce Labs Backpack\")")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiSelector().text(\"BACK TO PRODUCTS\")")).click();
		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
        Assert.assertTrue(Product.isDisplayed());
	}

	@Test
	public void ExactNameandPriceareDisplayed() {
	    WebElement productname = driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiSelector().text(\"Sauce Labs Backpack\")"));
	    String expectedName = productname.getText();
	    
	    WebElement Productprice = driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiSelector().text(\"$29.99\")"));
		String expectedPrice = Productprice.getText();

		productname.click();

	    String actualName = driver.findElement(AppiumBy.xpath(
	        "//android.widget.TextView[@text=\"Sauce Labs Backpack\"]"
	    )).getText();
	    
	    driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiScrollable(new UiSelector().scrollable(true))" +
		        ".scrollIntoView(new UiSelector().text(\"$29.99\"))")
		    );
	    String actualPrice = driver.findElement(AppiumBy.xpath(
		        "//android.widget.TextView[@content-desc=\"test-Price\"]"
		    )).getText();

	    Assert.assertEquals(actualName, expectedName);
	    Assert.assertEquals(actualPrice, expectedPrice);
	}
	
	@Test
	public void ImageandDescriptionareDisplayed() {
	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiSelector().text(\"Sauce Labs Backpack\")")).click();
	    
	    WebElement ProductImage = driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiSelector().className(\"android.widget.ImageView\").instance(5)"));

	    WebElement ProductDescription = driver.findElement(AppiumBy.xpath(
	        "//android.widget.TextView[starts-with(@text, 'carry.allTheThings() wit')]"));
	    
	    Assert.assertTrue(ProductImage.isDisplayed());
	    Assert.assertTrue(ProductDescription.isDisplayed());
	}

}
