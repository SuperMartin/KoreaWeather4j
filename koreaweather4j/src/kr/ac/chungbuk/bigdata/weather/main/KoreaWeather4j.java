package kr.ac.chungbuk.bigdata.weather.main;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.ac.chungbuk.bigdata.weather.manager.HttpProtocolManager;
import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;
import kr.ac.chungbuk.bigdata.weather.model.gson.TestCase4WindMap;
import kr.ac.chungbuk.bigdata.weather.util.GeoLocationManager;

/**
 * 기상청의 데이터를 쉽게 가져오기 위해 제작된 OPEN API입니다.
 * 
 * @version 0.2.0
 * 
 * @author MartinPark (ruserive@gmail.com)
 * @author SuperMario (charisma0629@gmail.com)
 */
public class KoreaWeather4j {

	// 1. 초기화단계(Map 초기화, HTTP 프로토콜 초기화)
	// 2. 진행단계
	// 3. 종료단계

	public static void main(String[] args) throws Throwable {
		
		//CLI APACHE PROJECT
		Logger logger = LoggerFactory.getLogger(KoreaWeather4j.class);
		logger.info("{}", "starting application");
		logger.info("{}", "Initializing GeoLocationManager");
		GeoLocationManager.initialize();
		logger.info("{}", "Initializing GeoLocationManager...OK");
		
		// AWS Data를 받아올 날짜 입력 (YYYYMMDDHHMM). null = 현재.
		// 추가해야 할 사항 : 1.과거 데이터 수집 시, From~To 기능 추가 / 2.일정 간격으로 계속 수집하는 기능 추가
		
//		String inputDate = null; // ex = "201405201800"
		String inputDate = "201512271800"; // ex = "201405201800"
		HttpProtocolManager httpMgr = new HttpProtocolManager();
		String httpRequest = httpMgr.getHttpURL(inputDate);
		String responseHtml = httpMgr.getWeatherData(httpRequest);
		List<KoreaWeatherAWSModel> koreaWeatherDataList = httpMgr.getKoreaAWSWeatherDataList(responseHtml);


		// db에 저장. 현재 MySQL로 저장 가능한 상태.
		// 추가해야 할 사항 : 1.Create, Update 기능 추가 / 2.MS_SQL 연동 추가
		
//		DatabaseConnectionPoolManager database = new DatabaseConnectionPoolManager(true, "ipaddress", "port", "databaseName", "user", "passwd", "tableName");	
		//isMysql = true or false;
		//ipaddress = 000.000.000.000;
		//port = 0000;
		//databaseName = databaseName;
		//user = user;
		//passwd = passwd;
		//tableName = tableName;
//		long start = System.currentTimeMillis();
//		database.persistAWSWeatherData(koreaWeatherDataList);
//		long end = System.currentTimeMillis();
//		System.out.println("** Inserted Recoreds : " + koreaWeatherDataList.size() + " Working Time : " + (end - start) + " ms ");

		
		// Gson File로 저장.
		// 추가해야 할 사항 : 1.Output Data의 형식들(csv, tsv, xml, json 등등) / 2.Gson->Wx->info 수정
		TestCase4WindMap windMap = new TestCase4WindMap();
		File file = new File("");
		String path = file.getAbsolutePath();
		path = path.substring(0, path.lastIndexOf(File.separatorChar));
		path = path.substring(0, path.lastIndexOf(File.separatorChar));
		String fileLocation = path + File.separatorChar + "workspace" + File.separatorChar + "BellaTellus" + File.separatorChar + "WebContent" + File.separatorChar + "data" + File.separatorChar + "KoreaAWS.js";
		windMap.setFileLocation(fileLocation);
		windMap.makeWindMapGSonFile(koreaWeatherDataList);
		logger.info("{}", "shutdown application");
	}
}
