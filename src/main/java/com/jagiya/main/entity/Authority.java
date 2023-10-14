package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity(name = "Authority")
@Table(name = "Authority")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "알람권한 VO")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorityId")
    @Schema(description = "authority Id")
    private Long authorityId;


    @Column(name = "deviceId")
    @Schema(description = "디바이스 ID")
    private String deviceId;


    @Column(name = "authFlag")
    @Schema(description = "알림권한 동의여부")
    private Integer authFlag;



    @Column(name = "authDate")
    @Schema(description = "알람권한 갱신일자")
    private Date authDate;


}
