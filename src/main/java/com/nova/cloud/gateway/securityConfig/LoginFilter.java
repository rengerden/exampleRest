package com.nova.cloud.gateway.securityConfig;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.nova.cloud.gateway.BO.UserSession;
import com.nova.cloud.gateway.repository.UserSessionRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  
  private JwtAuthenticationConfig config;
  
  private UserSessionRepository userSessionRepository;


  public LoginFilter(JwtAuthenticationConfig config,
      AuthenticationManager authManager, UserSessionRepository userSessionRepository) {
    super(new AntPathRequestMatcher(config.getUrl()));
    setAuthenticationManager(authManager);
    this.config = config;
    this.userSessionRepository = userSessionRepository;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)throws AuthenticationException, IOException, ServletException {
    try {
      LoginRequest creds =
          new ObjectMapper().readValue
          (request.getInputStream(), LoginRequest.class);
      return getAuthenticationManager().authenticate
          (new UsernamePasswordAuthenticationToken(
          creds.getUsername(), creds.getPassword(), Collections.emptyList()));
    } catch (Exception e) {
      e.printStackTrace();
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
        .setExpiration(
            new Date(System.currentTimeMillis() + Long.parseLong(config.getExpiration())))
        .signWith(SignatureAlgorithm.HS512, config.getSecret()).setIssuedAt(new Date())
        .setIssuer("emailSender").compact();
    res.addHeader(config.getHeader(), " " + config.getPrefix() + "  " + token);
    userSessionRepository.save(new UserSession(token, "0101", "alfonso"));
    System.out.println(userSessionRepository.findById(token));
  }
}
