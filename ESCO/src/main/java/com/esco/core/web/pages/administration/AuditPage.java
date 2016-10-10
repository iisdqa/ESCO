package com.esco.core.web.pages.administration;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
import com.esco.core.web.pages.other.MainPage;

public class AuditPage extends WebPage<AuditPage> 
{
private static final String PAGE_URL = BASE_URL + "/User";
	
	public AuditPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public AuditPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return new Elements().login_Input().isAvailable() && 
			   new Elements().search_Button().isAvailable();
	}
	
	public void operationsSearch()
	{
		new Elements().login_Input().inputText("auto_user");
		new Elements().search_Button().click();
		new CommonActions().simpleWait(1);
		
		// �������� ��������� �����
		waitUntilClickable(new Elements().new Grid().grid_Table());
	}
	
	public void operationsCheck()
	{
		// ����������� ������� ����
		String currentDate = new CustomMethods().getCurrentDate();
		
		// ����������� IP
		String myIp = getLocalAddress().toString().replace("/", "");
		
		// ����������� ��������� ��������
		String[][] ExpectedValues = new String [2][];
		ExpectedValues[0] = new String[] {currentDate, 
								  		  "����� ������ ������������", 
								  		  "������������ �������� ����� �� �������", 
								  		  "auto_user", 
								  		  "�������� ������� ��������",
								  		  myIp};
		
		ExpectedValues[1] = new String[] {currentDate, 
				  						  "������ ������ ������������", 
				  						  "�������� ���� � �������", 
				  						  "auto_user", 
				  						  "�������� ������� ��������",
				  						  myIp};
		
		// �������� �������� �� �����
		String[][] ActualValues = new CustomMethods().new Grid().GetSpecificRows(new Elements().new Grid().grid_Body(), 2, true);
		
		// �������� �������� �����
		new CustomMethods().new Grid().gridValuesEqualityCheck(ExpectedValues, ActualValues);
	}
	
/*	public AdministrationPage BackTo_AdministrationPage()
	{
		getBackToAdministrationButton().click();
		return new AdministrationPage(driver).waitUntilAvailable();
	}*/
	
	public MainPage BackTo_MainPage()
	{
		new Elements().backToMain_Link(driver).click();
		return new MainPage(driver).waitUntilAvailable();
	}
	
	private InetAddress getLocalAddress(){
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while( b.hasMoreElements()){
                for ( InterfaceAddress f : b.nextElement().getInterfaceAddresses())
                    if ( f.getAddress().isSiteLocalAddress())
                        return f.getAddress();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	private class Elements extends CommonElements.Other_Elements
	{
		private TextInput login_Input()					{return new TextInput(driver, By.id("Login"));}	
		private Button search_Button()					{return new Button(driver, By.id("btnSearch"));}
	
	/*	private Button getBackToAdministrationButton()
		{
			return new Button(driver, By.xpath("//a[@href='/User/']"));
		}*/
		private class Grid
		{
			private Custom grid_Table()						{return new Custom(driver, By.id("list"));}
			private WebElement grid_Body()					{return driver.findElement(By.xpath("//*[@id='list']/tbody"));}
		}
	}
}
