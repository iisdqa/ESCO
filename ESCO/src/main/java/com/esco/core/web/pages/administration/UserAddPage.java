package com.esco.core.web.pages.administration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.CheckBox;
import com.esco.core.web.elements.Text;
import com.esco.core.web.elements.TextInput;

public class UserAddPage extends WebPage<UserAddPage> 
{
private static final String PAGE_URL = BASE_URL + "/User/AddUser";
	
	public UserAddPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public UserAddPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getUserSaveButton().isAvailable() && 
		   new RequiredFields().getLogin().isAvailable();
	}
	
	/*_____________________________________________________________________________________________*/
	
	public void OptionalFieldsFill()
	{
		// Фамилия
		new OptionalFields().getSurname().inputText("Тестовко");
		
		// Имя
		new OptionalFields().getName().inputText("Василий");
		
		// Отчество
		new OptionalFields().getPatronimic().inputText("Петрович");
		
		// Должность
		new OptionalFields().getPosition().inputText("Спец");
		
		// Телефон
		new OptionalFields().getPhoneNumber().inputText("(000) 111-22-33");
	}
	
	public void ValidatorsCheck()
	{
			getUserSaveButton().click();
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}

			// Проверка валидатора логина
			assertThat(new ValidationFields().getLoginValidator().getText(), is(equalTo("Поле \"Логін\" є обов'язковим.")));
			
			// Проверка валидатора логина
			assertThat(new ValidationFields().getPasswordValidator().getText(), is(equalTo("Поле \"Пароль\" є обов'язковим.")));
			
			// Проверка валидатора логина
			assertThat(new ValidationFields().getEmailValidator().getText(), is(equalTo("Поле \"Адреса електронної пошти\" обов'язковe для заповнення.")));
			
			// Проверка валидатора логина
			assertThat(new ValidationFields().getRoleValidator().getText(), is(equalTo("Повинна бути обрана хоча б одна роль.")));
	}
	
	// Заполнить Обязательные поля
	public void RequiredFieldsFill()
	{
		// Логин
		new RequiredFields().getLogin().inputText("auto_user");
		
		// Пароль
		new RequiredFields().getPassword().inputText("auto_user");
		
		// Повторить пароль
		new RequiredFields().getConfirmPassword().inputText("auto_user");
		
		// E-mail
		new RequiredFields().getEmail().inputText("auto_user@email.com");
		
		// Роль
		new RequiredFields().getLocalRole().click();
	}

	public UsersPage UserSave()
	{
		getUserSaveButton().click();
		return new UsersPage(driver).waitUntilAvailable();
	}
	
	/*_____________________________________________________________________________________________*/
	
	private Button getUserSaveButton()
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
		
		private TextInput getPassword()
		{
			return new TextInput(driver, By.id("Password"));
		}
		
		private TextInput getConfirmPassword()
		{
			return new TextInput(driver, By.id("ConfirmPassword"));
		}
		
		private TextInput getEmail()
		{
			return new TextInput(driver, By.id("Email"));
		}
		
		private CheckBox getLocalRole()
		{
			return new CheckBox(driver, By.id("Expert_Local_Level"));
		}
	}
	
	// Обязательные поля
	private class ValidationFields
	{
		private Text getLoginValidator()
		{
			return new Text(driver, By.id("Login-error"));
		}
		
		private TextInput getPasswordValidator()
		{
			return new TextInput(driver, By.id("Password-error"));
		}
		
		private TextInput getEmailValidator()
		{
			return new TextInput(driver, By.id("Email-error"));
		}
		
		private TextInput getRoleValidator()
		{
			return new TextInput(driver, By.id("RolesContains-error"));
		}
	}
	
	// Необязательные поля
	private class OptionalFields
	{
		private TextInput getSurname()
		{
			return new TextInput(driver, By.id("SurName"));
		}
		
		private TextInput getName()
		{
			return new TextInput(driver, By.id("Name"));
		}
		
		private TextInput getPatronimic()
		{
			return new TextInput(driver, By.id("MiddleName"));
		}
		
		private TextInput getPosition()
		{
			return new TextInput(driver, By.id("Position"));
		}
		
		private TextInput getPhoneNumber()
		{
			return new TextInput(driver, By.id("Phone"));
		}
	}
}
