package com.esco.core.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.esco.core.web.WebComponent;

public class Cell extends WebComponent<Cell>
{
	public Cell(WebDriver driver, By findByMethod) 
	{
		super(driver, findByMethod);
	}
}
