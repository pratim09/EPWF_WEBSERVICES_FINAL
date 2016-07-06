package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IBMPaymentSelectPage {

	@FindBy(id = "form1:achSelectLink")
	private static WebElement bankAccountLink;

	@FindBy(id = "form1:cardSelectLink")
	private static WebElement cardLink;

	public static WebElement getCardLink() {
		return cardLink;
	}

	public static WebElement getBankAccountLink() {
		return bankAccountLink;
	}

}
