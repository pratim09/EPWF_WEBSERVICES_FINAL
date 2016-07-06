package businessSpecific;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import commonPackage.CommonClass;
import commonPackage.CommonSeleniumClass;
import commonPackage.ExcelUtils;
import commonPackage.HTTPPostRequest;

public class ScenariosSequencing {
	private static int colNumber;
	private static String IFrameType = "";
	private static boolean IframeSuccessful = false;
	private static boolean responseVerification = false;

	// Objects for different classes
	ExcelOperations excelOperations = new ExcelOperations();
	VerifyExpectedActual verifyExpectedActual = new VerifyExpectedActual();
	VerifyDBDetails verifyDBDetails = new VerifyDBDetails();
	ModifyXMLForServices modifyXMLForServices = new ModifyXMLForServices();
	HTTPPostRequest httpPostRequest = new HTTPPostRequest();
	CommonClass commonClass = new CommonClass();
	CommonSeleniumClass commonSeleniumClass = new CommonSeleniumClass();
	IFrameNavigation iFrameNavigation = new IFrameNavigation();
	GlobalVariables gv = new GlobalVariables();

	// send submit payment request and take it to capture_ready
	public void SubmitPayment(String submitPaymentXMLFilePath) throws Throwable {
		int paymentStatusIndex = 0;

	//	modifyXMLForServices.modifyForSubmitPayment(submitPaymentXMLFilePath);
		boolean check = commonHTTPRequestSequence(submitPaymentXMLFilePath,
				false, GlobalVariables.Ex_serviceNameValue);
		if (check) {
			GlobalVariables.paymentIDValue = httpPostRequest
					.getTagValue(GlobalVariables.paymentIDTag);
			GlobalVariables.paymentsList.add("Env :"
					+ GlobalVariables.envNameValue + "|| Payment ID : "
					+ GlobalVariables.paymentIDValue);
			GlobalVariables.sessionIDValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionIDTag);
			GlobalVariables.sessionURLValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionURLTag);
			GlobalVariables.successURLValue = httpPostRequest
					.getTagValueFromRequestXML(GlobalVariables.successNotificationTag);
			IFrameType = analyseSessionURL();
			switch (IFrameType) {
			case "IBM":
				
				IframeSuccessful = iFrameNavigation.payFromIBMiFrame(openFirefoxDriver());
				break;
			case "HCDE":
				IframeSuccessful = iFrameNavigation.payFromHCDEIframe(openFirefoxDriver());
				break;
			default:
				System.out.println("incorrect URL");
				IframeSuccessful = false;
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"Incorrect Session URL:"
								+ GlobalVariables.sessionURLValue);
				break;
			}
			if (IframeSuccessful) {
				GlobalVariables.XMLString = "";
				System.out.println(GlobalVariables.paymentIDValue);
				modifyXMLForServices
						.modifyUpdatePayment(GlobalVariables.genericXMLfilePath
								+ GlobalVariables.updatePaymentFileName);
				GlobalVariables.Ex_serviceNameValue = "UpdatePayment";
				if (UpdatePayment(GlobalVariables.genericXMLfilePath
						+ GlobalVariables.updatePaymentFileName)) {
					verifyDBDetails.checkPaymentStatus(
							GlobalVariables.paymentIDValue,
							GlobalVariables.paymentStatusQuery, false);
					long startTime = Long.parseLong(commonClass
							.getCurrentDateTime("yyyyMMddHHmmss"));
					if(GlobalVariables.paymentDBStatus.get(paymentStatusIndex).equalsIgnoreCase("Approved")){
						verifyDBDetails.checkPaymentStatus(
								GlobalVariables.paymentIDValue,
								GlobalVariables.paymentStatusQuery, true);
						while (GlobalVariables.paymentDBTempStatus.get(
							paymentStatusIndex).equalsIgnoreCase("Approved")) {
						System.out.println(GlobalVariables.paymentDBTempStatus.get(
								paymentStatusIndex));
						verifyDBDetails.checkPaymentStatus(
								GlobalVariables.paymentIDValue,
								GlobalVariables.paymentStatusQuery, true);
						paymentStatusIndex++;
						if (Long.parseLong(commonClass
								.getCurrentDateTime("yyyyMMddHHmmss"))
								- startTime > 200 && GlobalVariables.paymentDBTempStatus.get(
										paymentStatusIndex).equalsIgnoreCase("Approved"))
							
							break;
					}
					}
					boolean status = verifyDBDetails
							.verifyPaymentStatusPresence(GlobalVariables.paymentDBTempStatus
									.get(paymentStatusIndex));
					verifyExpectedActual.verifyDBExpectedValue(status, false,
							paymentStatusIndex);
				}
		}}

	}

	// Wallet Management
	public void walletManagement(String walletManagementXMLFilePath)
			throws Exception {
		int paymentStatusIndex = 0;
		modifyXMLForServices
				.modifyForwalletManagement(walletManagementXMLFilePath);
		boolean check = commonHTTPRequestSequence(walletManagementXMLFilePath,
				false, GlobalVariables.Ex_serviceNameValue);
		if (check) {
			GlobalVariables.walletReferenceValue = httpPostRequest
					.getTagValue(GlobalVariables.WalletReferenceTag);
			GlobalVariables.sessionIDValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionIDTag);
			GlobalVariables.sessionURLValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionURLTag);
			GlobalVariables.successURLValue = httpPostRequest
					.getTagValueFromRequestXML(GlobalVariables.successNotificationTag);
			analyseSessionURL();
			
			IframeSuccessful = iFrameNavigation.createWalletFromIframe(openFirefoxDriver());

			if (IframeSuccessful) {

				verifyDBDetails
						.checkRecordStatusforWalletManagement(GlobalVariables.walletReferenceValue);
				boolean status = verifyDBDetails
						.verifyAutopayrecordPresence(GlobalVariables.walletDBStatus);
				verifyExpectedActual.verifyDBExpectedValue(status, true,
						paymentStatusIndex);
			}
		}
	}

	// MultiPayment
	public void multiPayment(String MultiPaymentXMLFilePath) throws Exception {
		int paymentStatusIndex = 0;
		ArrayList<String> btnArrayList = new ArrayList<String>();
		for (String btn : GlobalVariables.Ex_btnValue.split(",")) {
			btnArrayList.add(btn);
		}
		modifyXMLForServices.modifyMultiPayment(MultiPaymentXMLFilePath,
				btnArrayList);
		boolean check = commonHTTPRequestSequence(MultiPaymentXMLFilePath,
				false, GlobalVariables.Ex_serviceNameValue);
		if (check) {
			GlobalVariables.paymentVrfTrnsIdValue = httpPostRequest
					.getTagValue(GlobalVariables.paymentVerificationTranIdTag);
			GlobalVariables.paymentIDValue = GlobalVariables.paymentVrfTrnsIdValue;
			System.out.println("Payment ID value:"
					+ GlobalVariables.paymentIDValue);
			GlobalVariables.sessionIDValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionIDTag);
			GlobalVariables.sessionURLValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionURLTag);
			GlobalVariables.successURLValue = httpPostRequest
					.getTagValueFromRequestXML(GlobalVariables.successNotificationTag);
			IFrameType = analyseSessionURL();
			switch (IFrameType) {
			case "IBM":
				
				IframeSuccessful = iFrameNavigation.payFromIBMiFrame(openFirefoxDriver());
				break;
			case "HCDE":
				
				IframeSuccessful = iFrameNavigation.payFromHCDEIframe(openFirefoxDriver());
				break;
			default:
				System.out.println("incorrect URL");
				IframeSuccessful = false;
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"Incorrect Session URL:"
								+ GlobalVariables.sessionURLValue);
				break;
			}

			if (IframeSuccessful) {
				Thread.sleep(20000);
				verifyDBDetails.checkPaymentStatus(
						GlobalVariables.paymentVrfTrnsIdValue,
						GlobalVariables.multiPaymentStatusQuery, false);
				/*long startTime = Long.parseLong(commonClass
						.getCurrentDateTime("yyyyMMddHHmmss"));
				for (int i = 0; i < GlobalVariables.paymentDBStatus.size(); i++) {
					if (GlobalVariables.paymentDBStatus.get(i)
							.equalsIgnoreCase("Approved")) {
						verifyDBDetails.checkPaymentStatus(
								GlobalVariables.paymentVrfTrnsIdValue,
								GlobalVariables.multiPaymentStatusQuery, true);
						while (GlobalVariables.paymentDBStatus.get(i)
								.equalsIgnoreCase("Approved")) {
							paymentStatusIndex++;
							System.out.println("Payment Status:"
									+ GlobalVariables.paymentDBStatus
											.get(paymentStatusIndex));
							verifyDBDetails.checkPaymentStatus(
									GlobalVariables.paymentVrfTrnsIdValue,
									GlobalVariables.multiPaymentStatusQuery,
									true);

							if (Long.parseLong(commonClass
									.getCurrentDateTime("yyyyMMddHHmmss"))
									- startTime > 200)
								break;
						}
					}
					paymentStatusIndex = 0;
				}*/
				ArrayList<Boolean> status = new ArrayList<Boolean>();
				boolean finalStatus = true;
				for (int i = 0; i < GlobalVariables.paymentDBStatus.size(); i++) {
					status.add(verifyDBDetails
							.verifyPaymentStatusPresence(GlobalVariables.paymentDBStatus
									.get(i)));
					if (status.get(i) == false) {
						finalStatus = false;
						break;
					}

				}
				verifyExpectedActual.verifyDBExpectedValue(finalStatus, false,
						paymentStatusIndex);
			}
		}

	}

	// Refund Payment
	public void refundPayment(String refundXMLFilePath) throws Exception {
		modifyXMLForServices.modifyForRefundPayment(refundXMLFilePath);
		boolean check = commonHTTPRequestSequence(refundXMLFilePath, false,
				GlobalVariables.Ex_serviceNameValue);
		if (check) {
			GlobalVariables.paymentIDValue = httpPostRequest
					.getTagValue(GlobalVariables.paymentIDTag);
			GlobalVariables.associatedPaymentIDValue = httpPostRequest
					.getTagValue(GlobalVariables.AssociatePaymentIDTag);
			GlobalVariables.ResponsePaymentStatusValue = httpPostRequest
					.getTagValue(GlobalVariables.paymentStatusTag);
			verifyExpectedActual.verifyExpectedActualForOthers("Capture_Ready",
					GlobalVariables.ResponsePaymentStatusValue, true);
		}
	}

	// make update payment with payment ID
	private boolean UpdatePayment(String updatePaymentXMLFilePath)
			throws Exception {
		return commonHTTPRequestSequence(updatePaymentXMLFilePath, false,
				GlobalVariables.Ex_serviceNameValue);
	}

	// Account lookup
	public void AccountLookup(String accountLookupXMLFilePath) throws Exception {
		modifyXMLForServices.modifyForAccountLookup(accountLookupXMLFilePath);
		commonHTTPRequestSequence(accountLookupXMLFilePath, true,
				GlobalVariables.Ex_serviceNameValue);
	}
	
	
	
	public void submitpaymentElectronicsandNonElectronics( String submitpaymentElectronicsnonelectronics) throws Exception{	
		modifyXMLForServices.modifyForSubmitPaymentCPAL(submitpaymentElectronicsnonelectronics);
		boolean check=	commonHTTPRequestSequence(submitpaymentElectronicsnonelectronics, false,
				GlobalVariables.Ex_serviceNameValue);
		if (check) {
			GlobalVariables.paymentIDValue = httpPostRequest
					.getTagValue(GlobalVariables.paymentIDTag);
			GlobalVariables.paymentsList.add("Env :"
					+ GlobalVariables.envNameValue + "|| Payment ID : "
					+ GlobalVariables.paymentIDValue);
			GlobalVariables.sessionIDValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionIDTag);
			GlobalVariables.sessionURLValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionURLTag);
			GlobalVariables.successURLValue = httpPostRequest
					.getTagValueFromRequestXML(GlobalVariables.successNotificationTag);
			String paymentStatusValue = httpPostRequest
					.getTagValue(GlobalVariables.paymentStatusTag);
			verifyExpectedActual.verifyPaymentStatus("Session_InProgress", paymentStatusValue, GlobalVariables.errorTag);
			IFrameType = analyseSessionURL();
			switch (IFrameType) {
			case "IBM":
				
				IframeSuccessful = iFrameNavigation.payFromIBMiFrame(openFirefoxDriver());
				break;
			case "HCDE":
				IframeSuccessful = iFrameNavigation.payFromHCDEIframe(openFirefoxDriver());
				break;
			default:
				System.out.println("incorrect URL");
				IframeSuccessful = false;
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"Incorrect Session URL:"
								+ GlobalVariables.sessionURLValue);
				break;
				
			}
			if (IframeSuccessful) {
				GlobalVariables.XMLString = "";
				System.out.println(GlobalVariables.paymentIDValue);
				modifyXMLForServices
						.modifyUpdatePayment(GlobalVariables.genericXMLfilePath
								+ GlobalVariables.updatePaymentFileName);
				GlobalVariables.Ex_serviceNameValue = "UpdatePayment";
				if (UpdatePayment(GlobalVariables.genericXMLfilePath
						+ GlobalVariables.updatePaymentFileName)) {
					verifyExpectedActual.verifyPaymentStatus(httpPostRequest.getTagValue(GlobalVariables.paymentStatusTag), paymentStatusValue, GlobalVariables.errorTag);
			}
		}
	}}
	
	
