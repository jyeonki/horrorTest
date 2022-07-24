package com.project.horror.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/horror")
public class FrontController {

    @GetMapping("/welcome")
    public String welcome()
    {
        return "/welcome";
    }
}
