package kr.ac.chungbuk.bigdata.weather.model.gson;

public class Period {
	Rep	Rep;

	public Period() {

	}

	public Period(String D, String S) {
		this.Rep = new Rep(D, S);
	}
}
