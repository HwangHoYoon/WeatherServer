package com.jagiya.weather.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="cmmMsgHeader")
@Data
public class WeatherErrorHeader {

    @XmlElement(name= "errMsg")
    private String errMsg;

    @XmlElement(name= "returnAuthMsg")
    private String returnAuthMsg;
    @XmlElement(name= "returnReasonCode")
    private String returnReasonCode;
}
