package com.jagiya.alarm.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@AllArgsConstructor
public enum TimeOfDay {

    MORINING(0, "AM","오전"),
    AFTERNOON(1, "PM", "오후"),
    ALLDAY(1, "ALL", "종일");

    private Integer code;

    private String engName;

    private String name;

    private static final TimeOfDay[] VALUES;

    static {
        VALUES = values();
    }

    public static String getName(Integer code) {
        for (TimeOfDay status : VALUES) {
            if (status.code == code) {
                return status.getName();
            }
        }
        return "";
    }

    public static String getEngName(Integer code) {
        for (TimeOfDay status : VALUES) {
            if (status.code == code) {
                return status.getEngName();
            }
        }
        return "";
    }

}
