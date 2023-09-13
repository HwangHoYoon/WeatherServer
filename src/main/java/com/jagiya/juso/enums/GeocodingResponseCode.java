package com.jagiya.juso.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GeocodingResponseCode {


    NORMAL( "200", "정상", "", ""),
    UNAUTHORIZED( "gw", "401", "UNAUTHORIZED", "Unauthorized - 앱키가 없는 호출"),
    INVALID_API_KEY( "gw", "403", "INVALID_API_KEY", "Forbidden - 잘못된 앱키로 호출"),
    ACCESS_DENIED( "gw", "403", "ACCESS_DENIED", "Authorized IP Address only - Whitelist 차단"),
    MISSING_AUTHENTICATION_TOKEN( "gw", "403", "MISSING_AUTHENTICATION_TOKEN", "Missing Authentication Token - 존재하지 않는 API 나 Method 를 호출"),
    THROTTLED( "gw", "429", "THROTTLED", "Too Many Requests - 초당 호출 건수 초과"),
    QUOTA_EXCEEDED( "gw", "429", "QUOTA_EXCEEDED", "Limit Exceeded - SLA 초과"),
    API_CONFIGURATION_ERROR( "gw", "500", "API_CONFIGURATION_ERROR", "Internal server error - G/W 내부 에러"),
    INTEGRATION_FAILURE( "gw", "504", "INTEGRATION_FAILURE", "Network error communicating with endpoint - 백엔드 라우팅 에러"),

    NO_CONTENT( "tmap", "204", "NO_CONTENT", "요청 데이터에 오류에 의한 검색 결과가 없는 경우, 검색 결과 자체가 없는 경우, 리버스 지오코딩(Reverse Geocoding) 사용 시 잘못된 좌표 혹은 대한민국을 벗어난 좌표를 요청하는 경우"),
    BAD_REQUEST_DEFAULT( "tmap", "400", "1100", "요청 데이터 오류입니다. 파라미터, 숫자, 문자, 범위 등 데이터 형식, 지정된 데이터 형식, 경유지 개수 및 데이터 형식을 확인해주세요"),
    BAD_REQUEST_JSON( "tmap", "400", "1101", "요청 JSON 데이터 오류입니다."),
    BAD_REQUEST_COORDINATE( "tmap", "400", "1006", "잘못된 좌표 형식입니다. 좌표에 문자가 있는지 파라미터를 확인해 주세요."),
    BAD_REQUEST_COORDINATE_NOT_AVAILABLE( "tmap", "400", "1007", "사용할 수 없는 좌표계입니다. 사용 가능한 좌표계를 확인해 주세요."),
    BAD_REQUEST_COORDINATE_OUT_OF_RANGE( "tmap", "400", "1009", "입력 좌표 오류입니다. 입력된 좌표가 사용 가능한 최소, 최대 범위를 벗어났습니다. 입력 좌표의 X, Y가 반대로 입력되었습니다."),
    BAD_REQUEST_ADDRESS_NOT_PROVIDED( "tmap", "400", "2200", "제공되지 않는 주소 범위입니다."),
    BAD_REQUEST_INVALID_ADDRESS( "tmap", "400", "2300", "잘못된 주소 형식입니다."),
    BAD_REQUEST_INVALID_BUNJI( "tmap", "400", "2301", "잘못된 번지 주소 형식입니다."),
    BAD_REQUEST_SERVICE_NOT_SUPPORTED( "tmap", "400", "3002", "해당 서비스가 지원되지 않는 구간입니다."),
    BAD_REQUEST_REQUIRED_PARAMETER( "tmap", "400", "9401", "필수 파라미터가 없습니다."),

    INTERNAL_SERVER_ERROR( "tmap", "500", "1005", "시스템 오류입니다.");

    private final String category;

    private final String statusCode;

    private final String errorCode;

    private final String errorMessage;
}
