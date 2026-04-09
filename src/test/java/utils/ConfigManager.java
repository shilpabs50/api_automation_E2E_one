package utils;


import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	
	private static Properties properties = new Properties();
	

	static {
		
		try {
			// to load default/master config file if the env is not passed from the command line
			 //Step 1: Load master config (config.properties)
			InputStream masterConfig = ConfigManager.class
					                                .getClassLoader()
					                                .getResourceAsStream("config.properties");
			
			Properties masterProps = new Properties();
			masterProps.load(masterConfig);
								
			
			//Step 2: Get environment (priority: command line > config file)
			String env = System.getProperty("env",masterProps.getProperty("env"));
			
			if(env == null || env.isEmpty()) {
				
				throw new RuntimeException("Environment (env) is not specified!!");
			}
			
			//Step 3: Build env file name
			String envFileName = "config-"+env+".properties";
			
			// Step 4: Load environment-specific config
			InputStream envConfig = ConfigManager.class
												 .getClassLoader()
												 .getResourceAsStream(envFileName);
			properties.load(envConfig);			
			
			System.out.println("Loaded environment: "+env);
			
		}catch(Exception e){
			throw new RuntimeException("Failed to load the confile file",e);
			
		}
	}
		
		
		public static String get(String key) {
			
			String value = properties.getProperty(key);
			if(value == null) {
				throw new RuntimeException("Value not found for the key: "+key);
			}
			
			return value;
	   }
	
	
		
		public static int getInt(String key) {
			return Integer.parseInt(get(key));
			
		}
	
	
	
}
