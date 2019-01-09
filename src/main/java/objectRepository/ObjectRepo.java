package objectRepository;

import org.openqa.selenium.By;
//**********************************************************************************************************
//Author: Mahantesh Hadimani
//Description: This class contains all the locators
//**********************************************************************************************************
public class ObjectRepo {
	
	public By MainMenu = By.id("com.ebay.mobile:id/home");
	public By signInLink = By.id("com.ebay.mobile:id/textview_sign_out_status");
	public By loginEmail = By.xpath("//*[@text='Email or username' or @index='0']");
	public By loginPassword = By.xpath("//*[@text='Password' or @index='2']");
	public By denayGoogleAccountLink = By.id("com.ebay.mobile:id/button_google_deny");
	public static final String loginPassword1="//*[@text='Password' or @index='2']";
	
	public By searchBox = By.id("com.ebay.mobile:id/search_box");
	public By mainSearchBox = By.id("com.ebay.mobile:id/search_src_text");
	public By productLink = By.xpath("//*[@index=2]");
	public By productLink1 = By.id("com.ebay.mobile:id/cell_collection_item");
	public By productLink2 = By.xpath("(//*[@class='android.widget.RelativeLayout' or @id='cell_collection_item'])[3]");
	public By addToCartButton = By.id("com.ebay.mobile:id/button_add_to_cart");
	public By viewCartButton = By.id("com.ebay.mobile:id/button_view_in_cart");
	public By checkoutButton = By.id("com.ebay.mobile:id/shopping_cart_checkout");
	public By paymentLink = By.id("com.ebay.mobile:id/payment_main_text");
	public static final String paymentLink1="com.ebay.mobile:id/payment_main_text";
	public By selectCCPayment = By.id("com.ebay.mobile:id/payment_method_name");
}