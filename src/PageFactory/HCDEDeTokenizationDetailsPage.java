package PageFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HCDEDeTokenizationDetailsPage {
@FindBy(id="Done")
private static WebElement detokenizationDonebtn;
@FindBy(id="Continue")
private static WebElement detokenizationcontinuebtn;
@FindBy(id = "cardNum")
private static WebElement cardNumTextBox;

public static WebElement getcardNumTextBox(){
	return cardNumTextBox;
}

public static WebElement getdetokenizationcontinuebtn(){
	return detokenizationcontinuebtn;
}

public static WebElement getdetokenizationDonebtn(){
	return detokenizationDonebtn;
}
}
