package com.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.BrowserSetup;

/**
 * @author Mahantesh This class contains Payment Page Locators and related
 *         methods
 */
public class PaymentPage {
	WebDriver driver;
	public WebDriverWait wait;
	BrowserSetup main = new BrowserSetup();

	public PaymentPage(AppiumDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}

	@FindBy(how = How.ID, using = "com.ebay.mobile:id/payment_main_text")
	WebElement paymentLink;

	@FindBy(how = How.ID, using = "com.ebay.mobile:id/payment_method_name")
	WebElement selectCCPayment;

	//Click on Payment link
	public void clickOnPaymentLink() {
		wait.until(ExpectedConditions.elementToBeClickable((paymentLink)));
		paymentLink.click();
	}

	//Click on select CC payment method
	public void clickOnselectCCPayment() {
		wait.until(ExpectedConditions.elementToBeClickable((selectCCPayment)));
		selectCCPayment.click();
	}

}
