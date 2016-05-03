package com.supplierconnection.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

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
				else if(keyword.equals("verifyPage"))
					result=verifyPage((object));
				else if (keyword.equals("Verifytextpresent"))
					result=Verifytextpresent(object,(table.get(data))); //raghus method
			
					
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
/////////////////////////////suraj/////////////////////////////////////////	
	
	//verify images
	public String verifyPage(String ExpectedImagePath)
	{
		log("image verification");

		String result="Untested";
		Boolean compare;
		try
		{
			
		Thread.sleep(3000);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);		



		String current = "C:/Users/IBM_ADMIN/Eclipse/workspace/Supplier Connection/Images/image.png";
		
		FileUtils.copyFile(scrFile, new File(current));

		
		
		Image image1 = Toolkit.getDefaultToolkit().getImage(ExpectedImagePath);
		Image image2 = Toolkit.getDefaultToolkit().getImage(current);

		Thread.sleep(3000);

			PixelGrabber grab1 =new PixelGrabber(image1, 0, 0, -1, -1, false);
			PixelGrabber grab2 =new PixelGrabber(image2, 0, 0, -1, -1, false);

			int[] data1 = null;

			if (grab1.grabPixels()) {
				int width = grab1.getWidth();
				int height = grab1.getHeight();
				data1 = new int[width * height];
				data1 = (int[]) grab1.getPixels();
			}

			int[] data2 = null;

			if (grab2.grabPixels()) {
				int width = grab2.getWidth();
				int height = grab2.getHeight();
				data2 = new int[width * height];
				data2 = (int[]) grab2.getPixels();
			}

			compare=java.util.Arrays.equals(data1, data2);
			//System.out.println("Pixels equal: " + java.util.Arrays.equals(data1, data2));
			
			if(compare==true)
			{
				result="Pass";
			}
			else
			{
				result="Fail";
			}	
		}
		catch(Exception e)
		{
	   e.printStackTrace();
	   log("image verify failed");
			
		}
		 
		  return result;
		 
	}
	
	public String Verifytextpresent(String xpath, String textmatch){  //raghus
		log("Checking for the text presnet  ");


			try
			{
				String Pagetext=driver.findElement(By.xpath(OR.getProperty(xpath))).getText().trim();
				if (Pagetext.equals(textmatch.trim()))
				{
					System.out.println("Text is presnet");
					System.out.println("page text ="+Pagetext);
					System.out.println("page text ="+textmatch);

				}
				
				else
				{
					System.out.println("Text is not present" + xpath);
					//return "Fail";
					return "Fail -unable to verify text -"+xpath;
				}
			}
			catch(Exception e)
			{
				System.out.println("Text is not presnet" + xpath);
			// return "Fail";
			 return "Fail -unable to verify text -"+xpath;
			}
			return "Pass";
		} 
	 
	 ////////////////////////////////suraj////////////////////////////////////////
	 
	 
	//For text fields
	public String input(String xpathtext,String inputText){
		log("Enter text"+inputText+"in"+xpathtext);
		try{
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
	  driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS );
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
}