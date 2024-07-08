package com.bran.springboot.application.login_application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bran.springboot.application.login_application.entities.User;
import com.bran.springboot.application.login_application.repositories.RoleRepository;
import com.bran.springboot.application.login_application.services.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/userform")
    public String userForm(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("listTab", "active");
        return "user-form/user-view";

    }

    @PostMapping("/userform")
    public String createUser(
            @Valid @ModelAttribute("userForm") User user,
            BindingResult result,
            ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            model.addAttribute("formTab", "active");
        }
        else{
            try {
                userService.createUser(user);
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("formTab", "active");
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("roles", roleRepository.findAll());
            }
        }
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "user-form/user-view";
    }
}
