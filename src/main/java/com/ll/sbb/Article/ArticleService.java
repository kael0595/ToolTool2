package com.ll.sbb.Article;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.Answer.Answer;
import com.ll.sbb.User.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private Specification<Article> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Article> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Article, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Article, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

    public Page<Article> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Article> spec = search(kw);
        return this.articleRepository.findAll(spec, pageable);
    }


    public Article getArticle(Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new DataNotFoundException("article not found");
        }
    }


    public void create(String subject, String content, SiteUser user, Integer price) {
        Article q = new Article();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        q.setPrice(price);
        this.articleRepository.save(q);
    }

    public void create(String subject, String content, Integer price) {
        Article q = new Article();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setPrice(price);
        this.articleRepository.save(q);
    }


    public List<Article> getMainCategory(String mainCategory) {
        return this.articleRepository.findByMainCategory(mainCategory);
    }

    public List<Article> getAll() {
        return this.articleRepository.findAll();
    }

    public List<Article> getByType(String type) {
        return this.articleRepository.findByType(type);
    }

    public List<Article> getBySeason(String season) {
        return this.articleRepository.findBySeason(season);
    }

    public void modify(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);
        article.setModifyDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    public void vote(Article article, SiteUser siteUser) {
        article.getVoter().add(siteUser);
        this.articleRepository.save(article);
    }
}