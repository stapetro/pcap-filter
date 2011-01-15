package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import constants.PCapFilterConstants;

public class ConfigurationReader {

	public static String getValue(String keyName) {
		FileInputStream configInputStream = null;
		try {
			configInputStream = new FileInputStream(
					PCapFilterConstants.CONFIG_PROP_FILE_NAME);
			Properties configProps = new Properties();
			try {
				configProps.load(configInputStream);
				return configProps.getProperty(keyName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (configInputStream != null) {
				try {
					configInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
