package kr.ac.chungbuk.bigdata.weather.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
 * 어제 00시부터 23시 날씨데이터를 가져옴
 * 
 * @author HYUNA,Chihwan Choi(charisma0629@gmail.com)
 * 
 */

public class DailyScrapSimpleWeatherData {
	public void getData() throws FileNotFoundException, IOException, ParseException, InterruptedException {
		Properties prop = ConfigUtil.getXMLProperties("koreaweather4j.xml");
		// CLI APACHE PR OJECT
		Logger logger = LoggerFactory.getLogger(DailyScrapSimpleWeatherData.class);
		logger.info("{}", "starting application");
		logger.info("{}", "Initializing GeoLocationManager");
		GeoLocationManager.initialize();
		logger.info("{}", "Initializing GeoLocationManager...OK");

		// AWS Data를 받아올 날짜 입력 (YYYYMMDDHHMM). null = 현재.
		// 추가해야 할 사항 : 1.과거 데이터 수집 시, From~To 기능 추가 / 2.일정 간격으로 계속 수집하는 기능 추가
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH");
		long yesterday = System.currentTimeMillis() - 1000 * 60 * 60 * 24;

		Date date = new Date(yesterday);
		String dateString = sdf.format(date);
		// System.out.println(dateString);
		dateString = dateString.subSequence(0, 11) + "00.00.00";
		Date fromDate = sdf.parse(dateString);
		Date toDate = new Date((fromDate.getTime() + 1000 * 60 * 60 * 24));
		// System.out.println(sdf.format(fromDate));
		// System.out.println(sdf.format(toDate));

		List<Date> dateList = DateGenerator.doDateGenerateYYYYMMDDHH(sdf.format(fromDate), sdf.format(toDate), true);
		for (Date inputDate : dateList) {
			//
			HttpProtocolManager2 httpMgr = new HttpProtocolManager2();
			String httpRequest = httpMgr.getHttpURL(sdf.format(inputDate));
			String responseHtml = httpMgr.getWeatherData(httpRequest);
			List<KoreaWeatherSimpleModel> koreaWeatherDataList = httpMgr.getKoreaSimpleWeatherDataList(responseHtml);

			// db에 저장. 현재 MySQL로 저장 가능한 상태.
			// 추가해야 할 사항 : 1.Create, Update 기능 추가 / 2.MS_SQL 연동 추가
			DatabaseConnectionPoolManager2 database = new DatabaseConnectionPoolManager2(false,
					prop.getProperty("IPADDRESS"), prop.getProperty("PORT"), prop.getProperty("DATABASENAME"),
					prop.getProperty("USER"), prop.getProperty("PASSWORD"), prop.getProperty("TABLENAME"));
			long start = System.currentTimeMillis();
			database.persistSimpleWeatherData(koreaWeatherDataList);
			long end = System.currentTimeMillis();
			System.out.println("** Inserted Recoreds : " + koreaWeatherDataList.size() + " Working Time : "
					+ (end - start) + " ms ");

			try {
				Thread.sleep(3000); // 1000 milliseconds is one second.
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
		System.exit(0);
		// String inputDate = "201406201800"; // ex = "201405201800"
	}

	public static void main(String[] args) {
		MarioService.printMarioAsciiPictureR2();
		DailyScrapSimpleWeatherData crawler = new DailyScrapSimpleWeatherData();
		try {
			crawler.getData();
		} catch (IOException | ParseException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
