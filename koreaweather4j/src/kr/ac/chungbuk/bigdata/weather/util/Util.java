package kr.ac.chungbuk.bigdata.weather.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;

public class Util {

	/**
	 * 날짜의 형식을 바꿔주는 메소드
	 */
	public static String convertDate2String(Date nowDate) throws ParseException {
		// SimpleDateFormat inputFormat = new
		// SimpleDateFormat("yyyy.MM.dd.HH:mm");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmm");
		// Date outputDate = inputFormat.parse(inputDateString);
		return outputFormat.format(nowDate);
	}

	/**
	 * 주소를 좌표로 변환하는 메소드
	 */
	public static final GeocoderResult convertAddress2Geocode(String address) throws IOException {
		Geocoder Geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("kr").getGeocoderRequest();
		GeocodeResponse geocoderResponse = Geocoder.geocode(geocoderRequest);
		List<GeocoderResult> resultList = geocoderResponse.getResults();
		// System.out.println(resultList.get(0).getGeometry().getLocation().);
		if (resultList.size() > 0)
			return resultList.get(0);
		else
			return null;
	}

}
