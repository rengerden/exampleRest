package com.nova.cloud.gateway.securityConfig;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.nova.cloud.gateway.repository.UserBdRepository;

@Component
public class CustomAuthenticationPrvr implements AuthenticationProvider {

  @Autowired
  private UserBdRepository userBdRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String userName = authentication.getName();
    String psswrd = authentication.getCredentials().toString();
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    if (userName != null && userBdRepository.isValidUser(userName, psswrd) != 0) {
      return new UsernamePasswordAuthenticationToken(userName, psswrd, grantedAuthorities);
    } else {
      throw new BadCredentialsException("Error al autenticarse: Datos incorrectos");
    }

  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }


}
