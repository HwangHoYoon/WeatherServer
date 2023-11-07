package com.jagiya;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
class ApplicationTests {


	@Test
	void test() {
		String originalString = "16_6_BE_jagiya_secretKey_base64Encode_additional_data_20231024_hwang_jwt";
		byte[] bytesToEncode = originalString.getBytes();
		String encodedString = Base64.getEncoder().encodeToString(bytesToEncode);
		System.out.println("Base64 인코딩 결과: " + encodedString);

		// Base64 문자열을 디코딩
		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		System.out.println("Base64 디코딩 결과: " + decodedString);
	}

//	@Test
//	void contextLoads() {
//		//GpsTransfer gpsTransfer = new GpsTransfer(37.5732694444444, 126.970955555555);
//		//gpsTransfer.transfer(gpsTransfer, 0);
//		//System.out.println(gpsTransfer.toString());
//
//		List<Temp> list = testService.findByFld06("2");
//		for (Temp t : list) {
//			//if (StringUtils.isBlank(t.getFld04()) || StringUtils.isBlank(t.getFld03())) {
//		//		System.out.println(t.toString());
//	//		}
//			GpsTransfer gpsTransfer = new GpsTransfer(Double.parseDouble(t.getFld04()), Double.parseDouble(t.getFld03()));
//			gpsTransfer.transfer(gpsTransfer, 0);
//			t.setFld17(String.valueOf(gpsTransfer.getxLat()));
//			t.setFld18(String.valueOf(gpsTransfer.getyLon()));
//			testService.save(t);
//		}
//	}


//	@Test
//	void test2() throws Exception {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
//		String weatherUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
//		StringBuilder urlBuilder = new StringBuilder(weatherUrl); /*URL*/
//		urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="  + "F0MSmDjhkdQ1G0EGs/2omvk+AQkF7O/r6GSul3+mgBVmfyYEMNymmK4vUKXVG/Rhh7Wek5fKnJv2/rbQxeNSBQ=="); /*Service Key*/
//		urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//		urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
//		urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
//		urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode("20230903", "UTF-8")); /*‘21년 6월 28일발표*/
//		urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("0500", "UTF-8")); /*05시 발표*/
//		urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("60", "UTF-8")); /*예보지점의 X 좌표값*/
//		urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
//
//		String apiUrl = urlBuilder.toString();
//		System.out.println("apiUrl : " +  apiUrl);
//		RestTemplate restTemplate = new RestTemplate();
//		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
//
//		ResponseEntity<WeatherApiResponse> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, WeatherApiResponse.class);
//		List<WeatherItem> weatherItemList = response.getBody().getResponse().getBody().getItems().getItem();
//		List<Weather> weathers = groupDataByDateAndTime(weatherItemList);
//		if (weathers.size() > 0) {
//			//weathe.saveAll(weathers);
//		} else {
//			System.out.println("값이 없습니다.");
//		}
//	}

//	@Test
//	void test3() throws Exception {
//		weatherService.insertWeather();
//	}
//
//	@Test
//	void test4() throws Exception {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		String weatherUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
//		UriComponents uri = UriComponentsBuilder
//				.fromHttpUrl(weatherUrl)
//				.queryParam("serviceKey", "1F0MSmDjhkdQ1G0EGs%2F2omvk%2BAQkF7O%2Fr6GSul3%2BmgBVmfyYEMNymmK4vUKXVG%2FRhh7Wek5fKnJv2%2FrbQxeNSBQ%3D%3D")
//				.queryParam("pageNo", "1")
//				.queryParam("numOfRows", "1000")
//				.queryParam("dataType", "JSON")
//				.queryParam("base_date", "20230907")
//				.queryParam("base_time", "0500")
//				.queryParam("nx", "50")
//				.queryParam("ny", "125")
//				.build(true);
//
//		URI apiUrl = uri.toUri();
//		RestTemplate restTemplate = new RestTemplate();
//		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
//		try {
//			ResponseEntity<String> responseAsString = restTemplate.getForEntity(apiUrl, String.class);
//			if (responseAsString != null) {
//				try {
//					ObjectMapper objectMapper = new ObjectMapper();
//					WeatherApiResponse response = objectMapper.readValue(responseAsString.getBody(), WeatherApiResponse.class);
//					if (responseAsString.getStatusCode() == HttpStatus.OK) {
//						String resultCode = response.getResponse().getHeader().getResultCode();
//						if (resultCode.equals(WeatherResponseCode.NORMAL_SERVICE.getCode())) {
//							System.out.println(response);
//							System.out.println("성공");
//						} else {
//							System.out.println("API 통신 오류 : " + resultCode);
//						}
//					} else {
//						System.out.println("API 통신 결과 실패");
//					}
//				} catch (Exception e) {
//					WeatherErrorResponse weatherApiErrorResponse = xmlConvertToVo(responseAsString.getBody(), WeatherErrorResponse.class);
//					String returnReasonCode = weatherApiErrorResponse.getCmmMsgHeader().getReturnReasonCode();
//					System.out.println(WeatherResponseCode.getMessageByCode(returnReasonCode));
//					System.out.println("JSON 변환 실패 XML 변환");
//				}
//			} else {
//				System.out.println("API 통신 실패");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("API 통신 실패 및 변환실패");
//		}
//	}
//
//	@Test
//	void test5() throws Exception {
//		LocalDateTime dateTime = LocalDateTime.parse("20230910", DateTimeFormatter.ofPattern("yyyyMMdd"));
//		String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//		System.out.println(formattedTime);
//	}
//
//	private <T> T xmlConvertToVo(String xml, Class<T> voClass) throws JAXBException {
//		JAXBContext context = JAXBContext.newInstance(voClass);
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//
//		StringReader reader = new StringReader(xml);
//		return (T)unmarshaller.unmarshal(reader);
//	}


//	private List<Weather> groupDataByDateAndTime(List<WeatherItem> weatherItemList) {
//		Map<String, Weather> groupedData = new HashMap<>();
//
//		List<String> categoryList = new ArrayList<>();
//		categoryList.add("POP");
//		categoryList.add("PTY");
//		categoryList.add("PCP");
//		categoryList.add("SKY");
//		categoryList.add("TMP");
//		categoryList.add("TMN");
//		categoryList.add("TMX");
//		categoryList.add("T1H");
//		categoryList.add("RN1");
//
//		for (WeatherItem dataItem : weatherItemList) {
//			String dateAndTime = dataItem.getFcstDate() + dataItem.getFcstTime();
//
//			Weather weather = groupedData.getOrDefault(dateAndTime, new Weather());
//			String category = dataItem.getCategory();
//
//			boolean containsValue = categoryList.contains(category);
//
//			if (containsValue) {
//				weather.setBaseDate(dataItem.getBaseDate());
//				weather.setBaseTime(dataItem.getBaseTime());
//				weather.setFcstDate(dataItem.getFcstDate());
//				weather.setFcstTime(dataItem.getFcstTime());
//				weather.setLatX(dataItem.getNx());
//				weather.setLonY(dataItem.getNy());
//				weather.setRegDate(new Date());
//				String value = dataItem.getFcstValue();
//				setValue(weather, category.toLowerCase(), value);
//			}
//			groupedData.put(dateAndTime, weather);
//		}
//
//		List<Weather> arrayList = new ArrayList<>(groupedData.values());
//		return arrayList;
//	}

//	private void setValue(Object targetObject, String fieldName, String value) {
//		try {
//			String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//			Class<?> targetClass = targetObject.getClass();
//			Class<?> valueType = String.class;
//			targetClass.getMethod(setterName, valueType).invoke(targetObject, value);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	@Test
//	void test6() {
//		LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 40));
//		System.out.println("현재시간 : " +  localDateTime);
//		int minusMinute;
//		List<LocalTime> targetTimes = new ArrayList<>();
//			minusMinute = 15;
//			for (int i = 0; i<=23; i++) {
//				targetTimes.add(LocalTime.of(i, 45));
//			}
//
//		LocalTime currentTime = localDateTime.toLocalTime();
//
//		LocalTime closestTime = null;
//		for (LocalTime targetTime : targetTimes) {
//			if (currentTime.isAfter(targetTime)) {
//				closestTime = targetTime;
//			}
//		}
//
//		if (closestTime == null) {
//			localDateTime = localDateTime.minusDays(1);
//			closestTime = targetTimes.get(targetTimes.size() - 1);
//		}
//
//		closestTime = closestTime.minusMinutes(minusMinute);
//		localDateTime = localDateTime.withHour(closestTime.getHour()).withMinute(closestTime.getMinute());
//
//
//		System.out.println("기준시간 : " +  localDateTime);
//	}

}
