package com.example.Login.controller;

import com.example.Login.dto.LoginRequestDTO;
import com.example.Login.dto.UserResponse;
import com.example.Login.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor

public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequest,
                        HttpServletRequest request,
                        HttpServletResponse response,RedirectAttributes redirectAttributes)
    {
        try {
            String result = authService.login(loginRequest,request,response);


            if("login success".equalsIgnoreCase(result)){
                redirectAttributes.addFlashAttribute("success", "Login successful!");
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                boolean isAdmin = auth.getAuthorities().stream()
                        .anyMatch(r -> r.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));


                if (isAdmin) {
                    return "redirect:http://localhost:8083/dashboard/dashboard.xhtml";
                } else {
                    return "redirect:http://localhost:8083/dashboard/user.xhtml";
                }

            }
            else {
                redirectAttributes.addFlashAttribute("error", "Invalid credentials");
                return "redirect:/api/login";
            }

        }catch (Exception e){

            redirectAttributes.addFlashAttribute("error", "login failed" + e.getMessage());
            return "redirect:/login";


        }

    }


    @GetMapping("/login")
    public String showLoginForm()
    {
        return "login";
    }

//    @GetMapping("/dashboard")
//    public String dashboard()
//    {
//        return "dashboard";
//    }


}
