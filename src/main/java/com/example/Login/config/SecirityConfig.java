package com.example.Login.config;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
//import org.springframework.security.web.context.SecurityContextRepository;
//
//@Configuration
//public class SecirityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//
//                // Authorize requests
//                .authorizeHttpRequests(auth -> auth
//                       .requestMatchers("/api/login", "/login" , "/register").permitAll()
////                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/dashboard/**").hasAnyRole("USER","ADMIN")
//                        .anyRequest().authenticated() // all other requests need authentication
//                )
//                // Form login
//                .formLogin(form -> form
//                        .loginPage("/api/login")
//                        .loginProcessingUrl("/process-login-internal")
//                        .defaultSuccessUrl("/dashboard", true)
//                        .permitAll()
//                )
//                // Logout
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                )
//
//        .securityContext(context -> context
//                .securityContextRepository(securityContextRepository())
//        );
//
//        return http.build();
//    }
//
//    @Bean
//    public SecurityContextRepository securityContextRepository() {
//
//        return new HttpSessionSecurityContextRepository();
//    }
//
//
//    // Password encoder
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecirityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for simplicity (OK for server-side forms)
                .csrf(csrf -> csrf.disable())

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/login",
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**"
                        ).permitAll()
                        .requestMatchers("/dashboard/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )

                // IMPORTANT: we manage authentication manually
                .formLogin(form -> form.disable())

                // Logout is still fine
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // Use session-backed SecurityContext
                .securityContext(context -> context
                        .securityContextRepository(securityContextRepository())
                );

        return http.build();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

