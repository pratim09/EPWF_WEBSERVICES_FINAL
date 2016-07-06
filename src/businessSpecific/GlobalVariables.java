package businessSpecific;

import java.io.BufferedWriter;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.w3c.dom.Node;

import commonPackage.CommonClass;

public class GlobalVariables {

	// Excel Path
	public static String RelativePath = new File("src/TestData")
			.getAbsolutePath();
	public static String excelReportRelativePath = new File(
			"src/ResultReports/OtherReports/ExcelReports").getAbsolutePath();
	public static final String Path_TestData = RelativePath;
	public static final String File_TestData = "\\WebServicesData.xls";
	public static final String excelReportFilePath = excelReportRelativePath;
	public static final String envSheetName = "Environments";
	public static final String tcSheetName = "TestCases";
	public static final String tdSheetName = "TestData";

	// Final Card Numbers
	public static final String visaCardNumber = "4539930885467126";
	public static final String masterCardNumber = "5424180279791732";
	public static final String amexCardNumber = "373294280919414";
	public static final String discoverCardNumber = "6011054717989777";

	// File path related variables
	public static String genericXMLfilePath;
	public static String fullXMLfilePath;
	public static final String updatePaymentFileName = "UpdatePayment.xml";
	public static final String deTokenizationFileName = "deTokenization.xml";
	public static final String authorizePaymentFileName = "AuthorizePayment.xml";
	public static final String voidPaymentFileName = "voidPayment.xml";
	public static final String updateWalletFileName = "UpdateWallet.xml";
	public static final String writePaymentFilePath = "src/ResultReports/OtherReports/Payments";
	public static String WTFFilePath;
	public static File XMLFile;
	public static File WTFile;
	public static Scanner XMLFileReader;
	public static BufferedWriter writerObject;
	
	public static final String posRefundFileName = "POSRefund.xml";

	// HTTP Post variables
	public static SSLContext sslContext;
	public static String responseResult;
	public static CloseableHttpClient httpXMLRunner;
	public static HttpPost httpXMLPoster;
	public static ByteArrayEntity postDataEntity;
	public static CloseableHttpResponse httpXMLResponseReciever;
	public static HttpEntity responseConverter;

	// DOM Parser variables
	public static DocumentBuilderFactory docFactory;
	public static DocumentBuilder docBuilder;
	public static Document doc;
	public static Node FirstName;
	public static TransformerFactory transformerFactory;
	public static Transformer transformer;
	public static DOMSource source;
	public static StreamResult result;

	// jsoup variables
	public static Document jsoupParserResponse;
	public static Document jsoupParserModifyXML;
	public static String commonTagValue;
	public static Element FirstTagValue;
	public static String walletReferenceValue;

	// xml string format variable
	public static String XMLString = "";

	// variable for elements that are not found in a page
	public static String elementNotFound = "";

	// Modifying request XML tags
	// response tags
	public static final String sub_submitPaymentResponseTag = "xs:SubmitPaymentResponse";
	public static final String upd_UpdatePaymentResponseTag = "xs:UpdatePaymentResponse";
	public static final String act_AccountLookupResponseTag = "xs:AcctLookupResponse";
	public static final String ref_RefundResponseTag = "xs:RefundPaymentResponse";
	public static final String mng_WalletMgmtResponseTag = "xs:WalletMgmtSvcResponse";
	public static final String mul_MultiPaymentSubmitResponseTag = "xs:MultiPaymentSubmitResponse";
	public static final String aut_AuthorizePaymentResposeTag = "xs:TRX";
	public static final String aut_AutopayResponseTag = "xs:AutoPayMangementResponse";
	public static final String his_PaymentHistoryResponseTag = "xs:PaymentHistoryResponse";
	public static final String tokenizationresponceTag="xs:HSD";
	public static final String updWal_UpdateWalletResponseTag = "xs:WalletMgmtSvcResponse";
	public static final String retWal_RetrieveWalletResponseTag = "xs:WalletMgmtSvcResponse";

	// Submit payment request tags
	public static final String sub_BTNTag = "xs:BillingAcctId";
	public static final String sub_RegionTag = "xs:BillingAcctRegion";
	public static final String sub_BillingAppIDTag = "xs:BillingApplicationId";
	public static final String sub_DestAppIDTag = "xs:DestinationApplicationId";
	public static final String sub_InputChannelIDTag = "xs:InputChannelId";
	public static final String sub_SourceAppIDTag = "xs:SrcApplicationId";
	public static final String sub_MsgSourceSysTag = "bim:MessageSrcSystem";
	public static final String sub_RequestIDTag = "bim:RequestId";
	public static final String sub_CustomerTypeTag = "xs:CustomerType";
	public static final String sub_PaymentScheduleTypeTag = "xs:PaymentScheduleType";
	
