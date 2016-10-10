package com.esco.core;

import static org.monte.media.VideoFormatKeys.*;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

public class ScreenRecording 
{
	private static ScreenRecorder screenRecorder;

	// Define file download path
	private String tempFilesFolder_Path = System.getProperties().get("basedir").toString() + "\\storage\\files\\temp_files\\video_recording";
	
    public void startRecording(String TestName)
    {   
 	    File file = new File(tempFilesFolder_Path);
                         
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
                          
        Rectangle captureSize = new Rectangle(0,0, width, height);
                          
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        try 
        {
			screenRecorder = new VideoRecorder(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				   				  new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
				   						 	 CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
				   						 	 DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				   				  new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",FrameRateKey, Rational.valueOf(30)), null, file, TestName);
			
			// Сделано для того, чтобы не делать это действие после каждого 'onConfigurationSuccess' события
			File videoFile = new File(tempFilesFolder_Path + "\\" + get_FileName(TestName));
			if(!videoFile.exists())
			{
				screenRecorder.start();
			}
        } 
        catch (IOException e) 
		{
			e.printStackTrace();
		} 
        catch (AWTException e) 
        {
			e.printStackTrace();
		}
    }

    public void stopRecording()
    {
    	try
    	{
    		ScreenRecorder newScreenRecorder = screenRecorder;
    		newScreenRecorder.stop();
    	}
        catch (IOException e) 
		{
			e.printStackTrace();
		}
    }
    
	public String video_Copy(String TestName)
	{
		String fileToCopy_Name = get_FileName(TestName);
		String timeStamp = new SimpleDateFormat("HH_mm_ss dd.MM.yyyy").format(Calendar.getInstance().getTime());
		String copiedFile_Name = TestName + "  " + timeStamp + ".avi";
		
		String reportPath = System.getProperties().get("basedir").toString() + "\\target\\surefire-reports\\videos\\";
		
		File fileToCopy = new File(tempFilesFolder_Path + "\\" + fileToCopy_Name);
		File copiedFile = new File(reportPath + copiedFile_Name);
		try
		{
			FileUtils.copyFile(fileToCopy, copiedFile);
			FileUtils.deleteQuietly(fileToCopy);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return copiedFile_Name;
	}
	
	public void video_Delete(String TestName)
	{
		File file = new File(tempFilesFolder_Path + "\\" + get_FileName(TestName));
		file.delete();
	}
	
	public String screen_Shot(String testName, WebDriver webDriver)
	{
		// Определение места и имени скриншота
		String screenPath = System.getProperties().get("basedir").toString() + "\\target\\surefire-reports\\screenshots\\";
		String timeStamp = new SimpleDateFormat("HH_mm_ss dd.MM.yyyy").format(Calendar.getInstance().getTime());
		String file_Name = testName + "  " + timeStamp + ".png";
		
		File scrFile = null;
		String driverName = webDriver.toString();
		
		// Если RemoteWebDriver, то нужны доп. манипуляции с драйвером
		if(driverName.contains("RemoteWebDriver"))
		{
		    Augmenter augmenter = new Augmenter(); 
		    TakesScreenshot ts = (TakesScreenshot) augmenter.augment(webDriver);	
		    scrFile = ts.getScreenshotAs(OutputType.FILE);
		}
		else
		{
			scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		}
		
		
		// Копируем файл в папку репорта
		try 
		{
			FileUtils.copyFile(scrFile, new File(screenPath + file_Name));
			scrFile.delete();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return file_Name;
	}
	
	private String get_FileName(String testName)
	{
		String fileName = testName + ".avi";
		return fileName;
	}
}
