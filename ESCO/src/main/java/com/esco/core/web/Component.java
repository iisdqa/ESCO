package com.esco.core.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.esco.core.web.elements.Custom;

public abstract class Component <T extends Component<T>>
{
	protected WebDriver driver;
	
	private static final int WAIT_TIMEOUT = 10000;	
	private static final int WAIT_DELAY = 500;
	
	public Component(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public abstract boolean isAvailable();
	
	@SuppressWarnings("unchecked")
	public T waitUntilAvailable()
	{
		return new Wait<T>().forComponent((T) this).toBeAvailable();
	}
	
	// Ожидание исчезновения элемента
	//public void waitUntilUnblocked(Custom element)
	public void waitUntilClickable(WebComponent<?> element)
	{
		// Определение необходимых переменных
		boolean clickResult = false;
		int timePassed = 0;
		
		// Само ожидание 
		while(!clickResult && (timePassed < WAIT_TIMEOUT))
		{
			try
			{
				element.click();
				clickResult = true;
			}
			catch(Exception e)
			{
				clickResult = false;
			}
			timePassed = timePassed + delay();
		}
		
		if(!clickResult)
		{
			System.err.println("Время ожидания разблокировки элемента превысило " + WAIT_TIMEOUT/1000 + "сек." +
							   "\r\n Элемент: " + element.getWebElement().toString());
			Assert.fail();
		}
	}
	
	// Ожидание пока элемент кликабельный
	public void waitWhileClickable(WebComponent<?> element)
	{
		// Определение необходимых переменных
		boolean existResult = true;
		int timePassed = 0;
		
		// Само ожидание 
		while(existResult && (timePassed < WAIT_TIMEOUT))
		{
			try
			{
				element.click();
				existResult = true;
			}
			catch(Exception e)
			{
				existResult = false;
			}
			timePassed = timePassed + delay();
		}
		
		if(existResult)
		{
			System.err.println("Время ожидания невозможности кликнуть по элементу превысило " + WAIT_TIMEOUT/1000 + "сек." +
							   "\r\n Элемент: " + element.getWebElement().toString());
			Assert.fail();
		}
	}
	
	// Ожидание определенного статуса блокировки элемента
	public void waitForBlockStatus(Custom element, boolean ExpectedBlockStatus)
	{
		// Объявление переменной для получения реального статуса блокировки 
		boolean realBlockStatus = !ExpectedBlockStatus;
		
		// Определение необходимых переменных
		int timePassed = 0;
		
		// Само ожидание 
		while((ExpectedBlockStatus != realBlockStatus) && (timePassed < WAIT_TIMEOUT))
		{
			// Временная переменная
			boolean tempBlockStatus = false;
			
			// Если заблокированно, то присвоить true временной переменной
			if(element.getCssValue("display").equals("block"))
			{
				tempBlockStatus = true;
			}
			timePassed = timePassed + delay();
			
			// Присвоить значение временной переменной для сравняемой переменной
			realBlockStatus = tempBlockStatus;
		}
		
		// Если все же ожидаемый статус != реальному, то выводить ошибку
		if(ExpectedBlockStatus != realBlockStatus)
		{
			System.err.println("Время ожидания определенного состояния блокировки элемента превысило " + WAIT_TIMEOUT/1000 + "сек." +
							   "\r\n Элемент: " + element.getWebElement().toString() + 
							   "\r\n Ожидаемое состояние блокировки: " + ExpectedBlockStatus + 
							   "\r\n Реальное состояние блокировки: " + realBlockStatus);
			Assert.fail();
		}
	}
	
	public void sendKeys(String keys)
	{
		Actions action = new Actions(driver);
		action.sendKeys(keys).build().perform();
		new CommonActions().simpleWait(1);
	}
	
	private int delay()
	{
		try
		{
			Thread.sleep(WAIT_DELAY); 
			return WAIT_DELAY;
		}
		catch(InterruptedException e){throw new RuntimeException(e);}
	}
}
