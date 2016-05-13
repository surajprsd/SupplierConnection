package com.supplierconnection.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class SC_memberpageValidations 
{

	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	
	@Test(dataProvider="getDataForSteps")
	public void step1(Hashtable<String, String> data) throws IOException{
		
		//check the runmode of testcase
		if(!TestUtil.isTestCaseExecutable("SC_memberpageValidations", xls))
			throw new SkipException("Flag set to no for testcase"+"SC_memberprofile");
		
		//check the runmode of testdata
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("SC_memberpageValidations", xls, data);
}
	
	@DataProvider
	public  Object[][] getDataForSteps(){
		
		return TestUtil.getDataForSteps("SC_memberpageValidations", xls);
		
	}

}
