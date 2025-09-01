package org.Appium_Mobile_Testing.SwagLab_App;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class LoginPage extends BasicConfig {
    String Error_Message;

    // Helper method to login
    public void login(String username, String password) {
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys(username);
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys(password);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
    }
    // Helper method to Perform LongPress Gesture
    public void LongPressGesture(RemoteWebElement Elem) {
    		driver.executeScript("mobile: longClickGesture", Map.of(
    		    "elementId", Elem.getId(),
    		    "duration", 2000  // 2 seconds
    		));
    }
    
    @BeforeMethod
    public void SetLoginPage() {
        driver.terminateApp("com.swaglabsmobileapp");
        driver.activateApp("com.swaglabsmobileapp");
    }
    
    @Test
    public void VerifyLoginPageElements() {
    		WebElement SwagLabLogo = driver.findElement(
    				AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)"));
    		
    		WebElement userField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]"));
    		String UserNameplaceholder = userField.getAttribute("hint");
    		
    		WebElement passField = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]"));
    		String Passwordplaceholder = passField.getAttribute("hint");
    		
    		WebElement LoginButton  = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")"));
    		WebElement SwagLabPhoto = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(1)"));
    		
    		Assert.assertTrue(SwagLabLogo.isDisplayed() 
    			      && UserNameplaceholder.equals("Username") 
    			      && Passwordplaceholder.equals("Password")
    			      && LoginButton.isDisplayed()
    			      && SwagLabPhoto.isDisplayed(), 
    			      "Login screen elements are missing");
    }
    
	@Test
    public void LoginPositive() {
        login("standard_user", "secret_sauce");
        WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
        Assert.assertTrue(Product.isDisplayed());
    }

    @Test
    public void LoginNegative_BlankUserName() {
        login("", "secret_sauce");
        Error_Message = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Username is required\"]")).getText();
        Assert.assertEquals(Error_Message, "Username is required");
    }

    @Test
    public void LoginNegative_BlankPassword() {
        login("standard_user", "");
        Error_Message = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Password is required\"]")).getText();
        Assert.assertEquals(Error_Message, "Password is required");
    }
    
    @Test
    public void LoginNegative_BlankUserNameandPassword() {
        login("", "");
        Error_Message = driver.findElement(By.xpath("//android.widget.TextView[@text=\"Username is required\"]")).getText();
        Assert.assertEquals(Error_Message, "Username is required");
    }

    @Test
    public void LoginNegative_WrongUserName() {
        login("WrongUserName", "secret_sauce");
        Error_Message = driver.findElement(
            By.xpath("//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
        ).getText();
        Assert.assertTrue(Error_Message.contains("Username and password do not match"));
    }
    
    @Test
    public void LoginNegative_WrongPasseord() {
        login("standard_user", "WrongPassword");
        Error_Message = driver.findElement(
            By.xpath("//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]")
        ).getText();
        Assert.assertTrue(Error_Message.contains("Username and password do not match"));
    }
    
    @Test
    public void LoginNegative_LockedOutUser() {
        login("locked_out_user", "secret_sauce");
        Error_Message = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Sorry, this user has been locked out.\")")).getText();
        Assert.assertTrue(Error_Message.contains("locked out"));
    }
    
    @Test
    public void LoginPositive_CopyandPastFunctionality() throws InterruptedException {
    		driver.setClipboardText("standard_user");
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Password\")")).sendKeys("secret_sauce");
        
        RemoteWebElement ElemtoLongPress = (RemoteWebElement) driver.findElement(AppiumBy.accessibilityId("test-Username"));
        ElemtoLongPress.click();
        LongPressGesture(ElemtoLongPress);
      
		driver.pressKey(new KeyEvent(AndroidKey.PASTE));
		Thread.sleep(1000);
		driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOGIN\")")).click();
		WebElement Product =  driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"PRODUCTS\")"));
        Assert.assertTrue(Product.isDisplayed());
    }
}
