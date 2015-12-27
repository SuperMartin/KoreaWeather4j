package kr.ac.chungbuk.bigdata.weather.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * @author HYUNA
 * @reference http://www.mkyong.com/java/how-to-check-if-date-is-valid-in-java/
 * @deprecated
 */
public class DateValidator {
	@Deprecated
	public boolean isThisDateValid(String dateToValidate, String dateFromat) {

		if (dateToValidate == null) {
			return false;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {

			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Test
	public void isThisDateValid() {
		String dateToValidate;
		String dateFromat;
		dateToValidate = "201402282300";
		dateFromat = "yyyyMMddHHmm";
		if (dateToValidate == null) {
			System.out.println("ERROR");
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);

		try {

			// if not valid, it will throw ParseException
			Date date = sdf.parse(dateToValidate);
			System.out.println(date);

		} catch (ParseException e) {

			e.printStackTrace();
			System.out.println("없는 날짜임.");
			// return false;
		}

		// return true;
	}
}
