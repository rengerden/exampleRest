package com.nova.cloud.gateway.securityConfig;

import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nova.cloud.gateway.BO.LoginRequest;
import com.nova.cloud.gateway.repository.UserBdRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class LoginFilter extends AbstractAuthenticationProcessingFilter {
  

  @Autowired
  private UserBdRepository userBdRepository;
  
  @Autowired
  private JwtAuthenticationConfig config;


  public LoginFilter(JwtAuthenticationConfig config, AuthenticationManager authManager) {
    super(new AntPathRequestMatcher(config.getUrl()));
    setAuthenticationManager(authManager);
    this.config =config;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    try {
      
      LoginRequest creds =
          new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
      return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
          creds.getUsername(), creds.getPassword(), Collections.emptyList()));
    
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("" + e.getLocalizedMessage());
      throw new BadCredentialsException("Error al autenticarse: Datos incorrectos");
    }
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    SecurityContextHolder.clearContext();
    new CustomLoginFailureHandler().onAuthenticationFailure(request, response, failed);;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse res,
      FilterChain chain, Authentication auth) throws IOException, ServletException {

    String token = Jwts.builder().setSubject(auth.getName())
        .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(config.getExpiration())))
        .signWith(SignatureAlgorithm.HS512, config.getSecret())
        .setIssuedAt(new Date()).setIssuer("emailSender")
        .compact();
    res.addHeader(config.getHeader(), " " + config.getPrefix() + "  " + token);
        System.out.println("dasdasdas " +config.getExpiration());
        System.out.println("dasdasdas " +config.getSecret());
        System.out.println("dasdasdas " +config.getPrefix());
        System.out.println("dasdasdas " +config.getUrl());
        System.out.println("dasdasdas " +config.getHeader());
        
        
  }



}
