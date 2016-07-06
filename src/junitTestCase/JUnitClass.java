package junitTestCase;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPException;
import javax.xml.transform.TransformerException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import commonPackage.CommonClass;
import commonPackage.HTTPPostRequest;
import businessSpecific.GlobalVariables;
import businessSpecific.TestCaseSwitchScenario;

public class JUnitClass {

	// Main Test Class
	@Test
	public void RunTest() throws Throwable {
		TestCaseSwitchScenario TSobject = new TestCaseSwitchScenario();
		TSobject.Execute();
	}

}