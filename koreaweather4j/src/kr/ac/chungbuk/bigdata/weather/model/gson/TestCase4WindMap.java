package kr.ac.chungbuk.bigdata.weather.model.gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import kr.ac.chungbuk.bigdata.weather.model.KoreaWeatherAWSModel;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class TestCase4WindMap {

	private Logger	logger	= LoggerFactory.getLogger(getClass());
	private String	fileLocation;

	public void makeWindMapGSonFile(List<KoreaWeatherAWSModel> resultDataList) throws ClientProtocolException, IOException {
		Gson gson = new Gson();
		// this.finalDataList = resultDataList;
		Varweather resultList = new Varweather(resultDataList);

		String json = gson.toJson(resultList);
		String string4JSPrefix = "var weather = ";
		String string4JSSuffix = ";";
		logger.debug("{}","JSON: " + string4JSPrefix + json + string4JSSuffix);
		logger.info("{}","Saving to : " + fileLocation);
		File file = new File(fileLocation);

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(string4JSPrefix + json + string4JSSuffix);
		bw.flush();
		bw.close();
		fw.close();

	}

	@Deprecated
	public List<KoreaWeatherAWSModel> getDataList() {
		return null;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}
