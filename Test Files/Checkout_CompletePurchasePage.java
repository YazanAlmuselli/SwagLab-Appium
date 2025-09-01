package org.Appium_Mobile_Testing.SwagLab_App;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class Checkout_CompletePurchasePage extends BasicConfig {

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
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiScrollable(new UiSelector().scrollable(true))" +
		        ".scrollIntoView(new UiSelector().text(\"FINISH\"))"));
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-FINISH\")")).click();
    }
	
	@Test
	public void Checkout_OverviewVerifyPageElements() {
		WebElement CompleteTitle = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CHECKOUT: COMPLETE!\")"));
		WebElement ThanksMessage = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"THANK YOU FOR YOU ORDER\")"));
		WebElement SubmissionMessage = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"Your order has been dispatched, and will arrive just as fast as the pony can get there!\")"));
		WebElement ShipperLogo = driver.findElement(AppiumBy.xpath(
				"//android.widget.ScrollView[@content-desc=\"test-CHECKOUT: COMPLETE!\"]/android.view.ViewGroup/android.widget.ImageView"));
		WebElement BackHomeButton = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-BACK HOME\")"));
		
		Assert.assertTrue(CompleteTitle.isDisplayed());	
		Assert.assertTrue(ThanksMessage.isDisplayed());
		Assert.assertTrue(SubmissionMessage.isDisplayed());
		Assert.assertTrue(ShipperLogo.isDisplayed());
		Assert.assertTrue(BackHomeButton.isDisplayed());
	}
	
	@Test
	public void Checkout_OverviewBacktoHomeButtonTest() {
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-BACK HOME\")")).click();
		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
		Assert.assertTrue(Product.isDisplayed());
	}
	
}
