package kr.ac.chungbuk.bigdata.weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * 날짜 생성기
 * 
 * @author HYUNA { wj35hj88@naver.com }
 * 
 * @since 20141114
 */
public class DateGenerator {

	private static final String DATEFORMAT = "yyyyMMddHHmm";
	private static final String DATEFORMAT2 = "yyyy.MM.dd.HH";

	/**
	 * 시작일과 끝일을 분단위또는 시간 단위로 DATE객체를 만듦
	 * isHour == true then hourly, otherwise minutes
	 * @format yyyyMMddHHmmss
	 * @param fromDateString
	 * @param toDateString
	 * @return List<Date>
	 * @throws ParseException
	 */
	public static List<Date> doDateGenerateYYYYMMDDHHMI(String fromDateString, String toDateString, boolean isHour) throws ParseException {
		List<Date> dateList = new ArrayList<Date>();
		SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(DATEFORMAT);

		Date toDate = simpleDateFormatter.parse(toDateString);
		Date fromDate = simpleDateFormatter.parse(fromDateString);
		long ms = fromDate.getTime();
		while (true) {
			Date date = new Date(ms);
//			ms = ms + (1000 * 60 * 60);
			ms = isHour ? ms + (1000 * 60 * 60) : ms + (1000 * 60);
			dateList.add(date);
			System.out.println(simpleDateFormatter.format(date));

			if (ms == toDate.getTime())
				break;
		}
		return dateList;
	}
	/**
	 * 시작일과 끝일을 분단위또는 시간 단위로 DATE객체를 만듦
	 * isHour == true then hourly, otherwise minutes
	 * @format yyyyMMddHHmmss
	 * @param fromDateString
	 * @param toDateString
	 * @return List<Date>
	 * @throws ParseException
	 */
	public static List<Date> doDateGenerateYYYYMMDDHH(String fromDateString, String toDateString, boolean isHour) throws ParseException {
		List<Date> dateList = new ArrayList<Date>();
		SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(DATEFORMAT2);

		Date toDate = simpleDateFormatter.parse(toDateString);
		Date fromDate = simpleDateFormatter.parse(fromDateString);
		long ms = fromDate.getTime();
		while (true) {
			Date date = new Date(ms);
//			ms = ms + (1000 * 60 * 60);
			ms = isHour ? ms + (1000 * 60 * 60) : ms + (1000 * 60);
			dateList.add(date);
//			System.out.println(simpleDateFormatter.format(date));

			if (ms == toDate.getTime())
				break;
		}
		return dateList;
	}

	/**
	 * 날짜 생성하는 메소드
	 * 
	 * @throws ParseException
	 * 
	 */
	@Test
	public void doDateGenerate() throws ParseException {

		// 최소단위 : 시간단위로 생성
		String fromDate = "201201010001";
		String toDate = "201412140001";
		List<Date> dateList = new ArrayList<Date>();

		// Calendar c = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
		Date maxDate2 = sdf.parse(toDate);
		Date minDate2 = sdf.parse(fromDate);
		long ms = minDate2.getTime();
		while (true) {
			Date date = new Date(ms);
			ms = ms + (1000 * 1);
			dateList.add(date);
			System.out.println(sdf.format(date));

			if (ms == maxDate2.getTime())
				break;
		}

		System.out.println("sdfsdf" + dateList.size());
	}
}
