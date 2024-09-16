package com.authentication.Authentication.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DashBordController {


    @PreAuthorize("hasAuthority('SCOPE_READ')")
    @GetMapping("/welcome-message")
    public ResponseEntity<String> getFirstWelcomeMessage(Authentication authentication) {
        return ResponseEntity.ok("Welcome to the JWT Tutorial :" + authentication.getName() + "with scope:" + authentication.getAuthorities());
    }

    //    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PreAuthorize("hasAuthority('SCOPE_READ')")
    @GetMapping("/manager-message")
    public ResponseEntity<String> getManagerData(Principal principal) {
        return ResponseEntity.ok("Manager :: RAM" );
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('SCOPE_WRITE')")
    @PostMapping("/admin-message")
    public ResponseEntity<String> getAdminData(@RequestParam("message") String message, Principal principal) {
        return ResponseEntity.ok("Admin ::" + principal.getName() + " has this message:" + message);

    }

    @PreAuthorize("hasAuthority('SCOPE_READ')")
    @PostMapping("/xyz")
    public String getCheck(Principal principal) {
        return "Sachin mishra " + principal.getName();
    }

}
