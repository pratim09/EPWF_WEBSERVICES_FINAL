package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PRFDeleteSavedPaymentPage {
	
	@FindBy(id="Delete")
	private static WebElement deleteBtn;
	
	@FindBy(id="Cancel")
	private static WebElement cancelBtn;
	
	
	public static WebElement getDeleteButton(){
		return deleteBtn;
	}
	
	public static WebElement getCancelButton(){
		return cancelBtn;
	}

}