public void POSPayment(String posPaymentXMLPAth) throws Exception{
		GlobalVariables.vandorPaymentID  = commonClass.getCurrentDateTime("yyyyMMddHHmmss");
		modifyXMLForServices.modifyForPOSPayment(posPaymentXMLPAth);
		boolean check = commonHTTPRequestSequence(posPaymentXMLPAth, false,
				GlobalVariables.Ex_serviceNameValue);
		if(check){
			GlobalVariables.paymentIDValue =  httpPostRequest
					.getTagValue(GlobalVariables.paymentIDTag);
			GlobalVariables.paymentsList.add("Env :"
					+ GlobalVariables.envNameValue + "|| Payment ID : "
					+ GlobalVariables.paymentIDValue);
			String paymentStatusValue = httpPostRequest
					.getTagValue(GlobalVariables.paymentStatusTag);
		
			boolean flag = verifyExpectedActual.verifyPaymentStatus("Settlement_Completed",
							paymentStatusValue, GlobalVariables.errorTag);
			System.out.println("pass");
			if(flag){
				GlobalVariables.XMLString = "";
				modifyXMLForServices
				.modifyForPOSRefund(GlobalVariables.genericXMLfilePath
						+ GlobalVariables.posRefundFileName);
				GlobalVariables.Ex_serviceNameValue = "POS";
				if (POSRefund(GlobalVariables.genericXMLfilePath
						+ GlobalVariables.posRefundFileName)) {
					System.out.println("abc");
				paymentStatusValue = httpPostRequest
							.getTagValue(GlobalVariables.paymentStatusTag);
				System.out.println("paymentStatusValue = "+paymentStatusValue);
				flag =  verifyExpectedActual.verifyPaymentStatus("Settlement_Completed",
								paymentStatusValue, GlobalVariables.errorTag);
				System.out.println(flag);
					if(flag){
						excelOperations.WriteExecutionStatusToExcel("Pass",
								"POS Refund Sucessfull");
					}
					
				}
			}

			}
		}
	
	
