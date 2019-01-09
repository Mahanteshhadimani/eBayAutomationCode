package Appium.MobileAutomation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PressesKeyCode;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;
import objectRepository.ObjectRepo;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.GenericMethods;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//**********************************************************************************************************
//Author: Mahantesh Hadimani
//Description: This class contains initializing the appium and executing the tests.
//**********************************************************************************************************

public class EbayApp {
	private static final Logger logger = Logger.getLogger(EbayApp.class);
	public static AppiumDriver driver;
	public WebDriverWait wait;
	ObjectRepo obj = new ObjectRepo();
	GenericMethods com;
	String xlFilePath = System.getProperty("user.dir")
			+ "/src/main/java/testData/loginTestData.xlsx";
	Workbook myWorkbook = null;
	String sheetName = "TestData";
	protected static Properties properties;

	public static String DEVICE_NAME;
	public static int TIME_OUT;
	public static String UDID;
	public static String PLATFORM_VERSION;
	public static String PLATFORM_Name;
	public static String APP_PACKAGE;
	public static String APP_ACTIVITY;
	public static String APPIUM_SERVER_URL;

	public static String BASE_URL;
	public static String USERNAME;
	public static String PASSWORD;
	public static String SEARCH_TERM;

	@BeforeTest
	public void setup() throws Exception {
		// Opening the app and
		properties = new Properties();
		try {

			properties.load(new FileInputStream(
					"src/main/java/testData/test.properties"));

			TIME_OUT = Integer.parseInt(properties.getProperty("TIME_OUT"));
			USERNAME = properties.getProperty("USERNAME");
			PASSWORD = properties.getProperty("PASSWORD");
			DEVICE_NAME = properties.getProperty("DEVICE_NAME");
			PLATFORM_Name = properties.getProperty("PLATFORM_Name");
			APP_PACKAGE = properties.getProperty("APP_PACKAGE");
			APP_ACTIVITY = properties.getProperty("APP_ACTIVITY");
			APPIUM_SERVER_URL = properties.getProperty("APPIUM_SERVER_URL");
			SEARCH_TERM = properties.getProperty("SEARCH_TERM");

			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("deviceName", DEVICE_NAME);
			caps.setCapability("platformName", PLATFORM_Name);
			caps.setCapability("platformVersion", "5.0.2");
			caps.setCapability("skipUnlock", "true");
			caps.setCapability("appPackage", APP_PACKAGE);
			caps.setCapability("appActivity", APP_ACTIVITY);
			caps.setCapability("noReset", "false");
			driver = new AndroidDriver(new URL(APPIUM_SERVER_URL + "/wd/hub"),
					caps);
			logger.info("Starting eBay App ");
			driver.manage().timeouts()
					.implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, TIME_OUT);
			com = new GenericMethods(driver);
			//switch to portrait mode
			driver.rotate(ScreenOrientation.PORTRAIT);

		} catch (IOException e) {
			logger.fatal(e);
			System.out.println(e);
			// System.exit(0);
		}
	}

	@Test(priority = 0, dataProvider = "loginData", enabled = true)
	public void eBayLogin(String userName, String passWord)
			throws InterruptedException {

		// Click on Main menu
		com.tapByElement(driver, obj.MainMenu);

		// Click on sign in link
		com.tapByElement(driver, obj.signInLink);

		// Enter email
		com.enterText(driver, obj.loginEmail, userName);

		Thread.sleep(3000);
		WebElement pass = driver.findElement(By.xpath(obj.loginPassword1));
		pass.sendKeys(passWord);
		Thread.sleep(3000);
		((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.KEYCODE_ENTER);
		// Click on denay google accounting linking link
		com.tapByElement(driver, obj.denayGoogleAccountLink);
		logger.info("Sign in completed");
	}

	@Test(priority = 2, enabled = true, dependsOnMethods = { "eBayLogin" })
	public void eBaySearchAnItem() throws InterruptedException {
		// Enter email
		// Click on sign in link
		com.tapByElement(driver, obj.searchBox);

		// Enter search keyword
		com.enterText(driver, obj.mainSearchBox, SEARCH_TERM);

		((PressesKeyCode) driver).pressKeyCode(66);
		// ((PressesKeyCode) driver).pressKeyCode(AndroidKeyCode.KEYCODE_ENTER);
		Thread.sleep(5000);
		com.tapByElement(driver, obj.productLink);
		logger.info("Searching item completed");

	}

	@Test(priority = 3, enabled = true, dependsOnMethods = { "eBaySearchAnItem" })
	public void eBayAddItemToCart() {
		try {
			com.tapByElement(driver, obj.addToCartButton);
			com.tapByElement(driver, obj.viewCartButton);

		} catch (Exception ex) {
			logger.info("item already exist in the cart");
			com.tapByElement(driver, obj.viewCartButton);

		}
	}

	@Test(priority = 4, enabled = false)
	public void eBayPlaceOrder() {
		com.tapByElement(driver, obj.checkoutButton);

		WebElement payment = driver.findElement(By.id(obj.paymentLink1));
		Actions action = new Actions(driver);
		action.moveToElement(payment).build().perform();
		payment.click();
		com.tapByElement(driver, obj.selectCCPayment);
		logger.info("Able to go till Payment select page");
	}

	@AfterSuite
	public void teardown() {
		driver.quit();
	}

	@DataProvider(name = "loginData")
	public Object[][] userFormData() throws Exception {

		Object[][] data = com.testData(xlFilePath, sheetName);
		return data;
	}

}