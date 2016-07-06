package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PRFPaymentMethodPage {
	
	@FindBy(id="selectedInstrument")
	private static WebElement storedAccountDrpdwn;
	
	@FindBy(linkText="Delete Selected Account")
	private static WebElement deletedSelectedAccountBtn;
	
	@FindBy(linkText="Update Selected Account")
	private static WebElement updateSelectedAccountBtn;
	
	@FindBy(linkText="Add Bank Account")
	private static WebElement addBankAQccountBtn;
	
	@FindBy(linkText="Add Credit/Debit Card")
	private static WebElement addCardBtn;
	
	public static WebElement getStoreAccountDrpdwn(){
		return storedAccountDrpdwn;
	}
	
	public static WebElement getDeleteAccountButton(){
		return deletedSelectedAccountBtn;
	}
	
	public static WebElement getUpdateAccountButton() {
		return updateSelectedAccountBtn;
	}

	public static WebElement getAddBankAccountButton(){
		return addBankAQccountBtn;
	}
	
	public static WebElement getCardButton(){
		return addCardBtn;
	}
}
