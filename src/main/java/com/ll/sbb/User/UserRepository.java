package com.ll.sbb.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Integer> {

    Optional<SiteUser> findByusername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE SiteUser u SET u.mailKey = :mailKey WHERE u.email = :email AND u.id = :id")
    int updateMailKey(@Param("mailKey") int mailKey, @Param("email") String email, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE SiteUser u SET u.mailAuth = true WHERE u.email = :email AND u.mailKey = :mailKey")
    int updateMailAuth(@Param("email") String email, @Param("mailKey") int mailKey);

    @Modifying
    @Transactional
    @Query("UPDATE SiteUser u SET u.password = :password WHERE u.id = :id")
    void updateUserPassword(@Param("id") int id, @Param("password") String password);

    Optional<SiteUser> findByEmailAndUsername(String email, String username);

    long countByEmailAndMailAuth(String email, boolean mailAuth);

    Optional<SiteUser> findByEmail(String email);

    SiteUser findUserById(String userEmail);

    Optional<SiteUser> findByUsername(String username);

    SiteUser findById(int id);

    List<SiteUser> findUserByUserRole(UserRole userRole);
}
