package org.Appium_Mobile_Testing.SwagLab_App;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class Checkout_OverviewPage extends BasicConfig{

	@BeforeMethod
    public void Login_AddtoCart_MovetoCheckOut() {
		driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
		driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\").instance(0)")).click();
        driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(3)")).click();
        driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CHECKOUT\")")).click();
        driver.findElement(AppiumBy.accessibilityId(
				"test-First Name")).sendKeys("Yazan");
		driver.findElement(AppiumBy.accessibilityId(
				"test-Last Name")).sendKeys("Almuselli");
		driver.findElement(AppiumBy.accessibilityId(
				"test-Zip/Postal Code")).sendKeys("01010101");
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CONTINUE\")")).click();
    }
	
	@Test
	public void Checkout_OverviewVerifyPageElements() {
		WebElement PaymentInformation = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"Payment Information:\")"));
		WebElement SauceCard = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"SauceCard #31337\")"));
		WebElement ShippingInformation = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"Shipping Information:\")"));
		WebElement ShippingCost = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"FREE PONY EXPRESS DELIVERY!\")"));
		WebElement ItemCost = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"Item total: $29.99\")"));
		WebElement TaxCost = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"Tax: $2.40\")"));
		WebElement TotalCost = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"Total: $32.39\")"));
		
		Assert.assertTrue(PaymentInformation.isDisplayed());	
		Assert.assertTrue(SauceCard.isDisplayed());
		Assert.assertTrue(ShippingInformation.isDisplayed());
		Assert.assertTrue(ShippingCost.isDisplayed());
		Assert.assertTrue(ItemCost.isDisplayed());
		Assert.assertTrue(TaxCost.isDisplayed());
		Assert.assertTrue(TotalCost.isDisplayed());
	}

	@Test
	public void Checkout_OverviewcanelButtonTest() {
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-CANCEL\")")).click();
		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
		Assert.assertTrue(Product.isDisplayed());
	}
}
