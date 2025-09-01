package org.Appium_Mobile_Testing.SwagLab_App;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class Checkout_InformationPage extends BasicConfig{
	
	// Helper Method to Fill CHeckOut: Information
	private void FillInformation(String firstname, String lastname, String postalcode){
		driver.findElement(AppiumBy.accessibilityId(
				"test-First Name")).sendKeys(firstname);
		driver.findElement(AppiumBy.accessibilityId(
				"test-Last Name")).sendKeys(lastname);
		driver.findElement(AppiumBy.accessibilityId(
				"test-Zip/Postal Code")).sendKeys(postalcode);
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CONTINUE\")")).click();
    }
	
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
    }

	@Test
	public void Checkout_InformationVerifyPageElements() {
		WebElement Checkout_InformationPageTitle = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CHECKOUT: INFORMATION\")"));
		WebElement FirstName = driver.findElement(AppiumBy.accessibilityId(
				"test-First Name"));
		WebElement LastName = driver.findElement(AppiumBy.accessibilityId(
				"test-Last Name"));
		WebElement PostalCode = driver.findElement(AppiumBy.accessibilityId(
				"test-Zip/Postal Code"));
		WebElement ContinueButton = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CONTINUE\")"));
		
		Assert.assertTrue(Checkout_InformationPageTitle.isDisplayed());	
		Assert.assertTrue(FirstName.isDisplayed());
		Assert.assertTrue(LastName.isDisplayed());
		Assert.assertTrue(PostalCode.isDisplayed());
		Assert.assertTrue(ContinueButton.isDisplayed());
	}
	
	@Test
	public void Checkout_InformationCancelButtonTest() {
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CANCEL\")")).click();;

		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
		Assert.assertTrue(Product.isDisplayed());
	}
	
	@Test
	public void FillInfowithBlankFirstName() {
		FillInformation("", "Almuselli", "01010101");
		WebElement ErrorMessage = driver.findElement(AppiumBy.xpath(
				"//android.widget.TextView[@text=\"First Name is required\"]"));
		Assert.assertEquals(ErrorMessage.getText(), "First Name is required");
	}
	
	@Test
	public void FillInfowithBlankLastName() {
		FillInformation("Yazan", "", "01010101");
		WebElement ErrorMessage = driver.findElement(AppiumBy.xpath(
				"//android.widget.TextView[@text=\"First Name is required\"]"));
		Assert.assertEquals(ErrorMessage.getText(), "Last Name is required");
	}
	
	@Test
	public void FillInfowithBlankPostalCode() {
		FillInformation("Yazan", "Almuselli", "");
		WebElement ErrorMessage = driver.findElement(AppiumBy.xpath(
				"//android.widget.TextView[@text=\"First Name is required\"]"));
		Assert.assertEquals(ErrorMessage.getText(), "Postal Code is required");
	}
	
	@Test(enabled = false)
	public void FillInfowithWrongData() {
		FillInformation("1", "2", "Yazan");
		WebElement ErrorMessage = driver.findElement(AppiumBy.xpath(
				"//android.widget.TextView[@text=\"First Name is required\"]"));
		Assert.assertEquals(ErrorMessage.getText(), "Postal Code is required");
	}
	
}
