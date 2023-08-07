package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "SnsInfo")
@Table(name = "SnsInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SnsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snsInfoId")
    private Integer snsInfoId;


    /* 유저 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    private Users usersTb;



    @Column(name = "snsType")
    private Integer snsType;


    @Column(name = "snsName")
    private String snsName;


    @Column(name = "snsProfile")
    private String snsProfile;


    @Column(name = "accessToken")
    private String accessToken;


    @Column(name = "snsConnectDate")
    private Date snsConnectDate;



}
