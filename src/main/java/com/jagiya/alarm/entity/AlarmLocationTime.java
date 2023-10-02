package com.jagiya.alarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity(name = "AlarmLocationTime")
@Table(name = "AlarmLocationTime")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AlarmLocationTime VO")
public class AlarmLocationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarmLocationTimeId")
    @Schema(description = "alarmLocationTimeId")
    private Long alarmLocationTimeId;

    @Column(name = "locationTime")
    @Schema(description = "지역시간")
    private String locationTime;

    @Column(name = "regDate")
    @Schema(description = "등록일")
    private Date regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmLocationId")
    private AlarmLocation alarmLocation;

    // @PrePersist 메서드 정의 (최초 등록시 호출)
    @PrePersist
    public void prePersist() {
        this.regDate = new Date(); // 현재 날짜와 시간으로 등록일 설정
    }

}
