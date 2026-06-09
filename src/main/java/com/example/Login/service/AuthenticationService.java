package com.example.Login.service;

import com.example.Login.dto.LoginRequestDTO;
import com.example.Login.dto.UserResponse;
import com.example.Login.interfaces.UserClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserClient userClient;

    private final PasswordEncoder passwordEncoder;

    // Use the same repository Spring Security is using
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public String login(LoginRequestDTO loginRequest, HttpServletRequest request, HttpServletResponse response) {
        UserResponse userResponse = userClient.findByEmail(loginRequest.getEmail());

        if (userResponse != null && passwordEncoder.matches(loginRequest.getPassword(),userResponse.getPassword())) {


            // Correct Role Prefix: Must be "ROLE_" (all caps, no extra space)
            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + userResponse.getRole().toUpperCase().trim())
            );

            // Create Authentication
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    userResponse.getEmail(), null, authorities);

            //Create and set Context
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            //CRITICAL: Save the context to the session/response
            securityContextRepository.saveContext(context, request, response);
            request.getSession().setAttribute("ROLE", userResponse.getRole().toUpperCase().trim());


            return "login success";
        }
        return "Invalid email or password";
    }
}
