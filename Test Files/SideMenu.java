package org.Appium_Mobile_Testing.SwagLab_App;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class SideMenu extends BasicConfig{
	
	// Helper method for List_Drawing method
	private void drawLine(int startX, int startY, int endX, int endY, String fingerName) {
	    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, fingerName);
	    Sequence draw = new Sequence(finger, 1);

	    draw.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
	    draw.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
	    draw.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), endX, endY));
	    draw.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

	    driver.perform(Arrays.asList(draw));
	}
	
	@BeforeMethod
    public void Login() {
		driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
		driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
    }

	@Test
	public void ListIconIsDisplayed() {
		WebElement MenuList = driver.findElement(AppiumBy.accessibilityId("test-Menu"));
		Assert.assertTrue(MenuList.isDisplayed());
	}
	
	@Test
	public void RemoveListIconTest() {
		driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(5)")).click();
		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
        Assert.assertTrue(Product.isDisplayed());
	}
	
	@Test
	public void ListRemoveBySwipe() throws InterruptedException {
		driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();

		WebElement Elem = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(1)"));
        driver.executeScript("mobile: swipeGesture", Map.of(
                "elementId", ((RemoteWebElement)Elem).getId(),   
                "direction", "left",             
                "percent", 0.9                   
            ));
        WebElement Product = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
        Assert.assertTrue(Product.isDisplayed());
    }
	
	@Test
	public void List_AllItem() {
		driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"ALL ITEMS\")")).click();
		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
        Assert.assertTrue(Product.isDisplayed());
	}
	
	@Test
	public void List_WebView_Positive() throws InterruptedException {
		driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"WEBVIEW\")")).click();
		Assert.assertTrue(driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"WEBVIEW SELECTION\")")).isDisplayed());
		driver.findElement(AppiumBy.className("android.widget.EditText")).sendKeys("www.google.com");
		driver.findElement(By.xpath("//android.widget.TextView[@text=\"GO TO SITE\"]")).click();
		Thread.sleep(7000);
		Set<String> contexts = driver.getContextHandles();
		List<String> contextList = new ArrayList<>(contexts);
		Assert.assertEquals(contextList.get(1), "WEBVIEW_com.swaglabsmobileapp");
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(7000);
		contexts = driver.getContextHandles();
		contextList = new ArrayList<>(contexts);
		Assert.assertEquals(contextList.get(0), "NATIVE_APP");
	}
	
	@Test
	public void List_WebView_Negative() throws InterruptedException {
		driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"WEBVIEW\")")).click();
		Assert.assertTrue(driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"WEBVIEW SELECTION\")")).isDisplayed());
		driver.findElement(AppiumBy.className("android.widget.EditText")).sendKeys("google.com");
		driver.findElement(By.xpath("//android.widget.TextView[@text=\"GO TO SITE\"]")).click();
		WebElement ErrorMessage = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Please provide a correct https url\")"));
		Assert.assertTrue(ErrorMessage.isDisplayed());
	}
	
	@Test
	public void List_QRcodeScanner() {
		driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"QR CODE SCANNER\")")).click();
		
		WebElement QR_Page = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Scan a QR Code that contains a url.\"]"));
		Assert.assertTrue(QR_Page.isDisplayed());
	}
	
	@Test
	public void List_GeoLocation() {
		driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"GEO LOCATION\")")).click();
        WebElement Latitude = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Latitude:\"]"));
        WebElement Longitude = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"Longitude:\"]"));
        Assert.assertTrue(Latitude.isDisplayed());
        Assert.assertTrue(Longitude.isDisplayed());
	}
	
	@Test
	public void List_Drawing() throws InterruptedException {
	    driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
	    driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"DRAWING\")")).click();

	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	        AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\")")));

	    // First line
	    drawLine(150, 400, 450, 400, "finger1");
	    Thread.sleep(2000);

	    driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"CLEAR\")")).click();
	    Thread.sleep(2000);

	    // Second line
	    drawLine(200, 500, 500, 500, "finger2");
	    Thread.sleep(3000);

	    driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"SAVE\")")).click();
	    driver.findElement(AppiumBy.id("android:id/button1")).click();
	}

	@Test
	public void List_Logout() throws InterruptedException {
	    driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
	    driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGOUT\")")).click();

	    WebElement LogoutButton = driver.findElement(By.xpath("//android.widget.TextView[@text=\"LOGIN\"]"));
	    Assert.assertTrue(LogoutButton.isDisplayed());
	}
	
	@Test
	public void List_ResetAppState() throws InterruptedException {
	    driver.findElement(AppiumBy.androidUIAutomator(
	    		"new UiSelector().text(\"ADD TO CART\").instance(0)")).click();
	    WebElement CartBadge = driver.findElement(
	    		By.xpath("//android.widget.TextView[@text=\"1\"]"));
	    Assert.assertTrue(CartBadge.isDisplayed());
	    
	    driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
	    driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"RESET APP STATE\")")).click();
	    
	    List<WebElement> cartBadges = driver.findElements(
	            AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.view.ViewGroup"));

	    Assert.assertFalse(cartBadges.isEmpty());
	}

}
