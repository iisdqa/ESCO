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
	private static String queriesPath = "C:\\Selenium_TestData\\Projects\\ESCO\\SQL\\SQL_Queries\\";
	private static String TextFiles_Path = System.getProperties().get("basedir").toString() + "\\storage\\files\\temp_files\\text_files\\";
	
	// ����� �� ����� '���������'
	public static class EnergyAuditTests
	{
		public static class Objects
		{
			public static class Select_Queries
			{
			}
			
			public static class Deletion_Queries
			{
				// ����������� ������, ������� ����� �������� � ������ ������� �������
			    public static String objectDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� �������.\r\n����� ������:\r\n";
 			 			    
			    // ����������� ������ �������
			    public static String objectDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "object_deletion.sql");
			}	
		}
		
		public static class Auditors
		{
			public static class Select_Queries
			{
			}
			
			public static class Deletion_Queries
			{
				// ����������� ������, ������� ����� �������� � ������ ������� �������
			    public static String auditorDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� ��������.\r\n����� ������:\r\n";
 			 			    
			    // ����������� ������ �������
			    public static String auditorDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "auditor_deletion.sql");
			}	
		}
	}
	
	public static class DictionaryTests
	{
		// ������� ��� �������� ������
		public static class Deletion_Queries
		{
			// ����������� ������, ������� ����� �������� � ������ ������� ��������
		    public static String cacheUpdateValueDeletion_ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� ������, �������, ������ � ��. �� ������� ���(��� ���������� ����).\r\n����� ������:\r\n";
		    
		    // ����������� ������ �������
		    public static String cacheUpdateValueDeletion_Statement = new CustomMethods().new WorkWith_TextFiles().file_Read(queriesPath + "handbook_cleanup_for_cached_data.sql");
		}
	}
	
	public static class AdministrationTests
	{
		public static class UsersAndAuditQueries
		{
			public static class DeletionQueries
			{
				// ����������� ������, ������� ����� �������� � ������ ������� �������
			    public static final String ErrorMessage = "\r\n\r\n��������� ������ ��� ������� �������� ������������.\r\n����� ������:\r\n";
 			 			    
			    // ����������� ������ �������
			    public static String UserDeletionStatement = readFile(queriesPath + "user_deletion.sql");
			}
		}
	}	
	
	// ��������� ����� �� �����
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
			Assert.fail("�� ������� ����� ���� ��� ������� ����������� SQL �������.");
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
