package com.esco.core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.Link;
import com.esco.core.web.elements.Text;
import com.esco.core.web.elements.TextInput;

public abstract class CommonElements 
{
	// Разные элементы
	public static class Other_Elements
	{
		// Ссылка выхода из системы
		public Button userOut_Button(WebDriver driver)
		{
			return new Button(driver, By.xpath("//a[@href='/Account/LogOff']"));
		}
		
		// Ссылка возврата на главную страничку(В header(е))
		public Link backToMain_Link(WebDriver driver)
		{
			return new Link(driver, By.className("header-link"));
		}
		
		// <div> в котором лежит главная меню
		public WebElement mainMenu_Div(WebDriver driver)
		{
			return driver.findElement(By.className("main-menu"));
		}
	}
	
	// Дерево подразделений
	public static class UnitsTree_Elements
	{
		// Аккордеон
		public Custom units_Accordion(WebDriver driver)			{ return new Custom(driver, By.id("departments-accordion")); }
		
		// Само дерево
		public Custom tree_Div(WebDriver driver)				{ return new Custom(driver, By.id("tree")); }
	}

	// Аккордеон фильтрации
	public static class FiltrationControl_Elements
	{
		// Аккордеон
		public Custom accordion_Div(WebDriver driver)			{ return new Custom(driver, By.id("generalFilter")); }
		
		// Название поля в гриде
		public Select fieldName_Select(WebDriver driver)		{ return new Select(driver.findElement(By.xpath("//select[contains(@id, 'name')]"))); }
		
		// Вид соответствия
		public Select matchType_Select(WebDriver driver)		{ return new Select(driver.findElement(By.xpath("//select[contains(@id, 'operation')]"))); }
	
		// Значение
		public TextInput value_TextInput(WebDriver driver)		{ return new TextInput(driver, By.xpath("//input[contains(@id, 'value')]")); }
	
		// Кнопка 'Шукати'
		public Button search_Button(WebDriver driver)   		{ return new Button(driver, By.id("buttonSearch")); }	
		
		// Кнопка 'Очистити'
		public Button clear_Button(WebDriver driver)   			{ return new Button(driver, By.id("buttonClear")); }	
	}
	
	// Грид карточек
	public static class BaseGrid_Elements
	{
		// Кнопкаы "Додати"
		public Button add_Button(WebDriver driver)				{ return new Button(driver, By.id("btnAdd")); }
		
		// "Завантаження"
		public WebElement grid_Body(WebDriver driver)			{ return driver.findElement(By.xpath("//table[@id='grid']/tbody")); }
		
		// "Завантаження"
		public Custom download_Div(WebDriver driver)			{ return new Custom(driver, By.id("load_grid")); }
		
		// Кнопка просмотра
		public Button view_Button(WebDriver driver)   			{ return new Button(driver, By.xpath("//td[@aria-describedby='grid_view']")); }
		
		// Кнопка редактирования
		public Button edit_Button(WebDriver driver)   			{ return new Button(driver, By.xpath("//td[@aria-describedby='grid_edit']")); }
			
		// Кнопка удаления
		public Button delete_Button(WebDriver driver)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid_del']")); }
		
		// Элементы пагинатора
		public static class Pager_Elements
		{
			// Полное значение
			public TextInput pageNumber_Input(WebDriver driver){return new TextInput(driver, By.className("ui-pg-input"));}
		}
	}
	
	// Элементы карточек
	public static class Card_Elements
	{
		// Общие элементы
		public static class General_Elements
		{
			// Тайтл вкладки(Индекс + дата рег.)
			public Text card_Header(WebDriver driver)							{ return new Text(driver, By.xpath("//*[contains(@class, 'doc-header')]")); }
			
			// Кнопка 'Зберегти'
			public Button save_Button(WebDriver driver)   						{ return new Button(driver, By.id("saveBtnTop")); }	
			
			// Кнопка 'Закрити'
			public Button close_Button(WebDriver driver)   						{ return new Button(driver, By.id("cancelBtnTop")); }	
			
			// Кнопка 'Генерація наступного номера'
			public WebElement numGenerate_Button(WebDriver driver) 				{ return driver.findElement(By.id("btnGenerateNum")); }
			
			// Єлемент меню карточки
			public WebElement inset_Link(WebDriver driver, String inset_Number)	{ return driver.findElement(By.xpath("(//div[@class='item'])[" + inset_Number + "]")); }  
														  						// Где, 'InsetId' - номер вкладки 
			
			// Кнопка 'Додати' значение в словарь
			public Button dictAdd_Button(WebDriver driver, String id) 			{ return new Button(driver, By.id(id + "_btn_add")); }	
		}
		
		// Элементы универсального грида внутри карточки
		public static class Grid
		{			
			// Кнопка добавления
			public Button add_Button(WebDriver driver, String grid_Id)   		{ return new Button(driver, By.id("btnAdd" + grid_Id)); }
		
