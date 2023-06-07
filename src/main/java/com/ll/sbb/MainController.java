package com.ll.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/MainPage")
    public String root() {
        return "MainPage";
    }
}
