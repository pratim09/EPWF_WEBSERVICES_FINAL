package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PRFUpdatePaymentMethodPage {
	
	@FindBy(id="expiryMonth")
	private static WebElement monthDrpdwn;
	
	@FindBy(id="expiryYear")
	private static WebElement yearDrpdwn;
	
	@FindBy(id="cardZipCode")
	private static WebElement zipCodeTextBox;
	
	@FindBy(id="Update")
	private static WebElement saveBtn;
	
	
	public static WebElement getMonthDrpdwn(){
		return monthDrpdwn;
	}

	public static WebElement getYearDrpdwn(){
		return yearDrpdwn;
	}
	
	public static WebElement getZipCodeTextBox(){
		return zipCodeTextBox;
	}
	
	public static WebElement getSaveButton(){
		return saveBtn;
	}
}
