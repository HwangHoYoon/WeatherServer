package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "Users")
@Table(name = "Users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "유저 정보 VO")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usersId")
    @Schema(description = "유저 아이디")
    private Integer usersId;



    @Column(name = "username")
    @Schema(description = "유저 이름")
    private String username;


    @Column(name = "nickname")
    @Schema(description = "유저 닉네임")
    private String nickname;


    @Column(name = "email")
    @Schema(description = "유저 이메일")
    private String email;


    @Column(name = "deleteFlag")
    @Schema(description = "탈퇴여부(0:유지 / 1:탈퇴)")
    private Integer deleteFlag;


    @Column(name = "agreesFalg")
    @Schema(description = "약관동의여부(0:미동의 1:동의)")
    private Integer agreesFalg;


    @Column(name = "regDate")
    @Schema(description = "가입일")
    private Date regDate;

    @Column(name = "modifyDate")
    @Schema(description = "수정일")
    private Date modifyDate;

    @Column(name = "agreesDate")
    @Schema(description = "약관동의 날짜")
    private Date agreesDate;





}
