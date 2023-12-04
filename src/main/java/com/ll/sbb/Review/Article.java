package com.ll.sbb.Review;

import com.ll.sbb.Answer.Answer;
import com.ll.sbb.User.SiteUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
public class Article {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 50)
    private String subject;

    @Column(columnDefinition = "text")
    private String content;

    private int price;

    @ManyToMany
    private Set<SiteUser> voter;

    private int starScore;

    private int viewCount;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @Size(max = 20)
    private String type;
    @Size(max = 20)
    private String season;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private SiteUser author;

    private int likeCount;

    @Column
    private List<String> filenames;

    @Column
    private List<String> filepaths;

}