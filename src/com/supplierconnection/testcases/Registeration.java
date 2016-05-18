package com.supplierconnection.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class Registeration
{

	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	
	@Test(dataProvider="getData")
	public void register(Hashtable<String, String> data) throws IOException{
		
		//check the runmode of testcase
		if(!TestUtil.isTestCaseExecutable("SC_Register", xls))
			throw new SkipException("Flag set to no for testcase"+"SC_Register");
		
		//check the runmode of testdata
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("SC_Register", xls, data);
}
	
	
	@DataProvider
	public  Object[][] getData(){
		
		return TestUtil.getData("SC_Register", xls);
		
	}

}

	
