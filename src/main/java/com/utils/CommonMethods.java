package com.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PressesKeyCode;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.pages.PaymentPage;
import com.pages.ProductPage;
import com.pages.SearchPage;

/**
 * @author Mahantesh 
 * This class contains common reusable functions like login, search item etc..
 */
public class CommonMethods extends BrowserSetup{
	BrowserSetup main = new BrowserSetup();
	Workbook myWorkbook = null;
	private AppiumDriver driver;
	public WebDriverWait wait;
	HomePage hPage;
	LoginPage lPage;
	SearchPage sPage;
	ProductPage pPage;
	PaymentPage payPage;

	public CommonMethods(AppiumDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}
	
    //Method for Login functionality
	public void eBayLogin(String uID, String pass) throws InterruptedException {
		hPage = new HomePage(driver);
		lPage = new LoginPage(driver);
		sPage = new SearchPage(driver);
		pPage = new ProductPage(driver);
		payPage = new PaymentPage(driver);
		hPage.clickOnMainMenu();
		lPage.validLogin(uID, pass);
		logger.info("Sign in completed");
	}
	
	//Method for searching the products
	public void eBaySearchAnItem() throws InterruptedException {
		// Click on search Text Box
		hPage.clickSearchTextBox();
		// Enter search keyword
		hPage.setSearchKeyword(SEARCH_TERM);
		// Press Enter key
		((PressesKeyCode) driver).pressKeyCode(66);
		sPage.clickOnProductLink();
		logger.info("Searching item completed");
	}
	
	//Method for adding item to Cart
	public void eBayAddItemToCart() {
		try {
			pPage.clickOnAddToCart();
			pPage.clickOnViewCart();

		} catch (Exception ex) {
			logger.info("item already exist in the cart");
			pPage.clickOnViewCart();
			eBayPlaceOrder();

		}
	}
	
	//Method for placing the order
	public void eBayPlaceOrder() {
		pPage.clickOnCheckout();
		payPage.clickOnPaymentLink();
		payPage.clickOnselectCCPayment();
		logger.info("Able to go till Payment select page");
	}
}