//make POSRefund with payment ID
	private boolean POSRefund(String posRefundXMLFilePath)
			throws Exception {
		return commonHTTPRequestSequence(posRefundXMLFilePath, false,
				GlobalVariables.Ex_serviceNameValue);
	}	


	//submit payment CPAL_Non Electronic
	public void submitPayment_CPALNonElectronic(String submitPaymentXMLFilePath) throws Exception{
		modifyXMLForServices.modifyForSubmitPaymentCPAL(submitPaymentXMLFilePath);
		boolean check=	commonHTTPRequestSequence(submitPaymentXMLFilePath,
						false, GlobalVariables.Ex_serviceNameValue);
	if(check)
	{
		GlobalVariables.paymentIDValue = httpPostRequest
				.getTagValue(GlobalVariables.paymentIDTag);
		GlobalVariables.paymentsList.add("Env :"
				+ GlobalVariables.envNameValue + "|| Payment ID : "
				+ GlobalVariables.paymentIDValue);
		String paymentStatusValue = httpPostRequest
				.getTagValue(GlobalVariables.paymentStatusTag);
	
		boolean flag = verifyExpectedActual.verifyPaymentStatus("Session_Initiated",
						paymentStatusValue, GlobalVariables.errorTag);
		if(flag){
			GlobalVariables.XMLString = "";
			System.out.println(GlobalVariables.paymentIDValue);
			modifyXMLForServices
					.modifyUpdatePayment(GlobalVariables.genericXMLfilePath
							+ GlobalVariables.updatePaymentFileName);
			GlobalVariables.Ex_serviceNameValue = "UpdatePayment";
			if (UpdatePayment(GlobalVariables.genericXMLfilePath
					+ GlobalVariables.updatePaymentFileName)) {
				verifyExpectedActual.verifyPaymentStatus("Session_Initiated", httpPostRequest.getTagValue(GlobalVariables.paymentStatusTag), GlobalVariables.errorTag);
			}
			}
		}
	}
		
	
	// auto pay deEnrollment
	public void autopay_deEnrollment(String deEnrollmentXMLPath) throws Exception{
		modifyXMLForServices.modifyForAutopay(deEnrollmentXMLPath);
		boolean check=	commonHTTPRequestSequence(deEnrollmentXMLPath,
				false, GlobalVariables.Ex_serviceNameValue);
		if(check){
				verifyDBDetails
						.checkRecordStatusforAutopay(GlobalVariables.Ex_btnValue);
				boolean status = verifyDBDetails
						.verifyAutopayrecordPresence(GlobalVariables.autopayDBStatus);
				verifyExpectedActual.verifyDBExpectedValueForAutopay(status);
			
		}
	}
		
