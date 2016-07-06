package businessSpecific;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.Assert;

import PageFactory.*;
import commonPackage.CommonClass;
import commonPackage.CommonSeleniumClass;
import commonPackage.ExcelUtils;

public class IFrameNavigation {
	ExcelOperations excelOperations = new ExcelOperations();
	static CommonClass commonClass = new CommonClass();
	CommonSeleniumClass commonSeleniumClass = new CommonSeleniumClass();
	private static int colNumber;
	private static String cardNumber;
	private static boolean elementsFound = false;
	private static final int dropdownIndexValue = Integer.parseInt(commonClass
			.getRandomNumber(1)) + 2;
	private static final String amount = commonClass.getRandomNumber(2);
	private static final String accountNumber = commonClass.getRandomNumber(10);
	private static final String zipcode = commonClass.getRandomNumber(5);
	GlobalVariables gv = new GlobalVariables();
	
	//Page Factory Objects
	/*ACTAccountDetailsPage actAccountDetailsPage= PageFactory.initElements(gv.driver, ACTAccountDetailsPage.class);//new ACTAccountDetailsPage();
	ACTCardDetailsPage actCardDetailsPage = PageFactory.initElements(gv.driver,ACTCardDetailsPage.class);//new ACTCardDetailsPage();
	ACTSelectAPaymentMethodPage actSelectAPaymentMethodPage = PageFactory.initElements(gv.driver,ACTSelectAPaymentMethodPage.class);//new ACTSelectAPaymentMethodPage();
	HCDEChooseMethodPaymentPage hcdeChooseMethodPaymentPage = PageFactory.initElements(gv.driver,HCDEChooseMethodPaymentPage.class);//new HCDEChooseMethodPaymentPage();
	HCDEPaymentInformationPage hcdePaymentInformationPage = PageFactory.initElements(gv.driver,HCDEPaymentInformationPage.class);//new HCDEPaymentInformationPage();
	HCDEReceiveConfirmationPage hcdeReceiveConfirmationPage = PageFactory.initElements(gv.driver,HCDEReceiveConfirmationPage.class);//new HCDEReceiveConfirmationPage();
	HCDEWelcomeToSpeedpayPage hcdeWelcomeToSpeedpayPage = PageFactory.initElements(gv.driver,HCDEWelcomeToSpeedpayPage.class);//new HCDEWelcomeToSpeedpayPage();
	HCDEVerifyInformationPage hcdeVerifyInformationPage = PageFactory.initElements(gv.driver,HCDEVerifyInformationPage.class);//new HCDEVerifyInformationPage();
	IBMBankPaymentPage ibmBankPaymentPage = PageFactory.initElements(gv.driver,IBMBankPaymentPage.class);new IBMBankPaymentPage();
	IBMCardPaymentPage ibmCardPaymentPage = PageFactory.initElements(gv.driver,IBMCardPaymentPage.class);new IBMCardPaymentPage();
	IBMPaymentSelectPage IBMPaymentSelectPage = PageFactory.initElements(gv.driver,IBMPaymentSelectPage.class);new IBMPaymentSelectPage();
	PRFAddBankAccountPage prfAddBankAccountPage = PageFactory.initElements(gv.driver,PRFAddBankAccountPage.class);//new PRFAddBankAccountPage();
	PRFAddCardPage prfAddCardPage = PageFactory.initElements(gv.driver, PRFAddCardPage.class);//new PRFAddCardPage();
	PRFBankAccountAddedPage prfBankAccountAddedPage = PageFactory.initElements(gv.driver, PRFBankAccountAddedPage.class);//new PRFBankAccountAddedPage();
	PRFDeleteSavedPaymentPage prfDeleteSavedPaymentPage = PageFactory.initElements(gv.driver, PRFDeleteSavedPaymentPage.class);//new PRFDeleteSavedPaymentPage();
	PRFPaymentMethodPage prfPaymentMethodPage = PageFactory.initElements(gv.driver, PRFPaymentMethodPage.class);//new PRFPaymentMethodPage();
	PRFUpdatePaymentMethodPage prfUpdatePaymentMethodPage = PageFactory.initElements(gv.driver, PRFUpdatePaymentMethodPage.class);//new PRFUpdatePaymentMethodPage();
	RECAddCardDetailsPage recAddCardDetailsPage = PageFactory.initElements(gv.driver, RECAddCardDetailsPage.class);//new RECAddCardDetailsPage();
	RECCardDataEntryPage recCardDataEntryPage = PageFactory.initElements(gv.driver, RECCardDataEntryPage.class);//new RECCardDataEntryPage();
	RECPaymentAccountListPage recPaymentAccountListPage = PageFactory.initElements(gv.driver, RECPaymentAccountListPage.class);//new RECPaymentAccountListPage();
*/	
	
