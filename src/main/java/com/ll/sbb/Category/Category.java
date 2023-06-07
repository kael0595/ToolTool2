package com.ll.sbb.Category;

import com.ll.sbb.Article.Article;
import com.ll.sbb.Market.Market;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String name;

    @ManyToOne
    private Article article;

    @OneToMany(mappedBy = "mainCategory")
    private List<subCategory> subCategories;

    @ManyToOne
    private Market market;

    private String type;

    private String season;
}