public  void autopay_Update(String updatexmlfilepath) throws Exception {
		
		modifyXMLForServices.modifyForAutopay(updatexmlfilepath);
		boolean check = commonHTTPRequestSequence(updatexmlfilepath,
				false, GlobalVariables.Ex_serviceNameValue);
		if (check) {
			GlobalVariables.sessionIDValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionIDTag);
			GlobalVariables.sessionURLValue = httpPostRequest
					.getTagValue(GlobalVariables.sessionURLTag);
			GlobalVariables.successURLValue = httpPostRequest
					.getTagValueFromRequestXML(GlobalVariables.successNotificationTag);
			IframeSuccessful = iFrameNavigation.Auto_payFromHCDEIframe(openFirefoxDriver());
			}
			
	if(IframeSuccessful){
			verifyDBDetails
			.checkRecordStatusforAutopay(GlobalVariables.Ex_btnValue);
	boolean status = verifyDBDetails.verifyAutopayrecordPresence(GlobalVariables.autopayDBStatus);
			System.out.println(status);
	verifyExpectedActual.verifyDBExpectedValueForAutopay(status);
		}
	}
	
public void autoPay_Enrollment(String autoPayEnrollmentXMLFilePath) throws Exception{
	modifyXMLForServices.modifyForAutopay(autoPayEnrollmentXMLFilePath);
	boolean check = commonHTTPRequestSequence(autoPayEnrollmentXMLFilePath, 
								false, GlobalVariables.Ex_serviceNameValue);
	if(check){
		GlobalVariables.sessionIDValue = httpPostRequest
				.getTagValue(GlobalVariables.sessionIDTag);
		GlobalVariables.sessionURLValue = httpPostRequest
				.getTagValue(GlobalVariables.sessionURLTag);
		GlobalVariables.successURLValue = httpPostRequest
				.getTagValueFromRequestXML(GlobalVariables.successNotificationTag);
			IframeSuccessful = iFrameNavigation.Auto_payFromHCDEIframe(openFirefoxDriver());
		}
		if (IframeSuccessful) {
			verifyDBDetails.checkRecordStatusforAutopay(GlobalVariables.Ex_btnValue);
			boolean status = verifyDBDetails.verifyAutopayrecordPresence(GlobalVariables.autopayDBStatus);
			verifyExpectedActual.verifyDBExpectedValueForAutopay(status);
		}
	}

