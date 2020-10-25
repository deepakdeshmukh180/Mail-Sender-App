package in.edelweiss.gmail.util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

public class Utility {
	
	
	private final static org.slf4j.Logger log = LoggerFactory.getLogger(Utility.class);

	
	public  String getAttachFile(String fileContent) throws IOException {
		File theDir = new File("D:/files/"+fileContent);
		if (!theDir.exists()){
		    theDir.mkdirs();
		    log.info("--<< Make File Directory :"+theDir);
		}
		String path = "D:/files/"+fileContent+"/"+"attachment.txt";
	    FileOutputStream outputStream = new FileOutputStream(path);
	    DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(outputStream));
	    dataOutStream.writeUTF(fileContent);
	    dataOutStream.close();
		return path;
	}
	
	public static String getScreenshot(WebDriver driver)
	{
		TakesScreenshot ts=(TakesScreenshot) driver;
		
		File src=ts.getScreenshotAs(OutputType.FILE);
		
		String path=System.getProperty("user.dir")+"/Screenshot/"+System.currentTimeMillis()+".png";
		
		File destination=new File(path);
		
		try 
		{
			FileUtils.copyFile(src, destination);
		} catch (IOException e) 
		{
			System.out.println("Capture Failed "+e.getMessage());
		}
		
		return path;
	}

}
