package com.esco.core.web.custom_classes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;

import com.esco.core.web.CustomMethods;
import com.esco.core.web.elements.Button;

public abstract class WorkWith_Files 
{
	// Upload file with robot events
	public void file_Upload(String filePath)
	{
		// Should wait a bit
		new CustomMethods().simpleWait(2);
		
		// Add filePath to Clipboard
		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		
		// Robot events(CTRL + V and ENTER)
		try 
		{
			Robot rob = new Robot();
			rob.keyPress(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_V);
			rob.keyRelease(KeyEvent.VK_V);
			rob.keyRelease(KeyEvent.VK_CONTROL);
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
		} 
		catch (AWTException e) 
		{
			e.printStackTrace();
		}
		
	}
		
	// Add text file with specified info
    public void file_Create(String filePath, String info)
    {   
        try 
        {
            // File object
            File file = new File(filePath);

            // If file exists - delete
            if (file.exists() == true)
            {
            	file.delete();
            }

            // Add file and write info
            file.createNewFile();
        	FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(info);
			bw.close();
        }
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    }

    // Read info from text file
    public String file_Read(String filePath)
    {
        // File object
        File file = new File(filePath);
        
        // Where info will be written
        String fileInfo = null;
        
    	try 
    	{
    		// Read info from file and write to 'fileInfo'
    		Reader input = null;
    		StringWriter output = new StringWriter();
    		input = new FileReader(file);
    		IOUtils.copy(input, output);
  		  	input.close();
  		  	fileInfo = output.toString();
    	}
    	catch (Exception e) 
		{
			e.printStackTrace();
			Assert.fail("Error! Can't read info from text file.");
		}
        return fileInfo;
    }

    // File deletion
    public void file_Delete(String filePath)
    {
        // File object
        File file = new File(filePath);

        // If file exists - delete
        if (file.exists() == true)
        {
        	file.delete();
        }
    }
    
    // File download casual check
	public void fileDownload_Check(Button downloadButton, String expectedFileName)
	{
		// Download
		downloadButton.click();	
		new CustomMethods().simpleWait(3);
		
		// Define file download path
		String folderPath = new CustomMethods().BASE_DIR + "\\storage\\files\\downloaded_files";
		
		// Find all files in folder
		File folderToScan = new File(folderPath); 
	    File[] listOfFiles = folderToScan.listFiles();
	    int filesCount = listOfFiles.length;
		
    	// If files count > 1  - delete all files
    	if(filesCount != 1)
    	{
    		// If files count = 0 - do nothing 
    		if(filesCount != 0)
    		{
        		for(int i = 0; i < filesCount; i++)
        		{
        			File tempFileVariable = listOfFiles[i];
        			tempFileVariable.delete();
        		}
    		}
    		
    		// Send error
    		System.err.println("File download error. Files found amount != expected." +
 		   		   			   "\r\nExpected file = " + expectedFileName + 
 		   		   			   "\r\nFound files amount = " + filesCount);
    		Assert.fail();
    	}
    	
    	// Вытянуть файл из массива
    	File file = listOfFiles[0];
    	
    	// Define file extension
    	String fileFullName = file.getName();
    	int splitter = fileFullName.lastIndexOf('.');
    	String fileName = fileFullName.substring(0, splitter);
    	String fileExtension = fileFullName.substring(splitter + 1);
    	
    	try
    	{
        	// If Excel file - check file name partially
        	if(fileExtension.equals("xls"))
        	{
            	splitter = expectedFileName.lastIndexOf('.');
            	String ExpectedFileName_Cut = expectedFileName.substring(0, splitter);
        		assertThat(fileName, startsWith(ExpectedFileName_Cut));
        	}
        	
        	// For other - check full name
        	else
        	{
    			assertThat(fileFullName, is(equalTo(expectedFileName)));
        	}
    	}
    	
		catch(Exception e)
		{
    		System.err.println("Error while file name check!" +
   		   			   		   "\r\nError = " + e.getMessage() +
   		   			   		   "\r\nStack = " + e.getStackTrace());
		}
		finally
		{
    		file.delete();
		}
	}
}
