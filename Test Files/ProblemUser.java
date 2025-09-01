package org.Appium_Mobile_Testing.SwagLab_App;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class ProblemUser extends BasicConfig{

	@BeforeMethod
    public void Login() {
		driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
		driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("problem_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
    }
	
	@Test
	public void Add6ProductstoCart(){
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\").instance(0)")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\")")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiScrollable(new UiSelector().scrollable(true))" +
		        ".scrollIntoView(new UiSelector().text(\"$49.99\"))"));
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\").instance(0)")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\")")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiScrollable(new UiSelector().scrollable(true))" +
		        ".scrollIntoView(new UiSelector().text(\"$7.99\"))"));
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\").instance(0)")).click();
		driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().description(\"test-ADD TO CART\")")).click();
		
		WebElement CartBadge = driver.findElement(AppiumBy.androidUIAutomator(
			    "new UiSelector().textMatches(\"[0-9]+\")"));
		Assert.assertEquals(CartBadge.getText(), "3");
	}
	
	@Test
	public void ProblemWithCostCalculation() {
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
		
		WebElement ItemCost = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"Item total: $59.98\")"));

	    String itemText = ItemCost.getText().replace("Item total: $", "");

	    double item = Double.parseDouble(itemText);

	    Assert.assertEquals(item, "29.99");
	}

}
