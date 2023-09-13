package com.jagiya.juso.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jagiya.main.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Data
@Entity(name = "JusoGroup")
@Table(name = "JusoGroup")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "JusoGroup VO")
public class JusoGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "jusoGroupId")
    @Schema(description = "jusoGroupId")
    private Long jusoGroupId;

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
