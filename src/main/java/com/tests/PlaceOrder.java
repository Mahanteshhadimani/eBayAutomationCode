package com.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.utils.BrowserSetup;
import com.utils.CommonMethods;

/**
 * @author Mahantesh 
 * This class contains placing order test case
 */
public class PlaceOrder extends BrowserSetup {
	CommonMethods cm;
    
	@Test(priority = 1, enabled = true, dataProvider = "loginData")
	public void placeOrderTestCase(String uID, String pass)
			throws InterruptedException {
		cm = new CommonMethods(driver);
		// Calling eBayLogin method by using username and password
		cm.eBayLogin(uID, pass);

		// Calling eBaySearchAnItem method
		cm.eBaySearchAnItem();

		// Calling eBayAddItemToCart method
		cm.eBayAddItemToCart();

		// Calling eBayAddItemToCart method
		cm.eBayAddItemToCart();

		// Calling eBayPlaceOrder method
		cm.eBayPlaceOrder();
	}

	//This method reads user crendentials from loginTestData.xlsx file and passes to test method
	@DataProvider(name = "loginData")
	public Object[][] userFormData() throws Exception {

		Object[][] data = com.testData(xlFilePath, SHEET_NAME);
		return data;
	}

}
