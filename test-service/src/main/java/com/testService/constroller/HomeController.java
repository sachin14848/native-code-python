package com.testService.constroller;

import com.testService.dto.ErrorResponse;
import com.testService.entity.UserInfo;
import com.testService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/test")
public class HomeController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('READ')")
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username, Authentication authentication) {
        try {
            System.out.println(username);
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            System.out.println("Authenticated user: " + authentication.getName());
            System.out.println("Granted authorities: " + authorities);
            return new ResponseEntity<>(userService.getUserDetails(username, authentication), HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setError(e.getMessage());
            errorResponse.setStatus("Failed to retrieve user details");
            errorResponse.setCode(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage("Failed to retrieve user details :"+e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/save-user")
    public UserInfo saveUser(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo);
        return userService.saveUserDetails(userInfo);
    }

}