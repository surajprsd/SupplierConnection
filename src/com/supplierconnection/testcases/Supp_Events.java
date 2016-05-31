package com.supplierconnection.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class Supp_Events {
	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	@Test(dataProvider="getData")
	public void Supp_Events1(Hashtable<String, String> data) throws IOException{
		
		if(!TestUtil.isTestCaseExecutable("Supp_Events", xls))
			throw new SkipException("Flag set to no for testcase"+"Supp_Events");
		
		//check the runmode of testdata
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("Supp_Events", xls, data);
		
		
		
	}


@DataProvider
public  Object[][] getData(){
	
	return TestUtil.getData("Supp_Events", xls);
	
}


		
	}


