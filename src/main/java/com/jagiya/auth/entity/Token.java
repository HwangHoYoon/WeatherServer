package com.jagiya.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenId")
    private Integer tokenId;

    @Column(name = "tokenType")
    private String tokenType;
    @Column(name = "accessToken")
    private String accessToken;
    @Column(name = "idToken")
    private String idToken;
    @Column(name = "expiresIn")
    private Integer expiresIn;
    @Column(name = "refreshToken")
    private String refreshToken;
    @Column(name = "refreshTokenExpiresIn")
    private Integer refreshTokenExpiresIn;
    @Column(name = "scope")
    private String scope;
    @Column(name = "regDate")
    private Date regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId")
    private Users usersTb;

    public TokenEditor.TokenEditorBuilder toEditor() {
        return TokenEditor.builder()
                .tokenType(tokenType)
                .accessToken(accessToken)
                .idToken(idToken)
                .expiresIn(expiresIn)
                .refreshToken(refreshToken)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .scope(scope);
    }

    public void edit(TokenEditor tokenEditor) {
        tokenType = tokenEditor.getTokenType();
        accessToken = tokenEditor.getAccessToken();
        idToken = tokenEditor.getIdToken();
        expiresIn = tokenEditor.getExpiresIn();
        refreshToken = tokenEditor.getRefreshToken();
        refreshTokenExpiresIn = tokenEditor.getRefreshTokenExpiresIn();
        scope = tokenEditor.getScope();
    }

}
