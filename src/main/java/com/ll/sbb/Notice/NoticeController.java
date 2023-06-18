package com.ll.sbb.Notice;

import com.ll.sbb.Market.Market;
import com.ll.sbb.Market.MarketForm;
import com.ll.sbb.User.SiteUser;
import com.ll.sbb.User.UserRole;
import com.ll.sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
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
        return "notice_detail";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String marketCreate(NoticeForm noticeForm) {
        return "notice_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String marketCreate(@Valid NoticeForm noticeForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (siteUser.getRoles().equals(UserRole.ADMIN.getValue())) {
            this.noticeService.create(noticeForm.getSubject(), noticeForm.getContent());
        } else {
            System.out.println("작성 권한이 없습니다.");
        }
        return "redirect:/notice/list";
    }


}
