package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.UserDetailsDTO;
import com.gavinfenton.quizolation.dto.UserLoginDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping(Endpoints.USERS + "/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<UserDetailsDTO> registerUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.registerUser(appUser));
    }

    @PostMapping(Endpoints.USERS + "/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<UserDetailsDTO> loginUser(@RequestBody UserLoginDTO userLogin) {
        return ResponseEntity.ok(appUserService.loginUser(userLogin));
    }

    @GetMapping(Endpoints.USERS)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDetailsDTO> getUser(Authentication authentication) {
        return ResponseEntity.ok(appUserService.getUser(((User) authentication.getPrincipal()).getUsername()));
    }

}
