package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "UserLog")
@Table(name = "UserLog")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "유저 로그인 정보 VO")
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userLogId")
    @Schema(description = "userLog Id")
    private Integer userLogId;


    /* 유저 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    @Schema(description = "유저 식별자")
    private Users usersTb;



    @Column(name = "loginStatus")
    @Schema(description = "로그인 상태")
    private Integer loginStatus;


    @Column(name = "로그인 날짜")
    private Date loginDate;




}
