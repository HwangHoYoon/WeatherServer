package com.jagiya.alarm.controller;

import com.jagiya.alarm.request.AlarmDeleteRequest;
import com.jagiya.alarm.request.AlarmEnabledRequest;
import com.jagiya.alarm.request.AlarmInsertRequest;
import com.jagiya.alarm.request.AlarmUpdateRequest;
import com.jagiya.alarm.response.*;
import com.jagiya.alarm.service.AlarmService;
import com.jagiya.common.response.CommonMsgResponse;
import com.jagiya.common.response.MessageCode;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Alarm", description = "알람 API")
@RequestMapping("alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "알람리스트 조회", description = "알람리스트 조회")
    @GetMapping("/getAlarmList")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public List<AlarmResponse> getAlarmList(@Schema(description = "유저ID", example = "1", name = "userId") Long userId) throws Exception {
        return alarmService.selectAlarmList(userId);
    }

    @Operation(summary = "알람 조회", description = "알람 조회")
    @GetMapping("/getAlarmDetail")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public AlarmDetailResponse getAlarmDetail(@Schema(description = "알람ID", example = "1", name = "alarmId") Long alarmId) throws Exception {
        return alarmService.selectAlarmDetail(alarmId);
    }

    @Operation(summary = "알람 등록", description = "알람 등록")
    @PostMapping("/insertAlarm")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public AlarmDetailResponse insertAlarm(@RequestBody AlarmInsertRequest alarmInsertRequest) {
        return alarmService.insertAlarm(alarmInsertRequest);
    }

    @Operation(summary = "알람 수정", description = "알람 수정")
    @PutMapping("/updateAlarm")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public AlarmDetailResponse updateAlarm(@RequestBody AlarmUpdateRequest alarmUpdateRequest) {
        return alarmService.updateAlarm(alarmUpdateRequest);
    }

    @Operation(summary = "알람 사용여부 변경", description = "알람 사용여부 변경")
    @PutMapping("/updateAlarmEnabled")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public CommonMsgResponse updateAlarmEnabled(@RequestBody AlarmEnabledRequest alarmEnabledRequest) {
        alarmService.updateAlarmEnabled(alarmEnabledRequest);
        return new CommonMsgResponse(MessageCode.SUCCESS_SAVE.getMessage());
    }

    @Operation(summary = "알람삭제", description = "알람삭제")
    @DeleteMapping("/deleteAlarm")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public CommonMsgResponse deleteAlarm(@RequestBody AlarmDeleteRequest alarmDeleteRequest) {
        alarmService.deleteAlarm(alarmDeleteRequest);
        return new CommonMsgResponse(MessageCode.SUCCESS_DELETE.getMessage());
    }

    @Operation(summary = "알람지역별날씨 조회", description = "알람지역별날씨 조회")
    @GetMapping("/getAlarmLocationWeather")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public AlarmLocationNotiResponse getAlarmLocationWeather(@Schema(description = "알람ID", example = "1", name = "alarmId") Long alarmId) {
        return alarmService.selectAlarmLocationWeather(alarmId);
    }

    @Operation(summary = "알람지역별날씨 상세보기", description = "알람지역별날씨 상세보기")
    @GetMapping("/getAlarmLocationWeatherDetail")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public List<AlarmLocationWeatherDetailResponse> getAlarmLocationWeatherDetail(@Schema(description = "알람ID", example = "1", name = "alarmId") Long alarmId) {
        return alarmService.selectAlarmLocationWeatherDetail(alarmId);
    }

    @Operation(summary = "알람소리리스트 조회", description = "알람소리리스트 조회")
    @GetMapping("/getAlarmSoundList")
    @Hidden
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    public List<AlarmSoundResponse> getAlarmSoundList() throws Exception {
        return alarmService.selectAlarmSoundList();
    }


    @Operation(summary = "알람지역시간목록 조회", description = "알람지역시간목록 조회")
    @GetMapping("/getAlarmLocationTimeList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(schema = @Schema(example = "[\"0100\", \"0200\"]"))
            })
    })
    public List<String> getAlarmLocationTimeList(@Schema(description = "알람시간", example = "0120", name = "alarmTime") String alarmTime) {
        return alarmService.selectAlarmLocationTimeList(alarmTime);
    }

}
