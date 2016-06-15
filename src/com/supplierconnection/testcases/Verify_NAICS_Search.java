/**
 * 
 */
package com.supplierconnection.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.supplierconnection.util.TestUtil;
import com.supplierconnection.util.Xls_Reader;
import com.supplierconnection.util.keywords;

/**
 * @author vani
 *
 */
public class Verify_NAICS_Search {
	keywords k = keywords.getInstance();
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//supplierconnection//xls//TestSuite1.xlsx");
	@Test(dataProvider="getData")
	public void NAICSsearch(Hashtable<String, String> data) throws IOException{
		System.out.println(data);
		//check the runmode of testcase
		if(!TestUtil.isTestCaseExecutable("Verify_NAICS_Search", xls))
			throw new SkipException("Flag set to no for testcase"+ "Verify_NAICS_Search");
		
		//check the runmode of testdata
		if(data.get("RunMode").equals("N"))
			throw new SkipException("Flag set to no");
		
		k.executeKeywords("Verify_NAICS_Search", xls, data);
		
		
	}







@DataProvider
public  Object[][] getData(){
	
	return TestUtil.getData("Verify_NAICS_Search", xls);
	
	
	
}



}



