package com.ll.sbb.Notice;

import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.User.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<Notice> getList() {
        return this.noticeRepository.findAll();
    }

    public Page<Notice> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.noticeRepository.findAll(pageable);
    }

    public Notice getNotice(Integer id) {
        Optional<Notice> Notice = this.noticeRepository.findById(id);
        if (Notice.isPresent()) {
            return Notice.get();
        } else {
            throw new DataNotFoundException("notice not found");
        }
    }

    public void create(String subject, String content) {
        Notice q = new Notice();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.noticeRepository.save(q);
    }

    public List<Notice> getAll() {
        return this.noticeRepository.findAll();
    }

    public void modify(String subject, String content) {
        Notice q = new Notice();
        q.setSubject(subject);
        q.setContent(content);
        q.getModifyDate();
        this.noticeRepository.save(q);
    }

    public void delete(Notice notice) {
        this.noticeRepository.delete(notice);
    }

    public void viewCountUp(Notice notice) {
        notice.setViewCount(notice.getViewCount() + 1);
        this.noticeRepository.save(notice);
    }
}
