package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ACTCardDetailsPage {

	@FindBy(id="form1:creditCardNumber")
	private static WebElement cardNumberTextBox;
	
	@FindBy(id="form1:expiryMonth")
	private static WebElement monthDrpDwn;
	
	@FindBy(id="form1:expiryYear")
	private static WebElement yearDrpDwn;
	
	@FindBy(id="form1:saveButton")
	private static WebElement saveBtn;
	
	@FindBy(id="form1:zipCode")
	private static WebElement zipCodeTextBox;
	
	public static WebElement getCardNumber(){
		return cardNumberTextBox;	
	}
	
	public static WebElement getExpiryMonthComboBox(){
		return monthDrpDwn;	
	}
	
	public static WebElement getExpiryYearComboBox(){
		return yearDrpDwn;
	}
	
	public static WebElement getZipCodeTextBox(){
		return zipCodeTextBox;
	}
	
	public static WebElement getSaveButton(){
		return saveBtn;
	}
}
