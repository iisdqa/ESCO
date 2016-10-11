package com.esco.core.web.pages.energy_audit.objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

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
import com.esco.core.web.elements.Custom;
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
		TextInput group = new Elements().group_Input();
		TextInput objectType = new Elements().objectType_Input();
		TextInput projectType = new Elements().projectType_Input();
		Select modelOrientation = new Elements().modelOrientation_Select();
		TextInput adress = new Elements().Adress_Input();
		WebElement comment = new Elements().comment_Text();
		TextInput koatuu = new Elements().new Koatuu_Elements().Koatuu_Input();
		//endregion
		
		// �������� ������������� ������ ����������
		// �������� ������������ ����
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));
		assertThat(year.getAttribute("value"), is(equalTo("1946")));
		
		// ���������� ������� �����
		name.inputText(new Elements().new Values().name);
		year.inputText(String.valueOf(new Elements().new Values().year));
		group.inputText(new Elements().new Values().group);
		objectType.inputText(new Elements().new Values().objectType);
		projectType.inputText(new Elements().new Values().projectType);
		modelOrientation.selectByVisibleText(new Elements().new Values().modelOrientation);
		adress.inputText(new Elements().new Values().adress);
		
		// ���������� ���������(�������������) �����
		comment.click();
		sendKeys(new Elements().new Values().comment);
		
		// ����� ������ �� �������
		koatuu_Set();
		
		// �������� ��������� ������
		assertThat(koatuu.getAttribute("value"), is(equalTo(new Elements().new Koatuu_Elements().new Values().district_Code)));
		koatuuSet_Check(new Elements().new Koatuu_Elements().new Values().district_Name);
		
		// ��������� ����������
		geolocation_Set();
		geolocationSet_Check();
		
		// ��������� ��������� ����
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
		
		// ������� ���-�� ������ ������
		choose.click();
		waitUntilClickable(set);
		
		// ���������� ������
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
		
		// �������� ������������ ��������
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
		
		// ����� �������
		locationSet.click();
		new CustomMethods().simpleWait(2);
	}
	
	private void geolocationSet_Check()
	{
		//region Variables	
		TextInput latitude = new Elements().latitude_Input();
		TextInput longitude = new Elements().longitude_Input();
		//endregion
		
		// �������� ������������ ��������
		assertThat(latitude.getAttribute("value"), is(equalTo(new Elements().new Values().latitude)));
		assertThat(longitude.getAttribute("value"), is(equalTo(new Elements().new Values().longitude)));
	}
	
	public void card_Check()
	{
		//region Variables	
		Button save = new Elements().save_Button(driver);
		TextInput name = new Elements().name_Input();
		TextInput year = new Elements().year_Input();
		TextInput group = new Elements().group_Input();
		TextInput objectType = new Elements().objectType_Input();
		TextInput projectType = new Elements().projectType_Input();
		Select modelOrientation = new Elements().modelOrientation_Select();
		TextInput koatuu = new Elements().new Koatuu_Elements().Koatuu_Input();
		TextInput adress = new Elements().Adress_Input();
		WebElement comment = new Elements().comment_Frame();
		//endregion
		
		// �������� ������������� ������ ���������� � ��������� ������
		assertThat(save.getAttribute("disabled"), is(equalTo("true")));

		// �������� ������� ��������� �����
		assertThat(name.getAttribute("value"), is(equalTo(new Elements().new Values().name)));
		assertThat(year.getAttribute("value"), is(equalTo(new Elements().new Values().year)));
		assertThat(group.getAttribute("value"), is(equalTo(new Elements().new Values().group)));
		assertThat(objectType.getAttribute("value"), is(equalTo(new Elements().new Values().objectType)));
		assertThat(projectType.getAttribute("value"), is(equalTo(new Elements().new Values().projectType)));
		assertThat(modelOrientation.getFirstSelectedOption(), is(equalTo(new Elements().new Values().modelOrientation)));

		// �������� ����� ������
		assertThat(koatuu.getAttribute("value"), is(equalTo(new Elements().new Koatuu_Elements().new Values().district_Code)));
		koatuuSet_Check(new Elements().new Koatuu_Elements().new Values().district_Name);
		
		// �������� ����� ����������
		assertThat(adress.getAttribute("value"), is(equalTo(new Elements().new Values().adress)));
		geolocationSet_Check();
		
		// �������� ���������(�������������) �����
		String shortSummaryValue = new CustomMethods().new WorkWith_TextEditor().getTextValue(driver, comment);
		assertThat(shortSummaryValue, is(equalTo(new Elements().new Values().comment)));
	}
	
	public void card_Edit()
	{
		//region Variables	
		TextInput year = new Elements().year_Input();
		Button save = new Elements().save_Button(driver);
		//endregion
		
		// �������������� ���
		year.clear();
		year.inputText(String.valueOf(new Elements().new Values().year + 1));
				
		// ��������� ��������� ����
		save.click();
		new CommonActions().simpleWait(2);
		
		// �������� �������� ���������
		waitUntilClickable(year);
	}
	
	// �������� ����������������� ��������
	public void editedCard_Check()
	{
		//region Variables	
		TextInput year = new Elements().year_Input();
		//endregion
		
		// �������� �����
		assertThat(year.getAttribute("value"), is(equalTo(String.valueOf(new Elements().new Values().year + 1))));
	}
	
	public void koatuu_Edit()
	{
/*		//region Variables	
		TextInput email = new Elements().email_Input();
		//endregion
		
		// �������������� email
		email.clear();
		email.inputText(new Elements().new Values().email + "2");*/
	}
	
	public void uniqueModel_Check()
	{
		
	}
	
	// �������� ������ ��������
	public void cardHeader_Check()
	{
		//region Variables	
		String expected_Header = "�ᒺ���";
		//endregion
		
		new CommonActions().cardHeader_Check(driver, expected_Header);
	}
	
	// ������� �� ���� ����������
	public Objects_Page card_Close()
	{
		// �������� �������������� ��������
		new Elements().close_Button(driver).click();
		new CommonActions().simpleWait(1);
		
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
		// '��������'
		private TextInput name_Input()   				{ return new TextInput(driver, By.id("937")); }
		
		// '���'
		private TextInput year_Input()   				{ return new TextInput(driver, By.id("938")); }
		
		// '������ ����� ��������'
		private TextInput group_Input()   				{ return new TextInput(driver, By.id("939_auto")); }	
	
		// '��� �������'
		private TextInput objectType_Input()      		{ return new TextInput(driver, By.id("940_auto")); }
	
		// '��� �������'
		private TextInput projectType_Input()      		{ return new TextInput(driver, By.id("941_auto")); }
				
		// '������'
		private TextInput model_CheckBox()     	    	{ return new TextInput(driver, By.id("942_cb")); }
		
		// '���������� ������������ �������'
		private Select modelOrientation_Select() 		{ return new Select(driver.findElement(By.id("943"))); } 
	
		// '�����'
		private TextInput Adress_Input() 				{ return new TextInput(driver, By.id("945")); }
		
		// ������ '������'
		private Button findAdress_Button() 				{ return new Button(driver, By.xpath("//input[@onclick='findAddress()']")); }
		
		// '������'
		private TextInput latitude_Input() 				{ return new TextInput(driver, By.id("947")); }
		
		// '�������'
		private TextInput longitude_Input() 			{ return new TextInput(driver, By.id("948")); }
		
		// '�����������'
		private WebElement comment_Text()   			{ WebElement element = driver.findElement(By.id("customTextEditor_949")); return element; }
		
		// '�����������' �����
		private WebElement comment_Frame()  	 		{ return driver.findElement(By.id("customTextEditor_949_DesignIFrame")); }	
		
		// ��������, ������� ����� �������������� ��� ���������� ���������
		public class Values
		{		
			protected String name = "�� ������������"; 								// '��������'
			protected int year = 2000; 												// '���'
			protected String group = "����������"; 									// '������'
			protected String objectType = "����� �����"; 						// '��� �������'
			protected String projectType = "����� ������ ����������"; 			// '��� �������'
			protected String modelOrientation = "��������"; 						// '���������� ������������ �������'
			protected String adress = "������ ����������� 31�"; 					// '�����'
			protected String latitude = "50,447766"; 								// '������'
			protected String longitude = "30,463813"; 								// '�������'
			protected String comment = "��������_"; 								// '�����������'
		}
		
		// �������� ����� "������"
		protected class Koatuu_Elements
		{
			// ������ �������� ������ ������
			private Button choose_Button() 					{ return new Button(driver, By.id("944_showkoatuu")); }
			
			// '������'
			private TextInput Koatuu_Input() 				{ return new TextInput(driver, By.id("944_koatuu")); }
			
			// '�������'
			private TextInput region_Input() 				{ return new TextInput(driver, By.id("Area")); }
			
			// '�����'
			private TextInput district_Input() 				{ return new TextInput(driver, By.id("Region")); }
			
			// '�����'
			private TextInput city_Input() 					{ return new TextInput(driver, By.id("City")); }
			
			// '�-� ������'
			private TextInput cityDistrict_Input() 			{ return new TextInput(driver, By.id("CityRegion")); }
			
			// ���-�� ������ ������
			protected class Choose_PopUp
			{
				// ������ '������'
				private Button set_Button() 					{ return new Button(driver, By.id("koatuudict944_showkoatuu-OK")); }
			
				// Div '������������'
				private Custom download_Div() 					{ return new Custom(driver, By.id("load_koatuudict944_showkoatuu-tree")); }
				
				// ������ "+" ��� �������� "�. ���"
				private Button plus_Button() 					{ return new Button(driver, By.xpath("//td[@aria-describedby='koatuudict944_showkoatuu-tree_code' "+ 
																								     "and @title='8000000000']/div/div")); }
		
				// ������� ��� ������ '�����'�������' 
				private Cell district_Cell() 					{ return new Cell(driver, By.xpath("//td[@aria-describedby='koatuudict944_showkoatuu-tree_name' "+ 
     																			 				     "and @title='�����'�������']/div/div")); }
			}
			
			// ������������ ��������
			protected class Values
			{
				protected String city = "�.�ȯ�"; 								// '�����'
				protected String district_Name = "�-�.�����'�������"; 			// '�-� ������, ��������'
				protected String district_Code = "8038900000"; 					// '�-� ������, ���'
				protected String wrongDistrict_Name = "�-�.������ʲ������"; 	// '�-� ������, ��������'
				protected String wrongDistrict_Code = "8039100000"; 			// '�-� ������, ���'
				protected String location = city + ", "+ district_Name + ", " + new Elements().new Values().adress;
			}
		}
		
		// ���-�� ��������� �� ����� �� ���������, �� ������� ���� �� ����������� ������
		public class SaveOrNot_Elements extends CommonElements.Card_Elements.Pop_Ups
		{
		}
	}	
}
