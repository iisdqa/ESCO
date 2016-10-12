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
		
		// �������� ������������� ������ ����������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		
		// ���������� �����
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
		
		// ������� ���-�� ����������
		new Elements().new Counters().new Grid().add_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Counters().new PopUp().consumptionType_Input());
		
		// ��������� ������������ �������� � ���� "ĳ� �"
		assertThat(new Elements().new Counters().new PopUp().startYear_Input().getAttribute("value"), is(equalTo("2000")));
		
		// ���������� �����
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
		
		// ���������
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
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",										  
										  consumptionType, 
										  counterName, 
										  serialNum, 
										  inventoryNum,
										  ""};
		
		// ������� ������ ���� ��� ������������ �����
		if(checkType == "view")
		{
			int[] elements_ToRemove = new int[]{ ExpectedValues.length - 1, 0};
			ExpectedValues[0] = new CustomMethods().new Grid().arrayElements_Remove(ExpectedValues[0], elements_ToRemove);
		}
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void counter_Edit()
	{
		//region Variables
		String grid_Id = new Elements().new Counters().new Grid().grid_Id;
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// ������� ���-�� ��������������
		new Elements().new Counters().new Grid().edit_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Counters().new PopUp().consumptionType_Input());
		
		// �������� �����������
		new Elements().new Counters().new PopUp().serialNum_Input().inputText("2");
		
		// ���������
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
		
		// ��������� ��������� ����
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
			
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// �������� ����������� ��������
		assertThat(toolsIndicator.getAttribute("value"), is(equalTo(new Elements().new Values().toolsIndicator)));
		assertThat(commentValue, is(equalTo(new Elements().new Values().comment)));
	}
	
	public void inset_Edit()
	{
		//region Variables	
		TextInput toolsIndicator = new Elements().toolsIndicator_Input(false);
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// �������������� ���
		comment_Change();
				
		// ��������� ��������� ����
		save.click();
		new CommonActions().simpleWait(2);
		
		// �������� �������� ���������
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
	
	// �������� ����������������� ��������
	public void editedInset_Check()
	{
		//region Variables	
		WebElement comment = new Elements().comment_Frame();
		String commentValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, comment) ;
		//endregion
		
		// �������� �����
		assertThat(commentValue, is(equalTo(new Elements().new Values().comment + "2")));
	}
	
	public void infoView_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput toolsIndicator = new Elements().toolsIndicator_Input(true);
		Text comment = new Elements().commentView_Text();
		//endregion
			
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// �������� ����������� ��������
		assertThat(toolsIndicator.getAttribute("value"), is(equalTo(new Elements().new Values().toolsIndicator)));
		assertThat(comment.getAttribute("value"), is(equalTo(new Elements().new Values().comment)));
	}

	public Objects_Page card_Close(String close_Type)
	{
		//region Variables	
		Custom info = new Elements().new Counters().new PopUp().info_PopUp(driver);
		Button yes = new Elements().new Counters().new PopUp().yes_Button(driver);
		//endregion
		
		// �������� �������������� ��������
		new Elements().close_Button(driver).click();
		new CommonActions().simpleWait(1);
		
		// � ��������� ���-��� ��� ����� � �� ������������ �������
		if(close_Type == "tricky")
		{
			//����� � ���������� ������
			new CommonActions().simpleWait(1);
			waitUntilClickable(info);
			
			// �������� ��������
			assertThat(info.getText(), is(equalTo(new Elements().new SaveOrNot_Elements().cardGoAway_Message)));
			
			// �������� ���-���
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
		
		return new Objects_FilesPage(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ �������� _______________________________________________________*/	
	
	public class Elements extends CommonElements.Card_Elements.General_Elements
	{	
		// '�������� ������� �����'
		private TextInput toolsIndicator_Input(Boolean viewMode)		
		{ 
			String id = "1168";
			if(viewMode == false) id = id + "_auto";
			return new TextInput(driver, By.id(id)); 
		}
		
		// '��������'
		private WebElement comment_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_1167")); return element; }
		
		// '��������' �����
		private WebElement comment_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_1167_DesignIFrame")); }	
		
		// '�����������' ��� ����� ���������
		private Text commentView_Text() 				{ return new Text(driver, By.id("1167")); }
		
		// ��������, ������� ����� �������������� ��� ���������� ���������
		public class Values
		{		
			protected String toolsIndicator = "���������� �� ������ ������������� " + 
							 "����������� �� �������� �������� ����� ����� �����"; 		// '����� �������� ��'����'
			protected String comment = "��������"; 											// '��������� ����� ��'����'
		}
		
		// ���� ���������
		private class Counters
		{
			// ����
			private class Grid extends CommonElements.Card_Elements.Grid
			{
				private String grid_Id = "1169";
			}
			
			// ���-�� ��������/�������������� ���������
			private class PopUp extends CommonElements.General_PopUps
			{
				// '��� ����������'
				private TextInput consumptionType_Input()		{ return new TextInput(driver, By.id("1170_auto")); }
				
				// '����� (�����) ���������'
				private TextInput counterName_Input()			{ return new TextInput(driver, By.id("1171_auto")); }
				
				// '������� �����'
				private TextInput serialNum_Input()				{ return new TextInput(driver, By.id("1172")); }
				
				// '����������� �����'
				private TextInput inventoryNum_Input()			{ return new TextInput(driver, By.id("1173")); }
				
				// '̳��� ������������'
				private TextInput location_Input()				{ return new TextInput(driver, By.id("1174")); }
				
				// 'ĳ� �'
				private TextInput startYear_Input()				{ return new TextInput(driver, By.id("1175")); }
				
				// '���� ���������� ������������'
				private TextInput expirationDate_Input()		{ return new TextInput(driver, By.id("1176")); }
			}
			// ��������, ������� ����� �������������� ��� ���������� ���������
			private class Values
			{
				private String consumptionType = "���"; 		// '��� ����������'
				private String counterName = "Electronix"; 		// '����� (�����) ���������'
				private String serialNum = "1"; 				// '������� �����'
				private String inventoryNum = "2"; 				// '����������� �����'
				private String location = "����"; 				// '̳��� ������������'
				private String startYear = "2010"; 				// 'ĳ� �'
				private String expirationDate = "01.01.2020"; 	// '���� ���������� ������������'
			}
		}
		
		// ���-�� ��������� �� ����� �� ���������, �� ������� ���� �� ����������� ������
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
