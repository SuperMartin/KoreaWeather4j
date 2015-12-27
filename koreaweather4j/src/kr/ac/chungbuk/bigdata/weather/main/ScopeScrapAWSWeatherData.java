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

/** 시작일과 종료일을 설정하여 날씨데이터를 가져옴 
 * @author HYUNA
 *
 */
public class ScopeScrapAWSWeatherData {
	public static void main(String[] args) throws Throwable {

		// CLI APACHE PROJECT
		Logger logger = LoggerFactory.getLogger(ScopeScrapAWSWeatherData.class);
		logger.info("{}", "starting application");
		logger.info("{}", "Initializing GeoLocationManager");
		GeoLocationManager.initialize();
		logger.info("{}", "Initializing GeoLocationManager...OK");

		// AWS Data를 받아올 날짜 입력 (YYYYMMDDHHMM). null = 현재.
		// 추가해야 할 사항 : 1.과거 데이터 수집 시, From~To 기능 추가 / 2.일정 간격으로 계속 수집하는 기능 추가
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		List<Date> dateList = DateGenerator.doDateGenerateYYYYMMDDHHMI("201101010000", "2014011140000", true);
		for (Date inputDate : dateList) {

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
