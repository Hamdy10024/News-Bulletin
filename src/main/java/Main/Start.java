package Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Start {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String propertiesFile = args[0];
		Properties properties = new Properties();
		properties.load(new FileInputStream(propertiesFile));
	}
}