public void  paymentHistory(String paymentHistoryXMLFilePath) throws Exception {
	modifyXMLForServices.modifyForPaymentHistory(paymentHistoryXMLFilePath);
	boolean check = commonHTTPRequestSequence(paymentHistoryXMLFilePath, 
				false, GlobalVariables.Ex_serviceNameValue);
		
	if(check){
		excelOperations.WriteExecutionStatusToExcel("PASS", "Payment History is Successfull.");
	}
}
	

public void tokenization(String tokenizationxmlFilepath) throws Exception {
	boolean check=commonHTTPRequestSequence(tokenizationxmlFilepath,
			false, GlobalVariables.Ex_serviceNameValue);
	
	if (check) {
		GlobalVariables.sessionURLValue = httpPostRequest
				.getTagValue(GlobalVariables.successNotificationTagForTokenization);
		
		IframeSuccessful = iFrameNavigation.getNevigationForTokenization(openFirefoxDriver());			
		}
}

// deTokenization
	public void deTokenization(String deTokenizationXMLFilePath) throws Exception{
		GlobalVariables.Ex_endPointUrlValue = "https://hcde-itv1.centurylink.com/HCDE/Internal/services/session/initiate";
		modifyXMLForServices
				.modifyForDetokaniztion(deTokenizationXMLFilePath);
		boolean check=	commonHTTPRequestSequence(deTokenizationXMLFilePath,
				false, GlobalVariables.Ex_serviceNameValue);
		if(check){
			GlobalVariables.sessionURLValue = httpPostRequest.getTagValue(GlobalVariables.successNotificationTagForTokenization);
			iFrameNavigation.getNevigationForDeTokenization(openFirefoxDriver());
	}
	}
