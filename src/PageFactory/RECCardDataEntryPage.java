package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RECCardDataEntryPage {
	
	@FindBy(id="NICKNAME")
	private static WebElement nickNameTextBox;
	
	@FindBy(id="DEBIT_ACCOUNT")
	private static WebElement cardNumberTextBox;
	
	@FindBy(id="Next")
	private static WebElement nextBtn;
	
	
	public static WebElement getNickName(){
		return nickNameTextBox;
	}
	
	public static WebElement getcardNumber(){
		return cardNumberTextBox;	
	}
	
	public static WebElement getNextButton(){
		return nextBtn;	
	}

}
