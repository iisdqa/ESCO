package com.esco.core.web.pages.administration;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.CheckBox;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.Link;
import com.esco.core.web.pages.other.LogInPage;

public class UserViewPage extends WebPage<UserViewPage> 
{
private static final String PAGE_URL = BASE_URL + "/User/EditUser/";
	
	public UserViewPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public UserViewPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getViewForm().isAvailable() && 
			   getBackToUsersPageLink().isAvailable();
	}
	
	/*_____________________________________________________________________________________________*/
	
	public void ViewPageCheck()
	{		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [11][];
		ExpectedValues[0] = new String[] {"Логін:", "auto_user"};
		ExpectedValues[1] = new String[] {"Прізвище:", "Тестовко"};
		ExpectedValues[2] = new String[] {"Ім'я:", "Василий"};
		ExpectedValues[3] = new String[] {"По батькові:", "Петрович"};
		ExpectedValues[4] = new String[] {"Посада:", "Специалист"};
		ExpectedValues[5] = new String[] {"Телефон:", "(000) 111-22-33"};
		ExpectedValues[6] = new String[] {"Адреса електронної пошти:", "auto_user@email.com"};
		ExpectedValues[7] = new String[] {"Установа:", "Автоматизация"};
		ExpectedValues[8] = new String[] {"Роль:", "NotNull"};
		ExpectedValues[9] = new String[] {"Активний:", "Так"};
		ExpectedValues[10] = new String[] {"Параметри плагіну 'Документи'\nМова інтерфейсу\nУкраїнська"};
		
		// Вытянуть значения из грида
		String[][] ActualValues = getValuesFromViewPage(getGridBody());
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
		
		// Проверка чекбоксов
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new RoleCheckBoxes().getAdminRole(), "checked", false);
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new RoleCheckBoxes().getCentralRole(), "checked", false);
		new CustomMethods().new Attribute_Checkers().checkBoxAttribute_Check(new RoleCheckBoxes().getLocalRole(), "checked", true);
	}
	
	public LogInPage userOut()
	{
		// Выход из системы
		return new CommonActions().userOut(driver);
	}
	
	/*_____________________________________________________________________________________________*/
	
	private String[][] getValuesFromViewPage (WebElement table)
	{
		// Определение количества рядов
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		
		// Удаление лишних элементов(из блока 'Роль')
		int[] elementsToDelete = {20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9};
		for(int i=0; i < elementsToDelete.length; i++) rows.remove(elementsToDelete[i]);
		
		// Определение количества рядов в возвращаемом массиве
		String[][] GridValues = new String[rows.size()][];
		
		// Перебираем ряды
		for(int rnum=0; rnum < rows.size(); rnum++)
		{
			// Определяем количество колонок
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
			int sizeBeforeCut = columns.size();
			
			// Удаление лишних ячеек для роли
			if(rnum == 8)
			{
				for(int cnum=sizeBeforeCut- 1; cnum > 1; cnum--)
				{
						columns.remove(cnum);
				}
			}
			// Определение размера массива с значениями ячеек
			String[] ColumnValues = new String[columns.size()];
			
			// Записываем значения ячеек в массив
			for(int cnum=0; cnum<columns.size(); cnum++)
			{			
				ColumnValues[cnum] = new CustomMethods().StringSpacesCut(columns.get(cnum).getText());
			}
			
			// Положить значения колонок в ряд 
			GridValues[rnum] = ColumnValues;
		}
		return GridValues;
	}
	
	private Link getBackToUsersPageLink()
	{
		return new Link(driver, By.xpath("//a[@href='/Core/User/Search']"));
	}
	
	private Custom getViewForm()
	{
		return new Custom(driver, By.id("frmEditUser"));
	}
	
	private WebElement getGridBody()
	{
		return driver.findElement(By.xpath("//*[@id='frmEditUser']/table/tbody"));
	}
	
	// Обязательные поля
	private class RoleCheckBoxes
	{
		private CheckBox getLocalRole()
		{
			return new CheckBox(driver, By.id("Expert_Local_Level"));
		}
		
		private CheckBox getCentralRole()
		{
			return new CheckBox(driver, By.id("Expert_Central_Level"));
		}
		
		private CheckBox getAdminRole()
		{
			return new CheckBox(driver, By.id("System_Administrator"));
		}
	}
}
