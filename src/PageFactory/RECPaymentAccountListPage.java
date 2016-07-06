package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RECPaymentAccountListPage {

	@FindBy( id= "PageContent_ADD_CC_LINK")
	private static WebElement AddNewCreditCardLink;
	
	
	public static WebElement getAddNewCreditCard(){
		return AddNewCreditCardLink;
	}
}
