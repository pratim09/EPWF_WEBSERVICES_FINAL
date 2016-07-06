package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HCDETokenizationDetailspage {

	@FindBy(id ="custName")
	private static WebElement nameOnCardTextBox;
	
	@FindBy(id = "cardNum")
	private static WebElement cardNumTextBox;
	
	@FindBy(id = "expiryMonth")
	private static WebElement expiryMonthdrpDwn;
	
	@FindBy(id = "expiryYear")
	private static WebElement expiryYeardrpDwn;
	
	@FindBy(id = "cvc")
	private static WebElement cvvTextBox;
	
	@FindBy(id = "Continue")
	private static WebElement submitBtn;
	
	
	public static WebElement getNameonCardTextBox(){
		return nameOnCardTextBox;
	}
	
	public static WebElement getcardNumTextBox(){
		return cardNumTextBox;
	}
	
	public static WebElement getexpiryMonthdrpDwn(){
		return expiryMonthdrpDwn;
	}
	
	public static WebElement getexpiryYeardrpDwn(){
		return expiryYeardrpDwn;
	}
	
	public static WebElement getcvvTextBox(){
		return cvvTextBox;
	}
	
	public static WebElement getsubmitBtn(){
		return submitBtn;
	}
}
