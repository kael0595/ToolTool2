package com.ll.sbb.Answer;

import com.ll.sbb.Article.Article;
import com.ll.sbb.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "text")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Article article;

    @ManyToOne
    private SiteUser author;

}
