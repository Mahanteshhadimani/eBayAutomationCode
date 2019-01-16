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
 * @author Mahantesh This class contains Login Page Locators and related methods
 */
public class HomePage {
	WebDriver driver;
	public WebDriverWait wait;
	BrowserSetup main = new BrowserSetup();

	public HomePage(AppiumDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}

	@FindBy(how = How.ID, using = "com.ebay.mobile:id/home")
	WebElement mainMenu;

	@FindBy(how = How.ID, using = "com.ebay.mobile:id/search_box")
	WebElement searchBox;

	@FindBy(how = How.ID, using = "com.ebay.mobile:id/search_src_text")
	WebElement mainSearchBox;

	public void clickOnMainMenu() {
		wait.until(ExpectedConditions.elementToBeClickable((mainMenu)));
		mainMenu.click();
	}

	public void clickSearchTextBox() {
		wait.until(ExpectedConditions.elementToBeClickable((searchBox)));
		searchBox.click();
	}

	public void setSearchKeyword(String searchKeyWord) {
		mainSearchBox.sendKeys(searchKeyWord);
	}

}
