package kr.ac.chungbuk.bigdata.weather.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherSimpleModel;

/**
 * 기상청의 AWS 데이터를 가져오는 클래스
 */
public class HttpProtocolManager2 {

	// 1. Line 153의 Exception 변경하기.

	Logger logger = LoggerFactory.getLogger(HttpProtocolManager2.class);
	private String requestDatetime;
	// private String httpFront =
	// "http://www.kma.go.kr/weather/observation/currentweather.jsp?tm=";
	private String httpFront = "http://www.kma.go.kr/weather/observation/currentweather.jsp?auto_man=m&type=t99&tm=";

	private String httpEnd = "%3A00&x=26&y=6";

	public HttpProtocolManager2() {
		initialize();
	}

	private void initialize() {
	}

	/**
	 * 날짜를 입력받아 URL을 완성시켜주는 메소드
	 */
	public String getHttpURL(String inputDate) throws ParseException {

		String requestDatetime = null;

		requestDatetime = inputDate;

		String resultHttp = httpFront + requestDatetime + httpEnd;
		this.requestDatetime = requestDatetime;
		System.out.println(resultHttp);
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
	 * 2015.11.16 수정
	 */
	public List<KoreaWeatherSimpleModel> getKoreaSimpleWeatherDataList(String resultHtml)
			throws FileNotFoundException, IOException, InterruptedException {

		Document doc = Jsoup.parse(resultHtml);
		List<KoreaWeatherSimpleModel> resultDataList = new ArrayList<KoreaWeatherSimpleModel>();
		Elements dateInfo = doc.getElementsByClass("table_topinfo");
		String date = dateInfo.text();
		Elements el = doc.getElementsByClass("table_develop3").select("tbody").select("tr");

		for (org.jsoup.nodes.Element e : el) {
			KoreaWeatherSimpleModel m = new KoreaWeatherSimpleModel();

			// 정보 추가
			Iterator<Element> ite = e.select("td").iterator();

			m.setDate(date);
			try {
				m.setPointName(ite.next().text());
				m.setCondition(ite.next().text());
				m.setSight(ite.next().text());
				m.setCloud(ite.next().text());
				m.setCloudMid(ite.next().text());
				m.setTemperature(ite.next().text());
				resultDataList.add(m);

			} catch (Exception e2) {
				continue;
			}

//			System.out.println(m.getDate() + ", " + m.getPointName() + ", " + m.getCondition() + ", " + m.getSight()
//					+ ", " + m.getCloud() + ", " + m.getCloudMid() + ", " + m.getTemperature());
		}

		logger.debug("{}", "** Korea Weather AWS Records : " + resultDataList.size());
		logger.debug("{}", "** Forcast Date (yyyyMMddHHmm) : " + getRequestDatetime());
		logger.debug("{}", "------------------------------------");

		return resultDataList;

	}

	/**
	 * 2015.11.16 변경건 처리 각각의 값으로 분리하여 model로 정의하는 메소드
	 */
	@Test
	public void testGetKoreaWeatherDataList() throws FileNotFoundException, IOException, InterruptedException {
		File file = new File("sample.html");
		String resultHtml = new String(Files.readAllBytes(file.toPath()),"EUC-KR");

		Document doc = Jsoup.parse(resultHtml);
		List<KoreaWeatherSimpleModel> resultDataList = new ArrayList<KoreaWeatherSimpleModel>();
		Elements dateInfo = doc.getElementsByClass("table_topinfo");
		String date = dateInfo.text();
		Elements el = doc.getElementsByClass("table_develop3").select("tbody").select("tr");

		for (org.jsoup.nodes.Element e : el) {
			KoreaWeatherSimpleModel m = new KoreaWeatherSimpleModel();

			// 정보 추가
			Iterator<Element> ite = e.select("td").iterator();

			m.setDate(date);
			try {
				m.setPointName(ite.next().text());
				m.setCondition(ite.next().text());
				m.setSight(ite.next().text());
				m.setCloud(ite.next().text());
				m.setCloudMid(ite.next().text());
				m.setTemperature(ite.next().text());
				resultDataList.add(m);

			} catch (Exception e2) {
				continue;
			}

			System.out.println(m.getDate() + ", " + m.getPointName() + ", " + m.getCondition() + ", " + m.getSight()
					+ ", " + m.getCloud() + ", " + m.getCloudMid() + ", " + m.getTemperature());
		}

		logger.debug("{}", "** Korea Weather AWS Records : " + resultDataList.size());
		logger.debug("{}", "** Forcast Date (yyyyMMddHHmm) : " + getRequestDatetime());
		logger.debug("{}", "------------------------------------");

		// return resultDataList;

	}

}
