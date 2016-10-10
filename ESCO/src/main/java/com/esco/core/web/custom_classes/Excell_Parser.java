package com.esco.core.web.custom_classes;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.esco.core.web.CustomMethods;
import com.esco.core.web.elements.Button;

public abstract class Excell_Parser 
{
	public String[][] GetHeadPart_ForExcel(WebElement thead)
	{
		// Определение количества рядов
		List<WebElement> rows = thead.findElements(By.tagName("tr"));
		
		// Определение размера возвращаемого массива
		String[][] GridValues = new String[rows.size()][];
		
		// Перебираем ряды
		for(int rnum=0; rnum < rows.size(); rnum++)
		{
			// Определяем количество колонок
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("th"));
			int sizeBeforeCut = columns.size();
						
			// Отсеевание скрытых ячеек
			for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
			{
				if(columns.get(cnum).getCssValue("display").equals("none"))
				{
					columns.remove(cnum);
				}
			}
			
			// Удаление лишних значений
			columns.remove(columns.size() - 1);
			columns.remove(0);
			
			// Определение размера массива с значениями ячеек
			String[] ColumnValues = new String[columns.size()];
			
			// Записываем значения ячеек в массив
			for(int cnum=0; cnum<columns.size(); cnum++)
			{
				// Взять текст из элемента
				ColumnValues[cnum] = columns.get(cnum).findElement(By.tagName("div")).getAttribute("textContent");   // Также можно пробовать атрибуты 'innerText', 'innerHTML'
			}
			
			// Положить значения колонок в ряд 
			GridValues[rnum] = ColumnValues;
		}
		return GridValues;
	}
	
	public String[][] GetAllRows_ForExcel(WebElement tbody)
	{
		// Определение количества рядов
		List<WebElement> rows = tbody.findElements(By.tagName("tr"));
		
		// Удаление скрытого ряда
		rows.remove(0);
		
		// Определение размера возвращаемого массива
		String[][] GridValues = new String[rows.size()][];
		
		// Перебираем ряды
		for(int rnum=0; rnum < rows.size(); rnum++)
		{
			// Определяем количество колонок
			List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
			int sizeBeforeCut = columns.size();
						
			// Отсеевание скрытых ячеек
			for(int cnum= (sizeBeforeCut - 1); cnum >= 0; cnum--)
			{
				if(columns.get(cnum).getCssValue("display").equals("none"))
				{
					columns.remove(cnum);
				}
			}
			
			// Удаление лишних значений
			columns.remove(columns.size() - 1);
			columns.remove(0);
			
			// Определение размера массива с значениями ячеек
			String[] ColumnValues = new String[columns.size()];
			
			// Записываем значения ячеек в массив
			for(int cnum=0; cnum<columns.size(); cnum++)
			{
				ColumnValues[cnum] = columns.get(cnum).getText();
			}
			
			// Положить значения колонок в ряд 
			GridValues[rnum] = ColumnValues;
		}
		return GridValues;
	}
	
	public String[][] get_ExcelValues(Button DownloadButton, String ExpectedFileName)
	{
		// Скачать
		DownloadButton.click();	
		new CustomMethods().simpleWait(5);
		
		// Папка, куда должны попадать файлы
		String folderPath = new CustomMethods().BASE_DIR + "\\storage\\files\\downloaded_files";
		
		// Найти все файлы в папке
		File folderToScan = new File(folderPath); 
	    File[] listOfFiles = folderToScan.listFiles();
	    int filesCount = listOfFiles.length;
		
    	// Если не удается найти файл по указанному линку - удалить все файлы и выдать ошибку
    	if(filesCount != 1)
    	{
    		// Удалить все файлы, если они есть
    		if(filesCount != 0)
    		{
        		for(int i = 0; i < filesCount; i++)
        		{
        			File tempFileVariable = listOfFiles[i];
        			tempFileVariable.delete();
        		}
    		}
    		
    		// Вывести ошибку
    		System.err.println("Ошибка загрузки файла Excel для проверки. Количество найденных файлов != ожидаемому." +
 		   		   			   "\r\nОжидаемый файл = " + ExpectedFileName + 
 		   		   			   "\r\nКоличество найденных файлов = " + filesCount);
    		Assert.fail();
    	}
    	
    	// Вытянуть файл из массива
    	File file = listOfFiles[0];
    	
    	// Определение массива для возврата
    	String[][] data = null;
    	
    	try
    	{
    		data = getExcelValues(file);
    	}  	
		catch(Exception e)
		{
    		System.err.println("Ошибка при считывании значений из Excel файла." +
   		   			   		   "\r\nТекст ошибки = " + e.getMessage() +
   		   			   		   "\r\nСтэк = " + e.getStackTrace());
		}
		finally
		{
    		file.delete();
		}
    	
    	return data;
	}
	
	private String[][] getExcelValues(File excelFile)
	{
		Workbook w;
	    String[][] data = null;

        try 
        {
        	// Считывание файла
            w = Workbook.getWorkbook(excelFile);
            
            // Взять первый лист
            Sheet sheet = w.getSheet(0);    
        
            // Определение размера массива
            data = new String[sheet.getRows()][sheet.getColumns()];
            
            // Перебор рядов
            for (int i = 0; i <sheet.getRows(); i++) 
            {
            	//Перебор колонок + запись ячеек в массив
                for (int j = 0; j < sheet.getColumns(); j++) 
                {
                	// Взять значение из ячейки
                    Cell cell = sheet.getCell(j, i);
                    String cellValue = cell.getContents();
                    
                    // Убрать лишние символы('-' и ';')
                    cellValue.replace("-", "");
                    cellValue.replace(";", "");
                    cellValue = new CustomMethods().StringSpacesCut(cellValue);
                    // Запись значения в массив
                    data[i][j] = cellValue;
                }
            }

        } 
        
        catch (BiffException e) {e.printStackTrace();} 
        catch (IOException e) {e.printStackTrace();}
        
        return data;
	}
}
