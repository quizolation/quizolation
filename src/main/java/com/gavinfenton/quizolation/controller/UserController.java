package com.gavinfenton.quizolation.controller;

import com.gavinfenton.quizolation.constant.Endpoints;
import com.gavinfenton.quizolation.dto.UserDetailsDTO;
import com.gavinfenton.quizolation.dto.UserLoginDTO;
import com.gavinfenton.quizolation.dto.UserRegistrationDTO;
import com.gavinfenton.quizolation.entity.AppUser;
import com.gavinfenton.quizolation.mapper.UserDetailsMapper;
import com.gavinfenton.quizolation.mapper.UserLoginMapper;
import com.gavinfenton.quizolation.mapper.UserRegistrationMapper;
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

    private final UserRegistrationMapper userRegistrationMapper = UserRegistrationMapper.INSTANCE;
    private final UserLoginMapper userLoginMapper = UserLoginMapper.INSTANCE;
    private final UserDetailsMapper userDetailsMapper = UserDetailsMapper.INSTANCE;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * Registers a new user.
     * <p>
     * Permissions: Any unauthenticated user can register.
     *
     * @param registration Registration details.
     */
    @PostMapping(Endpoints.USERS + "/register")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<Void> registerUser(@RequestBody UserRegistrationDTO registration) {
        appUserService.registerUser(userRegistrationMapper.toUser(registration));

        return ResponseEntity.noContent().build();
    }

    /**
     * Logs in a user.
     * <p>
     * Permissions: Any unauthenticated users can attempt to log in.
     *
     * @param userLogin Log in details.
     * @return User details.
     */
    @PostMapping(Endpoints.USERS + "/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<UserDetailsDTO> loginUser(@RequestBody UserLoginDTO userLogin) {
        AppUser loggedIn = appUserService.loginUser(userLoginMapper.toUser(userLogin));

        return ResponseEntity.ok(userDetailsMapper.toDTO(loggedIn));
    }

    /**
     * Returns the user details of the authenticated user.
     * <p>
     * Permissions: Any user should be able to get their own details.
     *
     * @param authentication User authentication.
     * @return User details.
     */
    @GetMapping(Endpoints.USERS)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDetailsDTO> getUser(Authentication authentication) {
        AppUser user = appUserService.getUserByEmail(((AppUser) authentication.getPrincipal()).getEmail());

        return ResponseEntity.ok(userDetailsMapper.toDTO(user));
    }

}
