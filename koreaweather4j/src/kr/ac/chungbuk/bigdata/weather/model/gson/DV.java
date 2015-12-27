package kr.ac.chungbuk.bigdata.weather.model.gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;

import org.apache.http.client.ClientProtocolException;

public class DV {
	String					dataDate;
	String					type;
	List<LocationValues>	Location;

	public DV(List<KoreaWeatherAWSModel> list) throws ClientProtocolException, IOException {

		this.dataDate = list.get(0).getDate();
		this.type = "Forecast";
		this.Location = new ArrayList<LocationValues>();

		for (KoreaWeatherAWSModel m : list) {
			LocationValues value = new LocationValues(m.getLatitude(), m.getLongitude(), new Period(m.getWindDirection10(), String.valueOf(m.getWindSpeed10())));
			Location.add(value);
		}

	}

	class LocationValues {
		String	lat;
		String	lon;
		Period Period;

		public LocationValues(String lat, String lon, Period Period) {
			this.lat = lat;
			this.lon = lon;
			this.Period = Period;
		}
	}
}
