package com.ll.sbb.Admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    //해당 관리자 사용자의 이름

    private String password;

    @Column(unique = true)
    private String email;

    private int adminLevel = 1;
    // 1:일반관리자 2:슈퍼관리자  / 최초생성할 슈퍼관리자 외 모두 일반관리자로.
}