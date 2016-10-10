package com.esco.core;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class Listeners extends TestListenerAdapter  
{	
	@Override
	public void onTestStart(ITestResult tr)
	{
		// Определить WebDriver
        Object currentClass = tr.getInstance();
        String webDriverName = ((BaseTest) currentClass).getDriver().toString();

		// Если RemoteWebDriver, то нужны доп. манипуляции с драйвером
		if(!webDriverName.contains("RemoteWebDriver"))
		{
			// Старт записи видео
			new ScreenRecording().startRecording(tr.getTestClass().getRealClass().getSimpleName());
		}
	}
	
	@Override
	public void onTestSuccess(ITestResult tr)
	{
		// Название теста
        String testName = tr.getTestClass().getRealClass().getSimpleName();
		
		// Остановить запись видео + удалить видео
        new ScreenRecording().stopRecording();
		new ScreenRecording().video_Delete(testName);
        
        // Вывести в лог сообщение о удачном запуске
		Reporter.log("Test '" + testName + "' <font color=\"green\">PASSED</font>.");
	}
	
	@Override
	public void onTestFailure(ITestResult tr) 
	{
		// Название теста
        String testName = tr.getTestClass().getRealClass().getSimpleName();
        
        // Вывести в лог сообщение о падении теста
		Reporter.log("Test '" + testName + "' <font color=\"red\">FAILED</font>.");
        
		/*------------------------------------------ Видео ------------------------------------------*/
		// Остановить запись видео
        new ScreenRecording().stopRecording();
		
		// Сохранить в папку 'surefire-reports' + вывести ссылку в лог репорта
		String videoName = new ScreenRecording().video_Copy(testName);
	    Reporter.log("<br><span>Video - </><a href=\"videos\\"+ videoName +"\">Recorded Video</a>");
	    
	    // Удалить видео
		new ScreenRecording().video_Delete(testName);
		/*-------------------------------------------------------------------------------------------*/
		

		/*------------------------------------------ Скриншот ------------------------------------------*/
		// Определить WebDriver
        Object currentClass = tr.getInstance();
        WebDriver webDriver = ((BaseTest) currentClass).getDriver();
		
        // Сохранить в папку 'surefire-reports' + вывести ссылку в лог репорта
		String screenShotName = new ScreenRecording().screen_Shot(testName, webDriver);
	    Reporter.log("<br><span>Screen Shot - </><a href=\"screenshots\\"+ screenShotName +"\">Captured ScreenShot</a>");
		/*-------------------------------------------------------------------------------------------*/
	}
}
