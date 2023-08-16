package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "Device")
@Table(name = "Device")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "디바이스 VO")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceId")
    @Schema(description = "deviceId Id")
    private Integer deviceId;


    /* 유저 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    @Schema(description = "유저 식별자")
    private Users usersTb;



    @Column(name = "deviceName")
    @Schema(description = "디바이스 name")
    private String deviceName;


    @Column(name = "deviceCode")
    @Schema(description = "디바이스 code")
    private String deviceCode;


    @Column(name = "authAlert")
    @Schema(description = "알림권한")
    private Integer authAlert;


    @Column(name = "authLocation")
    @Schema(description = "위치권한")
    private Integer authLocation;



    @Column(name = "authStart")
    @Schema(description = "자동시작권한")
    private Integer authStart;



    @Column(name = "authShow")
    @Schema(description = "다른앱위에 표시권한")
    private Integer authShow;


    @Column(name = "authAlerDate")
    @Schema(description = "알림권한 동의일시")
    private Date authAlerDate;

    @Column(name = "authLocationDate")
    @Schema(description = "위치권한 동의일시")
    private Date authLocationDate;


    @Column(name = "authStartDate")
    @Schema(description = "자동시작권한 동의일시")
    private Date authStartDate;

    @Column(name = "authShowDate")
    @Schema(description = "다른앱위에 표시권한 동의일시")
    private Date authShowDate;



}
