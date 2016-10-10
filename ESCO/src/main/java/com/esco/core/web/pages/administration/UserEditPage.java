package com.esco.core.web.pages.administration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.TextInput;

public class UserEditPage extends WebPage<UserEditPage> 
{
private static final String PAGE_URL = BASE_URL + "/User/EditUser/";
	
	public UserEditPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public UserEditPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getPasswordChangeButton().isAvailable() && 
		   new RequiredFields().getLogin().isAvailable();
	}
	
	/*_____________________________________________________________________________________________*/
	
	public void UserPositionChange()
	{				
		// Изменить должность пользователя
		new OptionalFields().getPosition().clear();
		new OptionalFields().getPosition().inputText("Специалист");
	}
		
	public UsersPage UserUpdate()
	{
		getUserUpdateButton().click();
		return new UsersPage(driver).waitUntilAvailable();
	}
	
	/*_____________________________________________________________________________________________*/
	
	private Button getPasswordChangeButton()
	{
		return new Button(driver, By.id("btnChangePassword"));
	}
	
	private Button getUserUpdateButton()
	{
		return new Button(driver, By.id("btnInsert"));
	}
	
	// Обязательные поля
	private class RequiredFields
	{
		private TextInput getLogin()
		{
			return new TextInput(driver, By.id("Login"));
		}
	}
	// Необязательные поля
	private class OptionalFields
	{		
		private TextInput getPosition()
		{
			return new TextInput(driver, By.id("Position"));
		}
	}
}
