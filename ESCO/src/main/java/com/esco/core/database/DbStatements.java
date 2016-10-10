package com.esco.core.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbStatements
{
	private Statement st;
	
	public void SimpleStatement(Connection sqlConnection, String myQuery, String errorMessage) throws Exception
	{
		try 
		{
			// Insert statement
			st = sqlConnection.createStatement();
			st.setQueryTimeout(30);
			st.executeUpdate(myQuery);
		} 
		catch(Exception e)
		{			
			throw new Exception(errorMessage + e.getMessage() + "\r\n\r\nСтэк:\r\n" + e.getStackTrace());
		}
		finally
		{
			st.close();
		}
	}
	
	public String Simple_SelectStatement(Connection sqlConnection, String myQuery, String errorMessage) throws Exception
	{
		String result = "";
		try 
		{
			// Insert statement
			st = sqlConnection.createStatement();
			st.setQueryTimeout(30);
		    ResultSet set = st.executeQuery(myQuery);
		    if(set.next()) result = set.getString(1);
		} 
		catch(Exception e)
		{			
			throw new Exception(errorMessage + e.getMessage() + "\r\n\r\nСтэк:\r\n" + e.getStackTrace());
		}
		finally
		{		
			st.close();			
		}
		
		return result;
	}
}
