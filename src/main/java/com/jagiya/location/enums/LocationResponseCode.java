package com.jagiya.location.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum LocationResponseCode {

    NORMAL( "0", "정상", ""),
    SYSTEM_ERROR( "-999", "시스템에러", "도로명주소 도움센터로 문의하시기 바랍니다."),
    KEY_ERROR( "E0001", "승인되지 않은 KEY 입니다.", "정확한 승인키를 입력하세요.(팝업API 승인키 사용불가)"),
    KEYWORD_NULL_ERROR( "E0005", "검색어가 입력되지 않았습니다.", "검색어를 입력해주세요"),
    DETAIL_ERROR( "E0006", "주소를 상세히 입력해 주시기 바랍니다.", "시도명으로는 검색이 불가합니다."),
    KEYWORD_MIN_ERROR( "E0008", "검색어는 두글자 이상 입력되어야 합니다.", "한 글자만으로는 검색이 불가합니다."),
    KEYWORD_MAX_ERROR( "E0009", "검색어가 너무 깁니다. (한글40자, 영문,숫자 80자 이하)", "80글자를 초과한 검색어는 검색이 불가합니다."),
    KEYWORD_MAX_NUMBER_ERROR( "E0011", "검색어에 너무 긴 숫자가 포함되어 있습니다. (숫자10자 이하)", "10자리를 초과하는 숫자가 포함된 검색어는 검색이 불가합니다."),
    KEYWORD_NOT_STRING_ERROR( "E0012", "특수문자+숫자만으로는 검색이 불가능 합니다.", "특수문자와 숫자만으로 이루어진 검색어는 검색이 불가합니다."),
    KEYWORD_SQL_ERROR( "E0013", "SQL 예약어 또는 특수문자( %,=,>,<,[,] )는 검색이 불가능 합니다.", "SQL예약어 또는 특수문자를 제거 후 검색합니다."),
    KEY_EXPIRATION_ERROR( "E0014", "개발승인키 기간이 만료되어 서비스를 이용하실 수 없습니다.", "개발승인키를 다시 발급받아 API서비스를 호출합니다."),
    MAX_RESULT_ERROR( "E0015", "검색 범위를 초과하였습니다.", "검색결과가 9천건이 초과하는 검색은 불가합니다.");

    private final String code;

    private final String message;

    private final String solution;

    public static LocationResponseCode getLocationResponseCode(String code) {
        for (LocationResponseCode jusoCode : LocationResponseCode.values()) {
            if (jusoCode.getCode().equals(code)) {
                return jusoCode;
            }
        }
        return null;
    }

    public static boolean getRetryCode(String code) {
        List<LocationResponseCode> locationCodeList = new ArrayList<>();
        for (LocationResponseCode locationCode : locationCodeList) {
            if (locationCode.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
