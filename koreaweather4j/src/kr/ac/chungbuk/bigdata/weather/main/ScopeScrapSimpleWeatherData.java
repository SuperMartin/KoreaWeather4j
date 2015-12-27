package kr.ac.chungbuk.bigdata.weather.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.ac.chungbuk.bigdata.weather.manager.DatabaseConnectionPoolManager2;
import kr.ac.chungbuk.bigdata.weather.manager.HttpProtocolManager2;
import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherSimpleModel;
import kr.ac.chungbuk.bigdata.weather.util.ConfigUtil;
import kr.ac.chungbuk.bigdata.weather.util.DateGenerator;
import kr.ac.chungbuk.bigdata.weather.util.GeoLocationManager;
import kr.ac.chungbuk.bigdata.weather.util.MarioService;

/**
 * 시작일과 종료일을 설정하여 날씨데이터를 가져옴
 * 
 * @author HYUNA,Chihwan Choi(charisma0629@gmail.com)
 *
 */
public class ScopeScrapSimpleWeatherData {
	Properties prop = ConfigUtil.getXMLProperties("koreaweather4j.xml");

	public static void printHowtoUse() {
		System.out.println(
				"USAGE: <FROMDATE(YYYY.MM.DD.HH)> <TODATE(YYYY.MM.DD.HH)>\n\r example $ java -jar korean_weather4j.jar 2015.08.05.00 2015.08.05.23");
	}

	public void getData(String fromDate, String toDate)
			throws FileNotFoundException, IOException, ParseException, InterruptedException {
		// CLI APACHE PROJECT
		Logger logger = LoggerFactory.getLogger(ScopeScrapSimpleWeatherData.class);
		logger.info("{}", "starting application");
		logger.info("{}", "Initializing GeoLocationManager");
		GeoLocationManager.initialize();
		logger.info("{}", "Initializing GeoLocationManager...OK");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH");
		List<Date> dateList = DateGenerator.doDateGenerateYYYYMMDDHH(fromDate, toDate, true);
		// List<Date> dateList =
		// DateGenerator.doDateGenerateYYYYMMDDHH("2015.08.05.00",
		// "2015.11.16.23", true);

		for (Date inputDate : dateList) {

			HttpProtocolManager2 httpMgr = new HttpProtocolManager2();
			String httpRequest = httpMgr.getHttpURL(sdf.format(inputDate));
			String responseHtml = httpMgr.getWeatherData(httpRequest);
			List<KoreaWeatherSimpleModel> koreaWeatherDataList = httpMgr.getKoreaSimpleWeatherDataList(responseHtml);

			// db에 저장. 현재 MySQL로 저장 가능한 상태.
			// 추가해야 할 사항 : 1.Create, Update 기능 추가 / 2.MS_SQL 연동 추가

			// DatabaseConnectionPoolManager2 database = new
			// DatabaseConnectionPoolManager2(false, "000.000.000.000", "0000", "DatabaseName", "UserID", "Password", "[DatabaseName].[dbo].[TableName]");

			DatabaseConnectionPoolManager2 database = new DatabaseConnectionPoolManager2(false,
					prop.getProperty("IPADDRESS"), prop.getProperty("PORT"), prop.getProperty("DATABASENAME"),
					prop.getProperty("USER"), prop.getProperty("PASSWORD"), prop.getProperty("TABLENAME"));
			long start = System.currentTimeMillis();
			database.persistSimpleWeatherData(koreaWeatherDataList);
			long end = System.currentTimeMillis();
			System.out.println("** Inserted Recoreds : " + koreaWeatherDataList.size() + " Working Time : "
					+ (end - start) + " ms ");

			try {
				Thread.sleep(1000); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		}
	}

	@Test
	public void test() throws FileNotFoundException, IOException, ParseException, InterruptedException {

		// CLI APACHE PROJECT
		Logger logger = LoggerFactory.getLogger(ScopeScrapSimpleWeatherData.class);
		logger.info("{}", "starting application");
		logger.info("{}", "Initializing GeoLocationManager");
		GeoLocationManager.initialize();
		logger.info("{}", "Initializing GeoLocationManager...OK");

		// AWS Data를 받아올 날짜 입력 (YYYYMMDDHHMM). null = 현재.
		// 추가해야 할 사항 : 1.과거 데이터 수집 시, From~To 기능 추가 / 2.일정 간격으로 계속 수집하는 기능 추가
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH");
		// List<Date> dateList = DateGenerator2.doDateGenerate("2013.05.07.20",
		// "2014.01.21.00", true);
		// List<Date> dateList = DateGenerator2.doDateGenerate("2015.08.04.00",
		// "2015.08.04.23", true);
		List<Date> dateList = DateGenerator.doDateGenerateYYYYMMDDHH("2015.08.05.00", "2015.11.16.23", true);
		// System.out.println(sdf2.format(sdf.parse("20141201000000")));

		for (Date inputDate : dateList) {

			HttpProtocolManager2 httpMgr = new HttpProtocolManager2();
			String httpRequest = httpMgr.getHttpURL(sdf.format(inputDate));
			// System.out.println(httpRequest);
			// System.out.println(sdf.format(inputDate));
			String responseHtml = httpMgr.getWeatherData(httpRequest);
			// System.out.println(responseHtml);
			List<KoreaWeatherSimpleModel> koreaWeatherDataList = httpMgr.getKoreaSimpleWeatherDataList(responseHtml);

			// db에 저장. 현재 MySQL로 저장 가능한 상태.
			// 추가해야 할 사항 : 1.Create, Update 기능 추가 / 2.MS_SQL 연동 추가

			DatabaseConnectionPoolManager2 database = new DatabaseConnectionPoolManager2(false, "000.000.000.000", "0000", "DatabaseName", "UserID", "Password", "[DatabaseName].[dbo].[TableName]");
			long start = System.currentTimeMillis();
			database.persistSimpleWeatherData(koreaWeatherDataList);
			long end = System.currentTimeMillis();
			System.out.println("** Inserted Recoreds : " + koreaWeatherDataList.size() + " Working Time : "
					+ (end - start) + " ms ");

			try {
				Thread.sleep(1000); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		}
		// String inputDate = "201406201800"; // ex = "201405201800"
	}

	public static void main(String[] args) {
		MarioService.printMarioAsciiPictureR2();
		if (args.length != 2) {
			ScopeScrapSimpleWeatherData.printHowtoUse();
			System.exit(1);
		}
		ScopeScrapSimpleWeatherData test = new ScopeScrapSimpleWeatherData();
		try {
			test.getData(args[0], args[1]);
		} catch (IOException | ParseException | InterruptedException e) {
			ScopeScrapSimpleWeatherData.printHowtoUse();
			e.printStackTrace();
		}
	}
}