	//wallet management request tags
	public static final String mng_BTNTag = "ns2:BillingAccountId";
	public static final String mng_RegionTag = "ns2:BillingAcctRegion";
	public static final String mng_RequestIDTag = "RequestId";
	public static final String mng_BillingAppIDTag = "ns2:BillingApplicationId";
	public static final String mng_WalletTypeTag = "ns2:WalletType";
	public static final String mng_MsgSourceSysTag = "MessageSrcSystem";

	// update payment request tag
	public static final String upd_paymentIDTag = "xs:PaymentId";
	public static final String upd_requestTag = "bim:RequestId";

	// account lookup request tags
	public static final String act_BTNTag = "BillingAccntId";
	public static final String act_BillingAppIDTag = "BillingApplicationId";
	public static final String act_SourceAppIDTag = "SrcApplicationId";
	public static final String act_MsgSourceSysTag = "qb:MessageSrcSystem";
	public static final String act_InputChannelIDTag = "InputChannelId";
	public static final String act_RequestIDTag = "qb:RequestId";

	// refund request tags
	public static final String ref_RequestIDTag = "bim:RequestId";
	public static final String ref_OriginalPaymentIDTag = "xs:OriginalPaymentId";
	public static final String ref_RefundPaymentAmtTag = "xs:RefundPaymentAmt";
	public static final String ref_ConvinienceFeeAmtTag = "xs:ConvenienceFeeAmt";
	public static final String ref_TotalTaxAmtTag = "xs:TotalTaxAmt";
	public static final String ref_RefundReasonCode = "xs:RefundRequestReasonCd";
	
	//MultiPayment Request tags
	public static final String mul_RequestIDTag = "bim:RequestId";
	public static final String mul_MsgSourceSystemTag = "bim:MessageSrcSystem";
	public static final String mul_SourceApplicationTag = "xs:SrcApplicationId";
	public static final String mul_InputChannelTag = "xs:InputChannelId";
	public static final String mul_PaymentProcessDateTag = "xs:PaymentProcessDateTime";
	public static final String mul_CustomerTypeTag = "xs:CustomerType";
	public static final String mul_DestinationAppIDTag = "xs:DestinationApplicationId";
	public static final String mul_BillAccntRegionTag = "xs:BillingAcctRegion";
	public static final String mul_BillAppIDTag = "xs:BillingApplicationId";
	public static final String mul_BillAccntIDTag = "xs:BillingAcctId";
	public static final String mul_PaymentAmtTag = "xs:PaymentAmt";
	
	//POSPayment and refund
	public static  final String  pos_VendorPaymentID = "xs:VendorPaymentId";
	
	//Autopay Request Tags
	public static final String autopay_BTNTag = "BillingAcctId";
	public static final String autopay_BillingAppIDTag = "BillingApplicationId";
	public static final String autopay_InputChannelIDTag = "InputChannelId";
	public  static final String autopay_RequestIDTag = "bim:RequestId";
	public static final String autopay_MsgSourceSysTag = "bim:MessageSrcSystem";
	public static final String autopay_SourceAppIDTag = "SrcApplicationId";
	
	//detokenization Tags Request
	public static final String detoken_TokenNum = "xs:TKN";
	
	//void payment Tas Request
	public static final String voidPay_PIDTag = "xs:PID";
	
	//Payment History Request Tags
	public static final String his_paramValue = "xs:ParamValue";

	//UpdateWallet Request Tags
		public static final String updWal_WalletRefNumTag = "xs:WalletReferenceNumber";
		public static final String updWal_InstrumentIDTag = "xs:InstrumentId";
		public static final String updWal_ExpiryDateTag = "xs:ExpiryDate";
		public static final String updWal_requestTag = "bim:RequestId";
		public static final String updWal_BillingAppIDTag = "xs:BillingApplicationId";
		public static final String updWal_CustomerTypeTag = "xs:CustomerType";
		public static final String updWal_billingAccntRegion = "xs:BillingAcctRegion";
		public static final String updWal_BTNTag = "xs:BillingAccountId";
		public static final String updWal_InputChannelIdTag = "xs:InputChannelId";
		public static final String updWal_requestIDTag = "bim:RequestId";
		
