package com.uiautomation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
//import org.apache.poi.sl.usermodel.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;

public class MainJsonRunner {

	@Test
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
		try {
			// Initializing the WebDriver
			WebDriverManager.chromedriver().setup();
			ChromeDriver driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

			// Accessing the URL given
			driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
			driver.manage().window().maximize();
			Thread.sleep(10000);

			// clicking on the TABLE DATA link on UI
			driver.findElement(By.xpath("//*[contains(text(), 'Table Data')]")).click();
			Thread.sleep(10000);

			// fetching data from JSON file
			String content = new String(Files.readAllBytes(Paths.get("json\\read.json")));
			JSONArray originalArray = new JSONArray(content);

			// converting the JSON Array into String and copying it to clipboard.
			JSONArray copiedArray = new JSONArray(originalArray.toString());
			System.out.println("Copied JSON Array: " + copiedArray.toString());

			// copying the String into the textbox and checking the results.
			driver.findElement(By.xpath("//textarea[@id='jsondata']")).clear();
			driver.findElement(By.xpath("//textarea[@id='jsondata']")).sendKeys(copiedArray.toString());
			driver.findElement(By.xpath("//button[@id='refreshtable']")).click();

			// Applying assertions on the data we have uploaded into the UI.
			String newContent = new String(Files.readAllBytes(Paths.get("json\\read.json")));
			JSONArray jsonArray = new JSONArray(newContent);

			// Printing the data from the Json file
			for (int i=0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String expectedName = jsonObject.getString("name");
				int expectedAge = jsonObject.getInt("age");
				String expectedGender = jsonObject.getString("gender");
				System.out.println("name: " + expectedName + ", age: " + expectedAge + ", gender: " + expectedGender);
			}
			
			
			// printing the data from UI result
			WebElement table = driver.findElement(By.xpath("//div[@id='tablehere']"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			
			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				for (WebElement cell : cells) {
					System.out.print(cell.getText() + "\t"); 
				}
				System.out.println(); // Move to the next row
			}
			
			// Applying Assertions now, taking data from JSON file and comparing it with the values
			// generated on UI frontend.
			int k = 1;
			for (int i=0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String expectedName = jsonObject.getString("name");
				int expectedAge = jsonObject.getInt("age");
				String expectedGender = jsonObject.getString("gender");
				
				WebElement genderElement = driver.findElement(By.xpath("//table[@id='dynamictable']/tr["+(k+1)+"]/td[1]")); // Assuming dynamic ids
				WebElement nameElement = driver.findElement(By.xpath("//table[@id='dynamictable']/tr["+(k+1)+"]/td[2]")); // Assuming dynamic ids
                WebElement ageElement = driver.findElement(By.xpath("//table[@id='dynamictable']/tr["+(k+1)+"]/td[3]")); // Assuming dynamic ids
                k = k + 1;
                
                String genderFromUI = genderElement.getText();
                String nameFromUI = nameElement.getText();
                int ageFromUI = Integer.parseInt(ageElement.getText());
                
                Assert.assertEquals(nameFromUI, expectedName);
                Assert.assertEquals(ageFromUI, expectedAge);
                Assert.assertEquals(genderFromUI, expectedGender);
                System.out.println("Assert Passed");
			}
			
			
			driver.close();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
