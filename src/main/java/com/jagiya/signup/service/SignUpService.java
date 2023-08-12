package com.jagiya.signup.service;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jagiya.common.enums.SnsType;
import com.jagiya.main.entity.SnsInfo;
import com.jagiya.main.entity.Users;
import com.jagiya.main.repository.SnsInfoRepository;
import com.jagiya.main.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class SignUpService {

    private final SnsInfoRepository snsInfoRepository;
    private final UsersRepository usersRepository;

    public String getKaKaoAccessToken(String code){
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=ec17d3e02513195aac9f3a41a32260af"); // TODO REST_API_KEY 입력
            sb.append("&client_secret=gmD5csmFGEjcU90cYU3pi2IEo1xasv91"); // TODO 인가코드 받은 client_secret 입력
            sb.append("&redirect_uri=http://localhost:8080/kakao_callback"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonElement element = JsonParser.parseString(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    public void createKakaoUser(String token) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result);

            long id = element.getAsJsonObject().get("id").getAsLong();
            String email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
            String nickname = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();

            Date connected_at = new Gson().fromJson(element.getAsJsonObject().get("connected_at"), Date.class);

            System.out.println("id : " + id);
            System.out.println("email : " + email);
            System.out.println("nickname : " + nickname);
            System.out.println("connected_at : " + connected_at);

            br.close();

            SnsInfo snsInfoRst = snsInfoRepository.findBySnsTypeAndSnsProfile(SnsType.KAKAo.getCode(), email)
                    .orElseGet(() -> {

                        Users users = Users.builder()
                                .email(email)
                                .nickname(nickname)
                                .username(nickname)
                                .deleteFlag(0)
                                .agreesFalg(0)
                                .regDate(new Date())
                                .build();
                        Users usersRst = usersRepository.save(users);

                        SnsInfo snsInfo = SnsInfo.builder()
                                .snsType(SnsType.KAKAo.getCode())
                                .snsName(SnsType.KAKAo.getName())
                                .snsProfile(email)
                                .usersTb(usersRst)
                                .accessToken(token)
                                .snsConnectDate(connected_at)
                                .build();

                        return snsInfoRepository.save(snsInfo);
                    });

            System.out.println("snsInfoRst : " + snsInfoRst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
