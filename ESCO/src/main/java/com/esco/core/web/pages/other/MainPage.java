package com.esco.core.web.pages.other;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.CommonActions;
import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Custom;
import com.esco.core.web.elements.Link;
import com.esco.core.web.elements.Text;
import com.esco.core.web.pages.administration.AuditPage;
import com.esco.core.web.pages.administration.UsersPage;
import com.esco.core.web.pages.energy_audit.Auditors_FilesPage;
import com.esco.core.web.pages.energy_audit.Auditors_Page;

public class MainPage extends WebPage<MainPage> 
{
	private static final String PAGE_URL = BASE_URL;
	
	public MainPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public MainPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getExit_Link().isAvailable()
			&& getInfoDiv().isAvailable();
	}
	
	public LogInPage redirectToLogInPage()
	{
		load();
		return new LogInPage(driver).waitUntilAvailable();
	}
	
/*	public void waitFor_PageReady()
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("overflow-auto")));
		//wait.until(ExpectedConditions.elementToBeClickable(By.id<locator>));
	}*/
	
	// �������� ����� ������������
	public void userNameCheck(String expectedUserName)
	{
		// ����� ������� ��� ������������
		String actualUserName = getUserName().getText();
		
		// �������� ��������� ���������� �������� � ��������� ��������
		assertThat(actualUserName, is(equalTo(expectedUserName)));
	}
	
	// ��������� �� ����
	public class goTo
	{
		// ���� '���������'
		public class DocsBlock
		{						
			// ����� ���� '����� ���������'
			public Auditors_Page IncomingDocs_Page()
			{    
				// ������������ �������
				new CommonActions().menu_Handler(driver, 1, 1);
				
			    return new Auditors_Page(driver).waitUntilAvailable();			   
			}
			
			// ����� ���� '������ ���������'
			public Auditors_Page OutgoingDocs_Page()
			{    
				// ������������ �������
				new CommonActions().menu_Handler(driver, 1, 2);
		        
			    return new Auditors_Page(driver).waitUntilAvailable();			   
			}
		}
		
		public class EnergyAudit_Block
		{
/*			// ����� ���� '������ ��'
			public Individuals_Page individuals_Page()
			{    
				// ������������ �������
				new CommonActions().menu_Handler(driver, 2, 2);
				
			    return new Individuals_Page(driver).waitUntilAvailable();			   
			}
			
			// ����� ���� '������ ���'
			public Entrepreneurs_Page entrepreneurs_Page()
			{    
				// ������������ �������
				new CommonActions().menu_Handler(driver, 2, 3);
				
			    return new Entrepreneurs_Page(driver).waitUntilAvailable();			   
			}*/
		}
		
		// ��������� '�Ĳ'
		public Handbooks_Page handbooksPage()
		{
			// ������������ �������
			new CommonActions().menu_Handler(driver, 5, 1);
			
			return new Handbooks_Page(driver).waitUntilAvailable();
		}
		
		// �����������������
		public class AdministrationBlock
		{
			// �����
			public AuditPage Audit_Page()
			{    
				// ������������ �������
				new CommonActions().menu_Handler(driver, 6, 1);
		    
			    return new AuditPage(driver).waitUntilAvailable();
			}
			
			// ������ �������������
			public UsersPage Users_Page()
			{    
				// ������������ �������
				new CommonActions().menu_Handler(driver, 6, 2);
		    
			    return new UsersPage(driver).waitUntilAvailable();
			}
			
			// ����
			public Roles_Page Roles_Page()
			{    
				// ������������ �������
				new CommonActions().menu_Handler(driver, 4, 3);
		    
			    return new Roles_Page(driver).waitUntilAvailable();
			}
		}
		
		// ���
		public Stuff_Page stuffPage()
		{
			// ������������ �������
			new CommonActions().menu_Handler(driver, 5, 0);
			
			return new Stuff_Page(driver).waitUntilAvailable();
		}
		
		// ��������� '�������'
		public Reporting_Page reportingPage()
		{
			// ������������ �������
			new CommonActions().menu_Handler(driver, 6, 0);
			
			return new Reporting_Page(driver).waitUntilAvailable();
		}
		

		
		
		
		
		
		// ��� ������������/���������
		public Auditors_FilesPage direct_Redirect()
		{
			driver.get(BASE_URL + "/CommonDocs/Docs/Edit/55/8595/58");
			new CommonActions().simpleWait(3);
			
			return new Auditors_FilesPage(driver).waitUntilAvailable();
		}
	}
	
	
	/*__________________________________________________ �������� _______________________________________________________*/
	
	private Custom getInfoDiv()
	{
		return new Custom(driver, By.className("content"));
	}

	private Link getExit_Link()
	{
		return new Link(driver, By.xpath("//a[@href='/Account/LogOff']"));
	}
	
	private Text getUserName()
	{
		return new Text(driver, By.xpath("//*[@class='log']/span"));
	}	
}

