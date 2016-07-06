package businessSpecific;

import java.sql.SQLException;
import java.util.Arrays;

import commonPackage.CommonClass;

public class VerifyDBDetails {
	CommonClass commonClass = new CommonClass();

	// Get payment status from DB
	public void checkPaymentStatus(String paymentID,String paymentQuery,boolean secondTime) throws SQLException {
		commonClass.queryResultFromDB(paymentQuery
				+ paymentID + "'");
		while (GlobalVariables.queryResult.next()) {
			if(secondTime){
				GlobalVariables.paymentDBTempStatus.add(GlobalVariables.queryResult
						.getString("payment_status_cd"));
			}else{
				GlobalVariables.paymentDBStatus.add(GlobalVariables.queryResult
					.getString("payment_status_cd"));
			}
		}
	}

	// Verify payment status from availability in the string array
	public boolean verifyPaymentStatusPresence(String PaymentStatus) {
		boolean flag = false;
		for (String verify : GlobalVariables.variousPaymentStatus) {
			if (PaymentStatus.equalsIgnoreCase(verify)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	// Get wallet status from DB
	public void checkRecordStatusforWalletManagement(
			String walletReferenceValue) throws SQLException {
		commonClass
				.queryResultFromDB(GlobalVariables.walletCreationQuery
						+ walletReferenceValue + "'");
		while (GlobalVariables.queryResult.next()) {
			GlobalVariables.walletDBStatus = GlobalVariables.queryResult
					.getString("record_status_cd");
		}
	}

	// Verify wallet status
	public boolean verifyWalletStatusPresence(String walletStatus) {
		if (walletStatus.equalsIgnoreCase(GlobalVariables.walletRecordStatus)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	//verify autopay record status	
	public boolean verifyAutopayrecordPresence(String autopayStatus) {
		boolean flag = false;
		for(String s1 : GlobalVariables.autopayRecordStatus){
			if (autopayStatus.equalsIgnoreCase(s1)) {
				flag = true;
				break;
		}
		}
		return flag;
		
	}
	
	// Get wallet status from DB
	public void checkRecordStatusforAutopay(
			String billingAcntId) throws SQLException, InterruptedException {
		commonClass
				.queryResultFromDB(GlobalVariables.recordStatusQuey
						+ billingAcntId + "' order by created_dttm desc");
		while (GlobalVariables.queryResult.next()) {
			GlobalVariables.autopayDBStatus = GlobalVariables.queryResult
					.getString("record_status_cd");
			if(GlobalVariables.billAppId.contains("ENS")||GlobalVariables.billAppId.contains("LATIS")){
				if(GlobalVariables.autopayDBStatus.equalsIgnoreCase("Processed")){
				break;
				}
			}else if(GlobalVariables.billAppId.contains("CRIS")){
				if(GlobalVariables.autopayDBStatus.equalsIgnoreCase("Submitted")){
				break;
				}
			}
			
		}
		
	}
	
	
}
