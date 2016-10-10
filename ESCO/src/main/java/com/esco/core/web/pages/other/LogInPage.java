package com.esco.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.WebPage;
import com.esco.core.web.elements.TextInput;

public class LogInPage extends WebPage<LogInPage> 
{
	private static final String PAGE_URL = BASE_URL + "/Account/LogOn";
	
	public LogInPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public LogInPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getUserNameInput().isAvailable() && 
			   getPasswordInput().isAvailable();
	}

	public MainPage logInAs(String UserName, String Password)
	{
		getUserNameInput().inputText(UserName);
		getPasswordInput().inputText(Password);
		getSubmitButton().click();
		return new MainPage(driver).waitUntilAvailable();
	}
	
/*	public DrugRegistryPage logInToDrugsPage(String UserName, String Password)
	{
		getUserNameInput().inputText(UserName);
		getPasswordInput().inputText(Password);
		getSubmitButton().click();
		return new DrugRegistryPage(driver).waitUntilAvailable();
	}*/
	
	private TextInput getUserNameInput()
	{
		return new TextInput(driver, By.id("UserName"));
	}
		
	private TextInput getPasswordInput()
	{
		return new TextInput(driver, By.id("Password"));
	}
	
	private WebElement getSubmitButton()
	{
		WebElement element = driver.findElement(By.className("login-btn"));
		WebElement button = element.findElement(By.xpath("//input[@type='submit']"));
		return button;
	}
}

