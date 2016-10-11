package com.esco.core.web.pages.energy_audit.auditors;

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
		
		// �������� ������������� ������ ����������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		
		// ���������� ������� ��������� �����
		surname.inputText(new Elements().new Values().surname);
		name.inputText(new Elements().new Values().name);
		patronymic.inputText(new Elements().new Values().patronymic);
		phone.inputText(new Elements().new Values().phone);
		cellPhone.inputText(new Elements().new Values().cellPhone);
		email.inputText(new Elements().new Values().email);
		
		// ���������� ���������(�������������) �����
		comment.click();
		sendKeys(new Elements().new Values().comment);
		
		// ��������� ��������� ����
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
		
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// �������� ������� ��������� �����
		assertThat(surname.getAttribute("value"), is(equalTo(new Elements().new Values().surname)));
		assertThat(name.getAttribute("value"), is(equalTo(new Elements().new Values().name)));
		assertThat(patronymic.getAttribute("value"), is(equalTo(new Elements().new Values().patronymic)));
		assertThat(phone.getAttribute("value"), is(equalTo(new Elements().new Values().phone)));
		assertThat(cellPhone.getAttribute("value"), is(equalTo(new Elements().new Values().cellPhone)));
		assertThat(email.getAttribute("value"), is(equalTo(new Elements().new Values().email)));
		
		// �������� ���������(�������������) �����
		String shortSummaryValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, comment);
		assertThat(shortSummaryValue, is(equalTo(new Elements().new Values().comment)));
	}
	
	public void card_Edit()
	{
		//region Variables	
		TextInput email = new Elements().email_Input();
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// �������������� email
		email_Edit();
				
		// ��������� ��������� ����
		save.click();
		new CommonActions().simpleWait(3);
		
		// �������� �������� ���������
		waitUntilClickable(email);
	}
	
	// �������� ����������������� ��������
	public void editedCard_Check()
	{
		//region Variables	
		TextInput email = new Elements().email_Input();
		//endregion
		
		// �������� �����
		assertThat(email.getAttribute("value"), is(equalTo(new Elements().new Values().email + "2")));
	}
	
	public void email_Edit()
	{
		//region Variables	
		TextInput email = new Elements().email_Input();
		//endregion
		
		// �������������� email
		email.clear();
		email.inputText(new Elements().new Values().email + "2");
	}
	
	// �������� ������ ��������
	public void cardHeader_Check()
	{
		//region Variables	
		String expected_Header = "�������";
		//endregion
		
		new CommonActions().cardHeader_Check(driver, expected_Header);
	}
	
	// ������� �� ���� ����������
	public Auditors_Page card_Close()
	{
		// �������� �������������� ��������
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
				
	 	// ���� �� �������
		insetLink.click();
		
		// � ��������� ���-��� ��� ����� � �� ������������ �������
		if(go_Type == "tricky")
		{
			//����� � ���������� ������
			new CommonActions().simpleWait(1);
			waitUntilClickable(info);
			
			// �������� ��������
			assertThat(info.getText(), is(equalTo(new Elements().new SaveOrNot_Elements().goAway_Message)));
			
			// �������� ���-���
			yes.click();
			new CommonActions().simpleWait(1);
		}
		
		return new Auditors_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ �������� _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{		
		// '�������'
		private TextInput surname_Input()   			{ return new TextInput(driver, By.id("962")); }	
		
		// '��'�'
		private TextInput name_Input()   				{ return new TextInput(driver, By.id("963")); }
	
		// '�� �������'
		private TextInput patronymic_Input()      		{ return new TextInput(driver, By.id("964")); }
	
		// '�������'
		private TextInput phone_Input()      			{ return new TextInput(driver, By.id("966")); }
				
		// '�������� �������'
		private TextInput cellPhone_Input()     	    { return new TextInput(driver, By.id("967")); }
		
		// '���������� �����'
		private TextInput email_Input() 				{ return new TextInput(driver, By.id("968")); }
	
		// '�����������'
		private WebElement comment_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_969")); return element; }
		
		// '�����������' �����
		private WebElement comment_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_969_DesignIFrame")); }	
		
		// ��������, ������� ����� �������������� ��� ���������� ���������
		public class Values
		{
			protected String surname = "�����������"; 								// '�������'
			protected String name = "�����"; 										// '���'
			protected String patronymic = "����������"; 							// '��������'
			protected String phone = "(044)3332211"; 								// '�������'
			protected String cellPhone = "(099)1112233"; 							// '���. �������'
			protected String email = "test@email.com"; 								// '�����'
			protected String comment = "��������_"; 								// '�����������'
		}
		
		// ���-�� ��������� �� ����� �� ���������, �� ������� ���� �� ����������� ������
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
