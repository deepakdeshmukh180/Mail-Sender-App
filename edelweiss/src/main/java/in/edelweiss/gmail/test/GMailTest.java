package in.edelweiss.gmail.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import in.edelweiss.gmail.dao.EmailReceiver;
import in.edelweiss.gmail.dao.EmailSender;
import in.edelweiss.gmail.dao.MailData;
import in.edelweiss.gmail.pages.GmailPage;
import in.edelweiss.gmail.pages.LoginPage;
import in.edelweiss.gmail.util.Utility;
import junit.framework.TestCase;

public class GMailTest extends TestCase {
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(TestCase.class);
	private WebDriver driver;
	private WebDriverWait wait;
	public static String mailBody = readResource("msgbody.txt", Charsets.UTF_8);
	private static final String senderList = "1,testing20201011@gmail.com,dell@123&&2,testing20201012@gmail.com,dell@123&&3,testing20201013@gmail.com,dell@123&&4,testing20201014@gmail.com,dell@123&&5,testing20201015@gmail.com,dell@123";
	private static final String receiverList = "1,testing20201011@yahoo.com&&2,testing20201012@yahoo.com&&3,testing20201013@yahoo.com&&4,testing20201014@yahoo.com&&5,testing20201015@yahoo.com";

	ExtentReports extent;
	ExtentTest logger;

	@BeforeMethod
	public void setup() {
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("D:/Reports/gmail-test.html");

		extent = new ExtentReports();

		extent.attachReporter(reporter);

		logger = extent.createTest("GMAIL");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			String temp = Utility.getScreenshot(driver);

			logger.fail(result.getThrowable().getMessage(),
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}

		extent.flush();
		driver.quit();

	}

	@Test
	public void testSendEmail() throws IOException {
		List<EmailReceiver> receiverList = getEmailReceiverList();
		List<EmailSender> senderList = getEmailSenderList();
		Map<String, String> allAttachement = loadAllAttachement(senderList, receiverList);
		List<MailData> data = getMailData(senderList, receiverList, allAttachement);
		for (MailData mailData : data) {
			String msg = mailSenderMainMethod(mailData);
			System.out.println(msg);
		}

	}

	private String mailSenderMainMethod(MailData mailData) {
		Properties properties1 = new Properties();
		properties1.setProperty("username", mailData.getUser());
		properties1.setProperty("password", mailData.getPass());
		properties1.setProperty("email.subject", mailData.getPass());
		properties1.setProperty("email.body", mailBody);
		System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 10000);
		driver.get("https://mail.google.com/");
		LoginPage loginPage = new LoginPage(driver, wait, properties1);
		loginPage.login(mailData.getUser().trim(), mailData.getPass());
		GmailPage gmailPage = new GmailPage(driver, wait, properties1);
		gmailPage.composeEmail(mailData);
		wait = new WebDriverWait(driver, 40);
		driver.findElement(By.xpath("//*[@id='gb']/div[2]/div[3]/div/div[2]/div/a")).click();
		// click on the sign out
		wait = new WebDriverWait(driver, 30);
		WebElement Test = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Sign out')]")));
		Test.click();
		driver.quit();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return "Mail Sent to " + mailData.getSender() + " from " + mailData.getUser();

	}

	private List<MailData> getMailData(List<EmailSender> senderList, List<EmailReceiver> receiverList,
			Map<String, String> attchement) {
		List<MailData> mailData = new LinkedList<>();
		for (EmailSender sender : senderList) {
			for (EmailReceiver receiver : receiverList) {
				MailData maildata = new MailData(sender.getEmailId(), sender.getPassword(), receiver.getEmailId(),
						attchement.get(sender.getSerialId() + receiver.getSerialId()),
						sender.getSerialId() + " DEEPAK DESHMUKH " + receiver.getSerialId());
				mailData.add(maildata);
			}
		}
		return mailData;
	}

	public static String readResource(final String fileName, Charset charset) {
		String str = null;
		try {
			str = Resources.toString(Resources.getResource(fileName), charset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	private List<EmailSender> getEmailSenderList() {
		List<EmailSender> emailSenderList = new LinkedList<EmailSender>();
		List<String> senderListStr = Arrays.asList(senderList.split("&&"));
		senderListStr.stream().forEach(sender -> {
			List<String> senderStr = Arrays.asList(sender.split(","));
			emailSenderList.add(new EmailSender(senderStr.get(0), senderStr.get(1), senderStr.get(2)));
		});
		return emailSenderList;
	}

	private List<EmailReceiver> getEmailReceiverList() {
		List<EmailReceiver> emailReceiverList = new LinkedList<EmailReceiver>();
		List<String> receiverListStr = Arrays.asList(receiverList.split("&&"));
		receiverListStr.stream().forEach(receiver -> {
			List<String> reciverStr = Arrays.asList(receiver.split(","));
			System.out.println("-->>Getting Receiver from Properties File : " + reciverStr.get(1));
			emailReceiverList.add(new EmailReceiver(reciverStr.get(0), reciverStr.get(1)));
		});
		return emailReceiverList;
	}

	private Map<String, String> loadAllAttachement(List<EmailSender> emailSenders, List<EmailReceiver> emailReceivers) {
		Map<String, String> map = new HashMap<>();
		for (EmailSender emailSender : emailSenders) {
			for (EmailReceiver emailReceiver : emailReceivers) {
				String file = null;
				try {
					file = new Utility().getAttachFile(
							emailSender.getSerialId() + " DEEPAK-DESHMUKH " + emailReceiver.getSerialId());
				} catch (IOException e) {
					e.printStackTrace();
				}
				map.put(emailSender.getSerialId() + emailReceiver.getSerialId(), file);
			}
		}
		return map;

	}
}
