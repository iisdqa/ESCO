package com.esco.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.esco.core.BrowserType;

public class DriverMaster 
{
	private DriverMaster(){};
	
	private static WebDriver driver;
		
	public static WebDriver getDriver(String driverKey)
	{
		// Define browser
		BrowserType browser = BrowserType.get(driverKey);
		
		// Define file download path
		String download_Path = System.getProperties().get("basedir").toString() + "\\storage\\files\\downloaded_files";
		
		// Define web drivers path
		String driver_Path = System.getProperties().get("basedir").toString() + "\\storage\\web_drivers\\";

		switch (browser)
		{
		case FIREFOX_OLD:
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.type", 0);
			
			// Для загрузки файлов
		    profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.dir", download_Path);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain, application/octet-stream," + //applications/octet-stram сработало для *.txt
								  "application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel"); //для Excel файлов
			profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			
			DesiredCapabilities FirefoxCaps = DesiredCapabilities.firefox();
			FirefoxCaps.setJavascriptEnabled(true);
			FirefoxCaps.setCapability(FirefoxDriver.PROFILE, profile);
			
			driver = new FirefoxDriver(FirefoxCaps);
			driver.manage().window().maximize();
			break;
			
		case FIREFOX_M:
			FirefoxProfile m_profile = new FirefoxProfile();
			m_profile.setPreference("network.proxy.type", 0);
			
			// For blank page
			m_profile.setPreference("browser.startup.homepage", "about:blank");
			m_profile.setPreference("startup.homepage_welcome_url", "about:blank");
			m_profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
			
			// For file download
			m_profile.setPreference("browser.download.folderList", 2);
			m_profile.setPreference("browser.download.manager.showWhenStarting", false);
			m_profile.setPreference("browser.download.dir", download_Path);
			m_profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain, application/octet-stream," + //applications/octet-stram для *.txt
								  "application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel"); //для Excel файлов
			m_profile.setPreference("browser.helperApps.alwaysAsk.force", false);
			m_profile.setPreference("browser.download.manager.showAlertOnComplete", false);
			
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability(FirefoxDriver.PROFILE, m_profile);
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("marionette", true);	
			
			System.setProperty("webdriver.gecko.driver", driver_Path + "geckodriver_0_9.exe");
			System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");

			driver = new MarionetteDriver(capabilities);
			driver.manage().window().maximize();
			
		break;
			
		case CHROME:
			DesiredCapabilities ChromeCaps = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			
			// For file download
		    Map<String, Object> prefs = new HashMap<String, Object>();
		    prefs.put("download.default_directory", download_Path);
		    options.setExperimentalOption("prefs", prefs);
		    
		    // Window maximize and disable extensions
			options.addArguments("test-type", "start-maximized", "no-default-browser-check");
			options.addArguments("--disable-extensions");
			
			// Driver initialization
			ChromeCaps.setCapability(ChromeOptions.CAPABILITY, options); 
			System.setProperty("webdriver.chrome.driver", driver_Path + "chromedriver.exe");
			driver = new ChromeDriver(ChromeCaps);
			break;				

		case IE_9:
			DesiredCapabilities IeCaps = DesiredCapabilities.internetExplorer();
			IeCaps.setCapability("ignoreZoomSetting", true);
			IeCaps.setCapability("version", "9");
			System.setProperty("webdriver.ie.driver", driver_Path + "IEDriverServer_x64.exe");
			driver = new InternetExplorerDriver(IeCaps);
			driver.manage().window().maximize();
			break;
			
		case IE_11:
			DesiredCapabilities Ie11Caps = DesiredCapabilities.internetExplorer();
			Ie11Caps.setCapability("ignoreZoomSetting", true);
			Ie11Caps.setCapability("version", "11");
			System.setProperty("webdriver.ie.driver", driver_Path + "IEDriverServer_x64.exe");
			driver = new InternetExplorerDriver(Ie11Caps);
			driver.manage().window().maximize();
			break;
			
		case REMOTE_IE:
			DesiredCapabilities Remote_IeCaps = DesiredCapabilities.internetExplorer();
			Remote_IeCaps.setCapability("ignoreZoomSetting", true);
			Remote_IeCaps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			Remote_IeCaps.setCapability("platform", "WINDOWS");
			Remote_IeCaps.setCapability("browserName", "internet explorer");
			Remote_IeCaps.setCapability("version", "9");
			try {driver = new RemoteWebDriver(new URL("http://192.168.128.47:4444/wd/hub"), Remote_IeCaps);} 
			catch (MalformedURLException e) {e.printStackTrace();}
			driver.manage().window().maximize();
			break;
		}
		return driver;
	}
}

