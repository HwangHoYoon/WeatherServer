package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceId")
    private Integer deviceId;


    /* 유저 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    private Users usersTb;



    @Column(name = "deviceName")
    private String deviceName;


    @Column(name = "deviceCode")
    private String deviceCode;


    @Column(name = "authAlert")
    private Integer authAlert;


    @Column(name = "authLocation")
    private Integer authLocation;



    @Column(name = "authStart")
    private Integer authStart;



    @Column(name = "authShow")
    private Integer authShow;


    @Column(name = "authAlerDate")
    private Date authAlerDate;

    @Column(name = "authLocationDate")
    private Date authLocationDate;


    @Column(name = "authStartDate")
    private Date authStartDate;

    @Column(name = "authShowDate")
    private Date authShowDate;



}
