package com.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.utils.BrowserSetup;
import com.utils.UtilFunctions;

/**
 * @author Mahantesh This class contains Login Page Locators and related methods
 */
public class LoginPage {
	UtilFunctions com;
	public AppiumDriver driver1;
	public WebDriverWait wait;
	BrowserSetup main = new BrowserSetup();
	// Declare webElements
	@FindBy(id = "com.ebay.mobile:id/textview_sign_out_status")
	private WebElement signInLink;

	@FindBy(xpath = "//*[@text='Email or username' or @index='0']")
	private WebElement loginEmail;

	@FindBy(xpath = "//*[@text='Password' or @index='2']")
	private WebElement loginPassword;

	@FindBy(id = "com.ebay.mobile:id/button_google_deny")
	private WebElement denayGoogleAccountLink;

	// Initialization of webElements
	public LoginPage(AppiumDriver driver) {
		driver1 = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}

	//Click on Sign in link
	public void clickOnSignInLink() {
		wait.until(ExpectedConditions.elementToBeClickable((signInLink)));
		signInLink.click();
	}

	// set username on the email feild
	public void setEmail(String Customer_uniqueId) {
		loginEmail.sendKeys(Customer_uniqueId);
	}

	//set password on the password field
	public void setPassword(String Customer_password) {

		loginPassword.sendKeys(Customer_password);
	}

	//Click on Denay google account link
	public void clickDenayGoogleLink() {
		wait.until(ExpectedConditions
				.elementToBeClickable((denayGoogleAccountLink)));
		denayGoogleAccountLink.click();
	}

	public void validLogin(String username, String Password)
			throws InterruptedException {
		com = new UtilFunctions(driver1);
		clickOnSignInLink();
		setEmail(username);
		setPassword(Password);
		com.timeOut(main.MIN_TIME_OUT);
		((PressesKeyCode) driver1).pressKeyCode(AndroidKeyCode.KEYCODE_ENTER);
		clickDenayGoogleLink();
	}

	// driver.findElement(By.xpath("lll")).click();
}
