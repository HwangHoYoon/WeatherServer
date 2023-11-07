package com.jagiya.user.service;

import com.jagiya.alarm.service.AlarmService;
import com.jagiya.common.dto.Token;
import com.jagiya.common.dto.TokenDto;
import com.jagiya.common.exception.CommonException;
import com.jagiya.common.repository.TokenRepository;
import com.jagiya.common.utils.JwtUtil;
import com.jagiya.user.entity.User;
import com.jagiya.user.entity.UsersEditor;
import com.jagiya.user.enums.LoginType;
import com.jagiya.user.repository.UserRepository;
import com.jagiya.user.request.UserDetailUpdateRequest;
import com.jagiya.user.response.HtmlResponse;
import com.jagiya.user.response.UserDetailResponse;
import com.jagiya.user.response.UserRes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final AlarmService alarmService;

    private final JwtUtil jwtUtil;

    private final TokenRepository tokenRepository;

    public UserRes login(String snsId, String name, String email, Integer snsType, HttpServletResponse response) {
        Optional<User> usersInfo = userRepository.findBySnsTypeAndSnsId(snsType, snsId);

        User usersInfoRst;
        // 유저 정보가 있다면 업데이트 없으면 등록
        if (usersInfo.isPresent()) {

            //탈퇴 유저일때 정상화
            if(usersInfo.get().getDeleteFlag() == 1){
                log.info("탈퇴유저 확인 {}", usersInfo.get());
                usersInfo.get().setDeleteFlag(0);
                usersInfo.get().setDeleteDate(null);
                usersInfo.get().setModifyDate(new Date());
                log.info(" 재가입 확인 {}", usersInfo.get());
                userRepository.save(usersInfo.get());
            }

            log.info("기존유저 조회 {}", name);
            usersInfoRst = usersInfo.get();

        } else {
            // 비회원은 이름 비회원이라고 저장
            if (snsType == 0) {
                name = "비회원";
            } else {
                if (StringUtils.isBlank(name)) {
                    throw new CommonException("이름정보가 없습니다.", "666");
                }
            }

            log.info("신규유저 등록 {}", name);
            User user = User.builder()
                    .snsId(snsId)
                    .email(email)
                    .name(name)
                    .snsType(snsType)
                    .regDate(new Date())
                    .build();
            usersInfoRst = userRepository.save(user);
        }

        // 아이디 정보로 Token생성
        TokenDto tokenDto = jwtUtil.createAllToken(String.valueOf(usersInfoRst.getUserId()));

        // Refresh토큰 있는지 확인
        Optional<Token> refreshToken = tokenRepository.findByUserUserId(String.valueOf(usersInfoRst.getUserId()));

        // 있다면 새토큰 발급후 업데이트
        // 없다면 새로 만들고 디비 저장

        if(refreshToken.isPresent()) {
            tokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            Token newToken = new Token(tokenDto.getRefreshToken(),  User.builder().userId(usersInfoRst.getUserId()).build());
            tokenRepository.save(newToken);
        }

        // response 헤더에 Access Token / Refresh Token 넣음
        setHeader(response, tokenDto);

        return UserRes.builder()
                .userId(usersInfoRst.getUserId())
                .snsId(usersInfoRst.getSnsId())
                .email(usersInfoRst.getEmail())
                .name(usersInfoRst.getName())
                .snsName(LoginType.getLoginTypeName(usersInfoRst.getSnsType()))
                .build();
    }

    @Transactional
    public UserRes loginAndUserTransform(Long asisUserId, String snsId, String name, String email, Integer snsType, HttpServletResponse response) {

        // 새로운 계정으로 로그인
        UserRes tobeUserRes = login(snsId, name, email, snsType, response);
        Long tobeUserId = tobeUserRes.getUserId();
        if (tobeUserRes == null || tobeUserId == null) {
            throw new CommonException("정상적으로 로그인 되지 않았습니다.", "888");
        }

        User asisUser = userRepository.findById(asisUserId).orElseThrow(() -> new CommonException("회원정보가 없습니다.", "222"));

        // 정상 로그인 시 기존 알람 이관
        alarmService.updateAlarmUserId(asisUserId, tobeUserId);

        // 알람 이관 성공 시 기존 유저 삭제
        userRepository.deleteById(asisUserId);

        return tobeUserRes;
    }

    public UserDetailResponse selectUserDetail(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException("회원정보가 없습니다.", "222"));

        UserDetailResponse userDetailResponse = UserDetailResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        return userDetailResponse;
    }

    @Transactional
    public void updateUserName(UserDetailUpdateRequest userDetailUpdateRequest) {
        Long userId = userDetailUpdateRequest.getUserId();
        String name = userDetailUpdateRequest.getName();

        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException("회원정보가 없습니다.", "222"));

        UsersEditor.UsersEditorBuilder usersEditorBuilder = user.toEditor();
        UsersEditor usersEditor = usersEditorBuilder.name(name).build();
        user.edit(usersEditor);
    }

    public HtmlResponse selectTermsOfUse() throws IOException {
        Resource htmlResource = new ClassPathResource("static/termsOfUse.html");
        String htmlContent = StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);

        HtmlResponse htmlResponse = new HtmlResponse();

        htmlContent = htmlContent.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\\\"", "\'");;

        htmlResponse.setHtml(htmlContent);

        return htmlResponse;
    }

    public HtmlResponse selectPrivacyPolicy() throws IOException {
        Resource htmlResource = new ClassPathResource("static/privacyPolicy.html");
        String htmlContent = StreamUtils.copyToString(htmlResource.getInputStream(), StandardCharsets.UTF_8);

        HtmlResponse htmlResponse = new HtmlResponse();

        htmlContent = htmlContent.replaceAll("\r\n", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\\\"", "\'");;

        htmlResponse.setHtml(htmlContent);

        return htmlResponse;
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    public void logout(HttpServletResponse response, Long userId) {
        tokenRepository.deleteById(userId);
        jwtUtil.setHeaderAccessToken(response, "");
        jwtUtil.setHeaderRefreshToken(response, "");
    }
}
