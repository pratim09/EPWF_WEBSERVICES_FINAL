package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IBMBankPaymentPage {
	
	@FindBy(id = "form1:routingNumber")
	private static WebElement routingNumberTextBox;
	
	@FindBy(id = "form1:accountNumber")
	private static WebElement accountNumberTextBox;
	
	@FindBy(id = "form1:j_id30Savings")
	private static WebElement savingsRadio;
	
	@FindBy(id = "form1:j_id30Checking")
	private static WebElement checkingsRadio;
	
	@FindBy(id = "form1:j_id30MoneyMarket")
	private static WebElement moneyMarketRadio;
	
	@FindBy(id = "form1:agree")
	private static WebElement AgreeCheckbox;
	
	@FindBy(id = "form1:doPaymentButton")
	private static WebElement SubmitButton;

	public static WebElement getRoutingNumberTextBox() {
		return routingNumberTextBox;
	}

	public static WebElement getAccountNumberTextBox() {
		return accountNumberTextBox;
	}

	public static WebElement getSavingsRadio() {
		return savingsRadio;
	}

	public static WebElement getCheckingsRadio() {
		return checkingsRadio;
	}

	public static WebElement getMoneyMarketRadio() {
		return moneyMarketRadio;
	}

	public static WebElement getAgreeCheckbox() {
		return AgreeCheckbox;
	}

	public static WebElement getSubmitButton() {
		return SubmitButton;
	}

}
