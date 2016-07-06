package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ACTAccountDetailsPage {
	
	@FindBy(id="form1:routingNumber")
	private static WebElement routingNumberTextBox;
	
	@FindBy(id="form1:accountNumber")
	private static WebElement accountNumberTextBox;
	
	@FindBy(id="form1:saveButton")
	private static WebElement saveBtn;
	
	public static WebElement getRoutingNumber(){
		return routingNumberTextBox;
	}
	
	public static WebElement getAccountNumber(){
		return accountNumberTextBox;	
	}
	
	public static WebElement getSaveButton(){
		return saveBtn;
	}

}
