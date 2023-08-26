package com.jagiya.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usersId")
    private Long usersId;

    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "snsType")
    private Integer snsType;

    @Column(name = "snsName")
    private String snsName;

    @Column(name = "snsProfile")
    private String snsProfile;

    @Column(name = "gender")
    private String gender;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "ci")
    private String ci;

    @Column(name = "deleteFlag")
    private Integer deleteFlag;

    @Column(name = "agreesFalg")
    private Integer agreesFalg;

    @Column(name = "isAdmin")
    private Integer isAdmin;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "ciDate")
    private Date ciDate;
    @Column(name = "snsConnectDate")
    private Date snsConnectDate;

    @Column(name = "regDate")
    private Date regDate;

    @Column(name = "modifyDate")
    private Date modifyDate;

    @Column(name = "agreesDate")
    private Date agreesDate;

    public UsersEditor.UsersEditorBuilder toEditor() {
        return UsersEditor.builder()
                .email(email)
                .nickname(nickname)
                .username(username)
                .snsProfile(snsProfile)
                .birthDay(birthday)
                .gender(gender)
                .snsConnectDate(snsConnectDate)
                .modifyDate(modifyDate);
    }

    public void edit(UsersEditor usersEditor) {
        email = usersEditor.getEmail();
        nickname = usersEditor.getNickname();
        username = usersEditor.getUsername();
        snsProfile = usersEditor.getSnsProfile();
        birthday = usersEditor.getBirthDay();
        gender = usersEditor.getGender();
        snsConnectDate = usersEditor.getSnsConnectDate();
        modifyDate = usersEditor.getModifyDate();
    }

}
