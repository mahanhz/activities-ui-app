package com.amhzing.activities.ui.web.client.angular.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(path = "/angular")
    public String index() {
        return "redirect:/angular/index.html";
    }
}
