package kr.ac.chungbuk.bigdata.weather.model;

public class KoreaWeatherSimpleModel {

	// 관측시점 date 관측 시점(YYYYMMDDHHMM)
	// 지점 pointName 지점
	// 현재일기 condition 날씨 - 현재일기
	// 시정 sight 날씨 - 시정 km
	// 운량 cloud 날씨 - 운량 1/10
	// 중하운량 cloudMid 날씨 - 중하운량
	// 현재기온 temperature 기온 - 현재 기온(C)
	// 이슬점온도 dewPoint 기온 - 이슬점 온도(C)
	// 체감온도 sensoryTemp 기온 - 체감 온도(C)
	// 일강수 rain 강수 - 일강수(mm)
	// 적설 snow 강수 - 적설(cm)
	// 습도 humidity 강수 - 현재 습도(%)
	// 풍향 windDirection 바람 - 풍향(16방위)
	// 풍속 windSpeed 바람 - 풍속(m/s)
	// 해면기압 pressure 기압 - 해면기압(hPa)

	private String	date;
	private String	pointName;
	private String	condition;
	private String	sight;
	private String	cloud;
	private String	cloudMid;
	private String	temperature;


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getSight() {
		return sight;
	}

	public void setSight(String sight) {
		this.sight = sight;
	}

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public String getCloudMid() {
		return cloudMid;
	}

	public void setCloudMid(String cloudMid) {
		this.cloudMid = cloudMid;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
		
}
