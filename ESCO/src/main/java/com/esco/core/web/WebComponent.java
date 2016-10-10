package com.esco.core.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public abstract class WebComponent<T extends WebComponent<T>> extends Component<T> 
{
	protected final By findByMethod;
	
	public WebComponent(WebDriver driver, By findByMethod) 
	{
		super(driver);
		this.findByMethod = findByMethod;
	}

	@Override
	public boolean isAvailable()
	{
		try
		{
			return getWebElement() != null;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void click()
	{
		getWebElement().click();
	}
	
	public void clear()
	{
		getWebElement().clear();
	}
	
	public String getText()
	{
		return getWebElement().getText();
	}
	
	public String getAttribute(String attributeName)
	{
		return getWebElement().getAttribute(attributeName);
	}
	
	public String getCssValue(String attributeName)
	{
		return getWebElement().getCssValue(attributeName);
	}
	
	protected WebElement getWebElement()
	{
		return driver.findElement(findByMethod);
	}
}

