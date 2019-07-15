package com.nova.cloud.gateway.BO;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserBd {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idUser;  

  @Basic
  private String name;
  
  @Basic
  private String password;
  
  public int getIdUser() {
    return idUser;
  }
  public void setIdUser(int idUser) {
    this.idUser = idUser;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

}