		//submit payment CPAL request Tags
		public static final String subcpal_BTNTag = "BillingAcctId";
		public static final String subcpal_RegionTag = "BillingAcctRegion";
		public static final String subcpal_BillingAppIDTag = "BillingApplicationId";
		public static final String subcpal_DestAppIDTag = "DestinationApplicationId";
		public static final String subcpal_InputChannelIDTag = "InputChannelId";
		public static final String subcpal_SourceAppIDTag = "SrcApplicationId";
		public static final String subcpal_MsgSourceSysTag = "MessageSrcSystem";
		public static final String subcpal_RequestIDTag = "RequestId";
		public static final String subcpal_CustomerTypeTag = "CustomerType";
		public static final String subcpal_PaymentScheduleTypeTag = "PaymentScheduleType";

	
		
	// Response XML Tags
	public static final String errorTag = "bim|ErrorMessage";
	public static final String paymentIDTag = "xs|PaymentId";
	public static final String AssociatePaymentIDTag = "xs|AssociatedPaymentId";
	public static final String sessionIDTag = "xs|SessionId";
	public static final String sessionURLTag = "xs|SessionURL";
	public static final String paymentStatusTag = "xs|PaymentStatus";
	public static final String WalletReferenceTag = "xs|WalletReferenceNumber";
	public static final String paymentVerificationTranIdTag = "xs|PaymentVerificationTranId";
	public static final String successNotificationTag = "xs|SuccessNotificationURL";
	public static final String expiryDateTag = "xs|ExpiryDate";
	
	
	public static final String InstrumentIdTag = "xs|InstrumentId";
	public static final String WalletReferenceNumberTag = "xs|WalletReferenceNumber";
	public static final String CustomerTypeTag = "xs|CustomerType";
	public static final String PIDTag = "xs|PID";
	public static final String RCDTag = "xs|RCD";
	public static final String PSTTag = "xs|PST";
	public static final String PRMTag = "xs|PRM";
	public static final String PACTag = "xs|PAC";
	
	// Request XML Variables
	public static String billAcctRegion;
	public static String billAppId;
	public static String destAppId;
	public static String sourceAppId;
	public static String msgSourceSysId;
	public static String requestId;

	// response XML values
	public static List<String> assertValues;
	public static String paymentIDValue;
	public static String paymentVrfTrnsIdValue;
	public static String sessionURLValue;
	public static String sessionIDValue;
	public static String transStatusValue;
	public static String ResponsePaymentStatusValue;
	public static String associatedPaymentIDValue;
	public static String successURLValue;
	
	public static String vandorPaymentID;
	public static String tokenNum;
	public static String PID;
	public static String RCD;
	
	// for each required variables
	public static ArrayList<Integer> envRowNumList;
	public static ArrayList<String> paymentsList = new ArrayList<String>();
	public static ArrayList<Integer> rowNumberList;

	// DB variables
	public static Connection DBConn;
	public static PreparedStatement Prepstmt;
	public static ResultSet queryResult;
	public static final String projCodeDefSourceAppQuery = "select SOURCE_APPLICATION_CD from project_code_def where input_channel_cd = '";
	public static final String paymentStatusQuery = "select PAYMENT_STATUS_CD from payment where payment_id = '";
	public static final String batchTransIDQuery = "select batch_transaction_id from batch_transaction where batch_transaction_typ = 'CaptureRequest';";
	public static final String walletCreationQuery = "select record_status_cd from wallet_request where wallet_reference_val = '";
	public static final String multiPaymentStatusQuery = "select payment_status_cd from payment where PMT_VERIFICATION_REFERENCE_VAL='";
	
	public static final String recordStatusQuey = "select record_status_cd from enrollment_request where BILLING_APPLICATION_ACCNT_ID = '";
	
	//DB Results stored variables
	public static ArrayList<String> paymentDBStatus = new ArrayList<String>();
	public static ArrayList<String> paymentDBTempStatus = new ArrayList<String>();
	public static String walletDBStatus;
	
	public static String autopayDBStatus;
	
	// Selenium variables
	public WebDriver driver;

