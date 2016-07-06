package commonPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.testng.Assert;

import businessSpecific.GlobalVariables;

public class HTTPPostRequest {

	private static int colNumber;
	private static int rowNumber;
	
	CommonClass commonClass = new CommonClass();

	// Get XML from text file and set it in a String variable
	public void setXMLStringValue(String XMLFilePath)
			throws FileNotFoundException {

		GlobalVariables.XMLFile = new File(XMLFilePath);

		GlobalVariables.XMLFileReader = new Scanner(new FileReader(
				GlobalVariables.XMLFile));

		while (GlobalVariables.XMLFileReader.hasNext()) {
			GlobalVariables.XMLString = GlobalVariables.XMLString
					+ GlobalVariables.XMLFileReader.next() + "\n";
		}
		System.out.println("Sending "+GlobalVariables.Ex_serviceNameValue+" Request");
		
	}

	// created a trusted certificate for the HTTP POST connection
	public void createTrustedCertificate()
			throws KeyManagementException, NoSuchAlgorithmException {

		GlobalVariables.sslContext = SSLContext.getInstance("SSL");

		GlobalVariables.sslContext.init(null,
				new TrustManager[] { new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs,
							String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs,
							String authType) {
					}
				}
			}, new SecureRandom());

	}

	// set the http client with trusted certificate
	@SuppressWarnings("deprecation")
	public void setHttpClient() {
		GlobalVariables.httpXMLRunner = HttpClients
				.custom()
				.setSSLSocketFactory(
						new SSLSocketFactory(GlobalVariables.sslContext))
				.build();
	}

	// set the HTTP POST variable to run the request
	public void setHTTPPost() {
		GlobalVariables.httpXMLPoster = new HttpPost(
				GlobalVariables.Ex_endPointUrlValue);
		GlobalVariables.httpXMLPoster.setHeader("Content-type",
				"application/xml");
		GlobalVariables.httpXMLPoster.setHeader("Accept", "application/xml");
		GlobalVariables.postDataEntity = new ByteArrayEntity(
				GlobalVariables.XMLString.getBytes());
		GlobalVariables.postDataEntity.setChunked(true);
		GlobalVariables.httpXMLPoster.setEntity(GlobalVariables.postDataEntity);
	}

	// send an HTTP POST request
	public void sendHTTPPostRequest() throws ClientProtocolException,
			IOException {
		GlobalVariables.httpXMLResponseReciever = GlobalVariables.httpXMLRunner
				.execute(GlobalVariables.httpXMLPoster);
	}

	// convert the response into readable string format
	public void formatResponseToString() throws IOException {
		try {
			GlobalVariables.responseConverter = GlobalVariables.httpXMLResponseReciever
					.getEntity();
			GlobalVariables.responseResult = EntityUtils
					.toString(GlobalVariables.responseConverter);
			EntityUtils.consume(GlobalVariables.responseConverter);
		} finally {
			GlobalVariables.httpXMLResponseReciever.close();
		}
		commonClass.WTFXMLResponse(GlobalVariables.writerObject,
				GlobalVariables.WTFile, GlobalVariables.responseResult);
		System.out.println("Recieved "+GlobalVariables.Ex_serviceNameValue+" Response");
	}

	// jsoup verify if response is successful
	public String getTagValue(String tagName) {
		GlobalVariables.jsoupParserResponse = Jsoup.parse(
				GlobalVariables.responseResult, "", Parser.xmlParser());
		return GlobalVariables.jsoupParserResponse.select(tagName).text();
	}
	
	public String getTagValueFromRequestXML(String tagName) {
		GlobalVariables.jsoupParserResponse = Jsoup.parse(
				GlobalVariables.XMLString, "", Parser.xmlParser());
		return GlobalVariables.jsoupParserResponse.select(tagName).text();
	}


}
