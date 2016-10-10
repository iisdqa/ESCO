package com.esco.core.web.pages.energy_audit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.sql.Connection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.esco.core.database.DbQueries;
import com.esco.core.database.DbStatements;
import com.esco.core.web.CommonActions;
import com.esco.core.web.CommonElements;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.TextInput;

public class Auditors_RegistrationPage extends WebPage<Auditors_RegistrationPage>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/";
	
	public Auditors_RegistrationPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Auditors_RegistrationPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().close_Button(driver).isAvailable();
	}
	
	public void card_Generate(Connection sqlConnection)
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		WebElement numGenerate = new Elements().numGenerate_Button(driver);
		Custom docIndex = new Elements().getDocIndex_Input();
		TextInput regDate = new Elements().getRegDate_Date();
		TextInput caseName = new Elements().getCaseName_Input();
		//endregion
		
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		//assertThat(numGenerate.getAttribute("disabled"), is(equalTo("true")));
		
		// Определение ожидаемого номера
		String index = expectedIndex_Define(sqlConnection);
		
		// Заполнение обязательных полей
		regDate.click();
		regDate.inputText(new Elements().new Values().regDate);
		caseName.inputText(new Elements().new Values().caseName);
		
		// Выбор значения из автокомплита для 'Название по номенклатуре'
		new CommonActions().autoCompleteValue_Set(driver, caseName, 1);
	
        // Нажать на кнопку генерации нового номера карточки
        numGenerate.click();
        new CommonActions().simpleWait(3);
        
        // Ожидание доступности элемента
        waitUntilClickable(docIndex);
        
        // Проверка сгенерированного индекса
        Integer indexLength = index.length();
        String expectedIndex = "";
        if(indexLength.equals(1)) expectedIndex = "01/0000" + index;
        else if(indexLength.equals(2)) expectedIndex = "01/000" + index;
        else if(indexLength.equals(3)) expectedIndex = "01/00" + index;
        else if(indexLength.equals(4)) expectedIndex = "01/0" + index;
        else if(indexLength > 4) Assert.fail("Ошибка! Индекс документа принял неожиданное значение.");
        
		assertThat(docIndex.getAttribute("value"), is(equalTo(expectedIndex)));
		
		// Запись индекса в текстовую переменную
		new CustomMethods().new WorkWith_TextFiles().file_Create(TextFiles_Path + "IncomingDoc_Index", expectedIndex);
	}
	
	private String expectedIndex_Define(Connection sqlConnection)
	{
		//region Variables	
		String ErrorMessage = DbQueries.DocsTests.Incoming_Docs.Select_Queries.IndexesCountDefine_ErrorMessage;
		String Statement = DbQueries.DocsTests.Incoming_Docs.Select_Queries.IndexesCount_Define;
		//endregion
		
		String count = "";
		
		try 
		{
			count = new DbStatements().Simple_SelectStatement(sqlConnection, Statement, ErrorMessage);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		// Преобразование в int и добавить +1
		int expectedIndex = Integer.valueOf(count) + 1;
		
		return String.valueOf(expectedIndex);
	}
	
	public Auditors_RegistrationPage cardInfo_Set()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		WebElement numGenerate = new Elements().numGenerate_Button(driver);
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		Select regulation = new Elements().getRegulation_Select();
		Select docType = new Elements().getDocType_Select();
		Select docForm = new Elements().getDocForm_Select();
		TextInput pages = new Elements().getPages_Input();
		TextInput additionsCount = new Elements().getAdditionsCount_Input();
		TextInput corrType = new Elements().getCorrType_Input();
		TextInput correspondent = new Elements().getCorrespondent_Input();
		TextInput corrInfo = new Elements().getCorrInfo_Input();
		TextInput corrDate = new Elements().getCorrDate_Input();
		TextInput сorrNum = new Elements().getCorrNum_Input();
		WebElement shortSummary = new Elements().getShortSummary_Text();
		WebElement notes = new Elements().getNotes_Text();
		//endregion
		
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		new CustomMethods().elementExistenceCheck(numGenerate, false);
		
		// Заполнение 'Select' полей
		docWorkStage.selectByVisibleText(new Elements().new Values().docWorkStage);
		regulation.selectByVisibleText(new Elements().new Values().regulation);
		docType.selectByVisibleText(new Elements().new Values().docType);
		docForm.selectByVisibleText(new Elements().new Values().docForm);

		// Заполнение автокомплитных полей
		corrType.inputText(new Elements().new Values().corrType);
		new CommonActions().autoCompleteValue_Set(driver, corrType, 1);
		correspondent.inputText(new Elements().new Values().correspondent);
		new CommonActions().autoCompleteValue_Set(driver, correspondent, 1);
		corrInfo.inputText(new Elements().new Values().corrInfo);
		new CommonActions().autoCompleteValue_Set(driver, corrInfo, 1);
		
		// Заполнение обычных текстовых полей
		pages.inputText(new Elements().new Values().pages);
		additionsCount.inputText(new Elements().new Values().additionsCount);
		corrDate.click();
		corrDate.inputText(new Elements().new Values().corrDate);
		сorrNum.inputText(new Elements().new Values().corrNum);
		
		// Заполнение текстовых(многострочных) полей
		shortSummary.click();
		sendKeys(new Elements().new Values().shortSummary);
		notes.click();
		sendKeys(new Elements().new Values().notes);
		
		// Добавление резолюции
		resolution_add();
		
		// Проверка добавленной резолюции
		resolution_grid_check("add");
		
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(3);
				
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();		
	}
	
	private void resolution_add()
	{
		//region Variables
		String GridId = new Elements(). new Resolution_Elements().GridId;
		
		String projectType = new Elements().new Resolution_Elements().new Values().projectType;
		String author = new Elements().new Resolution_Elements().new Values().author;
		String resolutionDate = new Elements().new Resolution_Elements().new Values().resolutionDate;
		String resolution = new Elements().new Resolution_Elements().new Values().resolution;
		String deadlineDate =new Elements().new Resolution_Elements().new Values().deadlineDate;
		//endregion
	
		// Открыть поп-ап добавления
		new Elements().new Resolution_Elements().add_Button(driver, GridId).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Resolution_Elements().new Pop_Up().resolution_Text());
		
		// Заполнение полей
		sendKeys(resolution);
		new Elements().new Resolution_Elements().new Pop_Up().deadlineDate_Input().click();
		sendKeys(deadlineDate);
		new Elements().new Resolution_Elements().new Pop_Up().getProjectType_Select().selectByVisibleText(projectType);
		new Elements().new Resolution_Elements().new Pop_Up().getAuthor_Select().selectByVisibleText(author);
		new Elements().new Resolution_Elements().new Pop_Up().resolutionDate_Input().click();
		sendKeys(resolutionDate);
				
		// Сохранить
		new Elements().new Resolution_Elements().new Pop_Up().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new Resolution_Elements().download_Div(driver, GridId), false);
	}
	
	public void resolution_grid_check(String checkType)
	{
		//region Variables
		String GridId = new Elements(). new Resolution_Elements().GridId;
		WebElement grid = new Elements().new Resolution_Elements().grid_Body(driver, GridId);
		String resolution = new Elements().new Resolution_Elements().new Values().resolution;
		if(checkType != "add") resolution = new Elements().new Resolution_Elements().new Values().resolution + "2";
		String resolutionDate = new Elements().new Resolution_Elements().new Values().resolutionDate;
		String author = new Elements().new Resolution_Elements().new Values().author;
		String projectType = new Elements().new Resolution_Elements().new Values().projectType;
		String deadlineDate = new Elements().new Resolution_Elements().new Values().deadlineDate;
		//endregion
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  projectType,
										  author,
										  resolutionDate, 
										  resolution, 										   
										  deadlineDate,
										  ""};
		if(checkType == "view")
		{
			int[] elements_ToRemove = new int[]{ 0, ExpectedValues.length - 1};
			ExpectedValues[0] = new CustomMethods().new Grid().arrayElements_Remove(ExpectedValues[0], elements_ToRemove);
		}
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	public void card_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		WebElement numGenerate = new Elements().numGenerate_Button(driver);
		TextInput regDate = new Elements().getRegDate_Date();
		Custom docIndex = new Elements().getDocIndex_Input();
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		Select regulation = new Elements().getRegulation_Select();
		Select docType = new Elements().getDocType_Select();
		Select docForm = new Elements().getDocForm_Select();
		TextInput pages = new Elements().getPages_Input();
		TextInput additionsCount = new Elements().getAdditionsCount_Input();
		TextInput corrType = new Elements().getCorrType_Input();
		TextInput correspondent = new Elements().getCorrespondent_Input();
		TextInput corrInfo = new Elements().getCorrInfo_Input();
		TextInput corrDate = new Elements().getCorrDate_Input();
		TextInput сorrNum = new Elements().getCorrNum_Input();
		WebElement shortSummary = new Elements().getShortSummary_Frame();
		WebElement notes = new Elements().getNotes_Frame();
		//endregion
		
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		new CustomMethods().elementExistenceCheck(numGenerate, false);
		
		// Проверка индекса и даты регистрации
		assertThat(docIndex.getAttribute("value"), is(equalTo(new CustomMethods().new WorkWith_TextFiles().file_Read(TextFiles_Path + "IncomingDoc_Index"))));
		assertThat(regDate.getAttribute("value"), is(equalTo(new Elements().new Values().regDate)));
		
		// Проверка 'Select' полей
		assertThat(docWorkStage.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage)));
		assertThat(regulation.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().regulation)));
		assertThat(docType.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docType)));
		assertThat(docForm.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docForm)));

		// Проверка автокомплитных полей
		assertThat(corrType.getAttribute("value"), is(equalTo(new Elements().new Values().corrType)));
		assertThat(correspondent.getAttribute("value"), is(equalTo(new Elements().new Values().correspondent)));
		assertThat(corrInfo.getAttribute("value"), is(equalTo(new Elements().new Values().corrInfo)));
		
		// Проверка обычных текстовых полей
		assertThat(pages.getAttribute("value"), is(equalTo(new Elements().new Values().pages)));
		assertThat(additionsCount.getAttribute("value"), is(equalTo(new Elements().new Values().additionsCount)));
		assertThat(corrDate.getAttribute("value"), is(equalTo(new Elements().new Values().corrDate)));
		assertThat(сorrNum.getAttribute("value"), is(equalTo(new Elements().new Values().corrNum)));
		
		// Проверка текстовых(многострочных) полей
		String shortSummaryValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, shortSummary);
		assertThat(shortSummaryValue, is(equalTo(new Elements().new Values().shortSummary)));
		String notesValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, notes);
		assertThat(notesValue, is(equalTo(new Elements().new Values().notes)));
		
		// Проверка добавленной резолюции
		resolution_grid_check("add");
	}
	
	public void corr_ExistenceCheck()
	{
		//region Variables
		Button search = new Elements().new CorrespondentCheck_Elements().search_Button();
		Custom info = new Elements().new CorrespondentCheck_Elements().info_PopUp();
		Button close = new Elements().new CorrespondentCheck_Elements().close_Button();
		//endregion
		
		// Поиск 
		search.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(info);
		
		// Проверка сообщеия
		assertThat(info.getText(), is(equalTo(new Elements().new CorrespondentCheck_Elements().new Values().noMatches_Info)));
		
		// Закрытие поп-апа
		close.click();
		new CommonActions().simpleWait(1);
	}
	
	public void card_Edit()
	{
		//region Variables	
		String GridId = new Elements(). new Resolution_Elements().GridId;
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		Custom docIndex = new Elements().getDocIndex_Input();
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// Редактироваить этап
		docWorkStage.selectByVisibleText(new Elements().new Values().docWorkStage_Edit);
				
		// Редактировать резолюцию
		resolution_edit();
		
		// Проверка редактирования резолюции
		resolution_grid_check("edit");
		
		// Сохранить введенную инфу
/*		datePicker.click();
		new CommonActions().simpleWait(1);
		docIndex.click();
		new CommonActions().simpleWait(1);*/
		save.click();
		new CommonActions().simpleWait(3);
		
		// Ожидание загрузки странички
		waitUntilClickable(docIndex);
		waitForBlockStatus(new Elements().new Resolution_Elements().download_Div(driver, GridId), false);
	}
	
	private void resolution_edit()
	{			
		//region Variables
		String GridId = new Elements(). new Resolution_Elements().GridId;
		//endregion
		
		// Открыть поп-ап добавления		
		new Elements().new Resolution_Elements().edit_Button(driver, GridId).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Resolution_Elements().new Pop_Up().resolution_Text());
		
		// Заполнение полей
		sendKeys("2");
		sendKeys("\t");
				
		// Сохранить
		new Elements().new Resolution_Elements().new Pop_Up().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		waitForBlockStatus(new Elements().new Resolution_Elements().download_Div(driver, GridId), false);
	}
	
	// Проверка отредактированной карточки
	public void editedCard_Check()
	{
		//region Variables	
		Select docWorkStage = new Elements().getDocWorkStage_Select();
		//endregion
		
		// Проверка этапа обработки
		assertThat(docWorkStage.getFirstSelectedOption().getText(), is(equalTo(new Elements().new Values().docWorkStage_Edit)));
		
		// Проверка редактирования резолюции
		resolution_grid_check("edit");
	}
	
	// Проверка хэдера карточки
	public void cardHeader_Check()
	{
		//region Variables	
		String index = new CustomMethods().new WorkWith_TextFiles().file_Read(TextFiles_Path + "IncomingDoc_Index");
		String regDate = new Elements().new Values().regDate;
		String expected_Header = "Вхідні документи " + index + " від " + regDate;
		//endregion
		
		new CommonActions().cardHeader_Check(driver, expected_Header);
	}
	
	// Изменить краткое содержание
	public Auditors_RegistrationPage shortSummary_Edit()
	{
		// Установить краткое содержание
		new Elements().getShortSummary_Text().click();
		sendKeys("2");
		
		// Сохранить введенную инфу
		new Elements().save_Button(driver).click();
		new CommonActions().simpleWait(3);
				
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();		
	}
	
	// Возврат ко всем документам
	public Auditors_Page card_Close()
	{
		// Закрытие редактирования карточки
		new Elements().close_Button(driver).click();
		new CommonActions().simpleWait(1);
		
		return new Auditors_Page(driver).waitUntilAvailable();
	}
	
	public Auditors_FilesPage goTo_Files_Page(String go_Type)
	{
		//region Variables	
		WebElement insetLink = new Elements().inset_Link(driver, "3");
		Custom info = new Elements().new SaveOrNot_Elements().info_PopUp(driver);
		Button yes = new Elements().new SaveOrNot_Elements().yes_Button(driver);
		//endregion
				
	 	// Клик по вкладке
		insetLink.click();
		
		// С проверкой поп-апа при уходе с не сохраненными данными
		if(go_Type == "tricky")
		{
			//Отказ в сохранении данных
			new CommonActions().simpleWait(1);
			waitUntilClickable(info);
			
			// Проверка сообщеия
			assertThat(info.getText(), is(equalTo(new Elements().new SaveOrNot_Elements().new Values().info)));
			
			// Закрытие поп-апа
			yes.click();
			new CommonActions().simpleWait(1);
		}
		
		return new Auditors_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{		
		// 'Этап обработки документа'
		private Select getDocWorkStage_Select() 		{ return new Select(driver.findElement(By.id("509"))); } 
			
		// 'Индекс документа'
		private Custom getDocIndex_Input()  			{ return new Custom(driver, By.id("510")); }
		
		// 'Дата регистрации'
		private TextInput getRegDate_Date()   			{ return new TextInput(driver, By.id("511")); }	
		
		// 'Контроль'
		private Select getRegulation_Select()   		{ return new Select(driver.findElement(By.id("512"))); }
	
		// 'Вид документа'
		private Select getDocType_Select()      		{ return new Select(driver.findElement(By.id("513"))); }
		
		// 'Форма поступления документа'
		private Select getDocForm_Select()      		{ return new Select(driver.findElement(By.id("514"))); }
				
		// 'Листов'
		private TextInput getPages_Input()     	    	{ return new TextInput(driver, By.id("515")); }
		
		// 'Дополнений'
		private TextInput getAdditionsCount_Input() 	{ return new TextInput(driver, By.id("516")); }
		
		// 'Вид кореспондента'
		private TextInput getCorrType_Input()   		{ return new TextInput(driver, By.id("517_auto")); }
		
		// 'Корреспондент'
		private TextInput getCorrespondent_Input()  	{ return new TextInput(driver, By.id("518_auto")); }
		
		// 'ФИО корреспондента'
		private TextInput getCorrInfo_Input()   		{ return new TextInput(driver, By.id("519_auto")); }
		
		// 'Дата корреспондента'
		private TextInput getCorrDate_Input()   		{ return new TextInput(driver, By.id("520")); }
		
		// '№ корреспондента'
		private TextInput getCorrNum_Input()   			{ return new TextInput(driver, By.id("521")); }
		
		// 'Название дела по номенклатуре'
		private TextInput getCaseName_Input()   		{ return new TextInput(driver, By.id("522_auto")); }		
		
		// 'Краткое содержание'
		private WebElement getShortSummary_Text()   	{ WebElement element = driver.findElement(By.id("customTextEditor_523")); return element; }
	
		// 'Краткое содержание' фрэйм
		private WebElement getShortSummary_Frame()   	{ return driver.findElement(By.id("customTextEditor_523_DesignIFrame")); }
			
		// 'Заметки'
		private WebElement getNotes_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_524")); return element; }
		
		// 'Заметки' фрэйм
		private WebElement getNotes_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_524_DesignIFrame")); }	
		
		// Значения, которые будут использоваться для заполнения элементов
		public class Values
		{
			private String docWorkStage = "Новий";   								// 'Этап обработки документа'
			private String docWorkStage_Edit = "В обробці";   						// 'Этап обработки документа'
			protected String regDate = "01.01.2020"; 									// 'Дата регистрации'
			protected String regulation = "Інформаційні"; 							// 'Контроль'
			private String docType = "Акт"; 										// 'Вид документа'
			private String docForm = "Електронна пошта"; 							// 'Форма поступления документа'
			private String pages = "1"; 											// 'Листов'
			private String additionsCount = "2"; 									// 'Дополнений'
			private String corrType = "Суди"; 										// 'Вид кореспондента'
			protected String correspondent = "Суд_2"; 								// 'Корреспондент'
			private String corrInfo = "Сидоров С.С"; 								// 'ФИО корреспондента'
			protected String corrDate = new CustomMethods().getCurrentDate(); 		// 'Дата корреспондента'
			protected String corrNum = "222111"; 										// '№ корреспондента'
			private String caseName = "Номенклатура Тест_1"; 						// 'Название дела по номенклатуре'
			protected String shortSummary = "Зміст_"; 								// 'Краткое содержание'
			private String notes = "Примітка_"; 									// 'Заметки'
		}
		
		private class Resolution_Elements extends CommonElements.Card_Elements.Grid
		{
			private String GridId = "525";
		
			private class Pop_Up extends CommonElements.Card_Elements.Pop_Ups
			{
				private Select getProjectType_Select()      	{ return new Select(driver.findElement(By.id("526"))); }
				
				// Автор
				private Select getAuthor_Select()      			{ return new Select(driver.findElement(By.id("527"))); }
				
				// Дата резолюции
				private TextInput resolutionDate_Input() 		{ return new TextInput(driver, By.id("528")); }
				
				// 'Резолюция'
				private Custom resolution_Text()   				{ return new Custom(driver, By.id("customTextEditor_529")); }
				
				// Термин выполнения
				private TextInput deadlineDate_Input() 			{ return new TextInput(driver, By.id("530")); }
			}
			
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String projectType = "Проект";					// 'Проект'
				private String author = "Петров П.П";					// 'Автор'
				private String resolutionDate = "02.01.2020";			// 'Дата резолюции'
				private String resolution = "Резолюція_";				// 'Резолюция'
				private String deadlineDate = "03.01.2020";				// 'Термин выполнения'
			}
		}
		
		private class CorrespondentCheck_Elements
		{
			// Кнопка добавления
			private Button search_Button()   				{ return new Button(driver, By.id("chckBtn")); }	
		
			// Поп-ап
			private Custom info_PopUp()   					{ return new Custom(driver, By.id("dialogText")); }	
			
			// Кнопка закрытия
			private Button close_Button()   				{ return new Button(driver, By.xpath("//input[(@onclick='CloseDialog()') and (@value='Закрити')]")); }
		
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String noMatches_Info = "За відповідними датою та номером в базі даних не існує реєстраційної картки вхідного документа";
			}
		}
		
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
			// Значения, которые будут использоваться для заполнения элементов
			public class Values
			{
				public String info = "Дані на сторінці були змінені. Перейти до іншої сторінки без збереження даних?";
			}
		}
	}	
}
