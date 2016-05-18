package com.supplierconnection.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class ChangePassword {
	
	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	//Object[][]  data = TestUtil.getData("ChangePassword", xls);
	
	@Test(dataProvider="getData")
	public void changePwd(Hashtable<String, String> data){
		
		//System.out.println(data);
		//check the runmode of testcase
		if(!TestUtil.isTestCaseExecutable("ChangePassword", xls))
			throw new SkipException("Flag set to no for testcase"+"ChangePassword");
		
		//check the runmode of testdata
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("ChangePassword",xls, data);
		
		
	}
	
	@DataProvider
	public  Object[][] getData(){
		
		return TestUtil.getData("ChangePassword", xls);
		
	}
}
