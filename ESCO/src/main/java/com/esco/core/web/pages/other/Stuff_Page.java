package com.esco.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;

public class Stuff_Page extends WebPage<Stuff_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/OrgStaff/OrgStructure/List";
	
	public Stuff_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Stuff_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getAddEmployee_Button().isAvailable();
	}

	
	
	
	/*__________________________________________________ Ёлементы _______________________________________________________*/
	
	private Button getAddEmployee_Button()
	{
		return new Button(driver, By.id("AddEmployeeLink"));
	}
}
