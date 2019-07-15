package com.nova.cloud.gateway.securityConfig;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

  @Value("${app.jwt-config.expiration:3600}")
  private static long EXPIRATIONTIME;

  @Value("${app.jwt-config.clave:claveSecreta}")
  private static String SECRET;

  @Value("${app.jwt-config.prefix}")
  private static String TOKEN_PREFIX;

  @Value("${app.jwt-config.header}")
  private static String HEADER_STRING;
  
  @Resource 
  private Environment env;
  
  
  @Autowired static JwtAuthenticationConfig jwtAuthenticationConfig;


   static void addAuthentication(HttpServletResponse res, Authentication auth)  {
    System.out.println("jwtAuthenticationConfig " + jwtAuthenticationConfig.getExpiration());
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    String token = Jwts.builder().setSubject(auth.getName())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.ES512, signingKey)
        .setIssuedAt(new Date()).setIssuer("emailSender")
        .compact();
    res.addHeader(HEADER_STRING, " " + TOKEN_PREFIX + "  " + token);

  }



}
