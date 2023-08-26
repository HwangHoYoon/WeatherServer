package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jagiya.auth.entity.TokenEditor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "UsersTemp")
@Table(name = "UsersTemp")
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
    private Integer usersId;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "deleteFlag")
    private Integer deleteFlag;

    @Column(name = "agreesFalg")
    private Integer agreesFalg;

    @Column(name = "regDate")
    private Date regDate;

    @Column(name = "modifyDate")
    private Date modifyDate;

    @Column(name = "agreesDate")
    private Date agreesDate;


}
