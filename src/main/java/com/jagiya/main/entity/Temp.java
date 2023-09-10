package com.jagiya.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity(name = "Temp")
@Table(name = "Temp")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Temp VO")
public class Temp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id")
    private Long id;

    @Column(name = "fld01")
    @Schema(description = "fld01")
    private String fld01;

    @Column(name = "fld02")
    @Schema(description = "fld02")
    private String fld02;

    @Column(name = "fld03")
    @Schema(description = "fld03")
    private String fld03;

    @Column(name = "fld04")
    @Schema(description = "fld04")
    private String fld04;

    @Column(name = "fld05")
    @Schema(description = "fld05")
    private String fld05;

    @Column(name = "fld06")
    @Schema(description = "fld06")
    private String fld06;

    @Column(name = "fld07")
    @Schema(description = "fld07")
    private String fld07;

    @Column(name = "fld08")
    @Schema(description = "fld08")
    private String fld08;

    @Column(name = "fld09")
    @Schema(description = "fld09")
    private String fld09;

    @Column(name = "fld10")
    @Schema(description = "fld10")
    private String fld10;

    @Column(name = "fld11")
    @Schema(description = "fld11")
    private String fld11;

    @Column(name = "fld12")
    @Schema(description = "fld12")
    private String fld12;

    @Column(name = "fld13")
    @Schema(description = "fld13")
    private String fld13;

    @Column(name = "fld14")
    @Schema(description = "fld14")
    private String fld14;

    @Column(name = "fld15")
    @Schema(description = "fld15")
    private String fld15;

    @Column(name = "fld16")
    @Schema(description = "fld16")
    private String fld16;

    @Column(name = "fld17")
    @Schema(description = "fld17")
    private String fld17;

    @Column(name = "fld18")
    @Schema(description = "fld18")
    private String fld18;

    @Column(name = "fld19")
    @Schema(description = "fld19")
    private String fld19;

    @Column(name = "fld20")
    @Schema(description = "fld20")
    private String fld20;

}
