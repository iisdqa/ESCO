package com.esco.core.web.pages.energy_audit.objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CommonElements;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.CheckBox;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.TextInput;

public class Objects_PropertyAttributesPage extends WebPage<Objects_PropertyAttributesPage>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/Edit/82/";
	
	public Objects_PropertyAttributesPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Objects_PropertyAttributesPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().close_Button(driver).isAvailable();
	}
	
	public void attributes_Set()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput ownerName = new Elements().ownerName_Input();
		TextInput regNum = new Elements().regNum_Input();
		TextInput inventoryRegNum = new Elements().inventoryRegNum_Input();
		TextInput objectId = new Elements().objectId_Input();
		TextInput startDate = new Elements().startDate_Input();
		TextInput startDoc = new Elements().startDoc_Input();
		TextInput startDocDate = new Elements().startDocDate_Input();
		TextInput startDocNum = new Elements().startDocNum_Input();
		CheckBox energyPassportIndicator = new Elements().energyPassportIndicator_CheckBox();
		//endregion
		
		// Проверка недоступности кнопки сохранения
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		
		// Заполнение полей
		ownerName.inputText(new Elements().new Values().ownerName);
		regNum.inputText(String.valueOf(new Elements().new Values().regNum));
		inventoryRegNum.inputText(new Elements().new Values().inventoryRegNum);
		objectId.inputText(new Elements().new Values().objectId);
		startDate.click();
		startDate.inputText(new Elements().new Values().startDate);
		startDoc.inputText(new Elements().new Values().startDoc);
		startDocDate.click();
		startDocDate.inputText(new Elements().new Values().startDocDate);
		startDocNum.inputText(new Elements().new Values().startDocNum);
		energyPassportIndicator.click();
		
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(3);
		
		// Ожидание загрузки странички
		waitUntilClickable(ownerName);
	}

	public void attributes_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput ownerName = new Elements().ownerName_Input();
		TextInput regNum = new Elements().regNum_Input();
		TextInput inventoryRegNum = new Elements().inventoryRegNum_Input();
		TextInput objectId = new Elements().objectId_Input();
		TextInput startDate = new Elements().startDate_Input();
		TextInput startDoc = new Elements().startDoc_Input();
		TextInput startDocDate = new Elements().startDocDate_Input();
		TextInput startDocNum = new Elements().startDocNum_Input();
		Custom energyPassportIndicator = new Elements().energyPassportIndicator_Value();
		//endregion
		
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// Проверка сохраненных значений
		assertThat(ownerName.getAttribute("value"), is(equalTo(new Elements().new Values().ownerName)));
		assertThat(regNum.getAttribute("value"), is(equalTo(new Elements().new Values().regNum)));
		assertThat(inventoryRegNum.getAttribute("value"), is(equalTo(new Elements().new Values().inventoryRegNum)));
		assertThat(objectId.getAttribute("value"), is(equalTo(new Elements().new Values().objectId)));
		assertThat(startDate.getAttribute("value"), is(equalTo(new Elements().new Values().startDate)));
		assertThat(startDoc.getAttribute("value"), is(equalTo(new Elements().new Values().startDoc)));
		assertThat(startDocDate.getAttribute("value"), is(equalTo(new Elements().new Values().startDocDate)));
		assertThat(startDocNum.getAttribute("value"), is(equalTo(new Elements().new Values().startDocNum)));
		assertThat(energyPassportIndicator.getAttribute("value"), is("1"));
	}
	
	public void inset_Edit()
	{
		//region Variables	
		TextInput ownerName = new Elements().ownerName_Input();
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// Редактироваить имя
		ownerName_Change();
				
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(2);
		
		// Ожидание загрузки странички
		waitUntilClickable(ownerName);
	}
	
	public void ownerName_Change()
	{
		//region Variables	
		TextInput ownerName = new Elements().ownerName_Input();
		//endregion
		
		ownerName.inputText("2");
	}
	
	// Проверка отредактированной карточки
	public void editedInset_Check()
	{
		//region Variables	
		TextInput ownerName = new Elements().ownerName_Input();
		//endregion
		
		// Проверка почты
		assertThat(ownerName.getAttribute("value"), is(equalTo(new Elements().new Values().ownerName + "2")));
	}
	
	public Objects_CountersPage goTo_Counter_Page(String go_Type)
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
			assertThat(info.getText(), is(equalTo(new Elements().new SaveOrNot_Elements().goAway_Message)));
			
			// Закрытие поп-апа
			yes.click();
			new CommonActions().simpleWait(1);
		}
		
		return new Objects_CountersPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{	
		// 'Назва власника об'єкту'
		private TextInput ownerName_Input()   					{ return new TextInput(driver, By.id("953")); }
		
		// 'Реєстровий номер об'єкту'
		private TextInput regNum_Input()   						{ return new TextInput(driver, By.id("935")); }
		
		// 'Інвентарний номер об'єкту'
		private TextInput inventoryRegNum_Input()   			{ return new TextInput(driver, By.id("936")); }	
	
		// 'ID об'єкту в системі «Майно»'
		private TextInput objectId_Input()      				{ return new TextInput(driver, By.id("950")); }
	
		// 'Дата введення об'єкту в експлуатацію'
		private TextInput startDate_Input()      				{ return new TextInput(driver, By.id("952")); }
				
		// 'Документ прийняття об'єкту на облік'
		private TextInput startDoc_Input()     	    			{ return new TextInput(driver, By.id("954")); }
	
		// 'Дата документа прийняття об'єкту на облік'
		private TextInput startDocDate_Input() 					{ return new TextInput(driver, By.id("956")); }
		
		// 'Номер документа прийняття об'єкту на облік'
		private TextInput startDocNum_Input() 					{ return new TextInput(driver, By.id("955")); }
		
		// Радиобаттон 'Наявність енергетичного паспорту'
		private CheckBox energyPassportIndicator_CheckBox()		{ return new CheckBox(driver, By.id("951_cb")); }

		// Значение радиобаттона 'Наявність енергетичного паспорту'
		private Custom energyPassportIndicator_Value()			{ return new Custom(driver, By.id("951")); }
		
		// Значения, которые будут использоваться для заполнения элементов
		public class Values
		{		
			protected String ownerName = "Тестовий"; 			// 'Назва власника об'єкту'
			protected String regNum = "111"; 					// 'Реєстровий номер об'єкту'
			protected String inventoryRegNum = "222"; 			// 'Інвентарний номер об'єкту'
			protected String objectId = "333"; 					// 'ID об'єкту в системі «Майно»'
			protected String startDate = "01.01.2010"; 			// 'Дата введення об'єкту в експлуатацію'
			protected String startDoc = "Заява"; 				// 'Документ прийняття об'єкту на облік'
			protected String startDocDate = "02.01.2010"; 		// 'Дата документа прийняття об'єкту на облік'
			protected String startDocNum = "444"; 				// 'Номер документа прийняття об'єкту на облік'
		}
		
		// Поп-ап сообщения об уходе со странички, на которой есть не сохраненные данные
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
