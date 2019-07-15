package com.nova.cloud.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nova.cloud.gateway.BO.UserBd;

public interface UserBdRepository extends JpaRepository<UserBd, Integer> {

  @Query(
      value = "select count(idUser) "
          + "from users where name = :nombre "
          + "and password = md5(:psw)", nativeQuery = true)
Integer isValidUser(@Param("nombre") String name, @Param("psw") String password);
}
