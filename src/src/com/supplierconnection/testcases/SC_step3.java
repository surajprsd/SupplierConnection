package com.supplierconnection.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class SC_step3 {
	
	keywords k= keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	@Test(dataProvider="getData")
public void Step3(Hashtable<String, String> data) throws IOException{
		
		if(!TestUtil.isTestCaseExecutable("Step3", xls))
			throw new SkipException("Flag set to no for testcase---"+ "Step3");
		
		//check the runmode of testdata
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("Step3", xls, data);
		
		
	}







@DataProvider
public  Object[][] getData(){
	
	return TestUtil.getDataForSteps("Step3", xls);
	
}


		
	}





