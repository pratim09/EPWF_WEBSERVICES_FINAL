package commonPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.util.HSSFColor.GOLD;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import businessSpecific.GlobalVariables;

public class CommonClass {
	ExcelUtils excelUtils = new ExcelUtils();
	
	// Get the current system datetime
	public String getCurrentDateTime(String requiredDateFormat) {
		DateFormat dateFormat = new SimpleDateFormat(requiredDateFormat);
		Calendar cal = Calendar.getInstance();
		String CurrDateTime = dateFormat.format(cal.getTime());
		return CurrDateTime;
	}
		
	
	//JDBC Connectivity
	public void queryResultFromDB(String query) throws SQLException{
		GlobalVariables.DBConn =DriverManager.getConnection(GlobalVariables.Ex_dbConnectionStringValue, GlobalVariables.Ex_dbUserNameValue, GlobalVariables.Ex_dbPasswordValue);
		GlobalVariables.Prepstmt = GlobalVariables.DBConn.prepareStatement(query);
		GlobalVariables.queryResult = GlobalVariables.Prepstmt.executeQuery();
	}
	
	//Create a Random number with given length
	public String getRandomNumber(int length) {
		Random rnd = new Random();
	    long nextLong = Math.abs(rnd.nextLong());
	    return String.valueOf(nextLong).substring(0, length);
	}
	
	//Add List into String
	public String addListIntoString(ArrayList<String> list){
		String stringToBeReturned = "";
		for(String itr : list){
			stringToBeReturned += itr +"\n";
		}
		
		return stringToBeReturned;
		
	}

	// Write the XML response into the created text file
	public void WTFXMLResponse(BufferedWriter writerObject, File FTW,
			String XMLResponseToWrite) throws IOException {
		writerObject = new BufferedWriter(new FileWriter(FTW));
		writerObject.write(XMLResponseToWrite);
		writerObject.close();
	}
	
	//Modify XML Tag Values
	public void modifyXML(String XMLfilePath,Map<String,String> tagsToChange) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(XMLfilePath);
	    
		for(Map.Entry<String, String> mapEntry : tagsToChange.entrySet()){
			int noOfKeys = doc.getElementsByTagName(mapEntry.getKey()).getLength();
			for(int i=0;i<noOfKeys;i++){
			Node FirstName = doc.getElementsByTagName(mapEntry.getKey())
					.item(i);
			FirstName.setTextContent(mapEntry.getValue());
			}
		}
	    
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(XMLfilePath));
		transformer.transform(source, result);
	}
	
	//Modify XML Tag Values for multipayment
		public void modifyXMLForMultiPaymentBTN(String XMLfilePath,Map<String,ArrayList<String>> tagsToChange) throws ParserConfigurationException, SAXException, IOException, TransformerException{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(XMLfilePath);
		    
			for(Map.Entry<String, ArrayList<String>> mapEntry : tagsToChange.entrySet()){
				int noOfKeys = doc.getElementsByTagName(mapEntry.getKey()).getLength();
				for(int i=0;i<noOfKeys;i++){
					
				Node FirstName = doc.getElementsByTagName(mapEntry.getKey())
						.item(i);
				FirstName.setTextContent(mapEntry.getValue().get(i));
				}
			}
		    
		    TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(XMLfilePath));
			transformer.transform(source, result);
		}

	// Take the assert values from Excel and split them
	public void addAssertValueToArrayList(String sheetName, int rowNumber)
			throws Exception {
		int colNumber;
		String assertUnsplittedString;
		colNumber = excelUtils.getColumnNo(sheetName,
				GlobalVariables.tcAssertName);
		assertUnsplittedString = excelUtils.getCellData(rowNumber, colNumber);
		GlobalVariables.assertValues = new ArrayList<String>();
		GlobalVariables.assertValues = Arrays.asList(assertUnsplittedString
				.split(","));
	}
}
