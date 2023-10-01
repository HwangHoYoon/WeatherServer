package com.jagiya.alarm.controller;

import com.jagiya.alarm.request.AlarmRequest;
import com.jagiya.alarm.response.AlarmResponse;
import com.jagiya.alarm.service.AlarmService;
import com.jagiya.common.exception.CommonException;
import com.jagiya.common.response.MessageCode;
import io.swagger.v3.oas.annotations.Operation;
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
    public List<AlarmResponse> getAlarmList(@Schema(description = "유저ID", example = "example1", name = "userId") Long userId) throws Exception {
        return alarmService.getAlarmList(userId);
    }

    @Operation(summary = "알람 등록", description = "알람 등록")
    @PostMapping("/insertAlarm")
    @ApiResponses(value = {@ApiResponse(responseCode = "SS", description = "OK")}
    )
    public MessageCode insertAlarm(@RequestBody AlarmRequest alarmRequest) {
        try {
            alarmService.insertAlarm(alarmRequest);
            return MessageCode.SUCCESS_SAVE;
        } catch (CommonException ce) {
            log.error("insertAlarm error {}, {}", ce.getMessage(), ce.getErrorCode());
            return MessageCode.FAIL_SAVE;
        } catch (Exception e) {
            log.error("insertAlarm error {}", e);
            return MessageCode.FAIL_SAVE;
        }
    }

    @Operation(summary = "알람 수정", description = "알람 수정")
    @PostMapping("/updateAlarm")
    @ApiResponses(value = {@ApiResponse(responseCode = "SS", description = "OK")}
    )
    public MessageCode updateAlarm(@RequestBody AlarmRequest alarmRequest) {
        try {
            alarmService.updateAlarm(alarmRequest);
            return MessageCode.SUCCESS_SAVE;
        } catch (CommonException ce) {
            log.error("updateAlarm error {}, {}", ce.getMessage(), ce.getErrorCode());
            return MessageCode.FAIL_SAVE;
        } catch (Exception e) {
            log.error("updateAlarm error {}", e);
            return MessageCode.FAIL_SAVE;
        }
    }

}
