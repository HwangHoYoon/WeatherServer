package com.jagiya.location.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jagiya.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;


@Data
@Entity(name = "RecentLocation")
@Table(name = "RecentLocation")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "RecentLocation VO")
public class RecentLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recentLocationId")
    @Schema(description = "recentLocationId")
    private Long recentLocationId;


    /* user 식별자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @Schema(description = "userId 식별자")
    private User userTb;

    @Column(name = "cityDo")
    @Schema(description = "시도")
    private String cityDo;

    @Column(name = "guGun")
    @Schema(description = "구군")
    private String guGun;

    @Column(name = "eupMyun")
    @Schema(description = "읍면동")
    private String eupMyun;

    @Column(name = "regionCd")
    @Schema(description = "법정동코드")
    private String regionCd;

    @Column(name = "regDate")
    @Schema(description = "등록일")
    private Date regDate;

}
