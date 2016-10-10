package com.esco.core.web.pages.other;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.WebPage;
import com.esco.core.web.elements.Button;

public class Reporting_Page extends WebPage<Reporting_Page> 
{
	private static final String PAGE_URL = BASE_URL + "/Reporting/PrintedForms/SetParameters";
	
	public Reporting_Page(WebDriver driver) 
	{
		super(driver);
	}

	@Override
	public Reporting_Page load()
	{
		driver.get(PAGE_URL);
		return this;
	}

	@Override
	public boolean isAvailable()
	{
		return getMakeReport_Button().isAvailable();
	}

	
	
	/*__________________________________________________ Ёлементы _______________________________________________________*/
	
	private Button getMakeReport_Button()
	{
		return new Button(driver, By.id("save_btn"));
	}
}
