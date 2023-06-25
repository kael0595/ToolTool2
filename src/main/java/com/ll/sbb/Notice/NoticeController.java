package com.ll.sbb.Notice;

import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketForm;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserRole;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Notice> paging = this.noticeService.getList(page, kw);
        List<Notice> noticeList = this.noticeService.getAll();
        int noticeCount = noticeList.size();
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("noticeCount", noticeCount);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "notice";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Notice notice = this.noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "NoticeDetail";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String noticeCreate(NoticeForm noticeForm) {
        return "NoticeWriter";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String noticeCreate(@Valid NoticeForm noticeForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "NoticeWriter";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.noticeService.create(noticeForm.getSubject(), noticeForm.getContent());
        return "redirect:/admin/";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/modify/{id}")
    public String noticeModify(NoticeForm noticeForm, @PathVariable("id") Integer id, Principal principal) {
        Notice notice = this.noticeService.getNotice(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());

        noticeForm.setSubject(notice.getSubject());
        noticeForm.setContent(notice.getContent());
        return "notice";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/modify/{id}")
    public String noticeModify(@Valid NoticeForm noticeForm, BindingResult bindingResult,
                               Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "market_form";
        }
        Notice notice = this.noticeService.getNotice(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());

        return String.format("redirect:/notice/detail/%s", id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/delete/{id}")
    public String noticeDelete(Principal principal, @PathVariable("id") Integer id) {
        Notice notice = this.noticeService.getNotice(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());

        return "redirect:/";
    }

}
