package com.jagiya.alarm.service;

import com.jagiya.alarm.entity.*;
import com.jagiya.alarm.enums.TimeOfDay;
import com.jagiya.alarm.repository.*;
import com.jagiya.alarm.request.*;
import com.jagiya.alarm.response.*;
import com.jagiya.common.exception.CommonException;
import com.jagiya.location.entity.Location;
import com.jagiya.location.entity.LocationGroup;
import com.jagiya.location.request.LocationRequest;
import com.jagiya.location.service.LocationService;
import com.jagiya.user.entity.User;
import com.jagiya.weather.entity.Weather;
import com.jagiya.weather.service.WeatherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;

    private final AlarmLocationRespository alarmLocationRespository;

    private final AlarmLocationTimeRespository alarmLocationTimeRespository;

    private final AlarmWeekRepository alarmWeekRepository;

    private final LocationService locationService;

    private final WeatherService weatherService;

    private final AlarmSoundRepository alarmSoundRepository;


    public List<AlarmResponse> selectAlarmList(Long userId) {
        List<Alarm> alarmList = alarmRepository.findByUserUserId(userId);

        List<AlarmResponse> alarmResponseList = new ArrayList<>();

        if (alarmList != null && alarmList.size() > 0) {
            for (Alarm alarm : alarmList) {
                Long alarmId = alarm.getAlarmId();
                List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmIdOrderByAlarmLocationId(alarmId);
                List<AlarmLocationResponse> alarmLocationResponseList = new ArrayList<>();

                // 주소 목록
                if (alarmLocationList != null && alarmLocationList.size() > 0) {
                    for (AlarmLocation alarmLocation : alarmLocationList) {

                        Long alarmLocationId = alarmLocation.getAlarmLocationId();

                        String cityDo = alarmLocation.getLocation().getCityDo();
                        String guGun = alarmLocation.getLocation().getGuGun();
                        String eupMyun = alarmLocation.getLocation().getEupMyun();

                        List<AlarmLocationTime> alarmLocationTimeList = alarmLocationTimeRespository.findByAlarmLocationAlarmLocationIdOrderByAlarmLocationTimeId(alarmLocationId);
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
    public void insertAlarm(AlarmInsertRequest alarmInsertRequest) {
        // 알람 저장
        Long userId = alarmInsertRequest.getUserId();
        User user = User.builder()
                .userId(userId)
                .build();

        Long alarmSoundId = alarmInsertRequest.getAlarmSoundId();
/*        AlarmSound alarmSound = AlarmSound.builder()
                .alarmSoundId(alarmSoundId)
                .build();*/

        String reminder = alarmInsertRequest.getReminder();
        Integer vibration = alarmInsertRequest.getVibration();
        Integer volume = alarmInsertRequest.getVolume();
        String alarmTime = alarmInsertRequest.getAlarmTime();
        String timeOfDay = alarmInsertRequest.getTimeOfDay();
        String time = getTime(alarmTime, timeOfDay);

        Alarm alarm = Alarm.builder()
                    .alarmTime(time)
                    .reminder(reminder)
                    .vibration(vibration)
                    .volume(volume)
                    .alarmSoundId(alarmSoundId)
                    .user(user)
                    .build();
            alarmRepository.save(alarm);

        // 요일 저장 기존 요일 삭제 후 등록
        List<AlarmWeekRequest> weekList = alarmInsertRequest.getWeekList();
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
        List<AlarmLocationRequest> AlarmLocationList = alarmInsertRequest.getAlarmLocationList();
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
    public void updateAlarm(AlarmUpdateRequest alarmUpdateRequest) {
        // 알람 수정
        Long alarmSoundId = alarmUpdateRequest.getAlarmSoundId();
/*        AlarmSound alarmSound = AlarmSound.builder()
                .alarmSoundId(alarmSoundId)
                .build();*/

        String reminder = alarmUpdateRequest.getReminder();
        Integer vibration = alarmUpdateRequest.getVibration();
        Integer volume = alarmUpdateRequest.getVolume();
        String alarmTime = alarmUpdateRequest.getAlarmTime();
        String timeOfDay = alarmUpdateRequest.getTimeOfDay();
        String time = getTime(alarmTime, timeOfDay);
        Long alarmId = alarmUpdateRequest.getAlarmId();
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
                        .alarmSoundId(alarmSoundId)
                        .build();
                alarm.edit(alarmEditor);
            } else {
                throw new CommonException("알람정보가 올바르지 않습니다.", "444");
            }
        } else {
            throw new CommonException("알람정보가 올바르지 않습니다.", "443");
        }

        // 요일 저장
        List<AlarmWeekRequest> weekList = alarmUpdateRequest.getWeekList();

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
        List<AlarmLocationRequest> alarmLocationRequestList = alarmUpdateRequest.getAlarmLocationList();
        if (alarmLocationRequestList != null && alarmLocationRequestList.size() > 0) {
            // 비교를 위해 기존 값 조회
            List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmIdOrderByAlarmLocationId(alarmId);

            // 기존값 삭제
            findUniqueValuesAlarmLocation(alarmLocationList, alarmLocationRequestList);
            for (AlarmLocation deleteAlarmLocation : alarmLocationList) {
                // 해당 알람지역의 시간부터 삭제 후 삭제
                Long alarmLocationId = deleteAlarmLocation.getAlarmLocationId();
                alarmLocationTimeRespository.deleteByAlarmLocationAlarmLocationId(alarmLocationId);
                alarmLocationRespository.deleteById(alarmLocationId);
            }

            // 지역등록 및 지역시간 등록 및 수정 삭제
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
                    // 장소 변경 및 시간만 변경했을 경우
                    alarmLocation = alarmLocationRespository.findById(alarmLocationRequest.getAlarmLocationId()).orElseThrow(() -> new CommonException("알람지역정보가 올바르지 않습니다.", "334"));

                    String regionCd = alarmLocationRequest.getRegionCd();
                    String dbRegionCd = alarmLocation.getLocation().getRegionCd();

                    if (!StringUtils.equals(regionCd, dbRegionCd)) {
                        String cityDo = alarmLocationRequest.getCityDo();
                        String guGun = alarmLocationRequest.getGuGun();
                        String eupMyun = alarmLocationRequest.getEupMyun();

                        LocationRequest locationRequest = new LocationRequest();
                        locationRequest.setRegionCd(regionCd);
                        locationRequest.setCityDo(cityDo);
                        locationRequest.setGuGun(guGun);
                        locationRequest.setEupMyun(eupMyun);

                        Location location = locationService.selectInsertLocation(locationRequest);

                        AlarmLocationEditor.AlarmLocationEditorBuilder editorBuilder = alarmLocation.toEditor();
                        AlarmLocationEditor alarmLocationEditor = editorBuilder.alarm(alarm).location(location).build();
                        alarmLocation.edit(alarmLocationEditor);
                    }
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
                    List<AlarmLocationTime> alarmLocationTimeList = alarmLocationTimeRespository.findByAlarmLocationAlarmLocationIdOrderByAlarmLocationTimeId(alarmLocationRequest.getAlarmLocationId());

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

    @Transactional
    public void updateAlarmEnabled(AlarmEnabledRequest alarmEnabledRequest) {
        Long alarmId = alarmEnabledRequest.getAlarmId();
        Integer enabled = alarmEnabledRequest.getEnabled();

        // 알람 활성화 여부 변경
        if (alarmId != null) {
            Optional<Alarm> alarmInfo = alarmRepository.findById(alarmId);
            if (alarmInfo.isPresent()) {
                Alarm alarm = alarmInfo.get();
                AlarmEditor.AlarmEditorBuilder editorBuilder = alarm.toEditor();
                AlarmEditor alarmEditor = editorBuilder.enabled(enabled).build();
                alarm.edit(alarmEditor);
            } else {
                throw new CommonException("알람정보가 올바르지 않습니다.", "444");
            }
        } else {
            throw new CommonException("알람정보가 올바르지 않습니다.", "443");
        }
    }

    public AlarmLocationNotiResponse selectAlarmLocationWeather(Long alarmId) {
        AlarmLocationNotiResponse alarmLocationNotiResponse = new AlarmLocationNotiResponse();
        if (alarmId != null) {
            Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new CommonException("알람정보가 올바르지 않습니다.", "444"));
            String reminder = alarm.getReminder();
            alarmLocationNotiResponse.setReminder(reminder);
            List<AlarmLocationWeatherResponse> alarmLocationWeatherResponseList = selectAlarmLocationWeatherList(alarmId);
            int locationCnt = alarmLocationWeatherResponseList.size() == 0 ? alarmLocationWeatherResponseList.size() : alarmLocationWeatherResponseList.size() - 1;
            alarmLocationNotiResponse.setLocationCnt(locationCnt);

            TimeOfDay[] timeOfDays = TimeOfDay.values();
            for (TimeOfDay timeOfDay : timeOfDays) {
                for (AlarmLocationWeatherResponse alarmLocationWeatherResponse : alarmLocationWeatherResponseList) {
                    if (StringUtils.equals(timeOfDay.getEngName(), alarmLocationWeatherResponse.getTimeOfDay())) {
                        alarmLocationNotiResponse.setCityDo(alarmLocationWeatherResponse.getCityDo());
                        alarmLocationNotiResponse.setGuGun(alarmLocationWeatherResponse.getGuGun());
                        alarmLocationNotiResponse.setEupMyun(alarmLocationWeatherResponse.getEupMyun());
                        alarmLocationNotiResponse.setTimeOfDay(alarmLocationWeatherResponse.getTimeOfDay());
                        return alarmLocationNotiResponse;
                    }
                }
            }
        } else {
            throw new CommonException("알람정보가 올바르지 않습니다.", "443");
        }
        return null;
    }

    public List<AlarmLocationWeatherResponse> selectAlarmLocationWeatherList(Long alarmId) {
        List<AlarmLocationWeatherResponse> alarmLocationWeatherResponseList = new ArrayList<>();
        if (alarmId != null) {
            Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new CommonException("알람 정보가 올바르지 않습니다.", "666"));

            // 해당 알람과 동일한 시간대의 유저 알람 목록을 조회한다.
            String alarmTime = alarm.getAlarmTime();
            Long userId = alarm.getUser().getUserId();
            Integer enabled = 1;
            String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            List<Alarm> alarmList = alarmRepository.findByUserUserIdAndAlarmTimeAndEnabledOrderByAlarmId(userId, alarmTime, enabled);

            alarmList.stream().forEach(alarmData -> {
                Long alarmDataId = alarmData.getAlarmId();

                // 알람 지역 조회
                List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmIdOrderByAlarmLocationId(alarmDataId);

                alarmLocationList.stream().forEach(alarmLocation -> {
                    AlarmLocationWeatherResponse alarmLocationWeatherResponse = new AlarmLocationWeatherResponse();

                    Long alarmLocationId = alarmLocation.getAlarmLocationId();
                    LocationGroup locationGroup = alarmLocation.getLocation().getLocationGroup();

                    String cityDo = alarmLocation.getLocation().getCityDo();
                    String guGun = alarmLocation.getLocation().getGuGun();
                    String eupMyun = alarmLocation.getLocation().getEupMyun();
                    String regionCd = alarmLocation.getLocation().getRegionCd();

                    alarmLocationWeatherResponse.setAlarmLocationId(alarmLocationId);
                    alarmLocationWeatherResponse.setLocationGroup(locationGroup);
                    alarmLocationWeatherResponse.setCityDo(cityDo);
                    alarmLocationWeatherResponse.setGuGun(guGun);
                    alarmLocationWeatherResponse.setEupMyun(eupMyun);
                    alarmLocationWeatherResponse.setRegionCd(regionCd);

                    // 알람 지역 시간 조회
                    List<AlarmLocationTime> alarmLocationTimeList = alarmLocationTimeRespository.findByAlarmLocationAlarmLocationIdOrderByAlarmLocationTimeId(alarmLocationId);

                    List<String> locationTimeList = new ArrayList<>();
                    alarmLocationTimeList.stream().forEach(alarmLocationTime -> {
                        String locationTime = alarmLocationTime.getLocationTime();
                        locationTimeList.add(locationTime);
                    });

                    alarmLocationWeatherResponse.setLocationTimeList(locationTimeList);
                    alarmLocationWeatherResponseList.add(alarmLocationWeatherResponse);
                });
            });

            // 중복 합치기
            List<Long> deleteAlarmLocationIdList = new ArrayList<>();

            List<String> useRegionCd = new ArrayList<>();
            alarmLocationWeatherResponseList.stream().forEach(alarmLocationWeatherResponse -> {
                Long alarmLocationId = alarmLocationWeatherResponse.getAlarmLocationId();
                String regionCd = alarmLocationWeatherResponse.getRegionCd();
                List<String> locationTimeList = alarmLocationWeatherResponse.getLocationTimeList();
                if (!useRegionCd.contains(regionCd)) {
                    alarmLocationWeatherResponseList.stream().forEach(alarmLocationWeatherResponseTemp -> {
                        Long alarmLocationIdTemp = alarmLocationWeatherResponseTemp.getAlarmLocationId();
                        String regionCdTemp = alarmLocationWeatherResponseTemp.getRegionCd();
                        List<String> locationTimeListTemp = alarmLocationWeatherResponseTemp.getLocationTimeList();
                        if (alarmLocationId != alarmLocationIdTemp && StringUtils.equals(regionCd, regionCdTemp)) {
                            locationTimeList.addAll(locationTimeListTemp);
                            // 중복제거
                            List<String> newlocationTimeList = locationTimeList.stream().distinct().collect(Collectors.toList());
                            // 오름차순
                            Collections.sort(newlocationTimeList);
                            alarmLocationWeatherResponse.setLocationTimeList(newlocationTimeList);
                            deleteAlarmLocationIdList.add(alarmLocationIdTemp);
                        }
                    });
                }
                useRegionCd.add(regionCd);
            });

            // 중복 데이터 삭제
            List<Long> newDeleteAlarmLocationIdList = deleteAlarmLocationIdList.stream().distinct().collect(Collectors.toList());
            Iterator<AlarmLocationWeatherResponse> iterator = alarmLocationWeatherResponseList.iterator();
            while (iterator.hasNext()) {
                AlarmLocationWeatherResponse element = iterator.next();
                Long alarmLocationId = element.getAlarmLocationId();

                for (Long deleteAlarmLocationId : newDeleteAlarmLocationIdList) {
                    if (alarmLocationId.equals(deleteAlarmLocationId)) {
                        iterator.remove();
                    }
                }
            }

            // 데이터 세팅
            alarmLocationWeatherResponseList.stream().forEach(alarmLocationWeatherResponse -> {
                List<AlarmLocationWeatherDataResponse> alarmLocationWeatherDataResponseDataList = new ArrayList<>();
                List<String> locationTimeList = alarmLocationWeatherResponse.getLocationTimeList();
                LocationGroup locationGroup = alarmLocationWeatherResponse.getLocationGroup();

                boolean locationRain = false;
                boolean amCk = false;
                boolean pmCk = false;
                for (String fcstTime : locationTimeList) {
                    AlarmLocationWeatherDataResponse alarmLocationWeatherDataResponse = new AlarmLocationWeatherDataResponse();
                    Weather weather = weatherService.selectLocationAndTimeForWeather(locationGroup, baseDate, fcstTime);
                    // 비오는 기준 PTY 강수형태
                    // (초단기)없음(0), 비(1), 비/눈(2), 눈(3), 빗방울(5), 빗방울눈날림(6), 눈날림(7)
                    // (단기)없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
                    String ptyTxt = weather.getPty();

                    boolean rain = false;
                    if (StringUtils.isNotBlank(ptyTxt)) {
                        try {
                            int pty = Integer.parseInt(ptyTxt);
                            // 1 부터 6까지 비
                            if (pty > 0 && pty < 7) {
                                rain = true;
                                locationRain = true;
                            }
                        } catch (NumberFormatException e) {
                            log.error("강수량이 숫자형식이 아닙니다. {}, {}", ptyTxt, e);
                        }
                    }
                    alarmLocationWeatherDataResponse.setRain(rain);
                    // 시간 AM, PM 변환
                    String timeOfDay = getTimeOfDay(fcstTime);
                    String alarmLocationTimeConvert = getTime(fcstTime, "HHmm", "hhmm");

                    if (rain) {
                        if (StringUtils.equals(timeOfDay, TimeOfDay.MORINING.getEngName())) {
                            amCk = true;
                        } else if (StringUtils.equals(timeOfDay, TimeOfDay.AFTERNOON.getEngName())) {
                            pmCk = true;
                        }
                    }
                    alarmLocationWeatherDataResponse.setLocationTime(alarmLocationTimeConvert);
                    alarmLocationWeatherDataResponse.setTimeOfDay(timeOfDay);
                    alarmLocationWeatherDataResponseDataList.add(alarmLocationWeatherDataResponse);
                }
                alarmLocationWeatherResponse.setLocationRain(locationRain);
                if (locationRain) {
                    String timeOfDay = "";
                    if (amCk && pmCk) {
                        timeOfDay = TimeOfDay.ALLDAY.getEngName();
                    } else if (amCk) {
                        timeOfDay = TimeOfDay.MORINING.getEngName();
                    } else if (pmCk) {
                        timeOfDay = TimeOfDay.AFTERNOON.getEngName();
                    }
                    alarmLocationWeatherResponse.setTimeOfDay(timeOfDay);
                    alarmLocationWeatherResponse.setAlarmLocationWeatherList(alarmLocationWeatherDataResponseDataList);
                }
            });

        } else {
            throw new CommonException("알람정보가 잘못되었습니다.", "444");
        }
        return alarmLocationWeatherResponseList;
    }

    @Transactional
    public void deleteAlarm(AlarmDeleteRequest alarmDeleteRequest) {
        Long alarmId = alarmDeleteRequest.getAlarmId();
        if (alarmId != null) {
            // 알람요일삭제
            alarmWeekRepository.deleteByAlarmAlarmId(alarmId);

            // 알람지역조회
            List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmIdOrderByAlarmLocationId(alarmId);

            for (AlarmLocation alarmLocation : alarmLocationList) {
                Long alarmLocationId = alarmLocation.getAlarmLocationId();

                // 알람시간삭제
                alarmLocationTimeRespository.deleteByAlarmLocationAlarmLocationId(alarmLocationId);
            }

            // 알람지역 삭제
            alarmLocationRespository.deleteByAlarmAlarmId(alarmId);

            // 알람삭제
            alarmRepository.deleteById(alarmId);
        } else {
            throw new CommonException("알람정보가 잘못되었습니다.", "443");
        }
    }

    public List<AlarmLocationWeatherDetailResponse> selectAlarmLocationWeatherDetail(Long alarmId) {
        List<AlarmLocationWeatherResponse> alarmLocationWeatherResponseList = selectAlarmLocationWeatherList(alarmId);

        List<AlarmLocationWeatherDetailResponse> alarmLocationWeatherDetailResponses = new ArrayList<>();

        alarmLocationWeatherResponseList.stream().forEach(alarmLocationWeatherResponse -> {
            AlarmLocationWeatherDetailResponse alarmLocationWeatherDetailResponse = new AlarmLocationWeatherDetailResponse();
            alarmLocationWeatherDetailResponse.setAlarmLocationWeatherList(alarmLocationWeatherResponse.getAlarmLocationWeatherList());
            alarmLocationWeatherDetailResponse.setLocationRain(alarmLocationWeatherResponse.isLocationRain());
            alarmLocationWeatherDetailResponse.setCityDo(alarmLocationWeatherResponse.getCityDo());
            alarmLocationWeatherDetailResponse.setGuGun(alarmLocationWeatherResponse.getGuGun());
            alarmLocationWeatherDetailResponse.setEupMyun(alarmLocationWeatherResponse.getEupMyun());
            alarmLocationWeatherDetailResponse.setTimeOfDay(alarmLocationWeatherResponse.getTimeOfDay());
            alarmLocationWeatherDetailResponse.setRegionCd(alarmLocationWeatherResponse.getRegionCd());
            alarmLocationWeatherDetailResponses.add(alarmLocationWeatherDetailResponse);
        });

        return alarmLocationWeatherDetailResponses;
    }

    @Transactional
    public void refreshAlarmLocationWeather() {
        // 현재시간 조회
        LocalTime localFromTime = LocalTime.now();
        // 혹시모를 분은 45분으로 고정
        localFromTime = localFromTime.withMinute(45);

        // 1시간추가에 분은 44분으로 고정
        LocalTime localToTime = localFromTime.plusHours(1).withMinute(44);

        String fromTime = localFromTime.format(DateTimeFormatter.ofPattern("HHmm"));
        String toTime = localToTime.format(DateTimeFormatter.ofPattern("HHmm"));

        String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Integer enabled = 1;
        // 대상 알람 조회
        List<Alarm> alarmList = alarmRepository.findByEnabledAndAlarmTimeBetween(enabled, fromTime, toTime);

        for (Alarm alarm : alarmList) {
            Long alarmId = alarm.getAlarmId();

            // 알람지역 조회
            List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmId(alarmId);

            for (AlarmLocation alarmLocation : alarmLocationList) {
                LocationGroup locationGroup = alarmLocation.getLocation().getLocationGroup();

                Long alarmLocationId = alarmLocation.getAlarmLocationId();

                List<AlarmLocationTime> alarmLocationTimeList = alarmLocationTimeRespository.findByAlarmLocationAlarmLocationId(alarmLocationId);

                // 알람 시간 조회
                for (AlarmLocationTime alarmLocationTime : alarmLocationTimeList) {
                    String fcstTime = alarmLocationTime.getLocationTime();
                    weatherService.refreshWeather(locationGroup, baseDate, fcstTime);
                }
            }
        }
    }

    public AlarmDetailResponse selectAlarmDetail(Long alarmId) {
        AlarmDetailResponse alarmDetailResponse = new AlarmDetailResponse();
        if (alarmId != null) {
            Alarm alarm = alarmRepository.findById(alarmId).orElseThrow(() -> new CommonException("알람정보가 올바르지 않습니다.", "443"));

            // 요일목록
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

            alarmDetailResponse.setAlarmId(alarm.getAlarmId());
            alarmDetailResponse.setAlarmSoundId(alarm.getAlarmSoundId());

            String asisTime = alarm.getAlarmTime();
            String timeOfDay = getTimeOfDay(asisTime);
            String alarmTime = getTime(asisTime, "HHmm", "hhmm");
            alarmDetailResponse.setTimeOfDay(timeOfDay);
            alarmDetailResponse.setAlarmTime(alarmTime);

            alarmDetailResponse.setReminder(alarm.getReminder());
            alarmDetailResponse.setEnabled(alarm.getEnabled());
            alarmDetailResponse.setUserId(alarm.getUser().getUserId());
            alarmDetailResponse.setVibration(alarm.getVibration());
            alarmDetailResponse.setVolume(alarm.getVolume());


            List<AlarmLocation> alarmLocationList = alarmLocationRespository.findByAlarmAlarmIdOrderByAlarmLocationId(alarmId);
            List<AlarmLocationDetailResponse> alarmLocationDetailResponseList = new ArrayList<>();

            // 주소 목록
            if (alarmLocationList != null && alarmLocationList.size() > 0) {
                for (AlarmLocation alarmLocation : alarmLocationList) {

                    Long alarmLocationId = alarmLocation.getAlarmLocationId();
                    String cityDo = alarmLocation.getLocation().getCityDo();
                    String guGun = alarmLocation.getLocation().getGuGun();
                    String eupMyun = alarmLocation.getLocation().getEupMyun();
                    String regionCd = alarmLocation.getLocation().getRegionCd();

                    // 지역 시간 조회
                    List<AlarmLocationTime> alarmLocationTimeList = alarmLocationTimeRespository.findByAlarmLocationAlarmLocationIdOrderByAlarmLocationTimeId(alarmLocationId);
                    List<AlarmLocationTimeDetailResponse> alarmLocationTimeDetailResponseList = new ArrayList<>();
                    for (AlarmLocationTime alarmLocationTime : alarmLocationTimeList) {
                        AlarmLocationTimeDetailResponse alarmLocationTimeDetailResponse = new AlarmLocationTimeDetailResponse();
                        alarmLocationTimeDetailResponse.setAlarmLocationTimeId(alarmLocationTime.getAlarmLocationTimeId());
                        alarmLocationTimeDetailResponse.setLocationTime(alarmLocationTime.getLocationTime());
                        alarmLocationTimeDetailResponseList.add(alarmLocationTimeDetailResponse);
                    }

                    AlarmLocationDetailResponse alarmLocationResponse = AlarmLocationDetailResponse.builder()
                            .cityDo(cityDo)
                            .guGun(guGun)
                            .eupMyun(eupMyun)
                            .regionCd(regionCd)
                            .alarmLocationId(alarmLocation.getAlarmLocationId())
                            .alarmLocationTimeDetail(alarmLocationTimeDetailResponseList)
                            .build();

                    alarmLocationDetailResponseList.add(alarmLocationResponse);
                }
            }

            alarmDetailResponse.setAlarmLocation(alarmLocationDetailResponseList);
            alarmDetailResponse.setAlarmWeek(alarmWeekResponseList);

        } else {
            throw new CommonException("알람정보가 올바르지 않습니다.", "444");
        }

        return alarmDetailResponse;
    }

    public List<AlarmSoundResponse> selectAlarmSoundList() {
        List<AlarmSoundResponse> alarmSoundList = new ArrayList<>();

        alarmSoundRepository.findAll().stream().forEach(alarmSound -> {
            AlarmSoundResponse alarmSoundResponse = new AlarmSoundResponse();
            alarmSoundResponse.setAlarmSoundId(alarmSound.getAlarmSoundId());
            alarmSoundResponse.setAlarmSoundName(alarmSound.getAlarmSoundName());
            alarmSoundResponse.setFileName(alarmSound.getFileName());
            alarmSoundList.add(alarmSoundResponse);
        });

        return alarmSoundList;
    }

    @Transactional
    public void updateAlarmUserId(Long asisUserId, Long tobeUserId) {
        List<Alarm> alarmList = alarmRepository.findByUserUserId(asisUserId);
        alarmList.stream().forEach(alarm -> alarm.setUser(User.builder().userId(tobeUserId).build()));
    }
}
