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
		
		// �������� ������������� ������ ����������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		
		// ���������� �����
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
		
		// ��������� ��������� ����
		save.click();
		new CommonActions().simpleWait(3);
		
		// �������� �������� ���������
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
		
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// �������� ����������� ��������
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
		
		// �������������� ���
		ownerName_Change();
				
		// ��������� ��������� ����
		save.click();
		new CommonActions().simpleWait(2);
		
		// �������� �������� ���������
		waitUntilClickable(ownerName);
	}
	
	public void ownerName_Change()
	{
		//region Variables	
		TextInput ownerName = new Elements().ownerName_Input();
		//endregion
		
		ownerName.inputText("2");
	}
	
	// �������� ����������������� ��������
	public void editedInset_Check()
	{
		//region Variables	
		TextInput ownerName = new Elements().ownerName_Input();
		//endregion
		
		// �������� �����
		assertThat(ownerName.getAttribute("value"), is(equalTo(new Elements().new Values().ownerName + "2")));
	}
	
	public Objects_CountersPage goTo_Counter_Page(String go_Type)
	{
		//region Variables	
		WebElement insetLink = new Elements().inset_Link(driver, "3");
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
		
		return new Objects_CountersPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ �������� _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{	
		// '����� �������� ��'����'
		private TextInput ownerName_Input()   					{ return new TextInput(driver, By.id("953")); }
		
		// '��������� ����� ��'����'
		private TextInput regNum_Input()   						{ return new TextInput(driver, By.id("935")); }
		
		// '����������� ����� ��'����'
		private TextInput inventoryRegNum_Input()   			{ return new TextInput(driver, By.id("936")); }	
	
		// 'ID ��'���� � ������ ������'
		private TextInput objectId_Input()      				{ return new TextInput(driver, By.id("950")); }
	
		// '���� �������� ��'���� � ������������'
		private TextInput startDate_Input()      				{ return new TextInput(driver, By.id("952")); }
				
		// '�������� ��������� ��'���� �� ����'
		private TextInput startDoc_Input()     	    			{ return new TextInput(driver, By.id("954")); }
	
		// '���� ��������� ��������� ��'���� �� ����'
		private TextInput startDocDate_Input() 					{ return new TextInput(driver, By.id("956")); }
		
		// '����� ��������� ��������� ��'���� �� ����'
		private TextInput startDocNum_Input() 					{ return new TextInput(driver, By.id("955")); }
		
		// ����������� '�������� ������������� ��������'
		private CheckBox energyPassportIndicator_CheckBox()		{ return new CheckBox(driver, By.id("951_cb")); }

		// �������� ������������ '�������� ������������� ��������'
		private Custom energyPassportIndicator_Value()			{ return new Custom(driver, By.id("951")); }
		
		// ��������, ������� ����� �������������� ��� ���������� ���������
		public class Values
		{		
			protected String ownerName = "��������"; 			// '����� �������� ��'����'
			protected String regNum = "111"; 					// '��������� ����� ��'����'
			protected String inventoryRegNum = "222"; 			// '����������� ����� ��'����'
			protected String objectId = "333"; 					// 'ID ��'���� � ������ ������'
			protected String startDate = "01.01.2010"; 			// '���� �������� ��'���� � ������������'
			protected String startDoc = "�����"; 				// '�������� ��������� ��'���� �� ����'
			protected String startDocDate = "02.01.2010"; 		// '���� ��������� ��������� ��'���� �� ����'
			protected String startDocNum = "444"; 				// '����� ��������� ��������� ��'���� �� ����'
		}
		
		// ���-�� ��������� �� ����� �� ���������, �� ������� ���� �� ����������� ������
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
