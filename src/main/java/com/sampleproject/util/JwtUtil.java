package com.sampleproject.util;


import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sampleproject.entity.Login;
import com.sampleproject.entity.UserLogin;
import com.sampleproject.repositroy.UserLoginRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Autowired
	UserLoginRepository userLoginRepository;

	private static String secret = "this is secret";
	private static long expiryDuration = 60 * 60;

	public String generateJwt(Login login) {

		long millTime = System.currentTimeMillis();
		long expiryTime = millTime + expiryDuration * 1000;

		Date issuedAt = new Date(millTime);
		Date expiryAt = new Date(expiryTime);

		int integer = login.getId();
		String issuer = Integer.toString(integer);
		String ISSUER = issuer;

		// clamis
		Claims claims = Jwts.claims().setIssuer(ISSUER).setIssuedAt(issuedAt).setExpiration(expiryAt);
//		UserLogin user = userLoginRepository.findByUsername(userLogin.getName());
		
		Optional<UserLogin> userLogin2 = userLoginRepository.findById(login.getId());
		UserLogin user = userLogin2.get();
		claims.put("name", user.getName());
		claims.put("email_Id", user.getEmailId());

		// generat jwt using claims
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public void verify(String authorization) throws Exception {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
		} catch (Exception e) {
			throw new Exception();
		}
	}
}
 