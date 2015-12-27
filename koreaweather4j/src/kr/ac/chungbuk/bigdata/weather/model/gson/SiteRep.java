package kr.ac.chungbuk.bigdata.weather.model.gson;

import java.io.IOException;
import java.util.List;

import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;

import org.apache.http.client.ClientProtocolException;

public class SiteRep {
	Wx	Wx;
	DV	DV;

	public SiteRep(List<KoreaWeatherAWSModel> list) throws ClientProtocolException, IOException {
		Wx = new Wx();
		DV = new DV(list);
	}
}
