package com.supplierconnection.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

public class ResourceCenter {
	
	
	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	
	
	@Test(dataProvider="getData")
	public void ResourceCenter1(Hashtable<String, String> data){
		
		
		if(!TestUtil.isTestCaseExecutable("ResourceCenter", xls))
			throw new SkipException("Flag set to no for testcase"+"ResourceCenter");
		
		
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("ResourceCenter",xls, data);
		
		
	}
	
	@DataProvider
	public  Object[][] getData(){
		
		return TestUtil.getData("ResourceCenter", xls);
		
	}
}
