package com.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

/**
 * @author Mahantesh 
 * This class contains appium setup
 */
public class BrowserSetup {

	protected static final Logger logger = Logger.getLogger(BrowserSetup.class);
	public static AppiumDriver driver;
	public WebDriverWait wait;
	protected UtilFunctions com;
	public String xlFilePath = System.getProperty("user.dir")
			+ "/src/main/java/com/testData/loginTestData.xlsx";
	Workbook myWorkbook = null;
	protected static Properties properties;
	public static String DEVICE_NAME;
	public static int TIME_OUT;
	public static int MIN_TIME_OUT;
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
	protected static String SHEET_NAME;
	protected static int PORT_NUMBER;

	//Method setting up the appium server
	@BeforeTest
	public void setUp() throws Exception {
		// Opening the app and
		properties = new Properties();
		try {
			com = new UtilFunctions();
			logger.info("Appium server - Opening");
			com.startServer();
			logger.info("Appium server - Started");
			properties.load(new FileInputStream(
					"src/main/java/com/testData/test.properties"));
			TIME_OUT = Integer.parseInt(properties.getProperty("TIME_OUT"));
			MIN_TIME_OUT = Integer.parseInt(properties
					.getProperty("MIN_TIME_OUT"));
			USERNAME = properties.getProperty("USERNAME");
			PASSWORD = properties.getProperty("PASSWORD");
			DEVICE_NAME = properties.getProperty("DEVICE_NAME");
			PLATFORM_Name = properties.getProperty("PLATFORM_Name");
			APP_PACKAGE = properties.getProperty("APP_PACKAGE");
			APP_ACTIVITY = properties.getProperty("APP_ACTIVITY");
			APPIUM_SERVER_URL = properties.getProperty("APPIUM_SERVER_URL");
			SEARCH_TERM = properties.getProperty("SEARCH_TERM");
			SHEET_NAME = properties.getProperty("SHEET_NAME");
			
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
			com = new UtilFunctions(driver);
			logger.info("Starting eBay App ");
			driver.manage().timeouts()
					.implicitlyWait(TIME_OUT, TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, TIME_OUT);
			// switch to portrait mode
			driver.rotate(ScreenOrientation.PORTRAIT);

		} catch (IOException e) {
			logger.fatal(e);
			System.out.println(e);
		}
	}

	//Method which will take screenshot post test fail
	@AfterMethod
	public void takeScreenshot(ITestResult result) {
		try {
			if (ITestResult.FAILURE == result.getStatus()) {
				UtilFunctions.getFailedTestCaseScreenShot(driver,
						result.getName());
				Thread.sleep(5000);
				
			}
		} catch (Exception e) {

		}

	}

	//Method to close the app
	@AfterTest
	public void closeBrowser() {
		driver.close();
		logger.info("Appium server - Closing");
		com.stopServer();
		logger.info("Appium server - Closed");
	}
}
