package commonPackage;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Verify;
import com.google.common.base.VerifyException;

import businessSpecific.GlobalVariables;
import PageFactory.IBMBankPaymentPage;
import PageFactory.IBMPaymentSelectPage;

public class CommonSeleniumClass {
	private WebDriver driver;

	// select the appropriate driver
	public WebDriver selectRequiredBrowser(String browserName) {
		switch (browserName) {
		case "InternetExplorer":
			driver = new InternetExplorerDriver();
			return driver;

		case "FireFox":
			driver = new FirefoxDriver();
			return driver;

		default:
			driver = new FirefoxDriver();
			return driver;

		}
	}

	
	//Explicit wait
	public boolean CheckElementVisibility(WebElement dynamicElement){
		int openIndex,closeIndex;
		try{
			dynamicElement.getSize();	  
			return true;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
			openIndex = e.getMessage().indexOf("{");
			closeIndex = e.getMessage().indexOf("}");
			GlobalVariables.elementNotFound = GlobalVariables.elementNotFound+e.getMessage().substring(openIndex+1, closeIndex);
			return false;
		}
		
	
}
}
