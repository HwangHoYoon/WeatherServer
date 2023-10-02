package com.jagiya.alarm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jagiya.location.entity.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
import java.util.List;

@Data
@Entity(name = "AlarmLocation")
@Table(name = "AlarmLocation")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties()
@DynamicInsert
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "AlarmLocation VO")
public class AlarmLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarmLocationId")
    @Schema(description = "alarmLocationId")
    private Long alarmLocationId;

    @Column(name = "regDate")
    @Schema(description = "등록일")
    private Date regDate;

    @Column(name = "modifyDate")
    @Schema(description = "수정일")
    private Date modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locationsId")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmId")
    private Alarm alarm;

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


    public AlarmLocationEditor.AlarmLocationEditorBuilder toEditor() {
        return AlarmLocationEditor.builder()
                .alarm(alarm)
                .location(location);
    }

    public void edit(AlarmLocationEditor alarmLocationEditor) {
        alarm = alarmLocationEditor.getAlarm();
        location = alarmLocationEditor.getLocation();
    }
}
