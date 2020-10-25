package in.edelweiss.gmail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import in.edelweiss.gmail.dao.MailData;

import java.util.Properties;

public class GmailPage extends BasePage implements Runnable {

	public GmailPage(WebDriver driver, WebDriverWait wait, Properties properties) {
		super(driver, wait, properties);
	}

	public void composeEmail(MailData mailData) {
		clickCompose();
		enterToField(mailData.getSender());
		enterEmailSubject(mailData.getSubject());
		enterEmailBody();
		attachementFile(mailData.getFilePath());
		
		 Thread t = new Thread(new GmailPage(driver, wait, properties));
	      t.start();
		clickSend();
	}

	private void attachementFile(String filePath) {
		WebElement elem = driver.findElement(By.xpath("//input[@type='file']"));
		elem.sendKeys(filePath);

	}

	private void clickSend() {
		// Click Send
		driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
	}

	private void markLabelAsSocial() {
		// Click More settings
		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='J-JN-M-I J-J5-Ji Xv L3 T-I-ax7 T-I']/div[2]")))
				.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='J-Ph-hFsbo']"))).click();

		// Choose Social label
		wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='J-LC-Jz' and text()='Social']/div[@class='J-LC-Jo J-J5-Ji']"))).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='J-JK-Jz' and text()='Apply']")))
				.click();
	}

	private void enterEmailBody() {
		// Enter email body
		String emailBody = properties.getProperty("email.body");
		driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf tS-tW']")).clear();
		driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf tS-tW']")).sendKeys(emailBody);
	}

	private void enterEmailSubject(String subject) {
		// emailSubject and emailbody to be used in this unit test.
		By subjectBoxIdentifier = By.name("subjectbox");
		wait.until(ExpectedConditions.presenceOfElementLocated(subjectBoxIdentifier));
		WebElement subjectBox = driver.findElement(subjectBoxIdentifier);
		subjectBox.clear();
		subjectBox.sendKeys(subject);
	}

	private void enterToField(String receiver) {
		By toFieldIdentifier = By.name("to");
		wait.until(ExpectedConditions.presenceOfElementLocated(toFieldIdentifier));
		WebElement txtBoxToField = driver.findElement(toFieldIdentifier);
		txtBoxToField.clear();
		txtBoxToField.sendKeys(receiver);
	}

	private void clickCompose() {
		// Click Compose
		By composeElementIdentifier = By.xpath("//*[@role='button' and (.)='Compose']");
		wait.until(ExpectedConditions.presenceOfElementLocated(composeElementIdentifier));
		driver.findElement(composeElementIdentifier).click();
	}

	public void clickSocialTab() {
		// Click Social Tab
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='aKz' and text()='Social']")))
				.click();

		wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//table[@class='F cf zt']/tbody/tr[1]/td[3]/span")));

		try {
			driver.findElement(By.xpath("//table[@class='F cf zt']/tbody/tr[1]/td[3]/span")).click();
		} catch (ElementNotVisibleException e) {
			driver.findElements(By.xpath("//table[@class='F cf zt']/tbody/tr[1]/td[3]/span")).get(1).click();
		}
	}

	public void openReceivedEmail() {
		// Open the received email
		try {
			driver.findElement(By.xpath("//table[@class='F cf zt']/tbody/tr[1]")).click();
		} catch (ElementNotVisibleException e) {
			driver.findElements(By.xpath("//table[@class='F cf zt']/tbody/tr[1]")).get(1).click();
		}

	}

	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
