package com.supplierconnection.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class Step2 {
	
	
	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	
	
	@Test(dataProvider="getData")
	public void Step21(Hashtable<String, String> data){
		
		
		if(!TestUtil.isTestCaseExecutable("Step2", xls))
			throw new SkipException("Flag set to no for testcase"+"Step2");
		
		
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("Step2",xls, data);
		
		
	}
	
	@DataProvider
	public  Object[][] getData(){
		
		return TestUtil.getData("Step2", xls);
		
	}
}