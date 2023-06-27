package com.ll.sbb.Article;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.Answer.Answer;
import com.ll.sbb.User.SiteUser;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = search(kw);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getHigtList(int page, String kw, String sortKey) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = search(kw);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getRowList(int page, String kw, String sortKey) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = search(kw);
        return this.articleRepository.findAll(spec, pageable);
    }


    public Page<Article> getUserList(int page, String kw, String user) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = searchUser(user);
        return this.articleRepository.findAll(spec, pageable);
    }


    private Specification<Article> searchUser(String user) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add a condition to check for equality between the username field and the provided user value
            predicates.add(builder.equal(root.get("author").get("username"), user));

            // Add any other search conditions if needed
            // ...

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public Page<Article> getSeasonList(int page, String kw, String season) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = searchSeason(season);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getSeasonHighList(int page, String kw, String season, String sortKey) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = searchSeason(season);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getSeasonRowList(int page, String kw, String season, String sortKey) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = searchSeason(season);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Specification<Article> searchSeason(String sea) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Season 컬럼을 기준으로 검색 조건 생성
            if (sea != null) {
                Path<String> seasonPath = root.get("season");
                Predicate seasonPredicate = criteriaBuilder.equal(seasonPath, sea);
                predicates.add(seasonPredicate);
            }

            // 다른 조건들을 추가하고 싶다면 여기에 추가

            // 검색 조건들을 조합하여 최종 검색 조건 생성
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<Article> getTypeList(int page, String kw, String type) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = searchType(type);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getTypeHighList(int page, String kw, String type, String sortKey) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = searchType(type);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getTypeRowList(int page, String kw, String type, String sortKey) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = searchType(type);
        return this.articleRepository.findAll(spec, pageable);
    }

    public Specification<Article> searchType(String sea) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Season 컬럼을 기준으로 검색 조건 생성
            if (sea != null) {
                Path<String> typePath = root.get("type");
                Predicate typePredicate = criteriaBuilder.equal(typePath, sea);
                predicates.add(typePredicate);
            }

            // 다른 조건들을 추가하고 싶다면 여기에 추가

            // 검색 조건들을 조합하여 최종 검색 조건 생성
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Page<Article> getPriceList(int page, String kw, int min, int max) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        Specification<Article> spec = (root, query, builder) -> {
            return builder.between(root.get("price"), min, max);
        };
        // 비트윈으로 미니멈, 맥시멈값 지정후 spec변수에 담고 해당변수값의 아티클만 파인드올로 불러옴  //
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getPriceHigtList(int page, String kw, int min, int max, String sortKey) {

        Specification<Article> spec = (root, query, builder) -> {
            return builder.between(root.get("price"), min, max);
        };
        // 비트윈으로 미니멈, 맥시멈값 지정후 spec변수에 담고 해당변수값의 아티클만 파인드올로 불러옴  //
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        return this.articleRepository.findAll(spec, pageable);
    }

    public Page<Article> getPriceRowList(int page, String kw, int min, int max, String sortKey) {

        Specification<Article> spec = (root, query, builder) -> {
            return builder.between(root.get("price"), min, max);
        };
        // 비트윈으로 미니멈, 맥시멈값 지정후 spec변수에 담고 해당변수값의 아티클만 파인드올로 불러옴  //
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc(sortKey));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
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

    public void create(ArticleForm articleForm, SiteUser user, MultipartFile file) throws IOException {
        // 저장할 경로를 여기서 지정해줌
        String projectPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "files";

//      String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID(); // 랜덤으로 이름을 만들어줄 수 있음
        // uuid는 파일에 붙일 랜덤이름을 생성

        String fileName = uuid + "_" + file.getOriginalFilename();
        // 랜덤이름(uuid)을 앞에다 붙이고 그 다음에 언더바(_) 하고 파일이름을 뒤에 붙여서 저장될 파일 이름을 생성해줌
        String filePath = "/files/" + fileName;

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        Article q = new Article();
        q.setSubject(articleForm.getSubject());
        q.setContent(articleForm.getContent());
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        q.setPrice(articleForm.getPrice());
        q.setStarScore(articleForm.getStarScore());
        q.setSeason(articleForm.getSeason());
        q.setType(articleForm.getType());
        q.setFilename(fileName);
        q.setFilepath(filePath);
        this.articleRepository.save(q);
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

    public List<Article> getAuthor(SiteUser siteUser) {
        return this.articleRepository.findByAuthor(siteUser);
    }

    public void modify(Article article, String subject, String content, String type, String season, int price, int starscore) {
        article.setSubject(subject);
        article.setContent(content);
        article.setPrice(price);
        article.setSeason(season);
        article.setType(type);
        article.setStarScore(starscore);
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

    public List<Article> getByPrice(int min, int max) {

        return this.articleRepository.findByPriceBetween(min, max);
        // 비트윈을 사용하면 인트값의 범위를 지정하여 불러올 수 있다. (프라이스가 int값으로 지정되어있음 )
    }

    public void delVote(Article article, SiteUser siteUser) {
        article.getVoter().remove(siteUser);
        this.articleRepository.save(article);
    }

    public void viewCountUp(Article article) {

        article.setViewCount(article.getViewCount()+1);
       this.articleRepository.save(article);
    }
}