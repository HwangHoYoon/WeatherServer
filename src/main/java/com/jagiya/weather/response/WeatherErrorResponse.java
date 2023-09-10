package com.jagiya.weather.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="OpenAPI_ServiceResponse")
@Data
public class WeatherErrorResponse {

    @XmlElement(name = "cmmMsgHeader")
    private WeatherErrorHeader cmmMsgHeader;
}
