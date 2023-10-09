package com.jagiya.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity(name = "User")
@Table(name = "User")
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    @Schema(description = "아이디")
    private Long userId;

    @Column(name = "name")
    @Schema(description = "이름")
    private String name;

    @Column(name = "email")
    @Schema(description = "이메일")
    private String email;

    @Column(name = "deleteFlag")
    @Schema(description = "탈퇴여부(0:유지 / 1:탈퇴)")
    private Integer deleteFlag;

    @Column(name = "deleteDate")
    @Schema(description = "탈퇴 날짜")
    private Date deleteDate;

    @Column(name = "agreeFalg")
    @Schema(description = "약관동의여부(0:미동의 1:동의)")
    private Integer agreeFalg;

    @Column(name = "agreeDate")
    @Schema(description = "약관동의 날짜")
    private Date agreeDate;

    @Column(name = "regDate")
    @Schema(description = "가입일")
    private Date regDate;

    @Column(name = "modifyDate")
    @Schema(description = "수정일")
    private Date modifyDate;

    @Column(name = "admin")
    @Schema(description = "관리자 여부")
    private Integer admin;

    @Column(name = "snsType")
    @Schema(description = "소셜 로그인 플랫폼 (비회원, 카카오, 애플)")
    private Integer snsType;

    @Column(name = "snsId")
    @Schema(description = "소셜회원 ID")
    private String snsId;

    public UsersEditor.UsersEditorBuilder toEditor() {
        return UsersEditor.builder()
                .email(email)
                .name(name)
                .snsId(snsId)
                .modifyDate(modifyDate);
    }

    public void edit(UsersEditor usersEditor) {
        email = usersEditor.getEmail();
        name = usersEditor.getName();
        snsId = usersEditor.getSnsId();
        modifyDate = usersEditor.getModifyDate();
    }

}

