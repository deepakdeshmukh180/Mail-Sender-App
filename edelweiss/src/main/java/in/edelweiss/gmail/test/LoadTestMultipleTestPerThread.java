package in.edelweiss.gmail.test;

//--
import java.text.SimpleDateFormat;
//--
import java.util.Date;
//--
import java.util.concurrent.TimeUnit;
//--
import org.openqa.selenium.By;
//--
import org.openqa.selenium.WebDriver;
//--
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//--
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//--
import org.testng.Assert;
//--
import org.testng.annotations.AfterTest;
//--
import org.testng.annotations.BeforeTest;
//--
import org.testng.annotations.Test;

public class LoadTestMultipleTestPerThread {

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

	@Test(invocationCount = 10, threadPoolSize = 5)
	public void testGoogleSearch() {

		System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		driver.get("https://www.gmail.com");
		driver.findElement(By.id("identifierId")).sendKeys("testing20201011@gmail.com");
		driver.findElement(By.xpath("//*[@id='identifierNext']")).click();
		driver.findElement(By.name("password")).sendKeys("dell@123");
		driver.findElement(By.xpath("//*[@id='passwordNext']")).click();
		// click on the profile icon
		driver.findElement(By.xpath("//*[@id='gb']/div[2]/div[3]/div/div[2]/div/a")).click();
		// click on the sign out
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement Test = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Sign out')]")));
		Test.click();
		driver.quit();
	}
}