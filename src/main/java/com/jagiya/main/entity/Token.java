package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jagiya.auth.entity.UsersEditor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "Token")
@Table(name = "Token")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "토큰 정보 VO")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId")
    @Schema(description = "토큰 아이디")
    private Long tokenId;


    /* users 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    @Schema(description = "Users 식별자")
    private Users users;


    @Column(name = "tokenType")
    @Schema(description = "토큰 타입")
    private String tokenType;


    @Column(name = "accessToken")
    @Schema(description = "accessToken")
    private String accessToken;

    @Column(name = "idToken")
    @Schema(description = "idToken")
    private String idToken;

    @Column(name = "expiresIn")
    @Schema(description = "expiresIn")
    private String expiresIn;

    @Column(name = "refreshToken")
    @Schema(description = "refreshToken")
    private String refreshToken;

    @Column(name = "refreshTokenExpiresIn")
    @Schema(description = "refreshTokenExpiresIn")
    private Integer refreshTokenExpiresIn;

    @Column(name = "scope")
    @Schema(description = "scope")
    private String scope;

    @Column(name = "regDate")
    @Schema(description = "토큰생성일")
    private Date regDate;



}
