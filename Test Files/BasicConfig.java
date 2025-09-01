package org.Appium_Mobile_Testing.SwagLab_App;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;

public class BasicConfig {

	public AndroidDriver driver; 
	public WebDriverWait wait;
	
	@BeforeClass
	public void AppiumConfig() throws MalformedURLException, URISyntaxException { 
		DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appium:platformName", "Android");
        caps.setCapability("appium:deviceName", "YazanDevice");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("appium:appPackage", "com.swaglabsmobileapp");
        caps.setCapability("appium:appActivity", ".MainActivity");
        caps.setCapability("appium:noReset", false);
        caps.setCapability("appium:forceAppLaunch", true);
        caps.setCapability("appium:chromedriverExecutable", "F:\\chromedriver.exe");
        caps.setCapability("appium:autoGrantPermissions", true);
        
        driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(),caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}
	
	@AfterClass
	public void AppiumTearDown() {
		driver.quit();
	}
}
