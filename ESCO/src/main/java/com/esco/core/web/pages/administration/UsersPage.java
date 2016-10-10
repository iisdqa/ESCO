package com.esco.core.web.pages.administration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.CheckBox;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.TextInput;
import com.esco.core.web.pages.other.LogInPage;

public class UsersPage extends WebPage<UsersPage> 
{
private static final String PAGE_URL = BASE_URL + "/User/Search";
	
	public UsersPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public UsersPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getUserAddButton().isAvailable() && 
			   getUserSearchButton().isAvailable();
	}
	
	public void WaitForGridReady()
	{
		// Ожидание доступности элемента
		waitUntilClickable(getGridTable());
		// Ожидание прогрузки грида
		waitForBlockStatus(getGridDownload_Div(), false);
		new CommonActions().simpleWait(1);
	}
	
	public UserAddPage AddButtonClick()
	{
		getUserAddButton().click();
		return new UserAddPage(driver).waitUntilAvailable();
	}
	
	public void UserSearch()
	{
		getLoginTextInput().inputText("auto_user");
		getLocalRole().click();
		getUserSearchButton().click();
		new CommonActions().simpleWait(1);
		WaitForGridReady();
		new CommonActions().simpleWait(1);
	}
	
	public void AddedUserCheck()
	{
		// Определение текущей даты
		String currentDate = new CustomMethods().getCurrentDate();
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"", 
								  		  "",
								  		  "",
								  		  "auto_user", 
								  		  "  - Эксперт локального уровня", 
								  		  "Тестовко", 
								  		  "Спец", 
								  		  "Автоматизация",
								  		  currentDate,
								  		  currentDate};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody(), true);
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public UserEditPage EdidButtonClick()
	{
		getUserEditButton().click();
		return new UserEditPage(driver).waitUntilAvailable();
	}
	
	public void EditedUserCheck()
	{
		// Определение текущей даты
		String currentDate = new CustomMethods().getCurrentDate();
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"", 
								  		  "", 
								  		  "",
								  		  "auto_user", 
								  		  "  - Эксперт локального уровня", 
								  		  "Тестовко", 
								  		  "Специалист", 
								  		  "Автоматизация",
								  		  currentDate,
								  		  currentDate};
		
		// Вытянуть значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(getGridBody(), true);
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public UserViewPage ViewButtonClick()
	{
		getUserViewButton().click();
		return new UserViewPage(driver).waitUntilAvailable();
	}
	
	public void UserDelete()
	{
		getUserDeleteutton().click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(getDeletionPopUp());
		getDeletionAcceptButton().click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(getGridPager());
	}
	
	public void UserDeletionCheck()
	{
		String expectedValue = "Немає записів для перегляду";
		String actualValue = getGridPagerInfo().getText();
		
		// Проверка удаления пользователя
		assertThat(actualValue, is(equalTo(expectedValue)));
	}
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	private Custom getGridTable()
	{
		return new Custom(driver, By.id("list"));
	}
	
	private Custom getGridDownload_Div()
	{
		return new Custom(driver, By.id("load_list"));
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='list']/tbody"));
	}
	
	private Custom getGridPager()
	{
		return new Custom(driver, By.id("pager"));
	}
	
	private Custom getGridPagerInfo()
	{
		return new Custom(driver, By.className("ui-paging-info"));
	}
	
	private Button getUserAddButton()
	{
		return new Button(driver, By.xpath("//a[@href='/Core/User/AddUser' and @id='add_link']"));
	}
	
	private Button getUserEditButton()
	{
		return new Button(driver, By.xpath("//input[@title='Редактирование']"));
	}
	
	private Button getUserViewButton()
	{
		return new Button(driver, By.xpath("//input[@title='Просмотр']"));
	}
	
	private Button getUserDeleteutton()
	{
		return new Button(driver, By.xpath("//input[@title='Удаление']"));
	}
	
	private TextInput getLoginTextInput()
	{
		return new TextInput(driver, By.id("Login"));
	}
	
	private CheckBox getLocalRole()
	{
		return new CheckBox(driver, By.id("Expert_Local_Level"));
	}
	
	private Button getUserSearchButton()
	{
		return new Button(driver, By.id("btnSearch"));
	}

	private Custom getDeletionPopUp()
	{
		return new Custom(driver, By.id("dialog-confirm"));
	}
	
	private Custom getDeletionAcceptButton()
	{
		return new Custom(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Так')]"));
	}
}