			// Грид
			public WebElement grid_Body(WebDriver driver, String grid_Id)		{ return driver.findElement(By.xpath("//*[@id='grid" + grid_Id + "']/tbody")); }
			
			// Div 'Завантаження'
			public Custom download_Div(WebDriver driver, String grid_Id)  		{ return new Custom(driver, By.id("load_grid" + grid_Id)); }
				
			// Кнопка редактирования
			public Button edit_Button(WebDriver driver, String grid_Id)   		{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + grid_Id + "_Edit']")); }
				
			// Кнопка удаления
			public Button delete_Button(WebDriver driver, String grid_Id)   	{ return new Button(driver, By.xpath("//td[@aria-describedby='grid" + grid_Id + "_Del']")); }
		}
		
		// Поп-апы
		public static class Pop_Ups extends CommonElements.General_PopUps
		{
			// Сообщение при удалении записи
			public String deletion_Message = "Обраний документ буде видалено, продовжити?";
			
			// Сообщение при уходе без сохранения данных
			public String goAway_Message = "Дані на сторінці були змінені. Перейти до іншої сторінки без збереження даних?";
			
			// Сообщение при уходе без сохранения данных
			public String cardGoAway_Message = "Дані на сторінці були змінені. Закрити картку без збереження даних?";
			
			// Сообщение при добавлении записи в словарь
			public String dictValueAdd_AskMessage(String value) 
			{
				String message = "Ви дійсно хочете додати значення '" + value + "' в довідник?";
				return message;
			}
			
			// Сообщение при успешном добавлении записи в словарь
			public String dictValueAdd_SuccessMessage(String dictName) 
			{
				String message = "Значення успішно додано в довідник '" + dictName + "'";
				return message;
			}
		}
		
		// Блок файлов
		public static class Card_Files_Elements extends Pop_Ups
		{
			// Кнопка редактирования
			public Button fileDownload_Button(WebDriver driver)   		{ return new Button(driver, By.cssSelector(".btn.btn-filedownload-s")); }									
			
			// Кнопка загрузки файла
			public TextInput fileUpload_Button(WebDriver driver)		{return new TextInput(driver, By.id("fileSource"));}
		}
		
		// Блок связанных документов
		public static class Card_LinkedDocs_Elements
		{
			// Кнопка 'Додати зв'язок'
			public Button addLink_Button(WebDriver driver)   				{ return new Button(driver, By.id("btnAddLink")); }
			
			// Кнопка 'Видалити зв'язок'
			public Button deleteLink_Button(WebDriver driver)   			{ return new Button(driver, By.id("btnDelLink")); }
			
			// Индекс
			public TextInput index_TextInput(WebDriver driver)				{return new TextInput(driver, By.id("index"));}
			
			// Год
			public TextInput year_TextInput(WebDriver driver)				{return new TextInput(driver, By.id("year"));}
			
			// Кнопка 'Пошук'
			public Button search_Button(WebDriver driver)   				{ return new Button(driver, By.id("btnSearch")); }
			
			// Найденный документ
			public Text findedDoc_Text(WebDriver driver, String docName)	{return new Text(driver, By.xpath("//*[contains(@id, '_anchor') " +
																											  "and text()='" + docName + "']"));}
			
			// Кнопка 'Прив'язати документ'
			public Button linkDoc_Button(WebDriver driver)   				{ return new Button(driver, By.id("btnLink")); }
			
			
			// Добавленный документ
			public Text addedDoc_Text(WebDriver driver)						{return new Text(driver, By.xpath("//a[@href='#' and contains(@id, '_anchor')]"));}
			
			// Информация о документе
			public WebElement docInfo_Div(WebDriver driver)					{return driver.findElement(By.id("linkPreview"));}
			
			// Информация о документе
			public WebElement docInfo_Tbody(WebDriver driver)				{return driver.findElement(By.xpath("//div[@id='linkPreview']/table/tbody"));}
		}
	}
	
	// Общие элементы поп-апов
	public static class General_PopUps
	{
		// Информационный поп-ап
		public Custom info_PopUp(WebDriver driver)   				{ return new Custom(driver, By.cssSelector(".ui-dialog-content.ui-widget-content")); }	
		
		// Кнопка 'Так'
		public Button yes_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Так')]")); }
		
		// Кнопка 'Ні'
		public Button no_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Ні')]")); }
		
		// Кнопка 'Закрити'
		public Button close_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[(@class='ui-button-text') and contains(text(), 'Закрити')]")); }
		
		// Кнопка сохранения
		public Button save_Button(WebDriver driver)   				{ return new Button(driver, By.xpath("//span[@class='ui-button-text' and text()='Зберегти']")); }
	}
}
