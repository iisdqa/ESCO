package com.esco.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Parameters;

import com.esco.core.web.CustomMethods;

public class DbConnection 
{		
	protected static Connection connection;
	protected static Statement st;
	protected static String sqlPath = "C:\\Selenium_TestData\\Projects\\ESCO\\SQL\\";
	
	@Parameters({"environment"})
	public static Connection setDbConnection(String environment)
	{
		// Определение URL, логина и пароля для подключения
		String conUrl = defineConnection(environment);

		// Вытяжка юзера из файла
		String[] user = new CustomMethods().new WorkWith_TextFiles().file_Read(sqlPath + "db_user.txt").split("&");
		String login = user[1].trim().replace("\r\n", "");
		String password = user[2].trim();
		
		try 
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(conUrl, login, password);
			st = connection.createStatement();
			st.close();
		} 
		
		catch (Exception e) 
		{
			System.out.println("Ошибка при подключении к БД: " + e);
		}
		
		return connection;
	}
	
	public static void closeConnection(Connection sqlConnection)
	{
		try 
		{
			sqlConnection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static String defineConnection(String environment)
	{
		// Вытяжка серверов из файла
		String[] servers = new CustomMethods().new WorkWith_TextFiles().file_Read(sqlPath + "db_servers.txt").split("#");
		String dev = servers[1].trim().replace("\r\n", "");
		String release = servers[2].trim();
		
		// Объявление переменной
		String conUrl = null;
		
		// Если 'dev' площадка
		if(environment.equals("dev"))
		{
			conUrl = dev;
		}
		
		// Если 'release' площадка
		else if(environment.equals("release"))
		{
			conUrl = release;
		}
		
		// Если другое, то ошибка
		else
		{
			System.out.println("Ошибка при попытке определения URL для подключения к БД. Переменная 'environment' = " + environment);
		}
		
		// Вернуть URL подключения
		return conUrl;
	}
}
