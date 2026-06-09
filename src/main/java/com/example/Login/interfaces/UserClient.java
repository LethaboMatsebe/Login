package com.example.Login.interfaces;

import com.example.Login.dto.UserResponse;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Demo",url ="http://localhost:8080")
public interface UserClient {
    @GetMapping("/api/users/email/{email}")
    UserResponse findByEmail(@PathVariable("email") String email);
}