//authorize payment
	public void authorizePayment(String authorizePaymentXMLFilePath) throws Exception{
		GlobalVariables.Ex_endPointUrlValue = "https://hcde-itv1.centurylink.com/HCDE/Internal/services/payment";
		modifyXMLForServices
				.modifyForAuthorizePayment(authorizePaymentXMLFilePath);
		boolean check=	commonHTTPRequestSequence(authorizePaymentXMLFilePath,
				false, GlobalVariables.Ex_serviceNameValue);
		int count = 0;
		if(check){
			GlobalVariables.PID = httpPostRequest.getTagValue(GlobalVariables.PIDTag);
			if(httpPostRequest.getTagValue(GlobalVariables.PACTag).equalsIgnoreCase("AUTHORIZE")){
				System.out.println("PAC Tag is verified as"+httpPostRequest.getTagValue(GlobalVariables.PACTag));
				count++;
			}else{
				System.out.println("PAC Tag is not verified as"+httpPostRequest.getTagValue(GlobalVariables.PACTag));
			}
			if(httpPostRequest.getTagValue(GlobalVariables.PSTTag).equalsIgnoreCase("APPROVED")){
				System.out.println("PST Tag is verified as"+httpPostRequest.getTagValue(GlobalVariables.PSTTag));
				count++;
			}else{
				System.out.println("PST Tag is not verified as"+httpPostRequest.getTagValue(GlobalVariables.PSTTag));
			}
			if(httpPostRequest.getTagValue(GlobalVariables.PRMTag).equalsIgnoreCase("Approved")){
				System.out.println("PRM Tag is verified as"+httpPostRequest.getTagValue(GlobalVariables.PRMTag));
				count++;
			}else{
				System.out.println("PRM Tag is not verified as"+httpPostRequest.getTagValue(GlobalVariables.PRMTag));
			}
			if(httpPostRequest.getTagValue(GlobalVariables.RCDTag).equalsIgnoreCase("S")){
				System.out.println("RCD Tag is verified as"+httpPostRequest.getTagValue(GlobalVariables.RCDTag));
				count++;
			}else{
				System.out.println("RCD Tag is not verified as"+httpPostRequest.getTagValue(GlobalVariables.RCDTag));
			}
			if(count == 4){
				excelOperations.WriteExecutionStatusToExcel("PASS", "Authorize Payment is successfull");
			}else {
				System.out.println("Authorize Payment was not successful");
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"Authorize Payment was not successful.Navigated to:"
								+ " Session ID is:"
								+ GlobalVariables.sessionIDValue);
			}
		}
	}

