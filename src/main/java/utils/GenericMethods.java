package utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Appium.MobileAutomation.EbayApp;

import java.time.Duration;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

//**********************************************************************************************************
//Author: Mahantesh Hadimani
//Description: This class contains commonly used methods and MobileActions such as swipe, tap, multitouch, etc.
//**********************************************************************************************************

public class GenericMethods {
	EbayApp main=new EbayApp();
	Workbook myWorkbook = null;
	private AppiumDriver driver;
	public WebDriverWait wait;
	

	public GenericMethods(AppiumDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, main.TIME_OUT);
	}

	//Read Data from excel
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

	// Tap to an element 
	public void tapByElement(AppiumDriver driver2, By element) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(element)).click();
	}
	
	// enter text on text box 
		public void enterText(AppiumDriver driver2, By element,String data) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(element)).sendKeys(data);;
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