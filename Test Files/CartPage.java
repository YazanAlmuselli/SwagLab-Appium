package org.Appium_Mobile_Testing.SwagLab_App;

import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class CartPage extends BasicConfig{
	@BeforeMethod
    public void Login() {
		driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
		driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
    }

	@Test
	public void AddtoCartButton_RemoveButton_Test() {
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\").instance(0)")).click();
		WebElement CartBadge = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"1\")"));
		Assert.assertEquals(CartBadge.getText(), "1");
		
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"REMOVE\")")).click();
		
		boolean invisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        AppiumBy.androidUIAutomator("new UiSelector().text(\"1\")")));
		
		Assert.assertTrue(invisible, "Element was not removed from DOM!");
	}
	
	@Test
	public void AddtoCartviaDragandDropTest() {
		WebElement ElemToBeDraged = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"󰇛\").instance(0)"));
		
		driver.executeScript("mobile: dragGesture", Map.of(
                "elementId", ((RemoteWebElement)ElemToBeDraged).getId(),   
                "endX", 350,             
                "endY", 180                   
            ));
		
		WebElement CartBadge = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"1\")"));
		Assert.assertEquals(CartBadge.getText(), "1");
	}
	
	@Test
	public void AddtoCartfromProductDetailsPageTest() {
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiSelector().text(\"Sauce Labs Backpack\")")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiSelector().text(\"ADD TO CART\")")).click();
		WebElement CartBadge = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"1\")"));
		Assert.assertEquals(CartBadge.getText(), "1");
	}
	
	@Test
	public void VerifyCartPageElements() {
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(3)")).click();
		
		WebElement YourCartTitle =  driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.view.ViewGroup\").instance(13)"));
		WebElement QTY =  driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"QTY\")"));
		WebElement Description =  driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"DESCRIPTION\")"));
		WebElement ContinueShoppingBtn =  driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CONTINUE SHOPPING\")"));
		WebElement CheckOutBtn =  driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CHECKOUT\")"));
		
		Assert.assertTrue(YourCartTitle.isDisplayed());
		Assert.assertTrue(QTY.isDisplayed());
		Assert.assertTrue(Description.isDisplayed());
		Assert.assertTrue(ContinueShoppingBtn.isDisplayed());
		Assert.assertTrue(CheckOutBtn.isDisplayed());
	}
	
	@Test
	public void ContinueShoppingBtnTest() {
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(3)")).click();
		
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"CONTINUE SHOPPING\")")).click();;
		
		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
	    Assert.assertTrue(Product.isDisplayed());
	}
	
	@Test
	public void QTY_Test() { 
	// Add product to cart => Ensure QTY increased => Remove product from cart => Ensure QTY = 0 
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\").instance(0)")).click();
		
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(3)")).click();
		
		WebElement Quantity = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"1\").instance(1)"));
		Assert.assertEquals(Quantity.getText(), "1");
		
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"REMOVE\")")).click();
	
		boolean invisible1 = wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        AppiumBy.androidUIAutomator("new UiSelector().text(\"REMOVE\")")));
		boolean invisible2 = wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        AppiumBy.androidUIAutomator("new UiSelector().text(\"1\").instance(1)")));
		
		Assert.assertTrue(invisible1);
		Assert.assertTrue(invisible2);
	}
	
	@Test
	public void RemoveProductFromCartviaSwip() { 
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\").instance(0)")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(3)")).click();
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        AppiumBy.androidUIAutomator(
		        		"new UiSelector().className(\"android.widget.ImageView\").instance(7)")));
		
		WebElement Elem = driver.findElement(AppiumBy.accessibilityId(
				"test-Item"));
        driver.executeScript("mobile: swipeGesture", Map.of(
                "elementId", ((RemoteWebElement)Elem).getId(),   
                "direction", "left",             
                "percent", 0.4                   
            ));
		
        driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.view.ViewGroup\").instance(27)")).click();
        
        boolean invisible1 = wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        AppiumBy.androidUIAutomator("new UiSelector().text(\"REMOVE\")")));
		boolean invisible2 = wait.until(ExpectedConditions.invisibilityOfElementLocated(
		        AppiumBy.androidUIAutomator("new UiSelector().text(\"1\").instance(1)")));
		
		Assert.assertTrue(invisible1);
		Assert.assertTrue(invisible2);
	}
}
