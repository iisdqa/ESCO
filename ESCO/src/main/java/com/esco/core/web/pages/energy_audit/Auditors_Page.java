package com.esco.core.web.pages.energy_audit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CommonElements;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;

public class Auditors_Page extends WebPage<Auditors_Page>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/List/63";
	
	public Auditors_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Auditors_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new Units_Tree().units_Accordion(driver).isAvailable();
	}

	
	/*________________________________________________________ Actions ___________________________________________________*/
	
	// Ожидание готовности странички
	public void waitFor_PageReady()
	{
		//waitUntilClickable(new Elements().new Units_Tree().tree_Div(driver));
		new Elements().new Grid().add_Button(driver).waitUntilAvailable();
	}
	
	// Открыть дерево подразделений
	public void tree_Open()
	{
		// Путь к подразделению
		int[] tree_Path = {1, 4, 1};
		
		// Переход к подразделению
		new CommonActions().tree_Handler(driver, tree_Path);
		
		// Ожидание прогрузки грида
		gridDownload_Wait();
	}
	
	// Ожидание прогрузки грида
	public void gridDownload_Wait()
	{
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
		new CommonActions().simpleWait(1);
	}
	
	// Перейти к добавлению карточки документа
	public Auditors_RegistrationPage card_add()
	{
		// Нажать на кнопку добавления
		new Elements().new Grid().add_Button(driver).click();
		new CommonActions().simpleWait(2);
		
/*		// Закрыть пред. вкладку + переключить драйвер на новую
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(tabs.get(1));*/
		
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	// Поиск карточки
	public void card_Search()
	{
		//region Variables	
		String fieldName = new Elements().new Filtration_Accordion().new Values().fieldName;
		String matchType = new Elements().new Filtration_Accordion().new Values().matchType;
		String value = new Elements().new Filtration_Accordion().new Values().value;
		//endregion
		
		// Фильтрация
		new CommonActions().grid_Filtration(driver, fieldName, matchType, value);
		
		// Ожидание прогрузки грида
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
	}
	
	// Проверка найденной карточки
	public void card_Check(String checkType)
	{
		//region Variables
		WebElement grid = new Elements().new Grid().grid_Body(driver);
		String index = new Elements().new Filtration_Accordion().new Values().value;
		String number  = index.substring(index.lastIndexOf('0') + 1);
		String regDate = new Auditors_RegistrationPage(driver).new Elements().new Values().regDate;
		String correspondent = new Auditors_RegistrationPage(driver).new Elements().new Values().correspondent;
		String corrNum = new Auditors_RegistrationPage(driver).new Elements().new Values().corrNum;
		String corrDate = new Auditors_RegistrationPage(driver).new Elements().new Values().corrDate;
		String shortSummary = new Auditors_RegistrationPage(driver).new Elements().new Values().shortSummary;
		if (checkType == "edit") shortSummary = shortSummary + "2";
		String regulation = new Auditors_RegistrationPage(driver).new Elements().new Values().regulation;
		//endregion
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "",
										  number, 
										  index, 
										  regDate, 
										  correspondent,
										  corrNum,
										  corrDate,
										  shortSummary,
										  regulation};
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	// Переход к редактированию карточки
	public Auditors_RegistrationPage card_Edit()
	{
		// Нажать на кнопку редактирования
		new Elements().new Grid().edit_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	// Переход к просмотру карточки
	public Auditors_RegistrationPage card_View()
	{
		// Нажать на кнопку просмотра
		new Elements().new Grid().view_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	/*____________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements ________________________________________________________*/
	
	private class Elements
	{
		// Унаследовать элементы дерева
		private class Units_Tree extends CommonElements.UnitsTree_Elements {}
		
		// Унаследовать элементы грида
		private class Grid extends CommonElements.BaseGrid_Elements {}
		
		// Унаследовать элементы аккордеона фильтрации
		private class Filtration_Accordion extends CommonElements.FiltrationControl_Elements 
		{
			// Используемые значения
			private class Values
			{
				private String fieldName = "Індекс документа";
				private String matchType = "Дорівнює";
				private String value = new CustomMethods().new WorkWith_TextFiles().file_Read(TextFiles_Path + "IncomingDoc_Index");
			}
		}	
	}
	/*____________________________________________________________________________________________________________________*/
}
