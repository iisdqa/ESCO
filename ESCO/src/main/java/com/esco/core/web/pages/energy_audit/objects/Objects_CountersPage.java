package com.esco.core.web.pages.energy_audit.objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CommonElements;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.Text;
import com.esco.core.web.elements.TextInput;

public class Objects_CountersPage extends WebPage<Objects_CountersPage>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/Edit/82/";
	
	public Objects_CountersPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Objects_CountersPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().close_Button(driver).isAvailable();
	}
	
	public void info_Set()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput toolsIndicator = new Elements().toolsIndicator_Input(false);
		WebElement comment = new Elements().comment_Text();
		//endregion
		
		// Проверка недоступности кнопки сохранения
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		
		// Заполнение полей
		toolsIndicator.inputText(new Elements().new Values().toolsIndicator);
		new CommonActions().autoCompleteValue_Set(driver, toolsIndicator, 1);
		comment.click();
		sendKeys(new Elements().new Values().comment);
	}
	
	public void counter_Add()
	{
		//region Variables
		String grid_Id = new Elements().new Counters().new Grid().grid_Id;
		String consumptionType = new Elements().new Counters().new Values().consumptionType;
		String counterName = new Elements().new Counters().new Values().counterName;
		String serialNum = new Elements().new Counters().new Values().serialNum;
		String inventoryNum = new Elements().new Counters().new Values().inventoryNum;
		String location = new Elements().new Counters().new Values().location;
		String startYear = new Elements().new Counters().new Values().startYear;
		String expirationDate = new Elements().new Counters().new Values().expirationDate;
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// Открыть поп-ап добавления
		new Elements().new Counters().new Grid().add_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Counters().new PopUp().consumptionType_Input());
		
		// Проверить подтягивание значения в поле "Діє з"
		assertThat(new Elements().new Counters().new PopUp().startYear_Input().getAttribute("value"), is(equalTo("2000")));
		
		// Заполнение полей
		new Elements().new Counters().new PopUp().consumptionType_Input().inputText(consumptionType);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new Counters().new PopUp().consumptionType_Input(), 1);
		
		new Elements().new Counters().new PopUp().counterName_Input().inputText(counterName);
		new CommonActions().autoCompleteValue_Set(driver, new Elements().new Counters().new PopUp().counterName_Input(), 1);
		
		new Elements().new Counters().new PopUp().serialNum_Input().inputText(serialNum);
		new Elements().new Counters().new PopUp().inventoryNum_Input().inputText(inventoryNum);
		new Elements().new Counters().new PopUp().location_Input().inputText(location);
		
		new Elements().new Counters().new PopUp().startYear_Input().clear();
		new Elements().new Counters().new PopUp().startYear_Input().inputText(startYear);
		
		new Elements().new Counters().new PopUp().expirationDate_Input().click();
		new Elements().new Counters().new PopUp().expirationDate_Input().inputText(expirationDate);
		
		// Сохранить
		new Elements().new Counters().new PopUp().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		save.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Counters().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void counter_Check(String checkType)
	{
		//region Variables
		String grid_Id = new Elements().new Counters().new Grid().grid_Id;
		WebElement grid = new Elements().new Counters().new Grid().grid_Body(driver, grid_Id);
		String consumptionType = new Elements().new Counters().new Values().consumptionType;
		String counterName = new Elements().new Counters().new Values().counterName;
		String serialNum = new Elements().new Counters().new Values().serialNum;
		String inventoryNum = new Elements().new Counters().new Values().inventoryNum;
		if (checkType != "add") serialNum = new Elements().new Counters().new Values().serialNum + "2";
		//endregion
		
		// Определение ожидаемых значений
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",										  
										  consumptionType, 
										  counterName, 
										  serialNum, 
										  inventoryNum,
										  ""};
		
		// Удалить лишние поля для просмотровой формы
		if(checkType == "view")
		{
			int[] elements_ToRemove = new int[]{ ExpectedValues.length - 1, 0};
			ExpectedValues[0] = new CustomMethods().new Grid().arrayElements_Remove(ExpectedValues[0], elements_ToRemove);
		}
		
		// Вытянуть последнее значения из грида
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// Проверка значений грида
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void counter_Edit()
	{
		//region Variables
		String grid_Id = new Elements().new Counters().new Grid().grid_Id;
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// Открыть поп-ап редактирования
		new Elements().new Counters().new Grid().edit_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Counters().new PopUp().consumptionType_Input());
		
		// Изменить комментарий
		new Elements().new Counters().new PopUp().serialNum_Input().inputText("2");
		
		// Сохранить
		new Elements().new Counters().new PopUp().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		save.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Counters().new Grid().download_Div(driver, grid_Id), false);
	}

	public void inset_Save()
	{
		//region Variables	
		String grid_Id = new Elements().new Counters().new Grid().grid_Id;
		Button save = new Elements().save_Button(driver);
		TextInput toolsIndicator = new Elements().toolsIndicator_Input(false);
		//endregion
		
		// Сохранить введенную инфу
		save.click();
		new CustomMethods().simpleWait(2);
		toolsIndicator.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Counters().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void info_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput toolsIndicator = new Elements().toolsIndicator_Input(false);
		WebElement comment = new Elements().comment_Frame();
		String commentValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, comment);
		//endregion
			
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// Проверка сохраненных значений
		assertThat(toolsIndicator.getAttribute("value"), is(equalTo(new Elements().new Values().toolsIndicator)));
		assertThat(commentValue, is(equalTo(new Elements().new Values().comment)));
	}
	
	public void inset_Edit()
	{
		//region Variables	
		TextInput toolsIndicator = new Elements().toolsIndicator_Input(false);
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// Редактироваить имя
		comment_Change();
				
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(2);
		
		// Ожидание загрузки странички
		waitUntilClickable(toolsIndicator);
	}
	
	public void comment_Change()
	{
		//region Variables	
		WebElement comment = new Elements().comment_Text();
		//endregion
		
		comment.click();
		sendKeys("2");
	}
	
	// Проверка отредактированной карточки
	public void editedInset_Check()
	{
		//region Variables	
		WebElement comment = new Elements().comment_Frame();
		String commentValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, comment) ;
		//endregion
		
		// Проверка почты
		assertThat(commentValue, is(equalTo(new Elements().new Values().comment + "2")));
	}
	
	public void infoView_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput toolsIndicator = new Elements().toolsIndicator_Input(true);
		Text comment = new Elements().commentView_Text();
		//endregion
			
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// Проверка сохраненных значений
		assertThat(toolsIndicator.getAttribute("value"), is(equalTo(new Elements().new Values().toolsIndicator)));
		assertThat(comment.getAttribute("value"), is(equalTo(new Elements().new Values().comment)));
	}

	public Objects_Page card_Close(String close_Type)
	{
		//region Variables	
		Custom info = new Elements().new Counters().new PopUp().info_PopUp(driver);
		Button yes = new Elements().new Counters().new PopUp().yes_Button(driver);
		//endregion
		
		// Закрытие редактирования карточки
		new Elements().close_Button(driver).click();
		new CommonActions().simpleWait(1);
		
		// С проверкой поп-апа при уходе с не сохраненными данными
		if(close_Type == "tricky")
		{
			//Отказ в сохранении данных
			new CommonActions().simpleWait(1);
			waitUntilClickable(info);
			
			// Проверка сообщеия
			assertThat(info.getText(), is(equalTo(new Elements().new SaveOrNot_Elements().cardGoAway_Message)));
			
			// Закрытие поп-апа
			yes.click();
			new CommonActions().simpleWait(1);
		}
		
		return new Objects_Page(driver).waitUntilAvailable();
	}
	
	public Objects_FilesPage goTo_Files_Page(String go_Type)
	{
		//region Variables	
		WebElement insetLink = new Elements().inset_Link(driver, "4");
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
			assertThat(info.getText(), is(equalTo(new Elements().new SaveOrNot_Elements().goAway_Message)));
			
			// Закрытие поп-апа
			yes.click();
			new CommonActions().simpleWait(1);
		}
		
		return new Objects_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{	
		// 'Наявність приладів обліку'
		private TextInput toolsIndicator_Input(Boolean viewMode)		
		{ 
			String id = "1168";
			if(viewMode == false) id = id + "_auto";
			return new TextInput(driver, By.id(id)); 
		}
		
		// 'Коментар'
		private WebElement comment_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_1167")); return element; }
		
		// 'Коментар' фрэйм
		private WebElement comment_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_1167_DesignIFrame")); }	
		
		// 'Комментарий' най форме просмотра
		private Text commentView_Text() 				{ return new Text(driver, By.id("1167")); }
		
		// Значения, которые будут использоваться для заполнения элементов
		public class Values
		{		
			protected String toolsIndicator = "Розрахунок за спожиті енергоресурси " + 
							 "здійснюється за показами існуючих вузлів обліку тепло"; 		// 'Назва власника об'єкту'
			protected String comment = "Тестовий"; 											// 'Реєстровий номер об'єкту'
		}
		
		// Блок счетчиков
		private class Counters
		{
			// Грид
			private class Grid extends CommonElements.Card_Elements.Grid
			{
				private String grid_Id = "1169";
			}
			
			// Поп-ап создания/редактирования счетчиков
			private class PopUp extends CommonElements.General_PopUps
			{
				// 'Вид споживання'
				private TextInput consumptionType_Input()		{ return new TextInput(driver, By.id("1170_auto")); }
				
				// 'Назва (марка) лічильника'
				private TextInput counterName_Input()			{ return new TextInput(driver, By.id("1171_auto")); }
				
				// 'Серійний номер'
				private TextInput serialNum_Input()				{ return new TextInput(driver, By.id("1172")); }
				
				// 'Інвентарний номер'
				private TextInput inventoryNum_Input()			{ return new TextInput(driver, By.id("1173")); }
				
				// 'Місце розташування'
				private TextInput location_Input()				{ return new TextInput(driver, By.id("1174")); }
				
				// 'Діє з'
				private TextInput startYear_Input()				{ return new TextInput(driver, By.id("1175")); }
				
				// 'Дата припинення єксплуатації'
				private TextInput expirationDate_Input()		{ return new TextInput(driver, By.id("1176")); }
			}
			// Значения, которые будут использоваться для заполнения элементов
			private class Values
			{
				private String consumptionType = "Газ"; 		// 'Вид споживання'
				private String counterName = "Electronix"; 		// 'Назва (марка) лічильника'
				private String serialNum = "1"; 				// 'Серійний номер'
				private String inventoryNum = "2"; 				// 'Інвентарний номер'
				private String location = "Стіна"; 				// 'Місце розташування'
				private String startYear = "2010"; 				// 'Діє з'
				private String expirationDate = "01.01.2020"; 	// 'Дата припинення єксплуатації'
			}
		}
		
		// Поп-ап сообщения об уходе со странички, на которой есть не сохраненные данные
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
