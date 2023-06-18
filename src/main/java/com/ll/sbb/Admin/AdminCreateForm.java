//package com.ll.sbb.Admin;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotEmpty;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class AdminCreateForm {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Email
//    @NotEmpty(message = "이메일은 필수항목입니다.")
//    private String email;
//
//    @NotEmpty(message = "닉네임은 필수항목입니다.")
//    private String username;
//
//    private String password1;
//
//    private String password2;
//
//}