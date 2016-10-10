package com.esco.core;

public class EnvironmentConfiguration 
{
	private static final YamlEnvironments ALL_ENVIRONMENTS;
	static
	{
		YamlParser parser = new YamlParser("environment.yml");
		ALL_ENVIRONMENTS = parser.parseAs(YamlEnvironments.class);
	}
	
	private static YamlEnvironment GLOBAL_ENVIRONMENT;
	private static EnvironmentConfiguration CONFIG;
	
	private EnvironmentConfiguration(){}
	
	public static EnvironmentConfiguration getConfig()
	{
		if(CONFIG == null)
		{
			CONFIG = new EnvironmentConfiguration();
			return CONFIG;
		}
		return CONFIG;
	}
	
	public static void setGlobalEnvironment(String environmentKey)
	{
		GLOBAL_ENVIRONMENT = ALL_ENVIRONMENTS.environments.get(environmentKey);
	}
	
	public YamlEnvironment getEnvironmentSettings()
	{
		return GLOBAL_ENVIRONMENT;
	}
}

