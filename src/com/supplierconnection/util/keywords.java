package com.supplierconnection.util;

//import static executionEngine.DriverScript.OR;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

//import utility.Log;
//import executionEngine.DriverScript;

//import utility.Log;
//import executionEngine.DriverScript;

//import com.mysql.jdbc.log.Log;

public class keywords {

	
	//Webdriver,propertyfile intialization to null
	WebDriver driver = null;
	Properties OR =null;
	Properties ENV =null;
	WebDriverWait wait;
	static keywords k;
	
	WebDriver bak_Mozilla =null;
	WebDriver bak_Chrome= null;
	WebDriver bak_IE= null;
	
	//log4j.properties should be inside the src
		Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
	
	//Initializing properyfiles in a constructor
	 //Made private to avoid creation of object for every set of data 
	private keywords() {
		try {
		OR = new Properties();
		FileInputStream fs;
		fs = new FileInputStream(System.getProperty("user.dir")+"//src//com//supplierconnection//config//OR.properties");
		OR.load(fs);
		
		String fileName =OR.getProperty("Environment")+".properties";
		
		ENV = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//com//supplierconnection//config//"+fileName);
		ENV.load(fis);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static keywords getInstance(){
		if(k==null)
			try {
				k= new keywords();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		
		return k;
		
	}
	
	//fetching keywords from test steps sheet and executing the keywords
	public void executeKeywords(String testName, Xls_Reader  xls, Hashtable<String, String> table){
		//fetching teststeps rows for 
		int rows = xls.getRowCount("Test Steps"); 
		
		for(int rNum=2;rNum<=rows;rNum++){
			
			String tcid = xls.getCellData("Test Steps","TCID", rNum);
//			System.out.println(tcid);
//			System.out.println(testName);
			//checking for only particular testname
			if(tcid.equals(testName)){
				String keyword = xls.getCellData("Test Steps", "Keyword", rNum);
				String object = xls.getCellData("Test Steps", "Object", rNum);
				String data = xls.getCellData("Test Steps", "Data", rNum);
				
			    String result=" ";
				//System.out.println(tcid+"----"+keyword+"-----------"+object+"------"+data+"---"+result);
				 log(tcid+"----"+keyword+"-----------"+object+"------"+data+"---"+result);
				 
				//Executing the keywords
				
				if(keyword.equals("openBrowser"))
					result= openBrowser(table.get(data));
				else if(keyword.equals("navigate"))
					result= navigate(object);
				else if(keyword.equals("input"))
					result= input(object,(table.get(data)));
				else if(keyword.equals("click"))
					result= click(object);
				else if (keyword.equals("waitForElementPresence"))
					result= waitForElementPresence(object);
				else if(keyword.equals("verifyLogin"))
				    result= verifyLogin(table.get(data));
				else if(keyword.equals("closeBrowser"))
					result = closeBrowser();
				else if (keyword.equals("loginValidation"))
					result=loginValidation(table.get(data));
				else if(keyword.equals("waitfor"))
					result=waitfor();
				else if (keyword.equals("changeEmailVerify"))
					result=changeEmailVerify();
				else if (keyword.equals("SelectdropdownBytext"))
					result=SelectdropdownBytext(object,(table.get(data)));
				else if (keyword.equals("SelectdropdownByvalue"))
					result=SelectdropdownByvalue(object,(table.get(data)));
				else if(keyword.equals("Supplierprofilelogoupload"))
					result=Supplierprofilelogoupload();
				else if(keyword.equals("Supplierbrochureupload"))
					result=Supplierbrochureupload();
				else if(keyword.equals("zipuploadformat"))
					result=zipuploadformat();
					else if(keyword.equals("docuploadformat"))
						result=docuploadformat();
					else if(keyword.equals("loguploadformat"))
						result=loguploadformat();
				else if (keyword.equals("CleartextByxpath"))
					result=CleartextByxpath(object);
				else if (keyword.equals("Verifytextpresent"))
					result=Verifytextpresent(object,(table.get(data)));
				else if (keyword.equals("removeoptionstep1"))
					result=removeoptionstep1();
				else if (keyword.equals("step1initprocess"))
					result=step1initprocess();
				else if (keyword.equals("stepsleftnavigation"))
					result=stepsleftnavigation(object);
				else if(keyword.equals("clickbylinktext"))
					result= clickbylinktext(object);
				else if(keyword.equals("uploadmemberlogo"))
					result=uploadmemberlogo();
				else if(keyword.equals("memberprocurementlist"))
					result=memberprocurementlist();
				else if(keyword.equals("newtabswitch"))
					result= newtabswitch(object,(table.get(data)));
				else if(keyword.equals("PMSocialsharing"))
					result=PMSocialsharing();
				else if(keyword.equals("RegisterpageSocialsharing"))
					result=RegisterpageSocialsharing();
				else if(keyword.equals("iselementenabled"))
					result= iselementenabled(object);
				else if(keyword.equals("iselementpresent"))
					result= iselementpresent(object);
				else if(keyword.equals("selectmultidropdownoptions"))
					result= selectmultidropdownoptions();
				else if(keyword.equals("elementselected"))
					result= elementselected(object);
				
				else if(keyword.equals("elementnotpresent"))
					result= elementnotpresent(object);
				else if(keyword.equals("waitForSometime"))
					result=waitForSometime();
				
				else if (keyword.equals("Verifycontentpresent"))
					result=Verifycontentpresent(object,(table.get(data)));
					
				//assertions to show result as fail 
				
				String proceed = " ";
				if(!result.equals("Pass"))
				{
					
					//Taking screen shots
					try{
						//filename like testcasename_keyword_linenumber.jpg
						String fileName= tcid+"_"+keyword+"_"+rNum+".jpg";
							File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
							FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+fileName));
						}catch(Exception e){
						
						}
					
					proceed = xls.getCellData("Test Steps", "Proceed_On_Fail", rNum);
					if(proceed.equals("Y")){
						//fail and continue execution
						try{
							Assert.fail("result");
						}catch(Throwable t){
							System.out.println("*************Error*****************");
							//showing result as pass even failed so to avoid it using listeners
							t.printStackTrace();
							log(t.getStackTrace().toString());
							
						}
					}
					else{
						//Fail and stop
						Assert.fail("result");
					}
			}
			}
			}
			
		
	}
	
	//logging
	public void log(String msg){
		 APPLICATION_LOGS.debug(msg);
	}
	
	//Clicking on any link,button
	public String click(String xpathlink){
		
		log("Click on"+xpathlink);
		try{
		driver.findElement(By.xpath(OR.getProperty(xpathlink))).click();
		}catch(Exception e){
			return "Fail -Unable to click on -"+xpathlink;
		}
		return "Pass";
	}
	
	
	//For text fields
	public String input(String xpathtext,String inputText){
		log("Enter text"+inputText+"in"+xpathtext);
		try{
			
			driver.findElement(By.xpath(OR.getProperty(xpathtext))).clear();
		driver.findElement(By.xpath(OR.getProperty(xpathtext))).sendKeys(inputText);
		}catch(Exception e){
			return "Fail -unable to write to-"+xpathtext;
		}
		return "Pass";
	}
	
	//For opening URL/loading page
	public String openBrowser(String browser){
		log("Opening Browser"+browser);
		try{
		
		if( browser.equals(bak_Mozilla) && bak_Mozilla != null){
				driver=bak_Mozilla;
				
		}else if( browser.equals(bak_Chrome) && bak_Chrome != null){
				driver=bak_Chrome;
				
		}else if( browser.equals(bak_IE) && bak_IE != null){
				driver=bak_IE;
				
		}
		
		
		if (browser.equals("Mozilla")&& bak_Mozilla== null ){
			driver = new FirefoxDriver();
		 	bak_Mozilla = driver;
		 	
		}else if (browser.equals("Chrome") && bak_Chrome == null){
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//drivers//chromedriver.exe");
			driver = new ChromeDriver();
			bak_Chrome= driver;
			
		}else if(browser.equals("IE") && bak_IE == null){
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//drivers//IEDRiverServer.exe");
			driver = new InternetExplorerDriver();
			bak_IE=driver;
			
		}
		
		  driver.manage().window().maximize();
		 // driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		  
		  //initialize WebDriverWait
		  if(driver!=null){
			  wait= new WebDriverWait(driver, 4);
		  }
		
		}catch(Exception e){
			System.out.println(e);
			return "Fail- unable to open browser";
		}
		return "Pass";
		
		
	}
	
	//Closing all browsers and back up browsers
	public String closeBrowser(){
		log("Closing Browser");
		try{
		if(driver!=null)
		driver.quit();
		if(bak_Mozilla!= null)
			bak_Mozilla.quit();
		if(bak_Chrome!=null)
			bak_Chrome.quit();
		if(bak_IE!=null)
			bak_IE.quit();
		bak_IE=bak_Mozilla=bak_Chrome=null;
		}catch(Exception e){
			return "Fail- unable to close browser";
		}
		return "Pass";
		
		
	}
	
	
	public String isElementpresent(String xpathkey){
		log("Checking element present "+xpathkey);
		int count = driver.findElements(By.xpath(OR.getProperty(xpathkey))).size();
		
		if(count==0)
		return "Fail- element not found"+xpathkey;
		else 
		return "Pass";
		
	}
	
 public String waitfor(){
	 log("Calling implicit wait");
	 try{
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS );
	 }catch(Exception e){
		 return "Fail to wait for implicit time";
	 }
	return "Pass";
	 }
 


