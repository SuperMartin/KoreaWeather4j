package kr.ac.chungbuk.bigdata.weather.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

	public static Properties getXMLProperties(String path) {
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			properties.loadFromXML(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}

}
