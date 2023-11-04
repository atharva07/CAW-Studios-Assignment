package com.uiautomation;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class JsonAutomation {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//System.setProperty("webdriver.chrome.driver", "D:\\server files\\chromedriver-win64\\chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
		driver.manage().window().maximize();
		Thread.sleep(5000);
		driver.close();
	}
}