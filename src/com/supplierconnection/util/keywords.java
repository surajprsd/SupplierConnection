package com.supplierconnection.util;

import java.awt.Robot;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hslf.model.Sheet;
//import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.jetty.http.SSORealm;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import autoitx4java.AutoItX;

import com.google.common.io.Files;
import com.jacob.com.LibraryLoader;

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
	ArrayList<String> suppliernames =new ArrayList<String>();	
	ArrayList<String> DBSuppliers=new ArrayList<String>();
	
	//log4j.properties should be inside the src
		static Logger APPLICATION_LOGS = Logger.getLogger("devpinoyLogger");
	
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
				else if(keyword.equals("selectDropdownVisibleText"))
					result= selectDropdownVisibleText(object,table.get(data));
				else if(keyword.equals("uploadFile"))
					result=uploadFile(table.get(data), object);
				else if(keyword.equals("Steps_validatemsg"))
					result=Steps_validatemsg(tcid);
				else if(keyword.equals("smallBizcheckstatus"))
					result=smallBizcheckstatus(table.get(data));
				else if(keyword.equals("selectresultsPerpage_textview"))
						result=selectresultsPerpage_textview();
				else if(keyword.equals("bylinktext"))
						result=bylinktext(table.get(data));
				else if(keyword.equals("selectresultsPerpage_summarycards"))
					result=selectresultsPerpage_summarycards();
				else if(keyword.equals("displayResultsPerPage_textview"))
					result=displayResultsPerPage_textview();
				else if(keyword.equals("displayResultsPerPage_summaryview"))
					result=displayResultsPerPage_summaryview();
				else if(keyword.equals("pageNavigationByclick_textview"))
					result=pageNavigationByclick_textview();
				else if(keyword.equals("pageNavigationByclick_summaryview"))
					result=pageNavigationByclick_summaryview();
				else if(keyword.equals("profileBookmark"))
					result=profileBookmark(table.get(data));
				else if(keyword.equals("emailShortRecord"))
					result=emailShortRecord(table.get(data));
				else if(keyword.equals("keywordSearchByClick"))
					result=keywordSearchByClick(table.get(data));
				else if(keyword.equals("keywordSearchBykeyborad"))
					result=keywordSearchBykeyborad(table.get(data));
				else if(keyword.equals("facetSelection"))
					result=facetSelection();
				else if(keyword.equals("endecaRuntime"))
					result=endecaRuntime(table.get(data));
				else if(keyword.equals("smallBizRecordcontentVerify"))
					result=smallBizRecordcontentVerify(table.get(data));
				else if(keyword.equals("typeAheadsearch"))
					result=typeAheadsearch(table.get(data),table.get(data));
				else if(keyword.equals("sortBy"))
					result=sortBy(table.get(data));
				else if(keyword.equals("keywordSearchByClick"))
					result=keywordSearchByClick(table.get(data));
				else if(keyword.equals("pdfReading"))
					result=pdfReading(table.get(data));
				else if(keyword.equals("pdfReading_buyer"))
					result=pdfReading_buyer(table.get(data));
				else if(keyword.equals("zipuploadformat"))
					result=zipuploadformat();
				else if(keyword.equals("docuploadformat"))
						result=docuploadformat();
				else if(keyword.equals("loguploadformat"))
						result=loguploadformat(); 
				else if(keyword.equals("uploadmemberlogo"))
					result=uploadmemberlogo(); 
				else if(keyword.equals("largefileupload"))
					result=largefileupload();
				else if(keyword.equals("checkDiversitystatus"))
					result=checkDiversitystatus(object);
				else if(keyword.equals("step9Fileupload"))
					result=step9Fileupload(object);
				else if(keyword.equals("uncheckDiversitystatus"))
					result=uncheckDiversitystatus(object);
				else if(keyword.equals("isDisabled_button"))
					result=isDisabled_button(object);
				else if(keyword.equals("isDisabled_textbox"))
						result=isDisabled_textbox(object);
				else if(keyword.equals("isEnabled"))
					result=isEnabled(object);
				else if(keyword.equals("step8defaultcheck"))
					result=step8defaultcheck(object);
				else if(keyword.equals("isSelected"))
					result=isSelected(object);
				else if(keyword.equals("newtabswitch"))
					result= newtabswitch(object,table.get(data));
				else if(keyword.equals("Verifytextpresent"))
					result=Verifytextpresent(object,table.get(data));
				else if(keyword.equals("sortBy_findsupplier"))
					result=sortBy_findsupplier(table.get(data));
				else if(keyword.equals("mouseHoverandClick"))
					result=mouseHoverandClick(object);
				else if(keyword.equals("click_css"))
					result=click_css(object);
				else if(keyword.equals("step3helpfile"))
					result=step3helpfile();
				else if(keyword.equals("emailtheseSuppliers"))
					result=emailtheseSuppliers(table.get(data));
				else if (keyword.equals("SelectdropdownByvalue"))
					result=selectdropdownByvalue(object,(table.get(data)));
				else if(keyword.equals("Supplierprofilelogoupload"))
					result=Supplierprofilelogoupload();
				else if(keyword.equals("Supplierbrochureupload"))
					result=Supplierbrochureupload();
				else if (keyword.equals("CleartextByxpath"))
					result=CleartextByxpath(object);
				else if (keyword.equals("removeoptionstep1"))
					result=removeoptionstep1();
				else if(keyword.equals("clickbylinktext"))
					result= clickbylinktext(object);
				else if(keyword.equals("memberprocurementlist"))
					result=memberprocurementlist();
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
				else if (keyword.equals("Verifycontentpresent"))
					result=Verifycontentpresent(object,(table.get(data)));
				else if (keyword.equals("step1initprocess"))
					result=step1initprocess();
				else if(keyword.equals("verifybookmark_profile"))
					result=verifybookmark_profile(table.get(data));
				else if(keyword.equals("gotofindasupplierByrole"))
					result=gotofindasupplierByrole(table.get(data));
				else if(keyword.equals("dashboard_bookmarkcheck"))
					result=dashboard_bookmarkcheck(table.get(data));
				else if(keyword.equals("detailpage_bookmarkcheck"))
					result=detailpage_bookmarkcheck(table.get(data));
				else if(keyword.equals("facet_selection"))
					result=facet_selection();
				else if(keyword.equals("facet_deletiononebyone"))
					result=facet_deletiononebyone();
				else if(keyword.equals("NAICS_count_DBcheck"))
					result=NAICS_count_DBcheck(table.get(data));
				else if(keyword.equals("compare_NAICSresults"))
					result=compare_NAICSresults(DBSuppliers,suppliernames,table.get(data));
				else if(keyword.equals("downloadfile"))						
					result=downloadfile();
				
				 
					
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
	public static void log(String msg){
		 APPLICATION_LOGS.debug(msg);
	}
	
	//Clicking on any link,button
	public String click(String xpathlink){
		
		log("Click on" +xpathlink);
		try{
		driver.findElement(By.xpath(OR.getProperty(xpathlink))).click();
		}catch(Exception e){
			return "Fail -Unable to click on -"+xpathlink;
		}
		return "Pass";
	}
	