	// Add bank or card details and make payment through IBM iFrame
	public boolean payFromIBMiFrame(WebDriver driver) throws Exception {
		//openFirefoxDriver();
		InitIBMPages(driver);
		driver.get(GlobalVariables.sessionURLValue);

		if (GlobalVariables.Ex_accountTypeValue.equalsIgnoreCase("ACH")) {
			if (commonSeleniumClass.CheckElementVisibility(IBMPaymentSelectPage
					.getBankAccountLink())) {
				IBMPaymentSelectPage.getBankAccountLink().click();
				elementsFound = payByBankIBM();
			} else {
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						GlobalVariables.elementNotFound
								+ " not found in IBMPaymentSelectPage");
			}
		} else {
			if (commonSeleniumClass.CheckElementVisibility(IBMPaymentSelectPage
					.getCardLink())) {
				IBMPaymentSelectPage.getCardLink().click();
				elementsFound = payByCardIBM();
			} else {
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						GlobalVariables.elementNotFound
								+ " not found in IBMPaymentSelectPage");
			}
		}
		if (elementsFound) {
			driver.switchTo().alert().accept();
			if (driver.getCurrentUrl()
					.contains(GlobalVariables.successURLValue)) {
				System.out.println("payment made successfully");
			} else {
				System.out.println("Payment was not successful.Navigated to:"
						+ driver.getCurrentUrl());
				elementsFound = false;
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"Payment was not successful.Navigated to:"
								+ driver.getCurrentUrl()
								+ " Session ID is:"
								+ GlobalVariables.sessionIDValue);
			}

		}
		driver.quit();
		return elementsFound;
	}

	// pay from hcde iFrame
	public boolean payFromHCDEIframe(WebDriver driver) throws Exception {
		//openFirefoxDriver();
		InitHCDEPages(driver);
		driver.get(GlobalVariables.sessionURLValue);
		if (GlobalVariables.Ex_accountTypeValue.equalsIgnoreCase("ACH")) {
			if (GlobalVariables.Ex_CoBrandedValue)
				elementsFound = payByCoBrandedBankPages();
			else
				elementsFound = payByBankHCDE();
		} else {
			if (GlobalVariables.Ex_CoBrandedValue)
				elementsFound = payByCoBrandedCardPages();
			else
				elementsFound = payByCardHCDE();
		}
		if (elementsFound) {
			if (driver.getCurrentUrl()
					.contains(GlobalVariables.successURLValue)) {
				System.out.println("payment made successfully");
			} else {
				System.out.println("Payment was not successful.Navigated to:"
						+ driver.getCurrentUrl());
				elementsFound = false;
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"Payment was not successful.Navigated to:"
								+ driver.getCurrentUrl()
								+ " and Session ID is:"
								+ GlobalVariables.sessionIDValue);
			}
		}

		driver.quit();
		return elementsFound;
	}

	public boolean createWalletFromIframe(WebDriver driver) throws Exception {

		//openFirefoxDriver();
		switch (GlobalVariables.Ex_WalletTypeValue) {

		case "REC":
			InitRECPages(gv.driver);
			gv.driver.get(GlobalVariables.sessionURLValue);
			elementsFound = RECCreateWallet();
			if (elementsFound) {
				if (gv.driver.getCurrentUrl().contains(GlobalVariables.successURLValue)) {
					System.out.println("wallet created successfully");
				} else {
					System.out
							.println("Wallet creation was not successful.Navigated to:"
									+ gv.driver.getCurrentUrl());
					excelOperations.WriteExecutionStatusToExcel("FAIL",
							"wallet creation was not successful.Navigated to:"
									+ gv.driver.getCurrentUrl());
				}
			}
			break;

		case "ACT":
			InitACTPages(gv.driver);
			gv.driver.get(GlobalVariables.sessionURLValue);
			if (GlobalVariables.Ex_accountTypeValue.equalsIgnoreCase("ACH"))
				elementsFound = ACTCreateWalletByBank();
			else
				elementsFound = ACTCreateWalletByCard();
			if (elementsFound) {
				if (gv.driver.getCurrentUrl().contains(GlobalVariables.successURLValue)) {
					System.out.println("wallet created successfully");
				} else {
					System.out
							.println("wallet creation was not successful.Navigated to:"
									+ gv.driver.getCurrentUrl());
					excelOperations.WriteExecutionStatusToExcel("FAIL",
							"wallet creation was not successful.Navigated to:"
									+ gv.driver.getCurrentUrl());
				}
			}
			break;
		/*
		 * // Needs to be redone completely messed up case "PRF":
		 * InitPRFPages(GlobalVariables.driver);
		 * GlobalVariables.driver.get(GlobalVariables.sessionURLValue);
		 * walletManagementByPRFForCreation(); if
		 * (GlobalVariables.accountType.equalsIgnoreCase("ACH")) { elementsFound
		 * = walletManagementByPRFForDelete(); } else if
		 * (GlobalVariables.accountType.equalsIgnoreCase("CARD")) elementsFound
		 * = walletManagementByPRFForUpdate(); break; }
		 */
		}
		gv.driver.quit();
		return elementsFound;

	}

	// Initiate Page factory for IBM pages
	private void InitIBMPages(WebDriver driver) {

		PageFactory.initElements(driver, IBMBankPaymentPage.class);
		PageFactory.initElements(driver, IBMPaymentSelectPage.class);
		PageFactory.initElements(driver, IBMCardPaymentPage.class);

	}

	// Initiate page factory for HCDE pages
	private void InitHCDEPages(WebDriver driver) {

		PageFactory.initElements(driver, HCDEPaymentInformationPage.class);
		PageFactory.initElements(driver, HCDEReceiveConfirmationPage.class);
		PageFactory.initElements(driver, HCDEVerifyInformationPage.class);
		PageFactory.initElements(driver, HCDEChooseMethodPaymentPage.class);
		PageFactory.initElements(driver, HCDEWelcomeToSpeedpayPage.class);
		PageFactory.initElements(driver, HCDETokenizationDetailspage.class);
		PageFactory.initElements(driver, HCDEDeTokenizationDetailsPage.class);
	}

	// Initiate page factory for REC pages
	private void InitRECPages(WebDriver driver) {

		PageFactory.initElements(driver, RECPaymentAccountListPage.class);
		PageFactory.initElements(driver, RECAddCardDetailsPage.class);
		PageFactory.initElements(driver, RECCardDataEntryPage.class);
	}

	// initiate page factory for ACT pages
	private void InitACTPages(WebDriver driver) {
		PageFactory.initElements(driver, ACTSelectAPaymentMethodPage.class);
		PageFactory.initElements(driver, ACTAccountDetailsPage.class);
		PageFactory.initElements(driver, ACTCardDetailsPage.class);
	}

	// initiate page factory for PRf pages
	private void InitPRFPages(WebDriver driver) {
		PageFactory.initElements(driver, PRFAddBankAccountPage.class);
		PageFactory.initElements(driver, PRFPaymentMethodPage.class);
		PageFactory.initElements(driver, PRFAddCardPage.class);
		PageFactory.initElements(driver, PRFDeleteSavedPaymentPage.class);
		PageFactory.initElements(driver, PRFBankAccountAddedPage.class);
		PageFactory.initElements(driver, PRFUpdatePaymentMethodPage.class);
	}

	// CoBraded pages
	private boolean payByCoBrandedBankPages() throws Exception {
		if (commonSeleniumClass
				.CheckElementVisibility(HCDEChooseMethodPaymentPage
						.getPayWithBankBtn())) {
			HCDEChooseMethodPaymentPage.getPayWithBankBtn().click();
			if (payByBankHCDE())
				return true;
			else
				return false;
		} else {
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					GlobalVariables.elementNotFound
							+ " not found in HCDEChooseMethodPaymentPage");
			return false;
		}
	}

	// Pay for the BTN with Bank Details using HCDE
	private boolean payByBankHCDE() throws Exception {
		if (commonSeleniumClass
				.CheckElementVisibility(HCDEPaymentInformationPage
						.getPaymentMethodComboBox())) {
			Select methodpaymentDropdown;
			switch (GlobalVariables.Ex_BankAccountTypeValue) {
			case "savings":
				methodpaymentDropdown = new Select(
						HCDEPaymentInformationPage.getPaymentMethodComboBox());
				methodpaymentDropdown
						.selectByVisibleText(GlobalVariables.savingsTextHCDE);
				break;
			case "checkings":
				methodpaymentDropdown = new Select(
						HCDEPaymentInformationPage.getPaymentMethodComboBox());
				methodpaymentDropdown
						.selectByIndex(2);
				break;
			case "moneymarket":
				methodpaymentDropdown = new Select(
						HCDEPaymentInformationPage.getPaymentMethodComboBox());
				methodpaymentDropdown
						.selectByVisibleText(GlobalVariables.moneyMarketTextHCDE);
				break;

			}

			if (verifyHCDEBankPaymentPage()) {
				if (HCDEPaymentInformationPage.getAmountPaidTextBox()
						.getAttribute("readonly") == null) {
					HCDEPaymentInformationPage.getAmountPaidTextBox().clear();
					HCDEPaymentInformationPage.getAmountPaidTextBox().sendKeys(
							amount);
				}
				HCDEPaymentInformationPage.getRoutingNumberTextBox().sendKeys(
						GlobalVariables.routingNumber);
				HCDEPaymentInformationPage.getAccountNumberTextBox().sendKeys(
						accountNumber);

				HCDEPaymentInformationPage.getIAgreeCheckbox().click();

				HCDEPaymentInformationPage.getNextButton().click();
				if (commonSeleniumClass
						.CheckElementVisibility(HCDEVerifyInformationPage
								.getSubmitPaymentButton())) {
					HCDEVerifyInformationPage.getSubmitPaymentButton().click();
					return true;
				} else {
					excelOperations
							.WriteExecutionStatusToExcel(
									"FAIL",
									GlobalVariables.elementNotFound
											+ " not found in HCDEVerifyInformationPage");
					return false;
				}
			} else {
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						GlobalVariables.elementNotFound
								+ " not found in HCDEPaymentInformationPage");
				return false;
			}
		} else {
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					GlobalVariables.elementNotFound
							+ " not found in HCDEPaymentInformationPage");
			return false;
		}
	}

	// pay from co branded pages card
	private boolean payByCoBrandedCardPages() throws Exception {
		if (commonSeleniumClass
				.CheckElementVisibility(HCDEChooseMethodPaymentPage
						.getPayWithCardBtn())) {
			HCDEChooseMethodPaymentPage.getPayWithCardBtn().click();

			if (commonSeleniumClass
					.CheckElementVisibility(HCDEWelcomeToSpeedpayPage
							.getAgreeButton())) {
				HCDEWelcomeToSpeedpayPage.getAgreeButton().click();
				if (payByCardHCDE())
					return true;
				else
					return false;
			} else {
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						GlobalVariables.elementNotFound
								+ " not found in HCDEWelcomeToSpeedpayPage");
				return false;
			}
		} else {
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					GlobalVariables.elementNotFound
							+ " not found in HCDEChooseMethodPaymentPage");
			return false;

		}
	}

	// Pay for the BTN using CARD details using IBM
	private boolean payByCardHCDE() throws Exception {
		cardNumber = selectBasedOnCardType();
		if (commonSeleniumClass
				.CheckElementVisibility(HCDEPaymentInformationPage
						.getPaymentMethodComboBox())) {
			Select methodpaymentDropdown = new Select(
					HCDEPaymentInformationPage.getPaymentMethodComboBox());
			methodpaymentDropdown
					.selectByVisibleText(GlobalVariables.creditOrDebitTextHCDE);

			if (verifyHCDECardPaymentPage()) {
				if (HCDEPaymentInformationPage.getAmountPaidTextBox()
						.getAttribute("readonly") == null) {
					HCDEPaymentInformationPage.getAmountPaidTextBox().clear();
					HCDEPaymentInformationPage.getAmountPaidTextBox().sendKeys(
							amount);
				}

				HCDEPaymentInformationPage.getCardNumberTextBox().sendKeys(
						cardNumber);
				Select monthDropdown = new Select(
						HCDEPaymentInformationPage.getExpiryMonthComboBox());
				monthDropdown.selectByIndex(dropdownIndexValue);
				Select yearDropDown = new Select(
						HCDEPaymentInformationPage.getExpiryYearComboBox());
				yearDropDown.selectByIndex(dropdownIndexValue);
				HCDEPaymentInformationPage.getZipcodeTextBox().clear();
				HCDEPaymentInformationPage.getZipcodeTextBox()
						.sendKeys(zipcode);

				HCDEPaymentInformationPage.getIAgreeCheckbox().click();

				HCDEPaymentInformationPage.getNextButton().click();
				if (commonSeleniumClass
						.CheckElementVisibility(HCDEVerifyInformationPage
								.getSubmitPaymentButton())) {
					HCDEVerifyInformationPage.getSubmitPaymentButton().click();
					return true;
				} else {
					excelOperations
							.WriteExecutionStatusToExcel(
									"FAIL",
									GlobalVariables.elementNotFound
											+ " not found in HCDEVerifyInformationPage");
					return false;
				}
			} else {
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						GlobalVariables.elementNotFound
								+ " not found in HCDEPaymentInformationPage");
				return false;
			}
		} else {
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					GlobalVariables.elementNotFound
							+ " not found in HCDEPaymentInformationPage");
			return false;
		}
	}

	// Pay for the BTN using Bank details using IBM
	private boolean payByBankIBM() throws Exception {
		if (verifyIBMBankPaymentPageElements()) {
			IBMBankPaymentPage.getRoutingNumberTextBox().sendKeys(
					GlobalVariables.routingNumber);
			IBMBankPaymentPage.getAccountNumberTextBox()
					.sendKeys(accountNumber);
			switch (GlobalVariables.Ex_BankAccountTypeValue.toLowerCase()) {
			case "savings":
				IBMBankPaymentPage.getSavingsRadio().click();
				break;
			case "checkings":
				IBMBankPaymentPage.getCheckingsRadio().click();
				break;
			case "moneymarket":
				IBMBankPaymentPage.getMoneyMarketRadio().click();
				break;
			}
			IBMBankPaymentPage.getAgreeCheckbox().click();
			IBMBankPaymentPage.getSubmitButton().click();
			return true;
		} else {
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					GlobalVariables.elementNotFound
							+ " element was not found in IBMBankPaymentPage");
			return false;
		}

	}

	// Pay for the BTN using CARD details using IBM
	private boolean payByCardIBM() throws Exception {
		if (verifyIBMCardPaymentPage()) {
			cardNumber = selectBasedOnCardType();
			IBMCardPaymentPage.getCardNumberTextBox().sendKeys(cardNumber);
			Select monthDropdown = new Select(
					IBMCardPaymentPage.getExpiryMonthDropDown());
			monthDropdown.selectByIndex(dropdownIndexValue);
			Select yearDropDown = new Select(
					IBMCardPaymentPage.getExpiryYearDropDown());
			yearDropDown.selectByIndex(dropdownIndexValue);
			IBMCardPaymentPage.getZipCodeTextBox().clear();
			IBMCardPaymentPage.getZipCodeTextBox().sendKeys(zipcode);
			IBMCardPaymentPage.getSubmitButton().click();
			return true;
		} else {
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					GlobalVariables.elementNotFound
							+ " element was not found in IBMCardPaymentPage");
			return false;
		}
	}

	// wallet Management for REC

	private boolean RECCreateWallet() {

		cardNumber = selectBasedOnCardType();
		RECPaymentAccountListPage.getAddNewCreditCard().click();
		RECCardDataEntryPage.getNickName().sendKeys(
				GlobalVariables.Ex_NickNameValue);
		RECCardDataEntryPage.getcardNumber().sendKeys(cardNumber);
		RECCardDataEntryPage.getNextButton().click();
		Select monthDropdown = new Select(
				RECAddCardDetailsPage.getExpiryMonthComboBox());
		monthDropdown.selectByIndex(dropdownIndexValue);
		Select yearDropdown = new Select(
				RECAddCardDetailsPage.getExpiryYearComboBox());
		yearDropdown.selectByIndex(dropdownIndexValue);
		RECAddCardDetailsPage.getZipCode().sendKeys(zipcode);
		RECAddCardDetailsPage.getFinishButton().click();
		return true;
	}

	// wallet Management for ACT

	private boolean ACTCreateWalletByBank() {

		cardNumber = selectBasedOnCardType();

		ACTSelectAPaymentMethodPage.getbankAccount().click();
		ACTAccountDetailsPage.getRoutingNumber().sendKeys(
				GlobalVariables.routingNumber);
		ACTAccountDetailsPage.getAccountNumber().sendKeys(accountNumber);
		ACTAccountDetailsPage.getSaveButton().click();
		ACTSelectAPaymentMethodPage.getDoneButton().click();
		return true;
	}

	private boolean ACTCreateWalletByCard() {
		cardNumber = selectBasedOnCardType();
		ACTSelectAPaymentMethodPage.getNewCard().click();
		ACTCardDetailsPage.getCardNumber().sendKeys(cardNumber);
		Select monthDrpDwn = new Select(
				ACTCardDetailsPage.getExpiryMonthComboBox());
		monthDrpDwn.selectByIndex(dropdownIndexValue);
		Select yearDrpDwn = new Select(
				ACTCardDetailsPage.getExpiryYearComboBox());
		yearDrpDwn.selectByIndex(dropdownIndexValue);
		ACTCardDetailsPage.getZipCodeTextBox().sendKeys(zipcode);
		ACTCardDetailsPage.getSaveButton().click();
		ACTSelectAPaymentMethodPage.getDoneButton().click();
		return true;
	}

	/*
	 * // wallet management by PRF for wallet creation
	 * 
	 * private static void walletManagementByPRFForCreation() {
	 * 
	 * if (GlobalVariables.accountType.equalsIgnoreCase("ACH")) {
	 * GlobalVariables.accountNumber = CommonClass.getRandomNumber(10);
	 * PRF_PaymentMethodPage.getAddBankAccountButton().click();
	 * PRF_addBankAccountPage.getRoutingNumberTextBox().sendKeys(
	 * GlobalVariables.routingNumber);
	 * PRF_addBankAccountPage.getAccountNumberTextBox().sendKeys(
	 * GlobalVariables.accountNumber); Select accountTypeDropdown = new Select(
	 * PRF_addBankAccountPage.getBankAccountTyoeDrpdwn());
	 * accountTypeDropdown.selectByVisibleText("Savings Account");
	 * PRF_addBankAccountPage.getSaveButton().click(); if
	 * (PRF_BankAccountAddedPage .getsavedPaymentAccountLink() .getText()
	 * .equalsIgnoreCase( "View all of your saved payment accounts.")) {
	 * PRF_BankAccountAddedPage.getsavedPaymentAccountLink().click(); } } else {
	 * GlobalVariables.zipcode = CommonClass.getRandomNumber(5); cardNumber =
	 * selectBasedOnCardType(); PRF_PaymentMethodPage.getCardButton().click();
	 * PRF_addCardPage.getcardNumberTextBOx().sendKeys(cardNumber); Select
	 * monthDrpdwn = new Select(PRF_addCardPage.getmonthDropdwon());
	 * monthDrpdwn.selectByIndex(3); Select yearDrpdwn = new
	 * Select(PRF_addCardPage.getYearDropdown()); yearDrpdwn.selectByIndex(4);
	 * // PRF_addCardPage.getZipcodeTextBox().sendKeys(GlobalVariables.zipcode);
	 * PRF_addCardPage.getSaveButton().click(); if (PRF_BankAccountAddedPage
	 * .getsavedPaymentAccountLink() .getText() .equalsIgnoreCase(
	 * "View all of your saved payment accounts.")) {
	 * PRF_BankAccountAddedPage.getsavedPaymentAccountLink().click(); } }
	 * 
	 * }
	 */

	// get card number based on card type
	private String selectBasedOnCardType() {
		GlobalVariables.Ex_cardTypeValue = GlobalVariables.Ex_cardTypeValue
				.toLowerCase();
		switch (GlobalVariables.Ex_cardTypeValue) {
		case "visa":
			return GlobalVariables.visaCardNumber;

		case "mastercard":
			return GlobalVariables.masterCardNumber;

		case "amex":
			return GlobalVariables.amexCardNumber;

		case "discover":
			return GlobalVariables.discoverCardNumber;

		default:
			return GlobalVariables.visaCardNumber;

		}
	}


	// verify if all elements exist in IBM bank payment page
	private boolean verifyIBMBankPaymentPageElements() {
		if (commonSeleniumClass.CheckElementVisibility(IBMBankPaymentPage
				.getAccountNumberTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMBankPaymentPage
								.getAgreeCheckbox())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMBankPaymentPage
								.getCheckingsRadio())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMBankPaymentPage
								.getMoneyMarketRadio())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMBankPaymentPage
								.getRoutingNumberTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMBankPaymentPage
								.getSavingsRadio())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMBankPaymentPage
								.getSubmitButton())) {
			return true;
		} else {
			return false;
		}
	}

	// verify if all elements exist in IBM Card Page
	private boolean verifyIBMCardPaymentPage() {
		if (commonSeleniumClass.CheckElementVisibility(IBMCardPaymentPage
				.getCardNumberTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMCardPaymentPage
								.getExpiryMonthDropDown())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMCardPaymentPage
								.getExpiryYearDropDown())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMCardPaymentPage
								.getSubmitButton())
				&& commonSeleniumClass
						.CheckElementVisibility(IBMCardPaymentPage
								.getZipCodeTextBox()))
			return true;
		else
			return false;
	}

	// verify if all elements exist in HCDE card payment page
	private boolean verifyHCDECardPaymentPage() {
		if (commonSeleniumClass
				.CheckElementVisibility(HCDEPaymentInformationPage
						.getAmountPaidTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getCardNumberTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getExpiryMonthComboBox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getExpiryYearComboBox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getNextButton())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getIAgreeCheckbox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getZipcodeTextBox()))
			return true;
		else
			return false;
	}

	// verify all elements exist in HCDE bank payment Page
	private boolean verifyHCDEBankPaymentPage() {
		if (commonSeleniumClass
				.CheckElementVisibility(HCDEPaymentInformationPage
						.getAmountPaidTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getRoutingNumberTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getAccountNumberTextBox())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getNextButton())
				&& commonSeleniumClass
						.CheckElementVisibility(HCDEPaymentInformationPage
								.getIAgreeCheckbox()))
			return true;
		else
			return false;
	}



