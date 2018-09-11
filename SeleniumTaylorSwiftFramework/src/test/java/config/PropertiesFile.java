package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import test.TaylorSwiftTest;

public class PropertiesFile {

	static Properties prop = new Properties();
	static String projectPath = System.getProperty("user.dir");  			// Use relative path.

	public static void main(String[] args) {
		getProperties();
	}

	public static void getProperties() {    					 		   // Set up properties file path.
		try {
			InputStream input = new FileInputStream(projectPath + "/src/test/java/config/config.properties"); // Variable for properties file location.
			prop.load(input);									 		   // Load the properties file.
			String browser = prop.getProperty("browser"); 		 		   // Read properties file.
			System.out.println("\n *** Browser used for this test : " + browser); // Display th eBrowser used for this test.
			TaylorSwiftTest.browserName=browser;

		} catch (Exception exp) {
			System.out.println(exp.getMessage());
			System.out.println(exp.getCause());
			exp.printStackTrace();
		} 

	}

}