public String click_css(String csslink){
		
		log("Click on"  + csslink);
		try{
		driver.findElement(By.cssSelector(OR.getProperty(csslink))).click();
		}catch(Exception e){
			log("Fail -Unable to click on css path:" + e);
			return "Fail -Unable to click on -"+csslink;
			
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
			/*DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			capabilities.setCapability("chrome.binary", "//drivers//chromedriver.exe");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);*/
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
	
	
	public boolean isElementpresent(String xpathkey){
		log("Checking element present "+xpathkey);
		int count=driver.findElements(By.xpath(xpathkey)).size();
		
		if(count==0){
			log("Fail- element not found"+xpathkey);
		return false;
		}
		else {
			log("Element present"+xpathkey);
		return true;
		}
		
	}
	
	public String isEnabled(String xpathkey){
		log("Checking element enabled" +xpathkey);
		try{
				 boolean check= driver.findElement(By.xpath(OR.getProperty(xpathkey))).isEnabled();
				 if(check==true){
				 //return "Pass";
				 }
		}catch(Exception e){
			log("Unable to check element enablement" + xpathkey);
			return "Fail--Unable to check element enablement"+ "Exception:" + e;
		}
		return "Pass";
	}
	
	public String isSelected(String xpathkey){
		log("Checking element selected" +xpathkey);
		try{
				 boolean check= driver.findElement(By.xpath(OR.getProperty(xpathkey))).isSelected();
				 if(check==true){
				// return "Pass";
				 }
		}catch(Exception e){
			log("Unable to check element selected" + xpathkey);
			return "Fail--Unable to check element selected"+ "Exception:" + e;
		}
		return "Pass";
	} 
	
	public String isDisabled_textbox(String xpathkey){
		log("Checking textbox diabled" +xpathkey);
		try{
				 boolean check= driver.findElement(By.xpath(OR.getProperty(xpathkey))).isEnabled();
				 if(check==false){
				 //return "Pass";
				 }
		}catch(Exception e){
			log("Unable to check textbox diabled" + xpathkey);
			return "Fail--Unable to check textbox diabled"+ "Exception:" + e;
		}
		return "Pass";
	}
	
	public String isDisabled_button(String xpathkey){
		log("Checking radio/checkbox disabled" +xpathkey);
		try{
				 boolean check= driver.findElement(By.xpath(OR.getProperty(xpathkey))).isSelected();
				 if(check==false){
				 //return "Pass";
				 }
		}catch(Exception e){
			log("Unable to check radio/checkbox disabled" + xpathkey + "Exception:" + e);
			return "Fail--Unable to checkradio/checkbox disabled";
		}
		return "Pass";
	}
	
	
 public String waitfor(){
	 log("Calling implicit wait");
	 try{
	  driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS );
	 }catch(Exception e){
		 return "Fail to wait for implicit time";
	 }
	return "Pass";
	 }
 


  // Opening URL
	public String navigate(String link) {
		
		log("link value="+link);
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


   //Compare titles
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
		
		return "Pass";
		
	}
	
	public String uploadFile(String Browser, String objectKey){
		log("Uploading a file"+OR.getProperty("filePath"));
		try{
			
		if(Browser.equals("Mozilla")){
				
				driver.findElement(By.xpath(OR.getProperty(objectKey))).click();
				Process process = new ProcessBuilder(System.getProperty("user.dir")+"\\src\\com\\supplierconnection\\testcases\\fileup.exe",System.getProperty("user.dir")+"\\src\\AutoIT\\"+OR.getProperty("filePath")+"", "Open").start();
				Thread.sleep(5000);
				
	  }else if(Browser.equals("Chrome")){
		  		driver.findElement(By.xpath(OR.getProperty(objectKey))).click();
		  		Thread.sleep(8000);
				Process process = new ProcessBuilder(System.getProperty("user.dir")+"\\src\\com\\supplierconnection\\testcases\\chromeupload.exe",OR.getProperty("filePath"), "Open").start();
		  		//Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\com\\supplierconnection\\testcases\\chromeupload.exe");
				Thread.sleep(8000);
				
				}
		}catch(Exception e){
			return "Fail uplaoding file"+OR.getProperty("filePath");
		}
		return "Pass";
		
	}
	
	//For selection of drop down visible element
		public String selectDropdownVisibleText(String xpathlink, String inputText){
			log("Selecting dropdown visible text:"+ xpathlink);
			try{
				Thread.sleep(3000);
				WebElement temp = driver.findElement(By.xpath(OR.getProperty(xpathlink)));
				
				Select dropdowntemp = new Select(temp);
				
				if((inputText.equals(""))||(inputText.equals(null))){
					log("Checking for null revenue dropdown");
					
					dropdowntemp.selectByIndex(0);
					}
				
				dropdowntemp.selectByVisibleText(inputText);
			}catch(Exception e){
				return "Fail -Unable to select dropdown visible text -"+xpathlink;
			}
			return "Pass";
		}
		
		
		
		public String bylinktext(String name){
			log("Clicking  visible link text:"+ name);
			try{
				driver.findElement(By.linkText(name.trim())).click();
			}catch(Exception e){
				return "Fail- Element not Visible-"+name;
			}
			return "Pass";
		}
		
		public String newtabswitch(String xpathtext,String inputText)

		{
			log("Switching window for"+ inputText);
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
			      		log("Next window/tab opened suceefully"+ xpathtext);

			      	}

			      	else
			      	{
			      		log("Problem while opening Next window/tab"+ xpathtext);
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

				System.out.println("Problem while opening Next window/tab" +" Exception: "+ e);
				return "Fail--unable to do window seitch for xpath:"+xpathtext;
			}

			   return "Pass";
		}
		 
		
  public String mouseHoverandClick(String elementToHover){
	  try{
			Actions action = new Actions(driver);
			WebElement moveto = driver.findElement(By.xpath(OR.getProperty(elementToHover)));
			action.moveToElement(moveto).click(moveto);
			action.perform();
	  }catch(Exception e){
		  log("Unable to do mouse hover and click for an element: " + e);
		  return "Fail-Unable to do mouse hover and click for an element";
	  }
	return "Pass";
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
						
				else if(userRole.equals("Supplieradmin3")){		
					driver.findElement(By.xpath(OR.getProperty("username_xpath"))).sendKeys(OR.getProperty("supplieradminid3"));		
					driver.findElement(By.xpath(OR.getProperty("password_xpath"))).sendKeys(OR.getProperty("supplieradminpwd3"));

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
		     log("inside loginvalidation");  
		     Thread.sleep(1000);
		    
		     
		    if((flag.equals("N") && driver.findElement(By.xpath(OR.getProperty("Login_invalidemail_xpath"))).isDisplayed())){
					log("insideLogin_invalidemail_xpath for Incorrect Email or Password ");
					Thread.sleep(1000);
					String validmsg = driver.findElement(By.xpath(OR.getProperty("Login_invalidemail_xpath"))).getText();
					
					if(validmsg.equals(OR.getProperty("Invalid_idpwd")))
					log(validmsg);
					Thread.sleep(1000);
					return "Pass";
			}	
		 
		     if(flag.equals("N") && (driver.findElement(By.xpath(OR.getProperty("Login_maxlogin_xpath"))).isDisplayed()))
				{
					log("inside: Login_maxlogin_xpath ");
					Thread.sleep(1000);
				String validmsg = driver.findElement(By.xpath(OR.getProperty("Login_maxlogin_xpath"))).getText();
				
				log("After validmsg of max login");
				if(validmsg.equals(OR.getProperty("Invalid_login"))|| validmsg.equals(OR.getProperty("Fail_attempt_4")) || validmsg.equals(OR.getProperty("Fail_attempt_5")))
					log(validmsg);
				Thread.sleep(1000);
				return "Pass";
				}
		
		     if(flag.equals("Y")){
					log("Valid input"+ driver.getCurrentUrl());
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





public String Steps_validatemsg(String TCID){
	log("Checking validation of step "+ TCID);
	String saveMsg;
	String currencyValidationmsg = null;
	String revenueValidationmsg = null;
	
	
	try{
				Thread.sleep(5000);
				 saveMsg= driver.findElement(By.xpath(OR.getProperty("step3_validationmsg_xpath"))).getText().trim();
				log("step3_validationmsg_xpath saveMsg: "+ saveMsg);
				
				if(TCID.equals("SC_step3")){	
						
						if(saveMsg.equals(OR.getProperty("step3_saveasdraftvalidation_masgxpath").trim())){
								
									if(isElementpresent("//span[@class='errorMessage']")){
										if(isElementpresent("//div[@id='error_currencyCode']/ul/li/span")){
										List<WebElement> errormessages = driver.findElements(By.xpath("//div[@id='error_currencyCode']/ul/li/span"));
										int max= errormessages.size();
										if(max>1){
										for(int i=0;i<max;i++){
											currencyValidationmsg=errormessages.get(0).getText().trim();
											revenueValidationmsg=errormessages.get(1).getText().trim();
											
											if((revenueValidationmsg.equals(OR.getProperty("step3_lastrevenue_validationmsg"))) && ( currencyValidationmsg.equals(OR.getProperty("step3_currencycode_validationmsg"))))
											{
											log("Step3 validating both currency and revenue after clicking on save and conitnue");
											}
										}
										}
										else{
											revenueValidationmsg=errormessages.get(0).getText().trim();
											if(revenueValidationmsg.equals(OR.getProperty("step3_lastrevenue_validationmsg")))
												log("Step3 validating  revenue");
										}
										}
										
								if(isElementpresent("step3_fileerror_xpath")){
									 String tempmessage = driver.findElement(By.xpath("//div[@id='error_balanceSheetFile']/ul/li/span")).getText().trim();
									 if(tempmessage.equals(OR.getProperty("step3_largefileuplaod_error"))){
										 log("Uploaded file was too large.");
									 }
									 if(tempmessage.equals(OR.getProperty("step3_filetypeerror"))){
										 log("File extension is not allowed.");
									 }
									 
								}
								
							}
						}
						}
	
			}catch(Exception e){
			log("Unable to validate Steps"+e);
			return "Fail-Unable to validate Steps";
	}
	return "Pass";
}

//Added by vani 
public String smallBizcheckstatus(String subnavname){
	try{
		log("Executing smallBizcheckstatus for "+ subnavname);
		Thread.sleep(4000);
		String percentage= driver.findElement(By.xpath("//div[@class='graph-label']/span")).getText();
		
		driver.findElement(By.xpath("//a[contains(@href,'"+subnavname+"')]")).click();
		System.out.println(percentage.substring(0, 2));
		 String title;
		Thread.sleep(7000);
		//checking for small biz 
		boolean value =percentage.substring(0, 3).trim().equals("100");
		log("Checked value");
		
		if(value==true){
			title= driver.getTitle().toString();
			log(title + "Proifle is 100% completed");
			driver.findElement(By.xpath("//a[contains(@href,'/SupplierConnection/sec/reg_home.action')]")).click();
			return "Pass";
		}else if(value==false) {
					title= driver.getTitle();
					String msg= driver.findElement(By.xpath("//*[@id='coltable']/div/h2")).getText();
					if(msg.trim().contains("This content is available to suppliers who have completed 100% of their company profile."))
					{
						
					
					String percentage1= driver.findElement(By.xpath("//*[@id='coltable']/div/span[2]/span[2]/span[2]")).getText();
					log(percentage1.substring(0, 3).trim());
					if(percentage.equals(percentage1))
					{
						log("Proifle incomplete:"+ title + percentage1);
					}
					 driver.findElement(By.xpath("//*[@id='coltable']/div/span[3]/a")).getAttribute("href");
				
					 log( "In complete profile URL:" +title +driver.findElement(By.xpath("//*[@id='coltable']/div/span[3]/a")).getAttribute("href"));
					 }
					driver.findElement(By.xpath("//a[contains(@href,'/SupplierConnection/sec/reg_home.action')]")).click();
					return "Pass";
					
		 		}
	
}catch(Exception e){
	log("Unable to test small biz profiles"+ e);
	return "Fail-Unable to test small biz profiles";
}
	return "Pass";
}




public String selectresultsPerpage_summarycards() {
	try{
		log("Executing selectresultsPerpage_summarycards");
		//Checking the results per page
		String Resultsperpage  = driver.findElement(By.cssSelector(".col.showingMetaDiv")).getText();
		String result = Resultsperpage.substring(Resultsperpage.indexOf("f") + 1, Resultsperpage.indexOf("r"));
		 int x= Integer.parseInt(result.trim()) ;
		if(x>1){
				String[] pageresults = {"18", "27","36","45","9"};
				int size= pageresults.length;
				for(int i=0;i<size;i++){
				//Getting Results per page
			    String resultsperpage=pageresults[i];
			    log("Checking pagination for option--------"+pageresults[i]);
			  
			    driver.findElement(By.xpath("//select[@id='results_select']")).click();
			    WebElement resultsByPage= driver.findElement(By.xpath("//select[@id='results_select']"));
			    Select results1 = new Select(resultsByPage);
			    results1.selectByVisibleText(resultsperpage);
			    Thread.sleep(2000);
			    Verifytextpresent("//div[@id='LeftSideBar']/div[1]/h2", "Narrow Suppliers by:");
				}
		}
	}catch(Exception e){
		log("Unable to selectresultsPerpage on shortrecord"+ e);
		return "Fail-Unable to check selectresultsPerpage_summarycards";
	}
	return "Pass";
	}

public String selectresultsPerpage_textview() {
	try{
		log("Executing selectresultsPerpage_textview");
		String Resultsperpage  = driver.findElement(By.xpath("//div[@class='col showingMetaDiv']")).getText().trim();
		String result = Resultsperpage.substring(Resultsperpage.indexOf("f") + 1, Resultsperpage.indexOf("r"));
		 int x= Integer.parseInt(result.trim()) ;
		 
		if(x>1){
				String[] pageresults = {"20", "30","40","50","10"};
				int size= pageresults.length;
				for(int i=0;i<size;i++){
				//Getting Results per page
			    String resultsperpage=pageresults[i];
			    log("Checking pagination for option--------"+pageresults[i]);
			  
			    WebElement resultsByPage= driver.findElement(By.xpath("//select[@id='results_select']"));
			    Select results1 = new Select(resultsByPage);
			    results1.selectByVisibleText(resultsperpage);
			    Thread.sleep(2000);
			    Verifytextpresent("Leftsidenav_narrow_xapth", "Narrow Suppliers by:");
				}
		}
	}catch(Exception e){
		log("Unable to selectresultsPerpagein textview"+ e);
		return "Fail-Unable to selectresultsPerpagein textview";
	}
	return "Pass";
} 


public String displayResultsPerPage_textview(){
	
	try{
		log("Executing displayResultsPerPage");
	//Getting the suppleir records per page
		List<WebElement> results = driver.findElements(By.xpath("//div[@class='sup-container']"));
		String Resultsperpage  = driver.findElement(By.xpath("//div[@class='col showingMetaDiv']")).getText().trim();
		String numberofrecords = Resultsperpage.substring(Resultsperpage.indexOf('-')+1);
		String[] sub= numberofrecords.split(" ");
		System.out.println(sub[0]);
		String y= sub[0];
		int x= Integer.parseInt(y) ;
					
	
    int max= results.size();
    if(x==max){
				for(int i=1;i<=max;i++){
						;
						String xpa1="//*[@id='v_results']/div[";
						String xpa2="]/div[1]/div[1]/div/h2/span";
						
						//Printing all supplier profile names per page
						WebElement main =driver.findElement(By.xpath("//div[@id='v_results']/div"));  
						String temp = null;
						if(isElementpresent(xpa1+i+xpa2)){
							temp = main.findElement(By.xpath(xpa1+i+xpa2)).getText().trim();
							System.out.println(temp);
							suppliernames.add(temp);
							log("Supplier name: " + temp);
						
				}
				else{
					log("Element not present as not in textview "+ main.findElement(By.xpath((xpa1+i+xpa2))));
				}
			}
    }
	}catch(Exception e){
		log("Unable to check displayResultsPerPage" + e);
		return " Fail- Unable to check displayResultsPerPage";
	}
	return "Pass";
}

public String displayResultsPerPage_summaryview(){
	try{
		
				log("Executing displayResultsPerPage in displayResultsPerPage_summaryview");
				WebElement main = driver.findElement(By.xpath("//div[@id='v_results']"));
			
				//Getting the suppleir records per page
				List<WebElement> results = main.findElements(By.xpath("//div[@class='H6_new1 row']"));
			    int max= results.size();
			    System.out.println(max);
			    for(int i=0;i<max;i++){
			    //	List<WebElement> list2= results.get(i).findElements(By.cssSelector("div[class='row MT20px'] *:last-child"));
			    	//String temp = list2.get(1).getText().trim();
			    	String temp = results.get(i).findElement(By.cssSelector("div[class='row MT20px'] a")).getText().trim();
			    	suppliernames.add(temp);
			    	log("Supplier name: " + temp);
			    	System.out.println(suppliernames.get(i));
				
				}
				
	}catch(Exception e){
		log("Unable to do displayResultsPerPage_buyer"+ e);
		return "Fail-Unable to do displayResultsPerPage_buyer";
	}
	return "Pass";
}


public String pageNavigationByclick_summaryview(){
	try{
		log("Executing pageNavigationByclick");
	//Checking total result count 
	String Resultsperpage= driver.findElement(By.xpath("//div[@id='v_results']/div[2]/div[1]")).getText().trim();
	log("Resultsperpage:---"+  Resultsperpage);
	
	
	//knowing current page
	String currentpage = driver.findElement(By.xpath("//input[@id='pageNumber']")).getAttribute("value");
	System.out.println("currentpage no:"+ currentpage);
	int min=  Integer.parseInt(currentpage);
	log("currentpage no:"+ min);
	
	//Knowing last pagenumber
	String lastpage = driver.findElement(By.xpath("//span[@class='recordMetaDiv']")).getText();
	int max1= Integer.parseInt(lastpage);
	log("Last page no:"+ max1);
	//Getting the suppleir records per page
	List<WebElement> results = driver.findElements(By.xpath("//div[@class='sup-container']"));
       
	for(int j=min;j<=max1;j++){
		int max= results.size();
		//Clicking on pagination
		String displayedpagination = driver.getPageSource();
		String previouspagination= "//div[@class='pagePrev active col']";
		String nextpagination ="//div[@class='pageNext active col']";
		String firstpagination= "//div[@class='pagePrev inactive col']";
		String lastpagination="//div[@class='pageNext inactive col']";
		
			//if(displayedpagination.contains(firstpagination) && displayedpagination.contains(lastpagination) ){
		if(isElementpresent(firstpagination) && isElementpresent(nextpagination)){
				log("first button inactive, click on next");
				displayResultsPerPage_summaryview();//Getting first page result 
				driver.findElement(By.xpath("//*[@id='formPage']/div/div[4]/a/img")).click();
				waitfor();
				//return "Pass";
				
			}
			else if(isElementpresent(nextpagination) && isElementpresent(previouspagination)) {
				log("both the buttons are active so clicking next");
				displayResultsPerPage_summaryview();// Getting second page result
				driver.findElement(By.xpath("//*[@id='formPage']/div/div[4]/a/img")).click();
				waitfor();
				//displayResultsPerPage_textview();
				//return "Pass";
			}
			else if(isElementpresent(lastpagination) && isElementpresent(previouspagination)){
				log("In the last page so there is no next button active");
				displayResultsPerPage_summaryview();//Getting last page result
			//	driver.findElement(By.xpath("///*[@id='formPage']/div/div[1]/a/img")).click();
				//System.out.println("end of pagination");
				return "Pass";
			}
			else if(isElementpresent(firstpagination) && isElementpresent(lastpagination)){
				log("In the last page so there is no next button active");
				displayResultsPerPage_summaryview();//Getting last page result
			//	driver.findElement(By.xpath("///*[@id='formPage']/div/div[1]/a/img")).click();
				//System.out.println("end of pagination");
				return "Pass";
			}
	}
	}catch(Exception e){
		log("Unable to check pageNavigationByclick" + e);
		return "Fail- Unable to check pageNavigationByclick";
	}
return "Pass";
}


public String pageNavigationByclick_textview(){
	try{
		log("Executing pageNavigationByclick");
	//Checking total result count 
	String Resultsperpage= driver.findElement(By.xpath("//div[@id='ContentArea']/div[1]/div[3]")).getText();
	System.out.println(Resultsperpage);
	
	//knowing current page
	String currentpage = driver.findElement(By.xpath("//input[@id='pageNumber']")).getAttribute("value");
	System.out.println("currentpage no:"+ currentpage);
	int min=  Integer.parseInt(currentpage);
	System.out.println("currentpage no:"+ min);
	
	//Knowing last pagenumber
	String lastpage = driver.findElement(By.xpath("//span[@class='recordMetaDiv']")).getText();
	int max1= Integer.parseInt(lastpage);
	System.out.println("Last page no:"+ max1);
	//Getting the suppleir records per page
	List<WebElement> results = driver.findElements(By.xpath("//div[@class='sup-container']"));
       
	for(int j=min;j<=max1;j++){
		int max= results.size();
		//Clicking on pagination
		String displayedpagination = driver.getPageSource();
		String previouspagination= "../images/pagePrev_active.png";
		String nextpagination ="../images/pageNext_active.png";
		String firstpagination= "../images/pagePrev_inactive.png";
		String lastpagination="../images/pageNext_inactive.png";
		
			if(displayedpagination.contains(firstpagination) && displayedpagination.contains(nextpagination) ){
				log("first button inactive, click on next");
				displayResultsPerPage_textview();//Getting first page result 
				driver.findElement(By.xpath("//*[@id='paginationDiv']/div[4]/a/img")).click();
				waitfor();
				//return "Pass";
				
			}
			else if(displayedpagination.contains(nextpagination) && displayedpagination.contains(previouspagination)) {
				log("both the buttons are active so clicking next");
				displayResultsPerPage_textview();// Getting second page result
				driver.findElement(By.xpath("//*[@id='paginationDiv']/div[4]/a/img")).click();
				waitfor();
				//displayResultsPerPage_textview();
				//return "Pass";
			}
			else if(displayedpagination.contains(lastpagination) && displayedpagination.contains(previouspagination)){
				log("In the last page so there is no next button active");
				displayResultsPerPage_textview();//Getting last page result
			//	driver.findElement(By.xpath("//*[@id='paginationDiv']/div[1]/a/img")).click();
				//System.out.println("end of pagination");
				return "Pass";
			}
			else if(displayedpagination.contains(firstpagination) && displayedpagination.contains(lastpagination)){
				log("In the last page so there is no next button active");
				displayResultsPerPage_textview();//Getting last page result
			//	driver.findElement(By.xpath("//*[@id='paginationDiv']/div[1]/a/img")).click();
				//System.out.println("end of pagination");
				return "Pass";
			}
			}
	}catch(Exception e){
		log("Unable to check pageNavigationByclick" + e);
		return "Fail- Unable to check pageNavigationByclick";
	}
return "Pass";
}


public String profileBookmark(String profileName) {
	try{
		log("Executing profileBookmark");
		keywordSearchByClick(profileName);
	//String profileName= "Endica Job_For Refresh Test";
		
		if(isElementpresent("//div[@class='supplier_supplierportal']")){
			List<WebElement> results = driver.findElements(By.xpath("//div[@class='sup-container']"));
		    int max= results.size();
			
			for(int i=1;i<=max;i++){
		
				String xpa1="//*[@id='v_results']/div[";
				String xpa2="]/div[1]/div[1]/div/h2/span";
				WebElement main =driver.findElement(By.xpath("//div[@id='v_results']/div"));  
				 
				String bookmarkmsg = null;
				String profilenamecap=null;
				if(main.findElement(By.xpath(xpa1+i+xpa2)).isDisplayed()){
					profilenamecap = main.findElement(By.xpath(xpa1+i+xpa2)).getText();
						System.out.println(profilenamecap);
						if(profileName.equals(profilenamecap.trim())){
							if(main.findElement(By.xpath("//div[@class='star']")).isDisplayed()){
								String id1="//img[@id='savedSupplier";
								String id2="']";
								bookmarkmsg = main.findElement(By.xpath(id1+i+id2)).getAttribute("alt");
								System.out.println(bookmarkmsg);
								if(bookmarkmsg.equals("Add bookmark")){
									log("Bookmarking:"+ profileName);
									driver.findElement(By.xpath("//div[@class='star']")).click();
									return "Pass";
								}
								else if(bookmarkmsg.equals("Remove bookmark")){
									log("UnBookmarking:"+ profileName);
									driver.findElement(By.xpath("//div[@class='star']")).click();
									return "Pass";
								}	
								}
							}
						}
				}
		}else if(isElementpresent("//div[@class='buyer_portal']")||isElementpresent("//div[@class='system_administration']")||(isElementpresent("//div[@class='buyer_administration']")) ){
			
			
			WebElement main = driver.findElement(By.xpath("//div[@id='v_results']"));
			//Getting the suppleir records per page
			List<WebElement> results = main.findElements(By.xpath("//div[@class='H6_new1 row']"));
		    int max= results.size();
		    System.out.println(max);
		    for(int i=0;i<=max;i++){
		    //	List<WebElement> list2= results.get(i).findElements(By.cssSelector("div[class='row MT20px'] *:last-child"));
		    	//String temp = list2.get(1).getText().trim();
		    	//String temp = results.get(i).findElement(By.cssSelector("div[class='row MT20px'] a")).getText().trim();
		    	
		    	int j=i+1;
		    	String bookmarkmsg = null;
				String profilenamecap=null;
				if(results.get(i).findElement(By.cssSelector("div[class='row MT20px'] a")).isDisplayed()){
					profilenamecap = results.get(i).findElement(By.cssSelector("div[class='row MT20px'] a")).getText();
						System.out.println(profilenamecap);
						if(profileName.equals(profilenamecap.trim())){
							
								String id1="//img[@id='savedSupplier";
								String id2="']";
								bookmarkmsg = main.findElement(By.xpath(id1+j+id2)).getAttribute("alt");
								System.out.println(bookmarkmsg);
								if(bookmarkmsg.equals("Add bookmark")){
									log("Bookmarking:"+ profileName);
									driver.findElement(By.xpath(id1+j+id2)).click();
									return "Pass";
								}
								else if(bookmarkmsg.equals("Remove bookmark")){
									log("UnBookmarking:"+ profileName);
									driver.findElement(By.xpath(id1+j+id2)).click();
									return "Pass";
								}	
								
							}
						}
			
			}
		}
	}catch(Exception e){
		log("Unable to do profileBookmark" + e);
		return "Unable to do profileBookmark";
	}
	return "Pass";
		
		}

public String emailShortRecord(String inputname) {
	try{
		log("Executing emailShortRecord");
		keywordSearchByClick(inputname);
	String profileName= inputname;
	String profilenamecap=null;
	String xpa1="//*[@id='v_results']/div[";
	String xpa2="]/div[1]/div[1]/div/h2/span";
	List<WebElement> results = driver.findElements(By.xpath("//div[@class='sup-container']"));
    int max= results.size();
	
	for(int i=1;i<=max;i++){

		
		WebElement main =driver.findElement(By.xpath("//div[@id='v_results']/div"));  
		if(main.findElement(By.xpath(xpa1+i+xpa2)).isDisplayed()){
			profilenamecap = main.findElement(By.xpath(xpa1+i+xpa2)).getText();
				System.out.println(profilenamecap);
				if(profileName.equals(profilenamecap.trim())){

			if(main.findElement(By.xpath("//img[@title='Email']")).isDisplayed()){
				log("Clicking on email icon");
				driver.findElement(By.xpath("//img[@title='Email']")).click();
				Thread.sleep(1000);
				if(driver.findElement(By.xpath("//*[@id='help_info']/div/div[2]/div")).isDisplayed()){
					String confirm1= driver.findElement(By.xpath("//*[@id='help_info']/div/div[2]/div")).getText();
					System.out.println(confirm1);
					driver.findElement(By.xpath("//*[@id='send_done']")).click();
					String confirm2= driver.findElement(By.xpath("//*[@id='help_info']/div/div[2]/div")).getText();
					System.out.println(confirm2);
					driver.findElement(By.xpath("//input[@type='button']")).click();
					Boolean emailConfirmEmail= driver.findElement(By.xpath("//*[@id='help_info']/div/div[2]/div")).isDisplayed();
					if(emailConfirmEmail.equals("fasle")){
						log("Clciking on email icon for profile worked fine");
						}
				}
			}
				}
		}
	}
	}catch(Exception e){
		log("Unable to check emailShortRecord");
		return "Fail- Unable to check emailShortRecord";
	}
	return "Pass";
	
}

public String keywordSearchByClick(String profileName){
	try{
		log("Executing keywordSearchByClick");
	
	driver.findElement(By.xpath("//input[@id='newNtt']")).sendKeys(profileName);
	driver.findElement(By.xpath("//*[@id='SearchBox']/div/a")).click();
	}catch(Exception e){
		log("Unble to do keywordSearchByClick" + e);
		return "Fail- Unble to do keywordSearchByClick";
	}
	return "Pass";
	
}

public String keywordSearchBykeyborad(String profileName ){
	try{
		log("Executing keywordSearchBykeyborad");
	//String profileName= "2.8 Supplier admin";
	driver.findElement(By.xpath("//input[@id='newNtt']")).sendKeys(profileName);
	driver.findElement(By.xpath("//input[@id='newNtt']")).sendKeys(Keys.ENTER);
	}catch(Exception e){
		log("Unble to do keywordSearchBykeyborad" + e);
		return "Fail- Unble to do keywordSearchBykeyborad";
	}
	return "Pass";
}

public String facetSelection(){
	try{
		
		String Resultsperpage  = driver.findElement(By.xpath("//*[@id='ContentArea']/div[1]/div[3]")).getText();
		String result = Resultsperpage.substring(Resultsperpage.indexOf("f") + 1, Resultsperpage.indexOf("r"));
		 int x= Integer.parseInt(result.trim()) ;
		 if(x>1){
						String xpath1="//*[@id='faq12']/li[1]/a"; 
						String xpath2 = "";
						String xpath3 = "";
						//int leastn;
						log("Executing facetSelection");
						System.out.println(OR.getProperty("parent_facet_name"));
						System.out.println(OR.getProperty("child_facet_name"));
						
						//Selection of facet is decided here
						driver.findElement(By.xpath("//*[@id='ExpandAndCollapseControl']/a[1]")).click();
						List<WebElement> morelinks = driver.findElements(By.linkText("More..."));
						for(int i=0;i<morelinks.size();i++){
							WebElement facetlink= driver.findElement(By.linkText("More..."));
							facetlink.click();
							Thread.sleep(2000);
						}
						
						if(driver.findElement(By.xpath("//*[contains(text(), '"+OR.getProperty("parent_facet_name")+"')]")).isDisplayed()){
						WebElement parent = driver.findElement(By.xpath("//*[contains(text(), '"+OR.getProperty("parent_facet_name")+"')]"));
						parent.click();
						
						}
						if((OR.getProperty("child_facet_name")!=null) && driver.findElement(By.xpath("//*[contains(text(), '"+OR.getProperty("child_facet_name")+"')]")).isDisplayed()){
							
							WebElement child = driver.findElement(By.xpath("//*[contains(text(), '"+OR.getProperty("child_facet_name")+"')]"));
							child.click();
						}
						
						/*driver.findElement(By.xpath(xpath1)).click();
							
						if(!(xpath2.equals(""))){
								
								driver.findElement(By.xpath(xpath2)).click();
							
							}
							if(!(xpath3.equals(""))){
								
								driver.findElement(By.xpath(xpath3)).click();
								
							}
							*/
							String confrmn= driver.findElement(By.xpath("//*[@id='coltable']/div[2]/div[1]")).getText();
							System.out.println(confrmn);
							String confrmn1= driver.findElement(By.xpath("//*[@id='breadcrumbListDiv']")).getText();
							System.out.println("Breadcrum list"+ confrmn1);
							List<WebElement> names= driver.findElements(By.xpath("//li[@class='descriptor']"));
							int max= names.size();
							for(int i=0;i<max;i++){
								String temp= names.get(i).getText();
							System.out.println(temp);
							}
		 }
	}catch(Exception e){
		log("Unable to do facet selection" + e);
		return "Fail- Unable to do facet selection";
	}
		
	
	return "Pass";	
		
}
public String endecaRuntime(String maxdaysdiff) {
				try{
					log("Executing endecaRuntime");
					//String Resultsperpage  = driver.findElement(By.xpath("//*[@id='ContentArea']/div[1]/div[3]")).getText();
					//String result = Resultsperpage.substring(Resultsperpage.indexOf("f") + 1, Resultsperpage.indexOf("r"));
					String Resultsperpage = driver.findElement(By.xpath("//*[@id='paginationDiv']/div[3]/span")).getText();
					 int x= Integer.parseInt(Resultsperpage.trim()) ;
					 int xlsdiff = Integer.parseInt(maxdaysdiff.trim());
					 if(x>1){
					selectDropdownVisibleText("sortby_xpath", "Newest Suppliers");
					 }
				DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
				Date date = new Date();
				System.out.println(dateFormat.format(date));
				//System.out.printf("%s %tb %<te, %<tY", 
			         //   "Due date:", date);
				
				String inputString1 = driver.findElement(By.xpath("//*[@id='v_results']/div[1]/div[1]/div[4]/div[5]")).getText();
					Date date2 = date;
				    Date date1 = dateFormat.parse(inputString1);
				    long diff = date2.getTime() - date1.getTime();
				   log("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
				    long diffInDays = diff / (24 * 60 * 60 * 1000);
				   log("diffInDays: "+diffInDays);
				   WebElement datarefresh= driver.findElement(By.xpath("//*[@id='container']/div/div[1]/div[1]"));
				    if(datarefresh.getText().trim().equals("Data update in process.")){
				    	log("Data refresh screen is displaying. Check screen shot");
				    	takeScreenCapture("Endeca Fail");
				    	return "Fail--due to endeca refresh message";
				    }
				    else{
				    
						    if(diffInDays<xlsdiff){
						    	log("Endeca working");
						    	return "Pass";
						    }
						    else{
						    	log("Endeca failing due to difference in days. Last run was on "+ date1);
						    	return "Fail-- Endeca fail due to number of mismatch in days.";
						    }
				    }
				}catch(Exception e){
				log("Unable to check endecaRuntime" + e);
				return "Fail - Unable to check endecaRuntime  ";
			}
			
	}

public String smallBizRecordcontentVerify(String profileName){
	
	try{
		log("Executing smallBizRecordcontentVerify");
	String reconrdName=profileName;
	String xpath1="//*[@id='v_results']/div/div/";
	driver.findElement(By.xpath(xpath1+"div[1]/div[1]/span")).getText().equals("california");
	driver.findElement(By.xpath(xpath1+"div[2]/div[1]/span")).getText().equals("california");
	driver.findElement(By.xpath(xpath1+"div[3]/div[1]/span")).getText().equals("Colorado");
	driver.findElement(By.xpath(xpath1+"div[4]/div[1]/span")).getText().equals("12345");
	driver.findElement(By.xpath(xpath1+"div[5]/div[1]/span")).getText().equals("ABC LTH");
	driver.findElement(By.xpath(xpath1+"div[6]/div[1]/span")).getText().equals("1232123241");
	driver.findElement(By.xpath(xpath1+"div[7]/div[1]/span")).getText().equals("supplierindus@gmail.com");
	driver.findElement(By.xpath(xpath1+"div[8]/div[1]/span")).getText().equals("");
	driver.findElement(By.xpath(xpath1+"div[9]/div[1]/span")).getText().equals(" ");
	driver.findElement(By.xpath(xpath1+"/div[10]/div[1]/span")).getText().equals("");
	driver.findElement(By.xpath(xpath1+"div[11]/div[1]/span")).getText().equals("");
	driver.findElement(By.xpath(xpath1+"div[12]/div[1]/span")).getText().equals("Alaska");
	driver.findElement(By.xpath(xpath1+"div[13]/div[1]/span")).getText().equals("Automotive, Manufacturing, Retail, Technology, Transportation, Travel");
	driver.findElement(By.xpath(xpath1+"div[14]/div[1]/span")).getText().equals("Professional, Marketing and Technical Services:Educational Services");
	driver.findElement(By.xpath(xpath1+"div[15]/div[1]/span")).getText().equals("");
	driver.findElement(By.xpath(xpath1+"div[16]/div[1]/span")).getText().equals("Commercial");
	driver.findElement(By.xpath(xpath1+"div[17]/div[1]/span")).getText().equals("Non-Diverse");
	driver.findElement(By.xpath(xpath1+"div[18]/div[1]/span")).getText().equals("Bureau of Indian Affairs");
	}catch(Exception e){
		log("Unable to verify smallBizRecordcontentVerify " + e);
		return "Fail- Unable to verify smallBizRecordcontentVerify";
	}
	
	return "Pass";
	}

public String sortBy(String dropdownoption) {
	try{
		log("Executing sortby");
		String Resultsperpage  = driver.findElement(By.xpath("//div[@class='col showingMetaDiv']")).getText();
		String result = Resultsperpage.substring(Resultsperpage.indexOf("f") + 1, Resultsperpage.indexOf("r"));
		 int x= Integer.parseInt(result.trim()) ;
		 if(x>1){
					//String[] sortbynames = {"Relevance", "Newest Suppliers", "Supplier Name(A-Z)","Supplier Name(Z-A)"};
					//int size= sortbynames.length;
					//for(int i=0;i<size;i++){
					//Sort by 
					String sortbyname =	dropdownoption.trim();
					log("Sorting by----"+sortbyname);
					WebElement sortbyele=driver.findElement(By.xpath("//select[@id='sortBy_select']"));
					Select sortby = new Select (sortbyele);
				           sortby.selectByVisibleText(sortbyname);
				           Thread.sleep(2000);
				           Verifytextpresent("Leftsidenav_narrow_xapth", "Narrow Suppliers by:");
					//}
		 }
	}catch(Exception e){
		log("Unable to do sort: "+e);
		return "Fail- unable to do sort";
		
	}
	return "Pass";
}



public String typeAheadsearch(String profileName, String role) {
	try{
		log("Executing Type Ahead Search-- feature is not in supplier employee");
		String profileName1= "ABC LTH";
		if((role.equals("Supplieradmin")) || (role.equals("Buyer")) ||(role.equals("Buyeradmin")) || (role.equals("Systemadmin"))){
			System.out.println("after comparing roles");
				driver.findElement(By.xpath("//input[@id='newNtt']")).sendKeys("abc");
				//waitfor();
				//if(driver.findElement(By.xpath("html/body/div[8]/div")).isDisplayed()){
				if(isElementpresent("html/body/div[8]/div")){
					System.out.println("After displaying result box");
					int i=1;
					while(!(driver.findElement(By.xpath("html/body/div[8]/div/div/div["+i+"]")).getText().trim().equals(profileName1))){
						System.out.println("Searching for file name");
						//driver.findElement(By.xpath("html/body/div[8]/div/div/div["+i+"]")).click();
						String temp =driver.findElement(By.xpath("html/body/div[8]/div/div/div["+i+"]")).getText();
						//typeaheadResult.get(i).toString();
						log("Type ahead list name"+temp);
					
						i++;
					}
					driver.findElement(By.xpath("html/body/div[8]/div/div/div["+i+"]")).click();
					
					}	
				
		}
	}catch(Exception e){
		log("Unable to do typeahead search:"+ e);
		return "Fail- unable to do sort";
}
	return "Pass";
}

public String pdfReading(String ProfileName){
	try{
		log("Reading PDF");
	String jacobDllVersionToUse;
	jacobDllVersionToUse = "jacob-1.18-x64.dll";
	
	File file = new File("lib", jacobDllVersionToUse);
	System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
	String profileName= ProfileName;
	keywordSearchByClick(profileName);
	Thread.sleep(1000);
	
	driver.findElement(By.xpath("//img[@title='Print']")).click();
	//Process process = new ProcessBuilder("C:\\Users\\IBM_ADMIN\\Desktop\\smallbizprofiledownlaod.exe").start();
	AutoItX x = new AutoItX();
	Thread.sleep(3000);
	
	x.winActivate("[CLASS:MozillaDialogClass]");
	String temp=x.winGetTitle("[CLASS:MozillaDialogClass]");
	x.sleep(700);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{TAB}");
	x.sleep(700);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{TAB}");
	x.sleep(700);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{TAB}");
	x.sleep(700);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{Enter}");
	
String a[] = temp.split(" ",2);
System.out.println(a[0]);
System.out.println(a[1]);
Thread.sleep(2000);

driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
driver.get(OR.getProperty("pdfDownloadpath")+a[1]);
URL url = new URL(driver.getCurrentUrl());
String pdfname= driver.getTitle();
log("PDF page title:"+pdfname);
BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
 
PDFParser parser = new PDFParser(fileToParse);
parser.parse();
 
String output = new PDFTextStripper().getText(parser.getPDDocument());
String a1[]= output.split("\\n");
int max= a1.length;
for(int i=0;i<max;i++)
	System.out.println(a1[i]);
//System.out.println(output);
parser.getPDDocument().close();
driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"w");
driver.findElement(By.xpath("//a[@class='removalLink']")).click();

//File (or Directory) to be moved
File file1 = new File(OR.getProperty("pdfsrcpath")+a[1]);

// Destination directory
File dir = new File(System.getProperty("user.dir")+OR.getProperty("pdfdestpath"));

// Move file to a new directory
 file1.renameTo(new File(dir, file1.getName()));


	}catch(Exception e)
	{
		log("Exception caught while reading pdf"+ e);
		return "Fail--unable to read PDF";
	}
	return "Pass";
	
}


public String pdfReading_buyer(String ProfileName){
	try{
		log("Reading PDF");
	String jacobDllVersionToUse;
	jacobDllVersionToUse = "jacob-1.18-x64.dll";
	
	File file = new File("lib", jacobDllVersionToUse);
	System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
	String profileName= ProfileName;
	keywordSearchByClick(profileName);
	Thread.sleep(1000);
	
	driver.findElement(By.cssSelector(".print_img")).click();
	//Process process = new ProcessBuilder("C:\\Users\\IBM_ADMIN\\Desktop\\smallbizprofiledownlaod.exe").start();
	Thread.sleep(2000);
	AutoItX x = new AutoItX();
	x.winActivate("[CLASS:MozillaDialogClass]");
	String temp=x.winGetTitle("[CLASS:MozillaDialogClass]");
	System.out.println("temp"+ temp);
	x.sleep(400);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{TAB}");
	x.sleep(400);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{TAB}");
	x.sleep(400);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{TAB}");
	x.sleep(400);
	x.controlSend("[CLASS:MozillaDialogClass]", "", "", "{ENTER}");
	
String a[] = temp.split(" ",2);
System.out.println(a[0]);
System.out.println(a[1]);
waitfor();

driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
driver.get(OR.getProperty("pdfDownloadpath")+a[1]);
URL url = new URL(driver.getCurrentUrl());
String pdfname= driver.getTitle();
log("PDF page title:"+pdfname);
BufferedInputStream fileToParse = new BufferedInputStream(url.openStream());
 
PDFParser parser = new PDFParser(fileToParse);
parser.parse();
 
String output = new PDFTextStripper().getText(parser.getPDDocument());
String a1[]= output.split("\\n");
int max= a1.length;
for(int i=0;i<max;i++)
	System.out.println(a1[i]);
//System.out.println(output);
parser.getPDDocument().close();
//closing the pdf tab
driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"w");
driver.findElement(By.xpath("//a[@class='removalLink']")).click();
//File (or Directory) to be moved
File file1 = new File(OR.getProperty("pdfsrcpath")+a[1]);

//Destination directory
File dir = new File(System.getProperty("user.dir")+OR.getProperty("pdfdestpath"));

//Move file to a new directory
file1.renameTo(new File(dir, file1.getName()));




	}catch(Exception e)
	{
		log("Exception caught while reading pdf"+ e);
		return "Fail--unable to read PDF";
	}
	return "Pass";
	
}

public String emailtheseSuppliers(String role){
	log("Checking emailtheseSupplier");
	try{
		
					driver.findElement(By.xpath(OR.getProperty("facet_expandall"))).click();
					driver.findElement(By.xpath(OR.getProperty("click_facetname"))).click();
					String s= driver.findElement(By.xpath(OR.getProperty("record_count"))).getText();
					System.out.println(s);
					String numberofrecords = s.substring(s.indexOf('-')+1);
					String[] sub= numberofrecords.split(" ");
					System.out.println(sub[0]);
					String companyname= driver.findElement(By.xpath(OR.getProperty("companyname_ontop"))).getText().trim();
					String username= driver.findElement(By.cssSelector(OR.getProperty("username_ontop"))).getText().trim();
					String email_id = null;
			if(role.equals("Buyer") || role.equals("Buyeradmin")){
					switch(role){
					case "Buyer":email_id =OR.getProperty("buyerid");
									break;
					case "Buyeradmin":email_id =OR.getProperty("buyeradminid");
										
									break;
					default: log("No role matched to get email id in switch statement");
					}
					
					if(isElementpresent(OR.getProperty("emailthesesuppliers_link"))){
						driver.findElement(By.xpath(OR.getProperty("emailthesesuppliers_link"))).click();
						verifyText(OR.getProperty("form_heading"),"Email Suppliers");
						verifyText(OR.getProperty("form_heading1"),"Use this form to send a message to the"+sub[0]+"suppliers that appear in my search results.");
						verifyText(OR.getProperty("form_heading2"),"All fields are Required");
						verifyText(OR.getProperty("form_heading3"),"To(BCC):");
						verifyText(OR.getProperty("form_heading4"), "["+numberofrecords+"] Suppliers");
						verifyText(OR.getProperty("form_heading5"), "From:");
						verifyText(OR.getProperty("form_heading6"), ""+username+email_id+"");
						String subjecttext=driver.findElement(By.xpath(OR.getProperty("form_heading7"))).getText();
						subjecttext.contains(companyname);
						log("subject of email these suppliers text"+ subjecttext);
						
						//Checking for validation so sending blank
						driver.findElement(By.xpath(OR.getProperty("form_heading8"))).sendKeys(" ");
						
						verifyText(OR.getProperty("form_heading9"), "A copy will be sent to your email address:"+email_id+"");
						
						isElementpresent("cancelBtn");
						driver.findElement(By.xpath(OR.getProperty("form_cancelbutton"))).click();
						
						if(isElementpresent(OR.getProperty("form_validation"))){
							verifyText(OR.getProperty("form_validation"),"Please enter all the required fields");
						}
						
						//typing valid data to send email
						driver.findElement(By.xpath(OR.getProperty("form_heading8"))).sendKeys("Testing email these suppleirs with !@#$%^&*))");
						driver.findElement(By.xpath(OR.getProperty("form_submitbutton"))).click();
						
						//Verifying confirmation overlay
						if(isElementpresent(OR.getProperty("ets_confirmation_overlay1"))){
							verifyText(OR.getProperty("ets_confirm_heading1"),"Email Suppliers");
							verifyText(OR.getProperty("ets_confirm_heading2"), "Your message has been sent. You will receive a copy of the message sent to your email inbox.");
							if(isElementpresent(OR.getProperty("ets_confirm_donebutton"))){
								waitfor();
								WebElement done= driver.findElement(By.xpath(OR.getProperty("ets_confirmation_overlay1")));
								done.findElement(By.xpath(OR.getProperty("ets_confirm_donebutton"))).click();
							}
							else{
								log("Done button not found on email these suppliers confirmation overlay");
							}
						}
					}
					else{
						log("Element  not found for clicking on email these suppliers");
					}
		}
		else{
			log("User is not a buyer or buyer admin. Current logged in role is :" +role);
		}
		
	}catch(Exception e){
		log("Unable to do email these Suppliers"+ e);
		return "Fail-Unable to do email these Supplier";
	}
	return "Pass";
	
}


//copied from Raghu

public String docuploadformat(){
log("Upload doc type file");


	try
	{
		Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//docformatupload.exe");
		Thread.sleep(7000);


	}
	catch(Exception e)
	{
		log("Fail: Unable upload doc type file"+ e);
	 return "Fail: Unable upload doc type file"+ e;
	}
	return "Pass";
}

public String loguploadformat(){
log("Uploading invalid file format");


	try
	{
		Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//logformatupload.exe");
		Thread.sleep(5000);


	}
	catch(Exception e)
	{
		log("Fail : Unable to upload invalid file format"+e);
	 return "Fail : Unable to upload invalid file format";
	}
	return "Pass";
} 

 public String largefileupload(){
log("Uploading more than 10MB file");


	try
	{
		Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//largefileupload.exe");
		Thread.sleep(7000);


	}
	catch(Exception e)
	{
		log("Fail to upload more than 10MB file"+ e);
	 return "Fail to upload more than 10MB file"+ e;
	}
	return "Pass";
} 

 public String uploadmemberlogo(){
log("Uploading JPG file tupe ");


	try
	{
		Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\uploadmemberprofile.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//uploadmemberprofile.exe");
		Thread.sleep(7000);


	}
	catch(Exception e)
	{
	 return "Fail : Unable to uplaod  JPG file "+ e;
	}
	return "Pass";
}
 
 public String zipuploadformat(){
	 log("Uploading zip file format");


	 	try
	 	{
	 		//Thread.sleep(2000);
	 		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Supplierbrochureupload.exe");
	 		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//zipformatupload.exe");
	 		Thread.sleep(7000);


	 	}
	 	catch(Exception e)
	 	{
	 	 return "Fail : Unable to upload Zip File format"+e;
	 	}
	 	return "Pass";
	 } 
 
 public String Verifytextpresent(String xpath, String textmatch){
	 try
	 	{
	 		log("Checking for the text present  " + textmatch+ "in object " + xpath);
	 		Thread.sleep(2000);
	 		
	 		WebElement element =driver.findElement(By.xpath(OR.getProperty(xpath)));
	 		String Pagetext=element.getText().trim();
	 		
	 		
	 		
	 		if(textmatch.equals(null)){
	 			log("Matching text not entered in excel");
	 		}
	 		
	 		
	 		if (Pagetext.equals(textmatch.trim()))
	 		{
	 			//System.out.println("Text is present");
	 			log("Checking for the text present  " + textmatch+ "in object " + xpath);
	 			return "Pass";
	 		}
	 		/*else
	 		{
	 			//System.out.println("Text is not present" + xpath);
	 			log("Failed as text is not present  " + textmatch+ "in object " + xpath);
	 			//return "Fail";
	 			return "Fail -unable to verify text -"+xpath;
	 		}*/
	 	}
	 	catch(Exception e)
	 	{
	 		//System.out.println("Text is not present" + xpath);
	 	// return "Fail";
	 	 log ("Fail -unable to verify text -"+xpath+"Exception:" +e);
	 	 return "Fail-unable to verify text";
	 	 
	 	}
	return "Pass";
	 	
	 } 
 
 public String verifyText(String xpath_text, String text){
	 try{
		 String temp = driver.findElement(By.xpath(xpath_text)).getText().trim();
		 
		 if(temp.equals(text.trim())){
			 log("Text matches");
			 return "Pass";
		 }
		 
		 
	 }catch(Exception e){
		 log("Fail -unable to test verifytext"+e);
		 return"Fail-unable to test verifytext";
	 }
	return "Pass";
 }
 
 public String checkDiversitystatus(String status_xpathname){
	 try{
		 		String h=null;
		 		String s = OR.getProperty(status_xpathname);
		 		System.out.println("s"+ s);
		 		String k= s.substring(s.lastIndexOf('[')+1,s.lastIndexOf(']'));
		 		System.out.println("k"+k);
		 		
		 		WebElement statuschk= driver.findElement(By.xpath(OR.getProperty(status_xpathname)));
				String diversityname= statuschk.findElement(By.tagName("label")).getText();
				log("Checking Diversity status in step9 for name: "+ diversityname);
				
				//System.out.println("diversityname--"+diversityname);
				WebElement statusofCheckbox= statuschk.findElement(By.cssSelector("input[type='checkbox']"));
				
				String id= statusofCheckbox.getAttribute("id");
				boolean checked= statusofCheckbox.isSelected();
				System.out.println("checked--" +checked);
				System.out.println("ID:" + id);
						
				//System.out.println(id);
				log("checked--"+  checked);
				
				//Getting certificate number based on selected diversity 
				h= adddivesritycernumber(k);
				
						
				if(checked==false){
							
					System.out.println("Inside checking of diversity");
							
					statuschk.findElement(By.cssSelector("input[type='checkbox']")).click();
							Thread.sleep(4000);
							
							String temp1="//*[@id='certificationList";
				    		String temp2="']/fieldset/div[2]";
							List<WebElement>  certifications1 = driver.findElements(By.xpath(temp1+h+temp2));
							
									
							int cer_mx= certifications1.size();
							for(int j=0;j<cer_mx;j++){
								List<WebElement>  certifications = driver.findElements(By.xpath(temp1+h+temp2));
								System.out.println(certifications.get(j).getText());
								
								
								List<WebElement> cert = certifications.get(j).findElements(By.cssSelector("input[type='checkbox']"));
								for(int m=0;m<cert.size();m++){
									cert.get(m).click();//selecting certifications wrt to diversity status checked
								}
						}
								
						}//end of for for certification
						else{
							log("Diversity status is already selected for "+ diversityname);
							
						}
						
						
						
						
				
	
		}catch(Exception e){
		 log("Unable to check exception"+  e);
		 return "Fail--Unable to check diversity status";
	 }
	return "Pass";
	
	 
 }
 
 public String step9Fileupload(String status_xpathname){
	 String res = null;
	 String h=null;
	 try{
		 			log("Inside step9Fileupload");
				 	String s = OR.getProperty(status_xpathname);
				 	String k= s.substring(s.lastIndexOf('[')+1,s.lastIndexOf(']'));
				 	h= adddivesritycernumber(k);
				 	String filename= OR.getProperty("step9_filepath");
					Xls_Reader xlsreader = new Xls_Reader(filename);
					
					//Xls_Reader.setExcelFile(filename, "datadriven");
				 	
				 	int testCaseStartRowNum= Xls_Reader.getcurrentRowNumofTCID("SC_step9","datadriven", xlsreader);
				 			
				 	int rowStartRowNum=testCaseStartRowNum+2;
					int rows=0;
					
				 	
				 	
				 	
				 	
					//Iteration to fetch data for date entry directly from sheet
					while(!xlsreader.getCellData("datadriven", 0, (rowStartRowNum+rows)).equals("")){
							//rowStartRowNum=rowStartRowNum+rows;
							WebElement statuschk= driver.findElement(By.xpath(OR.getProperty(status_xpathname)));
							String diversityname= statuschk.findElement(By.tagName("label")).getText();
							//WebElement certificationsCheckbox =driver.findElement(By.xpath("//*[@id='AddCertificate"+h+"']/div[6]/div[1]"));
							WebElement certificationsCheckbox = driver.findElement(By.xpath("//*[@id='certificationList"+h+"']"));
							WebElement uploadBox = certificationsCheckbox.findElement(By.className("upload"));
							
							
						
							if( uploadBox.findElement(By.xpath("//*[@id='upload"+h+"']")).isDisplayed()){
								
								
									
									String day=xlsreader.getCellData("datadriven", 0, (rowStartRowNum+rows)).trim();
									String month = xlsreader.getCellData("datadriven",1, (rowStartRowNum+rows)).trim();
									String year= xlsreader.getCellData("datadriven", 2, (rowStartRowNum+rows)).trim();
									String fileUpload = xlsreader.getCellData("datadriven",3, (rowStartRowNum+rows)).trim();
									String helpcontent = xlsreader.getCellData("datadriven",4, (rowStartRowNum+rows)).trim();
									
									System.out.println("fileupload"+ fileUpload);
								 	System.out.println("day"+day);
								 	
								 	System.out.println("Month"+ month);
								 	
								 	System.out.println("Year"+ year);
								 	
								 	
								
								 	WebElement certdate = driver.findElement(By.xpath("//*[@id='AddCertificate"+h+"']/div[6]/div[1]"));
									
								 		if(certdate.isDisplayed()){
								 			//checking help bubble
											driver.findElement(By.xpath("//*[@id='certificationList"+h+"']/a")).click();
											
											Verifytextpresent("step9_help_link", helpcontent);
											log("Step9 help content verified");
											click("step9_help_close");
											
								 			System.out.println("inside certificate date entry");
								 			certdate.findElement(By.xpath("//*[@id='balanceSheetFile"+h+"']")).sendKeys(fileUpload.trim());
								 			waitfor();
								 			certdate.findElement(By.cssSelector("input[title='Day']")).clear();
								 			certdate.findElement(By.cssSelector("input[title='Day']")).sendKeys(day);
								 			certdate.findElement(By.cssSelector("input[title='Month']")).clear();
								 			certdate.findElement(By.cssSelector("input[title='Month']")).sendKeys(month);
								 			certdate.findElement(By.cssSelector("input[title='Year']")).clear();
								 			certdate.findElement(By.cssSelector("input[title='Year']")).sendKeys(year);
								 			waitfor();
								}
								 		driver.findElement(By.cssSelector("input#upload"+h+"")).click();
								 		 driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS );
								
								
								
										//checking validation after file upload
								 		 String commonmsg = driver.findElement(By.xpath("//*[@id='main-content']/div/div[1]/div[1]")).getText().trim();
											if(commonmsg.equals(OR.getProperty("step9_commonerrormsg"))){
													
														if(isElementpresent("//div[@id='divnofilechosen"+h+"']")){
														String Filevalidmesg = driver.findElement(By.xpath("//div[@id='divnofilechosen"+h+"']")).getText().trim();
														
														if(Filevalidmesg.equals("No File Chosen"))
															System.out.println("File not selected");
														
														log("File not selected as directly clicked on upload button without selecting any file");
														log("No File Chosen message apeared for "+ fileUpload);
														}
														
														if(isElementpresent("//div[@id='error_balanceSheetFile"+h+"']")){
														if(driver.findElement(By.xpath("//div[@id='error_balanceSheetFile"+h+"']")).getText().trim().equals("File extension is not allowed.")){
															System.out.println("Checking file upload for wrong format");
															String errormsg= driver.findElement(By.xpath("//div[@id='error_balanceSheetFile"+h+"']/ul/li/span")).getText().trim();
															System.out.println(errormsg);
														}
														
														if(driver.findElement(By.xpath("//div[@id='error_balanceSheetFile"+h+"']")).getText().trim().equals("Uploaded file was too large.")){
															System.out.println("Checking file upload size as it exceeds 10MB");
															String errormsg= driver.findElement(By.xpath("//div[@id='error_balanceSheetFile"+h+"']/ul/li/span")).getText().trim();
															System.out.println(errormsg);
														}
													
														}
														
														if(isElementpresent("//div[@id='error_diversityCertExpiryDate"+h+"']")){
													if((driver.findElement(By.xpath("//div[@id='error_diversityCertExpiryDate"+h+"']"))).isDisplayed()){
																
																if(driver.findElement(By.xpath("//div[@id='error_diversityCertExpiryDate"+h+"']")).getText().trim().equals("To proceed, either change date to future date,or remove expiry date entry.")) {
																Calendar now = Calendar.getInstance();
															    // 
															   
															    int currentyear = now.get(Calendar.YEAR);
															    int currentmonth = (now.get(Calendar.MONTH) + 1);
															    int currentday =  now.get(Calendar.DATE);
															    
															    // month start from 0 to 11
															    System.out.println("Current Year is : " + now.get(Calendar.YEAR));
															    System.out.println("Current Month is : " + (now.get(Calendar.MONTH) + 1));
															    System.out.println("Current Day is : " + now.get(Calendar.DATE));
															  
															    WebElement certificationsCheckbox1 = driver.findElement(By.xpath("//div[@id='AddCertificate"+h+"']"));
															   
															    String enteredDay;
															    String enteredMonth;
															    String enteredYear;
															    if(h=="1"){
															    	 enteredDay= certificationsCheckbox1.findElement(By.xpath("//input[@id='diversityCertExpiryDay'][@title='Day']")).getAttribute("value").trim();
																    waitfor();
																     enteredMonth = certificationsCheckbox1.findElement(By.xpath("//input[@id='diversityCertExpiryMonth'] [@title='Month']")).getAttribute("value").trim();
																    waitfor();
																     enteredYear= certificationsCheckbox1.findElement(By.xpath("//input[@id='diversityCertExpiryYear']")).getAttribute("value").trim();
																    waitfor();
															    }
															    else{
															     enteredDay= certificationsCheckbox1.findElement(By.xpath("//input[@id='diversityCertExpiryDay"+h+"'][@title='Day']")).getAttribute("value").trim();
															    waitfor();
															     enteredMonth = certificationsCheckbox1.findElement(By.xpath("//input[@id='diversityCertExpiryMonth"+h+"'] [@title='Month']")).getAttribute("value").trim();
															    waitfor();
															     enteredYear= certificationsCheckbox1.findElement(By.xpath("//input[@id='diversityCertExpiryYear"+h+"']")).getAttribute("value").trim();
															    waitfor();
															    }
															    
															    System.out.println("enteredDay"+ enteredDay);
															    System.out.println("enteredMonth"+ enteredMonth);
															    System.out.println( "enteredYear"+ enteredYear);
								
																   
																		
															   
															  int enteredYear1= Integer.valueOf(enteredYear.trim()) ;
															  int enteredMonth1 = Integer.valueOf(enteredMonth.trim()) ;
															  int enteredDay1 = Integer.valueOf(enteredDay.trim()) ;
															  
															  
															  System.out.println("enteredYear in int"+enteredYear1);
																if(enteredYear1<currentyear){
																	System.out.println("comparing years");
																	String errormsg= driver.findElement(By.xpath("//div[@id='error_diversityCertExpiryDate"+h+"']/ul/li/span")).getText().trim();
																	System.out.println(errormsg);
																	
																}
																
																if((enteredYear1==currentyear) && ((enteredMonth1<currentmonth))){
																	System.out.println("comparing months");
																	String errormsg= driver.findElement(By.xpath("//div[@id='error_diversityCertExpiryDate"+h+"']/ul/li/span")).getText().trim();
																	System.out.println(errormsg);
																	
																}
																
																if((enteredYear1==currentyear) && ((enteredMonth1==currentmonth)) && (enteredDay1<currentday)){
																	System.out.println("comparing days");
																	String errormsg= driver.findElement(By.xpath("//div[@id='error_diversityCertExpiryDate"+h+"']/ul/li/span")).getText().trim();
																	System.out.println(errormsg);
																	
																}
																
																}
																
													}//end of date validation
													
														}
													
													//return "Pass";
											}	
											else{
												log("There is no validation message");
												//return "Pass";
											}
											
											
													
							
			}else{
				log("Certificate box not displayed for"+ diversityname);
			}
			rows++;
			System.out.println("rows"+rows);
					}	
	 }catch(Exception e){
		 log("Unable to upload file in step9"+ e);
		 return "Fail--unable to upload file in step9";
	 }
	
	 return "Pass";
 }
 
 
 
 
 public String uncheckDiversitystatus(String status_xpathname){
	 try{
		 String s = OR.getProperty(status_xpathname);
		 String n= s.substring(s.lastIndexOf('[')+1,s.lastIndexOf(']'));
		 String h = null;
		 System.out.println("n:"+ n);
		 
		
		 
		 WebElement statuschk= driver.findElement(By.xpath(OR.getProperty(status_xpathname)));
			String diversityname= statuschk.findElement(By.tagName("label")).getText();
			System.out.println("diversityname--"+diversityname);
	
		
			WebElement statusofCheckbox= statuschk.findElement(By.cssSelector("input[type='checkbox']"));
			
			String id= statusofCheckbox.getAttribute("id");
			boolean checked= statusofCheckbox.isSelected();
			System.out.println("checked--" +checked);
			System.out.println("ID:" + id);
					
			//System.out.println(id);
			log("checked--"+  checked);
			
			
			
			//Keep adding h for new diversity status
			//Getting certificate number based on selected diversity 
			h= adddivesritycernumber(n);
			
			
			
			if(checked==true){
		
						    	 
						if(driver.findElement(By.xpath("//div[@id='divCertAttachment"+h+"']")).isDisplayed()){
							
									if(driver.findElement(By.xpath("//div[@class='upload'] [@id='AddCertificate"+h+"']")).isDisplayed()){
										
						    	 				log("Remove button not present as no file attached but displaying certification box");
						    	 				
						    	 				
						    	 				driver.findElement(By.cssSelector("#"+id)).click();
				
						    	 	}
						    	 
						    	 	
						    	 	
						    	 		
									else	{
								    	 			log("Remove button is present");
								    	 			driver.findElement(By.id("remove"+h+"")).click();//Clicking on remove button of attachment
													Thread.sleep(2000);
												
													Verifytextpresent("step9_popUpRemoveMsg_xpath",OR.getProperty("step9_popUpRemoveMsg_text"));
													driver.findElement(By.xpath(OR.getProperty("step9_remove_button_popupiverlay"))).click();//Click on popup Remove button
													Thread.sleep(2000);
													driver.findElement(By.cssSelector("#"+id)).click();
								    	 		
						    	 			}
						}
						    		
						    	 	
						else{	
						    	 		log("Uncheck status when no certification added");
						    	 		driver.findElement(By.cssSelector("#"+id)).click();
						    		 	
						    	 }

			}
			else{
				log("Already unchecked" +diversityname);
			}
	 
	 }catch(Exception e){
		 log("Unable to uncheck exception"+  e);
		 return "Fail--Unable to uncheck diversity status";
	 }
	return "Pass";
	 
 }
 
 
 
 public String step8defaultcheck(String xpath){
	 try{
		/* log("Checking Step8 Deafult check for No option for Individual contractor");
		 boolean defaultnocheck= driver.findElement(By.xpath(OR.getProperty(xpath))).isSelected();
		 System.out.println("defaultnocheck"+  defaultnocheck);
		 
		 if(!defaultnocheck){*/
			 log("Default no checking is not done so selecting no option");
			 driver.findElement(By.xpath(OR.getProperty("step8_IndependentContractor_no_xpath"))).click();
		// }
		 
		 
		log("Default no is checked so executing next steps");
		isDisabled_button("step8_IndependentContractor_yes_xpath");
		 isDisabled_button("step8_commercialoffice_yes_xpath");
		 isDisabled_button("step8_commercialoffice_no_xpath");
		 isDisabled_textbox("step8_commercialaddress_xpath");
		 isDisabled_button("step8_directemploy_yes_xpath");
		 isDisabled_button("step8_directemploy_no_xpath");
		 isDisabled_textbox("step8_directemploy_howmany_xpath");
		 isDisabled_textbox("step8_directemploycapacity");
		 isDisabled_button("step8_IncorporatedCompany_yes_xpath");
		 isDisabled_button("step8_IncorporatedCompany_no_xpath");
		 isDisabled_textbox("step8_netInvestment_xpath");
		 isDisabled_textbox("step8_clientbasesize_xpath");
		 isDisabled_textbox("step8_noofclients_xpath");
		 isDisabled_textbox("step8_clientaddress_xpath");
		 isDisabled_button("step8_AdvertiseMassMediaStrtrue_ye");
		 isDisabled_button("step8_AdvertiseMassMediaStrtrue_no");
		 isDisabled_button("step8_upload");
		 isDisabled_button("step8_scemployee_yes");
		 isDisabled_button("step8_scemployee_no");
		 isDisabled_button("step8_recenttypeemployment");
		 isDisabled_textbox("step8_employmentlocation");
		 isDisabled_textbox("step8_employment_day");
		 isDisabled_textbox("step8_employment_month");
		 isDisabled_textbox("step8_employment_year");
		 isSelected("step8_agreement_xpath");
			 
		driver.findElement(By.xpath(OR.getProperty("step8_commercialoffice_help"))).click();
		Thread.sleep(2000);
		Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_commercialoffice_help_content"));
		driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
			 
		 driver.findElement(By.xpath(OR.getProperty("step8_commercialaddress_helpbubble_xpath"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_commercialaddress_helpbubble_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_directemploy_help_xpath"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_directemploy_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_directemploy_howmany_help_xpath"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_directemploy_howmany_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_directemploycapacity_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_directemploycapacity_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_IncorporatedCompany_help_xpath"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_IncorporatedCompany_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_netInvestment_help_xpath"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_netInvestment_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_clientbasesize_help_xpath"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_clientbasesize_help_contnet"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_noofclients_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_noofclients_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_clientaddress_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_clientaddress_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_AdvertiseMassMediaStrtrue_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_AdvertiseMassMediaStrtrue_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_provideevidence_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_provideevidence_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_scemployee_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_scemployee_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_recenttypeemployment_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_recenttypeemployment_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_employmentlocation_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_employmentlocation_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_employment_help"))).click();
		 Verifytextpresent("step8_helpbubble_xpath1", OR.getProperty("step8_emplyment_help_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step8_saveasdraft"))).click();
		 Thread.sleep(2000);
		 
		 Verifytextpresent("step8_savedraft_msgxpath", OR.getProperty("step8_savedraft_msg"));
		 
		 
	 }catch(Exception e){
		 log("Unable to check step8defaultcheck"+ e);
		 return "Fail--Unable to check step8defaultcheck";
	 }
	return "Pass";
	 
 }
 
 public String step3helpfile(){
	 try{
		 driver.findElement(By.xpath(OR.getProperty("step3_currencycode_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_currencycode_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_revenue_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_revenue_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_lastyear_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_lastyear_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_2yearsago_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_2yearsago_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_3yearsagao_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_3yearsago_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_lastyearprofit_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_lastyearprofit_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_2yearsago_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_2yearsprofit_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_3yearsprofit_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_3yearsprofit_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_lastyearasset_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_lastyearasset_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_2yearsagoasset_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_2yearsagoasset_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_3yearsagoasset_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_3yearsagoasset_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_liability_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_liability_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_2yearsliability_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_2yearsliability_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
		 driver.findElement(By.xpath(OR.getProperty("step3_3yearsliability_help"))).click();
		 Verifytextpresent("step8_commercialoffice_help_xpath", OR.getProperty("step3_3yearsliability_content"));
		 driver.findElement(By.xpath("//*[@id='help_info']/div/div/div[1]/a")).click();
		 
	 }catch(Exception e){
		 log("Unable to check step3helpfile" + e);
		 return "Fail-Unable to check step3helpfile";
	 }
	return "Pass";
	 
 }
 
 public String adddivesritycernumber(String n){
	 String h = null;
	//Keep adding h for new diversity status
		if(n.equals("1"))
			h="1";
		else if(n.equals("4"))
			h="2";
		else if(n.equals("7"))
			h="3";
		else if(n.equals("10"))
			h="4";
		else if(n.equals("13"))
			h="5";
		else if(n.equals("16"))
			h="6";
		else if(n.equals("19"))
			h="7";
		else if(n.equals("22"))
			h="8";
		else if(n.equals("25"))
			h="9";
		else if(n.equals("28"))
			h="10";
		else if(n.equals("31"))
			h="11";
		else if(n.equals("34"))
			h="12";
		else if(n.equals("37"))
			h="13";
		else if(n.equals("40"))
			h="14";
		return h;
 }
 
 public String sortBy_findsupplier(String userrole){
	 try{
		 log("Executing sortby_findsupplier for the role" +  userrole);
		 	if((userrole.equals("Buyer")) ||(userrole.equals("Buyeradmin")) || (userrole.equals("Systemadmin"))){
		 		
		 	
					log("Executing sortby for role :"+ userrole);
					String Resultsperpage  = driver.findElement(By.cssSelector(".col.showingMetaDiv")).getText();
					String result = Resultsperpage.substring(Resultsperpage.indexOf("f") + 1, Resultsperpage.indexOf("r"));
					 int x= Integer.parseInt(result.trim()) ;
					 
					 //getting the current page number to verify text
					 String pagenumber = Resultsperpage.substring(Resultsperpage.indexOf("g") + 1, Resultsperpage.indexOf("-")); 
					 
					 if(x>1){
								String[] sortbynames = {"Relevance", "Newest Suppliers", "Supplier Name(A-Z)","Supplier Name(Z-A)"};
								int size= sortbynames.length;
								for(int i=0;i<size;i++){
								//Sort by 
								String sortbyname =	sortbynames[i];
								log("Sorting by----"+sortbyname);
								WebElement sortbyele=driver.findElement(By.cssSelector(OR.getProperty("sortby_css")));
								Select sortby = new Select (sortbyele);
							           sortby.selectByVisibleText(sortbyname);
							           Thread.sleep(2000);
							           if(pagenumber=="1"){
							           Verifytextpresent("currentsupplierofthemonth_xpath", OR.getProperty("currentsupplierofthemonth_msg"));
							           }else{
							        	   Verifytextpresent("//div[@id='LeftSideBar']/div[1]/h2", "Narrow Suppliers by:");
							           }
								}
					 }else{
						 log("sort by option is not present for single search result");
					 }
			 }
		 
	 }catch(Exception e){
		 log("Unable to do sort by in find a supplier for role:"+userrole+"---"+e);
		 return "Fail--Unable to do sort by in find a supplier for role";
		 
	 }
	return "Pass";
 }

//Select dropdown by value 

public String selectdropdownByvalue(String xpathtext,String inputText)  {
log("select text"+inputText+"in"+xpathtext);
		try {
			Select dropdown = new Select(driver.findElement(By.xpath(xpathtext)));
			dropdown.selectByValue(inputText);
			log("selected value "+inputText+"in"+xpathtext);
					} 
		catch (Exception e) { 

                      return "Fail -unable to select -"+xpathtext;
                      

	}
return "Pass";
}

public String Supplierprofilelogoupload()
   {
        log("upload supplier company logo in step1 ");

	try
	{
		//Thread.sleep(2000);
		//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\HybridKeywordDriven\\AutoIT\\Suppliercompanylogo.exe");
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//Suppliercompanylogo.exe");
		
		Thread.sleep(3000);
		
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload step1 supplier company logo";
	}
	return "Pass";
}

public String Supplierbrochureupload(){
log("upload supplier company Brochure file in step1 ");


	try
	{
		
		
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//AutoIT//Supplierbrochureupload.exe");
		Thread.sleep(3000);
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload Brochure file in step1";
	}
	return "Pass";
}

public String  CleartextByxpath(String xpathtext){
	log(" Clear text from mentioned field "+ xpathtext);
	try {
		
			
			driver.findElement(By.xpath(OR.getProperty(xpathtext))).clear();
			
		} 

	 catch (Exception e) 
	{
		 return "Fail -unable to Clearfrom fields text  -"+ xpathtext;
		 
	}
	return "Pass";	
	}

public String removeoptionstep1()
{
	log("Removing Step1 REMOVE options ");
	
	String step1_removeinloop1="step1_removeinloop1";
	String step1_removeinloop2="step1_removeinloop2";
	String step1_removeinloop3="step1_removeinloop3";
	String step1_removeinloop4="step1_removeinloop4";
	String step1_removeinloop5="step1_removeinloop5";
	String step1_removeinloop6="step1_removeinloop6";
	String step1_removeinloop7="step1_removeinloop7";
	String step1_removeinloop8="step1_removeinloop8";
	
	try{
		
		for (int z=1;z<9;z++)
		{
			
		if (driver.findElement(By.xpath(OR.getProperty(step1_removeinloop1))).isDisplayed()) 
		{
			
				driver.findElement(By.xpath(OR.getProperty(step1_removeinloop1))).click();
				
		}
		else if(driver.findElement(By.xpath(OR.getProperty(step1_removeinloop2))).isDisplayed())
		{ 
			driver.findElement(By.xpath(OR.getProperty(step1_removeinloop2))).click();
		}
		else if (driver.findElement(By.xpath(OR.getProperty(step1_removeinloop3))).isDisplayed())
		{
			driver.findElement(By.xpath(OR.getProperty(step1_removeinloop3))).click();
		}
		
		else if (driver.findElement(By.xpath(OR.getProperty(step1_removeinloop4))).isDisplayed())
		{
			driver.findElement(By.xpath(OR.getProperty(step1_removeinloop4))).click();
		}
         
		else if (driver.findElement(By.xpath(OR.getProperty(step1_removeinloop5))).isDisplayed())
		{
			driver.findElement(By.xpath(OR.getProperty(step1_removeinloop5))).click();
		}
		else if (driver.findElement(By.xpath(OR.getProperty(step1_removeinloop6))).isDisplayed())
		{
			driver.findElement(By.xpath(OR.getProperty(step1_removeinloop6))).click();
		}
		
		else if (driver.findElement(By.xpath(OR.getProperty(step1_removeinloop7))).isDisplayed())
		{
			driver.findElement(By.xpath(OR.getProperty(step1_removeinloop7))).click();
		}
		else if (driver.findElement(By.xpath(OR.getProperty(step1_removeinloop8))).isDisplayed())
		{
			driver.findElement(By.xpath(OR.getProperty(step1_removeinloop8))).click();
		}
		
		}
		log("Removed Step1 REMOVE options ");
		return "Pass";
		
	}
	
	catch(Exception e){
		return "Fail -unable to REMOVE options in step1-";
	}
}

public String clickbylinktext(String linktext)
{
	
	log("Click on element based on linktext "+ linktext);
	try{
	driver.findElement(By.linkText(OR.getProperty(linktext))).click();
	}
	catch(Exception e)
	{
		return "Fail -Unable to click on -"+linktext;
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
	    	log("Procurement list is matched ");
	    } 
	    else 
	    {
	    log("Differnece in member profile procurement list");
	    	return "Fail -Differnece in member profile procurement list -";
	    }
			
		
	}

	
	catch(Exception e)
	{
	 return "Fail Procurement list in member profile page has difference ";
	}
	return "Pass";
}

public String PMSocialsharing()

{
	String MP_socialsharing1="MP_socialsharing1";
	String MP_socialsharing2="MP_socialsharing2";
	String MP_socialsharing3="MP_socialsharing3";
	String MP_socialsharing4="MP_socialsharing4";
	String MP_socialsharing5="MP_socialsharing5";
	
	log("Opening Member profile social sharing options in Member profile page");
		try{

               driver.findElement(By.xpath(OR.getProperty(MP_socialsharing1))).click();
	           driver.findElement(By.xpath(OR.getProperty(MP_socialsharing2))).click();
               driver.findElement(By.xpath(OR.getProperty(MP_socialsharing3))).click();
               driver.findElement(By.xpath(OR.getProperty(MP_socialsharing4))).click();
               driver.findElement(By.xpath(OR.getProperty(MP_socialsharing5))).click();

                 String parentWindow = driver.getWindowHandle();
	
	        Set<String> handles =  driver.getWindowHandles();
	
	      for(String windowHandle  : handles)
	        {
	            if(!windowHandle.equals(parentWindow))
	                 {
	                       driver.switchTo().window(windowHandle);
	          
	        	             driver.close(); 
	         
	         driver.switchTo().window(parentWindow); //cntrl to parent window
	         log("Social sharing options in Member profile page opened ");
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
	String RP_socialsharing1="RP_socialsharing1";
	String RP_socialsharing2="RP_socialsharing2";
	String RP_socialsharing3="RP_socialsharing3";
	String RP_socialsharing4="RP_socialsharing4";
	String RP_socialsharing5="RP_socialsharing5";
	
	log("Opening social sharing options in Register page");
		try{

               driver.findElement(By.xpath(OR.getProperty(RP_socialsharing1))).click();
	           driver.findElement(By.xpath(OR.getProperty(RP_socialsharing2))).click();
               driver.findElement(By.xpath(OR.getProperty(RP_socialsharing3))).click();
               driver.findElement(By.xpath(OR.getProperty(RP_socialsharing4))).click();
               driver.findElement(By.xpath(OR.getProperty(RP_socialsharing5))).click();

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
	
	      log("Opened social sharing options in Register page");

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
			//System.out.println("Element " +xpathlink + " Is Disabled" );
			log("Element "+xpathlink + xpathlink + " Is Disabled");
		}
		else
		{
			log("Element " +xpathlink + " Is Enabled");
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
			log("Element " +xpathlink + " Is Present" );
			
		}
		else
		{
			log("Element " +xpathlink + " Is not Presene");
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
			log("Element " +xpathlink + " Is Selected " );
			
		}
		else
		{
			log("Element " +xpathlink + " is not Selected");
			return "Fail";
		}
	}catch(Exception e){
		return "Fail - Element is not Selected  -"+xpathlink;
	}
	return "Pass";
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

public String Verifycontentpresent(String xpath, String textmatch)
{
log("Checking for the Content present  " + textmatch+ "in object " + xpath);


	try
	{
		String Pagetext=driver.findElement(By.xpath(OR.getProperty(xpath))).getText().trim();
		if (Pagetext.contains(textmatch.trim()))
				{
			
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

public String selectmultidropdownoptions(){
	
       	String MP_multtidropdown="MP_multtidropdown";
	log(" Select multiple values from select element ");
	try{
		for (int i=1;i<5;i++)
		{
						
			driver.findElement(By.xpath(OR.getProperty(MP_multtidropdown))).click();
			//driver.findElement(By.xpath("//*[@id='subcategory']/option["+i+"]")).click();
			log(" Multiple values from select element is selected ");
							
		}
	}catch(Exception e){
		return "Fail - Element is not Presnet  -";
	}
	return "Pass";
}

public String step1initprocess()
{
	log("unchecking options on step1 ");
	try{
		
		driver.findElement(By.xpath(OR.getProperty("step1_init1"))).click();
		driver.findElement(By.xpath(OR.getProperty("step1_init2"))).click();
        driver.findElement(By.xpath(OR.getProperty("step1_init3"))).click();
        
        log("Uncheked options on step1 ");
		return "Pass";
		
	}catch(Exception e){
		return "Fail -unable to uncheck in step1-";
	}
}

public String verifybookmark_profile(String profileName){
	
	log("Executing Verifyboomkark");
// Check bookmarked supplier active /inactive  in saved supplier of supplier and buyer
	try{
		
		//String statusofprofile= bookmarkstatus(profileName);
		
		//if(statusofprofile.equals("Pass")){
			log("Checking for existing supplier in saved supplier");
		String currentpagenum = driver.findElement(By.xpath("//input[@id='pageNumber']")).getAttribute("value").trim();
		 
		 String result1= null;
		
		 String lastpagenum= driver.findElement(By.cssSelector("span[class='recordMetaDiv']")).getText();
		 
		 
		int currentpage =Integer.parseInt(currentpagenum.trim());
		// int currentpage=org.apache.commons.lang3.math.NumberUtils.toInt(currentpagenum, 0);
		 System.out.println("Currenpage"+ currentpagenum);
		 
		 int maxpage= Integer.valueOf(lastpagenum.trim());
		 System.out.println("Maxpage"+ maxpage);
		 
		 result1= SearchProfilePerPage_textview(profileName,currentpage);
		 System.out.println("Result"+ result1);
		 for(int i=0;i<maxpage;i++){
			if((result1.equals("Fail"))){
				  currentpagenum=  driver.findElement(By.xpath("//input[@id='pageNumber']")).getText();
				System.out.println("Currenpage"+ currentpagenum);
				
				 
				 System.out.println("Profile not bookmarked in this page");
		 	
			
				 if(isElementpresent("//div[@class='pageNext active col']/a")){
					
					 
					driver.findElement(By.xpath("//div[@class='pageNext active col']/a")).click();
					Thread.sleep(2000);
					result1= SearchProfilePerPage_textview(profileName,currentpage);
				 }
			
			}
		 
		 }
	//	}//end of if
		/*else{
			bookmarkstatus(profileName);
		}*/
	}catch(Exception e){
		log("Unable to do verify bookmark" + e);
		return "Fail-Unable to do verify bookmark";
	}
	return "Pass";
	
}



public String SearchProfilePerPage_textview(String profileName, int currentpage){
	 String result = null;
	 log("Executing SearchProfilePerPage_textview for profile name--"+profileName);
		try{
			System.out.println("Executing displayResultsPerPage");
		//Getting the suppleir records per page
			List<WebElement> results = driver.findElements(By.xpath("//div[@class='sup-container']"));
			
	    int max= results.size();
	    System.out.println("Max in search method:"+ max);
	    String currentpagenum= driver.findElement(By.xpath("//input[@id='pageNumber']")).getAttribute("value").trim();
	    currentpage =Integer.parseInt(currentpagenum.trim());
		System.out.println("Currenpage in method"+ currentpage);
		
		for(int i=1;i<=max;i++){
		
		String xpa1="//div[@id='v_results']/div[";
		String xpa2="]/div[1]/div[1]/div/h2/span";
		//Printing all supplier profile names per page
			WebElement main =driver.findElement(By.xpath("//div[@id='v_results']/div"));  
			String temp = null;
			//if(main.findElement(By.xpath(xpa1+i+xpa2)).isDisplayed()){
			if(isElementpresent(xpa1+i+xpa2)){
				temp = main.findElement(By.xpath(xpa1+i+xpa2)).getText();
					System.out.println(temp);
					System.out.println("Supplier name: " + temp);
					if(profileName.trim().equals(temp.trim())){
						System.out.println("Bookmarked profile is present in text view mode");
						if(isElementpresent("//img[@title='Remove bookmark']")){
							System.out.println("Bookmared in My saved suppliers");
							bookmarkstatus(profileName);
							result="Pass";
							break;
						}
						else{
							System.out.println("Not Bookmared My saved suppliers");
							result="Pass";
							break;
						}
					}
					else{
						//System.out.println("Bookmarked profile is not present in text view mode");
						result="Fail";
						
						
					}
					
			}
			/*else{
				System.out.println("Element not present as not in textview "+ main.findElement(By.xpath(xpa1+i+xpa2)));
			}*/
		}
		}catch(Exception e){
			System.out.println("Unable to check displayResultsPerPage" + e);
			//return " Fail- Unable to check displayResultsPerPage";
		}
		System.out.println("returning result"+ result);
		return result;
		
	}

public void bookmarkstatus(String profileName){
	
	try{
		log("Checking is profile active/inactive in saved supplier");
		String statusofprof = null;
		String returntemp = null;
		String offlineMsg=null;
		/*if(isElementpresent("//span[@class='col ML5px font11px summary_r']")){
			driver.findElement(By.xpath("//span[@class='col ML5px font11px summary_r']")).click();
		}*/
		
		//checks in text view
		WebElement main =driver.findElement(By.xpath("//div[@id='v_results']/div"));  
		List<WebElement> inactiverecords= driver.findElements(By.xpath("//span[@class='suppNameCard col W520px']"));
		List<WebElement> activerecords=driver.findElements(By.xpath("//span[@class='suppName col W520px']"));
		String tempName = null;
		int max1=inactiverecords.size();
		System.out.println("max1"+ max1);
		for(int i=0;i<max1;i++){
			tempName= inactiverecords.get(i).getText().trim();
			System.out.println("tempName"+tempName);
			System.out.println("ProfileName"+profileName);
			if(tempName.equals(profileName.trim())){
				System.out.println("Comapring names");
				 offlineMsg =inactiverecords.get(i).findElement(By.xpath("//span[@class='suppLink col W500px']")).getText().trim();
				statusofprof= "NOT ACTIVE";
				System.out.println("Status of profile is "+profileName+"---"+ statusofprof);
				returntemp="Fail";
				
		}
			break;
		}
		int max2=activerecords.size();
		System.out.println("max2"+ max2);
			for(int j=0;j<max2;j++){
				tempName= activerecords.get(j).getText().trim();
				System.out.println("tempName"+tempName);
				System.out.println("ProfileName"+profileName);
				if(tempName.equals(profileName.trim())){
					 WebElement temp =activerecords.get(j).findElement(By.xpath("//div[@class='t-row tall-lines row MTB']"));
					 if(temp.isDisplayed()){
						 statusofprof= "Active and VISIBLE";
						 System.out.println("Status of profile is "+profileName+"---"+ statusofprof);
						 returntemp="Pass";
						
					 }
				}
				 break;
			}
		
		
	}catch(Exception e){
		log("Profile status is not able to check"+ e);
		
		
	}
	
	
	
	
}

public String gotofindasupplierByrole(String Role){
	
	try{
		
		if((Role.equals("Systemadmin"))||(Role.equals("Buyeradmin"))||(Role.equals("Buyer"))){
			driver.findElement(By.xpath(OR.getProperty("mysupplierconnection_xpath"))).click();
			driver.findElement(By.xpath(OR.getProperty("findasupplier_xpath"))).click();
			driver.findElement(By.xpath(OR.getProperty("savedsupplier_xpath"))).click();
			driver.findElement(By.cssSelector(OR.getProperty("inProgress_css"))).click();
			mouseHoverandClick("summaryview_xpath");

		}
		
		if((Role.equals("Supplieradmin"))|| (Role.equals("Supplieremployee"))){
			driver.findElement(By.xpath(OR.getProperty("smallbizsupplier_xpath"))).click();
		}
		
	}catch(Exception e){
		log("Unable to do gotofindasupplierByrole"+ e);
		return "Fail- gotofindasupplierByrole";
	}
	return "Pass";
	
}

public String dashboard_bookmarkcheck(String ProfileName){
	try{
		driver.findElement(By.xpath("//*[@id='leadspace-nav']/ul/li[2]/a")).click();
		String profilename= ProfileName.trim();
		 if(isElementpresent("//select[@id='favorites']")){
			 System.out.println("Checking bookmarked/unbookmarked names");
			 WebElement favorites = driver.findElement(By.xpath("//select[@id='favorites']"));
			 List<WebElement> favoptions = favorites.findElements(By.tagName("option"));
			 	int max= favoptions.size();
								System.out.println(max);
								if(max>1){
									System.out.println("Favorite has list of bookmarked profiles");
									
									for(int i=0;i<max;i++){
										int j=0;
										String profilename_fav = favoptions.get(j+i).getText().trim();
										if(profilename_fav.equals(profilename)){
											System.out.println("Bookamred profilename is appearing in Dashboard Favorite list");
											Select fav_list = new Select(favorites);
											fav_list.selectByVisibleText(profilename.trim());
							
									
											if(isElementpresent("//img[@title='Remove bookmark']")){
												System.out.println("Bookmared in dashboard");
												break;
											}
										}
									
									else{
										System.out.println("Not Bookmared in dashboard");
									
									}
										
							 }
								}else{
								 System.out.println("Favorite has no bookmarked profiles hence shows default message");
								 String profilename_fav = favoptions.get(1).getText().trim();
								 System.out.println(profilename_fav);
							 }
								
			}
		 else{
			 System.out.println("Logged in might be as system admin so it won't appear in dashboard");
		 }
		 
	}catch(Exception e){
		log("Unable to do dashboard_bookmarkcheck--"+ e);
		return "Fail--Unable to do dashboard_bookmarkcheck";
	}
	return "Pass";
}

public String detailpage_bookmarkcheck(String ProfileName){
	
	try{
		//Checking for bookmarked supplier in my saved supplier
		 
		 driver.findElement(By.xpath("//a[@href='/SupplierConnection/byr/FSfind.action?srview=summary']")).click();
		 driver.findElement(By.xpath("//a[@href='/SupplierConnection/byr/FSfindBM.action?supInProgress=false']")).click();
		 driver.findElement(By.xpath("//div[@id='v_results']/div[1]/div[2]/div/a/span/span[2]")).click();
		 Thread.sleep(2000);
		String currentpagenum = driver.findElement(By.xpath("//input[@id='pageNumber']")).getAttribute("value").trim();
		 
		 String result1= null;
		
		 String lastpagenum= driver.findElement(By.cssSelector("span[class='recordMetaDiv']")).getText();
		 
		 
		int currentpage =Integer.valueOf(currentpagenum.trim());
		 //int currentpage=NumberUtils.toInt(currentpagenum, 0);
		 System.out.println("Currenpage"+ currentpagenum);
		 
		 int maxpage= Integer.valueOf(lastpagenum.trim());
		 System.out.println("Maxpage"+ maxpage);
		 
		 result1= SearchProfilePerPage_textview(ProfileName,currentpage);
		 System.out.println("Result"+ result1);
		 for(int i=0;i<maxpage;i++){
			if((result1.equals("Fail"))){
				  currentpagenum=  driver.findElement(By.xpath("//input[@id='pageNumber']")).getText();
				System.out.println("Currenpage"+ currentpagenum);
				
				 
				 System.out.println("Profile not bookmarked in this page");
		 	
			
				 if(isElementpresent("//div[@class='pageNext active col']/a")){
					
					 
					driver.findElement(By.xpath("//div[@class='pageNext active col']/a")).click();
					Thread.sleep(2000);
					result1= SearchProfilePerPage_textview(ProfileName,currentpage);
				 }
			
		 }
		 
		
		 }
		 
		
	}catch(Exception e){
		log("Unable to do detailpage_bookmarkcheck--"+ e);
		return "Fail--detailpage_bookmarkcheck";
	}
	return "Pass";
}

public String facet_selection(){
	try{
		log("Executing facet_selection for buyer portal");
		String filename= OR.getProperty("facet_selection");
		Xls_Reader xlsreader = new Xls_Reader(filename);
		
		//Xls_Reader.setExcelFile(filename, "datadriven");
	 	
	 	int testCaseStartRowNum= Xls_Reader.getcurrentRowNumofTCID("SC_factselection","datadriven", xlsreader);
	 			
	 	int rowStartRowNum=testCaseStartRowNum+2;
	 	System.out.println("rowStartRowNum"+ rowStartRowNum);
		int rows=0;
		int maxcols= xlsreader.getColumnCountofaRow("datadriven",rowStartRowNum, xlsreader);
		System.out.println("MAxCols"+ maxcols);
		int cols=0;
		System.out.println("Cols"+ cols);
	 	
	 	//Iteration to fetch data for date entry directly from sheet
		while(!xlsreader.getCellData("datadriven", cols, (rowStartRowNum+rows)).equals("")){
			
			driver.findElement(By.xpath(OR.getProperty("findasupplier_xpath"))).click();
			driver.findElement(By.cssSelector(OR.getProperty("inProgress_css"))).click();
			driver.findElement(By.xpath(OR.getProperty("ExpandAll"))).click();
			List<WebElement> moreList= driver.findElements(By.xpath(OR.getProperty("morelinks")));
			int max=moreList.size();
			System.out.println("max"+ max);
			for(int i=0;i<moreList.size();i++)
			{
				//List<WebElement> moreList1= driver.findElements(By.xpath(OR.getProperty("morelinks")));
				driver.findElement(By.xpath(OR.getProperty("morelinks"))).click();
			}
			
			for(cols=0;cols<maxcols;cols++){
				String temp= xlsreader.getCellData("datadriven", cols, (rowStartRowNum+rows)).trim(); 
				
				if(temp.contains("NAICS")){
					String[] code=temp.split(",");
					String NAICScode= code[1];
					System.out.println("NAICS CODE"+ NAICScode);
					driver.findElement(By.xpath(OR.getProperty("naics_checkbox"))).click();
					driver.findElement(By.xpath(OR.getProperty("findsupp_searchbox"))).sendKeys(NAICScode);
					driver.findElement(By.xpath(OR.getProperty("findasupp_searchlink"))).click();
				}
				else if(isElementpresent(temp)){
					driver.findElement(By.xpath(temp)).click();
						//to check more link inside parent category ex: inside yes of certification
					List<WebElement> moreList1= driver.findElements(By.xpath(OR.getProperty("morelinks")));
					int max1=moreList.size();
					System.out.println("max1"+ max);
					for(int i=0;i<moreList1.size();i++)
					{
						//List<WebElement> moreList1= driver.findElements(By.xpath(OR.getProperty("morelinks")));
						driver.findElement(By.xpath(OR.getProperty("morelinks"))).click();
					}
				}
			}
			
			rows++;
			cols=0;
			
			facet_deletiononebyone();
		}
		
	}catch(Exception e){
		if(e.getMessage().contains("NoSuchElementException")){
			if(isElementpresent("//div[@class='row reg_header']/span")){
				String dataRefreshmsg= driver.findElement(By.xpath("//div[@class='row reg_header']/span")).getText();
				log("Data Refresh time--"+dataRefreshmsg );
				WebDriverWait wait = new WebDriverWait(driver, 15);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='suppHeader']")));
				driver.navigate().refresh();
				facet_selection();
				 
				
			}
		}
		log("Unable to do facet_selection--"+e);
		return "Fail--facet_selection";
	}
	return "Pass";
}

public String facet_deletiononebyone(){
	try{
		log("Executing facet_deletiononebyone");
		
		while(isElementpresent(OR.getProperty("removeLinks_xpath"))){
			//if(isElementpresent(OR.getProperty("removeLinks_xpath"))){
				//List<WebElement> removeLinks= driver.findElements(By.xpath(OR.getProperty("removeLinks_xpath")));
				//for(int i=removeLinks.size();i>0;i++){
					driver.findElement(By.xpath(OR.getProperty("removeLinks_xpath"))).click();
			//}
		//}
	}
		while(isElementpresent(OR.getProperty("removelink2"))){
			driver.findElement(By.xpath(OR.getProperty("removelink2"))).click();
			
		}
		
	}catch(Exception e){
		if(e.equals("NoSuchElementException")){
			if(isElementpresent("//div[@class='row reg_header']/span")){
				String dataRefreshmsg= driver.findElement(By.xpath("//div[@class='row reg_header']/span")).getText();
				log("Data Refresh time--"+dataRefreshmsg );
				driver.navigate().refresh();
				facet_selection();
				 facet_deletiononebyone();
				
			}
		}
		log("Unable to do deletion of facets one by one --"+ e);
		return "Fail---unable to do facet_deletiononebyone";
	}
	return "Pass";
}

public String NAICS_count_DBcheck(String NAICS_code){
	try{
		//Always make test account ENABLE to execute this query
		log("Executing DB query to check NAICS total search count");
		DBUtil connect= new DBUtil();
		connect.getConnection();
		
		ResultSet rs= connect.executeQuery("select SUPP_NM from pes.tsmr where invisible_supp_timestamp is null and smr_id in (SELECT smr_id FROM PES.SAMDATA_NEW where NAICS_CODES LIKE '%"+NAICS_code+"%')");
		int count = 0;
			if (!rs.next()) {                            //if rs.next() returns false
            //then there are no rows.
				log("No records found");

			}
			else {
					do {
							// Get data from the current row and use it
							String DBsupplierName = rs.getString("SUPP_NM");
							//Adding names to Arraylist to compare the results
							
							DBSuppliers.add(DBsupplierName);

							count++;
						} while (rs.next());
					
					
					}
		 
			
		log("Total number of Result Row count"+ count);
		connect.closeConn();
		
		
		  //compare_NAICSresults(DBSuppliers,suppliernames);
		
	}catch(Exception e){
		log("Unable to do NAICSDBcheck---"+ e);
		return "Fail--Unable to do NAICSDBcheck";
	}
	return "Pass";
}

public String compare_NAICSresults(ArrayList<String> DBSuppliers, ArrayList<String> suppliernames,String naics_code){
	try{
		log("Executing compare_NAICSresults");
	
	int max= suppliernames.size();
	
	for(int i=0;i<max;i++){
		 String appname=suppliernames.get(i);
		 String dbname=DBSuppliers.get(i);
		 if(dbname.equals(appname)){
			  log("DB NAICS names and Application NAICS search names are matching for name--"+ appname);
			  if(isElementpresent("//a[contains(text(),'"+appname+"')]")){
			  driver.findElement(By.xpath("//a[contains(text(),'"+appname+"')]")).click();
			  waitfor();
			  }
			  driver.findElement(By.xpath(OR.getProperty("detailpage_sam_tab_xpath"))).click();
			  List samTable= driver.findElements(By.xpath(OR.getProperty("samtable_indetailpage")));
			 int maxcode= samTable.size();
			 
			 for(int j=4;j<=maxcode;j++){
				 String temp_naicscode= driver.findElement(By.xpath("//table[@class='ccr-tbl']/tbody/tr["+j+"]/td[1]")).getText().trim();
				 waitfor();
				 
				 if(temp_naicscode.equals(naics_code)){
					 log("NAICS CODE"+naics_code+" exist in SAM data of the supplier detail page");
				 }
 
			 }
			 driver.findElement(By.xpath(OR.getProperty("backto_Searchresult"))).click();
			 }
		    else{
		    	log("Names are not matching"+ appname);
		    }
	}
	suppliernames.clear();
	DBSuppliers.clear();
	}catch(Exception e){
		log("Unable to do compare_NAICSresults--"+ e);
		return "Fail--Unable to do compare_NAICSresults";
	}
	return "Pass";
}

public String downloadfile()
	{
          log("Downloading file  ");


	try
	{
		Robot robot = new Robot();

		// A short pause, just to be sure that OK is selected
		Thread.sleep(3000);
		
		robot.keyPress(java.awt.event.KeyEvent.VK_DOWN);

		robot.keyPress(java.awt.event.KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		log("File download completes");
			
		
	}
	catch(Exception e)
	{
	 return "Fail to upload";
	}
	return "Pass";
        }


}






