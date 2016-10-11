package com.esco.core.web.pages.energy_audit.objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CommonElements;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;
import com.esco.core.web.elements.Custom;

public class Objects_Page extends WebPage<Objects_Page>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/List/86";
	
	public Objects_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Objects_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().new Grid().add_Button(driver).isAvailable();
	}

	
	/*________________________________________________________ Actions ___________________________________________________*/
	
	// �������� ���������� ���������
	public void waitFor_PageReady()
	{
		//waitUntilClickable(new Elements().new Units_Tree().tree_Div(driver));
		new Elements().new Grid().add_Button(driver).waitUntilAvailable();
	}
	
	// �������� ��������� �����
	public void gridDownload_Wait()
	{
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
		new CommonActions().simpleWait(1);
	}
	
	// ������� � ���������� �������� ���������
	public Objects_RegistrationPage card_add()
	{
		// ������ �� ������ ����������
		new Elements().new Grid().add_Button(driver).click();
		new CommonActions().simpleWait(2);
		return new Objects_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	// ����� ��������
	public void card_Search()
	{
		//region Variables	
		String fieldName = new Elements().new Filtration_Accordion().new Values().fieldName;
		String matchType = new Elements().new Filtration_Accordion().new Values().matchType;
		String value = new Elements().new Filtration_Accordion().new Values().value;
		//endregion
		
		// ����������
		new CommonActions().grid_Filtration(driver, fieldName, matchType, value);
		
		// �������� ��������� �����
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
	}
	
	// �������� ��������� ��������
	public void card_Check(String checkType)
	{
		//region Variables
		WebElement grid = new Elements().new Grid().grid_Body(driver);
		String name = new Objects_RegistrationPage(driver).new Elements().new Values().name;
		String objectType = new Objects_RegistrationPage(driver).new Elements().new Values().objectType;
		String location = new Objects_RegistrationPage(driver).new Elements().new Koatuu_Elements().new Values().location;
		String year = String.valueOf(new Objects_RegistrationPage(driver).new Elements().new Values().year);
		if (checkType == "edit") year = String.valueOf(new Objects_RegistrationPage(driver).new Elements().new Values().year + 1);
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "",
										  name, 
										  objectType,
										  location,
										  year};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	// ������� � �������������� ��������
	public Objects_RegistrationPage card_Edit()
	{
		// ������ �� ������ ��������������
		new Elements().new Grid().edit_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Objects_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	// ������� � ��������� ��������
	public Objects_RegistrationPage card_View()
	{
		// ������ �� ������ ���������
		new Elements().new Grid().view_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Objects_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	public void card_Delete()
	{
		//region Variables
		Button deleteButton = new Elements().new Grid().delete_Button(driver);
		Custom info = new Elements().new Deletion_PopUp().info_PopUp(driver);
		Button yes = new Elements().new Deletion_PopUp().yes_Button(driver);
		Button add_Button = new Elements().new Grid().add_Button(driver);
		//endregion
		
		// �������� ���-���
		deleteButton.click();
		new CommonActions().simpleWait(1);
		waitUntilClickable(info);
		
		// �������� ���������
		assertThat(info.getText(), is(equalTo(new Elements().new Deletion_PopUp().new Values().cardDeletion_Message)));
		
		// �������� ���-���
		yes.click();
		new CommonActions().simpleWait(1);
		add_Button.waitUntilAvailable();
		waitForBlockStatus(new Elements().new Grid().download_Div(driver), false);
	}
	
	public void cardDeletion_Check()
	{
		// �������� ���������� �������� � ����� '���������� ��������'
		new CustomMethods().elementExistenceCheck(new Elements().new Grid().grid_Body(driver), false);		
	}
	
	/*____________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements ________________________________________________________*/
	
	private class Elements
	{		
		// ������������ �������� �����
		private class Grid extends CommonElements.BaseGrid_Elements {}

		// ���-�� ��������
		private class Deletion_PopUp extends CommonElements.General_PopUps
		{
			private class Values extends CommonElements.General_PopUps.Values{}
		}
		
		// ������������ �������� ���������� ����������
		private class Filtration_Accordion extends CommonElements.FiltrationControl_Elements 
		{
			// ������������ ��������
			private class Values
			{
				private String fieldName = "����� ��'����";
				private String matchType = "�������";
				private String name = new Objects_RegistrationPage(driver).new Elements().new Values().name;
				private String value = name;
			}
		}	
	}
	/*____________________________________________________________________________________________________________________*/
}
