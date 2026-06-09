package com.example.Login.controller;

import com.example.Login.dto.LoginRequestDTO;
import com.example.Login.dto.UserResponse;
import com.example.Login.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("LoginRequestDTO", new LoginRequestDTO());
        return "login";
    }


//    @GetMapping("/dashboard")
//    public String dashboard()
//    {
//        return "dashboard";
//    }




}