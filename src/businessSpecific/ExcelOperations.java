package businessSpecific;

import java.io.File;

import commonPackage.ExcelUtils;


public class ExcelOperations {
	private static int colNumber;
	private static int rowNumber;
	ExcelUtils excelUtils = new ExcelUtils();

	public void setEnvExcelValuesToVariables(String envSheetName,
			File excelFile, int currentRowNumber) throws Exception {

		excelUtils.setExcelFile(excelFile, envSheetName);

		/*
		 * rowNumber = excelUtils.getRowNo(envSheetName,
		 * GlobalVariables.executeStr);
		 */
		colNumber = excelUtils.getColumnNo(envSheetName,
				GlobalVariables.envName);
		GlobalVariables.envNameValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(envSheetName,
				GlobalVariables.envEndPointName);
		GlobalVariables.Ex_endPointUrlValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(envSheetName,
				GlobalVariables.adminURLName);
		GlobalVariables.Ex_adminURLValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(envSheetName,
				GlobalVariables.databaseConnectionString);
		GlobalVariables.Ex_dbConnectionStringValue = excelUtils.getCellData(
				currentRowNumber, colNumber);
		colNumber = excelUtils.getColumnNo(envSheetName,
				GlobalVariables.databaseUserName);
		GlobalVariables.Ex_dbUserNameValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(envSheetName,
				GlobalVariables.databasePassword);
		GlobalVariables.Ex_dbPasswordValue = excelUtils.getCellData(currentRowNumber,
				colNumber);

	}

	// Set all the values to global variables from excel sheet
	public void setExcelValuesToVariables(String testCaseSheetName,
			String testDataSheetName, File excelFile, int currentRowNumber)
			throws Exception {

		excelUtils.setExcelFile(excelFile, testDataSheetName);

		colNumber = excelUtils.getColumnNo(testDataSheetName,
				GlobalVariables.tdCuidName);
		GlobalVariables.Ex_cuidValue = excelUtils.getCellData(2, colNumber);
		colNumber = excelUtils.getColumnNo(testDataSheetName,
				GlobalVariables.tdPasswordName);
		GlobalVariables.Ex_ldapPasswordValue = excelUtils.getCellData(2, colNumber);

		excelUtils.setExcelFile(excelFile, testCaseSheetName);
		
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcScenarioName);
		GlobalVariables.Ex_ScenarioNameValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcNameOfService);
		GlobalVariables.Ex_serviceNameValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcXMLName);
		GlobalVariables.Ex_xmlNameValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcAssertName);
		GlobalVariables.Ex_assertValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcAssertTag);
		GlobalVariables.Ex_assertTagValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		GlobalVariables.Ex_assertTagValue = GlobalVariables.Ex_assertTagValue.replace(":", "|");
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcBTN);
		GlobalVariables.Ex_btnValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcInputChannel);
		GlobalVariables.Ex_inputChannelValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcAccountType);
		GlobalVariables.Ex_accountTypeValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcBankAccountType);
		GlobalVariables.Ex_BankAccountTypeValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcCardType);
		GlobalVariables.Ex_cardTypeValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName, 
				GlobalVariables.tcCoBranded);
		GlobalVariables.Ex_CoBrandedValue=Boolean.parseBoolean(excelUtils.getCellData(
				currentRowNumber, colNumber));
		colNumber = excelUtils.getColumnNo(testCaseSheetName, 
				GlobalVariables.tcCustomerType);
		GlobalVariables.Ex_CustomerTypeValue=excelUtils.getCellData(
				currentRowNumber, colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName, 
				GlobalVariables.tcPaymentScheduleType);
		GlobalVariables.Ex_PaymentScheduleTypeValue=excelUtils.getCellData(
				currentRowNumber, colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName, 
				GlobalVariables.tcWalletType);
		GlobalVariables.Ex_WalletTypeValue=excelUtils.getCellData(
				currentRowNumber, colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName, 
				GlobalVariables.tcNickName);
		GlobalVariables.Ex_NickNameValue=excelUtils.getCellData(
				currentRowNumber, colNumber);
		
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcOrgPaymentIDName);
		GlobalVariables.Ex_originalPaymentIDValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcRefundAmtName);
		GlobalVariables.Ex_refundAmtValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcConvFeeAmtName);
		GlobalVariables.Ex_convFeeAmtValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcTotalTaxAmtName);
		GlobalVariables.Ex_totalTaxAmtValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcRefundReasonCodeName);
		GlobalVariables.Ex_refundReasonCodeValue = excelUtils.getCellData(currentRowNumber,
				colNumber);
		
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcWalletRefNumName);
		GlobalVariables.Ex_WalletRefNumvalue = excelUtils.getCellData(currentRowNumber,
				colNumber);

		colNumber = excelUtils.getColumnNo(testCaseSheetName,
					GlobalVariables.tcInstrumentIDName);
			
		GlobalVariables.Ex_InstrumentIDvalue = excelUtils.getCellData(currentRowNumber,
				colNumber);

		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcExpiryDateName);		
		GlobalVariables.Ex_ExpiryDatevalue = excelUtils.getCellData(currentRowNumber,
				colNumber);

		
		colNumber = excelUtils.getColumnNo(testCaseSheetName,
				GlobalVariables.tcNoOfIterations);
		GlobalVariables.Ex_noOfIterationsValue = Integer.parseInt(excelUtils
				.getCellData(currentRowNumber, colNumber));
		
		

	}
	
	//Write into status and comment
	public void WriteExecutionStatusToExcel(String Status,String Comments) throws Exception{
		colNumber = excelUtils.getColumnNo(GlobalVariables.tcSheetName,
				GlobalVariables.tcStatusName);
		excelUtils.setCellData(Status, GlobalVariables.currRowNumber,
				colNumber);
		colNumber = excelUtils.getColumnNo(GlobalVariables.tcSheetName,
				GlobalVariables.tcCommentsName);
		excelUtils.setCellData(Comments,
				GlobalVariables.currRowNumber, colNumber);
	}

	
	// Set all path values in global variables
	public void setPathValuesToVariables(String currDateTime) {
		
		GlobalVariables.genericXMLfilePath =  "src/XMLFiles/"
				+ GlobalVariables.Ex_serviceNameValue + "/";
		GlobalVariables.WTFFilePath = "src/ResultReports/"
				+ GlobalVariables.Ex_serviceNameValue;
		GlobalVariables.WTFile = new File(GlobalVariables.WTFFilePath + "/"
				+ GlobalVariables.Ex_serviceNameValue + "_" + currDateTime + ".txt");

	}
	
}