//-------------------------------------------------------------------------------------------------------------------

public boolean  Auto_payFromHCDEIframe(WebDriver driver) throws Exception {
		InitHCDEPages(driver);
		driver.get(GlobalVariables.sessionURLValue);			
				elementsFound =Auto_payByBankHCDE();
		if (elementsFound) {			
			if (driver.getCurrentUrl()
					.contains(GlobalVariables.successURLValue)) {
				
				System.out.println("payment made successfully");
				
			} else {
				System.out.println("Payment was not successful.Navigated to:"
						+ driver.getCurrentUrl());
				elementsFound = false;
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"Payment was not successful.Navigated to:"
								+ driver.getCurrentUrl()
								+ " and Session ID is:"
								+ GlobalVariables.sessionIDValue);
			}
		}

		driver.quit();
		return elementsFound;
	}




//----------------------------------------------------------------------------------------------------------------
private boolean Auto_payByBankHCDE() throws Exception {
	
	if(commonSeleniumClass.CheckElementVisibility(HCDEPaymentInformationPage.getaccountdropdown())){
		
		Select methodpaymentDropdown;
		switch (GlobalVariables.Ex_BankAccountTypeValue) {
		case "savings":

			HCDEPaymentInformationPage.getRoutingNumberTextBox().sendKeys(
					GlobalVariables.routingNumber);
			HCDEPaymentInformationPage.getAccountNumberTextBox().sendKeys(
					accountNumber);
			
			methodpaymentDropdown = new Select(
					HCDEPaymentInformationPage.getaccountdropdown());
			methodpaymentDropdown
					.selectByVisibleText(GlobalVariables.savingsTextHCDE);
			break;
		case "checkings":
			
			HCDEPaymentInformationPage.getRoutingNumberTextBox().sendKeys(
					GlobalVariables.routingNumber);
			HCDEPaymentInformationPage.getAccountNumberTextBox().sendKeys(
					accountNumber);
			
			methodpaymentDropdown = new Select(
					HCDEPaymentInformationPage.getaccountdropdown());
			methodpaymentDropdown
					.selectByVisibleText(GlobalVariables.checkingsTextHCDE);
			
			break;
		case "moneymarket":

			HCDEPaymentInformationPage.getRoutingNumberTextBox().sendKeys(
					GlobalVariables.routingNumber);
			HCDEPaymentInformationPage.getAccountNumberTextBox().sendKeys(
					accountNumber);
			
			
			methodpaymentDropdown = new Select(
					HCDEPaymentInformationPage.getaccountdropdown());
			methodpaymentDropdown
					.selectByVisibleText(GlobalVariables.moneyMarketTextHCDE);
			break;
			default:
				System.out.println("no match");
			break;
		}
			HCDEPaymentInformationPage.getIAgreeCheckbox().click();

			HCDEPaymentInformationPage.clickNextButton().click();
		

			if (commonSeleniumClass
					.CheckElementVisibility(HCDEPaymentInformationPage.clicksaveButton())
					) {
				
				HCDEPaymentInformationPage.clicksaveButton().click();
				HCDEPaymentInformationPage.doneButton().click();
				
				
				return true;
			} 
			else {
				excelOperations
						.WriteExecutionStatusToExcel(
								"FAIL",
								GlobalVariables.elementNotFound
										+ " not found in HCDEVerifyInformationPage");
				return false;
			}
		
		}
		return false;
}

	public boolean getNevigationForTokenization(WebDriver driver) throws Exception{
		InitHCDEPages(driver);
		boolean flag;
		driver.get(GlobalVariables.sessionURLValue);
		cardNumber = selectBasedOnCardType();
		HCDETokenizationDetailspage.getNameonCardTextBox().sendKeys("mohit");
		HCDETokenizationDetailspage.getcardNumTextBox().sendKeys(cardNumber);
		Select mnthDrpDwn = new Select(HCDETokenizationDetailspage.getexpiryMonthdrpDwn());
		mnthDrpDwn.selectByIndex(5);
		Select yearDrpDwn = new Select(HCDETokenizationDetailspage.getexpiryYeardrpDwn());
		yearDrpDwn.selectByIndex(5);
		HCDETokenizationDetailspage.getcvvTextBox().sendKeys("500");
		HCDETokenizationDetailspage.getsubmitBtn().click();
		
		String successURL = driver.getCurrentUrl();
		String splitSuccessURL[] = successURL.split("=");
		GlobalVariables.tokenNum = splitSuccessURL[1];
		int tokenNumLength = GlobalVariables.tokenNum.length();
		String fourDigitVerification = GlobalVariables.tokenNum.substring(tokenNumLength-4, tokenNumLength);
		if(cardNumber.contains(fourDigitVerification)){
			excelOperations.WriteExecutionStatusToExcel("PASS", "Tokenization is successfull and token Num is "+GlobalVariables.tokenNum);
		flag= true;
		}else {
			System.out.println("Payment was not successful.Navigated to:"
					+ driver.getCurrentUrl());
			elementsFound = false;
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					"Tokenization was not successful.Navigated to:"
							+ driver.getCurrentUrl()
							+ " Session ID is:"
							+ GlobalVariables.sessionIDValue);
			 flag = false;
		}
		
		driver.close();
		return flag;
	}


public void getNevigationForDeTokenization(WebDriver driver) throws Exception{
	InitHCDEPages(driver);
	driver.get(GlobalVariables.sessionURLValue);
	HCDEDeTokenizationDetailsPage.getdetokenizationcontinuebtn().click();
	String actualcardnum = HCDEDeTokenizationDetailsPage.getcardNumTextBox().getAttribute("value");
	if(actualcardnum.equals(cardNumber)){
		System.out.println("Card Number is verified");
		excelOperations.WriteExecutionStatusToExcel("PASS", "DeTokenization is successfull and Card Num is "+actualcardnum);
	}else {
		System.out.println("Payment was not successful.Navigated to:"
				+ driver.getCurrentUrl());
		elementsFound = false;
		excelOperations.WriteExecutionStatusToExcel("FAIL",
				"DeTokenization was not successful.Navigated to:"
						+ driver.getCurrentUrl()
						+ " Session ID is:"
						+ GlobalVariables.sessionIDValue);
	}
	HCDEDeTokenizationDetailsPage.getdetokenizationDonebtn().click();
	driver.close();
}



}
