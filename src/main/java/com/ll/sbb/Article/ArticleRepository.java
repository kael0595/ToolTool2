package com.ll.sbb.Article;

import com.ll.sbb.User.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findByType(String type);


    List<Article> findByPriceBetween(int min, int max);

    List<Article> findBySeason(String season);

    Article findBySubject(String subject);

    Article findBySubjectAndContent(String subject, String content);

    List<Article> findBySubjectLike(String subject);

    List<Article> findByAuthor(SiteUser siteUser);

    Page<Article> findAll(Pageable pageable);

    Page<Article> findAll(Specification<Article> spec, Pageable pageable);


    @Query("SELECT DISTINCT q " +
            "FROM Article q " +
            "LEFT JOIN q.author u1 " +
            "LEFT JOIN q.answerList a " +
            "LEFT JOIN a.author u2 " +
            "WHERE q.subject LIKE %:kw% " +
            "OR q.content LIKE %:kw% " +
            "OR u1.username LIKE %:kw% " +
            "OR a.content LIKE %:kw% " +
            "OR u2.username LIKE %:kw%")
    Page<Article> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}