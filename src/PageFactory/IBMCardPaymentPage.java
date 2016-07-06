package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IBMCardPaymentPage {
	
	@FindBy(id = "form1:creditCardNumber")
	private static WebElement cardNumberTextBox;
	
	@FindBy(id = "form1:expiryMonth")
	private static WebElement expiryMonthDropDown;
	
	@FindBy(id = "form1:expiryYear")
	private static WebElement expiryYearDropDown;
	
	@FindBy(id = "form1:zipCode")
	private static WebElement zipCodeTextBox;
	
	@FindBy(id = "form1:doPaymentButton")
	private static WebElement submitButton;

	public static WebElement getCardNumberTextBox() {
		return cardNumberTextBox;
	}

	public static WebElement getExpiryMonthDropDown() {
		return expiryMonthDropDown;
	}

	public static WebElement getExpiryYearDropDown() {
		return expiryYearDropDown;
	}

	public static WebElement getZipCodeTextBox() {
		return zipCodeTextBox;
	}

	public static WebElement getSubmitButton() {
		return submitButton;
	}
	
}
