package com.utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

//**********************************************************************************************************
//Author: Mahantesh Hadimani
//Description: This class contains commonly used methods and MobileActions such as swipe, tap, multitouch, etc.
//**********************************************************************************************************

public class UtilFunctions extends BrowserSetup{
	BrowserSetup main = new BrowserSetup();
	Workbook myWorkbook = null;
	private AppiumDriver driver;
	public WebDriverWait wait;
	

	public UtilFunctions(AppiumDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}

	public UtilFunctions() {
	}

	// Read Data from excel
	public Object[][] testData(String xlFilePath, String sheetName)
			throws Exception {

		Object[][] excelData = null;
		FileInputStream inputStream = new FileInputStream(xlFilePath);
		myWorkbook = new XSSFWorkbook(inputStream);
		Sheet mySheet = myWorkbook.getSheet(sheetName);
		int rowCount = mySheet.getLastRowNum() - mySheet.getFirstRowNum();
		excelData = new Object[rowCount][2];

		for (int i = 1; i <= rowCount; i++) {
			Row row = mySheet.getRow(i);
			for (int j = 0; j < 2; j++) {
				excelData[i - 1][j] = row.getCell(j).getStringCellValue();
				System.out.print(row.getCell(j).getStringCellValue() + "|| ");
			}
			System.out.println();
		}
		return excelData;
	}

	private static AppiumDriverLocalService service;

	//Method for starting the Appium Server
	public static void startServer() {
		AppiumServiceBuilder builder;
		BrowserSetup.logger.info("Building and starting the server:");
		builder = new AppiumServiceBuilder();
		builder.usingPort(4725);
		// builder.withCapabilities(capabilities);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		BrowserSetup.logger.info("Server started on Port - " + 4725);
	}
    
	//Method for stopping the Appium Server
	public static void stopServer() {
		try {
			BrowserSetup.logger.info("Trying to stop the server...");
			service.stop();
			BrowserSetup.logger.info("Success, Server stopped.");
		} catch (Exception e) {
			BrowserSetup.logger.info("Appium server could not be stopped.");
		}
	}

	//Method for taking the failed screenshot
	public static void getFailedTestCaseScreenShot(AppiumDriver driver,
			String testCaseName) {
		try {
			Date date = new Date();
			String currentDate = date.toString().replaceAll(":", "_");
			File srcFile = ((TakesScreenshot) driver)
					.getScreenshotAs(OutputType.FILE);
			FileHandler.copy(srcFile, new File("./failedTestScreenShot/"
					+ testCaseName + "_" + currentDate + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TimeOut method
	public void timeOut(int unit) throws InterruptedException {
		Thread.sleep(unit);
	}

	// Tap to an element
	public void tapByElement(AppiumDriver driver2, By element) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(element))
				.click();
	}

	// enter text on text box
	public void enterText(AppiumDriver driver2, By element, String data) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(element))
				.sendKeys(data);
		;
	}

	// Tap by coordinates
	public void tapByCoordinates(int x, int y) {
		new TouchAction(driver).tap(point(x, y))
				.waitAction(waitOptions(Duration.ofMillis(250))).perform();
	}

	// Press by element
	public void pressByElement(AndroidElement element, long seconds) {
		new TouchAction(driver).press(element(element))
				.waitAction(waitOptions(ofSeconds(seconds))).release()
				.perform();
	}

	// Press by coordinates
	public void pressByCoordinates(int x, int y, long seconds) {
		new TouchAction(driver).press(point(x, y))
				.waitAction(waitOptions(ofSeconds(seconds))).release()
				.perform();
	}

	// Horizontal Swipe by percentages
	public void horizontalSwipeByPercentage(double startPercentage,
			double endPercentage, double anchorPercentage) {
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.height * anchorPercentage);
		int startPoint = (int) (size.width * startPercentage);
		int endPoint = (int) (size.width * endPercentage);

		new TouchAction(driver).press(point(startPoint, anchor))
				.waitAction(waitOptions(ofMillis(1000)))
				.moveTo(point(endPoint, anchor)).release().perform();
	}

	// Vertical Swipe by percentages
	public void verticalSwipeByPercentages(double startPercentage,
			double endPercentage, double anchorPercentage) {
		Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.width * anchorPercentage);
		int startPoint = (int) (size.height * startPercentage);
		int endPoint = (int) (size.height * endPercentage);

		new TouchAction(driver).press(point(anchor, startPoint))
				.waitAction(waitOptions(ofMillis(1000)))
				.moveTo(point(anchor, endPoint)).release().perform();
	}

	// Swipe by elements
	public void swipeByElements(AndroidElement startElement,
			AndroidElement endElement) {
		int startX = startElement.getLocation().getX()
				+ (startElement.getSize().getWidth() / 2);
		int startY = startElement.getLocation().getY()
				+ (startElement.getSize().getHeight() / 2);

		int endX = endElement.getLocation().getX()
				+ (endElement.getSize().getWidth() / 2);
		int endY = endElement.getLocation().getY()
				+ (endElement.getSize().getHeight() / 2);

		new TouchAction(driver).press(point(startX, startY))
				.waitAction(waitOptions(ofMillis(1000)))
				.moveTo(point(endX, endY)).release().perform();
	}

	// Multitouch action by using an android element
	public void multiTouchByElement(AndroidElement androidElement) {
		TouchAction press = new TouchAction(driver)
				.press(element(androidElement))
				.waitAction(waitOptions(ofSeconds(1))).release();

		new MultiTouchAction(driver).add(press).perform();
	}
}