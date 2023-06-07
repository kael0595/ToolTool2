package com.ll.sbb.Market;

import com.ll.sbb.Category.Category;
import com.ll.sbb.MarketAnswer.MarketAnswer;

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

public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 50)
    private String subject;

    @Column(columnDefinition = "text")
    private String content;

    private int price;
    //Integer로 적어주면 null값도 허용할 수 있다.
    //가격은 직접 입력할거여서 column으로 작성.

    private int viewCount;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "market", cascade = CascadeType.REMOVE)
    private List<MarketAnswer> marketAnswerList;

    @OneToMany(mappedBy = "market", cascade = CascadeType.REMOVE)
    private List<Category> mainCategory;

    @OneToMany
    private Set<SiteUser> wishList;

    private String type;

    private String season;

    @ManyToOne
    private SiteUser author;

}