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
	public static String DiabetesgetEliminatorList() {
		String eliminators = prop.getProperty("DiabetesEliminators");
		if (eliminators != null) {
			return eliminators;
		} else {
			throw new RuntimeException("eliminators not specified in config.properties file");
		}
	}

	public static String HypertensiongetEliminatorList() {
		String eliminators = prop.getProperty("HypertensionEliminators");
		if (eliminators != null) {
			return eliminators;
		} else {
			throw new RuntimeException("eliminators not specified in config.properties file");
		}
	}

	public static String PCOSEliminatorsList() {
		String eliminators = prop.getProperty("PCOSEliminators");
		if (eliminators != null) {
			return eliminators;
		} else {
			throw new RuntimeException("eliminators not specified in config.properties file");
		}
	}

	public static String HypothyroidismList() {
		String eliminators = prop.getProperty("HyperthyroidismEliminators");
		if (eliminators != null) {
			return eliminators;
		} else {
			throw new RuntimeException("eliminators not specified in config.properties file");
		}
	}

	public static String AllergyList() {
		String eliminators = prop.getProperty("AllergyEliminators");
		if (eliminators != null) {
			return eliminators;
		} else {
			throw new RuntimeException("eliminators not specified in config.properties file");
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

	public static String getToAddListDiabetes() {
		String toAdd = prop.getProperty("DiabetesToAdd");

		if (toAdd != null) {
			return toAdd;
		} else {
			throw new RuntimeException("DiabetesToAdd not specified in config.properties file");
		}
	}

	public static String getToAddListHypothyroidsm() {
		String toAdd = prop.getProperty("HypothyroidismToAdd");

		if (toAdd != null) {
			return toAdd;
		} else {
			throw new RuntimeException("HypothyroidismToAdd not specified in config.properties file");
		}
	}
	public static String getToAddListPCOS() {
		String toAdd = prop.getProperty("PCOSToAdd");

		if (toAdd != null) {
			return toAdd;
		} else {
			throw new RuntimeException("PCOSToAdd not specified in config.properties file");
		}
	}

}
