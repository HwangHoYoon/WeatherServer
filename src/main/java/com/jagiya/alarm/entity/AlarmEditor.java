package com.jagiya.alarm.entity;

import lombok.Getter;

@Getter
public class AlarmEditor {

    private String alarmTime;
    private String reminder;
    private Integer vibration;
    private Integer volume;
    private AlarmSound alarmSound;

    public AlarmEditor(String alarmTime, String reminder, Integer vibration, Integer volume, AlarmSound alarmSound) {
        this.alarmTime = alarmTime;
        this.reminder = reminder;
        this.vibration = vibration;
        this.volume = volume;
        this.alarmSound = alarmSound;
    }
    public static AlarmEditorBuilder builder() {
        return new AlarmEditorBuilder();
    }

    public static class AlarmEditorBuilder {
        private String alarmTime;
        private String reminder;
        private Integer vibration;
        private Integer volume;
        private AlarmSound alarmSound;

        AlarmEditorBuilder() {
        }

        public AlarmEditorBuilder alarmTime(final String alarmTime) {
            if (alarmTime != null) {
                this.alarmTime = alarmTime;
            }
            return this;
        }

        public AlarmEditorBuilder reminder(final String reminder) {
            if (reminder != null) {
                this.reminder = reminder;
            }
            return this;
        }

        public AlarmEditorBuilder vibration(final Integer vibration) {
            if (vibration != null) {
                this.vibration = vibration;
            }
            return this;
        }

        public AlarmEditorBuilder volume(final Integer volume) {
            if (volume != null) {
                this.volume = volume;
            }
            return this;
        }

        public AlarmEditorBuilder alarmSound(final AlarmSound alarmSound) {
            if (alarmSound != null) {
                this.alarmSound = alarmSound;
            }
            return this;
        }

        public AlarmEditor build() {
            return new AlarmEditor(this.alarmTime, this.reminder, this.vibration, this.volume, this.alarmSound);
        }

    }
}
