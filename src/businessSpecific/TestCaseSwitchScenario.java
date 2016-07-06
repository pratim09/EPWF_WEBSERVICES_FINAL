package businessSpecific;

import java.io.File;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.google.common.io.Files;
import com.sun.jna.platform.FileUtils;

import commonPackage.CommonClass;
import commonPackage.ExcelUtils;
import commonPackage.HTTPPostRequest;

public class TestCaseSwitchScenario {
	private static int rowNumber;
	private static String currDateTime;
	private static boolean PaymentFlag = false;
	private static File testDataExcelFile;
	private static File reportExcelFile;
	private static File paymentsFile;
	
	//Class Objects
	ScenariosSequencing scenariosSequencing = new ScenariosSequencing();
	ExcelOperations excelOperations = new ExcelOperations();
	ExcelUtils excelUtils = new ExcelUtils();
	CommonClass commonClass = new CommonClass();

	// Switch scenarios based on the input values in excel
	public void Execute() throws Throwable {
		testDataExcelFile = new File(GlobalVariables.Path_TestData
				+ GlobalVariables.File_TestData);
		excelUtils.setExcelFile(testDataExcelFile, GlobalVariables.tcSheetName);
		GlobalVariables.envRowNumList = excelUtils.getRowNoList(
				GlobalVariables.envSheetName, GlobalVariables.executeStr);
		for (int environment : GlobalVariables.envRowNumList) {
			excelOperations.setEnvExcelValuesToVariables(
					GlobalVariables.envSheetName, testDataExcelFile,
					environment);
			GlobalVariables.rowNumberList = excelUtils.getRowNoList(
					GlobalVariables.tcSheetName, GlobalVariables.executeStr);
			for (int testCase : GlobalVariables.rowNumberList) {
				
				GlobalVariables.currRowNumber = testCase;
				currDateTime = commonClass.getCurrentDateTime("yyyy-MM-dd_HH-mm-ss");

				excelOperations.setExcelValuesToVariables(
						GlobalVariables.tcSheetName,
						GlobalVariables.tdSheetName, testDataExcelFile,
						GlobalVariables.currRowNumber);
				System.out.println("Testing In Env : "+GlobalVariables.envNameValue);
				System.out.println("##################################################");
				System.out.println();
				excelOperations.setPathValuesToVariables(currDateTime);
				System.out.println("-------------------Starting Test Case:"
						+ GlobalVariables.Ex_ScenarioNameValue + " ----------------");
				GlobalVariables.fullXMLfilePath = GlobalVariables.genericXMLfilePath+GlobalVariables.Ex_xmlNameValue;
				while (GlobalVariables.Ex_noOfIterationsValue > 0) {
					System.out.println("--------Start Iteration---------");
					switchBasedOnScenarioName(GlobalVariables.Ex_ScenarioNameValue);
					GlobalVariables.XMLString = "";
					GlobalVariables.elementNotFound ="";
					GlobalVariables.Ex_noOfIterationsValue--;
					System.out.println("-------End Iteration-------");
					System.out.println();
					System.out.println();
				}

				System.out.println("-------------------Ended Test Case:"
						+ GlobalVariables.Ex_ScenarioNameValue + " ----------------");
				System.out.println();
				System.out.println();
				System.out.println();

			}
			reportExcelFile = new File(GlobalVariables.excelReportFilePath
					+ "\\Report_" + GlobalVariables.envNameValue + "_"
					+ currDateTime + ".xls");
			Files.copy(testDataExcelFile, reportExcelFile);

		}
		if (PaymentFlag) {
			paymentsFile = new File(GlobalVariables.writePaymentFilePath
					+ "/PaymentIDs_" + currDateTime + ".txt");
			commonClass
					.WTFXMLResponse(
							GlobalVariables.writerObject,
							paymentsFile,
							commonClass
									.addListIntoString(GlobalVariables.paymentsList));
		}
	}
	
	private void switchBasedOnScenarioName(String scenarioName) throws Throwable{
		switch (scenarioName.trim()) {

		case "AccountLookupCRIS":
			scenariosSequencing.AccountLookup(GlobalVariables.fullXMLfilePath);
			break;
		case "AccountLookupLATIS":
			scenariosSequencing.AccountLookup(GlobalVariables.fullXMLfilePath);
			break;
		case "SubmitPaymentIBMBank":
			PaymentFlag = true;
			scenariosSequencing.SubmitPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "SubmitPaymentIBMCARD":
			PaymentFlag = true;
			scenariosSequencing.SubmitPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "SubmitPaymentHCDECoACH":
			PaymentFlag = true;
			scenariosSequencing.SubmitPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "SubmitPaymentHCDENoCoCARD":
			PaymentFlag = true;
			scenariosSequencing.SubmitPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "SubmitPaymentHCDECoCARD":
			PaymentFlag = true;
			scenariosSequencing.SubmitPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "RefundPayment":
			scenariosSequencing.refundPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "WalletMgmtACT":
			scenariosSequencing.walletManagement(GlobalVariables.fullXMLfilePath);
			break;
		case "WalletMgmtREC":
			scenariosSequencing.walletManagement(GlobalVariables.fullXMLfilePath);
			break;
		case "WalletMgmtPRF":
			scenariosSequencing.walletManagement(GlobalVariables.fullXMLfilePath);
			break;
		case "MultiPaymentNoWalletCON":
			scenariosSequencing.multiPayment(GlobalVariables.fullXMLfilePath);
			break;
			
		case "AccountLookupWithCombo":
			scenariosSequencing.AccountLookup(GlobalVariables.fullXMLfilePath);
			break;
		case "SubmitPaymentCPAL_NonElectronic":
			scenariosSequencing.submitPayment_CPALNonElectronic(GlobalVariables.fullXMLfilePath);
			break;			
		case "SubmitPaymentCPAL_ElectronicsandnonElectronics":
			scenariosSequencing.submitpaymentElectronicsandNonElectronics(GlobalVariables.fullXMLfilePath);
			break;
		case "POS Refund CRIS_CARD":
			scenariosSequencing.POSPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "AutoPay_Enrollment":	
			scenariosSequencing.autoPay_Enrollment(GlobalVariables.fullXMLfilePath);
			break;
		case "AutoPay_Update" :
			scenariosSequencing.autopay_Update(GlobalVariables.fullXMLfilePath);
			break;
		case "AutoPay_deEnrollment":
			scenariosSequencing.autopay_deEnrollment(GlobalVariables.fullXMLfilePath);
			break;
		case "PaymentHistoryDetails":
			scenariosSequencing.paymentHistory(GlobalVariables.fullXMLfilePath);
			break;
		case "HCDE_Tokenization":
            scenariosSequencing.tokenization(GlobalVariables.fullXMLfilePath);	
            break;
		case "HCDE_AuthorizePayment":
			scenariosSequencing.authorizePayment(GlobalVariables.fullXMLfilePath);
			break;
		case "HCDE_VoidPayment":
			scenariosSequencing.voidPayment(GlobalVariables.fullXMLfilePath);
			break;
		case "HCDE_DeTokenization":
			scenariosSequencing.deTokenization(GlobalVariables.fullXMLfilePath);
			break;
		case "UpdateWallet":
			scenariosSequencing.updateWallet(GlobalVariables.fullXMLfilePath);
			break;
		case "RetrieveWallet":
			scenariosSequencing.retrieveWallet(GlobalVariables.fullXMLfilePath);
			break;
		default:
			System.out.println("invalid service name");
			break;

		}
	}
}
