package com.mozgoff.blog.controller;

import com.mozgoff.blog.service.UserRepr;
import com.mozgoff.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model){
        model.addAttribute("user", new UserRepr());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerNewUser(@Valid UserRepr userRepr, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "registration";
        }

        if (!userRepr.getPassword().equals(userRepr.getRepeatPassword())){
            bindingResult.rejectValue("password", "","Пароли не совпадают");
            return "registration";
        }

        userService.create(userRepr);
        return "redirect:/login";
    }
}
