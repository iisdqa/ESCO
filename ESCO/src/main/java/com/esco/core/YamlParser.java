package com.esco.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class YamlParser
{
	private static final String CONFIG_FILES_ROOT = "config/";
	private final String fileName;
	
	
	public YamlParser(String fileName)
	{
		this.fileName = fileName;
	}
	
	public <T> T parseAs(Class<T> type)
	{
		T object;
		try
		{
			InputStream input = new FileInputStream(new File(CONFIG_FILES_ROOT + fileName));
			Yaml yaml = new Yaml();
			object = yaml.loadAs(input, type);
			input.close();
		}
		catch(Exception e)
		{
			throw new RuntimeException("Could not load next file - '" + CONFIG_FILES_ROOT + fileName + "'.");
		}
		return object;
	}
}

