package com.supplierconnection.testcases;

//import org.apache.bcel.generic.Select;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.seleniumhq.jetty7.util.log.Log;

public class Test 
{
public static void main (String arge[]) throws Exception
	
	{
	
       WebDriver Driver= new FirefoxDriver();
      // String expectedMsg = "ADDED AS SUPPLIER";
	
	Driver.get("https://100x02.lexington.ibm.com/SupplierConnection/byr/FSfind.action?srview=summary");

	  Driver.manage().window().maximize();

	Driver.findElement(By.xpath("//input[@id='signin_email']")).sendKeys("raniranj@in.ibm.com");
	Driver.findElement(By.xpath("//input[@id='signin_password']")).sendKeys("passw0rd");
	Driver.findElement(By.xpath("//input[@id='signin_button']")).click();




List<WebElement> allLinks = Driver.findElements(By.xpath("//li[@class='row']"));
 
 ArrayList<String> a= new ArrayList<String>();
 
	
 	for(int i=0;i<allLinks.size();i++){
 		String l = allLinks.get(i).getText();
	    a.add(l);
	} 


 	System.out.println("Actual Arrray Lis is \n" +a);
	
	ArrayList<String> AV= new ArrayList<String>();
	
	AV.add("Industry");
	AV.add("Primary Commodity / Category");
	AV.add("Country");
	AV.add("Office Location");
	AV.add("Area Served");
	AV.add("Company Size");
	AV.add("Annual Revenue");
	AV.add("Company Age");
	AV.add("Certifications");
	AV.add("Diverse Status");
	AV.add("Requirements");
	AV.add("Market Served");
	AV.add("Current Federal Contracts");
	AV.add("Prior Federal Experience");
	AV.add("Security Clearances");
	AV.add("Confirmed suppliers of Buying Members");
	
	System.out.println("AV lis is " +AV);

	Collections.sort(a);
	Collections.sort(AV);
	
	if (a.equals(AV))
	{
		System.out.println("Matched ");
	}
	else
	{
		System.out.println("Not matched");
	}
	
	
	

	
	
	
	
	

	
	Driver.findElement(By.xpath("//a[contains(text(),'Expand All')]")).click();
	
	for (int k=1;k<4;k++)
	{
		Driver.findElement(By.xpath("//span[contains(text(),'More...')]")).click();
	}


	List<WebElement> allLinks1 = Driver.findElements(By.xpath("//span[@class='refinementName']"));
	 Iterator<WebElement> i1 = allLinks1.iterator();
		
		while(i1.hasNext()) {
		    WebElement row = i1.next();
		    System.out.println(row.getText());
		}
	
	

		
		
		
		
		
		
		


	/*Driver.findElement(By.xpath("//*[@id='masthead-options']/div[2]/div/div[1]/ul/li/a")).click();
	Driver.findElement(By.linkText("Profile Managers")).click();
	*/
	
	//Driver.findElement(By.xpath("//*[@id='col']/div[2]/div[1]/input")).click();
   // Thread.sleep(3000);
//Driver.findElement(By.xpath("//*[@id='col']/div[2]/div[3]/div[3]/input[2]")).click();
//Thread.sleep(3000);
//String actuallogintime=Driver.findElement(By.xpath("//span[contains(text(),'rag.h.unandann6@gmail.com') and @class='row H4']/following-sibling::span[@class='row subscript']")).getText().trim();

/*String comparewithactual=actuallogintime.replace("last login:","");

System.out.println("Actual  : " +  comparewithactual );


Class.forName("com.ibm.db2.jcc.DB2Driver");
Connection con = DriverManager.getConnection("jdbc:db2:suppconndev.supconcognos.com:50010/SCDEVDB", "db2inst2", "dbpass4suppl13r");
Statement s=con.createStatement();
ResultSet rs=s.executeQuery("select last_login from pes.user_login where username='rag.h.unandann6@gmail.com");


//SimpleDateFormat formatter = new SimpleDateFormat("MMM,dd yyyy hh:mm");
//String cur = formatter.format(date2); 
System.out.println("result " + rs.getString(1));
con.close();
*/

 
  
/*Driver.findElement(By.xpath("//*[@id='removeButton']")).click();
Thread.sleep(3000);
    WebElement msg = Driver.findElement(By.xpath("//div[contains(text(),'ADDED AS SUPPLIER')]"));
    
    String msgDisp = msg.getText();
    
    if(expectedMsg.contains(msgDisp)){
    	System.out.println("Pass");
    }else{
    	System.out.println("False");
    }
    */
    
    
 
    
	
	//Driver.findElement(By.xpath("//*[@id='Autocomplete_f9960']/div[1]")).click();

	/*//String Expected =
	
	WebDriver Driver= new FirefoxDriver();
	
	
	Driver.get("https://100x02.lexington.ibm.com/SupplierConnection/registration.action");
	
	Select dropdown = new Select(Driver.findElement(By.xpath("//*[@id='selectreason']")));
	dropdown.selectByVisibleText("Member Company: I am looking to BUY products/services");
	
	Select category1 = new Select(Driver.findElement(By.xpath("html/body/div[2]/div[1]/div[4]/div/div[2]/div[3]/fieldset/form/div[27]/div[4]/div[3]/div[1]/div[2]/select")));
	category1.selectByVisibleText("Facilities Support");
	
	Select category2 = new Select(Driver.findElement(By.xpath("html/body/div[2]/div[1]/div[4]/div/div[2]/div[3]/fieldset/form/div[27]/div[4]/div[3]/div[2]/div[2]/select")));
	//category2.selectByVisibleText("Chemicals");
	
	//Driver.findElement(By.xpath("//*[@id='divclk2continue']/img"))
	
	
	for (int i=1;i<3;i++)
	{
		System.out.println("inside loop" + i);
		
		Driver.findElement(By.xpath("//*[@id='subcategory']/option["+i+"]")).click();
			System.out.println("//*[@id='subcategory']/option["+i+"]");
			
			
	}
			*/
			
	
	
	  
     
	
	
	//Log.info()
	//Driver.get("https://www.supplier-connection.net/SupplierConnection/mad/MPviewMemberProfileData.action?cid=325");
	/*Driver.manage().window().maximize();
	Driver.findElement(By.xpath("//a[@class='col reg_btn_links MT10px']")).click();*/
	//Driver.findElements(By.xpath(""))
	
/*	Driver.findElement(By.xpath("//input[@id='signin_email']")).sendKeys("raniranj@in.ibm.com");
	Driver.findElement(By.xpath("//input[@id='signin_password']")).sendKeys("passw0rd");
	Driver.findElement(By.xpath("//input[@id='signin_button']")).click();
	Thread.sleep(2000);*/
	
	//Driver.findElement(By.xpath("//input[@id='upload_logo']")).click();
	
	//Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\Framework\\AutoIT\\uploadmemberprofile.exe");
	
	/*String[] fileformat={"Supplierbrochureupload.exe","Suppliercompanylogo.exe","uploadmemberprofile.exe"};
	
	for (int i=0; i<fileformat.length;i++)
	{
		
		Driver.findElement(By.xpath("//input[@id='upload_logo']")).click();	
		Runtime.getRuntime().exec("C:\\raghunandhan\\AutomationSelenium\\Framework\\AutoIT\\"+fileformat[i]);
		Thread.sleep(4000);
	}*/
	
	


/*	
	Select dropdown3 = new Select(Driver.findElement(By.id("procurements")));	
	int size = dropdown3.getOptions().size();
	List<WebElement> allOptions = dropdown3.getOptions();
	System.out.println("Size is -"+size);
	// Configure the Action
			Actions action = new Actions(Driver);

			// To click on the element
			//action.moveToElement(element).click().perform();
	for(int i1=0;i1<size;i1++)
	{
		
		//dropdown3.deselectByIndex(i1);
	
		allOptions.get(i1).click();
		action.release();
		Thread.sleep(1500);

	} 
	*/
	//Driver.findElement(By.xpath("//input[@id='preview']")).click();
	
	/*
	Driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[1]/a")).click();
	Driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[2]/a")).click();
    Driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[4]/a")).click();
    Driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[5]/a")).click();
    Driver.findElement(By.xpath("html/body/div[4]/div/div/div/div[2]/ul/li[3]/a")).click();
	
		
	//String match="Buyer Company Selenium Test1";
	
	String parentWindow = Driver.getWindowHandle();
	
	Set<String> handles =  Driver.getWindowHandles();
	
	   for(String windowHandle  : handles)
	       {
	       if(!windowHandle.equals(parentWindow))
	          {
	          Driver.switchTo().window(windowHandle);
	          
	          if (match.equals(Driver.findElement(By.xpath(".//*[@id='main-content']/div/div[1]/font")).getText().trim()))
	      	{
	      		System.out.println("Preview Page opened suceefully");
	      		
	      	}
	      	
	      	else
	      	{
	      		System.out.println("Problem while opening Preview Page");
	      	}
	      	                 
	          
	        // <!--Perform your operation here for new window-->
	          
	         Driver.close(); //closing child window
	         
	         Driver.switchTo().window(parentWindow); //cntrl to parent window
	         
	          }
	       }
	
	Driver.findElement(By.xpath("//input[@id='savePublish']")).click();*/
	
	
	
	
	

	
	/*Select dropdown3 = new Select(Driver.findElement(By.id("procurements")));	
	int size = dropdown3.getOptions().size();
	List<WebElement> allOptions = dropdown3.getOptions();
	System.out.println("Size is -"+size);
	
	for(int i1=0;i1<size;i1++)
	{
		//dropdown3.deselectByIndex(i1);
		allOptions.get(i1).click();
		Thread.sleep(1500);
		dropdown3.deselectByIndex(i1);
		Thread.sleep(1500);

	}*/
	
	/*Driver.findElement(By.xpath("//input[@id='removeProcurement']")).click();
	System.out.println("Remove all");*/
	/*
	for (int i=1;i<10;i++)
	{
		System.out.println("inside loop" + i);
		if (Driver.findElement(By.xpath(".//*[@id='procurements']/option["+i+"]")).isEnabled())
			System.out.println(".//*[@id='procurements']/option["+i+"]");
			
			
		{
			
			//Select dropdown = new Select(Driver.findElement(By.xpath("//*[@id='procurements']")));
				Select dropdown3 = new Select(Driver.findElement(By.id("procurements")));	
				int size = dropdown3.getOptions().size();
				
				for(int i1=0;i1<size;i1++)
				{
					dropdown3.selectByIndex(i1);

				}
			
			
					}
		
		
		
	}
	
	*/
	
	
	
	/*Driver.findElement(By.xpath("html/body/div[4]/div/div/div/form/div[33]/div[2]/div[4]/div/a/span")).click();
	Driver.findElement(By.xpath("//input[@id='preview']")).click();*/
	/*ArrayList<String> newTab = new ArrayList<String>(Driver.getWindowHandles());
	Driver.switchTo().window(newTab.get(0));
	System.out.println("switched");*/
	
	/*Driver.findElement(By.xpath(".//*[@id='preview']")).click();
	
	Set<String> allWindowHandles = Driver.getWindowHandles();
	 
	for(String handle : allWindowHandles)
	{
		System.out.println("Switching to window - > " + handle);
		System.out.println("Navigating to Preview");
		Driver.switchTo().window(handle); //Switch to the desired window first and then execute commands using driver
		Driver.get("http://google.com");
	}*/
	
	
    
	
	
	
	
	/*String[] exp = {"Raghu","Food and Beverage Manufacturing","Industrial Manufacturing","Lab Supplies and Equipment","Logistics","Professional, Marketing and Technical Services","Service Parts","Technology","Others"};	
	 WebElement dropdown = Driver.findElement(By.id("procurements")); 
	         Select select = new Select(dropdown);  

	         List<WebElement> options = select.getOptions();  
	         
	          
	          for (int i=0; i<options.size(); i++)
	          
	          {
	              if (options.get(i).getText().equals(exp[i]));
	              
	              {
	                 System.out.println("Matched");
	              } 
	            
	              
	              
	            }
	          */
	
	
	
	
	          }
	          
	         
	         }

