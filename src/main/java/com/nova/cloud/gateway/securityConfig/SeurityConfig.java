package com.nova.cloud.gateway.securityConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.nova.cloud.gateway.repository.UserSessionRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeurityConfig extends WebSecurityConfigurerAdapter{
  
  @Autowired
  private CustomAuthenticationPrvr authProvider;
  
  @Autowired 
  private JwtAuthenticationConfig appProperties;
  
  @Autowired  
  private UserSessionRepository userSessionRepository;

    
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
    .cors()
  .and()
    .csrf()
      .disable()
  .authorizeRequests()
    .antMatchers(HttpMethod.POST, "/login")
      .permitAll()
    .antMatchers("/logout")
      .permitAll()
    .anyRequest()
      .authenticated()      
  .and()
    .logout()
      .logoutUrl("/logout").permitAll()
      .clearAuthentication(true)
      .deleteCookies("JSESSIONID")
  .and()
    .addFilterBefore(new LoginFilter(appProperties, authenticationManager(),userSessionRepository),
        UsernamePasswordAuthenticationFilter.class);
    
  }
  
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider);
    
}
  


}
