package com.nova.cloud.gateway;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.nova.cloud.gateway.securityConfig.JwtAuthenticationConfig;

@SpringBootApplication
public class GatewayApplication{

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }
  

}
