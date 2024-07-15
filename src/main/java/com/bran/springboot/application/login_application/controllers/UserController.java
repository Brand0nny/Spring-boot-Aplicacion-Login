package com.bran.springboot.application.login_application.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bran.springboot.application.login_application.dto.ChangePasswordForm;
import com.bran.springboot.application.login_application.entities.User;
import com.bran.springboot.application.login_application.exception.IdorUsernameNotFound;
import com.bran.springboot.application.login_application.repositories.RoleRepository;
import com.bran.springboot.application.login_application.services.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @GetMapping({"/","/login"})
    public String index() {
        return "index";
    }




    @GetMapping("/userForm")
    public String userForm(Model model) {
         
        model.addAttribute("userForm", new User());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("listTab", "active");
        return "user-form/user-view";

    }

    @PostMapping("/userForm")
    public String createUser(
            @Valid @ModelAttribute("userForm") User user,
            BindingResult result,
            ModelMap model) {

        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            model.addAttribute("formTab", "active");
        } else {
            try {
                userService.createUser(user);
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("formTab", "active");
                model.addAttribute("roles", roleRepository.findAll());
            }
        }
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "user-form/user-view";
    }

    @GetMapping("/editUser/{id}")
    public String getEditUserForm(Model model, @PathVariable(name = "id") Long id) throws Exception {
        
        User userToEdit = userService.getUserById(id);

        model.addAttribute("userForm", userToEdit);
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("formTab", "active");

        model.addAttribute("editMode", "true");
        model.addAttribute("passwordForm", new ChangePasswordForm(id));
        return "user-form/user-view";
    }

    @PostMapping("/editUser")
    public String postEditUserForm(
            @Valid @ModelAttribute("userForm") User user,
            BindingResult result,
            ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("userForm", user);
            System.out.println("========================"+user);
            model.addAttribute("formTab", "active");
            model.addAttribute("editMode", "true");
            model.addAttribute("passwordForm", new ChangePasswordForm(user.getId()));

        } else {
            try {
                userService.updateUser(user);
                model.addAttribute("userForm", new User());
                model.addAttribute("listTab", "active");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", user);
                model.addAttribute("formTab", "active");
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("roles", roleRepository.findAll());
                model.addAttribute("editMode", "true");
                model.addAttribute("passwordForm", new ChangePasswordForm(user.getId()));

            }
        }
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        return "user-form/user-view";
    }




    @GetMapping("/editUser/cancel")
    public String cancelEditUserForm(Model model) {
        return "redirect:/userform";
    }





    @GetMapping("/deleteUser/{id}")
    public String deleteUser(Model model, @PathVariable(name = "id") Long id) {
        try {
            userService.deleteUser(id);
        } catch (IdorUsernameNotFound e) {
            model.addAttribute("listErrorMessage", e.getMessage());
        }
        
        return userForm(model);
    }

    @PostMapping("/editUser/changePassword")
    public ResponseEntity<String> postEditUseChangePassword(
            @Valid @RequestBody ChangePasswordForm form,
            Errors errors) {
        try {
            if (errors.hasErrors()) {
                String result = errors
                        .getAllErrors()
                        .stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.joining(""));
                throw new Exception(result);
            }
            userService.changePassword(form);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

        return ResponseEntity.ok("Success");

    }

    public void setUpModel(Model model) {
      
    }

}
