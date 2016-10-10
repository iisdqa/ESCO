package com.esco.core.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;

import com.esco.core.web.CustomMethods;

public class DbQueries 
{	
	private static String queriesPath = "C:\\Selenium_TestData\\Projects\\DocsFlow\\SQL\\SQL_Queries\\";
	private static String TextFiles_Path = System.getProperties().get("basedir").toString() + "\\storage\\files\\temp_files\\text_files\\";
	
	// Тесты по блоку 'Документи'
	public static class DocsTests
	{
		public static class Incoming_Docs
		{
			public static class Select_Queries
			{
				// Определение ошибки, которую будем выводить в случае падения запроса
			    public static String IndexesCountDefine_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке выборки количества документов из БД за определенный период.\r\nТекст ошибки:\r\n";
 			 			    
			    // Определение текста запроса
			    public static String IndexesCount_Define = "select dc.DNM_NUM" + "\r\n" +
			    								           "from [dbo].[DocNum] dc"  + "\r\n" +
			    								           "join [dbo].Tree_Staff tf on dc.STT_IDP = tf.STT_IDP"  + "\r\n" +
			    								           "where dc.DNM_YER = 2020 and tf.STT_LNAM = 'Входящие документы'";
			}
			
			public static class Deletion_Queries
			{
				// Определение ошибки, которую будем выводить в случае падения запроса
			    public static String DocDeletion_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления входящего документа.\r\nТекст ошибки:\r\n";
 			 			    
			    // Определение текста запроса
			    public static String DocDeletion_Statement()
			    {
			    	String statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "incoming_doc_deletion.sql");
			    	String docIndex = new CustomMethods().new WorkWith_TextFiles().file_Read(TextFiles_Path + "IncomingDoc_Index");
			    	statement = statement.replace("&", docIndex);
			    	return statement;
			    }
			}	
		}
	}
	
	// Тесты по блоку 'ЦНАП'
	public static class CnapTests
	{
		// Реестры
		public static class Registers
		{
			// Реестр 'ФО'
			public static class Individuals
			{
				// Запросы для удаления данных
				public static class Deletion_Queries
				{
					// Определение ошибок, которые будем выводить в случае падения запросов
				    public static String FoDeletion_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления карточек в реестре 'ФО'.\r\nТекст ошибки:\r\n";
				    public static String RegPlaceDeletion_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления страны, области, города и тд. из словаря НДИ.\r\nТекст ошибки:\r\n";		    
				    
				    // Определение самих запросов
				    public static String FoDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "fo_deletion.sql");
				    public static String RegPlaceDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "registration_place_deletion.sql");
				}	
			}
			
			// Реестр 'ФОП'
			public static class Entrepreneurs
			{
				// Запросы для удаления данных
				public static class Deletion_Queries
				{
					// Определение ошибок, которые будем выводить в случае падения запросов
				    public static String FopDeletion_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления карточек в реестре 'ФОП'.\r\nТекст ошибки:\r\n";				   		   
				    
				    // Определение самих запросов
				    public static String FopDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "fop_deletion.sql");				    
				}	
			}
		}
	}
	
	public static class DictionaryTests
	{
		// Запросы для удаления данных
		public static class Deletion_Queries
		{
			// Определение ошибок, которые будем выводить в случае падения запросов
		    public static String cacheUpdateValueDeletion_ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления страны, области, города и тд. из словаря НДИ(для обновления кэша).\r\nТекст ошибки:\r\n";
		    
		    // Определение текста запроса
		    public static String cacheUpdateValueDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "handbook_cleanup_for_cached_data.sql");
		}
	}
	
	public static class AdministrationTests
	{
		public static class UsersAndAuditQueries
		{
			public static class DeletionQueries
			{
				// Определение ошибки, которую будем выводить в случае падения запроса
			    public static final String ErrorMessage = "\r\n\r\nПроизошла ошибка при попытке удаления пользователя.\r\nТекст ошибки:\r\n";
 			 			    
			    // Определение текста запроса
			    public static String UserDeletionStatement = readFile(queriesPath + "user_deletion.sql");
			}
		}
	}	
	
	// Прочитать текст из файла
	private static String readFile(String path)
	{
		File file = new File(path);
		Reader input = null;
		StringWriter output = new StringWriter();
		try 
		{
			input = new FileReader(file);
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
			Assert.fail("Не удалось найти файл при попытке определения SQL запроса.");
		}
		try 
		{
		  IOUtils.copy(input, output);
		  input.close();
		}	  
		catch (Exception e) 
	    {
			e.printStackTrace();
	    }
		String fileContents = output.toString();
		return fileContents;
	}
}
