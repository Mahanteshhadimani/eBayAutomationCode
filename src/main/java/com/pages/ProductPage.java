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
public class ProductPage {
	WebDriver driver;
	public WebDriverWait wait;
	BrowserSetup main = new BrowserSetup();

	public ProductPage(AppiumDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}

	@FindBy(how = How.ID, using = "com.ebay.mobile:id/button_add_to_cart")
	WebElement addToCartButton;

	@FindBy(how = How.ID, using = "com.ebay.mobile:id/button_view_in_cart")
	WebElement viewCartButton;

	@FindBy(how = How.XPATH, using = "//*[@class='android.widget.LinearLayout']/*[@id='shopping_cart_checkout' or @text='Checkout' or @index='5']")
	WebElement checkoutButton;

	//Click on the add to cart button
	public void clickOnAddToCart() {
		wait.until(ExpectedConditions.elementToBeClickable((addToCartButton)));
		addToCartButton.click();
	}

	//Click on View cart button
	public void clickOnViewCart() {
		wait.until(ExpectedConditions.elementToBeClickable((viewCartButton)));
		viewCartButton.click();
	}

	//Click on Checkout button
	public void clickOnCheckout() {
		wait.until(ExpectedConditions.elementToBeClickable((checkoutButton)));
		checkoutButton.click();
	}

}
