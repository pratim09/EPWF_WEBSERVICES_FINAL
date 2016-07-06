package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RECAddCardDetailsPage {
	
	@FindBy(id="CARD_EXPIRATION_MONTH")
	private static WebElement monthDrpdwn;
	
	@FindBy(id="CARD_EXPIRATION_YEAR")
	private static WebElement yearDrpdwn;
	
	@FindBy(id="DEBIT_ZIP")
	private static WebElement zipTextBox;
	
	@FindBy(id="Submit9")
	private static WebElement finishBtn;
	
	public static WebElement getExpiryMonthComboBox(){
		return monthDrpdwn;
	}
	
	public static WebElement getExpiryYearComboBox(){
		return yearDrpdwn;
	}
	
	public static WebElement getZipCode(){
		return zipTextBox;
	}
	
	public static WebElement getFinishButton(){
		return finishBtn;
	}
}
