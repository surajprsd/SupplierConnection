package com.supplierconnection.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class Webinars {
	
	
	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	
	
	@Test(dataProvider="getData")
	public void Webinars1(Hashtable<String, String> data){
		
		
		if(!TestUtil.isTestCaseExecutable("Webinars", xls))
			throw new SkipException("Flag set to no for testcase"+"Webinars");
		
		
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("Webinars",xls, data);
		
		
	}
	
	@DataProvider
	public  Object[][] getData(){
		
		return TestUtil.getData("Webinars", xls);
		
	}
}
