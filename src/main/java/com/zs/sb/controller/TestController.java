package com.zs.sb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/index.html")
    public String index(Model model){
        model.addAttribute("field","hello World!");
        return "index";
    }

}
