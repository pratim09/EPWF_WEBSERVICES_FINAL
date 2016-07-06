package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ACTSelectAPaymentMethodPage {
	
	@FindBy(id="form1:achSelectLink")
	private static WebElement newBankAcntlink;
	
	@FindBy(id="form1:cardSelectLink")
	private static WebElement newCardlink;	
	
	@FindBy(id="form1:doDoneButton")
	private static WebElement doneBtn;	
	
	public static WebElement getbankAccount(){
		return newBankAcntlink;
	}
	
	public static WebElement getNewCard(){
		return newCardlink;		
	}
	
	public static WebElement getDoneButton(){
		return doneBtn;		
	}

}
