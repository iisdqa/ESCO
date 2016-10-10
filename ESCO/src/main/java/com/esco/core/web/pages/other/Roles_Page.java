package com.esco.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;

public class Roles_Page extends WebPage<Roles_Page>
{
private static final String PAGE_URL = BASE_URL + "/IODocs/InputDocsN/Index";
	
	public Roles_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Roles_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAdd_Button().isAvailable();
	}

	
	/*__________________________________________________ Ёлементы _______________________________________________________*/
	
	private Button getAdd_Button()
	{
		return new Button(driver, By.id("accord"));
	}
}
