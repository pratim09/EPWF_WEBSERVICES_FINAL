package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PRFAddCardPage {
	
	@FindBy(id="cardNumber")
	private static WebElement cardNumbertextBox;
	
	@FindBy(id="expiryMonth")
	private static WebElement monthDrpDwn;
	
	@FindBy(id="expiryYear")
	private static WebElement yearDrpDwn;
	
	@FindBy(id="cardZipCode")
	private static WebElement zipCodeTextBox;
	
	@FindBy(id="Add")
	private static WebElement saveBtn;
	
	@FindBy(id="Cancel")
	private static WebElement cancelLink;

	
	public static WebElement getcardNumberTextBOx(){
		return cardNumbertextBox;		
	}
	
	public static WebElement getmonthDropdwon(){
		return monthDrpDwn;
	}
	
	public static WebElement getYearDropdown(){
		return yearDrpDwn;
	}
	
	public static WebElement getZipcodeTextBox(){
		return zipCodeTextBox;
	}
	
	public static WebElement getSaveButton(){
		return saveBtn;
	}
	
	public static WebElement getCancelLink(){
		return cancelLink;
	}
}
