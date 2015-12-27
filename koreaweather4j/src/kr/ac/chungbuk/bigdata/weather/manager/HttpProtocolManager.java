package kr.ac.chungbuk.bigdata.weather.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;
import kr.ac.chungbuk.bigdata.weather.util.GeoLocationManager;
import kr.ac.chungbuk.bigdata.weather.util.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

/**
 * 기상청의 AWS 데이터를 가져오는 클래스
 */
public class HttpProtocolManager {

	// 1. Line 153의 Exception 변경하기.

	Logger			logger		= LoggerFactory.getLogger(HttpProtocolManager.class);
	private String	requestDatetime;
	private String	httpFront	= "http://www.kma.go.kr/cgi-bin/aws/nph-aws_txt_min?";
//	private String	httpEnd		= "&0&MINDB_01M&0&a";
	private String	httpEnd		= "0&MINDB_01M&4108&m";

	public HttpProtocolManager() {
		initialize();
	}

	private void initialize() {
	}

	/**
	 * 날짜를 입력받아 URL을 완성시켜주는 메소드
	 */
	public String getHttpURL(String inputDate) throws ParseException {

		String requestDatetime = null;

		if (inputDate != null) {
			requestDatetime = inputDate;
		} else {
			Calendar time = Calendar.getInstance();
			time.add(Calendar.MINUTE, -2);
			Date nowDate = time.getTime();
			requestDatetime = Util.convertDate2String(nowDate);
		}

		String resultHttp = httpFront + requestDatetime + httpEnd;
		this.requestDatetime = requestDatetime;
		return resultHttp;
	}

	public String getRequestDatetime() {
		return this.requestDatetime;
	}

	/**
	 * Html형식으로 가져오는 메소드
	 */
	public final String getWeatherData(String request) throws ClientProtocolException, IOException {

		String weatherDataHtml = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

			HttpGet httpget = new HttpGet(request);
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity, "EUC-KR") : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			weatherDataHtml = responseBody;

		} finally {
			httpclient.close();
		}
		return weatherDataHtml;
	}

	/**
	 * 각각의 값으로 분리하여 model로 정의하는 메소드
	 */
	public List<KoreaWeatherAWSModel> getKoreaAWSWeatherDataList(String resultHtml) throws FileNotFoundException, IOException, InterruptedException {

		Document doc = Jsoup.parse(resultHtml);
		List<KoreaWeatherAWSModel> resultDataList = new ArrayList<KoreaWeatherAWSModel>();
		Elements el = doc.select("table td tr[class=text]");

		for (org.jsoup.nodes.Element e : el) {
			KoreaWeatherAWSModel m = new KoreaWeatherAWSModel();

			// AWS 정보 추가
			Iterator<Element> ite = e.select("td").iterator();
			m.setId(ite.next().text());
			m.setPointName(ite.next().text());
			m.setAltitude(ite.next().text());
			m.setRain(ite.next().text());
			m.setRain15(ite.next().text());
			m.setRain60(ite.next().text());
			m.setRain6h(ite.next().text());
			m.setRain12h(ite.next().text());
			m.setRain24h(ite.next().text());
			m.setTemperature(ite.next().text());
			m.setWindDirection1(ite.next().text());
			m.setWindDirection1c(ite.next().text());
			m.setWindSpeed1(ite.next().text());
			m.setWindDirection10(ite.next().text());
			m.setWindDirection10c(ite.next().text());
			m.setWindSpeed10(ite.next().text());
			m.setHumidity(ite.next().text());
			m.setPressure(ite.next().text());
			m.setPointAddress(ite.next().text());

			// date 추가
			m.setDate(new String(getRequestDatetime()));
			// System.out.println(getRequestDatetime());

			// latitude, longitude 추가
			String resultLatLng = GeoLocationManager.getLatLng(m.getId());
			logger.debug("{}", resultLatLng);
			if (resultLatLng == null) {

				GeocoderResult result = Util.convertAddress2Geocode(m.getPointAddress());
				/*
				 * EXCEPTION --> exception_id_address.meta ,
				 * exception_id_lat_lng.meta 만들어서 예외처리
				 */
				if (result == null) {
					if (m.getPointAddress().contentEquals("세종특별자치시 연기면 세종리")) {
						result = new GeocoderResult();
						GeocoderGeometry geo = new GeocoderGeometry();
						LatLng latlng = new LatLng("36.592881", "127.292327");
						geo.setLocation(latlng);
						result.setGeometry(geo);
					}
				}

				if (!GeoLocationManager.checkIDExists(m.getId())) {
					logger.info("{}", m.getId() + ", " + m.getPointAddress() + " 주소가 없습니다. 주소와 위도경도 값을 새롭게 추가합니다. ");
					GeoLocationManager.putAddress(m.getId(), m.getPointAddress());
					GeoLocationManager.putLatLng(m.getId(), result.getGeometry().getLocation().getLat() + "\t" + result.getGeometry().getLocation().getLng());
				}
				resultLatLng = GeoLocationManager.getLatLng(m.getId());
				Thread.sleep(200l);
			}

			String[] value = resultLatLng.split("\t");
			m.setLatitude(value[0]);
			m.setLongitude(value[1]);

			resultDataList.add(m);

			// System.out.println(m.getDate() + ", " + m.getId() + ", "
			// + m.getPointName() + ", " + m.getLatitude() + ", "
			// + m.getLongitude() + ", " + m.getPointAddress());
		}

		logger.debug("{}", "** Korea Weather AWS Records : " + resultDataList.size());
		logger.debug("{}", "** Forcast Date (yyyyMMddHHmm) : " + getRequestDatetime());
		logger.debug("{}", "------------------------------------");

		return resultDataList;

	}

}
