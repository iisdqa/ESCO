package com.esco.core.web.pages.administration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Link;

public class AdministrationPage extends WebPage<AdministrationPage> 
{
private static final String PAGE_URL = BASE_URL + "/User";
	
	public AdministrationPage(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public AdministrationPage load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAuditLink().isAvailable() && 
				getUsersLink().isAvailable() && 
				getAutoMessagesLink().isAvailable();
	}
	
	public class goTo
	{
		public AuditPage AuditPage()
		{
			getAuditLink().click();
			return new AuditPage(driver).waitUntilAvailable();
		}
		
		public UsersPage UsersPage()
		{
			getUsersLink().click();
			return new UsersPage(driver).waitUntilAvailable();
		}
	}
	
	private Link getAuditLink()
	{
		return new Link(driver, By.xpath("//a[@href='/User/SearchOperation']"));
	}
	
	private Link getUsersLink()
	{
		return new Link(driver, By.xpath("//a[@href='/User/Search']"));
	}
	
	private Link getAutoMessagesLink()
	{
		return new Link(driver, By.xpath("//a[@href='/User/MailsSettings']"));
	}
}
