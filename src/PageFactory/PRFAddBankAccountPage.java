package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PRFAddBankAccountPage {
	
	@FindBy(id="routingNumber")
	private static WebElement routingNumberTextBox;
	
	@FindBy(id="accountNumber")
	private static WebElement accountNumberTextbox;
	
	@FindBy(id="bankAccountType")
	private static WebElement bankAccountTypeDrpdwn;
	
	@FindBy(id="Add")
	private static WebElement saveBtn;
	
	@FindBy(id="Cancel")
	private static WebElement cancelLink;
	
	
	public static WebElement getRoutingNumberTextBox(){
		return routingNumberTextBox;
	}
	
	public static WebElement getAccountNumberTextBox(){
		return accountNumberTextbox;
	}
	
	public static WebElement getBankAccountTyoeDrpdwn(){
		return bankAccountTypeDrpdwn;
	}
	
	public static WebElement getSaveButton(){
		return saveBtn;
	}
	
	public static WebElement getCancelLink(){
		return cancelLink;
	}

}
