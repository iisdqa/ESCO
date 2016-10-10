package com.esco.core.web.pages.other;

import java.sql.Connection;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonElements;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.Cell;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.TextInput;

public class Handbooks_Page extends WebPage<Handbooks_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/Dictionaries/DictionariesUDB/List";
	
	public Handbooks_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Handbooks_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new DictValues().new Grid().add_Button().isAvailable();
	}

	/*__________________________________________________ Actions ________________________________________________________*/
	// �������� �������� �����
	public void wait_ForPageReady()
	{		
		new CustomMethods().simpleWait(1);
		waitForBlockStatus(new Elements().new DictValues().new Grid().download_Div(), false);
	}
	
	// ���������� ������ ��� ���������� ����
	public void dictionaryCache_Update(Connection sqlConnection)
	{
		//region Variables
		String[] dictNames = {"�����", "�������" , "����� (����)", "������", "������"};
		String fullValue = new Elements().new DictValues().new PopUps().new AddEdit().new Values().fullValue;
		String shortValue = new Elements().new DictValues().new PopUps().new AddEdit().new Values().shortValue;
		//endregion
		
		// ������� �� ������ ���������
		new Elements().new DictNames().new Grid().new Pager().pageNumber_Input(driver).clear();
		new Elements().new DictNames().new Grid().new Pager().pageNumber_Input(driver).inputText("3");
		sendKeys(Keys.ENTER.toString());
		
		// ���������� �������� � �������
		for(int i = 0; i<dictNames.length; i++)
		{
			// ����� �������
			new Elements().new DictNames().new Grid().dictName_Cell(dictNames[i]).click();
			wait_ForPageReady();
			
			// ���������� �������� � ����
			new Elements().new DictValues().new Grid().add_Button().click();
			waitUntilClickable(new Elements().new DictValues().new PopUps().new AddEdit().fullValue_Input());
			new Elements().new DictValues().new PopUps().new AddEdit().fullValue_Input().inputText(fullValue);
			new Elements().new DictValues().new PopUps().new AddEdit().shortValue_Input().inputText(shortValue);
			new Elements().new DictValues().new PopUps().new AddEdit().save_Button().click();
			new Elements().new DictValues().new Grid().add_Button().waitUntilAvailable();
			wait_ForPageReady();
		}
	}
	
	
	/*___________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements _______________________________________________________*/
	private class Elements
	{
		private class DictNames
		{
			// �������� ����� ��������
			private class Grid
			{
				// "������������"
				public Cell dictName_Cell(String dictName)	{return new Cell(driver, By.xpath("//td[@aria-describedby='list_dict_DIC_PARENT_NAME' and text()='" + dictName + "']"));}
				
				private class Pager extends CommonElements.BaseGrid_Elements.Pager_Elements{}
			}
		}
		
		private class DictValues
		{
			private class PopUps extends CommonElements.General_PopUps
			{
				private class AddEdit
				{
					// ������ ��������
					private TextInput fullValue_Input()					{return new TextInput(driver, By.xpath("//input[contains(@id, 'DICV_LNAME')]"));}
					
					// ������� ��������
					private TextInput shortValue_Input()				{return new TextInput(driver, By.xpath("//input[contains(@id, 'DICV_SNAME')]"));}
					
					// ������ ����������
					public Button save_Button( )   						{ return new Button(driver, By.xpath("//input[contains(@onclick, 'SaveValueUDB()')]")); }
					
					// ������������ ��������
					private class Values
					{
						private String fullValue = "Test Automation";
						private String shortValue = fullValue;						
					}
				}
			}
			
			// �������� ����� �������� ��������
			private class Grid
			{
				// ������ ����������
				private Button add_Button()					{return new Button(driver, By.id("add_value_dict"));}
				
				// "������������"
				public WebElement grid_Body()				{ return driver.findElement(By.xpath("//table[@id='list_value_dict']/tbody")); }
				
				// "������������"
				public Custom download_Div()				{ return new Custom(driver, By.id("load_list_value_dict")); }

				// ������ ��������������
				public Button edit_Button()   				{ return new Button(driver, By.xpath("//td[@aria-describedby='list_value_dict_edit']")); }	
			
				// ������������ ��������
				private class Values
				{
				
				}
			}
		}
	}
	/*___________________________________________________________________________________________________________________*/
}
