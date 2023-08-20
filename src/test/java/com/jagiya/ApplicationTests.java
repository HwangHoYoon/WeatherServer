package com.jagiya;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.util.Base64;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		byte[] encodedKey = key.getEncoded();
		String strKey = Base64.getEncoder().encodeToString(encodedKey);
		System.out.println(strKey);
	}

}
