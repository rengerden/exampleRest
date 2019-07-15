package com.nova.cloud.gateway.redisConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.nova.cloud.gateway.BO.UserSession;
import com.nova.cloud.gateway.securityConfig.JwtAuthenticationConfig;

@Configuration
public class RedisConfig {

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
    return connectionFactory;
  }

  @Bean
  public RedisTemplate<String, UserSession> redisTemplate() {
    RedisTemplate<String, UserSession> redisTemplate = new RedisTemplate<String, UserSession>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    return redisTemplate;
  }
  
  @Bean
  public JwtAuthenticationConfig jwtConfig() {
      return new JwtAuthenticationConfig();
  }
}
