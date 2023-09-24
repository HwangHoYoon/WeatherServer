package com.jagiya.main.controller;

import com.jagiya.main.service.Impl.AppleServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequiredArgsConstructor
@Controller
public class HomeController {
    private final AppleServiceImpl appleService;


    @Hidden
    @RequestMapping(value = "/appleLogin", method = RequestMethod.GET)
    public String login() {
        String appleUrl = appleService.getAppleLogin();
        return "redirect:"+appleUrl;
    }

}
