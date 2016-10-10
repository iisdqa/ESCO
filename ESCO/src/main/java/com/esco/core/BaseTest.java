package com.esco.core;

import java.sql.Connection;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.esco.core.database.DbConnection;

public abstract class BaseTest 
{
	protected WebDriver driver;
	protected Connection sqlConnection;
	protected String browser;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"browser", "environment"})
	public void setUp(String browser, String environment)
	{
		driver = DriverMaster.getDriver(browser);
		EnvironmentConfiguration.setGlobalEnvironment(environment);
		sqlConnection = DbConnection.setDbConnection(environment);
		this.browser = browser;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown()
	{
		DbConnection.closeConnection(sqlConnection);
		driver.close();
		driver.quit();
	}
	
    public WebDriver getDriver() 
    {
        return driver;
    }
}
