package MusalaSoft_Project.MusalaSoft_Assignment;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import MusalaSoft_Project.MusalaSoft_Assignment.Utilities;

public class AssignmentTests {

	public ExtentReports extent;
	public ExtentTest logger;
	public WebDriver driver;
	WebDriverWait wait;
	Utilities ut=new Utilities();
	
	@BeforeTest
	public void BeforeTests() {
		extent = new ExtentReports(".\\ExtentReport.html", true);
		logger = extent.startTest("Test Name","Automation Scenarios for Musula Soft Website");
	}

	@BeforeMethod
	public void BaseTest() throws FileNotFoundException, IOException {
        
		logger.log(LogStatus.INFO, "Launching the browser ...");
		if(ut.ReadDatafromPropFile("browser").contains("Chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();

		}else if (ut.ReadDatafromPropFile("browser").contains("FF")){
			System.setProperty("webdriver.gecko.driver", ".\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();	
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(ut.ReadDatafromPropFile("url"));
	}

	@Test
	public void FirstTest() throws IOException {

		logger.log(LogStatus.INFO, "Starting 'FirstTest' testcase ...");
		driver.findElement(By.xpath("//a[@href='#contact_form_pop']")).click();
		driver.findElement(By.xpath("//input[@name='your-name']")).sendKeys("Test User");
		driver.findElement(By.xpath("//input[@name='your-email']")).sendKeys("test@test");
		driver.findElement(By.xpath("//input[@name='mobile-number']")).sendKeys("23232323");
		driver.findElement(By.xpath("//input[@name='your-subject']")).sendKeys("Test Subject");
		driver.findElement(By.xpath("//textarea[@name='your-message']")).sendKeys("Test Message");
		driver.findElement(By.xpath("//input[contains(@class,'btn-cf-submit')]")).click();
		wait= new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='your-email']/following-sibling::span")));
		Assert.assertEquals(driver.findElement(By.xpath("//input[@name='your-email']/following-sibling::span")).getText(), "The e-mail address entered is invalid.");
		for(int i=1;i<=ut.NumberofRows();i++) {
			driver.findElement(By.xpath("//input[@name='your-email']")).clear();
			driver.findElement(By.xpath("//input[@name='your-email']")).sendKeys(ut.ReadDatafrimExcelFile(i,0));
			driver.findElement(By.xpath("//input[contains(@class,'btn-cf-submit')]")).click();
			wait= new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='your-email']/following-sibling::span")));
			Assert.assertEquals(driver.findElement(By.xpath("//input[@name='your-email']/following-sibling::span")).getText(), "The e-mail address entered is invalid.");
		}
		logger.log(LogStatus.INFO, "Finishing 'FirstTest' testcase ...");
	}

	@Test
	public void SecondTest() {

		logger.log(LogStatus.INFO, "Starting 'SecondTest' testcase ...");
		driver.findElement(By.xpath("//div[@id='menu']/ul/li[1]/a")).click();
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.musala.com/company/");
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'Leadership')]")).isDisplayed());
		driver.findElement(By.xpath("//a[@href='https://www.facebook.com/MusalaSoft?fref=ts']")).click();
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		Assert.assertTrue(driver.getCurrentUrl().contains("https://web.facebook.com/MusalaSoft?"));
		Assert.assertTrue(driver.findElement(By.xpath("//a[@href='/116922188328473/photos/3926723730681614/']")).isDisplayed());
		logger.log(LogStatus.INFO, "Finishing 'SecondTest' testcase ...");
	}

	@Test
	public void ThirdTest() {

		logger.log(LogStatus.INFO, "Starting 'ThirdTest' testcase ...");
		driver.findElement(By.xpath("//div[@id='menu']/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//button[contains(.,'Check our open positions')]")).click();
		Assert.assertEquals(driver.getCurrentUrl(),"https://www.musala.com/careers/join-us/");
		WebElement select=driver.findElement(By.xpath("//select[@id='get_location']"));
		Select selectelement=new Select(select);
		selectelement.selectByVisibleText("All Locations");
		driver.findElement(By.xpath("//a[@href='https://www.musala.com/job/experienced-automation-qa-engineer/']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'General description')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'Requirements')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'Responsibilities')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//h2[contains(.,'What we offer')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//input[contains(@class,'btn-join-us')]")).isDisplayed());
		driver.findElement(By.xpath("//input[contains(@class,'btn-join-us')]")).click();
		driver.findElement(By.xpath("//input[@name='your-email']")).sendKeys("test@test");
		driver.findElement(By.xpath("//input[@id='uploadtextfield']")).sendKeys(".\\Mohamed Ahmed Abdel Hameed Zahran.pdf");
		driver.findElement(By.xpath("//input[@name='linkedin']")).sendKeys("https://www.linkedin.com/in/mohamed-zahran-2237a817/");
		driver.findElement(By.xpath("//textarea[@name='your-message']")).sendKeys("My Test message");
		driver.findElement(By.xpath("//input[contains(@class,'btn-cf-submit')]")).click();
		wait= new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Close')]")));
		driver.findElement(By.xpath("//button[contains(.,'Close')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//input[@name='your-name']/following-sibling::span")).getText(), "The field is required.");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@name='your-email']/following-sibling::span")).getText(), "The e-mail address entered is invalid.");
		Assert.assertEquals(driver.findElement(By.xpath("//input[@name='mobile-number']/following-sibling::span")).getText(), "The field is required.");
		logger.log(LogStatus.INFO, "Finishing 'ThirdTest' testcase ...");
	}

	@Test
	public void FourthTest() {

		logger.log(LogStatus.INFO, "Starting 'FourthTest' testcase ...");
		driver.findElement(By.xpath("//div[@id='menu']/ul/li[5]/a")).click();
		driver.findElement(By.xpath("//button[contains(.,'Check our open positions')]")).click();
		Assert.assertEquals(driver.getCurrentUrl(),"https://www.musala.com/careers/join-us/");
		WebElement select=driver.findElement(By.xpath("//select[@id='get_location']"));
		Select selectelement=new Select(select);
		selectelement.selectByVisibleText("Sofia");
		WebElement select1=driver.findElement(By.xpath("//select[@id='get_location']"));
		Select selectelement1=new Select(select1);
		System.out.println(selectelement1.getFirstSelectedOption().getText());
		int countofpositions=driver.findElements(By.xpath("//div[contains(@class,'card-container')]/a")).size();
		for (int i=1;i<=countofpositions;i++) {
			System.out.println("Position : " + driver.findElement(By.xpath("(//h2[contains(@class,'card-jobsHot__title')])["+i+"]")).getText());
			System.out.println("More Info : " + driver.findElement(By.xpath("(//div[contains(@class,'card-container')]/a)["+i+"]")).getAttribute("href"));
		}	

		WebElement select2=driver.findElement(By.xpath("//select[@id='get_location']"));
		Select selectelement2=new Select(select2);
		selectelement2.selectByVisibleText("Skopje");
		int countofpositions1=driver.findElements(By.xpath("//div[contains(@class,'card-container')]/a")).size();
		WebElement select3=driver.findElement(By.xpath("//select[@id='get_location']"));
		Select selectelement3=new Select(select3);
		System.out.println(selectelement3.getFirstSelectedOption().getText());
		for (int i=1;i<=countofpositions1;i++) {
			System.out.println("Position : " + driver.findElement(By.xpath("(//h2[contains(@class,'card-jobsHot__title')])["+i+"]")).getText());
			System.out.println("More Info : " + driver.findElement(By.xpath("(//div[contains(@class,'card-container')]/a)["+i+"]")).getAttribute("href"));
		}
		logger.log(LogStatus.INFO, "Finishing 'FourthTest' testcase ...");
	}

	@AfterMethod
	public void TearDownTest(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE)
        {
            logger.log(LogStatus.FAIL," Test case FAILED");
        } 
        else if (result.getStatus() == ITestResult.SUCCESS)
        {
            logger.log(LogStatus.PASS, " Test Case PASSED");
        } 
		
		driver.quit();
	}
	
	@AfterTest
    public void ExitExtentReport() throws Exception {
        extent.flush();
    }
}