	// Excel sheet columns
	public static final String executeStr = "Y";
	public static final String envName = "EnvironmentName";
	public static final String envEndPointName = "SOAPURL";
	public static final String adminURLName = "AdminURL";
	public static final String databaseConnectionString = "DatabaseConnectionString";
	public static final String databaseUserName = "DBUserName";
	public static final String databasePassword = "DBPassword";
	public static final String tcScenarioName = "ScenarioName";
	public static final String tcNameOfService = "ServiceName";
	public static final String tcXMLName = "XMLFileName";
	public static final String tcAssertName = "AssertValues";
	public static final String tcAssertTag = "AssertTag";
	public static final String tcBTN = "BTN";
	public static final String tcInputChannel = "InputChannel";
	public static final String tcPaymentIDTag = "PaymentIDTag";
	public static final String tcSessionIDTag = "SessionIDTag";
	public static final String tcSessionURLTag = "SessionURLTag";
	public static final String tcAccountType = "AccountType";
	public static final String tcBankAccountType = "BankAccountType";
	public static final String tcCardType = "CardType";
	public static final String tcRoutingNumber = "RoutingNumber";
	public static final String tcAccountNumber = "AccountNumber";
	public static final String tcCoBranded = "CoBranded";
	public static final String tcCustomerType = "CustomerType";
	public static final String tcPaymentScheduleType = "PaymentScheduleType";
	public static final String tcWalletType = "WalletType";
	public static final String tcNickName = "NickName";
	public static final String tcOrgPaymentIDName = "OrgPaymentID";
	public static final String tcRefundAmtName = "RefundAmt";
	public static final String tcConvFeeAmtName = "ConvFeeAmt";
	public static final String tcTotalTaxAmtName = "TotalTaxAmt";
	public static final String tcRefundReasonCodeName = "RefundReasonCode";
	public static final String tcNoOfIterations = "NoOfIterations";
	public static final String tcStatusName = "Status";
	public static final String tcCommentsName = "Comments";
	public static final String tdCuidName = "CUID";
	public static final String tdPasswordName = "Password";

	public static final String tcWalletRefNumName = "WalletReferenceNumber";
	public static final String tcInstrumentIDName = "InstrumentID";
	public static final String tcExpiryDateName = "ExpiryDate";

	
	// Excel values to column names
	public static String Ex_ScenarioNameValue;
	public static String Ex_serviceNameValue;
	public static String Ex_xmlNameValue;
	public static String Ex_assertValue;
	public static String Ex_assertTagValue;
	public static String Ex_btnValue;
	public static String Ex_inputChannelValue;
	public static String Ex_dbConnectionStringValue;
	public static String Ex_dbUserNameValue;
	public static String Ex_dbPasswordValue;
	public static String Ex_accountTypeValue;
	public static String Ex_BankAccountTypeValue;
	public static String Ex_cardTypeValue;
	public static boolean Ex_CoBrandedValue;
	public static String Ex_CustomerTypeValue;
	public static String Ex_PaymentScheduleTypeValue;
	public static String Ex_NickNameValue;
	public static String Ex_WalletTypeValue;
	public static String Ex_originalPaymentIDValue;
	public static String Ex_refundAmtValue;
	public static String Ex_convFeeAmtValue;
	public static String Ex_totalTaxAmtValue;
	public static String Ex_refundReasonCodeValue;
	public static int Ex_noOfIterationsValue;
	public static String Ex_cuidValue;
	public static String Ex_ldapPasswordValue;
	public static String Ex_adminURLValue;
	public static String Ex_endPointUrlValue;
	

	public static String Ex_WalletRefNumvalue;
	public static String Ex_InstrumentIDvalue;
	public static String Ex_ExpiryDatevalue;
	// rowNumber Variable
	public static int currRowNumber = 0;

	// other important variables
	
	public static String currDateTime;
	public static final String routingNumber = "011000015";
	public static final String savingsTextHCDE = "New Savings Account";
	public static final String checkingsTextHCDE = "New Checking Account";
	public static final String moneyMarketTextHCDE = "New Money Market Account";
	public static final String creditOrDebitTextHCDE = "New Credit or Debit Account";
	public static String envNameValue;
	public static final String variousPaymentStatus[] = { "capture_ready",
			"capture_requested", "settlement_completed", "posting_requested",
			"posted" };
	public static final String walletRecordStatus = "Processed";
	
	public static final String autopayRecordStatus[] = {"Processed", "Submitted"};

	
	public static final String successNotificationTagForTokenization="xs|SEU"; 
}
