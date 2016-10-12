package com.esco.core.web.pages.energy_audit.objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CommonElements;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.Cell;
import com.esco.core.web.elements.CheckBox;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.Text;
import com.esco.core.web.elements.TextInput;

public class Objects_RegistrationPage extends WebPage<Objects_RegistrationPage>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/";
	
	public Objects_RegistrationPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Objects_RegistrationPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().close_Button(driver).isAvailable();
	}
	
	public Objects_RegistrationPage cardInfo_Set()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput name = new Elements().name_Input();
		TextInput year = new Elements().year_Input();
		TextInput group = new Elements().group_Input(false);
		TextInput objectType = new Elements().objectType_Input(false);
		TextInput projectType = new Elements().projectType_Input(false);
		Select modelOrientation = new Elements().modelOrientation_Select();
		TextInput adress = new Elements().Adress_Input();
		WebElement comment = new Elements().comment_Text();
		TextInput koatuu = new Elements().new Koatuu_Elements().Koatuu_Input(false);
		//endregion
		
		// Проверка недоступности кнопки сохранения
		// Проверка подтягивания года
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		assertThat(year.getAttribute("value"), is(equalTo("1946")));
		
		// Заполнение обычных полей
		name.inputText(new Elements().new Values().name);
		year.inputText(String.valueOf(new Elements().new Values().year));
		group.inputText(new Elements().new Values().group);
		new CommonActions().autoCompleteValue_Set(driver, group, 1);
		objectType.inputText(new Elements().new Values().objectType);
		new CommonActions().autoCompleteValue_Set(driver, objectType, 1);
		projectType.inputText(new Elements().new Values().projectType);
		modelOrientation.selectByVisibleText(new Elements().new Values().modelOrientation);
		adress.inputText(new Elements().new Values().adress);
		
		// Заполнение текстовых(многострочных) полей
		comment.click();
		sendKeys(new Elements().new Values().comment);
		
		// Выбор КОАТУУ из словаря
		koatuu_Set();
		
		// Проверка установки КОАТУУ
		assertThat(koatuu.getAttribute("value"), is(equalTo(new Elements().new Koatuu_Elements().new Values().district_Code)));
		koatuuSet_Check(new Elements().new Koatuu_Elements().new Values().district_Name);
		
		// Установка геолокации
		geolocation_Set();
		geolocationSet_Check();
		
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(3);
				
		return new Objects_RegistrationPage(driver).waitUntilAvailable();		
	}
	
	private void koatuu_Set()
	{
		//region Variables	
		Button choose = new Elements().new Koatuu_Elements().choose_Button();
		Button set = new Elements().new Koatuu_Elements().new Choose_PopUp().set_Button();
		Button plus = new Elements().new Koatuu_Elements().new Choose_PopUp().plus_Button();
		Cell district = new Elements().new Koatuu_Elements().new Choose_PopUp().district_Cell();
		//endregion
		
		// Открыть поп-ап выбора КОАТУУ
		choose.click();
		waitUntilClickable(set);
		
		// Установить КОАТУУ
		plus.click();
		new CustomMethods().simpleWait(1);
		waitForBlockStatus(new Elements().new Koatuu_Elements().new Choose_PopUp().download_Div(), false);
		district.click();
		set.click();
		choose.waitUntilAvailable();
	}
	
	private void koatuuSet_Check(String cityDistrict)
	{
		//region Variables	
		TextInput region = new Elements().new Koatuu_Elements().region_Input();
		TextInput district = new Elements().new Koatuu_Elements().district_Input();
		TextInput city = new Elements().new Koatuu_Elements().city_Input();
		TextInput cityDistrict_Input = new Elements().new Koatuu_Elements().cityDistrict_Input();
		//endregion
		
		// Проверка подтягивания значений
		assertThat(region.getAttribute("value"), is(equalTo("")));
		assertThat(region.getAttribute("readonly"), is(equalTo(true)));
		assertThat(district.getAttribute("value"), is(equalTo("")));
		assertThat(district.getAttribute("readonly"), is(equalTo(true)));
		assertThat(city.getAttribute("value"), is(equalTo(new Elements().new Koatuu_Elements().new Values().city)));
		assertThat(city.getAttribute("readonly"), is(equalTo(true)));
		assertThat(cityDistrict_Input.getAttribute("value"), is(equalTo(cityDistrict)));
		assertThat(cityDistrict_Input.getAttribute("readonly"), is(equalTo(true)));
	}
	
	private void geolocation_Set()
	{
		//region Variables	
		Button locationSet = new Elements().findAdress_Button();
		//endregion
		
		// Выбор локации
		locationSet.click();
		new CustomMethods().simpleWait(2);
	}
	
	private void geolocationSet_Check()
	{
		//region Variables	
		TextInput latitude = new Elements().latitude_Input();
		TextInput longitude = new Elements().longitude_Input();
		//endregion
		
		// Проверка подтягивания значений
		assertThat(latitude.getAttribute("value"), is(equalTo(new Elements().new Values().latitude)));
		assertThat(longitude.getAttribute("value"), is(equalTo(new Elements().new Values().longitude)));
	}
	
	public void card_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput name = new Elements().name_Input();
		TextInput year = new Elements().year_Input();
		TextInput group = new Elements().group_Input(false);
		TextInput objectType = new Elements().objectType_Input(false);
		TextInput projectType = new Elements().projectType_Input(false);
		Select modelOrientation = new Elements().modelOrientation_Select();
		TextInput koatuu = new Elements().new Koatuu_Elements().Koatuu_Input(false);
		TextInput adress = new Elements().Adress_Input();
		WebElement comment = new Elements().comment_Frame();
		//endregion
		
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// Проверка обычных текстовых полей
		assertThat(name.getAttribute("value"), is(equalTo(new Elements().new Values().name)));
		assertThat(year.getAttribute("value"), is(equalTo(new Elements().new Values().year)));
		assertThat(group.getAttribute("value"), is(equalTo(new Elements().new Values().group)));
		assertThat(objectType.getAttribute("value"), is(equalTo(new Elements().new Values().objectType)));
		assertThat(projectType.getAttribute("value"), is(equalTo(new Elements().new Values().projectType)));
		assertThat(modelOrientation.getFirstSelectedOption(), is(equalTo(new Elements().new Values().modelOrientation)));

		// Проверка блока КОАТУУ
		assertThat(koatuu.getAttribute("value"), is(equalTo(new Elements().new Koatuu_Elements().new Values().district_Code)));
		koatuuSet_Check(new Elements().new Koatuu_Elements().new Values().district_Name);
		
		// Проверка блока геолокации
		assertThat(adress.getAttribute("value"), is(equalTo(new Elements().new Values().adress)));
		geolocationSet_Check();
		
		// Проверка текстовых(многострочных) полей
		String shortSummaryValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, comment);
		assertThat(shortSummaryValue, is(equalTo(new Elements().new Values().comment)));
	}
	
	public void card_Edit()
	{
		//region Variables	
		TextInput year = new Elements().year_Input();
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// Редактироваить год
		year.clear();
		year.inputText(String.valueOf(new Elements().new Values().year + 1));
				
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(2);
		
		// Ожидание загрузки странички
		waitUntilClickable(year);
	}
	
	// Проверка отредактированной карточки
	public void editedCard_Check()
	{
		//region Variables	
		TextInput year = new Elements().year_Input();
		//endregion
		
		// Проверка почты
		assertThat(year.getAttribute("value"), is(equalTo(String.valueOf(new Elements().new Values().year + 1))));
	}
	
	public void koatuuEdit_Check()
	{
		//region Variables	
		TextInput koatuu = new Elements().new Koatuu_Elements().Koatuu_Input(false);
		TextInput city = new Elements().new Koatuu_Elements().city_Input();
		//endregion
		
		// Редактироваить КОАТУУ
		koatuu.clear();
		koatuu.inputText(new Elements().new Koatuu_Elements().new Values().wrongDistrict_Code);
		city.click();
		
		// Проверка подтягивания значения
		koatuuSet_Check(new Elements().new Koatuu_Elements().new Values().wrongDistrict_Name);
	}
	
	public void uniqueModel_Check()
	{
		//region Variables
		CheckBox model = new Elements().model_CheckBox();
		Button save = new Elements().save_Button(driver);
		String message = "Для даного «Типу проекту» вже існує один об’єкт з позначкою «еталон». Назва об'єкту:";
		//endregion
		
		// Установить чек-бокс
		model.click();
		
		// Проверка появления ошибки
		save.click();
		waitUntilClickable(new Elements().new SaveOrNot_Elements().info_PopUp(driver));
		assertThat(new Elements().new SaveOrNot_Elements().info_PopUp(driver).getText(), is(containsString(message)));
		new Elements().new SaveOrNot_Elements().close_Button(driver).click();;
		model.waitUntilAvailable();
	}
	
	// Проверка хэдера карточки
	public void cardHeader_Check()
	{
		//region Variables	
		String expected_Header = "Об’єкти";
		//endregion
		
		new CommonActions().cardHeader_Check(driver, expected_Header);
	}
	
	// Возврат ко всем документам
	public Objects_Page card_Close()
	{
		// Закрытие редактирования карточки
		new Elements().close_Button(driver).click();
		new CommonActions().simpleWait(1);
		
		return new Objects_Page(driver).waitUntilAvailable();
	}
	
	public Objects_PropertyAttributesPage goTo_PropertyAttributes_Page(String go_Type)
	{
		//region Variables	
		WebElement insetLink = new Elements().inset_Link(driver, "2");
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
		
		return new Objects_PropertyAttributesPage(driver).waitUntilAvailable();
	}
	
	public void viewCard_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput name = new Elements().name_Input();
		TextInput year = new Elements().year_Input();
		TextInput group = new Elements().group_Input(true);
		TextInput objectType = new Elements().objectType_Input(true);
		TextInput projectType = new Elements().projectType_Input(true);
		Custom modelCheckBox = new Elements().modelCheckBox_Value();
		WebElement modelOrientation =(WebElement) new Elements().modelOrientation_Select();
		TextInput koatuu = new Elements().new Koatuu_Elements().Koatuu_Input(true);
		TextInput adress = new Elements().Adress_Input();
		Text comment = new Elements().commentView_Text();
		//endregion
		
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// Проверка обычных текстовых полей
		assertThat(name.getAttribute("value"), is(equalTo(new Elements().new Values().name)));
		assertThat(year.getAttribute("value"), is(equalTo(new Elements().new Values().year)));
		assertThat(group.getAttribute("value"), is(equalTo(new Elements().new Values().group)));
		assertThat(objectType.getAttribute("value"), is(equalTo(new Elements().new Values().objectType)));
		assertThat(projectType.getAttribute("value"), is(equalTo(new Elements().new Values().projectType)));
		assertThat(modelCheckBox.getAttribute("value"), is(equalTo("0")));
		assertThat((modelOrientation).getAttribute("value"), is(equalTo(new Elements().new Values().modelOrientation)));
		assertThat(koatuu.getAttribute("value"), is(equalTo(new Elements().new Koatuu_Elements().new Values().district_Code)));
		assertThat(adress.getAttribute("value"), is(equalTo(new Elements().new Values().adress)));
		geolocationSet_Check();
		assertThat(comment.getText(), is(equalTo(new Elements().new Values().comment)));
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{	
		// 'Название'
		private TextInput name_Input()   				{ return new TextInput(driver, By.id("937")); }
		
		// 'Год'
		private TextInput year_Input()   				{ return new TextInput(driver, By.id("938")); }
		
		// 'Группа типов объектов'
		private TextInput group_Input(Boolean viewMode)
		{
			String id = "939";
			if(viewMode == false) id = id + "_auto";
			return new TextInput(driver, By.id(id)); 
		}	
	
		// 'Тип объекта'
		private TextInput objectType_Input(Boolean viewMode)      		
		{ 
			String id = "940";
			if(viewMode == false) id = id + "_auto";
			return new TextInput(driver, By.id(id)); 
		}
	
		// 'Тип проекта'
		private TextInput projectType_Input(Boolean viewMode)      		
		{ 
			String id = "941";
			if(viewMode == false) id = id + "_auto";
			return new TextInput(driver, By.id(id)); 
		}
				
		// 'Эталон'
		private CheckBox model_CheckBox()     	    	{ return new CheckBox(driver, By.id("942_cb")); }
		
		// 'Эталон' значение чекбокса
		private Custom modelCheckBox_Value()     	    { return new Custom(driver, By.id("942")); }
		
		// 'Ориентация относительно эталона'
		private Select modelOrientation_Select() 		{ return new Select(driver.findElement(By.id("943"))); } 
	
		// 'Адрес'
		private TextInput Adress_Input() 				{ return new TextInput(driver, By.id("945")); }
		
		// Кнопка 'Знайти'
		private Button findAdress_Button() 				{ return new Button(driver, By.xpath("//input[@onclick='findAddress()']")); }
		
		// 'Широта'
		private TextInput latitude_Input() 				{ return new TextInput(driver, By.id("947")); }
		
		// 'Долгота'
		private TextInput longitude_Input() 			{ return new TextInput(driver, By.id("948")); }
		
		// 'Комментарий' най форме просмотра
		private Text commentView_Text() 				{ return new Text(driver, By.id("949")); }
		
		// 'Комментарий'
		private WebElement comment_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_949")); return element; }
		
		// 'Комментарий' фрэйм
		private WebElement comment_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_949_DesignIFrame")); }	
		
		// Значения, которые будут использоваться для заполнения элементов
		public class Values
		{		
			protected String name = "БЦ Автоматичний"; 								// 'Название'
			protected int year = 2000; 												// 'Год'
			protected String group = "Васильович"; 									// 'Группа'
			protected String objectType = "Будівлі офісні"; 						// 'Тип объекта'
			protected String projectType = "Будівлі органів правосуддя"; 			// 'Тип проекта'
			protected String modelOrientation = "Збігається"; 						// 'Ориентация относительно эталона'
			protected String adress = "вулиця Політехнична 31В"; 					// 'Адрес'
			protected String latitude = "50,447766"; 								// 'Широта'
			protected String longitude = "30,463813"; 								// 'Долгота'
			protected String comment = "Коментар_"; 								// 'Комментарий'
		}
		
		// Элементы блока "КОАТУУ"
		protected class Koatuu_Elements
		{
			// Кнопка открытия списка КОАТУУ
			private Button choose_Button() 					{ return new Button(driver, By.id("944_showkoatuu")); }
			
			// 'КОАТУУ'
			private TextInput Koatuu_Input(Boolean viewMode) 				
			{
				String id = "944";
				if(viewMode == false) id = id + "_koatuu";
				return new TextInput(driver, By.id(id)); 
			}
			
			// 'Область'
			private TextInput region_Input() 				{ return new TextInput(driver, By.id("Area")); }
			
			// 'Район'
			private TextInput district_Input() 				{ return new TextInput(driver, By.id("Region")); }
			
			// 'Город'
			private TextInput city_Input() 					{ return new TextInput(driver, By.id("City")); }
			
			// 'Р-н города'
			private TextInput cityDistrict_Input() 			{ return new TextInput(driver, By.id("CityRegion")); }
			
			// Поп-ап выбора КОАТУУ
			protected class Choose_PopUp
			{
				// Кнопка 'Обрати'
				private Button set_Button() 					{ return new Button(driver, By.id("koatuudict944_showkoatuu-OK")); }
			
				// Div 'Завантаження'
				private Custom download_Div() 					{ return new Custom(driver, By.id("load_koatuudict944_showkoatuu-tree")); }
				
				// Кнопка "+" для элемента "м. Київ"
				private Button plus_Button() 					{ return new Button(driver, By.xpath("//td[@aria-describedby='koatuudict944_showkoatuu-tree_code' "+ 
																								     "and @title='8000000000']/div/div")); }
		
				// Элемент для района 'СОЛОМ'ЯНСЬКИЙ' 
				private Cell district_Cell() 					{ return new Cell(driver, By.xpath("//td[@aria-describedby='koatuudict944_showkoatuu-tree_name' "+ 
     																			 				     "and @title='СОЛОМ'ЯНСЬКИЙ']/div/div")); }
			}
			
			// Используемые значения
			protected class Values
			{
				protected String city = "М.КИЇВ"; 								// 'Город'
				protected String district_Name = "Р-Н.СОЛОМ'ЯНСЬКИЙ"; 			// 'Р-н города, Название'
				protected String district_Code = "8038900000"; 					// 'Р-н города, Код'
				protected String wrongDistrict_Name = "Р-Н.ШЕВЧЕНКІВСЬКИЙ"; 	// 'Р-н города, Название'
				protected String wrongDistrict_Code = "8039100000"; 			// 'Р-н города, Код'
				protected String location = city + ", "+ district_Name + ", " + new Elements().new Values().adress;
			}
		}
		
		// Поп-ап сообщения об уходе со странички, на которой есть не сохраненные данные
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
