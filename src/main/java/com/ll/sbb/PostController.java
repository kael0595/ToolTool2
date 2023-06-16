package com.ll.sbb;

import com.ll.sbb.Util.HtmlUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {
    @PostMapping
    public void savePost(@RequestBody String content) {
        String cleanedContent = HtmlUtils.removePTags(content);
        // DB에 cleanedContent 저장하는 로직 추가
    }
}