package kr.ac.chungbuk.bigdata.weather.model;

public class KoreaWeatherModel {

	//ID			id					기상청에서 관리하는 AWS 식별 번호
	//관측시점		date				관측 시점(YYYYMMDDHHMM)
	//지점			pointName			관측 장치가 있는 지점의 이름
	//위도			latitude			관측 장치가 있는 위치의 위도
	//경도			longitude			관측 장치가 있는 위치의 경도
	//고도			altitude			관측 장치가 있는 위치의 고도
	//강수			rain				강수량과 별도로 준비된 강수감시센서에서 관측한 강수 유무
	//강수15		rain15				자료시간에서 과거 15분간 내린 강수의 양(mm)
	//강수60		rain60				자료시간에서 과거 60분간 내린 강수의 양(mm)
	//강수6H		rain6h				자료시간에서 과거 6시간 내린 강수의 양(mm)
	//강수12H		rain12h				자료시간에서 과거 12시간 내린 강수의 양(mm)
	//일강수		rain24h				오늘 00시 00분부터 자료시간까지 내린 강수의 양(mm)
	//기온			temperature			현재 기온(C)
	//풍향1			windDirection1		1분풍향(degree)
	//풍향1c		windDirection1c		1분풍향(degree, 16방위)
	//풍속1			windSpeed1			1분평균 풍속(m/s)
	//풍향10		windDirection10		10분평균 풍향(degree)
	//풍향10c		windDirection10c	10분평균 풍향(degree, 16방위)
	//풍속10		windSpeed10			10분평균 풍속(m/s)
	//습도			humidity			현재 습도(%)
	//해면기압		pressure			현재 해면기압(hPa), * 습도와 해면기압은 유인기상관서에서만 관측됨.
	//위치			pointAddress		관측 장치가 있는 위치
	
	
	private String id;
	private String date;
	private String pointName;
	private String latitude;
	private String longitude;
	private String altitude;
	private String rain;
	private String rain15;
	private String rain60;
	private String rain6h;
	private String rain12h;
	private String rain24h;
	private String temperature;
	private String windDirection1;
	private String windDirection1c;
	private String windSpeed1;
	private String windDirection10;
	private String windDirection10c;
	private String windSpeed10;
	private String humidity;
	private String pressure;
	private String pointAddress;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getDate(){
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getLatitude(){
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude(){
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getRain() {
		return rain;
	}

	public void setRain(String rain) {
		this.rain = rain;
	}

	public String getRain15() {
		return rain15;
	}

	public void setRain15(String rain15) {
		this.rain15 = rain15;
	}

	public String getRain60() {
		return rain60;
	}

	public void setRain60(String rain60) {
		this.rain60 = rain60;
	}

	public String getRain6h() {
		return rain6h;
	}

	public void setRain6h(String rain6h) {
		this.rain6h = rain6h;
	}

	public String getRain12h() {
		return rain12h;
	}

	public void setRain12h(String rain12h) {
		this.rain12h = rain12h;
	}

	public String getRain24h() {
		return rain24h;
	}

	public void setRain24h(String rain24h) {
		this.rain24h = rain24h;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWindDirection1() {
		return windDirection1;
	}

	public void setWindDirection1(String windDirection1) {
		this.windDirection1 = windDirection1;
	}

	public String getWindDirection1c() {
		return windDirection1c;
	}

	public void setWindDirection1c(String windDirection1c) {
		this.windDirection1c = windDirection1c;
	}

	public String getWindSpeed1() {
		return windSpeed1;
	}

	public void setWindSpeed1(String windSpeed1) {
		this.windSpeed1 = windSpeed1;
	}

	public String getWindDirection10() {
		return windDirection10;
	}

	public void setWindDirection10(String windDirection10) {
		this.windDirection10 = windDirection10;
	}

	public String getWindDirection10c() {
		return windDirection10c;
	}

	public void setWindDirection10c(String windDirection10c) {
		this.windDirection10c = windDirection10c;
	}

	public String getWindSpeed10() {
		return windSpeed10;
	}

	public void setWindSpeed10(String windSpeed10) {
		this.windSpeed10 = windSpeed10;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getPointAddress() {
		return pointAddress;
	}

	public void setPointAddress(String pointAddress) {
		this.pointAddress = pointAddress;
	}
		
}
