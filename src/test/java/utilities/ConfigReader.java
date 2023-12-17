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

	// Get 
	public static String getEliminatorList() {
		String eliminators = prop.getProperty("AllergyEliminators");
		if (eliminators != null) {
			return eliminators;
		} else {
			throw new RuntimeException("eliminators not specified in config.properties file");
		}
	}

}

