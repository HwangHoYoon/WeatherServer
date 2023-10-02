package com.jagiya.weather.entity;

import com.jagiya.alarm.entity.AlarmEditor;
import lombok.Getter;

@Getter
public class WeatherEditor {

    private String pop;
    private String pty;
    private String tmx;
    private String tmp;
    private String pcp;
    private String tmn;
    private String sky;
    private String baseDate;
    private String baseTime;

    public WeatherEditor(String pop, String pty, String tmx, String tmp, String pcp, String tmn, String sky, String baseDate, String baseTime) {
        this.pop = pop;
        this.pty = pty;
        this.tmx = tmx;
        this.tmp = tmp;
        this.pcp = pcp;
        this.tmn = tmn;
        this.sky = sky;
        this.baseDate = baseDate;
        this.baseTime = baseTime;
    }

    public static WeatherEditorBuilder builder() {
        return new WeatherEditorBuilder();
    }

    public static class WeatherEditorBuilder {
        private String pop;
        private String pty;
        private String tmx;
        private String tmp;
        private String pcp;
        private String tmn;
        private String sky;
        private String baseDate;
        private String baseTime;
        public WeatherEditorBuilder() {
        }

        public WeatherEditorBuilder pop(final String pop) {
            if (pop != null) {
                this.pop = pop;
            }
            return this;
        }

        public WeatherEditorBuilder pty(final String pty) {
            if (pty != null) {
                this.pty = pty;
            }
            return this;
        }

        public WeatherEditorBuilder tmx(final String tmx) {
            if (tmx != null) {
                this.tmx = tmx;
            }
            return this;
        }

        public WeatherEditorBuilder tmp(final String tmp) {
            if (tmp != null) {
                this.tmp = tmp;
            }
            return this;
        }

        public WeatherEditorBuilder pcp(final String pcp) {
            if (pcp != null) {
                this.pcp = pcp;
            }
            return this;
        }

        public WeatherEditorBuilder tmn(final String tmn) {
            if (tmn != null) {
                this.tmn = tmn;
            }
            return this;
        }

        public WeatherEditorBuilder sky(final String sky) {
            if (sky != null) {
                this.sky = sky;
            }
            return this;
        }

        public WeatherEditorBuilder baseDate(final String baseDate) {
            if (baseDate != null) {
                this.baseDate = baseDate;
            }
            return this;
        }

        public WeatherEditorBuilder baseTime(final String baseTime) {
            if (baseTime != null) {
                this.baseTime = baseTime;
            }
            return this;
        }

        public WeatherEditor build() {
            return new WeatherEditor(this.pop, this.pty, this.tmx, this.tmp, this.pcp, this.tmn, this.sky, this.baseDate, this.baseTime);
        }
    }
}