  // Opening URL
	public String navigate(String link) {
		
		System.out.println("link value="+link);
		try{
			String url = ENV.getProperty("testurl");
			log("Openined URL:"+url);
			driver.get(url);
		
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}catch(Exception e){
			//System.out.println("inside catch"+e.getMessage());
			return "Fail- unable to navigate"+"openUrl";
		}
		return "Pass";
		
	}


   //Compare Strings
	public String validateText(String xpath) {
		log("Verifiying string"+xpath);
		String expectedTitle =OR.getProperty(xpath);
		String actualTitle = driver.getTitle();
		if(expectedTitle.equals(actualTitle))
			return "Pass";
		else
			return "Fail";
		
	}
	
	public String waitForElementPresence(String objectKey){
		log("Wait till element visible"+objectKey);
		try{
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(OR.getProperty(objectKey)))));
		}catch(Exception e){
			return "Fail- Element not Visible-"+objectKey;
		}
		return "Pass";
	}
	
	public String takeScreenCapture(String objectKey){
		log("Taking screenshot"+objectKey);
		try{
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+objectKey));
			
		}catch(Exception e){
			return "Fail taking ss"+objectKey;
		}
		
		return null;
		
	}
	
	
	/***********************Application Dependent Functions ***********************/
	
	public String verifyLogin(String userRole){
		log("Verifying login role"+ userRole);
		try{
			/*k.navigate("testurl");
  	        k.input("username_xpath",ENV.getProperty("Username"));
  	        k.input("password_xpath",ENV.getProperty("Password"));
  	        k.click("login_submit_xpath");*/
			if(userRole.equals("Systemadmin")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("systemadminid"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("systemadminpwd"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();
			}
			else if(userRole.equals("Buyeradmin")){
					driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("buyeradminid"));
					driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("buyeradminpwd"));
					driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
				}
			else if(userRole.equals("Buyer")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("buyerid"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("buyerpwd"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
			}
			else if(userRole.equals("Supplieradmin")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("supplieradminid"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("supplieradminpwd"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
			}
			else if(userRole.equals("Supplieremployee")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("supplieremployeeid"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("supplieremployeepwd"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
			}
			else if(userRole.equals("Buyerpending")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("buyerpendingid"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("buyerpendingpwd"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
			}
			else if(userRole.equals("Roleregistered")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("roleregisteredid"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("roleregisteredpwd"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
			}
			
			else if(userRole.equals("Buyeradmin2")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("buyeradminid2"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("buyeradminpwd2"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
			}
			
			else if(userRole.equals("Supplieradmin2")){
				driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("supplieradminid2"));
				driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("supplieradminpwd2"));
				driver.findElement(By.xpath(OR.getProperty("login_submit_xpath"))).click();	
			}
			
			}catch(Exception e){
				log("Exception in verifyrole"+ e);
				return "Fail- not logged in";
		
		}
		log("Verified login role"+ userRole);
		return "Pass";
	}
	
	
	public String loginValidation(String flag){
		log("Validating login credentials for flag set" + flag);
		
		try{
		     System.out.println("inside loginvalidation");  
		     Thread.sleep(1000);
		    
		     
		    if((flag.equals("N") && driver.findElement(By.xpath(OR.getProperty("Login_invalidemail_xpath"))).isDisplayed())){
					System.out.println("insideLogin_invalidemail_xpath for Incorrect Email or Password ");
					Thread.sleep(1000);
					String validmsg = driver.findElement(By.xpath(OR.getProperty("Login_invalidemail_xpath"))).getText();
					
					if(validmsg.equals(OR.getProperty("Invalid_idpwd")))
					System.out.println(validmsg);
					Thread.sleep(1000);
					return "Pass";
			}	
		 
		     if(flag.equals("N") && (driver.findElement(By.xpath(OR.getProperty("Login_maxlogin_xpath"))).isDisplayed()))
				{
					System.out.println("inside: Login_maxlogin_xpath ");
					Thread.sleep(1000);
				String validmsg = driver.findElement(By.xpath(OR.getProperty("Login_maxlogin_xpath"))).getText();
				
				System.out.println("After validmsg of max login");
				if(validmsg.equals(OR.getProperty("Invalid_login"))|| validmsg.equals(OR.getProperty("Fail_attempt_4")) || validmsg.equals(OR.getProperty("Fail_attempt_5")))
					System.out.println(validmsg);
				Thread.sleep(1000);
				return "Pass";
				}
		
		     if(flag.equals("Y")){
					System.out.println("Valid input"+ driver.getCurrentUrl());
					return "Pass";
		     }
		}catch(Exception e){
			System.out.println(e);
			return "Fail to do login validation";
		}
		
		finally{
			driver.quit();
		}
		return "Pass";
		
	}
	

//incomplete to click on done as its not working
public String changeEmailVerify(){
	log("Verifying changeemail fields");
	
	try{
		if(driver.findElement(By.xpath(OR.getProperty("change_newemail_validation_xpath"))).isDisplayed()){
				String validmsg = driver.findElement(By.xpath(OR.getProperty("change_newemail_validation_xpath"))).getText();
				if(validmsg.equals(OR.getProperty("Newemail_validation"))){
					return "Pass";
				}
			}
	
			if(driver.findElement(By.xpath(OR.getProperty("confirm_newmail_validation_xpath"))).isDisplayed()){
					String validmsg = driver.findElement(By.xpath(OR.getProperty("confirm_newmail_validation_xpath"))).getText();
					if(validmsg.equals(OR.getProperty("Confirm_newmail_validation"))){
						return "Pass";
					}
			}
			
			if(!(driver.findElement(By.xpath(OR.getProperty("change_newemail_validation_xpath"))).isDisplayed()) && driver.findElement(By.xpath(OR.getProperty("confirm_newmail_validation_xpath"))).isDisplayed()){
			driver.findElement(By.xpath(OR.getProperty("changeemail_overlay_button_xpath"))).click();
			driver.findElement(By.xpath(OR.getProperty("changeemail_overlay_done_xpath"))).click();
			}
			
	}
	
	catch(Exception e){
		log("Unable to validate changeemail fields");
		return "Fail-Unable to validate changeemail fields ";
		}
	
	return "Pass";
	
}

//Select dropdown1 by value

public String selectdropdownByvalue(String xpathtext,String inputText)  {
log("select text"+inputText+"in"+xpathtext);
		try {
			Select dropdown = new Select(driver.findElement(By.xpath(xpathtext)));
			dropdown.selectByValue(inputText);
					} 
		catch (Exception e) { 

                      return "Fail -unable to select -"+xpathtext;
	

	}
return "Pass";
}


public String SelectdropdownBytext(String xpathtext,String inputText){
log("select text"+ inputText+"in"+ xpathtext);
       
{
	try {
		Thread.sleep(3000);
		//WebElement temp=driver.findElement(By.xpath(xpathtext));
		System.out.println("The xpath-"+OR.getProperty(xpathtext)+" and text is -"+inputText);
		Select dropdown = new Select(driver.findElement(By.xpath(OR.getProperty(xpathtext))));
		dropdown.selectByVisibleText(inputText);
		
	}

	catch (Exception e) {
		return "Fail -unable to select -"+xpathtext;
		}
		
	}
return "Pass";
}

public String SelectdropdownByvalue(String xpathtext,String inputText){
log("select text"+ inputText+"in"+ xpathtext);
       
{
	try {
		Thread.sleep(3000);
		//WebElement temp=driver.findElement(By.xpath(xpathtext));
		System.out.println("The xpath-"+OR.getProperty(xpathtext)+" and text is -"+inputText);
		Select dropdown = new Select(driver.findElement(By.xpath(OR.getProperty(xpathtext))));
		dropdown.selectByValue(inputText);
		
	}

	catch (Exception e) {
		return "Fail -unable to select -"+xpathtext;
		}
		
	}
return "Pass";
}


public String  CleartextByxpath(String xpathtext){
	log("Clear text"+ xpathtext);
	try {
		
			
			driver.findElement(By.xpath(OR.getProperty(xpathtext))).clear();
			
		} 

	 catch (Exception e) 
	{
		 return "Fail -unable to select -"+ xpathtext;
		 
	}
	return "Pass";	
	}

public String Steps_validatemsg(String TCID){
	log("Checking validation of step "+ TCID);
	String saveMsg;
	String currencyValidationmsg = null;
	String revenueValidationmsg = null;
	String electicallyvalidmsg;
	try{
				Thread.sleep(5000);
				 saveMsg= driver.findElement(By.xpath(OR.getProperty("step3_validationmsg_xpath"))).getText().trim();
				log(saveMsg);

				if(TCID.equals("SC_step3")){	

						if(saveMsg.equals(OR.getProperty("step3_saveasdraftvalidation_masgxpath").trim()))
								{
									if(driver.findElement(By.xpath("step3_lastrevenue_validation_xpath")).isDisplayed())
									revenueValidationmsg=driver.findElement(By.xpath("step3_lastrevenue_validation_xpath")).getText();

									if(driver.findElement(By.xpath("step3_currencycode_validation_xpath")).isDisplayed())
									currencyValidationmsg= driver.findElement(By.xpath("step3_currencycode_validation_xpath")).getText();

									if(revenueValidationmsg.equals(OR.getProperty("step3_lastrevenue_validationmsg")))
											log("Step3 validating  revenue");

									else if((revenueValidationmsg.equals(OR.getProperty("step3_lastrevenue_validationmsg"))) && ( currencyValidationmsg.equals(OR.getProperty("step3_currencycode_validationmsg"))))
											{
											log("Step3 validating both currency and revenue after clicking on save and conitnue");
											}

										log("Step3 not saved succefully and checked the validation message of last revenue");
									}else if(saveMsg.equals(OR.getProperty("step3_saveasdraft_msgxpath")))
								log("Step3 drafted or saved successfully");
							return "Pass";
						}else if(TCID.equals("SC_step4")){
							System.out.println(saveMsg);
							System.out.println("Inside step4 loop");
								String temp =OR.getProperty("step3_saveasdraftvalidation_masgxpath").trim();
								System.out.println(temp+" from propertyfile");
								if(saveMsg.equals(temp)){
									System.out.println("first if ");
								//if(driver.findElement(By.xpath("step4_electically_validationmsg_xpath")).isDisplayed()){
									System.out.println("second if");
								electicallyvalidmsg=driver.findElement(By.xpath("step4_electically_validationmsg_xpath")).getAttribute("aria-label").trim();
								System.out.println(electicallyvalidmsg);
								String temp1=OR.getProperty("step4_validationmsg").trim();
								if(electicallyvalidmsg.equals(temp1))
									System.out.println("If not, please indicate when you expect your company to have this capability --validation is checked");
							//}
								return "Pass";
								}else if(saveMsg.equals(OR.getProperty("step4_successful_msgxpath")))
									System.out.println("Step4 drafted or saved successfully");
								return "Pass";
						}

			}catch(Exception e){
			log("Unable to validate Steps"+e);
			return "Fail-Unable to validate Steps";
	}
	return "Pass";
}


public String Supplierprofilelogoupload(){
log("upload supplier company logo ");


	try
	{
		//Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Suppliercompanylogo.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//Suppliercompanylogo.exe");
		
		Thread.sleep(3000);
		
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload";
	}
	return "Pass";
}

public String Supplierbrochureupload(){
log("upload supplier company logo ");


	try
	{
		//Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//Supplierbrochureupload.exe");
		Thread.sleep(3000);
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload";
	}
	return "Pass";
}

public String zipuploadformat(){
log("upload supplier company logo ");


	try
	{
		//Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//zipformatupload.exe");
		Thread.sleep(3000);
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload";
	}
	return "Pass";
}

public String docuploadformat(){
log("upload supplier company logo ");


	try
	{
		//Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//docformatupload.exe");
		Thread.sleep(3000);
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload";
	}
	return "Pass";
}

public String loguploadformat(){
log("upload supplier company logo ");


	try
	{
		//Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//logformatupload.exe");
		Thread.sleep(3000);
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload";
	}
	return "Pass";
}



public String Verifytextpresent(String xpath, String textmatch){
log("Checking for the text present  " + textmatch+ "in object " + xpath);


	try
	{
		String Pagetext=driver.findElement(By.xpath(OR.getProperty(xpath))).getText().trim();
		if (Pagetext.equals(textmatch.trim()))
		{
			//System.out.println("Text is present");
			log("Checking for the text present  " + textmatch+ "in object " + xpath);
		}
		else
		{
			//System.out.println("Text is not present" + xpath);
			//log("Checking for the text is not presnet  " + textmatch+ "in object " + xpath);
			//return "Fail";
			return "Fail -unable to verify text -"+xpath;
		}
	}
	catch(Exception e)
	{
		//System.out.println("Text is not present" + xpath);
	// return "Fail";
	 return "Fail -unable to verify text -"+xpath;
	}
	return "Pass";
}

public String input3(String xpathtext,String inputText){
	log("Enter text"+inputText+"in"+xpathtext);
	try{
		if (driver.findElement(By.xpath(OR.getProperty(xpathtext))).isDisplayed())
		{
			
				driver.findElement(By.xpath(OR.getProperty(xpathtext))).click();
				
				//driver.findElement(By.xpath(OR.getProperty(xpathtext))).clear();
				driver.findElement(By.xpath(OR.getProperty(xpathtext))).sendKeys(inputText);
				return "Pass";
		}
		else if(driver.findElement(By.xpath("//span[contains(text(),'Add a additional line')]")).isDisplayed())
		{ 
			List<WebElement> additionalnames = driver.findElements(By.xpath(("//span[contains(text(),'Add a additional line')]")));
			int max= additionalnames.size();
			WebElement main1= driver.findElement(By.xpath("//div[@role='main']"));
			for(int i=0;i<=max;i++){
				main1.findElement(By.xpath("//span[contains(text(),'Add a additional line')]")).click();
				String street=  main1.findElement(By.xpath("//span[contains(text(),'Add a additional line')]")).getText();
				if("legalname2".equals(street)){
					driver.findElement(By.xpath(OR.getProperty(xpathtext))).click();
					driver.findElement(By.xpath(OR.getProperty(xpathtext))).sendKeys(inputText);
				}
			}
			
		}
		return "Pass";
	}catch(Exception e){
		return "Fail -unable to write to-"+xpathtext;
	}
}


public String removeoptionstep1()
{
	log("Remove option ");
	try{
		if (driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_lname']")).isDisplayed()) 
		{
			
				driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_lname']")).click();
				
		}
		else if(driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_street']")).isDisplayed())
		{ 
			driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_street']")).click();
		}
		else if (driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_street3']")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_street3']")).click();
		}
		
		else if (driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_pobox']")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_pobox']")).click();
		}
         
		else if (driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_twitter']")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_twitter']")).click();
		}
		else if (driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_facebook']")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_facebook']")).click();
		}
		
		else if (driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_linkedin']")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_linkedin']")).click();
		}
		else if (driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_youtube']")).isDisplayed())
		{
			driver.findElement(By.xpath("//span[contains(text(),'Remove') and @id='remove_youtube']")).click();
		}
		
		return "Pass";
		
	}catch(Exception e){
		return "Fail -unable to remove-";
	}
}

public String step1initprocess()
{
	log("unchecking options on step1 ");
	try{
		
		driver.findElement(By.xpath(OR.getProperty("step1_init1"))).click();
		driver.findElement(By.xpath(OR.getProperty("step1_init2"))).click();
        driver.findElement(By.xpath(OR.getProperty("step1_init3"))).click();
        //driver.findElement(By.xpath(OR.getProperty("step1_init4"))).click();
        //driver.findElement(By.xpath(OR.getProperty("step1_init5"))).click();
		return "Pass";
		
	}catch(Exception e){
		return "Fail -unable to uncheck in step1-";
	}
}

public String stepsleftnavigation(String xpath){
log("Checking for Steps left Navigation  ");


	try
	{
		if (driver.findElement(By.xpath(OR.getProperty(xpath))).isDisplayed());
		{
			System.out.println("Left Navigation panel is presnet" );
		}	
		
	}
	catch(Exception e)
	{
	 return "Left Navigation panel is not presnet ";
	}
	return "Pass";
}

public String clickbylinktext(String linktext){
	
	log("Click on"+linktext);
	try{
	driver.findElement(By.linkText(OR.getProperty(linktext))).click();
	}
	catch(Exception e)
	{
		return "Fail -Unable to click on -"+linktext;
	}
	return "Pass";
}

public String uploadmemberlogo(){
log("upload mmeber company logo ");


	try
	{
		//Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\uploadmemberprofile.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//uploadmemberprofile.exe");
		Thread.sleep(3000);
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload";
	}
	return "Pass";
}

public String memberprocurementlist(){
log("Checking for expected procurement list in member profile page");


	try
	{
		int count = 0;
	    String[] exp = {"Facilities Support","Food and Beverage Manufacturing","Industrial Manufacturing","Lab Supplies and Equipment","Logistics","Professional, Marketing and Technical Services","Service Parts","Technology","Others"};
	    WebElement dropdown = driver.findElement(By.id("procurements"));
	    Select select = new Select(dropdown);

	    List<WebElement> options = select.getOptions();
	    
	    for (WebElement we : options) 
	    {
	        for (int i = 0; i < exp.length; i++)
	        {
	            if (we.getText().equals(exp[i])) 
	            
	            {
	                count++;
	            }
	        }
	    }
	    if (count == exp.length)
	    {
	        System.out.println("matched");
	    } 
	    else 
	    {
	        System.out.println(" Procurement list have a problem.");
	    }
			
		
	}
	catch(Exception e)
	{
	 return "Not as per Expected list";
	}
	return "Pass";
}


public String newtabswitch(String xpathtext,String inputText)

{
	log("Switching window");
	try{
	
	String parentWindow = driver.getWindowHandle();
	
	Set<String> handles =  driver.getWindowHandles();
	
	   for(String windowHandle  : handles)
	       {
	       if(!windowHandle.equals(parentWindow))
	          {
	          driver.switchTo().window(windowHandle);
	          
	          if (inputText.equals(driver.findElement(By.xpath(OR.getProperty(xpathtext))).getText().trim()))
	      	{
	      		System.out.println("Next window/tab opened suceefully");
	      		
	      	}
	      	
	      	else
	      	{
	      		System.out.println("Problem while opening Next window/tab");
	      		return "Fail";
	      	}
	      	                 
	          
	        // <!--Perform your operation here for new window-->
	          
	         driver.close(); //closing child window
	         
	         driver.switchTo().window(parentWindow); //cntrl to parent window
	         
	          }
	       }
	}
	
	catch(Exception e)
	{
	 
		System.out.println("Problem while opening Next window/tab");
		return "Fail";
	}
	
	   return "Pass";
}



public String PMSocialsharing()

{
	
	log("Opening social sharing options in Member profile page");
		try{

               driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[1]/a")).click();
	           driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[2]/a")).click();
               driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[4]/a")).click();
               driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[5]/a")).click();
               driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[3]/a")).click();

                 String parentWindow = driver.getWindowHandle();
	
	        Set<String> handles =  driver.getWindowHandles();
	
	      for(String windowHandle  : handles)
	        {
	            if(!windowHandle.equals(parentWindow))
	                 {
	                       driver.switchTo().window(windowHandle);
	          
	        	             driver.close(); 
	         
	         driver.switchTo().window(parentWindow); //cntrl to parent window
	         
	          }
	       }
	


                    }

              catch(Exception e)

                    {
			return "Fail -Not able to Open social sharing options in Member profile page-";

		    }
		   return "Pass";


}


public String RegisterpageSocialsharing()

{
	
	log("Opening social sharing options in Register page");
		try{

               driver.findElement(By.xpath("html/body/div[2]/div[2]/ul/li[1]/a")).click();
	           driver.findElement(By.xpath("html/body/div[2]/div[2]/ul/li[2]/a")).click();
               driver.findElement(By.xpath("html/body/div[2]/div[2]/ul/li[3]/a")).click();
               driver.findElement(By.xpath("html/body/div[2]/div[2]/ul/li[4]/a")).click();
               driver.findElement(By.xpath("html/body/div[2]/div[2]/ul/li[5]/a")).click();

                 String parentWindow = driver.getWindowHandle();
	
	        Set<String> handles =  driver.getWindowHandles();
	
	      for(String windowHandle  : handles)
	        {
	            if(!windowHandle.equals(parentWindow))
	                 {
	                       driver.switchTo().window(windowHandle);
	          
	        	             driver.close(); 
	         
	         driver.switchTo().window(parentWindow); //cntrl to parent window
	         
	          }
	       }
	


                    }

              catch(Exception e)

                    {
			return "Fail -Not able to Open social sharing options in Register page-";

		    }
		   return "Pass";


}


public String iselementenabled(String xpathlink){
	
	log("Check for element enable"+xpathlink);
	try{
		if (!driver.findElement(By.xpath(OR.getProperty(xpathlink))).isEnabled())
		{
			System.out.println("Element " +xpathlink + " Is Disabled" );
			
		}
		else
		{
			System.out.println("Element " +xpathlink + " Is Enabled");
			return "Fail";
		}
	}catch(Exception e){
		return "Fail - Element is enabled -"+xpathlink;
	}
	return "Pass";
}

public String iselementpresent(String xpathlink){
	
	log("Check for element enable"+xpathlink);
	try{
		if (driver.findElement(By.xpath(OR.getProperty(xpathlink))).isDisplayed())
		{
			System.out.println("Element " +xpathlink + " Is Presnet" );
			
		}
		else
		{
			System.out.println("Element " +xpathlink + " Is not Presnet");
			return "Fail";
		}
	}catch(Exception e){
		return "Fail - Element is not Presnet  -"+xpathlink;
	}
	return "Pass";
}

public String elementselected(String xpathlink){
	
	log("Check for element enable"+xpathlink);
	try{
		if (driver.findElement(By.xpath(OR.getProperty(xpathlink))).isSelected())
		{
			System.out.println("Element " +xpathlink + " Is Selected " );
			
		}
		else
		{
			System.out.println("Element " +xpathlink + " is not Selected");
			return "Fail";
		}
	}catch(Exception e){
		return "Fail - Element is not Selected  -"+xpathlink;
	}
	return "Pass";
}


public String selectmultidropdownoptions(){
	
	log(" Select multiple values from select element ");
	try{
		for (int i=1;i<5;i++)
		{
						
			driver.findElement(By.xpath("//*[@id='subcategory']/option["+i+"]")).click();
							
		}
	}catch(Exception e){
		return "Fail - Element is not Presnet  -";
	}
	return "Pass";
}

public String waitForSometime()
{
	driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS );
	return "pass";
	}

public String elementnotpresent(String xpathlink)

{
	
	log("Check for element present " + xpathlink);
	
	try
	{

		if(driver.findElement(By.xpath(OR.getProperty(xpathlink))).isDisplayed())
			
		{
			
			log("Element present " + xpathlink);
			return "Fail";
			
		}
		else
		{
			throw new NoSuchElementException(" Element is not presnet");
			
		}
		
		
	}
	
	catch(Exception e)
	{
		
		log("Element is not present " + xpathlink);
		return "Pass";
	}
	
}


public String Verifycontentpresent(String xpath, String textmatch){
log("Checking for the Content present  " + textmatch+ "in object " + xpath);


	try
	{
		String Pagetext=driver.findElement(By.xpath(OR.getProperty(xpath))).getText().trim();
		if (Pagetext.contains(textmatch.trim()))
				{
			//System.out.println("Text is present");
			log("Checking for the content present  " + textmatch+ "in object " + xpath);
		}
		else
		{
			//System.out.println("Text is not present" + xpath);
			//log("Checking for the text is not presnet  " + textmatch+ "in object " + xpath);
			//return "Fail";
			return "Fail -unable to verify content -"+xpath;
		}
	}
	catch(Exception e)
	{
		//System.out.println("Text is not present" + xpath);
	// return "Fail";
	 return "Fail -unable to verify content -"+xpath;
	}
	return "Pass";
}


}



