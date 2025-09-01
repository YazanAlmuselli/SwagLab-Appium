package org.Appium_Mobile_Testing.SwagLab_App;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;

public class ProductsPage extends BasicConfig{
	
	@BeforeMethod
    public void Login() {
		driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
		driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
    }

	@Test
	public void VerifyProductsPageElements() {
		WebElement SwagLabTopLogo = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(2)"));
		WebElement CartIcon = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(3)"));
		WebElement ViewToggleButton = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(4)"));
		WebElement FilterButton = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(5)"));
		
		driver.findElement(AppiumBy.androidUIAutomator(
		        "new UiScrollable(new UiSelector().scrollable(true))" +
		        ".scrollIntoView(new UiSelector().text(\"Terms of Service | Privacy Policy\"))")
		    );
		
		List<WebElement> SocilaMediaLogos = driver.findElements(AppiumBy.androidUIAutomator(
			    "new UiSelector().text(\"\")"));
		
		WebElement ImageofApp = driver.findElement(AppiumBy.androidUIAutomator(
				"new UiSelector().className(\"android.widget.ImageView\").instance(6)"));
		
	    Assert.assertTrue(SwagLabTopLogo.isDisplayed(), "SwagLabTopLogo is not displayed in UI hierarchy");
	    Assert.assertTrue(CartIcon.isDisplayed(), "CartIcon is not displayed in UI hierarchy");
	    Assert.assertTrue(ViewToggleButton.isDisplayed(), "ViewToggleButton is not displayed in UI hierarchy");
	    Assert.assertTrue(FilterButton.isDisplayed(), "FilterButton is not displayed in UI hierarchy");
	    Assert.assertTrue(SocilaMediaLogos.get(0).isDisplayed(), "Logo1 is not displayed in UI hierarchy");
	    Assert.assertTrue(SocilaMediaLogos.get(1).isDisplayed(), "Logo2 is not displayed in UI hierarchy");
	    Assert.assertTrue(SocilaMediaLogos.get(2).isDisplayed(), "Logo3 is not displayed in UI hierarchy");
	    Assert.assertTrue(SocilaMediaLogos.get(3).isDisplayed(), "Logo4 is not displayed in UI hierarchy");
	    Assert.assertTrue(ImageofApp.isDisplayed(), "ImageofApp is not displayed in UI hierarchy");
	}

	@Test
	public void ViewToggleButtonTest() {
		driver.findElement(AppiumBy.accessibilityId("test-Toggle")).click();
		String ProductDescription = driver.findElement(AppiumBy.androidUIAutomator(
			    "new UiSelector().textContains(\"carry.allTheThings()\")")).getText();
		Assert.assertTrue(ProductDescription.startsWith("carry.allTheThings()"));
	}
	
	@Test
	public void HightoLowPriceFilter() {
	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiSelector().className(\"android.widget.ImageView\").instance(5)")).click();

	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiSelector().text(\"Price (high to low)\")")).click();

	    LinkedList<String> prices = new LinkedList<>();
	    for (WebElement el : driver.findElements(AppiumBy.androidUIAutomator(
	        "new UiSelector().textMatches(\"\\$[0-9]+\\.[0-9]{2}\")"
	    ))) {
	        prices.add(el.getText());
	    }

	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	        ".scrollIntoView(new UiSelector().text(\"$15.99\"))")
	    );

	    LinkedList<String> newPrices = new LinkedList<>();
	    for (WebElement el : driver.findElements(AppiumBy.androidUIAutomator(
	        "new UiSelector().textMatches(\"\\$[0-9]+\\.[0-9]{2}\")"
	    ))) {
	        newPrices.add(el.getText());
	    }

	    prices.add(newPrices.get(0));
	    prices.add(newPrices.get(1));

	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	        ".scrollIntoView(new UiSelector().text(\"$9.99\"))")
	    );

	    LinkedList<String> newPrices1 = new LinkedList<>();
	    for (WebElement el : driver.findElements(AppiumBy.androidUIAutomator(
	        "new UiSelector().textMatches(\"\\$[0-9]+\\.[0-9]{2}\")"
	    ))) {
	        newPrices1.add(el.getText());
	    }
	    
	    prices.add(newPrices1.get(0));
	    prices.add(newPrices1.get(1));
	    
	    List<Double> numericPrices = prices.stream()
	        .map(p -> Double.parseDouble(p.replace("$", "")))
	        .collect(Collectors.toList());
	    
	    for (int i = 0; i < numericPrices.size() - 1; i++) {
	        double current = numericPrices.get(i);
	        double next = numericPrices.get(i + 1);

	        Assert.assertTrue(
	            current >= next,
	            "Prices are not sorted correctly: " + numericPrices
	        );
	    }

	    Assert.assertTrue(numericPrices.size() > 0, "No prices were captured!");
	}
	
	@Test
	public void ZtoANameFilter() {
	    // 1. Open the sort menu
	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiSelector().className(\"android.widget.ImageView\").instance(5)")).click();

	    // 2. Select "Name (Z to A)" option
	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiSelector().text(\"Name (Z to A)\")")).click();
	    
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
		        AppiumBy.xpath("//android.widget.TextView[@content-desc=\"test-Item title\"]")));
	    // 3. Capture initially visible product names
	    List<String> names = driver.findElements(AppiumBy.xpath(
	            "//android.widget.TextView[@content-desc=\"test-Item title\"]"))
	        .stream()
	        .map(WebElement::getText)  // get text immediately
	        .collect(Collectors.toList());
	    
	    // 4. Scroll down to make sure all products appear
	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	        ".scrollIntoView(new UiSelector().text(\"Sauce Labs Fleece Jacket\"))")
	    );

	    // 5. Capture again after scroll
	    List<String> names1 = driver.findElements(AppiumBy.xpath(
	            "//android.widget.TextView[@content-desc=\"test-Item title\"]"))
	        .stream()
	        .map(WebElement::getText)  // get text immediately
	        .collect(Collectors.toList());

	    // 6. Merge lists (remove duplicates if needed)
	    names.addAll(names1);
	    
	    driver.findElement(AppiumBy.androidUIAutomator(
	        "new UiScrollable(new UiSelector().scrollable(true))" +
	        ".scrollIntoView(new UiSelector().text(\"Sauce Labs Backpack\"))")
	    );

	    List<String> names2 = driver.findElements(AppiumBy.xpath(
	            "//android.widget.TextView[@content-desc=\"test-Item title\"]"))
	        .stream()
	        .map(WebElement::getText)  
	        .collect(Collectors.toList());

	    names.addAll(names2);
	    
	    List<String> sortedNames = new ArrayList<>(names);
	    sortedNames.sort(Comparator.reverseOrder());

	    Assert.assertEquals(
	    		names,
	        sortedNames,
	        "Product names are not sorted correctly (Z → A): " + names
	    );
	}

}
