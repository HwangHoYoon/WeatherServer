package com.jagiya.location.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity(name = "LocationGroup")
@Table(name = "LocationGroup")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "LocationGroup VO")
public class LocationGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationGroupId")
    @Schema(description = "locationGroupId")
    private Long locationGroupId;

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
