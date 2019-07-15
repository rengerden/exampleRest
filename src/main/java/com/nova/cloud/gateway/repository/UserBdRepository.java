package com.nova.cloud.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.nova.cloud.gateway.BO.UserBd;


public interface UserBdRepository extends JpaRepository<UserBd, Integer> {

  @Query(
      value = "select count(idUser) "
          + "from banco.users where name = :name "
          + "and password = md5(:password)", nativeQuery = true)
Integer isValidUser(@Param("name") String name, @Param("password") String password);
}
