package com.jagiya.alarm.service;

import com.jagiya.alarm.entity.*;
import com.jagiya.alarm.enums.TimeOfDay;
import com.jagiya.alarm.repository.AlarmLocationRespository;
import com.jagiya.alarm.repository.AlarmLocationTimeRespository;
import com.jagiya.alarm.repository.AlarmRepository;
import com.jagiya.alarm.repository.AlarmWeekRepository;
import com.jagiya.alarm.request.AlarmLocationRequest;
import com.jagiya.alarm.request.AlarmLocationTimeRequest;
import com.jagiya.alarm.request.AlarmRequest;
import com.jagiya.alarm.request.AlarmWeekRequest;
import com.jagiya.alarm.response.AlarmLocationResponse;
import com.jagiya.alarm.response.AlarmResponse;
import com.jagiya.alarm.response.AlarmWeekResponse;
import com.jagiya.common.exception.CommonException;
import com.jagiya.location.entity.Location;
import com.jagiya.location.request.LocationRequest;
import com.jagiya.location.service.LocationService;
import com.jagiya.login.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;

    private final AlarmLocationRespository alarmLocationRespository;

    private final AlarmLocationTimeRespository alarmLocationTimeRespository;

    private final AlarmWeekRepository alarmWeekRepository;

    private final LocationService locationService;

    public List<AlarmResponse> getAlarmList(Long userId) {
        List<Alarm> alarmList = alarmRepository.findByUserUserId(userId);

        List<AlarmResponse> alarmResponseList = new ArrayList<>();

        if (alarmList != null && alarmList.size() > 0) {
            for (Alarm alarm : alarmList) {
                Long alarmId = alarm.getAlarmId();
                List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmId(alarmId);
                List<AlarmLocationResponse> alarmLocationResponseList = new ArrayList<>();

                // 주소 목록
                if (alarmLocationList != null && alarmLocationList.size() > 0) {
                    for (AlarmLocation alarmLocation : alarmLocationList) {

                        Long alarmLocationId = alarmLocation.getAlarmLocationId();

                        String cityDo = alarmLocation.getLocation().getCityDo();
                        String guGun = alarmLocation.getLocation().getGuGun();
                        String eupMyun = alarmLocation.getLocation().getEupMyun();

                        List<AlarmLocationTime> alarmLocationTimeList = alarmLocationTimeRespository.findByAlarmLocationAlarmLocationId(alarmLocationId);
                        // 오전 오후 종일 체크
                        String timeOfDay = getTimeOfDayForTimeList(alarmLocationTimeList);

                        AlarmLocationResponse alarmLocationResponse = AlarmLocationResponse.builder()
                                .cityDo(cityDo)
                                .guGun(guGun)
                                .eupMyun(eupMyun)
                                .timeOfDay(timeOfDay)
                                .build();
                        alarmLocationResponseList.add(alarmLocationResponse);
                    }
                }

                // 요일 목록
                List<AlarmWeekResponse> alarmWeekResponseList = new ArrayList<>();
                List<AlarmWeek> alarmWeekList = alarmWeekRepository.findByAlarmAlarmId(alarmId);
                if (alarmWeekList != null && alarmWeekList.size() > 0) {
                    for (AlarmWeek alarmWeek : alarmWeekList) {
                        AlarmWeekResponse alarmWeekResponse = AlarmWeekResponse.builder()
                                .alarmWeekId(alarmWeek.getAlarmWeekId())
                                .weekId(alarmWeek.getWeek().getWeekId())
                                .build();
                        alarmWeekResponseList.add(alarmWeekResponse);
                    }
                }

                Integer enabled = alarm.getEnabled();
                Integer vibration = alarm.getVibration();
                String asisTime = alarm.getAlarmTime();
                String timeOfDay = getTimeOfDay(asisTime);
                String alarmTime = getTime(asisTime, "HHmm", "hhmm");

                AlarmResponse alarmResponse = AlarmResponse.builder()
                        .alarmId(alarmId)
                        .alarmTime(alarmTime)
                        .timeOfDay(timeOfDay)
                        .enabled(enabled)
                        .vibration(vibration)
                        .alarmLocation(alarmLocationResponseList)
                        .alarmWeek(alarmWeekResponseList)
                        .build();

                alarmResponseList.add(alarmResponse);
            }
        }
        return alarmResponseList;
    }

    private String getTimeOfDayForTimeList(List<AlarmLocationTime> alarmLocationTimeList) {

        try {
            boolean containsAM = false; // 오전 포함 여부
            boolean containsPM = false; // 오후 포함 여부

            for (AlarmLocationTime alarmLocationTime : alarmLocationTimeList) {
                // SimpleDateFormat을 사용하여 문자열을 시간 형식으로 파싱
                String timeStr = alarmLocationTime.getLocationTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                Date time = sdf.parse(timeStr);

                // 시간을 시로 변환
                int hours = time.getHours();

                // 시간대 확인 및 오전/오후 포함 여부 설정
                if (hours < 12) {
                    containsAM = true;
                } else if (hours >= 12) {
                    containsPM = true;
                }
            }

            // 오전과 오후를 모두 포함하는지 확인
            if (containsAM && containsPM) {
                return TimeOfDay.ALLDAY.getEngName();
            } else if (containsAM) {
                return TimeOfDay.MORINING.getEngName();
            } else if (containsPM) {
                return TimeOfDay.AFTERNOON.getEngName();
            } else {
                log.error("오전과 오후 모두 포함하지 않습니다. {}", alarmLocationTimeList);
            }
        } catch (Exception e) {
            log.error("오전 오후 체크 오류 getTimeOfDayForTimeList {}", e);
        }
        return null;
    }


    private String getTimeOfDay(String timeStr) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
            Date time = sdf.parse(timeStr);

            // 시간을 시로 변환
            int hours = time.getHours();

            // 시간대 확인 및 오전/오후 포함 여부 설정
            if (hours < 12) {
                return TimeOfDay.MORINING.getEngName();
            } else if (hours >= 12) {
                return TimeOfDay.AFTERNOON.getEngName();
            } else {
                log.error("오전과 오후 모두 포함하지 않습니다. {}", timeStr);
                return "";
            }
        } catch (Exception e) {
            log.error("오전 오후 체크 오류 getTimeOfDay {}", e);
        }
        return null;
    }

    private String getTime(String timeStr, String asisFormat, String tobeFormat) {

        try {
            SimpleDateFormat inputSdf = new SimpleDateFormat(asisFormat);
            Date date = inputSdf.parse(timeStr);

            SimpleDateFormat outputSdf = new SimpleDateFormat(tobeFormat);
            String formattedTime = outputSdf.format(date);

            return formattedTime;
        } catch (Exception e) {
            log.error("시간형식 변경 오휴 getTime {}", e);
        }
        return null;

    }

    @Transactional
    public void insertAlarm(AlarmRequest alarmRequest) {
        // 알람 저장
        Long userId = alarmRequest.getUserId();
        User user = User.builder()
                .userId(userId)
                .build();

        Long alarmSoundId = alarmRequest.getAlarmSoundId();
        AlarmSound alarmSound = AlarmSound.builder()
                .alarmSoundId(alarmSoundId)
                .build();

        String reminder = alarmRequest.getReminder();
        Integer vibration = alarmRequest.getVibration();
        Integer volume = alarmRequest.getVolume();
        String alarmTime = alarmRequest.getAlarmTime();
        String timeOfDay = alarmRequest.getTimeOfDay();
        String time = getTime(alarmTime, timeOfDay);

        Alarm alarm = Alarm.builder()
                    .alarmTime(time)
                    .reminder(reminder)
                    .vibration(vibration)
                    .volume(volume)
                    .alarmSound(alarmSound)
                    .user(user)
                    .build();
            alarmRepository.save(alarm);

        // 요일 저장 기존 요일 삭제 후 등록
        List<AlarmWeekRequest> weekList = alarmRequest.getWeekList();
        if (weekList != null && weekList.size() > 0) {
            Long alarmId = alarm.getAlarmId();
            alarmWeekRepository.deleteByAlarmAlarmId(alarmId);
            for (AlarmWeekRequest alarmWeekRequest : weekList) {
                Long weekId = alarmWeekRequest.getWeekId();
                Week week = Week.builder()
                        .weekId(weekId)
                        .build();
                AlarmWeek alarmWeek = AlarmWeek.builder()
                        .alarm(alarm)
                        .week(week)
                        .build();
                alarmWeekRepository.save(alarmWeek);
            }
        }

        // 지역 저장
        List<AlarmLocationRequest> AlarmLocationList = alarmRequest.getAlarmLocationList();
        if (AlarmLocationList != null && AlarmLocationList.size() > 0) {
            for (AlarmLocationRequest alarmLocationRequest : AlarmLocationList) {
                String regionCd = alarmLocationRequest.getRegionCd();
                String cityDo = alarmLocationRequest.getCityDo();
                String guGun = alarmLocationRequest.getGuGun();
                String eupMyun = alarmLocationRequest.getEupMyun();

                LocationRequest locationRequest = new LocationRequest();
                locationRequest.setRegionCd(regionCd);
                locationRequest.setCityDo(cityDo);
                locationRequest.setGuGun(guGun);
                locationRequest.setEupMyun(eupMyun);

                Location location = locationService.selectInsertLocation(locationRequest);

                AlarmLocation alarmLocation = AlarmLocation.builder()
                        .alarm(alarm)
                        .location(location)
                        .build();
                alarmLocationRespository.save(alarmLocation);

                // 지역 시간 저장 기존값은 전부 삭제 후 저장
                List<AlarmLocationTimeRequest> AlarmLocationTimeList = alarmLocationRequest.getAlarmLocationTimeRequest();
                if (AlarmLocationTimeList != null && AlarmLocationTimeList.size() > 0) {
                    alarmLocationTimeRespository.deleteByAlarmLocationAlarmLocationId(alarmLocation.getAlarmLocationId());
                    for (AlarmLocationTimeRequest alarmLocationTimeRequest : AlarmLocationTimeList) {
                        String locationTime = alarmLocationTimeRequest.getLocationTime();
                        AlarmLocationTime alarmLocationTime = AlarmLocationTime.builder()
                                .alarmLocation(alarmLocation)
                                .locationTime(locationTime)
                                .build();
                        alarmLocationTimeRespository.save(alarmLocationTime);
                    }
                }
            }
        }
    }

    private String getTime(String alarmTime, String timeOfDay) {
        if (alarmTime.length() == 4) {
            String hour = alarmTime.substring(0, 2);
            String minute = alarmTime.substring(2);
            if (StringUtils.equals(timeOfDay, "PM") && !StringUtils.equals(hour, "12")) {
                hour = String.valueOf(Integer.parseInt(hour) + 12);
            } else if (StringUtils.equals(timeOfDay, "AM") && StringUtils.equals(hour, "12")) {
                hour = "00";
            }
            return hour+minute;
        } else {
            throw new CommonException("456", "알람 시간이 올바르지 않습니다.");
        }
    }

    @Transactional
    public void updateAlarm(AlarmRequest alarmRequest) {
        // 알람 수정
        Long alarmSoundId = alarmRequest.getAlarmSoundId();
        AlarmSound alarmSound = AlarmSound.builder()
                .alarmSoundId(alarmSoundId)
                .build();

        String reminder = alarmRequest.getReminder();
        Integer vibration = alarmRequest.getVibration();
        Integer volume = alarmRequest.getVolume();
        String alarmTime = alarmRequest.getAlarmTime();
        String timeOfDay = alarmRequest.getTimeOfDay();
        String time = getTime(alarmTime, timeOfDay);
        Long alarmId = alarmRequest.getAlarmId();
        Alarm alarm;
        if (alarmId != null) {
            Optional<Alarm> alarmInfo = alarmRepository.findById(alarmId);
            if (alarmInfo.isPresent()) {
                alarm = alarmInfo.get();
                AlarmEditor.AlarmEditorBuilder editorBuilder = alarm.toEditor();
                AlarmEditor alarmEditor = editorBuilder.alarmTime(time)
                        .reminder(reminder)
                        .vibration(vibration)
                        .volume(volume)
                        .alarmSound(alarmSound)
                        .build();
                alarm.edit(alarmEditor);
            } else {
                throw new CommonException("알람정보가 올바르지 않습니다.", "444");
            }
        } else {
            throw new CommonException("알람정보가 올바르지 않습니다.", "443");
        }

        // 요일 저장
        List<AlarmWeekRequest> weekList = alarmRequest.getWeekList();

        // 비교를 위해 기존값 조회
        List<AlarmWeek> alarmWeekList = alarmWeekRepository.findByAlarmAlarmId(alarmId);

        // 기존 값 삭제
        findUniqueValuesAlarmWeek(alarmWeekList, weekList);
        for (AlarmWeek deleteAlarmWeek : alarmWeekList) {
            Long alarmWeekId = deleteAlarmWeek.getAlarmWeekId();
            alarmWeekRepository.deleteById(alarmWeekId);
        }

        // 새로운 값 등록
        if (weekList != null && weekList.size() > 0) {
            for (AlarmWeekRequest alarmWeekRequest : weekList) {
                if (alarmWeekRequest.getAlarmWeekId() == null) {
                    Long weekId = alarmWeekRequest.getWeekId();
                    Week week = Week.builder()
                            .weekId(weekId)
                            .build();
                    AlarmWeek alarmWeek = AlarmWeek.builder()
                            .alarm(alarm)
                            .week(week)
                            .build();
                    alarmWeekRepository.save(alarmWeek);
                }
            }
        }

        // 지역 등록 및 삭제
        List<AlarmLocationRequest> alarmLocationRequestList = alarmRequest.getAlarmLocationList();
        if (alarmLocationRequestList != null && alarmLocationRequestList.size() > 0) {
            // 비교를 위해 기존 값 조회
            List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmId(alarmId);

            // 기존값 삭제
            findUniqueValuesAlarmLocation(alarmLocationList, alarmLocationRequestList);
            for (AlarmLocation deleteAlarmLocation : alarmLocationList) {
                // 해당 알람지역의 시간부터 삭제 후 삭제
                Long alarmLocationId = deleteAlarmLocation.getAlarmLocationId();
                alarmLocationTimeRespository.deleteByAlarmLocationAlarmLocationId(alarmLocationId);
                alarmLocationRespository.deleteById(alarmLocationId);
            }

            // 지역등록 및 지역시간 등록 및 삭제
            for (AlarmLocationRequest alarmLocationRequest : alarmLocationRequestList) {
                AlarmLocation alarmLocation;
                if (alarmLocationRequest.getAlarmLocationId() == null) {
                    // 지역정보 조회
                    String regionCd = alarmLocationRequest.getRegionCd();
                    String cityDo = alarmLocationRequest.getCityDo();
                    String guGun = alarmLocationRequest.getGuGun();
                    String eupMyun = alarmLocationRequest.getEupMyun();

                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setRegionCd(regionCd);
                    locationRequest.setCityDo(cityDo);
                    locationRequest.setGuGun(guGun);
                    locationRequest.setEupMyun(eupMyun);

                    Location location = locationService.selectInsertLocation(locationRequest);

                    // 알람지역 저장
                    alarmLocation = AlarmLocation.builder()
                            .alarm(alarm)
                            .location(location)
                            .build();
                    alarmLocationRespository.save(alarmLocation);
                } else {
                    alarmLocation = AlarmLocation.builder()
                            .alarmLocationId(alarmLocationRequest.getAlarmLocationId())
                            .build();
                }


                if (alarmLocationRequest.getAlarmLocationId() == null) {
                    // 지역 시간 저장
                    List<AlarmLocationTimeRequest> AlarmLocationTimeList = alarmLocationRequest.getAlarmLocationTimeRequest();

                    if (AlarmLocationTimeList != null && AlarmLocationTimeList.size() > 0) {
                        for (AlarmLocationTimeRequest alarmLocationTimeRequest : AlarmLocationTimeList) {
                            String locationTime = alarmLocationTimeRequest.getLocationTime();
                            AlarmLocationTime alarmLocationTime = AlarmLocationTime.builder()
                                    .alarmLocation(alarmLocation)
                                    .locationTime(locationTime)
                                    .build();
                            alarmLocationTimeRespository.save(alarmLocationTime);
                        }
                    }
                } else {
                    // 기존 값 조회
                    List<AlarmLocationTime> alarmLocationTimeList = alarmLocationTimeRespository.findByAlarmLocationAlarmLocationId(alarmLocationRequest.getAlarmLocationId());

                    List<AlarmLocationTimeRequest> alarmLocationTimeRequestList = alarmLocationRequest.getAlarmLocationTimeRequest();

                    findUniqueValuesAlarmLocationTime(alarmLocationTimeList, alarmLocationTimeRequestList);

                    // 기존 값 삭제
                    for (AlarmLocationTime alarmLocationTime : alarmLocationTimeList) {
                        alarmLocationTimeRespository.deleteById(alarmLocationTime.getAlarmLocationTimeId());
                    }

                    // 새로운 값 등록
                    if (alarmLocationTimeRequestList != null && alarmLocationTimeRequestList.size() > 0) {
                        for (AlarmLocationTimeRequest alarmLocationTimeRequest : alarmLocationTimeRequestList) {
                            if (alarmLocationTimeRequest.getAlarmLocationTimeId() == null) {
                                String locationTime = alarmLocationTimeRequest.getLocationTime();
                                AlarmLocationTime alarmLocationTime = AlarmLocationTime.builder()
                                        .alarmLocation(alarmLocation)
                                        .locationTime(locationTime)
                                        .build();
                                alarmLocationTimeRespository.save(alarmLocationTime);
                            }
                        }
                    }
                }
            }
        }
    }

    private void findUniqueValuesAlarmLocation(List<AlarmLocation> listA, List<AlarmLocationRequest> listB) {
        Iterator<AlarmLocation> iteratorA = listA.iterator();
        while (iteratorA.hasNext()) {
            AlarmLocation elementA = iteratorA.next();
            for (AlarmLocationRequest elementB : listB) {
                if (elementA.getAlarmLocationId() == elementB.getAlarmLocationId()) {
                    iteratorA.remove(); // 중복된 요소를 삭제
                }
            }
        }
    }

    private void findUniqueValuesAlarmWeek(List<AlarmWeek> listA, List<AlarmWeekRequest> listB) {
        Iterator<AlarmWeek> iteratorA = listA.iterator();
        while (iteratorA.hasNext()) {
            AlarmWeek elementA = iteratorA.next();
            for (AlarmWeekRequest elementB : listB) {
                if (elementA.getAlarmWeekId() == elementB.getAlarmWeekId()) {
                    iteratorA.remove(); // 중복된 요소를 삭제
                }
            }
        }
    }

    private void findUniqueValuesAlarmLocationTime(List<AlarmLocationTime> listA, List<AlarmLocationTimeRequest> listB) {
        Iterator<AlarmLocationTime> iteratorA = listA.iterator();
        while (iteratorA.hasNext()) {
            AlarmLocationTime elementA = iteratorA.next();
            for (AlarmLocationTimeRequest elementB : listB) {
                if (elementA.getAlarmLocationTimeId() == elementB.getAlarmLocationTimeId()) {
                    iteratorA.remove(); // 중복된 요소를 삭제
                }
            }
        }
    }

}
