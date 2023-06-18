package com.ll.sbb.Notice;

import com.ll.sbb.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private int viewCount;

}
