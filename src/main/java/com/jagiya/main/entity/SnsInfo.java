package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "가입SNS VO")
public class SnsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snsInfoId")
    @Schema(description = "snsInfo Id")
    private Integer snsInfoId;


    /* 유저 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    @Schema(description = "유저 식별자")
    private Users usersTb;



    @Column(name = "snsType")
    @Schema(description = "SNS 타입")
    private Integer snsType;


    @Column(name = "snsName")
    @Schema(description = "SNS 이름")
    private String snsName;


    @Column(name = "snsProfile")
    @Schema(description = "프로필")
    private String snsProfile;


    @Column(name = "accessToken")
    @Schema(description = "토큰값")
    private String accessToken;


    @Column(name = "snsConnectDate")
    @Schema(description = "SNS 가입일")
    private Date snsConnectDate;



}
