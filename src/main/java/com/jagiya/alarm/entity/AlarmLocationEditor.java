package com.jagiya.alarm.entity;

import com.jagiya.location.entity.Location;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class AlarmLocationEditor {

    private Location location;

    private Alarm alarm;

    public AlarmLocationEditor(Location location, Alarm alarm) {
        this.location = location;
        this.alarm = alarm;
    }

    public static AlarmLocationEditorBuilder builder() {
        return new AlarmLocationEditorBuilder();
    }

    public static class AlarmLocationEditorBuilder {

        private Location location;

        private Alarm alarm;

        AlarmLocationEditorBuilder() {
        }

        public AlarmLocationEditorBuilder location(final Location location) {
            if (location != null) {
                this.location = location;
            }
            return this;
        }

        public AlarmLocationEditorBuilder alarm(final Alarm alarm) {
            if (alarm != null) {
                this.alarm = alarm;
            }
            return this;
        }

        public AlarmLocationEditor build() {
            return new AlarmLocationEditor(this.location, this.alarm);
        }
    }
}
