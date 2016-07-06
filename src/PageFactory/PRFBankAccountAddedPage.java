package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PRFBankAccountAddedPage {
	
	@FindBy(xpath="//a[contains(text(),'saved payment accounts')]")
	private static WebElement savedPaymentAccountLink;
	
	public static WebElement getsavedPaymentAccountLink(){
		return savedPaymentAccountLink;
	}

}
