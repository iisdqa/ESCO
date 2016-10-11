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
import com.esco.core.web.elements.TextInput;

public class Objects_FilesPage extends WebPage<Objects_FilesPage> 
{
private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/Edit/55/";
	
	public Objects_FilesPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Objects_FilesPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{	
		return new Elements().close_Button(driver).isAvailable();
	}
	
	public void wait_ForPageReady()
	{
		//region Variables
		String grid_Id = new Elements().new Files().new Grid().grid_Id;
		//endregion
		
		new CustomMethods().simpleWait(1);
		waitForBlockStatus(new Elements().new Files().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void file_Add()
	{
		//region Variables
		String grid_Id = new Elements().new Files().new Grid().grid_Id;
		String fileName = new Elements().new Files().new Values().fileName;
		String fileWay = new Elements().new Files().new Values().fileWay;
		String comment = new Elements().new Files().new Values().comment;
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// ������� ���-�� ����������
		new Elements().new Files().new Grid().add_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Files().comment_Text());
		
		// ���������� ���� '��������'
		sendKeys(comment);
		
		// ���������� ���� + �������� ����������� � ��������� ����
		new Elements().new Files().fileUpload_Button(driver).inputText(fileWay);
		new CommonActions().simpleWait(2);
		assertThat(new Elements().new Files().file_Input().getAttribute("value"), is(equalTo(fileName)));
		assertThat(new Elements().new Files().file_Input().getAttribute("readonly"), is(equalTo("true")));
		
		// ���������
		new Elements().new Files().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		save.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Files().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void filesGrid_Check(String checkType)
	{
		//region Variables
		String grid_Id = new Elements().new Files().new Grid().grid_Id;
		WebElement grid = new Elements().new Files().new Grid().grid_Body(driver, grid_Id);
		String date = new Elements().new Files().new Values().date;
		String user = new Elements().new Files().new Values().user;
		String fileName = new Elements().new Files().new Values().fileName;
		String comment = new Elements().new Files().new Values().comment;
		if (checkType != "add") comment = new Elements().new Files().new Values().comment + "2";
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",										  
										  fileName, 
										  date, 
										  user, 
										  comment,
										  ""};
		
		// ������� ������ ���� ��� ������������ �����
		if(checkType == "view")
		{
			int[] elements_ToRemove = new int[]{ 0, 0};
			ExpectedValues[0] = new CustomMethods().new Grid().arrayElements_Remove(ExpectedValues[0], elements_ToRemove);
		}
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		ActualValues[0][3] = ActualValues[0][3] + ":";
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);	
	}
	
	public void inset_Save()
	{
		//region Variables
		String grid_Id = new Elements().new Files().new Grid().grid_Id;
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// ���������� �������
		save.click();
		new CommonActions().simpleWait(3);
		save.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Files().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public void file_Edit()
	{
		//region Variables
		String grid_Id = new Elements().new Files().new Grid().grid_Id;
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// ������� ���-�� ��������������
		new Elements().new Files().new Grid().edit_Button(driver, grid_Id).click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(new Elements().new Files().comment_Text());
		
		// �������� �����������
		sendKeys("2");
		
		// ���������
		new Elements().new Files().save_Button(driver).click();
		new CommonActions().simpleWait(1);
		save.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Files().new Grid().download_Div(driver, grid_Id), false);
	}	
	
	public void fileUnload_check()
	{
		//region Variables
		Button download = new Elements().new Files().fileDownload_Button(driver);
		String fileName = new Elements().new Files().new Values().fileName;
		//endregion
		
		new CustomMethods().new WorkWith_TextFiles().fileDownload_Check(download, fileName);
	}
	
	public void file_Delete()
	{
		//region Variables
		String grid_Id = new Elements().new Files().new Grid().grid_Id;
		Button deleteButton = new Elements().new Files().new Grid().delete_Button(driver, grid_Id);
		Custom info = new Elements().new Files().info_PopUp(driver);
		Button yes = new Elements().new Files().yes_Button(driver);
		Button add_Button = new Elements().new Files().new Grid().add_Button(driver, grid_Id);
		//endregion
		
		// �������� ���-���
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(info);
		
		// �������� ���������
		assertThat(info.getText(), is(equalTo(new Elements().new Files().deletion_Message)));
		
		// �������� ���-���
		yes.click();
		new CommonActions().simpleWait(1);
		add_Button.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Files().new Grid().download_Div(driver, grid_Id), false);
	}
	
	public Objects_Page card_Close(String close_Type)
	{
		//region Variables	
		Custom info = new Elements().new Files().info_PopUp(driver);
		Button yes = new Elements().new Files().yes_Button(driver);
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
			assertThat(info.getText(), is(equalTo(new Elements().new Files().cardGoAway_Message)));
			
			// �������� ���-���
			yes.click();
			new CommonActions().simpleWait(1);
		}
		
		return new Objects_Page(driver).waitUntilAvailable();
	}
	
	/*__________________________________________________ �������� _______________________________________________________*/	
	
	private class Elements extends CommonElements.Card_Elements.General_Elements
	{			
		private class Files extends CommonElements.Card_Elements.Card_Files_Elements
		{
			// �������� �����
			public TextInput file_Input()      			{ return new TextInput(driver, By.id("971_fname")); }
			
			// '�����������'
			public Custom comment_Text()   				{ return new Custom(driver, By.id("customTextEditor_974")); }
			
			// ����
			private class Grid extends CommonElements.Card_Elements.Grid
			{
				private String grid_Id = "970";
			}
			
			private class Values
			{	private String date = new CustomMethods().getCurrentDate();	  			// ����
				private String user = "������� ����� ���������";						// ������������
				private String fileWay = BASE_DIR + 
										"\\storage\\files\\test_data\\simple_File.txt";	// ������ �� ����(��������)
				private String fileName = "simple_File.txt";							// �������� �����
				private String comment = "��������_";			     					// ������ �� ����
			}
		}
		
		private class LinkedDocs extends CommonElements.Card_Elements.Card_LinkedDocs_Elements{}
	}
}
