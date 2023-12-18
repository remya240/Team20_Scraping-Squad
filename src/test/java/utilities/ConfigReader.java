package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	public static Properties prop;// inbuilt class (reusing)
	public final static String propertyFilePath = "./src/test/resources/config.properties";

	public static void loadConfig() {

		try {

			FileInputStream Fis = new FileInputStream(propertyFilePath);
			prop = new Properties();
			try {
				prop.load(Fis);
				Fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	// Get Heypertension elimiated list 
	public static String getEliminatorListHypertension() {
		String eliminators = prop.getProperty("HypertensionEliminators");
	
		if (eliminators != null) {
			return eliminators;
		} else {
			throw new RuntimeException("HypertensionEliminators not specified in config.properties file");
		}
	}
	// Get Heypertension toAdd list 
		public static String getToAddListHypertension() {
			String toAdd = prop.getProperty("HypertensionToAdd");
		
			if (toAdd != null) {
				return toAdd;
			} else {
				throw new RuntimeException("HypertensionToAdd not specified in config.properties file");
			}
		}
	
	// Get 
		public static String getEliminatorListForDiabetes() {
		
			String eliminators = prop.getProperty("DiabetesEliminators");
			
			if (eliminators != null) {
				return eliminators;
			} else {
				throw new RuntimeException("DiabetesEliminators not specified in config.properties file");
			}
		}

}