//void payment
	public void voidPayment(String voidPaymentXMLPath) throws Exception{
		GlobalVariables.Ex_endPointUrlValue = "https://hcde-itv1.centurylink.com/HCDE/Internal/services/payment";
		modifyXMLForServices
				.modifyForVoidPayment(voidPaymentXMLPath);
		boolean check=	commonHTTPRequestSequence(voidPaymentXMLPath,
				false, GlobalVariables.Ex_serviceNameValue);
		if(check){
			int count = 0;
			if(httpPostRequest.getTagValue(GlobalVariables.RCDTag).equalsIgnoreCase("S")){
				System.out.println("RCD Tag is verified as"+httpPostRequest.getTagValue(GlobalVariables.RCDTag));
				count++;
			}else{
				System.out.println("RCD Tag is not verified as"+httpPostRequest.getTagValue(GlobalVariables.RCDTag));
			}
			if(count == 1){
				excelOperations.WriteExecutionStatusToExcel("PASS", "void Payment is successfull");
			}else {
				System.out.println("void Payment was not successful");
				excelOperations.WriteExecutionStatusToExcel("FAIL",
						"void Payment was not successful.Navigated to:"
								+ " Session ID is:"
								+ GlobalVariables.sessionIDValue);
			}
		}
	}


	public void updateWallet(String updateWalletXMLFilePath) throws Exception{
		modifyXMLForServices.modifyForUpdateWallet(updateWalletXMLFilePath);
		boolean check = commonHTTPRequestSequence(updateWalletXMLFilePath, true,
				GlobalVariables.Ex_serviceNameValue);
		if(check){
			excelOperations.WriteExecutionStatusToExcel("PASS", "Update Wallet is Successfull.");
		}
	}
	
	
	public void retrieveWallet(String retrieveWalletXMLFilePath) throws Exception{
		GlobalVariables.Ex_endPointUrlValue = "https://itv1.qecom.dev.qintra.com/EPWFAppWeb/EPWFServices";
		modifyXMLForServices.modifyForRetrieveWallet(retrieveWalletXMLFilePath);
		boolean check = commonHTTPRequestSequence(retrieveWalletXMLFilePath, true, 
				GlobalVariables.Ex_serviceNameValue);
		if(check){
			excelOperations.WriteExecutionStatusToExcel("PASS", "Retrieve Wallet is Successfull.");
		}
	}
	
	
	
	
	
	
	
	// send http post request and get response
	private boolean commonHTTPRequestSequence(String XMLFilePath,
			boolean accntLookUp, String serviceName) throws Exception {
		httpPostRequest.setXMLStringValue(XMLFilePath);
		httpPostRequest.createTrustedCertificate();
		httpPostRequest.setHttpClient();
		httpPostRequest.setHTTPPost();
		httpPostRequest.sendHTTPPostRequest();
		httpPostRequest.formatResponseToString();
		switch (serviceName) {
		case "AccountLookup":
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.act_AccountLookupResponseTag))
				responseVerification = performResponseVerification(true);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "SubmitPayment":
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.sub_submitPaymentResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "RefundPayment":
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.ref_RefundResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "UpdatePayment":
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.upd_UpdatePaymentResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "WalletManagement":
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.mng_WalletMgmtResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "MultiPayment":
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.mul_MultiPaymentSubmitResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "POS":
			if(GlobalVariables.responseResult
					.contains(GlobalVariables.sub_submitPaymentResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "Autopay":
			if(GlobalVariables.responseResult
					.contains(GlobalVariables.aut_AutopayResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "PaymentHistory":
			if(GlobalVariables.responseResult.contains(GlobalVariables.his_PaymentHistoryResponseTag))
				responseVerification = performResponseVerification(false);
			else {
				responseVerification = false;
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "Tokenization" :
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.tokenizationresponceTag)){
				System.out.println("Successfull response received");
				return true;
		}else
			{
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "deTokenization" :
			if (GlobalVariables.responseResult
					.contains(GlobalVariables.tokenizationresponceTag)){
				System.out.println("Successfull response received");
				return true;
		}else
			{
				excelOperations
						.WriteExecutionStatusToExcel("FAIL",
								"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "AuthorizePayment":
			if(GlobalVariables.responseResult
					.contains(GlobalVariables.aut_AuthorizePaymentResposeTag)){
				System.out.println("Successfull response received");
			return true;
	}else{
			excelOperations
					.WriteExecutionStatusToExcel("FAIL",
							"Response incorrect, env might be down. Check Response in File!!");
		}
			break;
		case "voidPayment":
			if(GlobalVariables.responseResult
					.contains(GlobalVariables.aut_AuthorizePaymentResposeTag)){
				System.out.println("Successfull response received");
			return true;
	}else{
			excelOperations
					.WriteExecutionStatusToExcel("FAIL",
							"Response incorrect, env might be down. Check Response in File!!");
		}
			break;
		case "UpdateWallet":
			if(GlobalVariables.responseResult
					.contains(GlobalVariables.updWal_UpdateWalletResponseTag)){
				responseVerification = performResponseVerification(false);
			} else {
				responseVerification = false;
				excelOperations
					.WriteExecutionStatusToExcel("FAIL", 
						"Response incorrect, env might be down. Check Response in File!!");
			}
			break;
		case "RetrieveWallet":
			if(GlobalVariables.responseResult
				.contains(GlobalVariables.retWal_RetrieveWalletResponseTag)){
				responseVerification = performResponseAndExpiryDateVerification();
			} else {
				responseVerification = false;
				excelOperations
					.WriteExecutionStatusToExcel("FAIL", 
						"Response incorrect, env might be down. Check Response in File!!");
			}
			break;

		default:
			System.out.println("invalid service name");
			responseVerification = false;
			excelOperations
					.WriteExecutionStatusToExcel(
							"FAIL",
							"Please double check the service name column in test cases sheet of the excel file!!");
			break;
		}

		return responseVerification;
	}

	private boolean performResponseVerification(boolean accntLookUp)
			throws Exception {

		String assertTagValue = httpPostRequest
				.getTagValue(GlobalVariables.Ex_assertTagValue);
		return verifyExpectedActual.verifyTagExpectedValue(
				GlobalVariables.Ex_assertValue, assertTagValue,
				GlobalVariables.errorTag, accntLookUp);

	}

	private boolean performResponseAndExpiryDateVerification() 
			throws Exception{
		String assertTagValue = httpPostRequest
				.getTagValue(GlobalVariables.Ex_assertTagValue);
		System.out.println("assertTagValue in performResponseVerification() = "+assertTagValue);
		
		String expiryDateValue = httpPostRequest
				.getTagValue(GlobalVariables.expiryDateTag);
		System.out.println("expiryDateValue in performExpiryDateVerificationForRetrieveWallet = "+expiryDateValue);

		return verifyExpectedActual.verifyResponseAndExpiryDateTagExpectedValue(
				GlobalVariables.Ex_assertValue, assertTagValue,
				GlobalVariables.Ex_ExpiryDatevalue, expiryDateValue, GlobalVariables.errorTag);
	}
	
	
	// Open firefox driver
	private WebDriver openFirefoxDriver() {
		gv.driver = commonSeleniumClass
				.selectRequiredBrowser("FireFox");
		gv.driver.manage().timeouts()
				.implicitlyWait(60, TimeUnit.SECONDS);
		return gv.driver;
	}

	private String analyseSessionURL() {
		if (GlobalVariables.sessionURLValue.contains("ips"))
			return "IBM";
		else if (GlobalVariables.sessionURLValue.contains("hcde"))
			return "HCDE";
		else
			return "";

	}

}
