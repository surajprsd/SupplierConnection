package com.supplierconnection.testcases;

import java.io.IOException;
import java.util.Hashtable;


import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class LoginTest {
	
	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	@Test(dataProvider="getData")
	public void doLogin(Hashtable<String, String> data) throws IOException{
		//System.out.println(data);
		//check the runmode of testcase
		if(!TestUtil.isTestCaseExecutable("LoginTest", xls))
			throw new SkipException("Flag set to no for testcase"+"LoginTest");
		
		//check the runmode of testdata
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("LoginTest", xls, data);
		
		
	}







@DataProvider
public  Object[][] getData(){
	
	return TestUtil.getData("LoginTest", xls);
	
}

}