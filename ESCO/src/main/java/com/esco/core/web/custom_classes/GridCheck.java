package com.esco.core.web.custom_classes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public abstract class GridCheck 
{
	// Get all rows from grid
	public String[][] GetAllRows(WebElement tbody, Boolean containsHiddenRow)
	{
		// Define rows count
		List<WebElement> rows = tbody.findElements(By.tagName("tr"));
		
		
		// Delete hidden row
		if(containsHiddenRow) rows.remove(0);
		
		// Define array size for array which will be returned
		String[][] GridValues = new String[rows.size()][];
		
		// Run through rows
		for(int rnum=0; rnum < rows.size(); rnum++)
		{
			// Define cells count
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
			int sizeBeforeCut = columns.size();
						
			// Remove hidden cells
			for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
			{
				if(columns.get(cnum).getCssValue("display").equals("none"))
				{
					columns.remove(cnum);
				}
			}
			
			// Define array size for cell values
			String[] ColumnValues = new String[columns.size()];
			
			// Write cell values to array
			for(int cnum=0; cnum<columns.size(); cnum++)
			{
				ColumnValues[cnum] = columns.get(cnum).getText();
			}
			
			// Add cell values to row
			GridValues[rnum] = ColumnValues;
		}
		
		// Return all rows
		return GridValues;
	}
	
	// Get specific row from grid
	public String[][] GetSpecificRows(WebElement table, int rowsToGet, Boolean containsHiddenRow)
	{
		// Define rows count
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		
		// Delete hidden row
		if(containsHiddenRow) rows.remove(0);
		
		// Define array size for array which will be returned
		String[][] GridValues = new String[rowsToGet][];
		
		// Run through rows
		for(int rnum=0; rnum < rowsToGet; rnum++)
		{
			// Define cells count
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
			int sizeBeforeCut = columns.size();
			
			// Remove hidden cells
			for(int cnum= (sizeBeforeCut - 1); cnum != 0; cnum--)
			{
				if(columns.get(cnum).getCssValue("display").equals("none"))
				{
					columns.remove(cnum);
				}
			}
			
			// Define array size for cell values
			String[] ColumnValues = new String[columns.size()];
			
			// Write cell values to array
			for(int cnum=0; cnum<columns.size(); cnum++)
			{
				ColumnValues[cnum] = columns.get(cnum).getText();
			}
			
			// Add cell values to row
			GridValues[rnum] = ColumnValues;
		}
		
		// Return specific amount of rows([rowsToGet]
		return GridValues;
	}
	
	// Arrays compare
	public void gridValuesEqualityCheck(String[][] ExpectedValues, String[][] ActualValues)
	{
		// Check rows count equality
		if(ExpectedValues.length != ActualValues.length)
		{
			System.err.println("Error while 'Grid' check! Expected rows amount != Actual rows amount." +
					   		   "\r\nExpected rows amount = " + ExpectedValues.length +
					   		   "\r\nActual rows amount = " + ActualValues.length);
			Assert.fail();
		}
		
		// Run through rows
		for(int rowNum = 0; rowNum < ActualValues.length; rowNum++)
		{
			// If amount of cells in specific row doesn't equal to expected - print error
			if(ExpectedValues[rowNum].length != ActualValues[rowNum].length)
			{
				System.err.println("Error while 'Grid' check! Cells amount in checked row != real cells amount." +
				   		   		   "\r\nExpected amount = " + ExpectedValues[rowNum].length +
				   		   		   "\r\nActual amount = " + ActualValues[rowNum].length + 
				   		   		   "\r\nChecked row = " + (rowNum + 1));
				Assert.fail();
			}
			
			// Run through cells
			for(int colNum = 0; colNum < ActualValues[rowNum].length; colNum++)
			{
				// Cut time values
				if(ActualValues[rowNum][colNum].contains(":") == true)
				{
					// Count ":"
					int countOfCharacters = ActualValues[rowNum][colNum].length() - ActualValues[rowNum][colNum].replace(":", "").length();
					
					// If count  of ":" = 2 - cut time
					if(countOfCharacters == 2)
					{
						ActualValues[rowNum][colNum] = ActualValues[rowNum][colNum].substring(0, (ActualValues[rowNum][colNum].indexOf(" ")));
					}
				}
				
				// Check that not empty
				if(ExpectedValues[rowNum][colNum] == "NotNull")
				{
					// Assert equality of actual and expected values
					assertThat(ActualValues[rowNum][colNum], is(not(equalTo(("")))));
					assertThat(ActualValues[rowNum][colNum], is(not(equalTo((" ")))));
				}
				
				// Check that actual == expected
				else
				{
					// Assert equality of actual and expected values
					assertThat(ActualValues[rowNum][colNum], is(equalTo(ExpectedValues[rowNum][colNum])));
}
			}			
		}
	}
	
	// Cut single-dimensional array
	public String[] arrayElements_Remove(String[] array, int[] elements_ToRemove)
	{
		// Create list and remove elemenets
		List<String> list = new ArrayList<String>(Arrays.asList(array));
		for(int i=0; i<elements_ToRemove.length; i++)list.remove(elements_ToRemove[i]);
		
		// Convert list to array
		array = new String[list.size()];
		array = list.toArray(array);
		
		return array;
	}
}
