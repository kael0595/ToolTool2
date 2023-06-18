package com.ll.sbb.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {

    Optional<SiteUser> findByusername(String username);

    @Modifying
    @Query("UPDATE SiteUser u SET u.mailKey = :mailKey WHERE u.email = :email AND u.id = :id")
    int updateMailKey(@Param("mailKey") String mailKey, @Param("email") String email, @Param("id") Long id);

    @Modifying
    @Query("UPDATE SiteUser u SET u.mailAuth = true WHERE u.email = :email AND u.mailKey = :mailKey")
    int updateMailAuth(@Param("email") String email, @Param("mailKey") String mailKey);

    long countByEmailAndMailAuth(String email, boolean mailAuth);

    Optional<SiteUser> findByEmail(String email);
}

//    SiteUser findUserByUserId(String userEmail);

