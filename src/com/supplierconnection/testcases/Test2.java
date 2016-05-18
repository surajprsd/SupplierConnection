package com.supplierconnection.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class Test2

	
{
	
	
	static ArrayList<String> exceldata= new ArrayList<String>();
	
	static ArrayList<String> finallist= new ArrayList<String>();
	
	
	public static void main (String args[]) throws IOException, InterruptedException
	{
		
	
		
			FileInputStream ExcelFile = new FileInputStream("C:\\Users\\IBM_ADMIN\\Desktop\\compare.xlsx");
			
			XSSFWorkbook ExcelWBook = new XSSFWorkbook(ExcelFile);
			
			XSSFSheet ExcelWSheet = ExcelWBook.getSheet("compare");
			
			for (int i=0; i<ExcelWSheet.getLastRowNum()+1;i++)
			{
			
			XSSFCell Cell =ExcelWSheet.getRow(i).getCell(0);
			
			 String CellData = Cell.getStringCellValue();
			 
			 			 
					exceldata.add(CellData);
					System.out.println("Exceldata  " +exceldata);

			}  
		
			WebDriver Driver= new FirefoxDriver();
		      			
			Driver.get("https://100x02.lexington.ibm.com/SupplierConnection/byr/FSfind.action?srview=summary");

			  Driver.manage().window().maximize();

			Driver.findElement(By.xpath("//input[@id='signin_email']")).sendKeys("raniranj@in.ibm.com");
			Driver.findElement(By.xpath("//input[@id='signin_password']")).sendKeys("passw0rd");
			Driver.findElement(By.xpath("//input[@id='signin_button']")).click();
			
			List<WebElement> allLinks = Driver.findElements(By.xpath("//li[@class='row']"));
			 
			 ArrayList<String> a= new ArrayList<String>();
			 
				
			 	for(int i=0;i<allLinks.size();i++){
			 		String l = allLinks.get(i).getText();
			 		System.out.println("actual values "  +l);
				    a.add(l);
				} 
			 	
			 	
			 	/*Collections.sort(a);
				Collections.sort(exceldata);
				
				if (a.equals(exceldata))
				{
					System.out.println("Matched ");
				}
				else
				{
					System.out.println("Not matched");
				}*/
		
				
				
				Driver.findElement(By.xpath("//a[contains(text(),'Expand All')]")).click();
				
				for (int k=1;k<4;k++)
				{
					Driver.findElement(By.xpath("//span[contains(text(),'More...')]")).click();
				}
				
				Thread.sleep(3000);
				
				List <WebElement> list2=Driver.findElements(By.xpath("//span[@class='refinementName']"));
				
				ArrayList<String> b= new ArrayList<String>();
				
							for (int j=0; j<list2.size();j++)
				{
					String childfacets=list2.get(j).getText();
					b.add(childfacets);
					
				}
				
			//Collections.sort(b);
			//Collections.sort(list);
		
							finallist.addAll(a);
							finallist.addAll(b);
							
							for (int f=0;f<finallist.size();f++)
							{
								System.out.println(finallist.get(f));
							}
							
	////Added

							Driver.findElement(By.xpath("//span[contains(text(),'Facilities Support')]")).click();
							
							List<WebElement> grandchild1= Driver.findElements(By.xpath("//ol[@id='faq117']/li/a"));
							
							for (int h=0; h<grandchild1.size();h++)
							{
								String grandchildof1=grandchild1.get(h).getText();
								
								
							}
							
							
				
							
							
	//	Added					
							Collections.sort(finallist);
							Collections.sort(exceldata);
							
							if (finallist.equals(exceldata))
							{
								System.out.println("Matched ");
							}
							else
							{
								System.out.println("Not matched");
							}
	
							
							
							
	
	}
	
	
	
}
