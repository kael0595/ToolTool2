package com.ll.sbb.MarketAnswer;

import com.ll.sbb.Market.Market;
import com.ll.sbb.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class MarketAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "text")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Market market;

    @ManyToOne
    private SiteUser author;

}