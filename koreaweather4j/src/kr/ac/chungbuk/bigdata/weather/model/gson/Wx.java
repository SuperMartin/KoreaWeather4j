package kr.ac.chungbuk.bigdata.weather.model.gson;

import java.util.ArrayList;
import java.util.List;

public class Wx {

	List<ParamValues>	Param;

	// 시각화 하는 정보들을 나타내줌.
	public Wx() {
		Param = new ArrayList<ParamValues>();
		Param.add(new ParamValues("F", "C", "Feels Like Temperature"));
		Param.add(new ParamValues("G", "mph", "Wind Gust"));
		Param.add(new ParamValues("H", "%", "Screen Relative Humidity"));
		Param.add(new ParamValues("T", "C", "Temperature"));
		Param.add(new ParamValues("V", "", "Visibility"));
		Param.add(new ParamValues("D", "compass", "Wind Direction"));
		Param.add(new ParamValues("S", "mph", "Wind Speed"));
		Param.add(new ParamValues("U", "", "Max UV Index"));
		Param.add(new ParamValues("W", "", "Weather Type"));
		Param.add(new ParamValues("Pp", "%", "Precipitation Probability"));
	}
}


class ParamValues {
	String	name;
	String	units;
	String	$;


	public ParamValues(String name, String units, String $) {
		this.name = name;
		this.units = units;
		this.$ = $;
	}

}