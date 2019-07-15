package com.nova.cloud.gateway.BO;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("personas")
public class UserSession {
  
  @Id
  private String token ;
  
  @Indexed
  private String account;
  
  @Indexed
  private String userName;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "UserSession [token=" + token + ", account=" + account + ", userName=" + userName + "]";
  }

  @PersistenceConstructor
  public UserSession(String token, String account, String userName) {
    super();
    this.token = token;
    this.account = account;
    this.userName = userName;
  }
  

}
