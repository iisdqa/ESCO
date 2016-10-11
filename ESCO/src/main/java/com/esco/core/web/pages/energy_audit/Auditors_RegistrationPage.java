package com.esco.core.web.pages.energy_audit;

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
	
	public Auditors_RegistrationPage cardInfo_Set()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput surname = new Elements().surname_Input();
		TextInput name = new Elements().name_Input();
		TextInput patronymic = new Elements().patronymic_Input();
		TextInput phone = new Elements().phone_Input();
		TextInput cellPhone = new Elements().cellPhone_Input();
		TextInput email = new Elements().email_Input();
		WebElement comment = new Elements().comment_Text();
		//endregion
		
		// Проверка недоступности кнопки сохранения
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		
		// Заполнение обычных текстовых полей
		surname.inputText(new Elements().new Values().surname);
		name.inputText(new Elements().new Values().name);
		patronymic.inputText(new Elements().new Values().patronymic);
		phone.inputText(new Elements().new Values().phone);
		cellPhone.inputText(new Elements().new Values().cellPhone);
		email.inputText(new Elements().new Values().email);
		
		// Заполнение текстовых(многострочных) полей
		comment.click();
		sendKeys(new Elements().new Values().comment);
		
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(3);
				
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();		
	}
	
	public void card_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput surname = new Elements().surname_Input();
		TextInput name = new Elements().name_Input();
		TextInput patronymic = new Elements().patronymic_Input();
		TextInput phone = new Elements().phone_Input();
		TextInput cellPhone = new Elements().cellPhone_Input();
		TextInput email = new Elements().email_Input();
		WebElement comment = new Elements().comment_Frame();
		//endregion
		
		// Проверка недоступности кнопки сохранения и генерации номера
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// Проверка обычных текстовых полей
		assertThat(surname.getAttribute("value"), is(equalTo(new Elements().new Values().surname)));
		assertThat(name.getAttribute("value"), is(equalTo(new Elements().new Values().name)));
		assertThat(patronymic.getAttribute("value"), is(equalTo(new Elements().new Values().patronymic)));
		assertThat(phone.getAttribute("value"), is(equalTo(new Elements().new Values().phone)));
		assertThat(cellPhone.getAttribute("value"), is(equalTo(new Elements().new Values().cellPhone)));
		assertThat(email.getAttribute("value"), is(equalTo(new Elements().new Values().email)));
		
		// Проверка текстовых(многострочных) полей
		String shortSummaryValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, comment);
		assertThat(shortSummaryValue, is(equalTo(new Elements().new Values().comment)));
	}
	
	public void card_Edit()
	{
		//region Variables	
		TextInput email = new Elements().email_Input();
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// Редактироваить email
		email_Edit();
				
		// Сохранить введенную инфу
		save.click();
		new CommonActions().simpleWait(3);
		
		// Ожидание загрузки странички
		waitUntilClickable(email);
	}
	
	// Проверка отредактированной карточки
	public void editedCard_Check()
	{
		//region Variables	
		TextInput email = new Elements().email_Input();
		//endregion
		
		// Проверка почты
		assertThat(email.getAttribute("value"), is(equalTo(new Elements().new Values().email + "2")));
	}
	
	public void email_Edit()
	{
		//region Variables	
		TextInput email = new Elements().email_Input();
		//endregion
		
		// Редактироваить email
		email.clear();
		email.inputText(new Elements().new Values().email + "2");
	}
	
	// Проверка хэдера карточки
	public void cardHeader_Check()
	{
		//region Variables	
		String expected_Header = "Аудитор";
		//endregion
		
		new CommonActions().cardHeader_Check(driver, expected_Header);
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
		
		return new Auditors_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ Элементы _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{		
		// 'Прізвище'
		private TextInput surname_Input()   			{ return new TextInput(driver, By.id("962")); }	
		
		// 'Ім'я'
		private TextInput name_Input()   				{ return new TextInput(driver, By.id("963")); }
	
		// 'По батькові'
		private TextInput patronymic_Input()      		{ return new TextInput(driver, By.id("964")); }
	
		// 'Телефон'
		private TextInput phone_Input()      			{ return new TextInput(driver, By.id("966")); }
				
		// 'Мобільний телефон'
		private TextInput cellPhone_Input()     	    { return new TextInput(driver, By.id("967")); }
		
		// 'Електронна пошта'
		private TextInput email_Input() 				{ return new TextInput(driver, By.id("968")); }
	
		// 'Комментарий'
		private WebElement comment_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_969")); return element; }
		
		// 'Комментарий' фрэйм
		private WebElement comment_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_969_DesignIFrame")); }	
		
		// Значения, которые будут использоваться для заполнения элементов
		public class Values
		{
			protected String surname = "Прискіпливий"; 								// 'Фамилия'
			protected String name = "Петро"; 										// 'Имя'
			protected String patronymic = "Васильович"; 							// 'Отчество'
			protected String phone = "(044)3332211"; 								// 'Телефон'
			protected String cellPhone = "(099)1112233"; 							// 'Моб. телефон'
			protected String email = "test@email.com"; 								// 'Почта'
			protected String comment = "Коментар_"; 								// 'Комментарий'
		}
		
		// Поп-ап сообщения об уходе со странички, на которой есть не сохраненные данные
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
