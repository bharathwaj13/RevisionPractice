package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Common {
	
	public Properties prop;
	public ChromeDriver driver;
	public String strUrl,testName,description;
	public ExtentHtmlReporter extent;
	public ExtentReports exTest;
	public ExtentTest test;
	public static int screenShot;
	
	@BeforeClass
	public void getProperty() {
		prop=new Properties();
		try {
			prop.load(new FileInputStream(new File("./config.properties")));
			strUrl = prop.getProperty("URL");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void launch() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		driver =new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(strUrl);
		Actions builder=new Actions(driver);
		builder.sendKeys(Keys.ESCAPE).perform();
		if(driver.findElementByXPath("//a[text()='Login & Signup']").isDisplayed())
		{
			System.out.println("Login is displayed");
			reportStep("pass","Logged in successfuly");
		}
		else
		{
			System.out.println("Login is displayed");
			reportStep("fail","Log in unsuccessfull");
		}
	}
	
	public void reportStep(String status,String description) {
		screenShot++;
		try {
			if(status=="pass")
			{
				FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE),new File("./reports/images/"+screenShot+".jpg"));
				test.pass(description, MediaEntityBuilder.createScreenCaptureFromPath("./reports/images/"+screenShot+".jpg").build());
			}
			else if(status=="fail")
			{
				FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE),new File("./reports/images/"+screenShot+".jpg"));
				test.fail(description, MediaEntityBuilder.createScreenCaptureFromPath("./reports/images/"+screenShot+".jpg").build());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@BeforeMethod
	public void report() {
		extent=new ExtentHtmlReporter("./reports/flipkart.html");
		extent.setAppendExisting(false);
		exTest=new ExtentReports();
		exTest.attachReporter(extent);
		System.out.println("report creation");
		test = exTest.createTest(testName, description);
		}
		
	@AfterSuite
	public void afterReport() {
		
		exTest.flush();
	}

	

}
