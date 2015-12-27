package kr.ac.chungbuk.bigdata.weather.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLocationManager {
	private static Logger					logger				= LoggerFactory.getLogger(GeoLocationManager.class);
	private static GeoLocationManager		INSTANCE;
	private static HashMap<String, String>	idLatLon;
	private static Map<String, String>		idAddressMap;

	private static String					idLatLngFilePath	= "meta_data"+File.separatorChar+"id_lat_lng.meta";
	private static String					addressFilePath		= "meta_data"+File.separatorChar+"id_address.meta";

	static {
		INSTANCE = getInstance();
		idLatLon = new HashMap<String, String>();
		idAddressMap = new HashMap<String, String>();
	}

	private GeoLocationManager() throws FileNotFoundException, IOException {
		// initialize();
	}

	public static void initialize() throws FileNotFoundException, IOException {
		INSTANCE = getInstance();
		logger.debug("{}", "INITIALIZING GeoLocationManager");

		BufferedReader br;

		File file = new File(idLatLngFilePath);
		if (!file.exists())
			file.createNewFile();
		FileReader fr = new FileReader(file);
		br = new BufferedReader(fr);
		String str = null;
		while ((str = br.readLine()) != null) {
			String[] array = str.split("\t");
			String key = array[0];
			String value = (array[1] + "\t" + array[2]);
			idLatLon.put(key, value);
		}
		fr.close();
		br.close();
		logger.debug("{}", "INITIALIZING GeoLocationManager...DONE");

		logger.debug("{}", "INITIALIZING Address MAP");

		initializeAddressMap();

		logger.debug("{}", "INITIALIZING Address MAP...DONE");

	}

	public static GeoLocationManager getInstance() {
		if (INSTANCE == null) {
			synchronized (GeoLocationManager.class) {
				if (INSTANCE == null) {
					try {
						INSTANCE = new GeoLocationManager();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return INSTANCE;
	}

	public static String getLatLng(String id) {
		if (INSTANCE == null) {
			getInstance();
		}
		String resultLatLong = idLatLon.get(id);
		return resultLatLong;
	}

	public static void putLatLng(String id, String latLng) throws IOException {
		idLatLon.put(id, latLng);
		FileUtils.writeStringToFile(new File(idLatLngFilePath), id + "\t" + latLng + "\n", true);

	}

	public static void initializeAddressMap() throws IOException {
		File file = new File(addressFilePath);
		if (!file.exists())
			file.createNewFile();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String s = null;
		while ((s = br.readLine()) != null) {
			String[] value = s.split("\t");
			idAddressMap.put(value[1], value[0]); // [0]==id , [1]==address
		}
		br.close();
		fr.close();

	}

	public static boolean checkIDExists(String id) {
		boolean returnVal = false;
		if (idAddressMap.containsValue(id))
			returnVal = true;
		return returnVal;
	}

	public static void putAddress(String id, String address) throws IOException {
		idAddressMap.put(address, id);
		FileUtils.writeStringToFile(new File(addressFilePath), id + "\t" + address + "\n", true);

	}

}
