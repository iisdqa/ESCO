package com.esco.core.web.pages.energy_audit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.esco.core.web.CommonActions;
import com.esco.core.web.CommonElements;
import com.esco.core.web.CustomMethods;
import com.esco.core.web.WebPage;

public class Auditors_Page extends WebPage<Auditors_Page>
{
	private static final String PAGE_URL = BASE_URL + "/CommonDocs/Docs/List/86";
	
	public Auditors_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Auditors_Page load()
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
	public Auditors_RegistrationPage card_add()
	{
		// ������ �� ������ ����������
		new Elements().new Grid().add_Button(driver).click();
		new CommonActions().simpleWait(2);
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();	
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
		String fullName = new Elements().new Filtration_Accordion().new Values().fullName;
		String phone = new Auditors_RegistrationPage(driver).new Elements().new Values().phone;
		String cellPhone = new Auditors_RegistrationPage(driver).new Elements().new Values().cellPhone;
		String email = new Auditors_RegistrationPage(driver).new Elements().new Values().email;
		if (checkType == "edit") email = email + "2";		
		//endregion
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [1][];
		ExpectedValues[0] = new String[] {"",
										  "",
										  "",
										  fullName, 
										  phone, 
										  cellPhone, 
										  email};
		
		// �������� ��������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetAllRows(grid, true);;
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
	// ������� � �������������� ��������
	public Auditors_RegistrationPage card_Edit()
	{
		// ������ �� ������ ��������������
		new Elements().new Grid().edit_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	// ������� � ��������� ��������
	public Auditors_RegistrationPage card_View()
	{
		// ������ �� ������ ���������
		new Elements().new Grid().view_Button(driver).click();
		new CommonActions().simpleWait(2);
		
		return new Auditors_RegistrationPage(driver).waitUntilAvailable();	
	}
	
	/*____________________________________________________________________________________________________________________*/
	
	
	
	/*__________________________________________________ Elements ________________________________________________________*/
	
	private class Elements
	{
		// ������������ �������� ������
		private class Units_Tree extends CommonElements.UnitsTree_Elements {}
		
		// ������������ �������� �����
		private class Grid extends CommonElements.BaseGrid_Elements {}
		
		// ������������ �������� ���������� ����������
		private class Filtration_Accordion extends CommonElements.FiltrationControl_Elements 
		{
			// ������������ ��������
			private class Values
			{
				private String fieldName = "ϲ�";
				private String matchType = "�������";
				private String surname = new Auditors_RegistrationPage(driver).new Elements().new Values().surname;
				private String name = new Auditors_RegistrationPage(driver).new Elements().new Values().name;
				private String patronymic = new Auditors_RegistrationPage(driver).new Elements().new Values().patronymic;
				private String fullName = surname + " " + name + " " + patronymic;
				private String value = fullName;
			}
		}	
	}
	/*____________________________________________________________________________________________________________________*/
}
