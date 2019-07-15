package com.nova.cloud.gateway.securityConfig;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler  implements AuthenticationFailureHandler {


  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      org.springframework.security.core.AuthenticationException exception)
      throws IOException, ServletException {
    request.getRequestDispatcher("/error/" + getErrorCode(exception)).forward(request, response);
    System.out.println("Error");
    
  }
  
  private String getErrorCode(AuthenticationException exception) {
    if (exception instanceof BadCredentialsException
        || exception instanceof UsernameNotFoundException
        || exception instanceof ProviderNotFoundException) {
      return exception.getMessage();
    } else {
      return "SERVICE_EXCEPTION";
    }
  }


}
