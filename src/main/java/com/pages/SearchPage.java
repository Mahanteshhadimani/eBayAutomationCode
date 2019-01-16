package com.pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.utils.BrowserSetup;

/**
 * @author Mahantesh This class contains Search Page Locators and related
 *         methods
 */
public class SearchPage {
	WebDriver driver;
	public WebDriverWait wait;
	BrowserSetup main = new BrowserSetup();

	public SearchPage(AppiumDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}

	@FindBy(xpath = "//android.support.v7.widget.RecyclerView[@resource-id='com.ebay.mobile:id/recycler']/*[@index='2']")
	WebElement productLink;

	//Click on the product link
	public void clickOnProductLink() {
		wait.until(ExpectedConditions.elementToBeClickable((productLink)));
		productLink.click();
	}

}
