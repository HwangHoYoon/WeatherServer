package com.jagiya.main.controller;

import com.jagiya.main.service.Impl.AppleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final AppleService appleService;


    @RequestMapping(value = "/appleLogin", method = RequestMethod.GET)
    public String login() {
        String appleUrl = appleService.getAppleLogin();
        return "redirect:"+appleUrl;
    }

}
