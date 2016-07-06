package businessSpecific;

import org.testng.Assert;

import commonPackage.ExcelUtils;
import commonPackage.HTTPPostRequest;

public class VerifyExpectedActual {
	private static int colNumber;
	ExcelOperations excelOperations = new ExcelOperations();
	HTTPPostRequest httpPostRequest = new HTTPPostRequest();

	// verify the tag value with expected and actual
	public boolean verifyTagExpectedValue(String Expected, String Actual,
			String errorTagName, boolean accntLookup) throws Exception {
		if (Expected.equalsIgnoreCase(Actual)) {
			System.out.println("Successful Response Recieved!!!");
			if (accntLookup) {
				excelOperations.WriteExecutionStatusToExcel("PASS",
						"Account Lookup successful");
			}
			return true;
		} else {

			String errorMessage = httpPostRequest.getTagValue(errorTagName);
			System.out.println("Error Message is:" + errorMessage
					+ " Request ID : " + GlobalVariables.currDateTime);
			System.out.println("Please Check the logs!!!");
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					"Error Message is:" + errorMessage + " Request ID : "
							+ GlobalVariables.currDateTime);
			return false;
		}
	}

	public boolean verifyExpectedActualForOthers(String Expected,
			String Actual, boolean refund) throws Exception {
		if (Expected.equalsIgnoreCase(Actual)) {
			System.out.println("Expected Matches Actual!!");
			if (refund) {
				System.out.println("Refund Successful!!");
				excelOperations.WriteExecutionStatusToExcel("PASS",
						"Refund successful. Refund Payment ID : "
								+ GlobalVariables.paymentIDValue);
			}
			return true;
		} else {
			System.out.println("Expected Not Matching Actual!!!");
			System.out.println("check report");
			excelOperations.WriteExecutionStatusToExcel("FAIL", "Excpected:"
					+ Expected + " is not the same as Actual:" + Actual);
			return false;
		}
	}

	// verify DB status of payment
	public void verifyDBExpectedValue(boolean status, boolean walletMgmt,
			int paymentStatusIndex) throws Exception {
		if (status) {
			if (walletMgmt) {
				System.out.println("wallet status is : "
						+ GlobalVariables.walletDBStatus);
				excelOperations.WriteExecutionStatusToExcel("PASS",
						"wallet status is : " + GlobalVariables.walletDBStatus
								+ " and wallet ID is : "
								+ GlobalVariables.walletReferenceValue);
			} else {
				System.out.println("payment status is : "
						+ GlobalVariables.paymentDBTempStatus
								.get(paymentStatusIndex));
				excelOperations.WriteExecutionStatusToExcel(
						"PASS",
						"Payment status is : "
								+ GlobalVariables.paymentDBTempStatus
										.get(paymentStatusIndex)
								+ " and Payment ID is : "
								+ GlobalVariables.paymentIDValue);
			}
		} else {
			if (walletMgmt) {
				System.out.println("wallet status is : "
						+ GlobalVariables.walletDBStatus);
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"wallet status is : " + GlobalVariables.walletDBStatus
								+ " and wallet ID is : "
								+ GlobalVariables.walletReferenceValue);
			} else {
				System.out.println("payment status is : "
						+ GlobalVariables.paymentDBTempStatus
								.get(paymentStatusIndex));
				excelOperations.WriteExecutionStatusToExcel(
						"FAIL",
						"Payment status is:"
								+ GlobalVariables.paymentDBTempStatus
										.get(paymentStatusIndex)
								+ " and Payment ID is : "
								+ GlobalVariables.paymentIDValue);
			}
		}
	}
	
	public boolean verifyPaymentStatus(String Expected, String Actual,
			String errorTagName) throws Exception{
		if (Expected.equalsIgnoreCase(Actual)) {
			excelOperations.WriteExecutionStatusToExcel("PASS",
					"Submit Payment CPAL is successfull and payment status is "+httpPostRequest.getTagValue(GlobalVariables.paymentStatusTag));
			return true;
		}else{
			String errorMessage = httpPostRequest.getTagValue(errorTagName);
			System.out.println("Error Message is:" + errorMessage
					+ " Request ID : " + GlobalVariables.currDateTime);
			System.out.println("Please Check the logs!!!");
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					"Error Message is:" + errorMessage + " Request ID : "
							+ GlobalVariables.currDateTime);
			return false;
		}
		
		
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------	
	
	
	
	// verify DB status of payment
	public void verifyDBExpectedValueForAutopay(boolean status) throws Exception {
		if (status) {
				System.out.println("autopay Record status is : "
						+ GlobalVariables.autopayDBStatus);
				excelOperations.WriteExecutionStatusToExcel("PASS",
						"autopay Record is : " + GlobalVariables.autopayDBStatus
								+ " and billing acnt id is : "
								+ GlobalVariables.Ex_btnValue);
		} else {
				System.out.println("autopay Record  is : "
						+ GlobalVariables.autopayDBStatus);
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"autopay Record is : " + GlobalVariables.autopayDBStatus
								+ " and  billing acnt ID is : "
								+ GlobalVariables.Ex_btnValue);
		}
	}
	
	
	
	public boolean verifyResponseAndExpiryDateTagExpectedValue(String ExpectedResponse, String ActualResponse,
			String ExpectedExpiryDate, String ActualExpiryDate, String errorTagName) throws Exception{

		if(ExpectedResponse.equals(ActualResponse)) {
			if(ExpectedExpiryDate.equals(ActualExpiryDate)){
				System.out.println("Successful Response Recieved!!!");
			}
			return true;
		} else {
			String errorMessage = httpPostRequest.getTagValue(errorTagName);
			System.out.println("Error Message is:" + errorMessage
					+ " Request ID : " + GlobalVariables.currDateTime);
			System.out.println("Please Check the logs!!!");
			excelOperations.WriteExecutionStatusToExcel("FAIL",
					"Error Message is:" + errorMessage + " Request ID : "
							+ GlobalVariables.currDateTime);
			return false;
		}
	}
	
	
	
}
