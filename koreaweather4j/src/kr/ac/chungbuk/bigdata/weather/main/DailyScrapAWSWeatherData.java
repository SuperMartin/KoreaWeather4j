package kr.ac.chungbuk.bigdata.weather.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.ac.chungbuk.bigdata.weather.manager.DatabaseConnectionPoolManager;
import kr.ac.chungbuk.bigdata.weather.manager.HttpProtocolManager;
import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;
import kr.ac.chungbuk.bigdata.weather.util.DateGenerator;
import kr.ac.chungbuk.bigdata.weather.util.GeoLocationManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 어제 00시부터 23시 날씨데이터를 가져옴
 * 
 * @author HYUNA
 * 
 */
public class DailyScrapAWSWeatherData {
	public static void main(String[] args) throws Throwable {

		// CLI APACHE PROJECT
		Logger logger = LoggerFactory.getLogger(DailyScrapAWSWeatherData.class);
		logger.info("{}", "starting application");
		logger.info("{}", "Initializing GeoLocationManager");
		GeoLocationManager.initialize();
		logger.info("{}", "Initializing GeoLocationManager...OK");

		// AWS Data를 받아올 날짜 입력 (YYYYMMDDHHMM). null = 현재.
		// 추가해야 할 사항 : 1.과거 데이터 수집 시, From~To 기능 추가 / 2.일정 간격으로 계속 수집하는 기능 추가
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		long yesterday = System.currentTimeMillis() - 1000 * 60 * 60 * 24;

		Date date = new Date(yesterday);
		String dateString = sdf.format(date);
		// System.out.println(dateString);
		dateString = dateString.subSequence(0, 8) + "000000";
		Date fromDate = sdf.parse(dateString);
		Date toDate = new Date((fromDate.getTime() + 1000 * 60 * 60 * 24));
		// System.out.println(sdf.format(fromDate));
		// System.out.println(sdf.format(toDate));

		List<Date> dateList = DateGenerator.doDateGenerateYYYYMMDDHHMI(sdf.format(fromDate), sdf.format(toDate), true);
		for (Date inputDate : dateList) {
			//
			HttpProtocolManager httpMgr = new HttpProtocolManager();
			String httpRequest = httpMgr.getHttpURL(sdf.format(inputDate));
			String responseHtml = httpMgr.getWeatherData(httpRequest);
			List<KoreaWeatherAWSModel> koreaWeatherDataList = httpMgr.getKoreaAWSWeatherDataList(responseHtml);

			// db에 저장. 현재 MySQL로 저장 가능한 상태.
			// 추가해야 할 사항 : 1.Create, Update 기능 추가 / 2.MS_SQL 연동 추가
			DatabaseConnectionPoolManager database = new DatabaseConnectionPoolManager(false, "000.000.000.000", "0000", "DatabaseName", "UserID", "Password", "[DatabaseName].[dbo].[TableName]");
			long start = System.currentTimeMillis();
			database.persistAWSWeatherData(koreaWeatherDataList);
			long end = System.currentTimeMillis();
			System.out.println("** Inserted Recoreds : " + koreaWeatherDataList.size() + " Working Time : " + (end - start) + " ms ");
		}
		// String inputDate = "201406201800"; // ex = "201405201800"
		
	}
}
