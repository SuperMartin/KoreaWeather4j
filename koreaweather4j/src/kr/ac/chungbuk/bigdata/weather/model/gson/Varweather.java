package kr.ac.chungbuk.bigdata.weather.model.gson;

import java.io.IOException;
import java.util.List;

import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;

import org.apache.http.client.ClientProtocolException;

public class Varweather {
	SiteRep	SiteRep;

	public Varweather(List<KoreaWeatherAWSModel> list) throws ClientProtocolException, IOException {
		SiteRep = new SiteRep(list);
	}

}
