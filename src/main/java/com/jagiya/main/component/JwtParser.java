package com.jagiya.main.component;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Base64Utils;

import java.security.PublicKey;
import java.util.Map;

@Component
@Slf4j
public class JwtParser {

    private static final String TOKEN_VALUE_DELIMITER = "\\.";
    private static final int HEADER_INDEX = 0;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Map<String, String> parseHeaders(String token) {
        try {
            String encodedHeader = token.split(TOKEN_VALUE_DELIMITER)[HEADER_INDEX];
            String decodedHeader = new String(Base64Utils.decodeFromUrlSafeString(encodedHeader));
            return OBJECT_MAPPER.readValue(decodedHeader, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e) {
//            throw UnauthorizedException.invalid();
            return null;
        }

    }

//    public Claims parseClaims(String idToken, PublicKey publicKey) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(publicKey)
//                    .build()
//                    .parseClaimsJws(idToken)
//                    .getBody();
//            } catch (ExpiredJwtException e) {
////            throw UnauthorizedException.expired();
//             }
////        } catch (ExpiredJwtException e) {
////            throw UnauthorizedException.expired();
////        }
////        catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
////            throw UnauthorizedException.invalid();
////        }
//    }


}
