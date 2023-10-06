package com.jagiya.alarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Data
@Entity(name = "AlarmSound")
@Table(name = "AlarmSound")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AlarmSound VO")
public class AlarmSound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarmSoundId")
    @Schema(description = "alarmSoundId")
    private Long alarmSoundId;

    @Column(name = "alarmSoundName")
    @Schema(description = "알람소리 이름")
    private String alarmSoundName;

    @Column(name = "fileName")
    @Schema(description = "파일이름")
    private String fileName;

    @Column(name = "regDate")
    @Schema(description = "등록일")
    private Date regDate;

    @Column(name = "modifyDate")
    @Schema(description = "수정일")
    private Date modifyDate;

    // @PrePersist 메서드 정의 (최초 등록시 호출)
    @PrePersist
    public void prePersist() {
        this.regDate = new Date(); // 현재 날짜와 시간으로 등록일 설정
    }

    // @PreUpdate 메서드 정의 (업데이트 시 호출)
    @PreUpdate
    public void preUpdate() {
        this.modifyDate = new Date(); // 현재 날짜와 시간으로 수정일 업데이트
    }
}
