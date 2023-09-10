package com.jagiya;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagiya.common.enums.WeatherCode;
import com.jagiya.weather.request.GpsTransfer;
import com.jagiya.main.entity.Temp;
import com.jagiya.weather.entity.Weather;
import com.jagiya.main.service.Impl.TestService;
import com.jagiya.weather.response.WeatherApiResponse;
import com.jagiya.weather.response.WeatherErrorResponse;
import com.jagiya.weather.response.WeatherItem;
import com.jagiya.weather.service.WeatherService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private TestService testService;



	@Autowired
	private WeatherService weatherService;


	@Test
	void contextLoads() {
		//GpsTransfer gpsTransfer = new GpsTransfer(37.5732694444444, 126.970955555555);
		//gpsTransfer.transfer(gpsTransfer, 0);
		//System.out.println(gpsTransfer.toString());

		List<Temp> list = testService.findByFld06("2");
		for (Temp t : list) {
			//if (StringUtils.isBlank(t.getFld04()) || StringUtils.isBlank(t.getFld03())) {
		//		System.out.println(t.toString());
	//		}
			GpsTransfer gpsTransfer = new GpsTransfer(Double.parseDouble(t.getFld04()), Double.parseDouble(t.getFld03()));
			gpsTransfer.transfer(gpsTransfer, 0);
			t.setFld17(String.valueOf(gpsTransfer.getxLat()));
			t.setFld18(String.valueOf(gpsTransfer.getyLon()));
			testService.save(t);
		}
	}

	@Test
	void test2() throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		String weatherUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
		StringBuilder urlBuilder = new StringBuilder(weatherUrl); /*URL*/
		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="  + "F0MSmDjhkdQ1G0EGs/2omvk+AQkF7O/r6GSul3+mgBVmfyYEMNymmK4vUKXVG/Rhh7Wek5fKnJv2/rbQxeNSBQ=="); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
		urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
		urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20230903", "UTF-8")); /*‘21년 6월 28일발표*/
		urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8")); /*05시 발표*/
		urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("60", "UTF-8")); /*예보지점의 X 좌표값*/
		urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/

		String apiUrl = urlBuilder.toString();
		System.out.println("apiUrl : " +  apiUrl);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

		ResponseEntity<WeatherApiResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, WeatherApiResponse.class);
		List<WeatherItem> weatherItemList = response.getBody().getResponse().getBody().getItems().getItem();
		List<Weather> weathers = groupDataByDateAndTime(weatherItemList);
		if (weathers.size() > 0) {
			//weathe.saveAll(weathers);
		} else {
			System.out.println("값이 없습니다.");
		}
	}

	@Test
	void test3() throws Exception {
		weatherService.insertWeatherWithCode();
	}

	@Test
	void test4() throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		String weatherUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
		UriComponents uri = UriComponentsBuilder
				.fromHttpUrl(weatherUrl)
				.queryParam("serviceKey", "1F0MSmDjhkdQ1G0EGs%2F2omvk%2BAQkF7O%2Fr6GSul3%2BmgBVmfyYEMNymmK4vUKXVG%2FRhh7Wek5fKnJv2%2FrbQxeNSBQ%3D%3D")
				.queryParam("pageNo", "1")
				.queryParam("numOfRows", "1000")
				.queryParam("dataType", "JSON")
				.queryParam("base_date", "20230907")
				.queryParam("base_time", "0500")
				.queryParam("nx", "50")
				.queryParam("ny", "125")
				.build(true);

		URI apiUrl = uri.toUri();
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		try {
			ResponseEntity<String> responseAsString = restTemplate.getForEntity(apiUrl, String.class);
			if (responseAsString != null) {
				try {
					ObjectMapper objectMapper = new ObjectMapper();
					WeatherApiResponse response = objectMapper.readValue(responseAsString.getBody(), WeatherApiResponse.class);
					if (responseAsString.getStatusCode() == HttpStatus.OK) {
						String resultCode = response.getResponse().getHeader().getResultCode();
						if (resultCode.equals(WeatherCode.NORMAL_SERVICE.getCode())) {
							System.out.println(response);
							System.out.println("성공");
						} else {
							System.out.println("API 통신 오류 : " + resultCode);
						}
					} else {
						System.out.println("API 통신 결과 실패");
					}
				} catch (Exception e) {
					WeatherErrorResponse weatherApiErrorResponse = xmlConvertToVo(responseAsString.getBody(), WeatherErrorResponse.class);
					String returnReasonCode = weatherApiErrorResponse.getCmmMsgHeader().getReturnReasonCode();
					System.out.println(WeatherCode.getMessageByCode(returnReasonCode));
					System.out.println("JSON 변환 실패 XML 변환");
				}
			} else {
				System.out.println("API 통신 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("API 통신 실패 및 변환실패");
		}
	}

	private <T> T xmlConvertToVo(String xml, Class<T> voClass) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(voClass);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		StringReader reader = new StringReader(xml);
		return (T)unmarshaller.unmarshal(reader);
	}


	private List<Weather> groupDataByDateAndTime(List<WeatherItem> weatherItemList) {
		Map<String, Weather> groupedData = new HashMap<>();

		List<String> categoryList = new ArrayList<>();
		categoryList.add("POP");
		categoryList.add("PTY");
		categoryList.add("PCP");
		categoryList.add("SKY");
		categoryList.add("TMP");
		categoryList.add("TMN");
		categoryList.add("TMX");
		categoryList.add("T1H");
		categoryList.add("RN1");

		for (WeatherItem dataItem : weatherItemList) {
			String dateAndTime = dataItem.getFcstDate() + dataItem.getFcstTime();

			Weather weather = groupedData.getOrDefault(dateAndTime, new Weather());
			String category = dataItem.getCategory();

			boolean containsValue = categoryList.contains(category);

			if (containsValue) {
				weather.setBaseDate(dataItem.getBaseDate());
				weather.setBaseTime(dataItem.getBaseTime());
				weather.setFcstDate(dataItem.getFcstDate());
				weather.setFcstTime(dataItem.getFcstTime());
				weather.setLatX(dataItem.getNx());
				weather.setLonY(dataItem.getNy());
				weather.setRegDate(new Date());
				String value = dataItem.getFcstValue();
				setValue(weather, category.toLowerCase(), value);
			}
			groupedData.put(dateAndTime, weather);
		}

		List<Weather> arrayList = new ArrayList<>(groupedData.values());
		return arrayList;
	}

	private void setValue(Object targetObject, String fieldName, String value) {
		try {
			String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Class<?> targetClass = targetObject.getClass();
			Class<?> valueType = String.class;
			targetClass.getMethod(setterName, valueType).invoke(targetObject, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
