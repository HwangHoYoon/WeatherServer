package com.jagiya.alarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jagiya.login.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "Alarm")
@Table(name = "Alarm")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Alarm VO")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarmId")
    @Schema(description = "alarmId")
    private Long alarmId;

    @Column(name = "enabled")
    @Schema(description = "활성화여부")
    private Integer enabled;

    @Column(name = "volume")
    @Schema(description = "볼륨")
    private Integer volume;

    @Column(name = "vibration")
    @Schema(description = "진동여부")
    private Integer vibration;

    @Column(name = "alarmTime")
    @Schema(description = "알람시간")
    private String alarmTime;

    @Column(name = "reminder")
    @Schema(description = "다시울림")
    private String reminder;

    @Column(name = "regDate")
    @Schema(description = "등록일")
    private Date regDate;

    @Column(name = "modifyDate")
    @Schema(description = "수정일")
    private Date modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmSoundId")
    private AlarmSound alarmSound;

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

    public AlarmEditor.AlarmEditorBuilder toEditor() {
        return AlarmEditor.builder()
                .alarmTime(alarmTime)
                .reminder(reminder)
                .vibration(vibration)
                .volume(volume)
                .alarmSound(alarmSound);
    }

    public void edit(AlarmEditor alarmEditor) {
        alarmTime = alarmEditor.getAlarmTime();
        reminder = alarmEditor.getReminder();
        vibration = alarmEditor.getVibration();
        volume = alarmEditor.getVolume();
        alarmSound = alarmEditor.getAlarmSound();
    }

}
