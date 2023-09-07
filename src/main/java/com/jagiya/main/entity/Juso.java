package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity(name = "Juso")
@Table(name = "Juso")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Juso VO")
public class Juso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jusoId")
    @Schema(description = "jusoId")
    private Long jusoId;

    @Column(name = "code")
    @Schema(description = "법정동코드")
    private String code;

    @Column(name = "cityDo")
    @Schema(description = "시도")
    private String cityDo;

    @Column(name = "guGun")
    @Schema(description = "구군")
    private String guGun;

    @Column(name = "eupMyun")
    @Schema(description = "읍면동")
    private String eupMyun;

    @Column(name = "lat")
    @Schema(description = "위도")
    private String lat;

    @Column(name = "lon")
    @Schema(description = "경도")
    private String lon;

    @Column(name = "latX")
    @Schema(description = "X좌표")
    private String latX;

    @Column(name = "lonY")
    @Schema(description = "Y좌표")
    private String lonY;

    @Column(name = "regDate")
    @Schema(description = "등록일")
    private String regDate;

}
