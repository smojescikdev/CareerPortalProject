package com.CarrerPortalProject.CarrerPortalProject.controller;

import com.CarrerPortalProject.CarrerPortalProject.model.Users;
import com.CarrerPortalProject.CarrerPortalProject.model.UsersType;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersRepository;
import com.CarrerPortalProject.CarrerPortalProject.repository.UsersTypeRepository;
import com.CarrerPortalProject.CarrerPortalProject.services.UsersService;
import com.CarrerPortalProject.CarrerPortalProject.services.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UsersController {

    private final UsersRepository usersRepository;
    private final UsersTypeRepository usersTypeRepository;
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersRepository usersRepository, UsersTypeRepository usersTypeRepository, UsersTypeService usersTypeService, UsersService usersService) {
        this.usersRepository = usersRepository;
        this.usersTypeRepository = usersTypeRepository;
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;

    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        List<UsersType> usersTypes = usersTypeService.getAllUsersTypes();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }


    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users) {
        System.out.println("User:: " + users);
        usersService.addNewUser(users);
        usersRepository.save(users);
        return "redirect:/login";

    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
